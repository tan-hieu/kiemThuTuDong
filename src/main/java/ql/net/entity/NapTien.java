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
public class NapTien {
    private String maNapTien;
    private String maKH;
    private String maNV;
    private BigDecimal soTienNap;
    
    @Builder.Default
    private LocalDateTime ngayNap = LocalDateTime.now();
    
    private String hinhThucNap;
    
    private KhachHang khachHang;
    public KhachHang getKhachHang() { 
        return khachHang; 
    }
    public void setKhachHang(KhachHang khachHang) { 
        this.khachHang = khachHang; 
    }
}
