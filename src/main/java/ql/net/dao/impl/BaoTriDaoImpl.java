package ql.net.dao.impl;

import ql.net.dao.BaoTriDao;
import ql.net.entity.BaoTri;
import ql.net.util.XJdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import ql.net.entity.NhanVien;


public class BaoTriDaoImpl implements BaoTriDao {
    @Override
    public List<BaoTri> getAll() {
        List<BaoTri> list = new ArrayList<>();
        String sql = "SELECT * FROM BaoTri";
        try (Connection conn = XJdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                BaoTri bt = BaoTri.builder()
                        .maBaoTri(rs.getString("MaBaoTri"))
                        .maMay(rs.getString("MaMay"))
                        .maNV(rs.getString("MaNV"))
                        .ngayBaoTri(rs.getDate("NgayBaoTri").toLocalDate())
                        .moTa(rs.getString("MoTa"))
                        .chiPhiSua(rs.getBigDecimal("ChiPhiSua"))
                        .trangThai(rs.getString("TrangThai"))
                        .ngaySua(rs.getDate("NgaySua") != null ? rs.getDate("NgaySua").toLocalDate() : null)
                        .build();
                list.add(bt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public BaoTri getById(String maBaoTri) {
        String sql = "SELECT * FROM BaoTri WHERE MaBaoTri = ?";
        try (Connection conn = XJdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maBaoTri);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return BaoTri.builder()
                            .maBaoTri(rs.getString("MaBaoTri"))
                            .maMay(rs.getString("MaMay"))
                            .maNV(rs.getString("MaNV"))
                            .ngayBaoTri(rs.getDate("NgayBaoTri").toLocalDate())
                            .moTa(rs.getString("MoTa"))
                            .chiPhiSua(rs.getBigDecimal("ChiPhiSua"))
                            .trangThai(rs.getString("TrangThai"))
                            .ngaySua(rs.getDate("NgaySua") != null ? rs.getDate("NgaySua").toLocalDate() : null)
                            .build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(BaoTri baoTri) {
        String sql = "INSERT INTO BaoTri (MaBaoTri, MaMay, MaNV, NgayBaoTri, MoTa, ChiPhiSua, TrangThai, NgaySua) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = XJdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, baoTri.getMaBaoTri());
            ps.setString(2, baoTri.getMaMay());
            ps.setString(3, baoTri.getMaNV());
            ps.setDate(4, Date.valueOf(baoTri.getNgayBaoTri()));
            ps.setString(5, baoTri.getMoTa());
            ps.setBigDecimal(6, baoTri.getChiPhiSua());
            ps.setString(7, baoTri.getTrangThai());
            if (baoTri.getNgaySua() != null) {
                ps.setDate(8, Date.valueOf(baoTri.getNgaySua()));
            } else {
                ps.setNull(8, Types.DATE);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(BaoTri baoTri) {
        String sql = "UPDATE BaoTri SET MaMay=?, MaNV=?, NgayBaoTri=?, MoTa=?, ChiPhiSua=?, TrangThai=?, NgaySua=? WHERE MaBaoTri=?";
        try (Connection conn = XJdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, baoTri.getMaMay());
            ps.setString(2, baoTri.getMaNV());
            ps.setDate(3, Date.valueOf(baoTri.getNgayBaoTri()));
            ps.setString(4, baoTri.getMoTa());
            ps.setBigDecimal(5, baoTri.getChiPhiSua());
            ps.setString(6, baoTri.getTrangThai());
            if (baoTri.getNgaySua() != null) {
                ps.setDate(7, Date.valueOf(baoTri.getNgaySua()));
            } else {
                ps.setNull(7, Types.DATE);
            }
            ps.setString(8, baoTri.getMaBaoTri());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String maBaoTri) {
        String sql = "DELETE FROM BaoTri WHERE MaBaoTri=?";
        try (Connection conn = XJdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maBaoTri);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<BaoTri> getBaoTriByDateRange(java.util.Date tuNgay, java.util.Date denNgay) {
        List<BaoTri> result = new ArrayList<>();
        String sql = "SELECT b.*, n.HoTen FROM BaoTri b " +
                     "LEFT JOIN NhanVien n ON b.MaNV = n.MaNV " +
                     "WHERE b.NgayBaoTri BETWEEN ? AND ?";

        try (Connection conn = XJdbc.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(tuNgay.getTime()));
            stmt.setDate(2, new java.sql.Date(denNgay.getTime()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Tạo đối tượng NhanVien từ join
                NhanVien nhanVien = NhanVien.builder()
                    .maNV(rs.getString("MaNV"))
                    .hoTen(rs.getString("HoTen"))
                    .build();

                // Tạo đối tượng BaoTri đầy đủ các trường
                BaoTri baoTri = BaoTri.builder()
                    .maBaoTri(rs.getString("MaBaoTri"))
                    .maMay(rs.getString("MaMay"))
                    .maNV(rs.getString("MaNV"))
                    .ngayBaoTri(rs.getDate("NgayBaoTri").toLocalDate())
                    .moTa(rs.getString("MoTa"))
                    .chiPhiSua(rs.getBigDecimal("ChiPhiSua"))
                    .trangThai(rs.getString("TrangThai"))
                    .ngaySua(rs.getDate("NgaySua") != null ? rs.getDate("NgaySua").toLocalDate() : null)
                    .nhanVien(nhanVien) // Set đối tượng NhanVien
                    .build();

                result.add(baoTri);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}