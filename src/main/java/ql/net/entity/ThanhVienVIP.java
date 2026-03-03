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
public class ThanhVienVIP {
    private String maVIP;
    private String maKH;
    private String loaiVIP;
    
    @Builder.Default
    private BigDecimal tongChiTieu = BigDecimal.ZERO;
    
    private LocalDate ngayThamGia;
    private LocalDate ngayHetHan;
    
    @Builder.Default
    private boolean trangThai = true; // 1 = còn hiệu lực, 0 = hết hạn
    
    private KhachHang khachHang;
    public KhachHang getKhachHang() { 
        return khachHang; 
    }
    public void setKhachHang(KhachHang khachHang) { 
        this.khachHang = khachHang; 
    }
}
