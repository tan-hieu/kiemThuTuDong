/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ql.net.dao;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import ql.net.entity.HoaDon;

/**
 *
 * @author ADMIN
 */
public interface HoaDonDao {
    List<HoaDon> getHoaDonByDateRange(Date tuNgay, Date denNgay);
    List<HoaDon> selectAll();
    HoaDon selectById(String maHD);
    List<HoaDon> selectByMaKH(String maKH);
    List<HoaDon> selectByTrangThai(String trangThai);
    boolean insert(HoaDon entity);
    boolean update(HoaDon entity);
    boolean delete(String maHD);
    boolean updateTrangThai(String maHD, String trangThai);
    List<HoaDon> selectByDateRange(LocalDateTime tuNgay, LocalDateTime denNgay);
    List<HoaDon> selectByNhanVien(String maNV);
    List<HoaDon> getDoanhThuByDateRange(Date tuNgay, Date denNgay);
}
