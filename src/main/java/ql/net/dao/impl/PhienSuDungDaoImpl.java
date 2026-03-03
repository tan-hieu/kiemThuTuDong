/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ql.net.dao.impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import ql.net.dao.PhienSuDungDao;
import ql.net.entity.PhienSuDung;
import ql.net.util.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ql.net.entity.KhachHang;
import ql.net.entity.MayTinh;
/**
 *
 * @author ADMIN
 */
public class PhienSuDungDaoImpl implements PhienSuDungDao{
    // SQL Queries
    private final String SELECT_ALL_SQL = 
        "SELECT MaPhien, MaMay, MaKH, MaNV, ThoiGianBatDau, ThoiGianKetThuc, SoGioSuDung, TongTien, TrangThai " +
        "FROM PhienSuDung ORDER BY ThoiGianBatDau DESC";
    
    private final String SELECT_BY_ID_SQL = 
        "SELECT MaPhien, MaMay, MaKH, MaNV, ThoiGianBatDau, ThoiGianKetThuc, SoGioSuDung, TongTien, TrangThai " +
        "FROM PhienSuDung WHERE MaPhien = ?";
    
    private final String SELECT_BY_MAMAY_SQL = 
        "SELECT MaPhien, MaMay, MaKH, MaNV, ThoiGianBatDau, ThoiGianKetThuc, SoGioSuDung, TongTien, TrangThai " +
        "FROM PhienSuDung WHERE MaMay = ? ORDER BY ThoiGianBatDau DESC";
    
    private final String SELECT_BY_MAKH_SQL = 
        "SELECT MaPhien, MaMay, MaKH, MaNV, ThoiGianBatDau, ThoiGianKetThuc, SoGioSuDung, TongTien, TrangThai " +
        "FROM PhienSuDung WHERE MaKH = ? ORDER BY ThoiGianBatDau DESC";
    
    private final String SELECT_BY_TRANGTHAI_SQL = 
        "SELECT MaPhien, MaMay, MaKH, MaNV, ThoiGianBatDau, ThoiGianKetThuc, SoGioSuDung, TongTien, TrangThai " +
        "FROM PhienSuDung WHERE TrangThai = ? ORDER BY ThoiGianBatDau DESC";
    
    private final String INSERT_SQL = 
        "INSERT INTO PhienSuDung (MaPhien, MaMay, MaKH, MaNV, ThoiGianBatDau, ThoiGianKetThuc, SoGioSuDung, TongTien, TrangThai) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private final String UPDATE_SQL = 
        "UPDATE PhienSuDung SET MaMay = ?, MaKH = ?, MaNV = ?, ThoiGianBatDau = ?, ThoiGianKetThuc = ?, " +
        "SoGioSuDung = ?, TongTien = ?, TrangThai = ? WHERE MaPhien = ?";
    
    private final String DELETE_SQL = 
        "DELETE FROM PhienSuDung WHERE MaPhien = ?";
    
    private final String UPDATE_TRANGTHAI_SQL = 
        "UPDATE PhienSuDung SET TrangThai = ? WHERE MaPhien = ?";
    
    private final String SELECT_BY_DATERANGE_SQL = 
        "SELECT MaPhien, MaMay, MaKH, MaNV, ThoiGianBatDau, ThoiGianKetThuc, SoGioSuDung, TongTien, TrangThai " +
        "FROM PhienSuDung WHERE ThoiGianBatDau BETWEEN ? AND ? ORDER BY ThoiGianBatDau DESC";
    
    private final String SELECT_BY_MANV_SQL = 
        "SELECT MaPhien, MaMay, MaKH, MaNV, ThoiGianBatDau, ThoiGianKetThuc, SoGioSuDung, TongTien, TrangThai " +
        "FROM PhienSuDung WHERE MaNV = ? ORDER BY ThoiGianBatDau DESC";
    
