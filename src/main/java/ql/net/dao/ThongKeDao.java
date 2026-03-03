/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ql.net.dao;

import java.sql.SQLException;

/**
 *
 * @author ADMIN
 */
public interface ThongKeDao {
    double getDoanhThu() throws SQLException;
    int getSoMay() throws SQLException;
    int getSoDichVu() throws SQLException;
    int getSoNhanVien() throws SQLException;
    int getSoVIP() throws SQLException;
    int getSoLanBaoTri() throws SQLException;
    int getSoLanNapTien() throws SQLException;
    int getSoGiaoDich() throws SQLException;
}
