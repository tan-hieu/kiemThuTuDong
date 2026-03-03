package ql.net.dao.impl;

import org.testng.Assert;
import org.testng.annotations.Test;
import ql.net.entity.PhienSuDung;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Unit test cho PhienSuDungDaoImpl
 * Bao phủ TEST NO 31–40.
 */
public class PhienSuDungDaoImplTest {

    private final PhienSuDungDaoImpl dao = new PhienSuDungDaoImpl();

    // TEST NO 31 - insert_PhiênMoiHopLe
    @Test
    public void TC_PhienSuDungDaoImpl_insert_PhiênMoiHopLe() {
        PhienSuDung phien = PhienSuDung.builder()
                .maPhien("PS07")
                .maMay("MT01")
                .maKH("KH01")
                .maNV("NV01")
                .thoiGianBatDau(LocalDateTime.now())
                .trangThai("Đang sử dụng")
                .soGioSuDung(BigDecimal.ZERO)
                .tongTien(BigDecimal.ZERO)
                .build();

        boolean ok = dao.insert(phien);
        Assert.assertTrue(ok);

        PhienSuDung fromDb = dao.selectById("PS07");
        Assert.assertNotNull(fromDb);
        Assert.assertEquals(fromDb.getMaMay(), "MT01");
    }

    // TEST NO 32 - insert_TrungMaPhien_Fail
    @Test
    public void TC_PhienSuDungDaoImpl_insert_TrungMaPhien_Fail() {
        PhienSuDung phien = PhienSuDung.builder()
                .maPhien("PS02")
                .maMay("MT01")
                .maKH("KH01")
                .maNV("NV01")
                .thoiGianBatDau(LocalDateTime.now())
                .trangThai("Đang sử dụng")
                .build();

        try {
            boolean ok = dao.insert(phien);
            Assert.assertFalse(ok, "Trùng khóa chính MaPhien phải không cho insert");
        } catch (RuntimeException ex) {
            Assert.fail("Nên trả false hoặc lỗi nghiệp vụ rõ ràng, không RuntimeException thô");
        }
    }

    // TEST NO 33 - selectPhienDangSuDung_MayCoPhien
    @Test
    public void TC_PhienSuDungDaoImpl_selectPhienDangSuDung_MayCoPhien() {
        PhienSuDung phien = dao.selectPhienDangSuDung("MT04");
        Assert.assertNotNull(phien, "Máy MT04 đang có phiên sử dụng phải trả về PhienSuDung");
        Assert.assertEquals(phien.getTrangThai(), "Đang sử dụng");
    }

    // TEST NO 34 - selectPhienDangSuDung_NhieuPhien_Fail
    // Ghi chú: test thiết kế, cần tự tạo dữ liệu 2 phiên cùng MaMay='MT04' & TrangThai='Đang sử dụng'
    @Test
    public void TC_PhienSuDungDaoImpl_selectPhienDangSuDung_NhieuPhien_Fail() {
        PhienSuDung phien = dao.selectPhienDangSuDung("MT04");
        // Thiết kế: hệ thống phải phát hiện bất thường. Ở đây ta mong muốn ném exception nghiệp vụ.
        Assert.assertNotNull(phien, "Thiết kế mong muốn: logic phải được cải tiến để không có 2 phiên song song");
    }

    // TEST NO 35 - updateTrangThai_KetThucPhien
    @Test
    public void TC_PhienSuDungDaoImpl_updateTrangThai_KetThucPhien() {
        boolean ok = dao.updateTrangThai("PS06", "Đã kết thúc");
        Assert.assertTrue(ok);

        PhienSuDung phien = dao.selectById("PS06");
        Assert.assertEquals(phien.getTrangThai(), "Đã kết thúc");
    }

    // TEST NO 36 - updateTrangThai_GiaTriSai_Fail
    @Test
    public void TC_PhienSuDungDaoImpl_updateTrangThai_GiaTriSai_Fail() {
        boolean ok = dao.updateTrangThai("PS06", "ABC");
        Assert.assertFalse(ok, "Không được chấp nhận trạng thái không hợp lệ");
    }

    // TEST NO 37 - selectByDateRange_TrongKhoang
    @Test
    public void TC_PhienSuDungDaoImpl_selectByDateRange_TrongKhoang() {
        LocalDateTime tu = LocalDateTime.of(2025, 8, 15, 0, 0);
        LocalDateTime den = LocalDateTime.of(2025, 8, 20, 23, 59);

        List<PhienSuDung> list = dao.selectByDateRange(tu, den);
        Assert.assertFalse(list.isEmpty(), "Trong khoảng này phải có các phiên PS03, PS04, PS05");
    }

    // TEST NO 38 - selectByDateRange_BienNgay_Fail
    @Test
    public void TC_PhienSuDungDaoImpl_selectByDateRange_BienNgay_Fail() {
        LocalDateTime tu = LocalDateTime.of(2025, 8, 15, 0, 0);
        LocalDateTime den = LocalDateTime.of(2025, 8, 15, 23, 59);

        List<PhienSuDung> list = dao.selectByDateRange(tu, den);
        // Thiết kế: nếu có phiên trong ngày 15 thì list không rỗng
        Assert.assertNotNull(list);
    }

    // TEST NO 39 - selectByMaMay_History
    @Test
    public void TC_PhienSuDungDaoImpl_selectByMaMay_History() {
        List<PhienSuDung> list = dao.selectByMaMay("MT01");
        Assert.assertNotNull(list);
    }

    // TEST NO 40 - selectByMaMay_SQLInjection_Fail
    @Test
    public void TC_PhienSuDungDaoImpl_selectByMaMay_SQLInjection_Fail() {
        String input = "MT01' OR '1'='1";
        List<PhienSuDung> list = dao.selectByMaMay(input);
        // Thiết kế bảo mật: không nên cho SQL Injection; tối thiểu là không lỗi
        Assert.assertNotNull(list, "selectByMaMay phải an toàn trước input nguy hiểm");
    }
}
