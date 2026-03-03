/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ql.net.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 *
 * @author ADMIN
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhienSuDung {
    private String maPhien;
    private String maMay;
    private String maKH;
    private String maNV;
    private LocalDateTime thoiGianBatDau;
    private LocalDateTime thoiGianKetThuc;
    
    @Builder.Default
    private BigDecimal soGioSuDung = BigDecimal.ZERO;
    
    @Builder.Default
    private BigDecimal tongTien = BigDecimal.ZERO;
    
    @Builder.Default
    private String trangThai = "Đang sử dụng";
    
    private KhachHang khachHang;
    public KhachHang getKhachHang() { 
        return khachHang; 
    }
    public void setKhachHang(KhachHang khachHang) { 
        this.khachHang = khachHang; 
    }
}
