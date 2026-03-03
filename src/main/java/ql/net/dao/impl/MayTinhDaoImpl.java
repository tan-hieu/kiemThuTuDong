/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ql.net.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import ql.net.dao.MayTinhDao;
import ql.net.entity.MayTinh;
import ql.net.util.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *
 * @author ADMIN
 */
public class MayTinhDaoImpl implements MayTinhDao{
    // SQL Queries
    private final String SELECT_ALL_SQL = 
        "SELECT MaMay, TenMay, LoaiMay, GiaTheoGio, TrangThai FROM MayTinh ORDER BY MaMay";
    
    private final String SELECT_BY_ID_SQL = 
        "SELECT MaMay, TenMay, LoaiMay, GiaTheoGio, TrangThai FROM MayTinh WHERE MaMay = ?";
    
    private final String SELECT_BY_LOAI_SQL = 
        "SELECT MaMay, TenMay, LoaiMay, GiaTheoGio, TrangThai FROM MayTinh WHERE LoaiMay = ? ORDER BY MaMay";
    
    private final String SELECT_BY_TRANGTHAI_SQL = 
        "SELECT MaMay, TenMay, LoaiMay, GiaTheoGio, TrangThai FROM MayTinh WHERE TrangThai = ? ORDER BY MaMay";
    
    private final String INSERT_SQL = 
        "INSERT INTO MayTinh (MaMay, TenMay, LoaiMay, GiaTheoGio, TrangThai) VALUES (?, ?, ?, ?, ?)";
    
    private final String UPDATE_SQL = 
        "UPDATE MayTinh SET TenMay = ?, LoaiMay = ?, GiaTheoGio = ?, TrangThai = ? WHERE MaMay = ?";
    
    private final String DELETE_SQL = 
        "DELETE FROM MayTinh WHERE MaMay = ?";
    
    private final String UPDATE_TRANGTHAI_SQL = 
        "UPDATE MayTinh SET TrangThai = ? WHERE MaMay = ?";
    
    private final String COUNT_BY_LOAI_SQL = 
        "SELECT COUNT(*) FROM MayTinh WHERE LoaiMay = ?";
    
    private final String COUNT_BY_TRANGTHAI_SQL = 
        "SELECT COUNT(*) FROM MayTinh WHERE TrangThai = ?";
    
    @Override
    public List<MayTinh> selectAll() {
        List<MayTinh> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(SELECT_ALL_SQL)) {
            while (rs.next()) {
                MayTinh entity = mapResultSetToEntity(rs);
                list.add(entity);
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi selectAll MayTinh: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return list;
    }
    
    @Override
    public MayTinh selectById(String maMay) {
        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_ID_SQL, maMay)) {
            if (rs.next()) {
                return mapResultSetToEntity(rs);
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi selectById MayTinh: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return null;
    }
    
    @Override
    public List<MayTinh> selectByLoai(String loaiMay) {
//        List<MayTinh> list = new ArrayList<>();
//        try {
//            ResultSet rs = XJdbc.executeQuery(SELECT_BY_LOAI_SQL, loaiMay);
//            while (rs.next()) {
//                MayTinh mt = new MayTinh(
//                    rs.getString("MaMay"),
//                    rs.getString("TenMay"),
//                    rs.getString("LoaiMay"),
//                    rs.getBigDecimal("GiaTheoGio"),
//                    rs.getString("TrangThai")
//                );
//                list.add(mt);
//            }
//            rs.getStatement().getConnection().close();
//        } catch (Exception e) {
//            System.out.println("Lỗi"+e);
//            e.printStackTrace();
//        }
//        return list;
        List<MayTinh> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(SELECT_BY_LOAI_SQL, loaiMay);
            while (rs.next()) {
                MayTinh mt = mapResultSetToEntity(rs); 
                list.add(mt);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            System.out.println("Lỗi"+e);
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public List<MayTinh> selectByTrangThai(String trangThai) {
        List<MayTinh> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_TRANGTHAI_SQL, trangThai)) {
            while (rs.next()) {
                MayTinh entity = mapResultSetToEntity(rs);
                list.add(entity);
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi selectByTrangThai MayTinh: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return list;
    }
    
    @Override
    public boolean insert(MayTinh entity) {
        try {
            int result = XJdbc.executeUpdate(INSERT_SQL,
                entity.getMaMay(),
                entity.getTenMay(),
                entity.getLoaiMay(),
                entity.getGiaTheoGio(),
                entity.getTrangThai()
            );
            return result > 0;
        } catch (Exception ex) {
            System.err.println("Lỗi insert MayTinh: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public boolean update(MayTinh entity) {
        try {
            int result = XJdbc.executeUpdate(UPDATE_SQL,
                entity.getTenMay(),
                entity.getLoaiMay(),
                entity.getGiaTheoGio(),
                entity.getTrangThai(),
                entity.getMaMay()
            );
            return result > 0;
        } catch (Exception ex) {
            System.err.println("Lỗi update MayTinh: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public boolean delete(String maMay) {
        try {
            int result = XJdbc.executeUpdate(DELETE_SQL, maMay);
            return result > 0;
        } catch (Exception ex) {
            System.err.println("Lỗi delete MayTinh: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public boolean updateTrangThai(String maMay, String trangThai) {
        try {
            int result = XJdbc.executeUpdate(UPDATE_TRANGTHAI_SQL, trangThai, maMay);
            return result > 0;
        } catch (Exception ex) {
            System.err.println("Lỗi updateTrangThai MayTinh: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public int countByLoai(String loaiMay) {
        try {
            Integer count = XJdbc.getValue(COUNT_BY_LOAI_SQL, loaiMay);
            return count != null ? count : 0;
        } catch (Exception ex) {
            System.err.println("Lỗi countByLoai MayTinh: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public int countByTrangThai(String trangThai) {
        try {
            Integer count = XJdbc.getValue(COUNT_BY_TRANGTHAI_SQL, trangThai);
            return count != null ? count : 0;
        } catch (Exception ex) {
            System.err.println("Lỗi countByTrangThai MayTinh: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * Map ResultSet thành đối tượng MayTinh
     */
    private MayTinh mapResultSetToEntity(ResultSet rs) throws SQLException {
        MayTinh entity = new MayTinh();
        entity.setMaMay(rs.getString("MaMay"));
        entity.setTenMay(rs.getString("TenMay"));
        entity.setLoaiMay(rs.getString("LoaiMay"));
        entity.setGiaTheoGio(rs.getBigDecimal("GiaTheoGio"));
        entity.setTrangThai(rs.getString("TrangThai"));
        return entity;
    }

    @Override
    public List<MayTinh> getMayByDateRange(Date tuNgay, Date denNgay) {
        return selectAll();
    }
}
