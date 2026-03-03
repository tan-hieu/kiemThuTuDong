package ql.net.dao.impl;

import org.testng.Assert;
import org.testng.annotations.Test;
import ql.net.entity.DichVu;

import java.util.List;

/**
 * Unit test cho DichVuDaoImpl
 * Bao phủ TEST NO 21–30.
 */
public class DichVuDaoImplTest {

    private final DichVuDaoImpl dao = new DichVuDaoImpl();

    // TEST NO 21 - selectByLoai_DoUong
    @Test
    public void TC_DichVuDaoImpl_selectByLoai_DoUong() {
        List<DichVu> list = dao.selectByLoai("Đồ uống");
        Assert.assertFalse(list.isEmpty(), "Phải có dịch vụ loại Đồ uống");

        boolean allDrink = list.stream().allMatch(dv -> "Đồ uống".equals(dv.getLoaiDV()));
        Assert.assertTrue(allDrink, "Tất cả phải có LoaiDV = 'Đồ uống'");
    }

    // TEST NO 22 - selectByLoai_SaiChinhTa_Fail
    @Test
    public void TC_DichVuDaoImpl_selectByLoai_SaiChinhTa_Fail() {
        List<DichVu> list = dao.selectByLoai("Do uong");
        // Thiết kế: nên trả về rỗng (vì không khớp chính tả)
        Assert.assertEquals(list.size(), 0, "Sai chính tả loại dịch vụ => không nên tìm thấy gì");
    }

    // TEST NO 23 - selectByTrangThai_DangBan
    @Test
    public void TC_DichVuDaoImpl_selectByTrangThai_DangBan() {
        List<DichVu> list = dao.selectByTrangThai(true);
        Assert.assertFalse(list.isEmpty(), "Phải có dịch vụ đang bán");
        boolean allActive = list.stream().allMatch(DichVu::isTrangThai);
        Assert.assertTrue(allActive);
    }

    // TEST NO 24 - selectByTrangThai_NgungBan_Fail
    // Cần cập nhật trước ít nhất 1 DV TrangThai=false
    @Test
    public void TC_DichVuDaoImpl_selectByTrangThai_NgungBan_Fail() {
        List<DichVu> list = dao.selectByTrangThai(false);
        // Thiết kế: nếu đã có DV ngưng bán => list.size() >= 1
        // ở đây giả sử chưa có => mong đợi rỗng.
        Assert.assertEquals(list.size(), 0, "Nếu chưa đánh dấu DV nào ngưng bán thì phải rỗng");
    }

    // TEST NO 25 - searchByTen_Tim1Phan
    @Test
    public void TC_DichVuDaoImpl_searchByTen_Tim1Phan() {
        List<DichVu> list = dao.searchByTen("mì");
        Assert.assertFalse(list.isEmpty(), "Phải tìm được các DV có chữ 'mì'");
        boolean allContains = list.stream().allMatch(dv -> dv.getTenDV().toLowerCase().contains("mì"));
        Assert.assertTrue(allContains);
    }

    // TEST NO 26 - searchByTen_EmptyString_Fail
    @Test
    public void TC_DichVuDaoImpl_searchByTen_EmptyString_Fail() {
        List<DichVu> list = dao.searchByTen("");
        // Thiết kế: hoặc trả về tất cả DV, hoặc rỗng; tuyệt đối không được lỗi
        Assert.assertNotNull(list, "searchByTen(\"\") không được trả null");
    }

    // TEST NO 27 - updateSoLuongTon_GiamSauBan
    @Test
    public void TC_DichVuDaoImpl_updateSoLuongTon_GiamSauBan() {
        DichVu dv = dao.selectById("DV01");
        int soLuongBanDau = dv.getSoLuongTon();
        int soLuongMoi = soLuongBanDau - 5;

        boolean ok = dao.updateSoLuongTon("DV01", soLuongMoi);
        Assert.assertTrue(ok);

        int soLuongSau = dao.selectById("DV01").getSoLuongTon();
        Assert.assertEquals(soLuongSau, soLuongMoi);
    }

    // TEST NO 28 - updateSoLuongTon_AmSoLuong_Fail
    @Test
    public void TC_DichVuDaoImpl_updateSoLuongTon_AmSoLuong_Fail() {
        boolean ok = dao.updateSoLuongTon("DV01", -1);
        Assert.assertFalse(ok, "Không được cho phép tồn kho âm");
    }

    // TEST NO 29 - selectByMaMay_MayDangSuDung
    @Test
    public void TC_DichVuDaoImpl_selectByMaMay_MayDangSuDung() {
        // Máy MT04 đang có PS06 'Đang sử dụng' theo script
        List<DichVu> list = dao.selectByMaMay("MT04");
        // Với dữ liệu mẫu của anh có thể rỗng hoặc có DV, ở đây chỉ check không lỗi:
        Assert.assertNotNull(list, "selectByMaMay không được trả về null");
    }

    // TEST NO 30 - selectByMaMay_KhongCoPhien_Fail
    @Test
    public void TC_DichVuDaoImpl_selectByMaMay_KhongCoPhien_Fail() {
        List<DichVu> list = dao.selectByMaMay("MT99");
        // Thiết kế: không có phiên => danh sách rỗng
        Assert.assertEquals(list.size(), 0, "Máy không tồn tại / không có phiên => list rỗng");
    }
}
