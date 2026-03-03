/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ql.net.dao;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import ql.net.entity.NapTien;

/**
 *
 * @author ADMIN
 */
public interface NapTienDao {
    List<NapTien> getNapTienByDateRange(Date tuNgay, Date denNgay);
    List<NapTien> selectAll();
    NapTien selectById(String maNapTien);
    List<NapTien> selectByMaKH(String maKH);
    List<NapTien> selectByMaNV(String maNV);
    boolean insert(NapTien entity);
    boolean update(NapTien entity);
    boolean delete(String maNapTien);
    List<NapTien> selectByDateRange(LocalDateTime tuNgay, LocalDateTime denNgay);
    List<NapTien> selectByHinhThuc(String hinhThucNap);
}
