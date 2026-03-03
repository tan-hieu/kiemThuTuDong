/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ql.net.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ql.net.dao.ThongKeDao;
import ql.net.util.XJdbc;

/**
 *
 * @author ADMIN
 */
public class ThongKeDaoImpl implements ThongKeDao{
    @Override
    public double getDoanhThu() throws SQLException {
        String query = "SELECT SUM(TongTien) FROM HoaDon";
        try (Connection conn = XJdbc.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getDouble(1);
            }
            return 0.0;
        }
    }
    
    @Override
    public int getSoMay() throws SQLException {
        String query = "SELECT COUNT(*) FROM MayTinh";
        return executeCountQuery(query);
    }
    
    @Override
    public int getSoDichVu() throws SQLException {
        String query = "SELECT COUNT(*) FROM DichVu";
        return executeCountQuery(query);
    }
    
    @Override
    public int getSoNhanVien() throws SQLException {
        String query = "SELECT COUNT(*) FROM NhanVien";
        return executeCountQuery(query);
    }
    
    @Override
    public int getSoVIP() throws SQLException {
        String query = "SELECT COUNT(*) FROM ThanhVienVIP";
        return executeCountQuery(query);
    }
    
    @Override
    public int getSoLanBaoTri() throws SQLException {
        String query = "SELECT COUNT(*) FROM BaoTri";
        return executeCountQuery(query);
    }
    
    @Override
    public int getSoLanNapTien() throws SQLException {
        String query = "SELECT COUNT(*) FROM NapTien";
        return executeCountQuery(query);
    }
    
    @Override
    public int getSoGiaoDich() throws SQLException {
        String query = "SELECT COUNT(*) FROM LichSuGiaoDich";
        return executeCountQuery(query);
    }
    
    // Phương thức hỗ trợ để thực thi truy vấn đếm
    private int executeCountQuery(String query) throws SQLException {
        try (Connection conn = XJdbc.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }
}
