
package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

//import org.apache.log4j.Logger;

import com.rays.pro4.Bean.TransportationBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Exception.RecordNotFoundException;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.EmailBuilder;
import com.rays.pro4.Util.EmailMessage;
import com.rays.pro4.Util.EmailUtility;
import com.rays.pro4.Util.JDBCDataSource;

/**
 * JDBC Implementation of TransportationModel.
 * 
 * @author Prakhar Solanki
 *
 */

public class TransportationModel {
	private static Logger log = Logger.getLogger(TransportationModel.class);

	/**
	 * Find next PK of Transportation
	 *
	 * @throws DatabaseException
	 */

	public int nextPK() throws DatabaseException {

		log.debug("Model nextPK Started");

		String sql = "select max(id) from st_transportation";
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
		log.debug("Model nextPK Started");
		return pk + 1;

	}

	/**
	 * Transportation Add
	 *
	 * @param bean
	 * @throws DatabaseException
	 *
	 */

	/**
	 * @param bean
	 * @return
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public long add(TransportationBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");

		String sql = "insert into st_transportation values(?,?,?,?,?)";

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getDiscription());
			pstmt.setInt(3, bean.getMode());
			pstmt.setDate(4, new java.sql.Date(bean.getDate().getTime()));
			pstmt.setLong(5, bean.getCost());

			int i = pstmt.executeUpdate();
			System.out.println(i);
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception ...", e);
			try {
				e.printStackTrace();
				conn.rollback();

			} catch (Exception e2) {
				e2.printStackTrace();
				// application exception
				throw new ApplicationException("Exception : add rollback exceptionn" + e2.getMessage());
			}
		}

		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Add End");
		return pk;

	}

	/**
	 * Delete a Transportation
	 *
	 * @param bean
	 * @throws DatabaseException
	 */
	public void delete(TransportationBean bean) throws ApplicationException {
		log.debug("Model delete start");
		String sql = "delete from st_transportation where id=?";
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			log.error("DataBase Exception", e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception: Delete rollback Exception" + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Delete End");
	}

	/**
	 * Find Transportation by PK
	 *
	 * @param pk : get parameter
	 * @return bean
	 * @throws DatabaseException
	 */

	public TransportationBean findByPK(long pk) throws ApplicationException {
		log.debug("Model findBy PK start");
		String sql = "select * from st_transportation where id=?";
		TransportationBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TransportationBean();
				bean.setId(rs.getLong(1));
				bean.setDiscription(rs.getString(2));
				bean.setMode(rs.getInt(3));
				bean.setDate(rs.getDate(4));
				bean.setCost(rs.getLong(5));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("DataBase Exception ", e);
			throw new ApplicationException("Exception : Exception in getting Transportation by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Method Find By PK end");
		return bean;
	}

	/**
	 * Update a transportation
	 *
	 * @param bean
	 * @throws DatabaseException
	 */

	public void update(TransportationBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model Update Start");
		String sql = "update st_transportation set discription=?, mode=?, date=?, cost=? where id=? ";
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, bean.getDiscription());
			pstmt.setInt(2, bean.getMode());
			pstmt.setDate(3, new java.sql.Date(bean.getDate().getTime()));
			pstmt.setLong(4, bean.getCost());
			pstmt.setLong(5, bean.getId());

			int i = pstmt.executeUpdate();
			System.out.println("update transportation>> " + i);
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("DataBase Exception", e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
				throw new ApplicationException("Exception : Update Rollback Exception " + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Update End ");
	}

	/**
	 * Search Transportation
	 *
	 * @param bean : Search Parameters
	 * @throws DatabaseException
	 */

	public List search(TransportationBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * Search Transportation with pagination
	 *
	 * @return list : List of Transportations
	 * @param bean     : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 *
	 * @throws DatabaseException
	 */

	public List search(TransportationBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model Search Start");
		StringBuffer sql = new StringBuffer("select * from st_transportation where 1=1");
		if (bean != null) {

			if (bean.getDiscription() != null && bean.getDiscription().length() > 0) {
				sql.append(" AND DISCRIPTION like '" + bean.getDiscription() + "%'");
			}

			if (bean.getMode() > 0) {
				sql.append(" AND MODE = " + bean.getMode());
			}

			if (bean.getDate() != null && bean.getDate().getTime() > 0) {
				Date d = new Date(bean.getDate().getTime());
				sql.append(" AND Date like '" + d + "%'");
			}
			if (bean.getId() > 0) {
				sql.append(" AND ID = " + bean.getId());
			}

			if (bean.getCost() > 0) {
				sql.append(" AND COST = " + bean.getCost());
			}

		}
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}
		System.out.println("sql query search >>= " + sql.toString());
		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TransportationBean();
				bean.setId(rs.getLong(1));
				bean.setDiscription(rs.getString(2));
				bean.setMode(rs.getInt(3));
				bean.setDate(rs.getDate(4));
				bean.setCost(rs.getLong(5));

				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception", e);
			throw new ApplicationException("Exception: Exception in Search Transportation");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Search end");
		return list;

	}

	/**
	 * Get List of Transportation
	 *
	 * @return list : List of Transportation
	 * @throws DatabaseException
	 */

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * Get List of Transportation with pagination
	 *
	 * @return list : List of transportations
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws DatabaseException
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_transportation");

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
				TransportationBean bean = new TransportationBean();

				bean.setId(rs.getLong(1));
				bean.setDiscription(rs.getString(2));
				bean.setMode(rs.getInt(3));
				bean.setDate(rs.getDate(4));
				bean.setCost(rs.getLong(5));

				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception...", e);
			throw new ApplicationException("Exception : Exception in getting list of transportations");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model list End");
		return list;
	}

}
