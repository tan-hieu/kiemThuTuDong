/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ql.net.dao;

import java.util.Date;
import java.util.List;
import ql.net.entity.NhanVien;

/**
 *
 * @author ADMIN
 */
public interface NhanVienDao {
    List<NhanVien> getNhanVienByDateRange(Date tuNgay, Date denNgay);
    List<NhanVien> selectAll();
    NhanVien selectById(String maNV);
    NhanVien login(String tenDangNhap, String matKhau);
    boolean insert(NhanVien entity);
    boolean update(NhanVien entity);
    boolean delete(String maNV);
    boolean changePassword(String maNV, String matKhauCu, String matKhauMoi);
    List<NhanVien> selectByChucVu(String chucVu);
}
