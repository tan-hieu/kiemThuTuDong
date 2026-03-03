/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ql.net.dao;
import java.util.Date;
import java.util.List;
import ql.net.entity.LichSuGiaoDich;
/**
 *
 * @author ADMIN
 */
public interface LichSuGiaoDichDao {
    List<LichSuGiaoDich> getGiaoDichByDateRange(Date tuNgay, Date denNgay);
    List<LichSuGiaoDich> selectAll();
    List<LichSuGiaoDich> selectByMaKH(String maKH);
    boolean insert(LichSuGiaoDich entity);
    boolean delete(String maGiaoDich);
}
