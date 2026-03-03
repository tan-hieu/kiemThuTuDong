/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ql.net.dao.impl;

import ql.net.dao.LichSuGiaoDichDao;
import ql.net.entity.LichSuGiaoDich;
import ql.net.util.XJdbc;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import ql.net.entity.KhachHang;

/**
 *
 * @author ADMIN
 */
public class LichSuGiaoDichDaoImpl implements LichSuGiaoDichDao{
    private final String SELECT_ALL_SQL = "SELECT * FROM LichSuGiaoDich";
    private final String SELECT_BY_MAKH_SQL = "SELECT * FROM LichSuGiaoDich WHERE MaKH = ?";
    private final String INSERT_SQL = "INSERT INTO LichSuGiaoDich (MaGiaoDich, MaKH, LoaiGiaoDich, SoTien, NgayGiaoDich) VALUES (?, ?, ?, ?, ?)";
    private final String DELETE_SQL = "DELETE FROM LichSuGiaoDich WHERE MaGiaoDich=?";

    private LichSuGiaoDich mapResultSetToEntity(ResultSet rs) throws SQLException {
        return LichSuGiaoDich.builder()
            .maGiaoDich(rs.getString("MaGiaoDich"))
            .maKH(rs.getString("MaKH"))
            .loaiGiaoDich(rs.getString("LoaiGiaoDich"))
            .soTien(rs.getBigDecimal("SoTien"))
            .ngayGiaoDich(rs.getTimestamp("NgayGiaoDich").toLocalDateTime())
            .build();
    }

    @Override
    public List<LichSuGiaoDich> selectAll() {
        List<LichSuGiaoDich> list = new ArrayList<>();
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
    public List<LichSuGiaoDich> selectByMaKH(String maKH) {
        List<LichSuGiaoDich> list = new ArrayList<>();
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
    public boolean insert(LichSuGiaoDich gd) {
        try {
            int result = XJdbc.executeUpdate(INSERT_SQL,
                gd.getMaGiaoDich(),
                gd.getMaKH(),
                gd.getLoaiGiaoDich(),
                gd.getSoTien(),
                Timestamp.valueOf(gd.getNgayGiaoDich())
            );
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String maGiaoDich) {
        try {
            int result = XJdbc.executeUpdate(DELETE_SQL, maGiaoDich);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public List<LichSuGiaoDich> getGiaoDichByDateRange(java.util.Date tuNgay, java.util.Date denNgay) {
        List<LichSuGiaoDich> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String tuNgayStr = sdf.format(tuNgay) + " 00:00:00";
        String denNgayStr = sdf.format(denNgay) + " 23:59:59";
        String sql = "SELECT l.*, k.HoTen FROM LichSuGiaoDich l " +
                     "LEFT JOIN KhachHang k ON l.MaKH = k.MaKH " +
                     "WHERE l.NgayGiaoDich BETWEEN ? AND ?";
        try (Connection conn = XJdbc.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tuNgayStr);
            stmt.setString(2, denNgayStr);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                KhachHang kh = KhachHang.builder()
                        .maKH(rs.getString("MaKH"))
                        .hoTen(rs.getString("HoTen"))
                        .build();
                LichSuGiaoDich gd = LichSuGiaoDich.builder()
                        .maGiaoDich(rs.getString("MaGiaoDich"))
                        .maKH(rs.getString("MaKH"))
                        .loaiGiaoDich(rs.getString("LoaiGiaoDich"))
                        .soTien(rs.getBigDecimal("SoTien"))
                        .ngayGiaoDich(rs.getTimestamp("NgayGiaoDich").toLocalDateTime())
                        .build();
                result.add(gd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
