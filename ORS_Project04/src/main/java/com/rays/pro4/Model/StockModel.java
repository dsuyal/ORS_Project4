package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rays.pro4.Bean.StockBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.JDBCDataSource;


public class StockModel {

    public int nextPK() throws DatabaseException {
        String sql = "SELECT MAX(ID) FROM st_stock";
        Connection conn = null;
        int pk = 0;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                pk = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            throw new DatabaseException("Exception: Exception in getting PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk + 1;
    }

    public long add(StockBean bean) throws ApplicationException, DuplicateRecordException {
        String sql = "INSERT INTO st_stock (id, quantity, purchasePrice, purchaseDate, orderType) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            pk = nextPK();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pk);
            pstmt.setInt(2, bean.getQuantity());
            pstmt.setLong(3, bean.getPurchasePrice());
            pstmt.setDate(4, new java.sql.Date(bean.getPurchaseDate().getTime()));
            pstmt.setString(5, bean.getOrderType());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception: add rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception: Exception in adding stock record");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk;
    }

    public void delete(StockBean bean) throws ApplicationException {
        String sql = "DELETE FROM st_stock WHERE ID=?";
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
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception: delete rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception: Exception in deleting stock record");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public void update(StockBean bean) throws ApplicationException, DuplicateRecordException {
        String sql = "UPDATE st_stock SET quantity=?, purchasePrice=?, purchaseDate=?, orderType=? WHERE ID=?";
        Connection conn = null;
        int pk = 0;
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(sql);
           
            pstmt.setInt(1, bean.getQuantity());
            pstmt.setLong(2, bean.getPurchasePrice());
            pstmt.setDate(3, new java.sql.Date(bean.getPurchaseDate().getTime()));

            pstmt.setString(4, bean.getOrderType());
            pstmt.setInt(5, pk);

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception: update rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception: Exception in updating stock record");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public List search( StockBean bean, int pageNo, int pageSize) throws ApplicationException {
        StringBuilder sql = new StringBuilder("SELECT * FROM st_stock WHERE 1=1");
        List<StockBean> list = new ArrayList<>();
        Connection conn = null;

        try {
            if (bean != null) {
                if (bean.getId() > 0) {
                    sql.append(" AND ID = ").append(bean.getId());
                }
                if (bean.getQuantity() > 0) {
                    sql.append(" AND quantity = ").append(bean.getQuantity());
                }
                if (bean.getPurchasePrice() > 0) {
                    sql.append(" AND purchasePrice = ").append(bean.getPurchasePrice());
                }
                if (bean.getPurchaseDate() != null) {
                    sql.append(" AND purchaseDate = '").append(new java.sql.Date(bean.getPurchaseDate().getTime()));
                }
                if (bean.getOrderType() != null && !bean.getOrderType().isEmpty()) {
                    sql.append(" AND orderType = '").append(bean.getOrderType()).append("'");
                }
            }

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                sql.append(" LIMIT ").append(pageNo).append(", ").append(pageSize);
            }

            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new StockBean();
                bean.setId(rs.getLong("id"));
                bean.setQuantity(rs.getInt("quantity"));
                bean.setPurchasePrice(rs.getLong("purchasePrice"));
                bean.setPurchaseDate(rs.getDate("purchaseDate"));
                bean.setOrderType(rs.getString("orderType"));
                list.add(bean);
            }
            rs.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception: Exception in search stock records");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return list;
    }

    public StockBean findByPK(long pk) throws ApplicationException {
        String sql = "SELECT * FROM st_stock WHERE ID=?";
        StockBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                bean = new StockBean();
                bean.setId(rs.getLong("id"));
                bean.setQuantity(rs.getInt("quantity"));
                bean.setPurchasePrice(rs.getLong("purchasePrice"));
                bean.setPurchaseDate(rs.getDate("purchaseDate"));
                bean.setOrderType(rs.getString("orderType"));
            }
            rs.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception: Exception in finding stock record by PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    public List list() throws ApplicationException {
        return list(0, 0);
    }

    public List list(int pageNo, int pageSize) throws ApplicationException {
        String sql = "SELECT * FROM st_stock";
        List<StockBean> list = new ArrayList<>();
        Connection conn = null;

        try {
            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                sql += " LIMIT " + pageNo + ", " + pageSize;
            }

            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                StockBean bean = new StockBean();
                bean.setId(rs.getLong("id"));
                bean.setQuantity(rs.getInt("quantity"));
                bean.setPurchasePrice(rs.getLong("purchasePrice"));
                bean.setPurchaseDate(rs.getDate("purchaseDate"));
                bean.setOrderType(rs.getString("orderType"));
                list.add(bean);
            }
            rs.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception: Exception in listing stock records");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return list;
    }
}
