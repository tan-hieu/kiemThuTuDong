/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ql.net.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import ql.net.dao.NhanVienDao;
import ql.net.entity.NhanVien;
import ql.net.util.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class NhanVienDaoImpl implements NhanVienDao{
    private final String SELECT_ALL_SQL = 
        "SELECT MaNV, TenDangNhap, MatKhau ,HoTen, SoDT, ChucVu, TrangThai, NgayTao, Image FROM NhanVien ORDER BY MaNV";
    
    private final String SELECT_BY_ID_SQL = 
        "SELECT MaNV, TenDangNhap, MatKhau, HoTen, SoDT, ChucVu, TrangThai, NgayTao, Image FROM NhanVien WHERE MaNV = ?";
    
    private final String LOGIN_SQL = 
        "SELECT MaNV, TenDangNhap, MatKhau, HoTen, SoDT, ChucVu, TrangThai, NgayTao, Image " +
        "FROM NhanVien WHERE TenDangNhap = ? AND MatKhau = ? AND TrangThai = 1";
    
    private final String INSERT_SQL = 
        "INSERT INTO NhanVien (MaNV, TenDangNhap, MatKhau, HoTen, SoDT, ChucVu, Image) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    private final String UPDATE_SQL = 
        "UPDATE NhanVien SET TenDangNhap = ?, HoTen = ?, SoDT = ?, ChucVu = ?, Image = ? WHERE MaNV = ?";
    
    private final String DELETE_SQL = 
        "DELETE FROM NhanVien WHERE MaNV = ?";
    
    private final String CHANGE_PASSWORD_SQL = 
        "UPDATE NhanVien SET MatKhau = ? WHERE MaNV = ? AND MatKhau = ?";
    
    private final String SELECT_BY_CHUCVU_SQL = 
        "SELECT MaNV, TenDangNhap, HoTen, SoDT, ChucVu, TrangThai, NgayTao, Image " +
        "FROM NhanVien WHERE ChucVu = ? AND TrangThai = 1 ORDER BY MaNV";
    
    @Override
    public List<NhanVien> selectAll() {
        List<NhanVien> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(SELECT_ALL_SQL)) {
            while (rs.next()) {
                NhanVien entity = createEntityFromResultSet(rs, false);
                list.add(entity);
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi selectAll NhanVien: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return list;
    }
    
    @Override
    public NhanVien selectById(String maNV) {
        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_ID_SQL, maNV)) {
            if (rs.next()) {
                return createEntityFromResultSet(rs, true);
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi selectById NhanVien: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return null;
    }
    
    @Override
    public NhanVien login(String tenDangNhap, String matKhau) {
//        try (ResultSet rs = XJdbc.executeQuery(LOGIN_SQL, tenDangNhap, matKhau)) {
//            if (rs.next()) {
//                return createEntityFromResultSet(rs, true);
//            }
//        } catch (SQLException ex) {
//            System.err.println("Lỗi login NhanVien: " + ex.getMessage());
//            throw new RuntimeException(ex);
//        }
//        return null;
         String sql = "SELECT * FROM NhanVien WHERE tenDangNhap = ? AND matKhau = ? AND trangThai = 1";
        return ql.net.util.XQuery.getSingleBean(NhanVien.class, sql, tenDangNhap, matKhau);
    }
    
    @Override
    public boolean insert(NhanVien entity) {
        try {
            int result = XJdbc.executeUpdate(INSERT_SQL,
                entity.getMaNV(),
                entity.getTenDangNhap(),
                entity.getMatKhau(),
                entity.getHoTen(),
                entity.getSoDT(),
                entity.getChucVu(),
                entity.getImage()
            );
            return result > 0;
        } catch (Exception ex) {
            System.err.println("Lỗi insert NhanVien: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public boolean update(NhanVien entity) {
        try {
            int result = XJdbc.executeUpdate(UPDATE_SQL,
                entity.getTenDangNhap(),
                entity.getHoTen(),
                entity.getSoDT(),
                entity.getChucVu(),
                entity.getImage(),
                entity.getMaNV()
            );
            return result > 0;
        } catch (Exception ex) {
            System.err.println("Lỗi update NhanVien: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public boolean delete(String maNV) {
        try {
            System.out.println("Xóa nhân viên với mã: " + maNV); // Debug
            int result = XJdbc.executeUpdate(DELETE_SQL, maNV);
            System.out.println("Kết quả xóa: " + result + " dòng bị ảnh hưởng"); // Debug
            return result > 0;
        } catch (Exception ex) {
            System.err.println("Lỗi delete NhanVien: " + ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public boolean changePassword(String maNV, String matKhauCu, String matKhauMoi) {
        try {
            int result = XJdbc.executeUpdate(CHANGE_PASSWORD_SQL, matKhauMoi, maNV, matKhauCu);
            return result > 0;
        } catch (Exception ex) {
            System.err.println("Lỗi changePassword NhanVien: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public List<NhanVien> selectByChucVu(String chucVu) {
        List<NhanVien> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_CHUCVU_SQL, chucVu)) {
            while (rs.next()) {
                NhanVien entity = createEntityFromResultSet(rs, false);
                list.add(entity);
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi selectByChucVu NhanVien: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return list;
    }
    
    /**
     * Tạo entity từ ResultSet với Lombok Builder
     */
    private NhanVien createEntityFromResultSet(ResultSet rs, boolean includePassword) throws SQLException {
        NhanVien.NhanVienBuilder builder = NhanVien.builder()
            .maNV(rs.getString("MaNV"))
            .tenDangNhap(rs.getString("TenDangNhap"))
            .matKhau(rs.getString("MatKhau"))
            .hoTen(rs.getString("HoTen"))
            .soDT(rs.getString("SoDT"))
            .chucVu(rs.getString("ChucVu"))
            .trangThai(rs.getBoolean("TrangThai"))
            .image(rs.getString("Image"));
        
        if (includePassword) {
            builder.matKhau(rs.getString("MatKhau"));
        }
        
        Timestamp timestamp = rs.getTimestamp("NgayTao");
        if (timestamp != null) {
            builder.ngayTao(timestamp.toLocalDateTime());
        }
        
        return builder.build();
    }

    @Override
    public List<NhanVien> getNhanVienByDateRange(Date tuNgay, Date denNgay) {
        List<NhanVien> result = new ArrayList<>();
        String sql = "SELECT MaNV, HoTen, SoDT, ChucVu FROM NhanVien WHERE NgayTao BETWEEN ? AND ?";
        try (Connection conn = XJdbc.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(tuNgay.getTime()));
            stmt.setDate(2, new java.sql.Date(denNgay.getTime()));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    NhanVien nv = NhanVien.builder()
                        .maNV(rs.getString("MaNV"))
                        .hoTen(rs.getString("HoTen"))
                        .soDT(rs.getString("SoDT"))
                        .chucVu(rs.getString("ChucVu"))
                        .build();
                    result.add(nv);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
