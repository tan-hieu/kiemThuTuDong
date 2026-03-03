/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ql.net.dao;

import java.util.Date;
import java.util.List;
import ql.net.entity.ChiTietHoaDon;
import ql.net.entity.DichVu;

/**
 *
 * @author ADMIN
 */
public interface DichVuDao {
    List<DichVu> selectAll();
    DichVu selectById(String maDV);
    List<DichVu> selectByLoai(String loaiDV);
    boolean insert(DichVu entity);
    boolean update(DichVu entity);
    boolean delete(String maDV);
    boolean updateSoLuongTon(String maDV, int soLuongMoi);
    List<DichVu> selectByTrangThai(boolean trangThai);
    
    // FIX: SỬA TÊN METHOD ĐÚNG
    List<DichVu> searchByTen(String tenDV);
    List<DichVu> selectByKeyword(String keyword);
    int countByLoai(String loaiDV);
    
    List<DichVu> selectByMaMay(String maMay);
    
    List<ChiTietHoaDon> getChiTietHoaDonByDateRange(Date tuNgay, Date denNgay);
    List<DichVu> getDichVuByDateRange(Date tuNgay, Date denNgay);
}
