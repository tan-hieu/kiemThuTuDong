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
public class NhapHang {
    private String maNhapHang;
    private String maNV;
    private String maDV;
    
    @Builder.Default
    private LocalDateTime ngayNhap = LocalDateTime.now();
    
    private String tenNhaCungCap;
    private int soLuong;
    private BigDecimal donGiaNhap;
    private BigDecimal thanhTien;
}
