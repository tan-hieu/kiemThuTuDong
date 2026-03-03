package ql.net.dao.impl;

import org.testng.Assert;
import org.testng.annotations.Test;
import ql.net.entity.KhachHang;

import java.math.BigDecimal;
import java.util.List;

/**
 * Unit test cho KhachHangDaoImpl
 * Gắn từng test với TEST NO 11–20 trong bảng testcase.
 */
public class KhachHangDaoImplTest {

    private final KhachHangDaoImpl dao = new KhachHangDaoImpl();

    // TEST NO 11 - TC_KhachHangDaoImpl_selectAll_DuLieuMacDinh
    @Test
    public void TC_KhachHangDaoImpl_selectAll_DuLieuMacDinh() {
        List<KhachHang> list = dao.selectAll();
        Assert.assertTrue(list.size() >= 2, "Ít nhất phải có KH01, KH02");

        boolean hasKH01 = list.stream().anyMatch(kh -> "KH01".equals(kh.getMaKH()));
        boolean hasKH02 = list.stream().anyMatch(kh -> "KH02".equals(kh.getMaKH()));
        Assert.assertTrue(hasKH01 && hasKH02, "Danh sách phải chứa KH01 và KH02");
    }

    // TEST NO 12 - TC_KhachHangDaoImpl_selectAll_SoLieuLon_Fail
    // Ghi chú: cần tự chèn >1000 KH test trước khi chạy
    @Test
    public void TC_KhachHangDaoImpl_selectAll_SoLieuLon_Fail() {
        List<KhachHang> list = dao.selectAll();
        // Thiết kế: trả về đầy đủ, thời gian chấp nhận được.
        // Ở đây chỉ check kích thước tương đối (>= 1000) nếu anh đã chuẩn bị dữ liệu.
        Assert.assertTrue(list.size() >= 1000,
                "Khi đã chèn nhiều dữ liệu, selectAll phải trả đủ >= 1000 bản ghi");
    }

    // TEST NO 13 - TC_KhachHangDaoImpl_selectById_TonTai
    @Test
    public void TC_KhachHangDaoImpl_selectById_TonTai() {
        KhachHang kh = dao.selectById("KH01");
        Assert.assertNotNull(kh);
        Assert.assertEquals(kh.getMaKH(), "KH01");
        Assert.assertEquals(kh.getSoDuTaiKhoan(), new BigDecimal("120040.00"));
    }

    // TEST NO 14 - TC_KhachHangDaoImpl_selectById_Null_Fail
    @Test
    public void TC_KhachHangDaoImpl_selectById_Null_Fail() {
        try {
            KhachHang kh = dao.selectById(null);
            // Thiết kế: hoặc trả về null, hoặc ném IllegalArgumentException
            // Ở đây ta chấp nhận null là OK
            Assert.assertNull(kh, "Input null nên trả về null chứ không ném lỗi SQL/NullPointer");
        } catch (Exception ex) {
            Assert.fail("Không nên ném SQLException/NullPointer cho input null");
        }
    }

    // TEST NO 15 - TC_KhachHangDaoImpl_updateSoDu_CongThemTienDung
    @Test
    public void TC_KhachHangDaoImpl_updateSoDu_CongThemTienDung() {
        BigDecimal soDuBanDau = dao.selectById("KH01").getSoDuTaiKhoan();
        BigDecimal congThem = new BigDecimal("20000");

        boolean ok = dao.updateSoDu("KH01", congThem);
        Assert.assertTrue(ok, "updateSoDu phải trả về true");

        BigDecimal soDuSau = dao.selectById("KH01").getSoDuTaiKhoan();
        Assert.assertEquals(soDuSau, soDuBanDau.add(congThem));
    }

    // TEST NO 16 - TC_KhachHangDaoImpl_updateSoDu_SoTienAm_Fail
    @Test
    public void TC_KhachHangDaoImpl_updateSoDu_SoTienAm_Fail() {
        BigDecimal soDuBanDau = dao.selectById("KH01").getSoDuTaiKhoan();
        BigDecimal truQuaLon = new BigDecimal("-9999999999.00");

        boolean ok = dao.updateSoDu("KH01", truQuaLon);
        // Thiết kế: phải từ chối, trả false
        Assert.assertFalse(ok, "Không được cho phép trừ tiền âm quá lớn gây âm số dư");

        BigDecimal soDuSau = dao.selectById("KH01").getSoDuTaiKhoan();
        Assert.assertEquals(soDuSau, soDuBanDau,
                "Số dư không được thay đổi khi input không hợp lệ");
    }

    // TEST NO 17 - TC_KhachHangDaoImpl_selectByTrangThai_DangHoatDong
    @Test
    public void TC_KhachHangDaoImpl_selectByTrangThai_DangHoatDong() {
        List<KhachHang> list = dao.selectByTrangThai(true);
        Assert.assertFalse(list.isEmpty(), "Phải có KH đang hoạt động");

        boolean allActive = list.stream().allMatch(KhachHang::isTrangThai);
        Assert.assertTrue(allActive, "Tất cả khách phải có trangThai = true");
    }

    // TEST NO 18 - TC_KhachHangDaoImpl_selectByTrangThai_False_Fail
    @Test
    public void TC_KhachHangDaoImpl_selectByTrangThai_False_Fail() {
        List<KhachHang> list = dao.selectByTrangThai(false);
        Assert.assertEquals(list.size(), 0, "Mặc định không có khách nào ngưng hoạt động");
    }

    // TEST NO 19 - TC_KhachHangDaoImpl_selectByKeyword_TimTheoTen
    // Lưu ý: sử dụng phương thức selectByKeyword đang có trong impl,
    // dù interface chưa khai báo – test theo đúng hiện trạng code.
    @Test
    public void TC_KhachHangDaoImpl_selectByKeyword_TimTheoTen() {
        List<KhachHang> list = dao.selectByKeyword("Nguyễn");
        Assert.assertFalse(list.isEmpty(), "Phải tìm được KH họ Nguyễn");

        boolean allMatch = list.stream().allMatch(kh -> kh.getHoTen().contains("Nguyễn"));
        Assert.assertTrue(allMatch, "Tất cả kết quả phải chứa 'Nguyễn' trong họ tên");
    }

    // TEST NO 20 - TC_KhachHangDaoImpl_selectByKeyword_KhongTonTai_Fail
    @Test
    public void TC_KhachHangDaoImpl_selectByKeyword_KhongTonTai_Fail() {
        List<KhachHang> list = dao.selectByKeyword("ABCXYZ");
        Assert.assertEquals(list.size(), 0, "Không KH nào khớp => danh sách rỗng");
    }
}
