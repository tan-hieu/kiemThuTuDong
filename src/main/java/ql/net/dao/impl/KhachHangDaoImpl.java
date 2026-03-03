/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ql.net.dao.impl;

import ql.net.dao.KhachHangDao;
import ql.net.entity.KhachHang;
import ql.net.util.XJdbc;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ADMIN
 */
public class KhachHangDaoImpl implements KhachHangDao{
    private final String SELECT_ALL_SQL = 
        "SELECT MaKH, HoTen, SoDT, SoDuTaiKhoan, TrangThai, Image FROM KhachHang ORDER BY MaKH";
    
    private final String SELECT_BY_ID_SQL = 
        "SELECT MaKH, HoTen, SoDT, SoDuTaiKhoan, TrangThai, Image FROM KhachHang WHERE MaKH = ?";
    
    private final String SELECT_BY_TRANGTHAI_SQL = 
        "SELECT MaKH, HoTen, SoDT, SoDuTaiKhoan, TrangThai, Image FROM KhachHang WHERE TrangThai = ? ORDER BY MaKH";
    
    private final String INSERT_SQL = 
        "INSERT INTO KhachHang (MaKH, HoTen, SoDT, SoDuTaiKhoan, Image) VALUES (?, ?, ?, ?, ?)";
    
    private final String UPDATE_SQL = 
        "UPDATE KhachHang SET HoTen = ?, SoDT = ?, Image = ? WHERE MaKH = ?";
    
    private final String DELETE_SQL = 
        "DELETE FROM KhachHang WHERE MaKH=?";
    
    private final String UPDATE_SODU_SQL = 
        "UPDATE KhachHang SET SoDuTaiKhoan = SoDuTaiKhoan + ? WHERE MaKH = ?";
    
    private final String SELECT_BY_KEYWORD_SQL = 
        "SELECT MaKH, HoTen, SoDT, SoDuTaiKhoan, TrangThai, Image FROM KhachHang " +
        "WHERE (HoTen LIKE ? OR SoDT LIKE ? OR MaKH LIKE ?) AND TrangThai = 1 ORDER BY MaKH";
    
    private final String COUNT_SQL = 
        "SELECT COUNT(*) FROM KhachHang WHERE TrangThai = 1";
    
    @Override
    public List<KhachHang> selectAll() {
        List<KhachHang> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(SELECT_ALL_SQL)) {
            while (rs.next()) {
                KhachHang entity = mapResultSetToEntity(rs);
                list.add(entity);
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi selectAll KhachHang: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return list;
    }
    
    @Override
    public KhachHang selectById(String maKH) {
        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_ID_SQL, maKH)) {
            if (rs.next()) {
                return mapResultSetToEntity(rs);
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi selectById KhachHang: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return null;
    }
    
    @Override
    public List<KhachHang> selectByTrangThai(boolean trangThai) {
        List<KhachHang> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_TRANGTHAI_SQL, trangThai)) {
            while (rs.next()) {
                KhachHang entity = mapResultSetToEntity(rs);
                list.add(entity);
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi selectByTrangThai KhachHang: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return list;
    }
    
    @Override
    public boolean insert(KhachHang entity) {
        try {
            int result = XJdbc.executeUpdate(INSERT_SQL,
                entity.getMaKH(),
                entity.getHoTen(),
                entity.getSoDT(),
                entity.getSoDuTaiKhoan(),
                entity.getImage()
            );
            return result > 0;
        } catch (Exception ex) {
            System.err.println("Lỗi insert KhachHang: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public boolean update(KhachHang entity) {
        try {
            int result = XJdbc.executeUpdate(UPDATE_SQL,
                entity.getHoTen(),
                entity.getSoDT(),
                entity.getImage(),
                entity.getMaKH()
            );
            return result > 0;
        } catch (Exception ex) {
            System.err.println("Lỗi update KhachHang: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public boolean delete(String maKH) {
        try {
            int result = XJdbc.executeUpdate(DELETE_SQL, maKH);
            return result > 0;
        } catch (Exception ex) {
            System.err.println("Lỗi delete KhachHang: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public boolean updateSoDu(String maKH, BigDecimal soTien) {
        try {
            int result = XJdbc.executeUpdate(UPDATE_SODU_SQL, soTien, maKH);
            return result > 0;
        } catch (Exception ex) {
            System.err.println("Lỗi updateSoDu KhachHang: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
//    @Override
    public List<KhachHang> selectByKeyword(String keyword) {
        List<KhachHang> list = new ArrayList<>();
        String searchPattern = "%" + keyword + "%";
        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_KEYWORD_SQL, 
                searchPattern, searchPattern, searchPattern)) {
            while (rs.next()) {
                KhachHang entity = mapResultSetToEntity(rs);
                list.add(entity);
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi selectByKeyword KhachHang: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return list;
    }
    
//    @Override
    public int count() {
        try {
            Integer count = XJdbc.getValue(COUNT_SQL);
            return count != null ? count : 0;
        } catch (Exception ex) {
            System.err.println("Lỗi count KhachHang: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
    private KhachHang mapResultSetToEntity(ResultSet rs) throws SQLException {
        return KhachHang.builder()
            .maKH(rs.getString("MaKH"))
            .hoTen(rs.getString("HoTen"))
            .soDT(rs.getString("SoDT"))
            .soDuTaiKhoan(rs.getBigDecimal("SoDuTaiKhoan"))
            .trangThai(rs.getBoolean("TrangThai"))
            .image(rs.getString("Image"))
            .build();
    }

    @Override
    public List<KhachHang> searchBySDT(String soDT) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<KhachHang> searchByTen(String hoTen) {
        throw new UnsupportedOperationException("Not supported yet."); 
// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    @Override
    public void updateImage(String maKH, String fileAnh) {
        String sql = "UPDATE KhachHang SET image = ? WHERE maKH = ?";
        try (Connection con = XJdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, fileAnh);
            ps.setString(2, maKH);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
