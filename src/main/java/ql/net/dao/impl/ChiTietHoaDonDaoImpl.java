/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ql.net.dao.impl;

import ql.net.dao.ChiTietHoaDonDao;
import ql.net.entity.ChiTietHoaDon;
import ql.net.util.XJdbc;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ADMIN
 */
public class ChiTietHoaDonDaoImpl implements ChiTietHoaDonDao{
    private final String SELECT_ALL_SQL = "SELECT * FROM ChiTietHoaDon";
    private final String SELECT_BY_MAHD_SQL = "SELECT * FROM ChiTietHoaDon WHERE MaHD = ?";
    private final String INSERT_SQL = "INSERT INTO ChiTietHoaDon (MaHD, MaDV, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE ChiTietHoaDon SET SoLuong=?, DonGia=?, ThanhTien=? WHERE MaHD=? AND MaDV=?";
    private final String DELETE_SQL = "DELETE FROM ChiTietHoaDon WHERE MaHD=? AND MaDV=?";

    private ChiTietHoaDon mapResultSetToEntity(ResultSet rs) throws SQLException {
        return ChiTietHoaDon.builder()
            .maHD(rs.getString("MaHD"))
            .maDV(rs.getString("MaDV"))
            .soLuong(rs.getInt("SoLuong"))
            .donGia(rs.getBigDecimal("DonGia"))
            .thanhTien(rs.getBigDecimal("ThanhTien"))
            .build();
    }

    @Override
    public List<ChiTietHoaDon> selectAll() {
        List<ChiTietHoaDon> list = new ArrayList<>();
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
    public List<ChiTietHoaDon> selectByMaHD(String maHD) {
        List<ChiTietHoaDon> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_MAHD_SQL, maHD)) {
            while (rs.next()) {
                list.add(mapResultSetToEntity(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean insert(ChiTietHoaDon ct) {
        try {
            int result = XJdbc.executeUpdate(INSERT_SQL,
                ct.getMaHD(),
                ct.getMaDV(),
                ct.getSoLuong(),
                ct.getDonGia(),
                ct.getThanhTien()
            );
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(ChiTietHoaDon ct) {
        try {
            int result = XJdbc.executeUpdate(UPDATE_SQL,
                ct.getSoLuong(),
                ct.getDonGia(),
                ct.getThanhTien(),
                ct.getMaHD(),
                ct.getMaDV()
            );
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String maHD, String maDV) {
        try {
            int result = XJdbc.executeUpdate(DELETE_SQL, maHD, maDV);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<ChiTietHoaDon> getChiTietHoaDonByDateRange(java.util.Date tuNgay, java.util.Date denNgay) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
