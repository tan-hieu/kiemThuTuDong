/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ql.net.dao;

import java.util.List;
import ql.net.entity.NhapHang;

/**
 *
 * @author ADMIN
 */

public interface NhapHangDao {
    List<NhapHang> selectAll();
    NhapHang selectById(String maNhapHang);
    boolean insert(NhapHang entity);
    boolean update(NhapHang entity);
    boolean delete(String maNhapHang);
}
