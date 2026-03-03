/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ql.net.dao;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import ql.net.entity.PhienSuDung;

/**
 *
 * @author ADMIN
 */
public interface PhienSuDungDao {
    List<PhienSuDung> getByDateRange(Date tuNgay, Date denNgay);
    List<PhienSuDung> selectAll();
    PhienSuDung selectById(String maPhien);
    List<PhienSuDung> selectByMaMay(String maMay);
    List<PhienSuDung> selectByMaKH(String maKH);
    List<PhienSuDung> selectByTrangThai(String trangThai);
    boolean insert(PhienSuDung entity);
    boolean update(PhienSuDung entity);
    boolean delete(String maPhien);
    boolean updateTrangThai(String maPhien, String trangThai);
    List<PhienSuDung> selectByDateRange(LocalDateTime tuNgay, LocalDateTime denNgay);
    List<PhienSuDung> selectByNhanVien(String maNV);
    PhienSuDung selectPhienDangSuDung(String maMay);
    
    // THÊM METHOD THIẾU
    boolean ketThucPhien(String maPhien, LocalDateTime thoiGianKetThuc);
}
