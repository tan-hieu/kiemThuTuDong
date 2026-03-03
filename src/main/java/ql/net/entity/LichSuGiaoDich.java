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
import java.time.LocalDateTime;
/**
 *
 * @author ADMIN
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LichSuGiaoDich {
    private String maGiaoDich;
    private String maKH;
    private String loaiGiaoDich;
    private BigDecimal soTien;
    
    @Builder.Default
    private LocalDateTime ngayGiaoDich = LocalDateTime.now();

    private KhachHang khachHang;
    public KhachHang getKhachHang() { 
        return khachHang; 
    }
    public void setKhachHang(KhachHang khachHang) { 
        this.khachHang = khachHang; 
    }
}
