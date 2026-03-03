/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ql.net.dao;

import java.util.Date;
import java.util.List;
import ql.net.entity.ThanhVienVIP;

/**
 *
 * @author ADMIN
 */
public interface ThanhVienVipDao {
    List<ThanhVienVIP> getVIPByDateRange(Date tuNgay, Date denNgay);
    List<ThanhVienVIP> selectAll();
    ThanhVienVIP selectById(String maVIP);
    boolean insert(ThanhVienVIP vip);
    void update(ThanhVienVIP vip);
    void delete(String maVIP);
}
