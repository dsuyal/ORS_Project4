package com.rays.pro4.Model;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.rays.pro4.Bean.CustomerBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.JDBCDataSource;

public class CustomerModel {

	public int nextPK() throws DatabaseException {

		String sql = "SELECT MAX(ID) FROM st_customer";
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {

			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;

	}

	public long add(CustomerBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "insert into st_customer values(?,?,?,?,?)";


		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getLocation());
			pstmt.setString(4, bean.getPhoneNumber());
			pstmt.setInt(5, bean.getImportance());

			int a = pstmt.executeUpdate();
			System.out.println("ho gyua re" + a);
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				e.printStackTrace();
				conn.rollback();

			} catch (Exception e2) {
				e2.printStackTrace();

				throw new ApplicationException("Exception : add rollback exceptionn" + e2.getMessage());
			}
		}

		finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;

	}

	public void delete(CustomerBean bean) throws ApplicationException {

		String sql = "DELETE FROM st_customer WHERE ID=?";
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, bean.getId());
			int i = pstmt.executeUpdate();
			System.out.println(i + "data deleted");
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception: Delete rollback Exception" + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public void update(CustomerBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "UPDATE st_customer SET NAME=?,LOCATION=?,PHONE_NUMBER=?,IMPORTANCE=? WHERE ID=?";
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getLocation());
			pstmt.setString(3, bean.getPhoneNumber());
			pstmt.setInt(4, bean.getImportance());
			pstmt.setLong(5, bean.getId());

			pstmt.executeUpdate();
			int i = pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();

			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
				throw new ApplicationException("Exception : Update Rollback Exception " + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	/*
	 * public List search(CustomerBean bean) throws ApplicationException { return
	 * search(bean); }
	 */

	public List search(CustomerBean bean, int pageNo, int pageSize) throws ApplicationException {

	    StringBuffer sql = new StringBuffer("SELECT * FROM st_customer WHERE 1=1");

	    if (bean != null) {

	        if (bean.getId() > 0) {
	            sql.append(" AND ID = " + bean.getId());
	        }

	        if (bean.getName() != null && bean.getName().length() > 0) {
	            sql.append(" AND NAME LIKE '" + bean.getName() + "%'");
	        }


	        if (bean.getLocation() != null && bean.getLocation().length() > 0) {
	            sql.append(" AND LOCATION LIKE '" + bean.getLocation() + "%'");
	        }

	        if (bean.getPhoneNumber() != null && bean.getPhoneNumber().length() > 0) {
	            sql.append(" AND PHONE_NUMBER = '" + bean.getPhoneNumber() + "'");
	        }

	        if (bean.getImportance() > 0) {
	            sql.append(" AND IMPORTANCE = " + bean.getImportance());
	        }

	        if (pageSize > 0) {
	            pageNo = (pageNo - 1) * pageSize;
	            sql.append(" LIMIT " + pageNo + ", " + pageSize);
	        }
	    }

	    System.out.println("SQL >>>>>>> " + sql.toString());

	    List list = new ArrayList();
	    Connection conn = null;

	    try {
	        conn = JDBCDataSource.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            CustomerBean cb = new CustomerBean(); // Do NOT reuse input 'bean'
	            cb.setId(rs.getLong(1));
	            cb.setName(rs.getString(2));
	            cb.setLocation(rs.getString(3));
	            cb.setPhoneNumber(rs.getString(4));
	            cb.setImportance(rs.getInt(5));
	            list.add(cb);
	        }

	        rs.close();
	        pstmt.close();

	    } catch (Exception e) {
	        throw new ApplicationException("Exception: Exception in Search customer");
	    } finally {
	        JDBCDataSource.closeConnection(conn);
	    }

	    return list;
	}

	public CustomerBean findByPK(long pk) throws ApplicationException {

		String sql = "SELECT * FROM st_customer WHERE ID=?";
		CustomerBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CustomerBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setLocation(rs.getString(3));
				bean.setPhoneNumber(rs.getString(4));
				bean.setImportance(rs.getInt(5));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();

			throw new ApplicationException("Exception : Exception in getting customer by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_customer");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CustomerBean bean = new CustomerBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setLocation(rs.getString(3));
				bean.setPhoneNumber(rs.getString(4));
				bean.setImportance(rs.getInt(5));

				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}