package ql.net.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ql.net.dao.NapTienDao;
import ql.net.entity.NapTien;
import ql.net.util.XJdbc;


public class NapTienDaoImpl implements NapTienDao {
    
    public List<NapTien> getAll() {
        List<NapTien> list = new ArrayList<>();
        String sql = "SELECT * FROM NapTien";
        try (Connection conn = XJdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                NapTien nt = NapTien.builder()
                        .maNapTien(rs.getString("MaNapTien"))
                        .maKH(rs.getString("MaKH"))
                        .maNV(rs.getString("MaNV"))
                        .soTienNap(rs.getBigDecimal("SoTienNap"))
                        .ngayNap(rs.getTimestamp("NgayNap").toLocalDateTime())
                        .hinhThucNap(rs.getString("HinhThucNap"))
                        .build();
                list.add(nt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public NapTien getById(String maNapTien) {
        String sql = "SELECT * FROM NapTien WHERE MaNapTien = ?";
        try (Connection conn = XJdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNapTien);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return NapTien.builder()
                            .maNapTien(rs.getString("MaNapTien"))
                            .maKH(rs.getString("MaKH"))
                            .maNV(rs.getString("MaNV"))
                            .soTienNap(rs.getBigDecimal("SoTienNap"))
                            .ngayNap(rs.getTimestamp("NgayNap").toLocalDateTime())
                            .hinhThucNap(rs.getString("HinhThucNap"))
                            .build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(NapTien napTien) {
        String sql = "INSERT INTO NapTien (MaNapTien, MaKH, MaNV, SoTienNap, NgayNap, HinhThucNap) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = XJdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, napTien.getMaNapTien());
            ps.setString(2, napTien.getMaKH());
            ps.setString(3, napTien.getMaNV());
            ps.setBigDecimal(4, napTien.getSoTienNap());
            ps.setTimestamp(5, Timestamp.valueOf(napTien.getNgayNap()));
            ps.setString(6, napTien.getHinhThucNap());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(NapTien napTien) {
        String sql = "UPDATE NapTien SET MaKH=?, MaNV=?, SoTienNap=?, NgayNap=?, HinhThucNap=? WHERE MaNapTien=?";
        try (Connection conn = XJdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, napTien.getMaKH());
            ps.setString(2, napTien.getMaNV());
            ps.setBigDecimal(3, napTien.getSoTienNap());
            ps.setTimestamp(4, Timestamp.valueOf(napTien.getNgayNap()));
            ps.setString(5, napTien.getHinhThucNap());
            ps.setString(6, napTien.getMaNapTien());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String maNapTien) {
        String sql = "DELETE FROM NapTien WHERE MaNapTien=?";
        try (Connection conn = XJdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNapTien);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<NapTien> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public NapTien selectById(String maNapTien) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<NapTien> selectByMaKH(String maKH) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<NapTien> selectByMaNV(String maNV) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<NapTien> selectByDateRange(LocalDateTime tuNgay, LocalDateTime denNgay) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<NapTien> selectByHinhThuc(String hinhThucNap) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<NapTien> getNapTienByDateRange(Date tuNgay, Date denNgay) {
        List<NapTien> list = new ArrayList<>();
        String sql = "SELECT * FROM NapTien WHERE NgayNap BETWEEN ? AND ?";
        try (Connection conn = XJdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, new java.sql.Timestamp(tuNgay.getTime()));
            ps.setTimestamp(2, new java.sql.Timestamp(denNgay.getTime()));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    NapTien nt = NapTien.builder()
                            .maNapTien(rs.getString("MaNapTien"))
                            .maKH(rs.getString("MaKH"))
                            .maNV(rs.getString("MaNV"))
                            .soTienNap(rs.getBigDecimal("SoTienNap"))
                            .ngayNap(rs.getTimestamp("NgayNap").toLocalDateTime())
                            .hinhThucNap(rs.getString("HinhThucNap"))
                            .build();
                    list.add(nt);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}