/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ql.net.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ql.net.dao.HoaDonDao;
import ql.net.entity.HoaDon;
import ql.net.entity.KhachHang;
import ql.net.util.XJdbc;

/**
 *
 * @author ADMIN
 */
public class HoaDonDaoImpl implements HoaDonDao{
    private final String SELECT_ALL_SQL = "SELECT * FROM HoaDon";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM HoaDon WHERE MaHD = ?";
    private final String SELECT_BY_MAKH_SQL = "SELECT * FROM HoaDon WHERE MaKH = ?";
    private final String INSERT_SQL = "INSERT INTO HoaDon (MaHD, MaPhien, MaNV, MaKH, TongTienMay, TongTienDichVu, TongTien, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE HoaDon SET MaPhien=?, MaNV=?, MaKH=?, TongTienMay=?, TongTienDichVu=?, TongTien=?, TrangThai=? WHERE MaHD=?";
    private final String DELETE_SQL = "DELETE FROM HoaDon WHERE MaHD=?";

    private HoaDon mapResultSetToEntity(ResultSet rs) throws SQLException {
        return HoaDon.builder()
            .maHD(rs.getString("MaHD"))
            .maPhien(rs.getString("MaPhien"))
            .maNV(rs.getString("MaNV"))
            .maKH(rs.getString("MaKH"))
            .ngayTao(rs.getTimestamp("NgayTao").toLocalDateTime())
            .tongTienMay(rs.getBigDecimal("TongTienMay"))
            .tongTienDichVu(rs.getBigDecimal("TongTienDichVu"))
            .tongTien(rs.getBigDecimal("TongTien"))
            .trangThai(rs.getString("TrangThai"))
            .build();
    }

    @Override
    public List<HoaDon> selectAll() {
        List<HoaDon> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(SELECT_ALL_SQL)) {
            while (rs.next()) {
                list.add(mapResultSetToEntity(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public HoaDon selectById(String maHD) {
        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_ID_SQL, maHD)) {
            if (rs.next()) {
                return mapResultSetToEntity(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<HoaDon> selectByMaKH(String maKH) {
        List<HoaDon> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_MAKH_SQL, maKH)) {
            while (rs.next()) {
                list.add(mapResultSetToEntity(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean insert(HoaDon hd) {
        try {
            int result = XJdbc.executeUpdate(INSERT_SQL,
                hd.getMaHD(),
                hd.getMaPhien(),
                hd.getMaNV(),
                hd.getMaKH(),
                hd.getTongTienMay(),
                hd.getTongTienDichVu(),
                hd.getTongTien(),
                hd.getTrangThai()
            );
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(HoaDon hd) {
        try {
            int result = XJdbc.executeUpdate(UPDATE_SQL,
                hd.getMaPhien(),
                hd.getMaNV(),
                hd.getMaKH(),
                hd.getTongTienMay(),
                hd.getTongTienDichVu(),
                hd.getTongTien(),
                hd.getTrangThai(),
                hd.getMaHD()
            );
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String maHD) {
        try {
            int result = XJdbc.executeUpdate(DELETE_SQL, maHD);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<HoaDon> selectByTrangThai(String trangThai) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean updateTrangThai(String maHD, String trangThai) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<HoaDon> selectByDateRange(LocalDateTime tuNgay, LocalDateTime denNgay) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<HoaDon> selectByNhanVien(String maNV) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public int getTongHoaDonByMaMay(String maMay) {
        String sql = "SELECT COUNT(*) FROM HoaDon hd " +
                     "JOIN PhienSuDung ps ON hd.MaPhien = ps.MaPhien " +
                     "WHERE ps.MaMay = ?";
        try {
            Integer count = XJdbc.getValue(sql, maMay);
            return count != null ? count : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<HoaDon> getHoaDonByDateRange(Date tuNgay, Date denNgay) {
        List<HoaDon> result = new ArrayList<>();
        // Sửa NgayTao thành NgayLap (tên đúng trong DB)
        String sql = "SELECT h.*, k.HoTen FROM HoaDon h " +
                     "LEFT JOIN KhachHang k ON h.MaKH = k.MaKH " +
                     "WHERE h.NgayLap BETWEEN ? AND ?";

        try (Connection conn = XJdbc.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(tuNgay.getTime()));
            stmt.setDate(2, new java.sql.Date(denNgay.getTime()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Tạo đối tượng KhachHang từ join
                KhachHang khachHang = KhachHang.builder()
                    .maKH(rs.getString("MaKH"))
                    .hoTen(rs.getString("HoTen"))
                    .build();

                // Tạo đối tượng HoaDon và set khachHang vào
                HoaDon hoaDon = HoaDon.builder()
                    .maHD(rs.getString("MaHD"))
                    .maKH(rs.getString("MaKH"))
                    .tongTien(rs.getBigDecimal("TongTien"))
                    .ngayTao(rs.getTimestamp("NgayLap").toLocalDateTime()) // Sửa NgayTao thành NgayLap
                    .khachHang(khachHang) // Set đối tượng KhachHang
                    .build();

                result.add(hoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<HoaDon> getDoanhThuByDateRange(Date tuNgay, Date denNgay) {
        List<HoaDon> result = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE NgayTao BETWEEN ? AND ?";
        try (Connection conn = XJdbc.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(tuNgay.getTime()));
            stmt.setDate(2, new java.sql.Date(denNgay.getTime()));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    HoaDon hd = HoaDon.builder()
                        .maHD(rs.getString("MaHD"))
                        .ngayTao(rs.getTimestamp("NgayTao").toLocalDateTime())
                        .tongTienMay(rs.getBigDecimal("TongTienMay"))
                        .tongTienDichVu(rs.getBigDecimal("TongTienDichVu"))
                        .tongTien(rs.getBigDecimal("TongTien"))
                        .build();
                    result.add(hd);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
