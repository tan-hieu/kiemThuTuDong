/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ql.net.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import ql.net.dao.DichVuDao;
import ql.net.entity.DichVu;
import ql.net.util.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ql.net.entity.ChiTietHoaDon;
import ql.net.entity.HoaDon;
import ql.net.entity.KhachHang;
/**
 *
 * @author ADMIN
 */
public class DichVuDaoImpl implements DichVuDao{
    // SQL Queries
    private final String SELECT_ALL_SQL = 
        "SELECT MaDV, TenDV, LoaiDV, DonGia, SoLuongTon, TrangThai, Image FROM DichVu ORDER BY MaDV";
    
    private final String SELECT_BY_ID_SQL = 
        "SELECT MaDV, TenDV, LoaiDV, DonGia, SoLuongTon, TrangThai, Image FROM DichVu WHERE MaDV = ?";
    
    private final String SELECT_BY_LOAI_SQL = 
        "SELECT MaDV, TenDV, LoaiDV, DonGia, SoLuongTon, TrangThai, Image FROM DichVu WHERE LoaiDV = ? ORDER BY TenDV";
    
    private final String INSERT_SQL = 
        "INSERT INTO DichVu (MaDV, TenDV, LoaiDV, DonGia, SoLuongTon, TrangThai, Image) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    private final String UPDATE_SQL = 
        "UPDATE DichVu SET TenDV = ?, LoaiDV = ?, DonGia = ?, SoLuongTon = ?, TrangThai = ?, Image = ? WHERE MaDV = ?";
    
    private final String DELETE_SQL = 
        "DELETE FROM DichVu WHERE MaDV = ?";
    
    private final String UPDATE_SOLUONGTON_SQL = 
        "UPDATE DichVu SET SoLuongTon = ? WHERE MaDV = ?";
    
    private final String SELECT_BY_TRANGTHAI_SQL = 
        "SELECT MaDV, TenDV, LoaiDV, DonGia, SoLuongTon, TrangThai, Image FROM DichVu WHERE TrangThai = ? ORDER BY TenDV";
    
    private final String SEARCH_BY_TEN_SQL = 
        "SELECT MaDV, TenDV, LoaiDV, DonGia, SoLuongTon, TrangThai, Image FROM DichVu WHERE TenDV LIKE ? ORDER BY TenDV";
    
    private final String SELECT_BY_KEYWORD_SQL = 
        "SELECT MaDV, TenDV, LoaiDV, DonGia, SoLuongTon, TrangThai, Image FROM DichVu " +
        "WHERE TenDV LIKE ? OR LoaiDV LIKE ? OR MaDV LIKE ? ORDER BY TenDV";
    
    private final String COUNT_BY_LOAI_SQL = 
        "SELECT COUNT(*) FROM DichVu WHERE LoaiDV = ?";
    
