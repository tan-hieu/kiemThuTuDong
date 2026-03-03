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
public class DichVu {
    private String maDV;
    private String tenDV;
    private String loaiDV;
    private BigDecimal donGia;
    
    @Builder.Default
    private int soLuongTon = 0;
    
    @Builder.Default
    private boolean trangThai = true;
    
    private String image;
    
    // Thêm thuộc tính cho việc hiển thị ở bảng Food
    private BigDecimal thanhTien;
    
    public int getSoLuong() {
        return this.soLuongTon; // hoặc field phù hợp
    }

    public BigDecimal getThanhTien() {
        if (this.thanhTien != null) {
            return this.thanhTien;
        }
        // Fallback nếu chưa set thanhTien
        return this.donGia.multiply(BigDecimal.valueOf(this.soLuongTon));
    }
    
    public void setThanhTien(BigDecimal thanhTien) {
        this.thanhTien = thanhTien;
    }
}
