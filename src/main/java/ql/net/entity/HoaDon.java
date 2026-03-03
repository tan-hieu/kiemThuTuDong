/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ql.net.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ADMIN
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDon {
    private String maHD;
    private String maPhien;
    private String maNV;
    private String maKH;
    
    @Builder.Default
    private LocalDateTime ngayTao = LocalDateTime.now();
    
    @Builder.Default
    private BigDecimal tongTienMay = BigDecimal.ZERO;
    
    @Builder.Default
    private BigDecimal tongTienDichVu = BigDecimal.ZERO;
    
    @Builder.Default
    private BigDecimal tongTien = BigDecimal.ZERO;
    
    @Builder.Default
    private String trangThai = "Chờ thanh toán";
    
    private KhachHang khachHang;
    public KhachHang getKhachHang() { 
        return khachHang; 
    }
    public void setKhachHang(KhachHang khachHang) { 
        this.khachHang = khachHang; 
    }
    
    private HoaDon hoaDon;

    public HoaDon getHoaDon() {
        return hoaDon; 
    }
    public void setHoaDon(HoaDon hoaDon) { 
        this.hoaDon = hoaDon; 
    }
}