    @Override
    public List<DichVu> selectAll() {
        List<DichVu> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(SELECT_ALL_SQL)) {
            while (rs.next()) {
                DichVu entity = mapResultSetToEntity(rs);
                list.add(entity);
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi selectAll DichVu: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return list;
    }
    
    @Override
    public DichVu selectById(String maDV) {
        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_ID_SQL, maDV)) {
            if (rs.next()) {
                return mapResultSetToEntity(rs);
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi selectById DichVu: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return null;
    }
    
    @Override
    public List<DichVu> selectByLoai(String loaiDV) {
        List<DichVu> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_LOAI_SQL, loaiDV)) {
            while (rs.next()) {
                DichVu entity = mapResultSetToEntity(rs);
                list.add(entity);
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi selectByLoai DichVu: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return list;
    }
    
    @Override
    public boolean insert(DichVu entity) {
        try {
            int result = XJdbc.executeUpdate(INSERT_SQL,
                entity.getMaDV(),
                entity.getTenDV(),
                entity.getLoaiDV(),
                entity.getDonGia(),
                entity.getSoLuongTon(),
                entity.isTrangThai(),
                entity.getImage()
            );
            return result > 0;
        } catch (Exception ex) {
            System.err.println("Lỗi insert DichVu: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public boolean update(DichVu entity) {
        try {
            int result = XJdbc.executeUpdate(UPDATE_SQL,
                entity.getTenDV(),
                entity.getLoaiDV(),
                entity.getDonGia(),
                entity.getSoLuongTon(),
                entity.isTrangThai(),
                entity.getImage(),
                entity.getMaDV()
            );
            return result > 0;
        } catch (Exception ex) {
            System.err.println("Lỗi update DichVu: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public boolean delete(String maDV) {
        try {
            int result = XJdbc.executeUpdate(DELETE_SQL, maDV);
            return result > 0;
        } catch (Exception ex) {
            System.err.println("Lỗi delete DichVu: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public boolean updateSoLuongTon(String maDV, int soLuongMoi) {
        try {
            int result = XJdbc.executeUpdate(UPDATE_SOLUONGTON_SQL, soLuongMoi, maDV);
            return result > 0;
        } catch (Exception ex) {
            System.err.println("Lỗi updateSoLuongTon DichVu: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public List<DichVu> selectByTrangThai(boolean trangThai) {
        List<DichVu> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_TRANGTHAI_SQL, trangThai)) {
            while (rs.next()) {
                DichVu entity = mapResultSetToEntity(rs);
                list.add(entity);
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi selectByTrangThai DichVu: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return list;
    }
    
    // FIX: SỬA TÊN METHOD
    @Override
    public List<DichVu> searchByTen(String tenDV) {
        List<DichVu> list = new ArrayList<>();
        String keyword = "%" + tenDV + "%";
        try (ResultSet rs = XJdbc.executeQuery(SEARCH_BY_TEN_SQL, keyword)) {
            while (rs.next()) {
                DichVu entity = mapResultSetToEntity(rs);
                list.add(entity);
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi searchByTen DichVu: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return list;
    }
    
    @Override
    public List<DichVu> selectByKeyword(String keyword) {
        List<DichVu> list = new ArrayList<>();
        String searchKeyword = "%" + keyword + "%";
        try (ResultSet rs = XJdbc.executeQuery(SELECT_BY_KEYWORD_SQL, 
                searchKeyword, searchKeyword, searchKeyword)) {
            while (rs.next()) {
                DichVu entity = mapResultSetToEntity(rs);
                list.add(entity);
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi selectByKeyword DichVu: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        return list;
    }
    
    @Override
    public int countByLoai(String loaiDV) {
        try {
            Integer count = XJdbc.getValue(COUNT_BY_LOAI_SQL, loaiDV);
            return count != null ? count : 0;
        } catch (Exception ex) {
            System.err.println("Lỗi countByLoai DichVu: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * Map ResultSet thành đối tượng DichVu với Lombok Builder
     */
    private DichVu mapResultSetToEntity(ResultSet rs) throws SQLException {
        return DichVu.builder()
            .maDV(rs.getString("MaDV"))
            .tenDV(rs.getString("TenDV"))
            .loaiDV(rs.getString("LoaiDV"))
            .donGia(rs.getBigDecimal("DonGia"))
            .soLuongTon(rs.getInt("SoLuongTon"))
            .trangThai(rs.getBoolean("TrangThai"))
            .image(rs.getString("Image"))
            .build();
    }
    
    public List<DichVu> selectByMaMay(String maMay) {
        System.out.println("=== DEBUG selectByMaMay ===");
        System.out.println("Mã máy nhận vào: " + maMay);

        // SỬA: Đơn giản hóa SQL query và thêm debug
        String sql = """
            SELECT dv.MaDV, dv.TenDV, dv.LoaiDV, dv.DonGia, 
                   ct.SoLuong, ct.ThanhTien, dv.Image
            FROM DichVu dv 
            INNER JOIN ChiTietHoaDon ct ON dv.MaDV = ct.MaDV
            INNER JOIN HoaDon hd ON ct.MaHD = hd.MaHD  
            INNER JOIN PhienSuDung ps ON hd.MaPhien = ps.MaPhien
            WHERE ps.MaMay = ? AND ps.TrangThai = N'Đang sử dụng'
        """;

        System.out.println("SQL: " + sql);

        // THÊM DEBUG: Kiểm tra từng bước JOIN
        String debugPhien = "SELECT MaPhien FROM PhienSuDung WHERE MaMay = ? AND TrangThai = N'Đang sử dụng'";
        try (ResultSet debugRs = XJdbc.executeQuery(debugPhien, maMay)) {
            if (debugRs.next()) {
                String maPhien = debugRs.getString("MaPhien");
                System.out.println("✓ Phiên tìm thấy: " + maPhien);

                // Kiểm tra hóa đơn
                String debugHD = "SELECT MaHD FROM HoaDon WHERE MaPhien = ?";
                try (ResultSet hdRs = XJdbc.executeQuery(debugHD, maPhien)) {
                    if (hdRs.next()) {
                        String maHD = hdRs.getString("MaHD");
                        System.out.println("✓ Hóa đơn tìm thấy: " + maHD);

                        // Kiểm tra chi tiết hóa đơn
                        String debugCT = "SELECT COUNT(*) as SoLuong FROM ChiTietHoaDon WHERE MaHD = ?";
                        try (ResultSet ctRs = XJdbc.executeQuery(debugCT, maHD)) {
                            if (ctRs.next()) {
                                int soLuongCT = ctRs.getInt("SoLuong");
                                System.out.println("✓ Số chi tiết hóa đơn: " + soLuongCT);
                            }
                        }
                    } else {
                        System.out.println("❌ KHÔNG tìm thấy hóa đơn cho phiên: " + maPhien);
                    }
                }
            } else {
                System.out.println("❌ KHÔNG tìm thấy phiên với TrangThai = 'Đang sử dụng'");
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi debug: " + ex.getMessage());
        }

        List<DichVu> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(sql, maMay)) {
            System.out.println("Thực thi query chính thành công");
            int count = 0;
            while (rs.next()) {
                count++;
                System.out.println("Tìm thấy dữ liệu dòng " + count + ":");
                System.out.println("  - MaDV: " + rs.getString("MaDV"));
                System.out.println("  - TenDV: " + rs.getString("TenDV"));
                System.out.println("  - SoLuong: " + rs.getInt("SoLuong"));
                System.out.println("  - ThanhTien: " + rs.getBigDecimal("ThanhTien"));

                DichVu dv = DichVu.builder()
                    .maDV(rs.getString("MaDV"))
                    .tenDV(rs.getString("TenDV"))
                    .loaiDV(rs.getString("LoaiDV"))
                    .donGia(rs.getBigDecimal("DonGia"))
                    .soLuongTon(rs.getInt("SoLuong"))
                    .image(rs.getString("Image"))
                    .build();
                dv.setThanhTien(rs.getBigDecimal("ThanhTien"));
                list.add(dv);
            }
            System.out.println("Tổng số dòng tìm thấy: " + count);
        } catch (SQLException ex) {
            System.err.println("Lỗi selectByMaMay DichVu: " + ex.getMessage());
            ex.printStackTrace();
        }

        System.out.println("Trả về list có " + list.size() + " phần tử");
        System.out.println("=== END DEBUG selectByMaMay ===");
        return list;
    }

    @Override
    public List<ChiTietHoaDon> getChiTietHoaDonByDateRange(Date tuNgay, Date denNgay) {
        List<ChiTietHoaDon> result = new ArrayList<>();

        // SỬA: Thêm alias NgayTao cho cột NgayLap
        String sql = "SELECT ct.*, d.TenDV, h.NgayLap AS NgayTao, k.HoTen FROM ChiTietHoaDon ct " +
                    "JOIN HoaDon h ON ct.MaHD = h.MaHD " +
                    "JOIN DichVu d ON ct.MaDV = d.MaDV " +
                    "LEFT JOIN KhachHang k ON h.MaKH = k.MaKH " +
                    "WHERE h.NgayLap BETWEEN ? AND ?";

        try (Connection conn = XJdbc.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(tuNgay.getTime()));
            stmt.setDate(2, new java.sql.Date(denNgay.getTime()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                KhachHang khachHang = KhachHang.builder()
                    .maKH(rs.getString("MaKH"))
                    .hoTen(rs.getString("HoTen"))
                    .build();

                // Giữ nguyên sử dụng .ngayTao()
                HoaDon hoaDon = HoaDon.builder()
                        .maHD(rs.getString("MaHD"))
                        .ngayTao(rs.getTimestamp("NgayTao").toLocalDateTime())
                        .khachHang(khachHang)
                        .build();

                DichVu dichVu = DichVu.builder()
                    .maDV(rs.getString("MaDV"))
                    .tenDV(rs.getString("TenDV"))
                    .build();

                ChiTietHoaDon chiTiet = ChiTietHoaDon.builder()
                    .maHD(rs.getString("MaHD"))
                    .maDV(rs.getString("MaDV"))
                    .soLuong(rs.getInt("SoLuong"))
                    .donGia(rs.getBigDecimal("DonGia"))
                    .hoaDon(hoaDon)
                    .dichVu(dichVu)
                    .build();

                result.add(chiTiet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<DichVu> getDichVuByDateRange(Date tuNgay, Date denNgay) {
        return selectAll();
    }
}
