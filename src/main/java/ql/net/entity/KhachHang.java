/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ql.net.entity;

import java.math.BigDecimal;
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
public class KhachHang {
    private String maKH;
    private String hoTen;
    private String soDT;
    
    @Builder.Default
    private BigDecimal soDuTaiKhoan = BigDecimal.ZERO;
    
    @Builder.Default
    private boolean trangThai = true;
    
    private String image;
    
    private KhachHang khachHang;
    
    public KhachHang getKhachHang() { 
        return khachHang;
    }
    public void setKhachHang(KhachHang khachHang) { 
        this.khachHang = khachHang; 
    }
}
