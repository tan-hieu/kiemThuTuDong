/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ql.net.dao;

import java.util.Date;
import java.util.List;
import ql.net.entity.BaoTri;

/**
 *
 * @author ADMIN
 */
public interface BaoTriDao {
    List<BaoTri> getBaoTriByDateRange(Date tuNgay, Date denNgay);
    List<BaoTri> getAll();
    BaoTri getById(String maBaoTri);
    boolean insert(BaoTri baoTri);
    boolean update(BaoTri baoTri);
    boolean delete(String maBaoTri);
}
