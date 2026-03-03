/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ql.net.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ql.net.dao.ThanhVienVipDao;
import ql.net.entity.KhachHang;
import ql.net.entity.ThanhVienVIP;
import ql.net.util.XJdbc;

/**
 *
 * @author ADMIN
 */
public class ThanhVienVipDaoImpl implements ThanhVienVipDao{
    private final String SELECT_ALL_SQL = "SELECT * FROM ThanhVienVIP";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM ThanhVienVIP WHERE MaVIP=?";
    private final String INSERT_SQL = "INSERT INTO ThanhVienVIP (MaVIP, MaKH, LoaiVIP, TongChiTieu, NgayThamGia, NgayHetHan, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE ThanhVienVIP SET MaKH=?, LoaiVIP=?, TongChiTieu=?, NgayThamGia=?, NgayHetHan=?, TrangThai=? WHERE MaVIP=?";
    private final String DELETE_SQL = "DELETE FROM ThanhVienVIP WHERE MaVIP=?";

    @Override
    public List<ThanhVienVIP> selectAll() {
        List<ThanhVienVIP> list = new ArrayList<>();
        try (Connection con = XJdbc.openConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ThanhVienVIP vip = new ThanhVienVIP();
                vip.setMaVIP(rs.getString("MaVIP"));
                vip.setMaKH(rs.getString("MaKH"));
                vip.setLoaiVIP(rs.getString("LoaiVIP"));
                vip.setTongChiTieu(rs.getBigDecimal("TongChiTieu"));
                vip.setNgayThamGia(rs.getDate("NgayThamGia").toLocalDate());
                vip.setNgayHetHan(rs.getDate("NgayHetHan").toLocalDate());
                vip.setTrangThai(rs.getBoolean("TrangThai"));
                list.add(vip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ThanhVienVIP selectById(String maVIP) {
        try (Connection con = XJdbc.openConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID_SQL)) {
            ps.setString(1, maVIP);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ThanhVienVIP vip = new ThanhVienVIP();
                    vip.setMaVIP(rs.getString("MaVIP"));
                    vip.setMaKH(rs.getString("MaKH"));
                    vip.setLoaiVIP(rs.getString("LoaiVIP"));
                    vip.setTongChiTieu(rs.getBigDecimal("TongChiTieu"));
                    vip.setNgayThamGia(rs.getDate("NgayThamGia").toLocalDate());
                    vip.setNgayHetHan(rs.getDate("NgayHetHan").toLocalDate());
                    vip.setTrangThai(rs.getBoolean("TrangThai"));
                    return vip;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(ThanhVienVIP vip) {
        try {
            int result = XJdbc.executeUpdate(INSERT_SQL,
                vip.getMaVIP(),
                vip.getMaKH(),
                vip.getLoaiVIP(),
                vip.getTongChiTieu(),
                java.sql.Date.valueOf(vip.getNgayThamGia()),
                java.sql.Date.valueOf(vip.getNgayHetHan()),
                vip.isTrangThai()
            );
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void update(ThanhVienVIP vip) {
        try (Connection con = XJdbc.openConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_SQL)) {
            ps.setString(1, vip.getMaKH());
            ps.setString(2, vip.getLoaiVIP());
            ps.setBigDecimal(3, vip.getTongChiTieu());
            ps.setDate(4, java.sql.Date.valueOf(vip.getNgayThamGia())); // đúng vị trí 4
            ps.setDate(5, java.sql.Date.valueOf(vip.getNgayHetHan()));  // đúng vị trí 5
            ps.setBoolean(6, vip.isTrangThai());                        // đúng vị trí 6
            ps.setString(7, vip.getMaVIP());                            // đúng vị trí 7
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String maVIP) {
        try (Connection con = XJdbc.openConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_SQL)) {
            ps.setString(1, maVIP);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ThanhVienVIP> getVIPByDateRange(Date tuNgay, Date denNgay) {
        List<ThanhVienVIP> result = new ArrayList<>();
    
        try (Connection conn = XJdbc.getConnection()) {
            if (conn == null) {
                System.err.println("Không thể kết nối database");
                return result;
            }

            // Sửa SQL để lấy tất cả dữ liệu VIP nếu nằm trong khoảng ngày hoặc không lọc ngày
            String sql = "SELECT tv.*, k.HoTen FROM ThanhVienVIP tv " +
                         "JOIN KhachHang k ON tv.MaKH = k.MaKH " +
                         "WHERE (tv.NgayThamGia BETWEEN ? AND ?) OR (? IS NULL AND ? IS NULL)";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDate(1, new java.sql.Date(tuNgay.getTime()));
                stmt.setDate(2, new java.sql.Date(denNgay.getTime()));
                stmt.setDate(3, null);
                stmt.setDate(4, null);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        KhachHang khachHang = KhachHang.builder()
                            .maKH(rs.getString("MaKH"))
                            .hoTen(rs.getString("HoTen"))
                            .build();

                        ThanhVienVIP vip = ThanhVienVIP.builder()
                            .maVIP(rs.getString("MaVIP"))
                            .maKH(rs.getString("MaKH"))
                            .ngayThamGia(rs.getDate("NgayThamGia").toLocalDate())
                            .ngayHetHan(rs.getDate("NgayHetHan").toLocalDate())
                            .loaiVIP(rs.getString("LoaiVIP"))
                            .khachHang(khachHang)
                            .build();

                        result.add(vip);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Tìm thấy " + result.size() + " thành viên VIP");
        return result;
    }

}
