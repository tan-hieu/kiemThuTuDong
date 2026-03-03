/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ql.net.dao;

import java.math.BigDecimal;
import java.util.List;
import ql.net.entity.KhachHang;

/**
 *
 * @author ADMIN
 */
public interface KhachHangDao {
    List<KhachHang> selectAll();
    KhachHang selectById(String maKH);
    boolean insert(KhachHang entity);
    boolean update(KhachHang entity);
    boolean delete(String maKH);
    boolean updateSoDu(String maKH, BigDecimal soDuMoi);
    List<KhachHang> selectByTrangThai(boolean trangThai);
    List<KhachHang> searchBySDT(String soDT);
    List<KhachHang> searchByTen(String hoTen);
    void updateImage(String maKH, String fileAnh);
}
