package ql.net.dao.impl;

import org.testng.Assert;
import org.testng.annotations.Test;
import ql.net.entity.NhanVien;

import java.util.List;

/**
 * Unit test cho NhanVienDaoImpl
 * Lưu ý chung:
 *  - Trước khi chạy test, hãy chạy lại script CSDL Quan_Ly_Tiem_NET.sql
 *    để dữ liệu về đúng trạng thái mô tả trong TEST DATA.
 */
public class NhanVienDaoImplTest {

    private final NhanVienDaoImpl dao = new NhanVienDaoImpl();

    /**
     * TEST NO 1 - TC_NhanVienDaoImpl_selectAll_DuLieuMacDinh
     * TEST DATA: CSDL theo script, bảng NhanVien có NV01, NV02, TrangThai = 1
     * STEPS: 1. Khởi tạo NhanVienDaoImpl; 2. Gọi selectAll()
     * EXPECTED: Trả về danh sách 2 nhân viên NV01, NV02, không ném ngoại lệ
     * STATUS: Pass
     */
    @Test
    public void TC_NhanVienDaoImpl_selectAll_DuLieuMacDinh() {
        List<NhanVien> list = dao.selectAll();
        // Kiểm tra kích thước danh sách >= 2
        Assert.assertTrue(list.size() >= 2, "Danh sách nhân viên phải có ít nhất 2 bản ghi");

        // Kiểm tra có NV01 và NV02
        boolean hasNV01 = list.stream().anyMatch(nv -> "NV01".equals(nv.getMaNV()));
        boolean hasNV02 = list.stream().anyMatch(nv -> "NV02".equals(nv.getMaNV()));
        Assert.assertTrue(hasNV01 && hasNV02, "Danh sách phải chứa NV01 và NV02");
    }

    /**
     * TEST NO 2 - TC_NhanVienDaoImpl_selectAll_BangRong_Fail
     * TEST DATA: Xóa hết dữ liệu bảng NhanVien (DELETE FROM NhanVien)
     *  (Cần chuẩn bị DB HAND-MADE: xóa dữ liệu + xử lý FK nếu có)
     * STEPS: 1. Khởi tạo NhanVienDaoImpl; 2. Gọi selectAll()
     * EXPECTED: Trả về danh sách rỗng (size = 0), không lỗi
     * STATUS dự kiến hiện tại: Fail (code đang ném SQLException)
     *
     * Unit test này assert KẾT QUẢ ĐÚNG. Khi chạy với code hiện tại,
     * test sẽ FAIL để thể hiện bug như tài liệu.
     */
    @Test
    public void TC_NhanVienDaoImpl_selectAll_BangRong_Fail() {
        List<NhanVien> list = dao.selectAll();
        Assert.assertEquals(list.size(), 0, "Khi bảng rỗng phải trả về danh sách size = 0");
    }

    /**
     * TEST NO 3 - TC_NhanVienDaoImpl_selectById_TonTai
     * TEST DATA: CSDL mặc định, tồn tại NV01
     * STEPS: 1. Khởi tạo NhanVienDaoImpl; 2. Gọi selectById("NV01")
     * EXPECTED: Trả về đối tượng NhanVien có MaNV = 'NV01'
     * STATUS: Pass
     */
    @Test
    public void TC_NhanVienDaoImpl_selectById_TonTai() {
        NhanVien nv = dao.selectById("NV01");
        Assert.assertNotNull(nv, "Phải tìm được NV01");
        Assert.assertEquals(nv.getMaNV(), "NV01");
    }

    /**
     * TEST NO 4 - TC_NhanVienDaoImpl_selectById_KhongTonTai_Fail
     * TEST DATA: Không có NV99
     * STEPS: 1. Khởi tạo NhanVienDaoImpl; 2. Gọi selectById("NV99")
     * EXPECTED: Trả về null, không ném ngoại lệ
     * STATUS dự kiến hiện tại: Fail (code đang quấn SQLException thành RuntimeException)
     */
    @Test
    public void TC_NhanVienDaoImpl_selectById_KhongTonTai_Fail() {
        NhanVien nv = dao.selectById("NV99");
        Assert.assertNull(nv, "Khi mã NV không tồn tại phải trả về null");
    }

    /**
     * TEST NO 5 - TC_NhanVienDaoImpl_login_ThongTinDung
     * TEST DATA: TenDangNhap='hieu', MatKhau='123', TrangThai=1
     * STEPS: 1. Khởi tạo NhanVienDaoImpl; 2. Gọi login("hieu","123")
     * EXPECTED: Trả về đối tượng NhanVien NV01, không lỗi
     * STATUS: Pass
     */
    @Test
    public void TC_NhanVienDaoImpl_login_ThongTinDung() {
        NhanVien nv = dao.login("hieu", "123");
        Assert.assertNotNull(nv, "Đăng nhập đúng phải trả về NhanVien");
        Assert.assertEquals(nv.getMaNV(), "NV01");
    }