    private final String SELECT_PHIEN_DANGSUDUNG_SQL = 
        "SELECT MaPhien, MaMay, MaKH, MaNV, ThoiGianBatDau, ThoiGianKetThuc, SoGioSuDung, TongTien, TrangThai " +
        "FROM PhienSuDung WHERE MaMay = ? AND TrangThai = N'Đang sử dụng'";
    
    private final String KETTHUC_PHIEN_SQL = 
        "UPDATE PhienSuDung SET ThoiGianKetThuc = ?, TrangThai = N'Đã kết thúc' WHERE MaPhien = ?";
    
    @Override
    public List<PhienSuDung> selectAll() {
        List<PhienSuDung> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(SELECT_ALL_SQL)) {
            while (rs.next()) {
                PhienSuDung entity = mapResultSetToEntity(rs);
                list.add(entity);
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi selectAll PhienSuDung: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return list;
    }
    
    @Override
    public PhienSuDung selectById(String maPhien) {
        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_ID_SQL, maPhien)) {
            if (rs.next()) {
                return mapResultSetToEntity(rs);
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi selectById PhienSuDung: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return null;
    }
    
    @Override
    public List<PhienSuDung> selectByMaMay(String maMay) {
        List<PhienSuDung> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_MAMAY_SQL, maMay)) {
            while (rs.next()) {
                PhienSuDung entity = mapResultSetToEntity(rs);
                list.add(entity);
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi selectByMaMay PhienSuDung: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return list;
    }
    
    @Override
    public List<PhienSuDung> selectByMaKH(String maKH) {
        List<PhienSuDung> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_MAKH_SQL, maKH)) {
            while (rs.next()) {
                PhienSuDung entity = mapResultSetToEntity(rs);
                list.add(entity);
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi selectByMaKH PhienSuDung: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return list;
    }
    
    @Override
    public List<PhienSuDung> selectByTrangThai(String trangThai) {
        List<PhienSuDung> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_TRANGTHAI_SQL, trangThai)) {
            while (rs.next()) {
                PhienSuDung entity = mapResultSetToEntity(rs);
                list.add(entity);
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi selectByTrangThai PhienSuDung: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return list;
    }
    
    @Override
    public boolean insert(PhienSuDung entity) {
        try {
            int result = XJdbc.executeUpdate(INSERT_SQL,
                entity.getMaPhien(),
                entity.getMaMay(),
                entity.getMaKH(),
                entity.getMaNV(),
                Timestamp.valueOf(entity.getThoiGianBatDau()),
                entity.getThoiGianKetThuc() != null ? Timestamp.valueOf(entity.getThoiGianKetThuc()) : null,
                entity.getSoGioSuDung(),
                entity.getTongTien(),
                entity.getTrangThai()
            );
            return result > 0;
        } catch (Exception ex) {
            System.err.println("Lỗi insert PhienSuDung: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public boolean update(PhienSuDung entity) {
        try {
            int result = XJdbc.executeUpdate(UPDATE_SQL,
                entity.getMaMay(),
                entity.getMaKH(),
                entity.getMaNV(),
                Timestamp.valueOf(entity.getThoiGianBatDau()),
                entity.getThoiGianKetThuc() != null ? Timestamp.valueOf(entity.getThoiGianKetThuc()) : null,
                entity.getSoGioSuDung(),
                entity.getTongTien(),
                entity.getTrangThai(),
                entity.getMaPhien()
            );
            return result > 0;
        } catch (Exception ex) {
            System.err.println("Lỗi update PhienSuDung: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public boolean delete(String maPhien) {
        try {
            int result = XJdbc.executeUpdate(DELETE_SQL, maPhien);
            return result > 0;
        } catch (Exception ex) {
            System.err.println("Lỗi delete PhienSuDung: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public boolean updateTrangThai(String maPhien, String trangThai) {
        try {
            int result = XJdbc.executeUpdate(UPDATE_TRANGTHAI_SQL, trangThai, maPhien);
            return result > 0;
        } catch (Exception ex) {
            System.err.println("Lỗi updateTrangThai PhienSuDung: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public List<PhienSuDung> selectByDateRange(LocalDateTime tuNgay, LocalDateTime denNgay) {
        List<PhienSuDung> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_DATERANGE_SQL, 
                Timestamp.valueOf(tuNgay), Timestamp.valueOf(denNgay))) {
            while (rs.next()) {
                PhienSuDung entity = mapResultSetToEntity(rs);
                list.add(entity);
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi selectByDateRange PhienSuDung: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return list;
    }
    
    @Override
    public List<PhienSuDung> selectByNhanVien(String maNV) {
        List<PhienSuDung> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_MANV_SQL, maNV)) {
            while (rs.next()) {
                PhienSuDung entity = mapResultSetToEntity(rs);
                list.add(entity);
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi selectByNhanVien PhienSuDung: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return list;
    }
    
    @Override
    public PhienSuDung selectPhienDangSuDung(String maMay) {
        // THÊM: Kiểm tra tất cả trạng thái có thể
        String checkAllSql = "SELECT TrangThai, COUNT(*) as SoLuong FROM PhienSuDung WHERE MaMay = ? GROUP BY TrangThai";
        System.out.println("=== DEBUG - Kiểm tra tất cả trạng thái của máy " + maMay + " ===");
        try (ResultSet allRs = XJdbc.executeQuery(checkAllSql, maMay)) {
            while (allRs.next()) {
                System.out.println("  - Trạng thái: '" + allRs.getString("TrangThai") + "' - Số lượng: " + allRs.getInt("SoLuong"));
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi check all status: " + ex.getMessage());
        }

        String sql = "SELECT * FROM PhienSuDung WHERE MaMay = ? AND TrangThai = N'Đang sử dụng'";
        System.out.println("=== DEBUG selectPhienDangSuDung ===");
        System.out.println("Mã máy: " + maMay);
        System.out.println("SQL: " + sql);

        try (ResultSet rs = XJdbc.executeQuery(sql, maMay)) {
            if (rs.next()) {
                PhienSuDung phien = mapResultSetToEntity(rs);
                System.out.println("✓ Tìm thấy phiên: " + phien.getMaPhien() + " - Trạng thái: " + phien.getTrangThai());
                System.out.println("  - Khách hàng: " + phien.getMaKH());
                System.out.println("  - Nhân viên: " + phien.getMaNV());
                System.out.println("=== END DEBUG selectPhienDangSuDung ===");
                return phien;
            } else {
                System.out.println("❌ KHÔNG tìm thấy phiên đang sử dụng cho máy: " + maMay);

                // Kiểm tra xem có phiên nào của máy này không
                String checkSql = "SELECT MaPhien, TrangThai FROM PhienSuDung WHERE MaMay = ? ORDER BY ThoiGianBatDau DESC";
                try (ResultSet checkRs = XJdbc.executeQuery(checkSql, maMay)) {
                    System.out.println("Danh sách TẤT CẢ phiên của máy " + maMay + ":");
                    boolean hasAny = false;
                    while (checkRs.next()) {
                        hasAny = true;
                        System.out.println("  - Phiên: " + checkRs.getString("MaPhien") + 
                                         " - Trạng thái: " + checkRs.getString("TrangThai"));
                    }
                    if (!hasAny) {
                        System.out.println("  ❌ KHÔNG CÓ phiên nào cho máy này");
                        System.out.println("  → Máy " + maMay + " chưa được bật lần nào!");
                    }
                }
                System.out.println("=== END DEBUG selectPhienDangSuDung ===");
                return null;
            }
        } catch (SQLException ex) {
            System.err.println("❌ Lỗi selectPhienDangSuDung: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
    
    // FIX: THÊM METHOD THIẾU
    @Override
    public boolean ketThucPhien(String maPhien, LocalDateTime thoiGianKetThuc) {
        try {
            int result = XJdbc.executeUpdate(KETTHUC_PHIEN_SQL, 
                Timestamp.valueOf(thoiGianKetThuc), maPhien);
            return result > 0;
        } catch (Exception ex) {
            System.err.println("Lỗi ketThucPhien PhienSuDung: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * Map ResultSet thành đối tượng PhienSuDung với Lombok Builder
     */
    private PhienSuDung mapResultSetToEntity(ResultSet rs) throws SQLException {
        PhienSuDung.PhienSuDungBuilder builder = PhienSuDung.builder()
            .maPhien(rs.getString("MaPhien"))
            .maMay(rs.getString("MaMay"))
            .maKH(rs.getString("MaKH"))
            .maNV(rs.getString("MaNV"))
            .soGioSuDung(rs.getBigDecimal("SoGioSuDung"))
            .tongTien(rs.getBigDecimal("TongTien"))
            .trangThai(rs.getString("TrangThai"));
        
        Timestamp thoiGianBatDau = rs.getTimestamp("ThoiGianBatDau");
        if (thoiGianBatDau != null) {
            builder.thoiGianBatDau(thoiGianBatDau.toLocalDateTime());
        }
        
        Timestamp thoiGianKetThuc = rs.getTimestamp("ThoiGianKetThuc");
        if (thoiGianKetThuc != null) {
            builder.thoiGianKetThuc(thoiGianKetThuc.toLocalDateTime());
        }
        
        return builder.build();
    }

    @Override
    public List<PhienSuDung> getByDateRange(Date tuNgay, Date denNgay) {
        List<PhienSuDung> result = new ArrayList<>();
        String sql = "SELECT p.*, m.TenMay, k.HoTen FROM PhienSuDung p " +
                     "LEFT JOIN MayTinh m ON p.MaMay = m.MaMay " +
                     "LEFT JOIN KhachHang k ON p.MaKH = k.MaKH " +
                     "WHERE (p.ThoiGianBatDau BETWEEN ? AND ?) OR " +
                     "(p.ThoiGianKetThuc BETWEEN ? AND ?) OR " +
                     "(p.ThoiGianBatDau <= ? AND (p.ThoiGianKetThuc IS NULL OR p.ThoiGianKetThuc >= ?))";

        try (Connection conn = XJdbc.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Thiết lập tham số
            stmt.setTimestamp(1, new java.sql.Timestamp(tuNgay.getTime()));
            stmt.setTimestamp(2, new java.sql.Timestamp(denNgay.getTime()));
            stmt.setTimestamp(3, new java.sql.Timestamp(tuNgay.getTime()));
            stmt.setTimestamp(4, new java.sql.Timestamp(denNgay.getTime()));
            stmt.setTimestamp(5, new java.sql.Timestamp(denNgay.getTime()));
            stmt.setTimestamp(6, new java.sql.Timestamp(tuNgay.getTime()));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Tạo đối tượng MayTinh
                MayTinh mayTinh = MayTinh.builder()
                    .maMay(rs.getString("MaMay"))
                    .tenMay(rs.getString("TenMay"))
                    .build();

                // Tạo đối tượng KhachHang
                KhachHang khachHang = null;
                if (rs.getString("MaKH") != null) {
                    khachHang = KhachHang.builder()
                        .maKH(rs.getString("MaKH"))
                        .hoTen(rs.getString("HoTen"))
                        .build();
                }

                // Tạo đối tượng PhienSuDung
                PhienSuDung phienSuDung = PhienSuDung.builder()
                    .maPhien(rs.getString("MaPhien"))
                    .maMay(rs.getString("MaMay"))
                    .maKH(rs.getString("MaKH"))
                    .thoiGianBatDau(rs.getTimestamp("ThoiGianBatDau").toLocalDateTime())
                    .thoiGianKetThuc(rs.getTimestamp("ThoiGianKetThuc").toLocalDateTime())
                    .trangThai(rs.getBoolean("TrangThai") ? "true" : "false")
                    .tongTien(rs.getBigDecimal("ThanhTien"))
                    .khachHang(khachHang)
                    .build();

                result.add(phienSuDung);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
