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
public class MayTinh {
    private String maMay;
    private String tenMay;
    private String loaiMay;
    private BigDecimal giaTheoGio;
    private String tamTinh;
    private String thoiGianSuDung;
    private String thoiDiemBatDau;
    
    @Builder.Default
    private String trangThai = "Trống";
    
    public String getTamTinh() {
        return tamTinh;
    }
    public String getThoiGianSuDung() {
        return thoiGianSuDung;
    }
    public String getThoiDiemBatDau() {
        return thoiDiemBatDau;
    }
}
