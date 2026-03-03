/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ql.net.dao;

import java.util.Date;
import java.util.List;
import ql.net.entity.ChiTietHoaDon;
/**
 *
 * @author ADMIN
 */
public interface ChiTietHoaDonDao {
    List<ChiTietHoaDon> getChiTietHoaDonByDateRange(Date tuNgay, Date denNgay);
    List<ChiTietHoaDon> selectAll();
    List<ChiTietHoaDon> selectByMaHD(String maHD);
    boolean insert(ChiTietHoaDon entity);
    boolean update(ChiTietHoaDon entity);
    boolean delete(String maHD, String maDV);
}
