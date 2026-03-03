/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ql.net.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author ADMIN
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaoTri {
    private String maBaoTri;
    private String maMay;
    private String maNV;
    private LocalDate ngayBaoTri;
    private String moTa;
    
    @Builder.Default
    private BigDecimal chiPhiSua = BigDecimal.ZERO;
    
    @Builder.Default
    private String trangThai = "Chờ sửa";
    
    private LocalDate ngaySua;
    
    private KhachHang khachHang;
    public KhachHang getKhachHang() { 
        return khachHang; 
    }
    public void setKhachHang(KhachHang khachHang) { 
        this.khachHang = khachHang; 
    }

    private NhanVien nhanVien;

    public NhanVien getNhanVien() {
        return nhanVien;
    }
    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien; 
    }
    
    
}
