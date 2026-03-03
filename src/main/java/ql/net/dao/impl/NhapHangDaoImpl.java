/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ql.net.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ql.net.dao.NhapHangDao;
import ql.net.entity.NhapHang;
import ql.net.util.XJdbc;

/**
 *
 * @author ADMIN
 */
public class NhapHangDaoImpl implements NhapHangDao{
    private final String SELECT_ALL_SQL = "SELECT * FROM NhapHang ORDER BY NgayNhap DESC";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM NhapHang WHERE MaNhapHang = ?";
    private final String INSERT_SQL = "INSERT INTO NhapHang (MaNhapHang, MaNV, MaDV, TenNhaCungCap, SoLuong, DonGiaNhap, ThanhTien) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE NhapHang SET MaNV=?, MaDV=?, TenNhaCungCap=?, SoLuong=?, DonGiaNhap=?, ThanhTien=? WHERE MaNhapHang=?";
    private final String DELETE_SQL = "DELETE FROM NhapHang WHERE MaNhapHang=?";
    private final String SELECT_BY_MANV_SQL = "SELECT * FROM NhapHang WHERE MaNV = ?";
    private final String SELECT_BY_MADV_SQL = "SELECT * FROM NhapHang WHERE MaDV = ?";

    private NhapHang mapResultSetToEntity(ResultSet rs) throws SQLException {
        return NhapHang.builder()
                .maNhapHang(rs.getString("MaNhapHang"))
                .maNV(rs.getString("MaNV"))
                .maDV(rs.getString("MaDV"))
                .ngayNhap(rs.getTimestamp("NgayNhap").toLocalDateTime())
                .tenNhaCungCap(rs.getString("TenNhaCungCap"))
                .soLuong(rs.getInt("SoLuong"))
                .donGiaNhap(rs.getBigDecimal("DonGiaNhap"))
                .thanhTien(rs.getBigDecimal("ThanhTien"))
                .build();
    }

    @Override
    public List<NhapHang> selectAll() {
        List<NhapHang> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(SELECT_ALL_SQL)) {
            while (rs.next()) {
                NhapHang nh = NhapHang.builder()
                    .maNhapHang(rs.getString("MaNhapHang"))
                    .maNV(rs.getString("MaNV"))
                    .maDV(rs.getString("MaDV"))
                    .ngayNhap(rs.getTimestamp("NgayNhap").toLocalDateTime())
                    .tenNhaCungCap(rs.getString("TenNhaCungCap"))
                    .soLuong(rs.getInt("SoLuong"))
                    .donGiaNhap(rs.getBigDecimal("DonGiaNhap"))
                    .thanhTien(rs.getBigDecimal("ThanhTien"))
                    .build();
                list.add(nh);
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi"+ex);
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public NhapHang selectById(String maNhapHang) {
        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_ID_SQL, maNhapHang)) {
            if (rs.next()) {
                return mapResultSetToEntity(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(NhapHang entity) {
        int result = XJdbc.executeUpdate(INSERT_SQL,
                entity.getMaNhapHang(),
                entity.getMaNV(),
                entity.getMaDV(),
                entity.getTenNhaCungCap(),
                entity.getSoLuong(),
                entity.getDonGiaNhap(),
                entity.getThanhTien()
        );
        return result > 0;
    }

    @Override
    public boolean update(NhapHang entity) {
        int result = XJdbc.executeUpdate(UPDATE_SQL,
                entity.getMaNV(),
                entity.getMaDV(),
                entity.getTenNhaCungCap(),
                entity.getSoLuong(),
                entity.getDonGiaNhap(),
                entity.getThanhTien(),
                entity.getMaNhapHang()
        );
        return result > 0;
    }

    @Override
    public boolean delete(String maNhapHang) {
        int result = XJdbc.executeUpdate(DELETE_SQL, maNhapHang);
        return result > 0;
    }

//    public List<NhapHang> selectByMaNV(String maNV) {
//        List<NhapHang> list = new ArrayList<>();
//        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_MANV_SQL, maNV)) {
//            while (rs.next()) {
//                list.add(mapResultSetToEntity(rs));
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return list;
//    }
//
//    public List<NhapHang> selectByMaDV(String maDV) {
//        List<NhapHang> list = new ArrayList<>();
//        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_MADV_SQL, maDV)) {
//            while (rs.next()) {
//                list.add(mapResultSetToEntity(rs));
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return list;
//    }
}