    /**
     * TEST NO 6 - TC_NhanVienDaoImpl_login_SaiMatKhau_Fail
     * TEST DATA: tenDangNhap='hieu', matKhau='999'
     * STEPS: 1. Khởi tạo NhanVienDaoImpl; 2. Gọi login("hieu","999")
     * EXPECTED: Trả về null (đăng nhập thất bại), không lỗi
     * STATUS dự kiến hiện tại: Fail (code vẫn trả về NhanVien)
     */
    @Test
    public void TC_NhanVienDaoImpl_login_SaiMatKhau_Fail() {
        NhanVien nv = dao.login("hieu", "999");
        Assert.assertNull(nv, "Mật khẩu sai phải trả về null (đăng nhập thất bại)");
    }

    /**
     * TEST NO 7 - TC_NhanVienDaoImpl_insert_DuLieuHopLe
     * TEST DATA: MaNV='NV03', TenDangNhap='test', MatKhau='123', ...
     * STEPS:
     *  1. Tạo entity NV03
     *  2. Gọi insert(entity)
     *  3. Gọi selectById("NV03") kiểm tra trong DB
     * EXPECTED: insert trả true, trong DB có NV03 đúng dữ liệu
     * STATUS: Pass
     */
    @Test
    public void TC_NhanVienDaoImpl_insert_DuLieuHopLe() {
        NhanVien nv = NhanVien.builder()
                .maNV("NV03")
                .tenDangNhap("test")
                .matKhau("123")
                .hoTen("Test User")
                .soDT("0123456789")
                .chucVu("Nhân viên")
                .image("nv3.jpg")
                .build();

        boolean result = dao.insert(nv);
        Assert.assertTrue(result, "Insert NV03 phải trả về true");

        NhanVien fromDb = dao.selectById("NV03");
        Assert.assertNotNull(fromDb, "Sau insert phải đọc được NV03 từ DB");
        Assert.assertEquals(fromDb.getHoTen(), "Test User");
    }

    /**
     * TEST NO 8 - TC_NhanVienDaoImpl_insert_TrungKhoaChinh_Fail
     * TEST DATA: Dùng lại MaNV='NV01' đã tồn tại
     * STEPS:
     *  1. Tạo entity có MaNV='NV01'
     *  2. Gọi insert(entity)
     * EXPECTED: Hàm trả false hoặc ném ngoại lệ nghiệp vụ rõ ràng
     * STATUS dự kiến hiện tại: Fail (ném RuntimeException từ SQLException)
     */
    @Test
    public void TC_NhanVienDaoImpl_insert_TrungKhoaChinh_Fail() {
        NhanVien nv = NhanVien.builder()
                .maNV("NV01")
                .tenDangNhap("any")
                .matKhau("123")
                .hoTen("Trung Khoa")
                .soDT("0000000000")
                .chucVu("Nhân viên")
                .image("any.jpg")
                .build();

        boolean ok;
        try {
            ok = dao.insert(nv);
            // Thiết kế mong muốn: không được insert thành công
            Assert.assertFalse(ok, "Insert trùng MaNV phải trả về false");
        } catch (RuntimeException ex) {
            // Với code hiện tại, sẽ vào nhánh này -> test này vẫn thể hiện bug
            Assert.fail("Thiết kế mong muốn xử lý mềm, không ném RuntimeException thô");
        }
    }

    /**
     * TEST NO 9 - TC_NhanVienDaoImpl_changePassword_DungMatKhauCu
     * TEST DATA: NV01 có MatKhau='123'
     * STEPS:
     *  1. Gọi changePassword("NV01","123","456")
     *  2. Đọc lại NV01
     * EXPECTED: changePassword trả true, mật khẩu đổi thành '456'
     * STATUS: Pass
     */
    @Test
    public void TC_NhanVienDaoImpl_changePassword_DungMatKhauCu() {
        boolean ok = dao.changePassword("NV01", "123", "456");
        Assert.assertTrue(ok, "Đổi mật khẩu với mật khẩu cũ đúng phải trả true");

        NhanVien nv = dao.selectById("NV01");
        Assert.assertEquals(nv.getMatKhau(), "456");
    }

    /**
     * TEST NO 10 - TC_NhanVienDaoImpl_changePassword_SaiMatKhauCu_Fail
     * TEST DATA: NV01 mật khẩu hiện tại '456' (sau test 9)
     * STEPS:
     *  1. Gọi changePassword("NV01","999","789")
     *  2. Đọc lại NV01
     * EXPECTED: changePassword trả false, mật khẩu vẫn là '456'
     * STATUS dự kiến hiện tại: Fail (hàm có thể trả true hoặc vẫn đổi mật khẩu)
     */
    @Test
    public void TC_NhanVienDaoImpl_changePassword_SaiMatKhauCu_Fail() {
        boolean ok = dao.changePassword("NV01", "999", "789");
        Assert.assertFalse(ok, "Mật khẩu cũ sai => phải trả false");

        NhanVien nv = dao.selectById("NV01");
        Assert.assertEquals(nv.getMatKhau(), "456",
                "Mật khẩu NV01 không được đổi khi mật khẩu cũ sai");
    }
}
