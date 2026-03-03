/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ql.net.dao;

import java.util.Date;
import ql.net.entity.MayTinh;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public interface MayTinhDao {
    List<MayTinh> selectAll();
    MayTinh selectById(String maMay);
    List<MayTinh> selectByLoai(String loaiMay);
    List<MayTinh> selectByTrangThai(String trangThai);
    boolean insert(MayTinh entity);
    boolean update(MayTinh entity);
    boolean delete(String maMay);
    boolean updateTrangThai(String maMay, String trangThai);
    int countByLoai(String loaiMay);
    int countByTrangThai(String trangThai);
    List<MayTinh> getMayByDateRange(Date tuNgay, Date denNgay);
}
