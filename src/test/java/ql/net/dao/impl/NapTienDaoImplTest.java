package ql.net.dao.impl;

import org.testng.Assert;
import org.testng.annotations.Test;
import ql.net.entity.NapTien;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Unit test cho NapTienDaoImpl
 * Bao phủ TEST NO 41–50.
 */
public class NapTienDaoImplTest {

    private final NapTienDaoImpl dao = new NapTienDaoImpl();

    // TEST NO 41 - insert_HopLe
    @Test
    public void TC_NapTienDaoImpl_insert_HopLe() {
        NapTien nt = NapTien.builder()
                .maNapTien("NT05")
                .maKH("KH01")
                .maNV("NV01")
                .soTienNap(new BigDecimal("50000"))
                .ngayNap(LocalDateTime.now())
                .hinhThucNap("Tiền mặt")
                .build();

        boolean ok = dao.insert(nt);
        Assert.assertTrue(ok);

        NapTien fromDb = dao.getById("NT05");
        Assert.assertNotNull(fromDb);
        Assert.assertEquals(fromDb.getSoTienNap(), new BigDecimal("50000"));
    }

    // TEST NO 42 - insert_KhongTonTaiMaKH_Fail
    @Test
    public void TC_NapTienDaoImpl_insert_KhongTonTaiMaKH_Fail() {
        NapTien nt = NapTien.builder()
                .maNapTien("NT06")
                .maKH("KH99") // không tồn tại
                .maNV("NV01")
                .soTienNap(new BigDecimal("10000"))
                .ngayNap(LocalDateTime.now())
                .hinhThucNap("Tiền mặt")
                .build();

        try {
            boolean ok = dao.insert(nt);
            Assert.assertFalse(ok, "Không được insert khi MaKH không tồn tại");
        } catch (RuntimeException ex) {
            Assert.fail("Nên xử lý lỗi FK một cách mềm, không ném RuntimeException thô");
        }
    }

    // TEST NO 43 - update_SuaSoTien
    @Test
    public void TC_NapTienDaoImpl_update_SuaSoTien() {
        NapTien nt = dao.getById("NT01");
        Assert.assertNotNull(nt, "Cần có NT01 trong DB mẫu");

        nt.setSoTienNap(new BigDecimal("60000"));
        boolean ok = dao.update(nt);
        Assert.assertTrue(ok);

        NapTien after = dao.getById("NT01");
        Assert.assertEquals(after.getSoTienNap(), new BigDecimal("60000"));
    }

    // TEST NO 44 - update_SaiMaNapTien_Fail
    @Test
    public void TC_NapTienDaoImpl_update_SaiMaNapTien_Fail() {
        NapTien nt = NapTien.builder()
                .maNapTien("NT99")
                .maKH("KH01")
                .maNV("NV01")
                .soTienNap(new BigDecimal("10000"))
                .ngayNap(LocalDateTime.now())
                .hinhThucNap("Tiền mặt")
                .build();

        boolean ok = dao.update(nt);
        Assert.assertFalse(ok, "Update với MaNapTien không tồn tại phải trả false");
    }

    // TEST NO 45 - delete_TonTai
    @Test
    public void TC_NapTienDaoImpl_delete_TonTai() {
        boolean ok = dao.delete("NT004");
        Assert.assertTrue(ok, "Xóa NT004 phải trả về true");

        NapTien nt = dao.getById("NT004");
        Assert.assertNull(nt, "Sau xóa NT004 không còn trong DB");
    }

    // TEST NO 46 - delete_DaLienKet_Fail
    // Ghi chú: cần cấu hình FK chặt nếu muốn tái hiện lỗi vi phạm khóa ngoại
    @Test
    public void TC_NapTienDaoImpl_delete_DaLienKet_Fail() {
        try {
            boolean ok = dao.delete("NT01");
            Assert.assertFalse(ok, "Nếu NT01 đã liên kết thì không nên xóa được");
        } catch (RuntimeException ex) {
            Assert.fail("Nên báo lỗi nghiệp vụ, không RuntimeException thô");
        }
    }

    // TEST NO 47 - getNapTienByDateRange_TrongKhoang
    @Test
    public void TC_NapTienDaoImpl_getNapTienByDateRange_TrongKhoang() {
        // 01/07/2025 đến 31/07/2025
        java.util.Date tu = java.sql.Date.valueOf("2025-07-01");
        java.util.Date den = java.sql.Date.valueOf("2025-07-31");

        List<NapTien> list = dao.getNapTienByDateRange(tu, den);
        Assert.assertFalse(list.isEmpty(), "Trong khoảng này phải có NT01, NT02");
    }

    // TEST NO 48 - getNapTienByDateRange_KhoangRong_Fail
    @Test
    public void TC_NapTienDaoImpl_getNapTienByDateRange_KhoangRong_Fail() {
        java.util.Date tu = java.sql.Date.valueOf("2030-01-01");
        java.util.Date den = java.sql.Date.valueOf("2030-01-31");

        List<NapTien> list = dao.getNapTienByDateRange(tu, den);
        Assert.assertEquals(list.size(), 0, "Khoảng ngày tương lai không có dữ liệu => list rỗng");
    }

    // TEST NO 49 - getAll_DuLieuMacDinh
    @Test
    public void TC_NapTienDaoImpl_getAll_DuLieuMacDinh() {
        List<NapTien> list = dao.getAll();
        Assert.assertEquals(list.size(), 3, "DB mẫu có NT01, NT02, NT004");
    }

    // TEST NO 50 - getAll_BangLon_Fail
    // Cần tự chèn thêm vài nghìn bản ghi trước khi chạy
    @Test
    public void TC_NapTienDaoImpl_getAll_BangLon_Fail() {
        List<NapTien> list = dao.getAll();
        Assert.assertTrue(list.size() >= 1000,
                "Khi dữ liệu lớn, getAll vẫn phải trả đủ bản ghi, không lỗi");
    }
}
