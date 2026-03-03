/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ql.net.ui;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import static java.awt.Font.BOLD;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import static javax.swing.text.StyleConstants.Bold;
import ql.net.dao.BaoTriDao;
import ql.net.dao.KhachHangDao;
import ql.net.dao.LichSuGiaoDichDao;
import ql.net.dao.NapTienDao;
import ql.net.entity.KhachHang;
import ql.net.dao.impl.ThanhVienVipDaoImpl;
import ql.net.entity.MayTinh;
import ql.net.entity.NhapHang;
import ql.net.entity.PhienSuDung;
import ql.net.util.XJdbc;

import ql.net.dao.ThongKeDao;
import ql.net.dao.impl.BaoTriDaoImpl;
import ql.net.dao.impl.KhachHangDaoImpl;
import ql.net.dao.impl.LichSuGiaoDichDaoImpl;
import ql.net.dao.impl.NapTienDaoImpl;
import ql.net.dao.impl.NhanVienDaoImpl;
import ql.net.dao.impl.ThongKeDaoImpl;
import ql.net.entity.BaoTri;
import ql.net.entity.DichVu;
import ql.net.entity.HoaDon;
import ql.net.entity.LichSuGiaoDich;
import ql.net.entity.NapTien;
import ql.net.entity.NhanVien;
import ql.net.entity.ThanhVienVIP;
import ql.net.entity.ChiTietHoaDon;

/**
 *
 * @author ADMIN
 */
public class HomeJFrame extends javax.swing.JFrame {
    private java.awt.CardLayout cardLayout;
    private java.awt.CardLayout cardLayoutTabs;
    private ql.net.dao.MayTinhDao mayTinhDao = new ql.net.dao.impl.MayTinhDaoImpl();
    private boolean loadedTieuChuan = false;
    private boolean loadedGaming = false;
    private boolean loadedChuyenNghiep = false;
    private boolean loadedThiDau = false;
    private ql.net.entity.NhanVien curentUser;
    private javax.swing.JSpinner spnTCTDBatDau;
    private ql.net.entity.KhachHang selectedMaKH;
    private String fileAnhMoi = null;
    private JLabel lblThoiGianSuDung;
    private Timer timer;
    private int seconds = 0;
    private final Map<String, javax.swing.Timer> mayTimers = new HashMap<>();
    private Map<String, LocalDateTime> mayStartTimes = new HashMap<>();
    private String selectMaMay;
    private final Map<String, JPanel> mapPanelMay = new HashMap<>();
    private final Map<String, JTextField> mapTxtThoiGian = new HashMap<>();
    private final Map<String, JTextField> mapTxtTamTinh = new HashMap<>();
    private final Map<String, JTextField> mapTxtTrangThai = new HashMap<>();
    private String maMay;
    private ql.net.entity.NhanVien currentUser;
    private javax.swing.Timer timerThoiGianSuDung;
    private JTextField currentTxtThoiGianSuDung;
    private javax.swing.JPanel pnlThongTinBaoTri;
    private javax.swing.JTextField txtTenMayBaoTri;
    private javax.swing.JTextField txtTrangThaiBaoTri;
    
    private final Map<String, Timer> timerMap = new HashMap<>();
    private final Map<String, JTextField> txtThoiGianSuDungMap = new HashMap<>();
    
    private String pendingBatMayMaMay = null; // Mã máy đang chờ bật
    private JTextField pendingTxtTrangThai;
    private JDateChooser pendingTxtThoiDiemBatDau;
    private JTextField pendingTxtThoiGianSuDung;
    private JTextField pendingTxtTamTinh;
    private JPanel pendingPnlMay;
    
    private ThongKeDao thongKeDao;
    private ql.net.dao.ThanhVienVipDao thanhVienVipDao = new ql.net.dao.impl.ThanhVienVipDaoImpl();
    private ql.net.dao.DichVuDao dichVuDao;
    private ql.net.dao.NhanVienDao nhanVienDao;
    private ql.net.dao.HoaDonDao hoaDonDao;
    private ql.net.dao.BaoTriDao baoTriDao;
    private ql.net.dao.NapTienDao napTienDao;
    private ql.net.dao.LichSuGiaoDichDao lichSuGiaoDichDao;
    private ql.net.dao.PhienSuDungDao phienSuDungDao = new ql.net.dao.impl.PhienSuDungDaoImpl();
    private KhachHang khachHang;
    private javax.swing.JTabbedPane tabbedPaneBaoCao;

    public KhachHang getKhachHang() { 
        return khachHang; 
    }

    public void setKhachHang(KhachHang khachHang) { 
        this.khachHang = khachHang; 
    }
    
    private javax.swing.JLabel lblTenMayBaoTri;
//    private javax.swing.JPanel pnlThongTinBaoTri;
    private javax.swing.JComboBox<String> cboBoPhanBaoTri;
//    private javax.swing.JTextField txtMoTaBaoTri;
    private javax.swing.JTable tblDanhSachBaoTri;
    
    private String currentActiveTab = "tieuChuan";//mặc định là tiêu chuẩn
    private String getCurrentActiveTab(){
        return this.currentActiveTab;
    }
    private void setCurrentActiveTab(String tabName) {
        this.currentActiveTab = tabName;
        System.out.println("Đã chuyển sang tab: " + tabName);
    }

    /**
     * Creates new form QLTNETJFrame
     */
    public HomeJFrame(ql.net.entity.NhanVien nv) {
        this.curentUser = nv;
        initComponents();
        
        cardLayoutTabs = (CardLayout) pnlTabContent.getLayout();
        pnlGridMayBaoTri.setLayout(new java.awt.GridLayout(0, 6, 10, 10));
        setLocationRelativeTo(null);
        setupCardLayout();
        setupRoleUI();
        //Khởi tạo cardlayout
        cardLayout = (CardLayout) pnl1.getLayout();
        cardLayoutTabs = (CardLayout) pnlTabContent.getLayout();
        // Hiển thị trang chào mừng đầu tiên
        cardLayout.show(pnl1, "gioiThieu");

        // Khởi tạo tab máy và hiển thị tab đầu tiên
        setupTabsMay();
//        gọi bảng khách hàng
        loadKhachHangToTable();
        //gọi thành viên vip
        loadVIPToTable();
//        hiển thị tên lên lbl
        lblNhanVien.setText(curentUser.getHoTen());
        //load dữ liệu trong dịch vụ
        loadDoAnToTable();
        loadDoUongToTable();
        loadDichVuKhac();
        tabDichVu.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                if (tabDichVu.getSelectedIndex() == 3) { // tab "Nhập hàng"
                    loadNhapHangToTable();
                }
            }
        });
        
        //load hóa đơn
        loadHoaDonToTable();
        loadLichSuGiaoDichToTable();
        //load nhân viên
        loadNhanVienToTable();
        //
        txtTCTDBatDau.setDateFormatString("dd/MM/yyyy HH:mm");
//        txtTCTGSuDung.setDateFormatString("dd/MM/yyyy HH:mm");
        txtGTDBatDau.setDateFormatString("dd/MM/yyyy HH:mm");
//        txtGTGSuDung.setDateFormatString("dd/MM/yyyy HH:mm");
        txtCNTDBatDau.setDateFormatString("dd/MM/yyyy HH:mm");
//        txtCNTGSuDung.setDateFormatString("dd/MM/yyyy HH:mm");
        txtTDTDBatDau.setDateFormatString("dd/MM/yyyy HH:mm");
//        txtTDTGSuDung.setDateFormatString("dd/MM/yyyy HH:mm");

        
        tblDanhSachBaoTri = new javax.swing.JTable();
        tblDanhSachBaoTri.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {},
                new String [] {
                    "Bộ phận","Mô tả","Ngày bảo trì","Trạng thái"
                }
        ));
//        pnlDSBaoTri.add(tblBaoTri);
        jScrollPane1.setViewportView(tblDanhSachBaoTri);
        
        //thống kê
        thongKeDao = new ThongKeDaoImpl();
        capNhatSoLieuThongKe();
        thanhVienVipDao = new ThanhVienVipDaoImpl();
        
        // Thêm code khởi tạo các DAO để tránh NullPointerException
        this.thanhVienVipDao = new ql.net.dao.impl.ThanhVienVipDaoImpl();
        this.dichVuDao = new ql.net.dao.impl.DichVuDaoImpl();
        this.nhanVienDao = new ql.net.dao.impl.NhanVienDaoImpl();
        this.baoTriDao = new ql.net.dao.impl.BaoTriDaoImpl();
        this.napTienDao = new ql.net.dao.impl.NapTienDaoImpl();
        this.lichSuGiaoDichDao = new ql.net.dao.impl.LichSuGiaoDichDaoImpl();
        this.mayTinhDao = new ql.net.dao.impl.MayTinhDaoImpl();
        this.hoaDonDao = new ql.net.dao.impl.HoaDonDaoImpl();
        
        tabbedPaneBaoCao = new javax.swing.JTabbedPane();
    }
    private void setupRoleUI() {
        if (curentUser.getChucVu().equalsIgnoreCase("Quản lý")) {
            // Hiện tất cả chức năng
            btnQuanLyNhanVien.setVisible(true);
            btnBaoTri.setVisible(true);
            btnBaoCao.setVisible(true);
        } else {
            // Ẩn chức năng chỉ dành cho quản lý
            pnlMenu.remove(btnBaoTri);
            pnlMenu.remove(btnQuanLyNhanVien);
            pnlMenu.remove(btnBaoCao);
            pnlMenu.revalidate();
            pnlMenu.repaint();
        }
        
    }
    
    //chính của chương trình
    public HomeJFrame() {
        initComponents();
        setLocationRelativeTo(null);//in ra giữa màn hình
        setupCardLayout();
        
        // Thêm hiệu ứng hover cho menu
        java.awt.Color normalColor = new java.awt.Color(80, 200, 120);
        java.awt.Color hoverColor = new java.awt.Color(152, 255, 152);
        java.awt.Color activeColor = new java.awt.Color(34, 139, 34);

        javax.swing.JButton[] menuButtons = {
            btnQuanLyMay, btnQuanLyKhach, btnQuanLyDichVu, btnQuanLyHoaDon,
            btnBaoTri, btnBaoCao, btnQuanLyNhanVien, btnCaiDat
        };

        for (javax.swing.JButton btn : menuButtons) {
            btn.setBackground(normalColor);
            btn.setForeground(java.awt.Color.WHITE);
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    if (btn.getBackground().equals(normalColor))
                        btn.setBackground(hoverColor);
                }
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    if (btn.getBackground().equals(hoverColor))
                        btn.setBackground(normalColor);
                }
            });
        }
    }
    
    //Thêm method setup
    private void setupCardLayout(){
        cardLayout = (java.awt.CardLayout) pnl1.getLayout();
        //Hiển thị trang chào mừng đầu tiên
        cardLayout.show(pnl1,"gioiThieu");
        //setup tabs máy
        setupTabsMay();
    }
    
    private void setupTabsMay() {
        try {
            // Hiển thị tab Tiêu chuẩn đầu tiên
            cardLayoutTabs.show(pnlTabContent, "tieuChuan");
            setTabVisibility("tieuChuan");

            // THÊM: Set tab mặc định
            setCurrentActiveTab("tieuChuan");

            // THÊM: Load tất cả loại máy ngay từ đầu
            System.out.println("Loading tất cả loại máy...");
            loadMayFromSQl("Tiêu chuẩn");
            loadMayFromSQl("Gaming");
            loadMayFromSQl("Chuyên nghiệp");
            loadMayFromSQl("Thi đấu");

            // Đánh dấu đã load để tránh load lại
            loadedTieuChuan = true;
            loadedGaming = true;
            loadedChuyenNghiep = true;
            loadedThiDau = true;

            System.out.println("Đã load xong tất cả loại máy");
        } catch (Exception e) {
            System.err.println("Lỗi setup tabs: " + e.getMessage());
            e.printStackTrace();
        }
    }
    //Phương thức để tải máy từ cơ sở dữ liệu theo
    private void loadMayFromSQl(String loaiMay){
        try {
            //xác định panel cần add máy vào
            javax.swing.JPanel targetPanel;
            javax.swing.JPanel pnlThongTin;
            javax.swing.JTextField txtTrangThai,txtTamTinh,txtThoiGian = null;
            com.toedter.calendar.JDateChooser txtThoiDiem;
            switch (loaiMay) {
                case "Tiêu chuẩn":
                    targetPanel = pnlGridMay;
                    pnlThongTin = pnlThongTin1;
                    txtTrangThai = txtTCTrangThai;
                    txtThoiDiem = txtTCTDBatDau;
                    txtThoiGian = txtTCTGSuDung;
                    txtTamTinh = txtTCTamTinh;
                    break;
                case "Gaming":
                    targetPanel = pnlGridMay1;
                    pnlThongTin = pnlThongTin2;
                    txtTrangThai = txtGTrangThai;
                    txtThoiDiem = txtGTDBatDau;
                    txtThoiGian = txtGTGSuDung;
                    txtTamTinh = txtGTamTinh;
                    break;
                case "Chuyên nghiệp":
                    targetPanel = pnlGridMay2;
                    pnlThongTin = pnlThongTin3;
                    txtTrangThai = txtCNTrangThai;
                    txtThoiDiem = txtCNTDBatDau;
                    txtThoiGian = txtCNTGSuDung;
                    txtTamTinh = txtCNTamTinh;
                    break;
                case "Thi đấu":
                    targetPanel = pnlGridMay3;
                    pnlThongTin = pnlThongTin4;
                    txtTrangThai = txtTDTrangThai;
                    txtThoiDiem = txtTDTDBatDau;
                    txtThoiGian = txtTDTGSuDung;
                    txtTamTinh = txtTDTamTinh;
                    break;
                default:
                    targetPanel = pnlGridMay;
                    pnlThongTin = pnlThongTin1;
                    txtTrangThai = txtTCTrangThai;
                    txtThoiDiem = txtTCTDBatDau;
                    txtThoiGian = txtTCTGSuDung;
                    txtTamTinh = txtTCTamTinh;
            }
            //xóa panel cũ
            targetPanel.removeAll();
            //Lấy danh sách máy theo loại
            java.util.List<ql.net.entity.MayTinh> danhSachMay;
            if (loaiMay.equals("all")) {
                danhSachMay = mayTinhDao.selectAll();
            } else {
                danhSachMay = mayTinhDao.selectByLoai(loaiMay);
            }
            
            //thêm panel máy vào đúng panel
            for (ql.net.entity.MayTinh may : danhSachMay) {
                JPanel pnlMay = createPanelMay(
                    may.getMaMay(), 
                    may.getTenMay(), 
                    may.getTrangThai(), 
                    pnlThongTin,
                    txtTrangThai, 
                    txtThoiDiem, 
                    txtThoiGian, 
                    txtTamTinh
                );
                //lưu đúng thời gian mapping máy-txt thời gian
                mapTxtThoiGian.put(may.getMaMay(), txtThoiGian);
                
                targetPanel.add(pnlMay);
            }
            //cập nhật giao diện 
            targetPanel.revalidate();
            targetPanel.repaint();
        } catch (Exception e) {
            System.out.println("Lỗi"+e);
        }
        
    }
    private void createSampleMayPanels() {
        try {
            // Xóa tất cả panels cũ trong grid
            pnlGridMay.removeAll();

        } catch (Exception e) {
            System.err.println("Lỗi tạo panels máy: " + e.getMessage());
        }
    }
    
    // THÊM METHOD createPanelMay() - TẠO 1 PANEL MÁY
    private javax.swing.JPanel createPanelMay(
        String maMay, String tenMay, String trangThai,
        JPanel pnlThongTin,
        JTextField txtTrangThai,
        JDateChooser txtThoiDiem,
        JTextField txtThoiGian,
        JTextField txtTamTinh
    ) {
        javax.swing.JPanel pnlMay = new javax.swing.JPanel();
        pnlMay.setLayout(new java.awt.BorderLayout());
        pnlMay.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLACK, 2));
        pnlMay.setPreferredSize(new java.awt.Dimension(120, 80));
        //lấy toàn bộ máy cho bảo trì
        pnlMay.add(new javax.swing.JLabel(tenMay + " (" + trangThai + ")"));

        //màu khối máy 01.....
        java.awt.Color mauNen;
        switch (trangThai.toLowerCase()) {
            case "đang sử dụng":
                mauNen = new java.awt.Color(173, 216, 230); // Xanh nhạt
                break;
            case "bảo trì":
                mauNen = java.awt.Color.RED; // Đỏ
                break;
            case "trống":
            default:
                mauNen = new java.awt.Color(192, 192, 192); // Xám
        }
        pnlMay.setBackground(mauNen);

        javax.swing.JLabel lblTenMay = new javax.swing.JLabel(tenMay);
        lblTenMay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTenMay.setFont(new java.awt.Font("Segoe UI", 1, 14));
        pnlMay.add(lblTenMay, java.awt.BorderLayout.CENTER);

        javax.swing.JLabel lblTrangThai = new javax.swing.JLabel(trangThai);
        lblTrangThai.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 12));
        pnlMay.add(lblTrangThai, java.awt.BorderLayout.PAGE_END);

        // Dùng maMay làm key
        mapPanelMay.put(maMay, pnlMay);
        mapTxtThoiGian.put(maMay, txtThoiGian);
        mapTxtTamTinh.put(maMay, txtTamTinh);
        mapTxtTrangThai.put(maMay, txtTrangThai);

        pnlMay.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HomeJFrame.this.chonMay(maMay, tenMay, trangThai, pnlThongTin, txtTrangThai, txtThoiDiem, txtThoiGian, txtTamTinh);
            }
        });

        return pnlMay;
    }
    
    // THÊM METHOD chonMay() - XỬ LÝ CLICK MÁY
    private void chonMay(
        String maMay, String tenMay, String trangThai,
        JPanel pnlThongTin,
        JTextField txtTrangThai,
        JDateChooser txtThoiDiemBatDau,
        JTextField txtThoiGianSuDung,
        JTextField txtTamTinh
    ) {
        //lưu máy cũ trước khi gắn máy mới
        String maMayCu = selectMaMay;
        //stop timer máy cũ(nếu có)
        if (maMayCu != null && timerMap.containsKey(maMayCu)) {
            Timer timerCu = timerMap.get(maMayCu);
            if (timerCu != null) {
                timerCu.stop();
            }
        }
        //sau khi dừng mới gắn máy mới
        selectMaMay = maMay;
        //Đổi viền bên phần máy
        pnlThongTin.setBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 100, 100), 2), // màu viền giống lúc khởi tạo
                tenMay,
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", BOLD, 14),
                Color.WHITE
            )
        );
        // Lấy trạng thái máy mới nhất từ DB
        ql.net.entity.MayTinh may = new ql.net.dao.impl.MayTinhDaoImpl().selectById(maMay);
        String trangThaiMoi = may != null ? may.getTrangThai() : trangThai;
        txtTrangThai.setText(trangThaiMoi);
        txtTamTinh.setText((may != null && may.getTamTinh() != null) ? may.getGiaTheoGio().toString() : "");
        if (may != null && may.getGiaTheoGio() != null) {
            txtTamTinh.setText(may.getGiaTheoGio().toString());
        }else{
            txtTamTinh.setText("");
        }
        //nếu đang sử dụng -> lấy phiên,hiển thị và tạo timer riêng
        ql.net.entity.PhienSuDung phien = new ql.net.dao.impl.PhienSuDungDaoImpl().selectPhienDangSuDung(maMay);

        // CHỈ CHẠY TIMER KHI MÁY ĐANG SỬ DỤNG
        if (phien != null && phien.getThoiGianBatDau() != null && "Đang sử dụng".equalsIgnoreCase(trangThaiMoi)) {
            txtThoiDiemBatDau.setDate(java.sql.Timestamp.valueOf(phien.getThoiGianBatDau()));
            //luôn tạo timer mới mới và gắn đúng txt hiện tại(không dùng biến chung)
            Timer timerMoi = new Timer(1000,e ->{
                if (txtThoiGianSuDung != null) {
                    Duration duration = Duration.between(phien.getThoiGianBatDau(), LocalDateTime.now());
                    long hours = duration.toHours();
                    long minutes = duration.toMinutes()%60;
                    long seconds = duration.getSeconds()%60;
                    txtThoiGianSuDung.setText(String.format("%02d:%02d:%02d",hours,minutes,seconds));
                }
            });
            timerMoi.start();
            timerMap.put(maMay, timerMoi);
        } else {
            // máy không sử dụng -> reset view
            txtThoiDiemBatDau.setDate(null);
            txtThoiGianSuDung.setText("00:00:00");
        }

        //Đổi màu trạng thái
        if ("Đang sử dụng".equalsIgnoreCase(trangThaiMoi)) {
            txtTrangThai.setBackground(new Color(173,216,230)); // Xanh nhạt
        } else if ("Bảo trì".equalsIgnoreCase(trangThaiMoi)) {
            txtTrangThai.setBackground(Color.RED); // Đỏ - GIỐNG MÀU PANEL MÁY
        } else if ("Trống".equalsIgnoreCase(trangThaiMoi)) {
            txtTrangThai.setBackground(Color.WHITE); // Trắng
        } else {
            txtTrangThai.setBackground(new Color(192,192,192)); // Xám mặc định
        }
        
        loadFoodForMay(maMay);
        loadTongHoaDonForMay(maMay);
    }
    
    // Sự kiện nút Dịch vụ chung cho tất cả tab máy
    private void xuLyDichVu() {
        System.out.println("=== DEBUG xuLyDichVu ===");
        System.out.println("selectMaMay hiện tại: " + selectMaMay);

        if (selectMaMay == null || selectMaMay.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn máy trước khi gọi dịch vụ!");
            return;
        }

        // Mở ServiceJDialog và truyền mã máy
        ql.net.ui.ServiceJDialog serviceDialog = new ql.net.ui.ServiceJDialog(this,true ,selectMaMay);
        serviceDialog.setLocationRelativeTo(this);
        serviceDialog.setVisible(true);

        System.out.println("Dialog đã đóng, bắt đầu reload food cho máy: " + selectMaMay);
        // Sau khi đóng dialog, cập nhật lại bảng food của máy
        loadFoodForMay(selectMaMay);
        System.out.println("=== END DEBUG xuLyDichVu ===");
    }
    
    private void loadFoodForMay(String maMay){
        System.out.println("=== DEBUG loadFoodForMay ===");
        System.out.println("Mã máy: " + maMay);

        //lấy danh sách dịch vụ food theo máy
        List<ql.net.entity.DichVu> dsFood = new ql.net.dao.impl.DichVuDaoImpl().selectByMaMay(maMay);
        System.out.println("Số lượng dịch vụ tìm thấy: " + dsFood.size());

        DefaultTableModel model = null;
        JTextField txtTongFood = null;

        // SỬA: Xác định bảng theo TAB HIỆN TẠI thay vì theo mã máy
        String currentTab = getCurrentActiveTab();
        System.out.println("Tab hiện tại: " + currentTab);

        switch (currentTab) {
            case "tieuChuan":
                model = (DefaultTableModel) tblTCBang.getModel();
                txtTongFood = txtTCFood;
                System.out.println("Hiển thị ở bảng Tiêu chuẩn");
                break;
            case "gaming":
                model = (DefaultTableModel) tblGBang.getModel();
                txtTongFood = txtGFood;
                System.out.println("Hiển thị ở bảng Gaming");
                break;
            case "chuyenNghiep":
                model = (DefaultTableModel) tblCNBang.getModel();
                txtTongFood = txtCNFood;
                System.out.println("Hiển thị ở bảng Chuyên nghiệp");
                break;
            case "thiDau":
                model = (DefaultTableModel) tblTDBang.getModel();
                txtTongFood = txtTDFood;
                System.out.println("Hiển thị ở bảng Thi đấu");
                break;
            default:
                // Fallback: Dùng bảng TC nếu không xác định được tab
                model = (DefaultTableModel) tblTCBang.getModel();
                txtTongFood = txtTCFood;
                System.out.println("Fallback: Hiển thị ở bảng Tiêu chuẩn");
                break;
        }

        if (model != null) {
            System.out.println("Model không null, bắt đầu xóa và thêm dữ liệu");
            model.setRowCount(0); // Xóa dữ liệu cũ
            BigDecimal tongFood = BigDecimal.ZERO;

            for (int i = 0; i < dsFood.size(); i++) {
                ql.net.entity.DichVu dv = dsFood.get(i);
                System.out.println("Thêm dòng " + (i+1) + ": " + dv.getTenDV() + " - SL: " + dv.getSoLuong() + " - Tiền: " + dv.getThanhTien());

                model.addRow(new Object[]{
                    dv.getTenDV(),
                    dv.getDonGia(),
                    dv.getSoLuong(),
                    dv.getThanhTien()
                });
                tongFood = tongFood.add(dv.getThanhTien());
            }

            System.out.println("Tổng food: " + tongFood);
            if (txtTongFood != null) {
                txtTongFood.setText(tongFood.toString());
            }
            System.out.println("Số dòng trong model sau khi thêm: " + model.getRowCount());
        } else {
            System.out.println("Model vẫn NULL sau khi sửa!");
        }
        System.out.println("=== END DEBUG loadFoodForMay ===");
    }
    
    //tạo method mở clientJdialog
    private void chonKhachHangDeBatMay(String maMay, JTextField txtTrangThai,JDateChooser txtThoiDiemBatDau,JTextField txtThoiGianSuDung,
                                        JTextField txtTamTinh, JPanel pnlMay){
        //lưu thông tin máy chờ bật
        pendingBatMayMaMay =maMay;
        pendingTxtTrangThai = txtTrangThai;
        pendingTxtThoiDiemBatDau = txtThoiDiemBatDau;
        pendingTxtThoiGianSuDung = txtThoiGianSuDung;
        pendingTxtTamTinh = txtTamTinh;
        pendingPnlMay = pnlMay;
        //mở clinet để chọn khách hàng
        ql.net.ui.ClinetJDialog clientJDialog = new ql.net.ui.ClinetJDialog(this, true);
        clientJDialog.setLocationRelativeTo(this);
        clientJDialog.setVisible(true);
        //sau khi đóng  dialog ,kiểm tra xem có khách hàng được chọn không
        String maKH = clientJDialog.getSelectedMaKH();
        if (maKH != null && !maKH.isEmpty()) {
            //Thực hiện bật máy với khách hàng đã chọn
            batMaySauKhiChonKH(maKH);
        }
        //Reset trạng thái 
        resetPendingBatMay();
    }
    private void resetPendingBatMay(){
        pendingBatMayMaMay = null;
        pendingTxtTrangThai = null;
        pendingTxtThoiDiemBatDau = null;
        pendingTxtThoiGianSuDung = null;
        pendingTxtTamTinh = null;
        pendingPnlMay = null;
    }
    private void batMaySauKhiChonKH(String maKH) {
        System.out.println("=== DEBUG batMaySauKhiChonKH ===");
        System.out.println("Mã KH nhận được: " + maKH);
        System.out.println("Pending máy: " + pendingBatMayMaMay);

        if (pendingBatMayMaMay != null) {
            try {
                // 1. Lấy thông tin máy và validate
                ql.net.entity.MayTinh may = new ql.net.dao.impl.MayTinhDaoImpl().selectById(pendingBatMayMaMay);
                if (may == null) {
                    JOptionPane.showMessageDialog(this, "❌ Không tìm thấy máy với mã: " + pendingBatMayMaMay);
                    resetPendingState();
                    return;
                }

                if (!may.getTrangThai().equalsIgnoreCase("Trống")) {
                    JOptionPane.showMessageDialog(this, "❌ Máy " + may.getTenMay() + " đang " + may.getTrangThai() + "!");
                    resetPendingState();
                    return;
                }

                // 2. Lấy thông tin khách hàng
                ql.net.entity.KhachHang khachHang = new ql.net.dao.impl.KhachHangDaoImpl().selectById(maKH);
                if (khachHang == null) {
                    JOptionPane.showMessageDialog(this, "❌ Không tìm thấy thông tin khách hàng!");
                    resetPendingState();
                    return;
                }

                // 3. Tạo mã phiên tự động
                String maPhien = generateMaPhienSuDung();
                System.out.println("Mã phiên được tạo: " + maPhien);

                // 4. Tạo phiên sử dụng mới
                ql.net.entity.PhienSuDung phien = ql.net.entity.PhienSuDung.builder()
                    .maPhien(maPhien)
                    .maMay(pendingBatMayMaMay)
                    .maKH(maKH)
                    .maNV("NV01")// Lấy từ session đăng nhập
                    .thoiGianBatDau(java.time.LocalDateTime.now())
                    .soGioSuDung(java.math.BigDecimal.ZERO)
                    .tongTien(java.math.BigDecimal.ZERO)
                    .trangThai("Đang sử dụng")
                    .build();

                System.out.println("Tạo phiên sử dụng: " + phien);

                // 5. Insert phiên sử dụng vào database
                boolean okPhien = new ql.net.dao.impl.PhienSuDungDaoImpl().insert(phien);
                if (!okPhien) {
                    JOptionPane.showMessageDialog(this, "❌ Lỗi tạo phiên sử dụng!");
                    resetPendingState();
                    return;
                }
                System.out.println("✅ Tạo phiên sử dụng thành công");

                // 6. Tạo hóa đơn ngay khi bật máy (để tránh lỗi khi thanh toán)
                String maHD = taoHoaDonKhiBatMay(phien);
                if (maHD == null) {
                    // Rollback: Nếu tạo hóa đơn thất bại, xóa phiên vừa tạo
                    new ql.net.dao.impl.PhienSuDungDaoImpl().delete(maPhien);
                    JOptionPane.showMessageDialog(this, "❌ Lỗi tạo hóa đơn! Không thể bật máy.");
                    resetPendingState();
                    return;
                }
                System.out.println("✅ Tạo hóa đơn thành công: " + maHD);

                // 7. Cập nhật trạng thái máy thành "Đang sử dụng"
                String updateMay = "UPDATE MayTinh SET TrangThai = N'Đang sử dụng' WHERE MaMay = ?";
                boolean okUpdateMay = ql.net.util.XJdbc.executeUpdate(updateMay, pendingBatMayMaMay) > 0;
                if (!okUpdateMay) {
                    JOptionPane.showMessageDialog(this, "❌ Lỗi cập nhật trạng thái máy!");
                    resetPendingState();
                    return;
                }
                System.out.println("✅ Cập nhật trạng thái máy thành công");

                // 8. Kiểm tra khách hàng có VIP không
                String loaiVIP = kiemTraVIP(maKH);
                String thongTinVIP = "";
                if (loaiVIP != null && !loaiVIP.trim().isEmpty()) {
                    thongTinVIP = "\n🌟 Khách hàng VIP " + loaiVIP + " - Được giảm giá khi thanh toán!";
                }

                // 9. Hiển thị thông báo thành công
                JOptionPane.showMessageDialog(this, 
                    String.format("✅ ĐÃ BẬT MÁY THÀNH CÔNG!\n\n" +
                                 "🖥️ Máy: %s (%s)\n" +
                                 "👤 Khách hàng: %s - %s\n" +
                                 "⏰ Thời gian bắt đầu: %s\n" +
                                 "📋 Mã phiên: %s\n" +
                                 "🧾 Mã hóa đơn: %s%s", 
                        may.getTenMay(), 
                        may.getLoaiMay(),
                        maKH, 
                        khachHang.getHoTen(),
                        java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                        maPhien, 
                        maHD,
                        thongTinVIP),
                    "Bật máy thành công", 
                    JOptionPane.INFORMATION_MESSAGE);

                // 10. Refresh giao diện để cập nhật màu máy và trạng thái
                loadMayFromSQl(may.getLoaiMay());

                // 11. Tự động chọn máy vừa bật để hiển thị thông tin
                tuChonMayVuaBat(pendingBatMayMaMay, may.getTenMay(), may.getLoaiMay());

                // 12. Reset pending state
                resetPendingState();

                System.out.println("✅ Hoàn thành bật máy!");

            } catch (Exception e) {
                System.err.println("❌ Lỗi batMaySauKhiChonKH: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "❌ Lỗi bật máy: " + e.getMessage());
                resetPendingState();
            }
        } else {
            System.out.println("❌ Không có máy pending để bật");
            JOptionPane.showMessageDialog(this, "❌ Không có máy nào được chọn để bật!");
        }

        System.out.println("=== END DEBUG batMaySauKhiChonKH ===");
    }
    // THÊM METHOD tạo hóa đơn khi bật máy (tránh lỗi "không tìm thấy hóa đơn" khi thanh toán)
    private String taoHoaDonKhiBatMay(ql.net.entity.PhienSuDung phien) {
        try {
            System.out.println("🔥 Bắt đầu tạo hóa đơn cho phiên: " + phien.getMaPhien());

            // Tạo mã hóa đơn unique
            String maHD = generateMaHoaDon();
            System.out.println("Mã hóa đơn được tạo: " + maHD);

            // Tạo entity hóa đơn
            ql.net.entity.HoaDon hoaDon = ql.net.entity.HoaDon.builder()
                .maHD(maHD)
                .maPhien(phien.getMaPhien())
                .maKH(phien.getMaKH())
                .maNV(phien.getMaNV())
                .tongTienMay(java.math.BigDecimal.ZERO)      // Ban đầu = 0, sẽ tính khi thanh toán
                .tongTienDichVu(java.math.BigDecimal.ZERO)   // Ban đầu = 0, sẽ cộng khi gọi dịch vụ
                .tongTien(java.math.BigDecimal.ZERO)         // Ban đầu = 0
                .trangThai("Chờ thanh toán")                 // Trạng thái phù hợp cho nút thanh toán
                .build();

            System.out.println("HoaDon entity: " + hoaDon);

            // Insert vào database
            boolean ok = new ql.net.dao.impl.HoaDonDaoImpl().insert(hoaDon);
            if (ok) {
                System.out.println("✅ Tạo hóa đơn bật máy thành công: " + maHD);
                return maHD;
            } else {
                System.out.println("❌ Tạo hóa đơn bật máy thất bại");
                return null;
            }

        } catch (Exception e) {
            System.err.println("💥 Lỗi taoHoaDonKhiBatMay: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    // THÊM METHOD tạo mã hóa đơn tự động
    private String generateMaHoaDon() {
        List<HoaDon> list = new ql.net.dao.impl.HoaDonDaoImpl().selectAll();
        int max = 0;
        for (HoaDon hd : list) {
            String ma = hd.getMaHD();
            if (ma != null && ma.startsWith("HD")) {
                try {
                    int num = Integer.parseInt(ma.substring(2));
                    if (num > max) max = num;
                } catch (NumberFormatException e) {
                    // bỏ qua nếu không đúng định dạng
                }
            }
        }
        return String.format("HD%02d", max + 1);
    }

    // THÊM METHOD tạo mã phiên sử dụng tự động
    private String generateMaPhienSuDung() {
        List<PhienSuDung> list = new ql.net.dao.impl.PhienSuDungDaoImpl().selectAll();
        int max = 0;
        for (PhienSuDung ps : list) {
            String ma = ps.getMaPhien();
            if (ma != null && ma.startsWith("PS")) {
                try {
                    int num = Integer.parseInt(ma.substring(2));
                    if (num > max) max = num;
                } catch (NumberFormatException e) {
                    // bỏ qua nếu không đúng định dạng
                }
            }
        }
        return String.format("PS%02d", max + 1);
    }

    // THÊM METHOD kiểm tra VIP
    private String kiemTraVIP(String maKH) {
        try {
            String sqlVIP = "SELECT v.LoaiVIP FROM ThanhVienVIP v WHERE v.MaKH = ? AND v.TrangThai = 1 AND v.NgayHetHan >= GETDATE()";
            return ql.net.util.XJdbc.getValue(sqlVIP, maKH);
        } catch (Exception e) {
            System.err.println("Lỗi kiểm tra VIP: " + e.getMessage());
            return null;
        }
    }

    // THÊM METHOD tự động chọn máy vừa bật để hiển thị thông tin
    private void tuChonMayVuaBat(String maMay, String tenMay, String loaiMay) {
        try {
            System.out.println("🎯 Tự động chọn máy vừa bật: " + maMay + " - " + tenMay);

            // Xác định tab và panel thông tin dựa vào loại máy
            JPanel pnlThongTin = null;
            JTextField txtTrangThai = null;
            JDateChooser txtThoiDiemBatDau = null;
            JTextField txtThoiGianSuDung = null;
            JTextField txtTamTinh = null;

            // Phân loại theo loại máy (dựa vào database)
            switch (loaiMay.toLowerCase()) {
                case "tiêu chuẩn":
                    pnlThongTin = pnlThongTin1;
                    txtTrangThai = txtTCTrangThai;
                    txtThoiDiemBatDau = txtTCTDBatDau;
                    txtThoiGianSuDung = txtTCTGSuDung;
                    txtTamTinh = txtTCTamTinh;
                    System.out.println("→ Chọn tab Tiêu chuẩn");
                    break;

                case "gaming":
                    pnlThongTin = pnlThongTin2;
                    txtTrangThai = txtGTrangThai;
                    txtThoiDiemBatDau = txtGTDBatDau;
                    txtThoiGianSuDung = txtGTGSuDung;
                    txtTamTinh = txtGTamTinh;
                    System.out.println("→ Chọn tab Gaming");
                    break;

                case "chuyên nghiệp":
                    pnlThongTin = pnlThongTin3;
                    txtTrangThai = txtCNTrangThai;
                    txtThoiDiemBatDau = txtCNTDBatDau;
                    txtThoiGianSuDung = txtCNTGSuDung;
                    txtTamTinh = txtCNTamTinh;
                    System.out.println("→ Chọn tab Chuyên nghiệp");
                    break;

                case "thi đấu":
                    pnlThongTin = pnlThongTin4;
                    txtTrangThai = txtTDTrangThai;
                    txtThoiDiemBatDau = txtTDTDBatDau;
                    txtThoiGianSuDung = txtTDTGSuDung;
                    txtTamTinh = txtTDTamTinh;
                    System.out.println("→ Chọn tab Thi đấu");
                    break;

                default:
                    // Fallback: Tiêu chuẩn
                    pnlThongTin = pnlThongTin1;
                    txtTrangThai = txtTCTrangThai;
                    txtThoiDiemBatDau = txtTCTDBatDau;
                    txtThoiGianSuDung = txtTCTGSuDung;
                    txtTamTinh = txtTCTamTinh;
                    System.out.println("→ Fallback: Chọn tab Tiêu chuẩn");
                    break;
            }

            // Gọi method chonMay để cập nhật giao diện và bắt đầu timer
            if (pnlThongTin != null && txtTrangThai != null) {
                chonMay(maMay, tenMay, "Đang sử dụng", 
                       pnlThongTin, txtTrangThai, txtThoiDiemBatDau, 
                       txtThoiGianSuDung, txtTamTinh);
                System.out.println("✅ Đã tự động chọn máy và bắt đầu timer");
            } else {
                System.out.println("❌ Không thể xác định panel thông tin cho loại máy: " + loaiMay);
            }

        } catch (Exception e) {
            System.err.println("Lỗi tự chọn máy vừa bật: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // THÊM METHOD reset pending state
    private void resetPendingState() {
        System.out.println("🔄 Reset pending state");
        pendingBatMayMaMay = null;
        pendingTxtTrangThai = null;
        pendingTxtThoiDiemBatDau = null;
        pendingTxtThoiGianSuDung = null;
        pendingTxtTamTinh = null;
        pendingPnlMay = null;
    }
    
    private void loadTongHoaDonForMay(String maMay) {
        // Lấy tổng hóa đơn theo máy
        int tongHoaDon = new ql.net.dao.impl.HoaDonDaoImpl().getTongHoaDonByMaMay(maMay);
        JTextField txtTongHoaDon = null;
        if (maMay.startsWith("TC")) txtTongHoaDon = txtTCHoaDon;
        else if (maMay.startsWith("GM")) txtTongHoaDon = txtGHoaDon;
        else if (maMay.startsWith("CN")) txtTongHoaDon = txtCNHoaDon;
        else if (maMay.startsWith("TD")) txtTongHoaDon = txtTDHoaDon;
        if (txtTongHoaDon != null) txtTongHoaDon.setText(String.valueOf(tongHoaDon));
    }
    //chỉ hiện 1 ,ẩn 3
    private  void setTabVisibility(String activeTab){
        highlightActiveTab(activeTab);
        //refresh UI
        pnlTabsBar.revalidate();
        pnlTabsBar.repaint();
        
    }
    //method chuyển đổi tab
    //method chuyển đổi tab
    private void switchToTab(String tabName){
        try {
            cardLayoutTabs.show(pnlTabContent, tabName);
            setTabVisibility(tabName);
            highlightActiveTab(tabName);

            // THÊM: Cập nhật tab hiện tại
            setCurrentActiveTab(tabName);

            if ("nhapHang".equals(tabName)) {
                loadNhapHangToTable(); // Đảm bảo dòng này được gọi
            }
        } catch (Exception e) {
            System.out.println("Lỗi chuyển tab: " + e.getMessage());
        }
    }
    // Method highlight tab button được chọn (chỉ tab hiển thị)
    private void highlightActiveTab(String activeTab) {
        // đổi màu nút khi ấn vào
        java.awt.Color normalColor = new java.awt.Color(0, 255, 200);// bình thường
        java.awt.Color activeColor = new java.awt.Color(0, 128, 128);  // Màu khi được chọn

        // Reset tất cả tab buttons về màu bình thường
        btnTieuChuan.setBackground(normalColor);
        btnGaming.setBackground(normalColor);
        btnChuyenNghiep.setBackground(normalColor);
        btnThiDau.setBackground(normalColor);
        
        btnTieuChuan.setForeground(java.awt.Color.BLACK);
        btnGaming.setForeground(java.awt.Color.BLACK);
        btnChuyenNghiep.setForeground(java.awt.Color.BLACK);
        btnThiDau.setForeground(java.awt.Color.BLACK);

        // Set màu active cho button được chọn (chỉ nếu nó đang hiển thị)
        switch(activeTab) {
            case "tieuChuan":
                if (btnTieuChuan.isVisible()) {
                    btnTieuChuan.setBackground(activeColor);
                    btnTieuChuan.setForeground(java.awt.Color.WHITE);
                }
                break;
            case "gaming":
                if (btnGaming.isVisible()) {
                    btnGaming.setBackground(activeColor);
                    btnGaming.setForeground(java.awt.Color.WHITE);
                }
                break;
            case "chuyenNghiep":
                if (btnChuyenNghiep.isVisible()) {
                    btnChuyenNghiep.setBackground(activeColor);
                    btnChuyenNghiep.setForeground(java.awt.Color.WHITE);
                }
                break;
            case "thiDau":
                if (btnThiDau.isVisible()) {
                    btnThiDau.setBackground(activeColor);
                    btnThiDau.setForeground(java.awt.Color.WHITE);
                }
                break;
        }
    }
//    load dữ liệu khách hàng
    private void loadKhachHangToTable(){
        // Lấy danh sách khách hàng từ database
        java.util.List<ql.net.entity.KhachHang> danhSach = new ql.net.dao.impl.KhachHangDaoImpl().selectAll();
        // Lấy model của bảng
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblBangKhachHang.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
        // Đổ dữ liệu lên bảng
        for (ql.net.entity.KhachHang kh : danhSach) {
            model.addRow(new Object[]{
                kh.getMaKH(),
                kh.getHoTen(),
                kh.getSoDT(),
                kh.getSoDuTaiKhoan(),
                kh.isTrangThai() ? "Hoạt động" : "Ngừng",
                kh.getImage()
            });
        }
    }
    //Load dữ liệu thành viên vip
    private void loadVIPToTable() {
        java.util.List<ql.net.entity.ThanhVienVIP> danhSach = new ql.net.dao.impl.ThanhVienVipDaoImpl().selectAll();
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblBangKhachVIP.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
        for (ql.net.entity.ThanhVienVIP vip : danhSach) {
            model.addRow(new Object[]{
                vip.getMaVIP(),
                vip.getMaKH(),
                vip.getLoaiVIP(),
                vip.getTongChiTieu(),
                vip.getNgayThamGia(),
                vip.getNgayHetHan(),
                vip.isTrangThai() ? "Còn hiệu lực" : "Hết hạn"
            });
        }
    }
    //đổ dữ liệu Đồ ăn lên bảng tblDoAn
    private void loadDoAnToTable(){
        java.util.List<ql.net.entity.DichVu> danhSach = new ql.net.dao.impl.DichVuDaoImpl().selectByLoai("Đồ ăn");
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblDoAn.getModel();
        model.setRowCount(0);//Xóa dữ liệu cũ
        for (ql.net.entity.DichVu dv : danhSach) {
            model.addRow(new Object[]{
                dv.getMaDV(),
                dv.getTenDV(),
                dv.getLoaiDV(),
                dv.getDonGia(),
                dv.getSoLuongTon(),
                dv.isTrangThai() ? "Còn hàng":"Hết hàng",
                dv.getImage()
            });
        }
    }
    //bảng đồ uống
    private void loadDoUongToTable(){
        java.util.List<ql.net.entity.DichVu> danhSach = new ql.net.dao.impl.DichVuDaoImpl().selectByLoai("Đồ uống");
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblDoUong.getModel();
        model.setRowCount(0);
        for(ql.net.entity.DichVu dv : danhSach){
            model.addRow(new Object[]{
                dv.getMaDV(),
                dv.getTenDV(),
                dv.getLoaiDV(),
                dv.getDonGia(),
                dv.getSoLuongTon(),
                dv.isTrangThai() ? "Còn hàng" :"Hết hàng",
                dv.getImage()
            });
        }
    }
    //Bảng dịch vụ khác
    private void loadDichVuKhac(){
        java.util.List<ql.net.entity.DichVu> danhSach = new ql.net.dao.impl.DichVuDaoImpl().selectByLoai("Khác");
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblDichVuKhac.getModel();
        model.setRowCount(0);
        for(ql.net.entity.DichVu dv : danhSach){
            model.addRow(new Object[]{
                dv.getMaDV(),
                dv.getTenDV(),
                dv.getLoaiDV(),
                dv.getDonGia(),
                dv.getSoLuongTon(),
                dv.isTrangThai() ?"Còn hàng":"Hết hàng",
                dv.getImage()
            });
        }
    }
    //load dữ liệu nhập hàng
    private void loadNhapHangToTable(){
        try {
            java.util.List<ql.net.entity.NhapHang> danhSachNhapHang = new ql.net.dao.impl.NhapHangDaoImpl().selectAll();
            System.out.println("Số bản ghi nhập hàng: " + danhSachNhapHang.size());
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblNhapHang.getModel();
            model.setRowCount(0);
            int i = 0;
            for (ql.net.entity.NhapHang nh : danhSachNhapHang) {
                Object[] row = new Object[]{
                    nh.getMaNhapHang(),
                    nh.getMaNV(),
                    nh.getMaDV(),
                    nh.getNgayNhap() != null ? nh.getNgayNhap().toString() : "",
                    nh.getTenNhaCungCap(),
                    nh.getSoLuong(),
                    nh.getDonGiaNhap(),
                    nh.getThanhTien()
                };
                System.out.println("Row " + (++i) + ": " + java.util.Arrays.toString(row));
                model.addRow(row);
            }
            System.out.println("Đã add xong " + i + " dòng vào bảng nhập hàng.");
        } catch (Exception ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }
    //sự kiện bật máy
    private void batMay(
        String maMay,
        JTextField txtTrangThai,
        JDateChooser txtThoiDiemBatDau,
        JTextField txtThoiGianSuDung,
        JTextField txtTamTinh,
        JPanel pnlMay
    ) {
        chonKhachHangDeBatMay(maMay, txtTrangThai, txtThoiDiemBatDau, txtThoiGianSuDung, txtTamTinh, pnlMay);
    }
    //tạo mã dịch vụ tăng tự động
    private String generateMaDV() {
        java.util.List<ql.net.entity.DichVu> list = new ql.net.dao.impl.DichVuDaoImpl().selectAll();
        int max = 0;
        for (ql.net.entity.DichVu dv : list) {
            try {
                int num = Integer.parseInt(dv.getMaDV().replace("DV", ""));
                if (num > max) max = num;
            } catch (Exception e) {
                // Nếu mã không đúng định dạng thì bỏ qua
            }
        }
        return String.format("DV%02d", max + 1);
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel17 = new javax.swing.JPanel();
        buttonGroup2 = new javax.swing.ButtonGroup();
        pnlSildebar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        pnlMenu = new javax.swing.JPanel();
        btnQuanLyMay = new javax.swing.JButton();
        btnQuanLyKhach = new javax.swing.JButton();
        btnQuanLyDichVu = new javax.swing.JButton();
        btnQuanLyHoaDon = new javax.swing.JButton();
        btnBaoTri = new javax.swing.JButton();
        btnBaoCao = new javax.swing.JButton();
        btnQuanLyNhanVien = new javax.swing.JButton();
        btnCaiDat = new javax.swing.JButton();
        btnDangXuat = new javax.swing.JButton();
        pnl1 = new javax.swing.JPanel();
        pnlGioiThieu = new javax.swing.JPanel();
        lblChaoMung = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        pnlQuanLyMay = new javax.swing.JPanel();
        pnlHeaderNhanVien = new javax.swing.JPanel();
        lblNhanVien = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        pnlMainContainer = new javax.swing.JPanel();
        pnlTabsBar = new javax.swing.JPanel();
        btnTieuChuan = new javax.swing.JButton();
        btnGaming = new javax.swing.JButton();
        btnChuyenNghiep = new javax.swing.JButton();
        btnThiDau = new javax.swing.JButton();
        pnlTabContent = new javax.swing.JPanel();
        pnlTieuChuan = new javax.swing.JPanel();
        pnlSidebarTieuChuan = new javax.swing.JPanel();
        pnlThongTin1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtTCTrangThai = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtTCTamTinh = new javax.swing.JTextField();
        pnlTCFood = new javax.swing.JPanel();
        scrTC = new javax.swing.JScrollPane();
        tblTCBang = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        txtTCFood = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtTCHoaDon = new javax.swing.JTextField();
        btnThanhToan = new javax.swing.JButton();
        btnBatMay = new javax.swing.JButton();
        btnTCDichVu = new javax.swing.JButton();
        txtTCTDBatDau = new com.toedter.calendar.JDateChooser();
        txtTCTGSuDung = new javax.swing.JTextField();
        btnTCXoa = new javax.swing.JButton();
        pnlMainTieuChuan = new javax.swing.JPanel();
        pnlCauHinh = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        pnlGridMay = new javax.swing.JPanel();
        pnlGaming = new javax.swing.JPanel();
        pnlSidebarGaming = new javax.swing.JPanel();
        pnlThongTin2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtGTrangThai = new javax.swing.JTextField();
        pnlGFood = new javax.swing.JPanel();
        scrG = new javax.swing.JScrollPane();
        tblGBang = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        txtGFood = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtGHoaDon = new javax.swing.JTextField();
        btnThanhToan1 = new javax.swing.JButton();
        btnBatMay1 = new javax.swing.JButton();
        txtGTamTinh = new javax.swing.JTextField();
        btnGDichVu = new javax.swing.JButton();
        txtGTDBatDau = new com.toedter.calendar.JDateChooser();
        txtGTGSuDung = new javax.swing.JTextField();
        btnGXoa = new javax.swing.JButton();
        pnlMainGaming = new javax.swing.JPanel();
        pnlCauHinhGaming = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        pnlGridMay1 = new javax.swing.JPanel();
        pnlChuyenNghiep = new javax.swing.JPanel();
        pnlSidebarChuyenNghiep = new javax.swing.JPanel();
        pnlThongTin3 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtCNTrangThai = new javax.swing.JTextField();
        txtCNTamTinh = new javax.swing.JTextField();
        btnBatMay2 = new javax.swing.JButton();
        pnlCNFood = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        txtCNFood = new javax.swing.JTextField();
        scrCN = new javax.swing.JScrollPane();
        tblCNBang = new javax.swing.JTable();
        jLabel26 = new javax.swing.JLabel();
        txtCNHoaDon = new javax.swing.JTextField();
        btnCNThanhToan = new javax.swing.JButton();
        btnCNDichVu = new javax.swing.JButton();
        txtCNTDBatDau = new com.toedter.calendar.JDateChooser();
        txtCNTGSuDung = new javax.swing.JTextField();
        btnCNXoa = new javax.swing.JButton();
        pnlMainChuyenNghiep = new javax.swing.JPanel();
        pnlCauHinhChuyenNghiep = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        pnlGridMay2 = new javax.swing.JPanel();
        pnlThiDau = new javax.swing.JPanel();
        pnlSidebarThiDau = new javax.swing.JPanel();
        pnlThongTin4 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtTDTrangThai = new javax.swing.JTextField();
        txtTDTamTinh = new javax.swing.JTextField();
        btnTDBatMay = new javax.swing.JButton();
        pnlTDFood = new javax.swing.JPanel();
        scrTD = new javax.swing.JScrollPane();
        tblTDBang = new javax.swing.JTable();
        jLabel31 = new javax.swing.JLabel();
        txtTDFood = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        txtTDHoaDon = new javax.swing.JTextField();
        btnTDThanhToan = new javax.swing.JButton();
        btnTDDichVu = new javax.swing.JButton();
        txtTDTDBatDau = new com.toedter.calendar.JDateChooser();
        txtTDTGSuDung = new javax.swing.JTextField();
        btnTDXoa = new javax.swing.JButton();
        pnlMainThiDau = new javax.swing.JPanel();
        pnlCauHinhThiDau = new javax.swing.JPanel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        pnlGridMay3 = new javax.swing.JPanel();
        pnlQuanLyDichVu = new javax.swing.JPanel();
        tabDichVu = new javax.swing.JTabbedPane();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblDoAn = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        lblAnhDoAn = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        txtTenDVDA = new javax.swing.JTextField();
        jLabel84 = new javax.swing.JLabel();
        txtDonGiaDA = new javax.swing.JTextField();
        jLabel85 = new javax.swing.JLabel();
        txtSoLuongTonDA = new javax.swing.JTextField();
        jLabel86 = new javax.swing.JLabel();
        cboTrangThaiDA = new javax.swing.JComboBox<>();
        cboLoaiDVDA = new javax.swing.JComboBox<>();
        txtMaDVDA = new javax.swing.JTextField();
        jPanel20 = new javax.swing.JPanel();
        btnThemDA = new javax.swing.JButton();
        btnCapNhatDA = new javax.swing.JButton();
        btnXoaDA = new javax.swing.JButton();
        btnLamMoiDA = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblDoUong = new javax.swing.JTable();
        jPanel21 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        lblAnhDoUong = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        txtMaDVDU = new javax.swing.JTextField();
        jLabel88 = new javax.swing.JLabel();
        txtTenDVDU = new javax.swing.JTextField();
        jLabel89 = new javax.swing.JLabel();
        cboLoaiDVDU = new javax.swing.JComboBox<>();
        jLabel90 = new javax.swing.JLabel();
        txtDonGiaDU = new javax.swing.JTextField();
        jLabel91 = new javax.swing.JLabel();
        txtSoLuongTonDU = new javax.swing.JTextField();
        jLabel92 = new javax.swing.JLabel();
        cboTrangThaiDU = new javax.swing.JComboBox<>();
        jPanel24 = new javax.swing.JPanel();
        btnThemDU = new javax.swing.JButton();
        btnCapNhatDU = new javax.swing.JButton();
        btnXoaDU = new javax.swing.JButton();
        btnLamMoiDU = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblDichVuKhac = new javax.swing.JTable();
        jPanel22 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        lblAnhDichVu = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        txtMaDVK = new javax.swing.JTextField();
        jLabel94 = new javax.swing.JLabel();
        txtTenDVK = new javax.swing.JTextField();
        jLabel95 = new javax.swing.JLabel();
        cboLoaiDVK = new javax.swing.JComboBox<>();
        jLabel96 = new javax.swing.JLabel();
        txtDonGiaDVK = new javax.swing.JTextField();
        jLabel97 = new javax.swing.JLabel();
        txtSoLuongDVK = new javax.swing.JTextField();
        jLabel98 = new javax.swing.JLabel();
        cboTrangThaiDVK = new javax.swing.JComboBox<>();
        jPanel25 = new javax.swing.JPanel();
        btnThemDVK = new javax.swing.JButton();
        btnCapNhatDVK = new javax.swing.JButton();
        btnXoaDVk = new javax.swing.JButton();
        btnLamMoiDVk = new javax.swing.JButton();
        jPanel34 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNhapHang = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        btnThemNhapHang = new javax.swing.JButton();
        btnCapNhatNhapHang = new javax.swing.JButton();
        btnXoaNhapHang = new javax.swing.JButton();
        btnLamMoiNhapHang = new javax.swing.JButton();
        jPanel23 = new javax.swing.JPanel();
        jLabel99 = new javax.swing.JLabel();
        txtMaNhapHang = new javax.swing.JTextField();
        jLabel100 = new javax.swing.JLabel();
        txtMaNVNhapHang = new javax.swing.JTextField();
        jLabel101 = new javax.swing.JLabel();
        txtMaDVNhapHang = new javax.swing.JTextField();
        jLabel102 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        txtTenNhaCungCap = new javax.swing.JTextField();
        jLabel104 = new javax.swing.JLabel();
        txtSoLuongNhapHang = new javax.swing.JTextField();
        jLabel105 = new javax.swing.JLabel();
        txtDonGiaNhapHang = new javax.swing.JTextField();
        jLabel106 = new javax.swing.JLabel();
        txtThanhTienNhapHang = new javax.swing.JTextField();
        txtNgayNhapHang = new com.toedter.calendar.JDateChooser();
        pnlQuanLyHoaDon = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblhoaDonChiTiet = new javax.swing.JTable();
        btnInHoaDon = new javax.swing.JButton();
        jPanel30 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblLichSuGiaoDich = new javax.swing.JTable();
        pnlQuanLyNhanVien = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblAnhNV = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtHoTenNV = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtSDTNV = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        cboChucVuNV = new javax.swing.JComboBox<>();
        jLabel34 = new javax.swing.JLabel();
        txtTenTaiKhoan = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        txtMatKhauNV = new javax.swing.JPasswordField();
        jLabel36 = new javax.swing.JLabel();
        cboTrangThaiNV = new javax.swing.JComboBox<>();
        jLabel37 = new javax.swing.JLabel();
        dchNgayTaoNV = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        btnThemNV = new javax.swing.JButton();
        btnCapNhatNV = new javax.swing.JButton();
        btnXoaNV = new javax.swing.JButton();
        btnLamMoiNV = new javax.swing.JButton();
        pnlQuanLyBaoCao = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jLabel111 = new javax.swing.JLabel();
        lblDoanhThu = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jLabel112 = new javax.swing.JLabel();
        lblSoMay = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        jLabel118 = new javax.swing.JLabel();
        lblSoVIP = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jLabel113 = new javax.swing.JLabel();
        lblSoDichVu = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jLabel114 = new javax.swing.JLabel();
        lblSoNhanVien = new javax.swing.JLabel();
        jPanel39 = new javax.swing.JPanel();
        jLabel117 = new javax.swing.JLabel();
        lblSoLanBaoTri = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        jLabel116 = new javax.swing.JLabel();
        lblSoLanNap = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        jLabel115 = new javax.swing.JLabel();
        lblSoGiaoDich = new javax.swing.JLabel();
        jPanel42 = new javax.swing.JPanel();
        jLabel119 = new javax.swing.JLabel();
        cboBaoCao = new javax.swing.JComboBox<>();
        jLabel120 = new javax.swing.JLabel();
        dateTuNgay = new com.toedter.calendar.JDateChooser();
        jLabel121 = new javax.swing.JLabel();
        dateDenNgay = new com.toedter.calendar.JDateChooser();
        btnLoc = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        jPanel43 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblBaoCao = new javax.swing.JTable();
        pnlCaiDat = new javax.swing.JPanel();
        btnThongTinHeThong = new javax.swing.JButton();
        btnDoiMatKhau = new javax.swing.JButton();
        pnlQuanLyBaoTri = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        pnlGridMayBaoTri = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        cboBoPhanBT = new javax.swing.JComboBox<>();
        jLabel41 = new javax.swing.JLabel();
        txtMoTaBaoTri = new javax.swing.JTextField();
        pnlDSBaoTri = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBaoTri = new javax.swing.JTable();
        jLabel42 = new javax.swing.JLabel();
        txtBTTongChiPhi = new javax.swing.JTextField();
        btnBTThanhToan = new javax.swing.JButton();
        btnNutBaoTri = new javax.swing.JButton();
        txtBTTrangThai = new javax.swing.JTextField();
        jLabel109 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        pnlQuanLyKhachHang = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblBangKhachHang = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        btnThemKH = new javax.swing.JButton();
        btnCapNhatKH = new javax.swing.JButton();
        btnXoaKH = new javax.swing.JButton();
        btnLamMoiKH = new javax.swing.JButton();
        btnNapTienKH = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        lblAnhKH = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        txtSDTKKH = new javax.swing.JTextField();
        txtSDTKH = new javax.swing.JTextField();
        txtHoTenKH = new javax.swing.JTextField();
        cboTrangThaiKH = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblBangKhachVIP = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        btnThemVIP = new javax.swing.JButton();
        btnCapNhatVIP = new javax.swing.JButton();
        btnXoaVIP = new javax.swing.JButton();
        btnLamMoiVIP = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        txtMaVIP = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        txtMaKH_VIP = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        txtLoaiVip = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        cboTrangThaiVip = new javax.swing.JComboBox<>();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        txtTongChiTieu = new javax.swing.JTextField();
        txtNgayThamGiaVip = new com.toedter.calendar.JDateChooser();
        txtNgayHetHanVip = new com.toedter.calendar.JDateChooser();

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hệ Thống Quản Lý Tiệm NET-Tấn Hiếu");
        setPreferredSize(new java.awt.Dimension(1320, 820));
        setSize(new java.awt.Dimension(1320, 820));

        pnlSildebar.setBackground(new java.awt.Color(60, 160, 90));
        pnlSildebar.setPreferredSize(new java.awt.Dimension(150, 150));
        pnlSildebar.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(245, 245, 245));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("HỆ THỐNG QUẢN LÝ TIỆM NET");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pnlSildebar.add(jLabel1, java.awt.BorderLayout.CENTER);

        jPanel31.setPreferredSize(new java.awt.Dimension(220, 150));

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ql/net/icons/logo.jpg"))); // NOI18N
        lblLogo.setPreferredSize(new java.awt.Dimension(32, 32));
        lblLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLogoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblLogoMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        pnlSildebar.add(jPanel31, java.awt.BorderLayout.LINE_START);

        getContentPane().add(pnlSildebar, java.awt.BorderLayout.PAGE_START);

        pnlMenu.setBackground(new java.awt.Color(0, 40, 30));
        pnlMenu.setMinimumSize(new java.awt.Dimension(250, 250));
        pnlMenu.setPreferredSize(new java.awt.Dimension(220, 800));
        pnlMenu.setLayout(new java.awt.GridLayout(0, 1, 0, 2));

        btnQuanLyMay.setBackground(new java.awt.Color(80, 200, 120));
        btnQuanLyMay.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnQuanLyMay.setForeground(new java.awt.Color(245, 245, 245));
        btnQuanLyMay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ql/net/icons/computer.png"))); // NOI18N
        btnQuanLyMay.setText("ĐẶT MÁY TÍNH");
        btnQuanLyMay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuanLyMayActionPerformed(evt);
            }
        });
        pnlMenu.add(btnQuanLyMay);

        btnQuanLyKhach.setBackground(new java.awt.Color(80, 200, 120));
        btnQuanLyKhach.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnQuanLyKhach.setForeground(new java.awt.Color(245, 245, 245));
        btnQuanLyKhach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ql/net/icons/clinet.png"))); // NOI18N
        btnQuanLyKhach.setText("KHÁCH HÀNG");
        btnQuanLyKhach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuanLyKhachActionPerformed(evt);
            }
        });
        pnlMenu.add(btnQuanLyKhach);

        btnQuanLyDichVu.setBackground(new java.awt.Color(80, 200, 120));
        btnQuanLyDichVu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnQuanLyDichVu.setForeground(new java.awt.Color(245, 245, 245));
        btnQuanLyDichVu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ql/net/icons/service.png"))); // NOI18N
        btnQuanLyDichVu.setText("DỊCH VỤ ");
        btnQuanLyDichVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuanLyDichVuActionPerformed(evt);
            }
        });
        pnlMenu.add(btnQuanLyDichVu);

        btnQuanLyHoaDon.setBackground(new java.awt.Color(80, 200, 120));
        btnQuanLyHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnQuanLyHoaDon.setForeground(new java.awt.Color(245, 245, 245));
        btnQuanLyHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ql/net/icons/bill.png"))); // NOI18N
        btnQuanLyHoaDon.setText("HÓA ĐƠN");
        btnQuanLyHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuanLyHoaDonActionPerformed(evt);
            }
        });
        pnlMenu.add(btnQuanLyHoaDon);

        btnBaoTri.setBackground(new java.awt.Color(80, 200, 120));
        btnBaoTri.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnBaoTri.setForeground(new java.awt.Color(245, 245, 245));
        btnBaoTri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ql/net/icons/technician.png"))); // NOI18N
        btnBaoTri.setText("BẢO TRÌ");
        btnBaoTri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBaoTriActionPerformed(evt);
            }
        });
        pnlMenu.add(btnBaoTri);

        btnBaoCao.setBackground(new java.awt.Color(80, 200, 120));
        btnBaoCao.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnBaoCao.setForeground(new java.awt.Color(245, 245, 245));
        btnBaoCao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ql/net/icons/report.png"))); // NOI18N
        btnBaoCao.setText("BÁO CÁO");
        btnBaoCao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBaoCaoActionPerformed(evt);
            }
        });
        pnlMenu.add(btnBaoCao);

        btnQuanLyNhanVien.setBackground(new java.awt.Color(80, 200, 120));
        btnQuanLyNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnQuanLyNhanVien.setForeground(new java.awt.Color(245, 245, 245));
        btnQuanLyNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ql/net/icons/staff.png"))); // NOI18N
        btnQuanLyNhanVien.setText("QUẢN LÝ NHÂN VIÊN");
        btnQuanLyNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuanLyNhanVienActionPerformed(evt);
            }
        });
        pnlMenu.add(btnQuanLyNhanVien);

        btnCaiDat.setBackground(new java.awt.Color(80, 200, 120));
        btnCaiDat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCaiDat.setForeground(new java.awt.Color(245, 245, 245));
        btnCaiDat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ql/net/icons/setting.png"))); // NOI18N
        btnCaiDat.setText("CÀI ĐẶT");
        btnCaiDat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCaiDatActionPerformed(evt);
            }
        });
        pnlMenu.add(btnCaiDat);

        btnDangXuat.setBackground(new java.awt.Color(255, 0, 51));
        btnDangXuat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDangXuat.setForeground(new java.awt.Color(245, 245, 245));
        btnDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ql/net/icons/export.png"))); // NOI18N
        btnDangXuat.setText("ĐĂNG XUẤT");
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });
        pnlMenu.add(btnDangXuat);

        getContentPane().add(pnlMenu, java.awt.BorderLayout.LINE_START);

        pnl1.setBackground(new java.awt.Color(10, 20, 15));
        pnl1.setLayout(new java.awt.CardLayout());

        pnlGioiThieu.setBackground(new java.awt.Color(8, 8, 12));
        pnlGioiThieu.setMinimumSize(new java.awt.Dimension(1084, 749));
        pnlGioiThieu.setPreferredSize(new java.awt.Dimension(933, 631));

        lblChaoMung.setBackground(new java.awt.Color(60, 160, 90));
        lblChaoMung.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblChaoMung.setForeground(new java.awt.Color(245, 245, 245));
        lblChaoMung.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblChaoMung.setText("CHÀO MỪNG ĐẾN VỚI NET DTH GAMING – NƠI CÔNG NGHỆ GẶP ĐAM MÊ!");
        lblChaoMung.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblChaoMung.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel80.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ql/net/icons/trangChu.png"))); // NOI18N

        javax.swing.GroupLayout pnlGioiThieuLayout = new javax.swing.GroupLayout(pnlGioiThieu);
        pnlGioiThieu.setLayout(pnlGioiThieuLayout);
        pnlGioiThieuLayout.setHorizontalGroup(
            pnlGioiThieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGioiThieuLayout.createSequentialGroup()
                .addComponent(jLabel80)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(lblChaoMung, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlGioiThieuLayout.setVerticalGroup(
            pnlGioiThieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGioiThieuLayout.createSequentialGroup()
                .addComponent(lblChaoMung)
                .addGap(0, 0, 0)
                .addComponent(jLabel80))
        );

        pnl1.add(pnlGioiThieu, "gioiThieu");

        pnlQuanLyMay.setLayout(new java.awt.BorderLayout());

        pnlHeaderNhanVien.setBackground(new java.awt.Color(50, 120, 70));

        lblNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNhanVien.setForeground(new java.awt.Color(245, 245, 245));
        lblNhanVien.setText("Nhân viên:Đoàn Tấn Hiếu");

        jLabel2.setForeground(new java.awt.Color(245, 245, 245));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ql/net/icons/xanh_nhat.png"))); // NOI18N
        jLabel2.setText("Đang sử dụng");

        jLabel107.setForeground(new java.awt.Color(245, 245, 245));
        jLabel107.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ql/net/icons/mau_do.png"))); // NOI18N
        jLabel107.setText("Bảo trì");

        jLabel108.setForeground(new java.awt.Color(245, 245, 245));
        jLabel108.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ql/net/icons/mau_trong.png"))); // NOI18N
        jLabel108.setText("Trống");

        javax.swing.GroupLayout pnlHeaderNhanVienLayout = new javax.swing.GroupLayout(pnlHeaderNhanVien);
        pnlHeaderNhanVien.setLayout(pnlHeaderNhanVienLayout);
        pnlHeaderNhanVienLayout.setHorizontalGroup(
            pnlHeaderNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHeaderNhanVienLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel107)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel108)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 671, Short.MAX_VALUE)
                .addComponent(lblNhanVien)
                .addContainerGap())
        );
        pnlHeaderNhanVienLayout.setVerticalGroup(
            pnlHeaderNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderNhanVienLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(pnlHeaderNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNhanVien)
                    .addComponent(jLabel2)
                    .addComponent(jLabel107)
                    .addComponent(jLabel108)))
        );

        pnlQuanLyMay.add(pnlHeaderNhanVien, java.awt.BorderLayout.PAGE_START);

        pnlMainContainer.setLayout(new java.awt.BorderLayout());

        pnlTabsBar.setBackground(new java.awt.Color(25, 40, 50));

        btnTieuChuan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTieuChuan.setForeground(new java.awt.Color(204, 204, 204));
        btnTieuChuan.setText("Tiêu chuẩn");
        btnTieuChuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTieuChuanActionPerformed(evt);
            }
        });
        pnlTabsBar.add(btnTieuChuan);

        btnGaming.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGaming.setForeground(new java.awt.Color(204, 204, 204));
        btnGaming.setText("Gaming");
        btnGaming.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGamingActionPerformed(evt);
            }
        });
        pnlTabsBar.add(btnGaming);

        btnChuyenNghiep.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnChuyenNghiep.setForeground(new java.awt.Color(204, 204, 204));
        btnChuyenNghiep.setText("Chuyên nghiệp");
        btnChuyenNghiep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChuyenNghiepActionPerformed(evt);
            }
        });
        pnlTabsBar.add(btnChuyenNghiep);

        btnThiDau.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThiDau.setForeground(new java.awt.Color(204, 204, 204));
        btnThiDau.setText("Thi đấu");
        btnThiDau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThiDauActionPerformed(evt);
            }
        });
        pnlTabsBar.add(btnThiDau);

        pnlMainContainer.add(pnlTabsBar, java.awt.BorderLayout.PAGE_START);

        pnlTabContent.setBackground(new java.awt.Color(102, 102, 255));
        pnlTabContent.setLayout(new java.awt.CardLayout());

        pnlTieuChuan.setLayout(new java.awt.BorderLayout());

        pnlSidebarTieuChuan.setBackground(new java.awt.Color(10, 20, 15));
        pnlSidebarTieuChuan.setPreferredSize(new java.awt.Dimension(400, 592));

        pnlThongTin1.setBackground(new java.awt.Color(25, 40, 50));
        pnlThongTin1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Máy 1", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        pnlThongTin1.setPreferredSize(new java.awt.Dimension(400, 527));

        jLabel3.setForeground(new java.awt.Color(245, 245, 245));
        jLabel3.setText("Trạng thái:");

        jLabel10.setForeground(new java.awt.Color(245, 245, 245));
        jLabel10.setText("Thời điểm bắt đầu:");

        jLabel11.setForeground(new java.awt.Color(245, 245, 245));
        jLabel11.setText("Thời gian sử dụng:");

        jLabel12.setForeground(new java.awt.Color(245, 245, 245));
        jLabel12.setText("Tạm tính:");

        pnlTCFood.setBackground(new java.awt.Color(25, 40, 50));
        pnlTCFood.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Food", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        tblTCBang.setBackground(new java.awt.Color(119, 161, 119));
        tblTCBang.setForeground(new java.awt.Color(255, 255, 255));
        tblTCBang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên", "Đơn giá", "Số lượng", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrTC.setViewportView(tblTCBang);
        if (tblTCBang.getColumnModel().getColumnCount() > 0) {
            tblTCBang.getColumnModel().getColumn(1).setMaxWidth(60);
            tblTCBang.getColumnModel().getColumn(2).setMaxWidth(65);
            tblTCBang.getColumnModel().getColumn(3).setMaxWidth(80);
        }

        jLabel13.setForeground(new java.awt.Color(245, 245, 245));
        jLabel13.setText("Tổng food:");

        javax.swing.GroupLayout pnlTCFoodLayout = new javax.swing.GroupLayout(pnlTCFood);
        pnlTCFood.setLayout(pnlTCFoodLayout);
        pnlTCFoodLayout.setHorizontalGroup(
            pnlTCFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTCFoodLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrTC, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTCFoodLayout.createSequentialGroup()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTCFood))
        );
        pnlTCFoodLayout.setVerticalGroup(
            pnlTCFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTCFoodLayout.createSequentialGroup()
                .addComponent(scrTC, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(pnlTCFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtTCFood, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(245, 245, 245));
        jLabel14.setText("Tổng hóa đơn:");

        btnThanhToan.setBackground(new java.awt.Color(34, 139, 34));
        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        btnBatMay.setBackground(new java.awt.Color(34, 139, 34));
        btnBatMay.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBatMay.setForeground(new java.awt.Color(255, 255, 255));
        btnBatMay.setText("Bật máy");
        btnBatMay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatMayActionPerformed(evt);
            }
        });

        btnTCDichVu.setBackground(new java.awt.Color(34, 139, 34));
        btnTCDichVu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTCDichVu.setForeground(new java.awt.Color(255, 255, 255));
        btnTCDichVu.setText("Dịch vụ");
        btnTCDichVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTCDichVuActionPerformed(evt);
            }
        });

        btnTCXoa.setBackground(new java.awt.Color(34, 139, 34));
        btnTCXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTCXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnTCXoa.setText("Xóa");
        btnTCXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTCXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThongTin1Layout = new javax.swing.GroupLayout(pnlThongTin1);
        pnlThongTin1.setLayout(pnlThongTin1Layout);
        pnlThongTin1Layout.setHorizontalGroup(
            pnlThongTin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTin1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThongTin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTin1Layout.createSequentialGroup()
                        .addComponent(pnlTCFood, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(pnlThongTin1Layout.createSequentialGroup()
                        .addGroup(pnlThongTin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlThongTin1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(61, 61, 61)
                                .addComponent(txtTCTrangThai))
                            .addGroup(pnlThongTin1Layout.createSequentialGroup()
                                .addGroup(pnlThongTin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlThongTin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel12))
                                .addGap(18, 18, 18)
                                .addGroup(pnlThongTin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTCTamTinh)
                                    .addComponent(txtTCTDBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                                    .addComponent(txtTCTGSuDung))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addGroup(pnlThongTin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBatMay)
                            .addComponent(btnTCDichVu)
                            .addComponent(btnTCXoa))
                        .addGap(29, 29, 29))))
            .addGroup(pnlThongTin1Layout.createSequentialGroup()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTCHoaDon)
                .addGap(18, 18, 18)
                .addComponent(btnThanhToan))
        );
        pnlThongTin1Layout.setVerticalGroup(
            pnlThongTin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTin1Layout.createSequentialGroup()
                .addGroup(pnlThongTin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTCTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatMay))
                .addGap(12, 12, 12)
                .addGroup(pnlThongTin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlThongTin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(btnTCDichVu))
                    .addComponent(txtTCTDBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtTCTGSuDung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTCXoa))
                .addGap(10, 10, 10)
                .addGroup(pnlThongTin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtTCTamTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlTCFood, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(34, 34, 34)
                .addGroup(pnlThongTin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtTCHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThanhToan))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout pnlSidebarTieuChuanLayout = new javax.swing.GroupLayout(pnlSidebarTieuChuan);
        pnlSidebarTieuChuan.setLayout(pnlSidebarTieuChuanLayout);
        pnlSidebarTieuChuanLayout.setHorizontalGroup(
            pnlSidebarTieuChuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSidebarTieuChuanLayout.createSequentialGroup()
                .addComponent(pnlThongTin1, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlSidebarTieuChuanLayout.setVerticalGroup(
            pnlSidebarTieuChuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlThongTin1, javax.swing.GroupLayout.DEFAULT_SIZE, 732, Short.MAX_VALUE)
        );

        pnlTieuChuan.add(pnlSidebarTieuChuan, java.awt.BorderLayout.LINE_END);

        pnlMainTieuChuan.setBackground(new java.awt.Color(25, 40, 50));
        pnlMainTieuChuan.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Tiêu chuẩn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        pnlMainTieuChuan.setLayout(new java.awt.BorderLayout());

        pnlCauHinh.setBackground(new java.awt.Color(25, 40, 50));
        pnlCauHinh.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Cấu Hình", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(245, 245, 245));
        jLabel5.setText("Màn hình ASUS TUF Gaming VG249Q1A");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(245, 245, 245));
        jLabel6.setText("Intel Core i3-12100F");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(245, 245, 245));
        jLabel7.setText("Samsung 980 Pro NVMe PCle M.2 500GB");

        jLabel55.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(245, 245, 245));
        jLabel55.setText("Chuột Redragon S101");

        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(245, 245, 245));
        jLabel56.setText("Nvidia GTX 1650S(Super)");

        jLabel57.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(245, 245, 245));
        jLabel57.setText("WD Black 2TB");

        jLabel58.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(245, 245, 245));
        jLabel58.setText("Bàn phím Redragon S101");

        javax.swing.GroupLayout pnlCauHinhLayout = new javax.swing.GroupLayout(pnlCauHinh);
        pnlCauHinh.setLayout(pnlCauHinhLayout);
        pnlCauHinhLayout.setHorizontalGroup(
            pnlCauHinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCauHinhLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(pnlCauHinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel55))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 279, Short.MAX_VALUE)
                .addGroup(pnlCauHinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel57)
                    .addComponent(jLabel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        pnlCauHinhLayout.setVerticalGroup(
            pnlCauHinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCauHinhLayout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCauHinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel56))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(pnlCauHinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel57))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCauHinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(jLabel58)))
        );

        pnlMainTieuChuan.add(pnlCauHinh, java.awt.BorderLayout.PAGE_END);

        pnlGridMay.setBackground(new java.awt.Color(25, 40, 50));
        pnlGridMay.setLayout(new java.awt.GridLayout(0, 4, 10, 10));
        pnlMainTieuChuan.add(pnlGridMay, java.awt.BorderLayout.CENTER);

        pnlTieuChuan.add(pnlMainTieuChuan, java.awt.BorderLayout.CENTER);

        pnlTabContent.add(pnlTieuChuan, "tieuChuan");

        pnlGaming.setMinimumSize(new java.awt.Dimension(40, 27));
        pnlGaming.setLayout(new java.awt.BorderLayout());

        pnlSidebarGaming.setBackground(new java.awt.Color(25, 40, 50));
        pnlSidebarGaming.setPreferredSize(new java.awt.Dimension(400, 592));

        pnlThongTin2.setBackground(new java.awt.Color(25, 40, 50));
        pnlThongTin2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Máy 21", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        pnlThongTin2.setPreferredSize(new java.awt.Dimension(400, 527));

        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Trạng thái:");

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Thời điểm bắt đầu:");

        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Thời gian sử dụng:");

        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Tạm tính:");

        pnlGFood.setBackground(new java.awt.Color(25, 40, 50));
        pnlGFood.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Food", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        pnlGFood.setPreferredSize(new java.awt.Dimension(50, 0));
        pnlGFood.setRequestFocusEnabled(false);

        scrG.setPreferredSize(new java.awt.Dimension(50, 0));

        tblGBang.setBackground(new java.awt.Color(119, 161, 119));
        tblGBang.setForeground(new java.awt.Color(255, 255, 255));
        tblGBang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên", "Đơn giá", "Số lượng", "Thành tiền"
            }
        ));
        scrG.setViewportView(tblGBang);
        if (tblGBang.getColumnModel().getColumnCount() > 0) {
            tblGBang.getColumnModel().getColumn(1).setMaxWidth(60);
            tblGBang.getColumnModel().getColumn(2).setMaxWidth(65);
            tblGBang.getColumnModel().getColumn(3).setMaxWidth(80);
        }

        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Tổng food:");

        javax.swing.GroupLayout pnlGFoodLayout = new javax.swing.GroupLayout(pnlGFood);
        pnlGFood.setLayout(pnlGFoodLayout);
        pnlGFoodLayout.setHorizontalGroup(
            pnlGFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGFoodLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlGFoodLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtGFood, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlGFoodLayout.setVerticalGroup(
            pnlGFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGFoodLayout.createSequentialGroup()
                .addComponent(scrG, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(pnlGFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtGFood, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Tổng hóa đơn:");

        btnThanhToan1.setBackground(new java.awt.Color(34, 139, 34));
        btnThanhToan1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThanhToan1.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToan1.setText("Thanh toán");
        btnThanhToan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToan1ActionPerformed(evt);
            }
        });

        btnBatMay1.setBackground(new java.awt.Color(34, 139, 34));
        btnBatMay1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBatMay1.setForeground(new java.awt.Color(255, 255, 255));
        btnBatMay1.setText("Bật máy");
        btnBatMay1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatMay1ActionPerformed(evt);
            }
        });

        btnGDichVu.setBackground(new java.awt.Color(34, 139, 34));
        btnGDichVu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGDichVu.setForeground(new java.awt.Color(255, 255, 255));
        btnGDichVu.setText("Dịch vụ");
        btnGDichVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGDichVuActionPerformed(evt);
            }
        });

        txtGTDBatDau.setPreferredSize(new java.awt.Dimension(68, 26));

        txtGTGSuDung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGTGSuDungActionPerformed(evt);
            }
        });

        btnGXoa.setBackground(new java.awt.Color(34, 139, 34));
        btnGXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnGXoa.setText("Xóa");
        btnGXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThongTin2Layout = new javax.swing.GroupLayout(pnlThongTin2);
        pnlThongTin2.setLayout(pnlThongTin2Layout);
        pnlThongTin2Layout.setHorizontalGroup(
            pnlThongTin2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTin2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThongTin2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTin2Layout.createSequentialGroup()
                        .addGroup(pnlThongTin2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlThongTin2Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(18, 18, 18)
                                .addComponent(txtGTDBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlThongTin2Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(63, 63, 63)
                                .addComponent(txtGTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(32, 32, 32)
                        .addGroup(pnlThongTin2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlThongTin2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnBatMay1)
                                .addComponent(btnGDichVu))
                            .addGroup(pnlThongTin2Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(btnGXoa))))
                    .addGroup(pnlThongTin2Layout.createSequentialGroup()
                        .addGroup(pnlThongTin2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18))
                        .addGap(18, 18, 18)
                        .addGroup(pnlThongTin2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtGTamTinh, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                            .addComponent(txtGTGSuDung)))
                    .addComponent(pnlGFood, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlThongTin2Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtGHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnThanhToan1)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        pnlThongTin2Layout.setVerticalGroup(
            pnlThongTin2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTin2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThongTin2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtGTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatMay1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlThongTin2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlThongTin2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(btnGDichVu))
                    .addComponent(txtGTDBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlThongTin2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtGTGSuDung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGXoa))
                .addGap(10, 10, 10)
                .addGroup(pnlThongTin2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtGTamTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlGFood, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlThongTin2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtGHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThanhToan1))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlSidebarGamingLayout = new javax.swing.GroupLayout(pnlSidebarGaming);
        pnlSidebarGaming.setLayout(pnlSidebarGamingLayout);
        pnlSidebarGamingLayout.setHorizontalGroup(
            pnlSidebarGamingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSidebarGamingLayout.createSequentialGroup()
                .addComponent(pnlThongTin2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlSidebarGamingLayout.setVerticalGroup(
            pnlSidebarGamingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSidebarGamingLayout.createSequentialGroup()
                .addComponent(pnlThongTin2, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 165, Short.MAX_VALUE))
        );

        pnlGaming.add(pnlSidebarGaming, java.awt.BorderLayout.LINE_END);

        pnlMainGaming.setBackground(new java.awt.Color(25, 40, 50));
        pnlMainGaming.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Gaming", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        pnlMainGaming.setForeground(new java.awt.Color(255, 255, 255));
        pnlMainGaming.setPreferredSize(new java.awt.Dimension(473, 148));
        pnlMainGaming.setLayout(new java.awt.BorderLayout());

        pnlCauHinhGaming.setBackground(new java.awt.Color(25, 40, 50));
        pnlCauHinhGaming.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Cấu hình", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel59.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setText("Màn hình LG 27GS95QE-B");

        jLabel60.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(255, 255, 255));
        jLabel60.setText("Intel Core i5-12400F ");

        jLabel61.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(255, 255, 255));
        jLabel61.setText("Samsung 980 Pro NVMe PCle M.2 500GB");

        jLabel62.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(255, 255, 255));
        jLabel62.setText("Chuột Corsair Harpoon");

        jLabel63.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(255, 255, 255));
        jLabel63.setText("Nvidia GTX 1660S(Super)");

        jLabel64.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(255, 255, 255));
        jLabel64.setText("WD Black 2TB");

        jLabel65.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(255, 255, 255));
        jLabel65.setText("Bàn phím Corsair K55");

        javax.swing.GroupLayout pnlCauHinhGamingLayout = new javax.swing.GroupLayout(pnlCauHinhGaming);
        pnlCauHinhGaming.setLayout(pnlCauHinhGamingLayout);
        pnlCauHinhGamingLayout.setHorizontalGroup(
            pnlCauHinhGamingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCauHinhGamingLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlCauHinhGamingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel60)
                    .addComponent(jLabel59)
                    .addComponent(jLabel61)
                    .addComponent(jLabel62))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 278, Short.MAX_VALUE)
                .addGroup(pnlCauHinhGamingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel63)
                    .addComponent(jLabel64)
                    .addComponent(jLabel65))
                .addGap(23, 23, 23))
        );
        pnlCauHinhGamingLayout.setVerticalGroup(
            pnlCauHinhGamingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCauHinhGamingLayout.createSequentialGroup()
                .addComponent(jLabel59)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCauHinhGamingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60)
                    .addComponent(jLabel63))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCauHinhGamingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel61)
                    .addComponent(jLabel64))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jLabel62))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCauHinhGamingLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel65)
                .addContainerGap())
        );

        pnlMainGaming.add(pnlCauHinhGaming, java.awt.BorderLayout.PAGE_END);

        pnlGridMay1.setBackground(new java.awt.Color(25, 40, 50));
        pnlGridMay1.setMinimumSize(new java.awt.Dimension(40, 10));
        pnlGridMay1.setPreferredSize(new java.awt.Dimension(40, 10));
        pnlGridMay1.setLayout(new java.awt.GridLayout(0, 4, 10, 10));
        pnlMainGaming.add(pnlGridMay1, java.awt.BorderLayout.CENTER);

        pnlGaming.add(pnlMainGaming, java.awt.BorderLayout.CENTER);

        pnlTabContent.add(pnlGaming, "gaming");

        pnlChuyenNghiep.setLayout(new java.awt.BorderLayout());

        pnlSidebarChuyenNghiep.setBackground(new java.awt.Color(25, 40, 50));
        pnlSidebarChuyenNghiep.setPreferredSize(new java.awt.Dimension(400, 592));

        pnlThongTin3.setBackground(new java.awt.Color(25, 40, 50));
        pnlThongTin3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Máy 31", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        pnlThongTin3.setPreferredSize(new java.awt.Dimension(400, 527));

        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Trạng thái:");

        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Thời điểm bắt đầu:");

        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Thời gian sử dụng:");

        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Tạm tính:");

        btnBatMay2.setBackground(new java.awt.Color(34, 139, 34));
        btnBatMay2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBatMay2.setForeground(new java.awt.Color(255, 255, 255));
        btnBatMay2.setText("Bật máy");
        btnBatMay2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatMay2ActionPerformed(evt);
            }
        });

        pnlCNFood.setBackground(new java.awt.Color(25, 40, 50));
        pnlCNFood.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Food", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        pnlCNFood.setPreferredSize(new java.awt.Dimension(150, 300));

        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Tổng food:");

        tblCNBang.setBackground(new java.awt.Color(119, 161, 119));
        tblCNBang.setForeground(new java.awt.Color(255, 255, 255));
        tblCNBang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên ", "Đơn giá", "Số lượng", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrCN.setViewportView(tblCNBang);
        if (tblCNBang.getColumnModel().getColumnCount() > 0) {
            tblCNBang.getColumnModel().getColumn(1).setMaxWidth(60);
            tblCNBang.getColumnModel().getColumn(2).setMaxWidth(65);
            tblCNBang.getColumnModel().getColumn(3).setMaxWidth(80);
        }

        javax.swing.GroupLayout pnlCNFoodLayout = new javax.swing.GroupLayout(pnlCNFood);
        pnlCNFood.setLayout(pnlCNFoodLayout);
        pnlCNFoodLayout.setHorizontalGroup(
            pnlCNFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCNFoodLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCNFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCNFoodLayout.createSequentialGroup()
                        .addComponent(scrCN, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(pnlCNFoodLayout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCNFood, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 16, Short.MAX_VALUE))))
        );
        pnlCNFoodLayout.setVerticalGroup(
            pnlCNFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCNFoodLayout.createSequentialGroup()
                .addComponent(scrCN, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCNFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txtCNFood, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Tổng hóa đơn:");

        btnCNThanhToan.setBackground(new java.awt.Color(34, 139, 34));
        btnCNThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCNThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnCNThanhToan.setText("Thanh toán");
        btnCNThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCNThanhToanActionPerformed(evt);
            }
        });

        btnCNDichVu.setBackground(new java.awt.Color(34, 139, 34));
        btnCNDichVu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCNDichVu.setForeground(new java.awt.Color(255, 255, 255));
        btnCNDichVu.setText("Dịch vụ");
        btnCNDichVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCNDichVuActionPerformed(evt);
            }
        });

        btnCNXoa.setBackground(new java.awt.Color(34, 139, 34));
        btnCNXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCNXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnCNXoa.setText("Xóa");
        btnCNXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCNXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThongTin3Layout = new javax.swing.GroupLayout(pnlThongTin3);
        pnlThongTin3.setLayout(pnlThongTin3Layout);
        pnlThongTin3Layout.setHorizontalGroup(
            pnlThongTin3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTin3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThongTin3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlCNFood, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlThongTin3Layout.createSequentialGroup()
                        .addGroup(pnlThongTin3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlThongTin3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel21)
                                .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel24))
                        .addGap(18, 18, 18)
                        .addGroup(pnlThongTin3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCNTrangThai)
                            .addComponent(txtCNTamTinh, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                            .addComponent(txtCNTDBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCNTGSuDung))
                        .addGap(18, 18, 18)
                        .addGroup(pnlThongTin3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBatMay2)
                            .addComponent(btnCNDichVu)
                            .addComponent(btnCNXoa)))
                    .addGroup(pnlThongTin3Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCNHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCNThanhToan)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlThongTin3Layout.setVerticalGroup(
            pnlThongTin3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTin3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThongTin3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtCNTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatMay2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlThongTin3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlThongTin3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel22)
                        .addComponent(btnCNDichVu))
                    .addComponent(txtCNTDBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(pnlThongTin3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtCNTGSuDung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCNXoa))
                .addGap(5, 5, 5)
                .addGroup(pnlThongTin3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtCNTamTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlCNFood, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTin3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(txtCNHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCNThanhToan))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlSidebarChuyenNghiepLayout = new javax.swing.GroupLayout(pnlSidebarChuyenNghiep);
        pnlSidebarChuyenNghiep.setLayout(pnlSidebarChuyenNghiepLayout);
        pnlSidebarChuyenNghiepLayout.setHorizontalGroup(
            pnlSidebarChuyenNghiepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSidebarChuyenNghiepLayout.createSequentialGroup()
                .addComponent(pnlThongTin3, javax.swing.GroupLayout.PREFERRED_SIZE, 394, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlSidebarChuyenNghiepLayout.setVerticalGroup(
            pnlSidebarChuyenNghiepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSidebarChuyenNghiepLayout.createSequentialGroup()
                .addComponent(pnlThongTin3, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 162, Short.MAX_VALUE))
        );

        pnlChuyenNghiep.add(pnlSidebarChuyenNghiep, java.awt.BorderLayout.LINE_END);

        pnlMainChuyenNghiep.setBackground(new java.awt.Color(25, 40, 50));
        pnlMainChuyenNghiep.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Chuyên nghiệp", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        pnlMainChuyenNghiep.setPreferredSize(new java.awt.Dimension(473, 148));
        pnlMainChuyenNghiep.setLayout(new java.awt.BorderLayout());

        pnlCauHinhChuyenNghiep.setBackground(new java.awt.Color(25, 40, 50));
        pnlCauHinhChuyenNghiep.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 100, 100), 2, true), "Cấu hình", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel66.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(255, 255, 255));
        jLabel66.setText("Màn hình Dell Alienware AW2725DF");

        jLabel67.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(255, 255, 255));
        jLabel67.setText("Intel Core i7-12700K");

        jLabel68.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(255, 255, 255));
        jLabel68.setText("Samsung 980 Pro NVMe PCle M.2 1TB");

        jLabel69.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(255, 255, 255));
        jLabel69.setText("Chuột Razer DeathAdder Elite");

        jLabel70.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(255, 255, 255));
        jLabel70.setText("Nvidia RTX 3060-Ti");

        jLabel71.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(255, 255, 255));
        jLabel71.setText("WD Black 2TB");

        jLabel72.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(255, 255, 255));
        jLabel72.setText("Bàn phím Razer BlackWindow Elite");

        javax.swing.GroupLayout pnlCauHinhChuyenNghiepLayout = new javax.swing.GroupLayout(pnlCauHinhChuyenNghiep);
        pnlCauHinhChuyenNghiep.setLayout(pnlCauHinhChuyenNghiepLayout);
        pnlCauHinhChuyenNghiepLayout.setHorizontalGroup(
            pnlCauHinhChuyenNghiepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCauHinhChuyenNghiepLayout.createSequentialGroup()
                .addGap(6, 132, Short.MAX_VALUE)
                .addGroup(pnlCauHinhChuyenNghiepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel66)
                    .addComponent(jLabel68)
                    .addComponent(jLabel69)
                    .addComponent(jLabel67))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 142, Short.MAX_VALUE)
                .addGroup(pnlCauHinhChuyenNghiepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel70)
                    .addComponent(jLabel72)
                    .addComponent(jLabel71))
                .addGap(12, 12, 12))
        );
        pnlCauHinhChuyenNghiepLayout.setVerticalGroup(
            pnlCauHinhChuyenNghiepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCauHinhChuyenNghiepLayout.createSequentialGroup()
                .addComponent(jLabel66)
                .addGap(12, 12, 12)
                .addGroup(pnlCauHinhChuyenNghiepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel67)
                    .addComponent(jLabel70))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCauHinhChuyenNghiepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel68)
                    .addComponent(jLabel71))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlCauHinhChuyenNghiepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel69)
                    .addComponent(jLabel72))
                .addContainerGap())
        );

        pnlMainChuyenNghiep.add(pnlCauHinhChuyenNghiep, java.awt.BorderLayout.PAGE_END);

        pnlGridMay2.setBackground(new java.awt.Color(25, 40, 50));
        pnlGridMay2.setLayout(new java.awt.GridLayout(0, 4, 10, 10));
        pnlMainChuyenNghiep.add(pnlGridMay2, java.awt.BorderLayout.CENTER);

        pnlChuyenNghiep.add(pnlMainChuyenNghiep, java.awt.BorderLayout.CENTER);

        pnlTabContent.add(pnlChuyenNghiep, "chuyenNghiep");

        pnlThiDau.setLayout(new java.awt.BorderLayout());

        pnlSidebarThiDau.setBackground(new java.awt.Color(25, 40, 50));
        pnlSidebarThiDau.setPreferredSize(new java.awt.Dimension(400, 592));

        pnlThongTin4.setBackground(new java.awt.Color(25, 40, 50));
        pnlThongTin4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Máy 11", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        pnlThongTin4.setPreferredSize(new java.awt.Dimension(400, 527));

        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Trạng thái:");

        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Thời điểm bắt đầu:");

        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Thời gian sử dụng:");

        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Tạm tính:");

        btnTDBatMay.setBackground(new java.awt.Color(34, 139, 34));
        btnTDBatMay.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTDBatMay.setForeground(new java.awt.Color(255, 255, 255));
        btnTDBatMay.setText("Bật máy");
        btnTDBatMay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTDBatMayActionPerformed(evt);
            }
        });

        pnlTDFood.setBackground(new java.awt.Color(25, 40, 50));
        pnlTDFood.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Food", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        tblTDBang.setBackground(new java.awt.Color(119, 161, 119));
        tblTDBang.setForeground(new java.awt.Color(255, 255, 255));
        tblTDBang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên", "Đơn giá", "Số lượng", "Thành tiền"
            }
        ));
        scrTD.setViewportView(tblTDBang);
        if (tblTDBang.getColumnModel().getColumnCount() > 0) {
            tblTDBang.getColumnModel().getColumn(1).setMaxWidth(60);
            tblTDBang.getColumnModel().getColumn(2).setMaxWidth(65);
            tblTDBang.getColumnModel().getColumn(3).setMaxWidth(80);
        }

        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Tổng food:");

        javax.swing.GroupLayout pnlTDFoodLayout = new javax.swing.GroupLayout(pnlTDFood);
        pnlTDFood.setLayout(pnlTDFoodLayout);
        pnlTDFoodLayout.setHorizontalGroup(
            pnlTDFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTDFoodLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTDFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrTD, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlTDFoodLayout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTDFood)))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        pnlTDFoodLayout.setVerticalGroup(
            pnlTDFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTDFoodLayout.createSequentialGroup()
                .addComponent(scrTD, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlTDFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(txtTDFood, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Tổng hóa đơn:");

        btnTDThanhToan.setBackground(new java.awt.Color(34, 139, 34));
        btnTDThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTDThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnTDThanhToan.setText("Thanh toán");
        btnTDThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTDThanhToanActionPerformed(evt);
            }
        });

        btnTDDichVu.setBackground(new java.awt.Color(34, 139, 34));
        btnTDDichVu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTDDichVu.setForeground(new java.awt.Color(255, 255, 255));
        btnTDDichVu.setText("Dịch vụ");
        btnTDDichVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTDDichVuActionPerformed(evt);
            }
        });

        btnTDXoa.setBackground(new java.awt.Color(34, 139, 34));
        btnTDXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTDXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnTDXoa.setText("Xóa");
        btnTDXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTDXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThongTin4Layout = new javax.swing.GroupLayout(pnlThongTin4);
        pnlThongTin4.setLayout(pnlThongTin4Layout);
        pnlThongTin4Layout.setHorizontalGroup(
            pnlThongTin4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTin4Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(pnlThongTin4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTin4Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTDHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTDThanhToan))
                    .addComponent(pnlTDFood, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlThongTin4Layout.createSequentialGroup()
                        .addGroup(pnlThongTin4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addComponent(jLabel28)
                            .addComponent(jLabel27)
                            .addComponent(jLabel30))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlThongTin4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTDTrangThai)
                            .addComponent(txtTDTamTinh)
                            .addComponent(txtTDTDBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                            .addComponent(txtTDTGSuDung))
                        .addGroup(pnlThongTin4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlThongTin4Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(pnlThongTin4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnTDBatMay)
                                    .addComponent(btnTDDichVu)))
                            .addGroup(pnlThongTin4Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(btnTDXoa))))))
        );
        pnlThongTin4Layout.setVerticalGroup(
            pnlThongTin4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTin4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThongTin4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(txtTDTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTDBatMay))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlThongTin4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlThongTin4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel28)
                        .addComponent(btnTDDichVu))
                    .addComponent(txtTDTDBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(pnlThongTin4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(txtTDTGSuDung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTDXoa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlThongTin4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(txtTDTamTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlTDFood, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlThongTin4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(txtTDHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTDThanhToan))
                .addContainerGap(77, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlSidebarThiDauLayout = new javax.swing.GroupLayout(pnlSidebarThiDau);
        pnlSidebarThiDau.setLayout(pnlSidebarThiDauLayout);
        pnlSidebarThiDauLayout.setHorizontalGroup(
            pnlSidebarThiDauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSidebarThiDauLayout.createSequentialGroup()
                .addComponent(pnlThongTin4, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlSidebarThiDauLayout.setVerticalGroup(
            pnlSidebarThiDauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSidebarThiDauLayout.createSequentialGroup()
                .addComponent(pnlThongTin4, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 167, Short.MAX_VALUE))
        );

        pnlThiDau.add(pnlSidebarThiDau, java.awt.BorderLayout.LINE_END);

        pnlMainThiDau.setBackground(new java.awt.Color(25, 40, 50));
        pnlMainThiDau.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Thi đấu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        pnlMainThiDau.setPreferredSize(new java.awt.Dimension(473, 148));
        pnlMainThiDau.setLayout(new java.awt.BorderLayout());

        pnlCauHinhThiDau.setBackground(new java.awt.Color(25, 40, 50));
        pnlCauHinhThiDau.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Cấu hình", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel73.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(255, 255, 255));
        jLabel73.setText("Màn hình Acer Nitro XV275K P3biipruzx");

        jLabel74.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(255, 255, 255));
        jLabel74.setText("Intel Core i7-12700KF");

        jLabel75.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(255, 255, 255));
        jLabel75.setText("Samsung 980 Pro NVMe PCle M.2 1TB");

        jLabel76.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(255, 255, 255));
        jLabel76.setText("Chuột Logitech G Pro Wireless");

        jLabel77.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(255, 255, 255));
        jLabel77.setText("Nvidia RTX 4060-Ti");

        jLabel78.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(255, 255, 255));
        jLabel78.setText("WD Black 2TB");

        jLabel79.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(255, 255, 255));
        jLabel79.setText("Bàn phím Logitech G915 TKL");

        javax.swing.GroupLayout pnlCauHinhThiDauLayout = new javax.swing.GroupLayout(pnlCauHinhThiDau);
        pnlCauHinhThiDau.setLayout(pnlCauHinhThiDauLayout);
        pnlCauHinhThiDauLayout.setHorizontalGroup(
            pnlCauHinhThiDauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCauHinhThiDauLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(pnlCauHinhThiDauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel73)
                    .addGroup(pnlCauHinhThiDauLayout.createSequentialGroup()
                        .addGroup(pnlCauHinhThiDauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel74)
                            .addComponent(jLabel75)
                            .addComponent(jLabel76))
                        .addGap(36, 36, 36)
                        .addGroup(pnlCauHinhThiDauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel77)
                            .addComponent(jLabel78)
                            .addComponent(jLabel79))))
                .addContainerGap(264, Short.MAX_VALUE))
        );
        pnlCauHinhThiDauLayout.setVerticalGroup(
            pnlCauHinhThiDauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCauHinhThiDauLayout.createSequentialGroup()
                .addComponent(jLabel73)
                .addGap(12, 12, 12)
                .addGroup(pnlCauHinhThiDauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel74)
                    .addComponent(jLabel77))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCauHinhThiDauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel75)
                    .addComponent(jLabel78))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jLabel76))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCauHinhThiDauLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel79)
                .addContainerGap())
        );

        pnlMainThiDau.add(pnlCauHinhThiDau, java.awt.BorderLayout.PAGE_END);

        pnlGridMay3.setBackground(new java.awt.Color(25, 40, 50));
        pnlGridMay3.setLayout(new java.awt.GridLayout(0, 4, 10, 10));
        pnlMainThiDau.add(pnlGridMay3, java.awt.BorderLayout.CENTER);

        pnlThiDau.add(pnlMainThiDau, java.awt.BorderLayout.CENTER);

        pnlTabContent.add(pnlThiDau, "thiDau");

        pnlMainContainer.add(pnlTabContent, java.awt.BorderLayout.CENTER);

        pnlQuanLyMay.add(pnlMainContainer, java.awt.BorderLayout.CENTER);

        pnl1.add(pnlQuanLyMay, "quanLyMay");

        pnlQuanLyDichVu.setBackground(new java.awt.Color(25, 40, 50));
        pnlQuanLyDichVu.setPreferredSize(new java.awt.Dimension(933, 631));

        tabDichVu.setBackground(new java.awt.Color(20, 35, 30));
        tabDichVu.setPreferredSize(new java.awt.Dimension(815, 330));

        jPanel11.setBackground(new java.awt.Color(20, 35, 30));

        jScrollPane5.setPreferredSize(new java.awt.Dimension(456, 406));

        tblDoAn.setBackground(new java.awt.Color(119, 161, 119));
        tblDoAn.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã dịch vụ", "Tên dịch vụ", "Loại dịch vụ", "Đơn giá", "Số lượng tồn", "Trạng thái", "Ảnh "
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDoAn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDoAnMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblDoAn);

        jPanel19.setBackground(new java.awt.Color(25, 40, 50));
        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Thông tin đồ ăn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jPanel27.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblAnhDoAn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhDoAnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnhDoAn, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnhDoAn, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
        );

        jLabel81.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(255, 255, 255));
        jLabel81.setText("Mã dịch vụ");

        jLabel82.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(255, 255, 255));
        jLabel82.setText("Tên dịch vụ:");

        jLabel83.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(255, 255, 255));
        jLabel83.setText("Loại dịch vụ:");

        jLabel84.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(255, 255, 255));
        jLabel84.setText("Đơn giá:");

        jLabel85.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(255, 255, 255));
        jLabel85.setText("Số lượng:");

        jLabel86.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel86.setForeground(new java.awt.Color(255, 255, 255));
        jLabel86.setText("Trạng thái:");

        cboTrangThaiDA.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Còn hàng", "Hết hàng" }));

        cboLoaiDVDA.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đồ ăn", "Đồ uống", "Khác" }));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel81)
                    .addComponent(jLabel82)
                    .addComponent(jLabel83))
                .addGap(33, 33, 33)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cboLoaiDVDA, 0, 156, Short.MAX_VALUE)
                    .addComponent(txtTenDVDA)
                    .addComponent(txtMaDVDA))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel84, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel85, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel86, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDonGiaDA)
                    .addComponent(txtSoLuongTonDA)
                    .addComponent(cboTrangThaiDA, 0, 150, Short.MAX_VALUE))
                .addContainerGap(163, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel81)
                            .addComponent(jLabel84)
                            .addComponent(txtDonGiaDA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaDVDA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel82)
                            .addComponent(txtTenDVDA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel85)
                            .addComponent(txtSoLuongTonDA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel83)
                            .addComponent(jLabel86)
                            .addComponent(cboTrangThaiDA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboLoaiDVDA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel20.setBackground(new java.awt.Color(25, 40, 50));
        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Xử lý", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        btnThemDA.setBackground(new java.awt.Color(34, 139, 34));
        btnThemDA.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemDA.setForeground(new java.awt.Color(255, 255, 255));
        btnThemDA.setText("Thêm ");
        btnThemDA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemDAActionPerformed(evt);
            }
        });

        btnCapNhatDA.setBackground(new java.awt.Color(34, 139, 34));
        btnCapNhatDA.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCapNhatDA.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhatDA.setText("Cập nhật");
        btnCapNhatDA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatDAActionPerformed(evt);
            }
        });

        btnXoaDA.setBackground(new java.awt.Color(34, 139, 34));
        btnXoaDA.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaDA.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaDA.setText("Xóa");
        btnXoaDA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaDAActionPerformed(evt);
            }
        });

        btnLamMoiDA.setBackground(new java.awt.Color(34, 139, 34));
        btnLamMoiDA.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoiDA.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoiDA.setText("Làm mới");
        btnLamMoiDA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiDAActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThemDA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCapNhatDA, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                    .addComponent(btnXoaDA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLamMoiDA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThemDA)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCapNhatDA)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXoaDA)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLamMoiDA)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1094, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabDichVu.addTab("Đồ ăn", jPanel11);

        jPanel12.setBackground(new java.awt.Color(20, 35, 30));

        jScrollPane6.setPreferredSize(new java.awt.Dimension(456, 406));

        tblDoUong.setBackground(new java.awt.Color(119, 161, 119));
        tblDoUong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã dịch vụ", "Tên dịch vụ", "Loại dịch vụ", "Đơn giá", "Số lượng tồn", "Trạng thái", "Ảnh"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDoUong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDoUongMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblDoUong);

        jPanel21.setBackground(new java.awt.Color(25, 40, 50));
        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Thông tin đồ uống", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jPanel28.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblAnhDoUong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhDoUongMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnhDoUong, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnhDoUong, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
        );

        jLabel87.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel87.setForeground(new java.awt.Color(255, 255, 255));
        jLabel87.setText("Mã dịch vụ:");

        jLabel88.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(255, 255, 255));
        jLabel88.setText("Tên dịch vụ:");

        jLabel89.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(255, 255, 255));
        jLabel89.setText("Loại dịch vụ:");

        cboLoaiDVDU.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đồ ăn", "Đồ uống", "Khác" }));
        cboLoaiDVDU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLoaiDVDUActionPerformed(evt);
            }
        });

        jLabel90.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(255, 255, 255));
        jLabel90.setText("Đơn giá:");

        jLabel91.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel91.setForeground(new java.awt.Color(255, 255, 255));
        jLabel91.setText("Số lượng tồn:");

        jLabel92.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel92.setForeground(new java.awt.Color(255, 255, 255));
        jLabel92.setText("Trạng thái:");

        cboTrangThaiDU.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Còn hàng", "Hết hàng" }));

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel87)
                        .addGap(18, 18, 18)
                        .addComponent(txtMaDVDU, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel88)
                        .addGap(18, 18, 18)
                        .addComponent(txtTenDVDU, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel89)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboLoaiDVDU, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(94, 94, 94)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel90)
                        .addGap(27, 27, 27)
                        .addComponent(txtDonGiaDU, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel91)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSoLuongTonDU, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel92)
                        .addGap(18, 18, 18)
                        .addComponent(cboTrangThaiDU, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel87)
                            .addComponent(txtMaDVDU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel90)
                            .addComponent(txtDonGiaDU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel88)
                            .addComponent(txtTenDVDU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel91)
                            .addComponent(txtSoLuongTonDU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel89)
                            .addComponent(cboLoaiDVDU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel92)
                            .addComponent(cboTrangThaiDU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel24.setBackground(new java.awt.Color(25, 40, 50));
        jPanel24.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Xử lý", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel24.setPreferredSize(new java.awt.Dimension(206, 27));

        btnThemDU.setBackground(new java.awt.Color(34, 139, 34));
        btnThemDU.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemDU.setForeground(new java.awt.Color(255, 255, 255));
        btnThemDU.setText("Thêm");
        btnThemDU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemDUActionPerformed(evt);
            }
        });

        btnCapNhatDU.setBackground(new java.awt.Color(34, 139, 34));
        btnCapNhatDU.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCapNhatDU.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhatDU.setText("Cập nhật");
        btnCapNhatDU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatDUActionPerformed(evt);
            }
        });

        btnXoaDU.setBackground(new java.awt.Color(34, 139, 34));
        btnXoaDU.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaDU.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaDU.setText("Xóa");
        btnXoaDU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaDUActionPerformed(evt);
            }
        });

        btnLamMoiDU.setBackground(new java.awt.Color(34, 139, 34));
        btnLamMoiDU.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoiDU.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoiDU.setText("Làm mới");
        btnLamMoiDU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiDUActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThemDU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCapNhatDU, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                    .addComponent(btnXoaDU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLamMoiDU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(btnThemDU)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCapNhatDU)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXoaDU)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLamMoiDU)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabDichVu.addTab("Đồ uống", jPanel12);

        jPanel13.setBackground(new java.awt.Color(20, 35, 30));

        jScrollPane7.setPreferredSize(new java.awt.Dimension(456, 406));

        tblDichVuKhac.setBackground(new java.awt.Color(119, 161, 119));
        tblDichVuKhac.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã dịch vụ", "Tên dịch vụ", "Loại dịch vụ", "Đơn giá", "Số lượng", "Trạng thái", "Ảnh"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDichVuKhac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDichVuKhacMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblDichVuKhac);

        jPanel22.setBackground(new java.awt.Color(25, 40, 50));
        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Thông tin dịch vụ khác", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jPanel29.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblAnhDichVu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhDichVuMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnhDichVu, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnhDichVu, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
        );

        jLabel93.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel93.setForeground(new java.awt.Color(255, 255, 255));
        jLabel93.setText("Mã dịch vụ:");

        jLabel94.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(255, 255, 255));
        jLabel94.setText("Tên dịch vụ:");

        jLabel95.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(255, 255, 255));
        jLabel95.setText("Loại dịch vụ:");

        cboLoaiDVK.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đồ ăn", "Đồ uống", "Khác" }));

        jLabel96.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel96.setForeground(new java.awt.Color(255, 255, 255));
        jLabel96.setText("Đơn giá:");

        jLabel97.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(255, 255, 255));
        jLabel97.setText("Số lượng:");

        jLabel98.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(255, 255, 255));
        jLabel98.setText("Trạng thái:");

        cboTrangThaiDVK.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Còn hàng", "Hết hàng" }));

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                            .addComponent(jLabel94)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                        .addGroup(jPanel22Layout.createSequentialGroup()
                            .addComponent(jLabel93)
                            .addGap(14, 14, 14)))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(jLabel95)
                        .addGap(12, 12, 12)))
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtMaDVK)
                    .addComponent(txtTenDVK)
                    .addComponent(cboLoaiDVK, 0, 148, Short.MAX_VALUE))
                .addGap(95, 95, 95)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                            .addComponent(jLabel97)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                        .addGroup(jPanel22Layout.createSequentialGroup()
                            .addComponent(jLabel96)
                            .addGap(17, 17, 17)))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(jLabel98)
                        .addGap(12, 12, 12)))
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtDonGiaDVK)
                    .addComponent(txtSoLuongDVK)
                    .addComponent(cboTrangThaiDVK, 0, 182, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel93)
                            .addComponent(txtMaDVK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel96)
                            .addComponent(txtDonGiaDVK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel94)
                            .addComponent(txtTenDVK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel97)
                            .addComponent(txtSoLuongDVK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel95)
                            .addComponent(cboLoaiDVK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel98)
                            .addComponent(cboTrangThaiDVK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel25.setBackground(new java.awt.Color(25, 40, 50));
        jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Xử lý", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        btnThemDVK.setBackground(new java.awt.Color(34, 139, 34));
        btnThemDVK.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemDVK.setForeground(new java.awt.Color(255, 255, 255));
        btnThemDVK.setText("Thêm");
        btnThemDVK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemDVKActionPerformed(evt);
            }
        });

        btnCapNhatDVK.setBackground(new java.awt.Color(34, 139, 34));
        btnCapNhatDVK.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCapNhatDVK.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhatDVK.setText("Cập nhật");
        btnCapNhatDVK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatDVKActionPerformed(evt);
            }
        });

        btnXoaDVk.setBackground(new java.awt.Color(34, 139, 34));
        btnXoaDVk.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaDVk.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaDVk.setText("Xóa");
        btnXoaDVk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaDVkActionPerformed(evt);
            }
        });

        btnLamMoiDVk.setBackground(new java.awt.Color(34, 139, 34));
        btnLamMoiDVk.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoiDVk.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoiDVk.setText("Làm mới");
        btnLamMoiDVk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiDVkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThemDVK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCapNhatDVK, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                    .addComponent(btnXoaDVk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLamMoiDVk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThemDVK)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCapNhatDVK)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXoaDVk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLamMoiDVk)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1094, Short.MAX_VALUE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabDichVu.addTab("Dịch vụ khác", jPanel13);

        jPanel34.setBackground(new java.awt.Color(20, 35, 30));

        jScrollPane2.setPreferredSize(new java.awt.Dimension(456, 406));

        tblNhapHang.setBackground(new java.awt.Color(119, 161, 119));
        tblNhapHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã nhập hàng", "Mã nhân viên", "Mã dịch vụ", "Ngày nhập", "Tên nhà cung cấp", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhapHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhapHangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblNhapHang);

        jPanel18.setBackground(new java.awt.Color(25, 40, 50));
        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Xử lý", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel18.setPreferredSize(new java.awt.Dimension(206, 183));

        btnThemNhapHang.setBackground(new java.awt.Color(34, 139, 34));
        btnThemNhapHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemNhapHang.setForeground(new java.awt.Color(255, 255, 255));
        btnThemNhapHang.setText("Thêm");
        btnThemNhapHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNhapHangActionPerformed(evt);
            }
        });

        btnCapNhatNhapHang.setBackground(new java.awt.Color(34, 139, 34));
        btnCapNhatNhapHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCapNhatNhapHang.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhatNhapHang.setText("Cập nhật");
        btnCapNhatNhapHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatNhapHangActionPerformed(evt);
            }
        });

        btnXoaNhapHang.setBackground(new java.awt.Color(34, 139, 34));
        btnXoaNhapHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaNhapHang.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaNhapHang.setText("Xóa");
        btnXoaNhapHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaNhapHangActionPerformed(evt);
            }
        });

        btnLamMoiNhapHang.setBackground(new java.awt.Color(34, 139, 34));
        btnLamMoiNhapHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoiNhapHang.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoiNhapHang.setText("Làm mới");
        btnLamMoiNhapHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiNhapHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThemNhapHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCapNhatNhapHang, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                    .addComponent(btnXoaNhapHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLamMoiNhapHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(btnThemNhapHang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCapNhatNhapHang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXoaNhapHang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLamMoiNhapHang)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel23.setBackground(new java.awt.Color(25, 40, 50));
        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Thông tin nhập hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel99.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel99.setForeground(new java.awt.Color(255, 255, 255));
        jLabel99.setText("Mã nhập hàng:");

        jLabel100.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(255, 255, 255));
        jLabel100.setText("Mã nhân viên:");

        jLabel101.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel101.setForeground(new java.awt.Color(255, 255, 255));
        jLabel101.setText("Mã dịch vụ:");

        jLabel102.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel102.setForeground(new java.awt.Color(255, 255, 255));
        jLabel102.setText("Ngày nhập:");

        jLabel103.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel103.setForeground(new java.awt.Color(255, 255, 255));
        jLabel103.setText("Tên nhà cung cấp:");

        jLabel104.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel104.setForeground(new java.awt.Color(255, 255, 255));
        jLabel104.setText("Số lượng:");

        txtSoLuongNhapHang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSoLuongNhapHangKeyReleased(evt);
            }
        });

        jLabel105.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel105.setForeground(new java.awt.Color(255, 255, 255));
        jLabel105.setText("Đơn giá nhập:");

        txtDonGiaNhapHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDonGiaNhapHangActionPerformed(evt);
            }
        });
        txtDonGiaNhapHang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDonGiaNhapHangKeyReleased(evt);
            }
        });

        jLabel106.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel106.setForeground(new java.awt.Color(255, 255, 255));
        jLabel106.setText("Thành tiền:");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel99)
                    .addComponent(jLabel100)
                    .addComponent(jLabel101)
                    .addComponent(jLabel102, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaNhapHang, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                    .addComponent(txtMaNVNhapHang)
                    .addComponent(txtMaDVNhapHang)
                    .addComponent(txtNgayNhapHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(96, 96, 96)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel103)
                    .addComponent(jLabel104)
                    .addComponent(jLabel105)
                    .addComponent(jLabel106))
                .addGap(32, 32, 32)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTenNhaCungCap)
                    .addComponent(txtSoLuongNhapHang)
                    .addComponent(txtDonGiaNhapHang)
                    .addComponent(txtThanhTienNhapHang, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
                .addContainerGap(180, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel99)
                    .addComponent(txtMaNhapHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel103)
                    .addComponent(txtTenNhaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel100)
                    .addComponent(txtMaNVNhapHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel104)
                    .addComponent(txtSoLuongNhapHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel101)
                    .addComponent(txtMaDVNhapHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel105)
                    .addComponent(txtDonGiaNhapHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel102)
                        .addComponent(jLabel106)
                        .addComponent(txtThanhTienNhapHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtNgayNhapHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(213, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabDichVu.addTab("Nhập hàng", jPanel34);

        javax.swing.GroupLayout pnlQuanLyDichVuLayout = new javax.swing.GroupLayout(pnlQuanLyDichVu);
        pnlQuanLyDichVu.setLayout(pnlQuanLyDichVuLayout);
        pnlQuanLyDichVuLayout.setHorizontalGroup(
            pnlQuanLyDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQuanLyDichVuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabDichVu, javax.swing.GroupLayout.DEFAULT_SIZE, 1106, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlQuanLyDichVuLayout.setVerticalGroup(
            pnlQuanLyDichVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQuanLyDichVuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabDichVu, javax.swing.GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnl1.add(pnlQuanLyDichVu, "quanLyDichVu");

        pnlQuanLyHoaDon.setBackground(new java.awt.Color(25, 40, 50));

        jPanel14.setBackground(new java.awt.Color(20, 35, 30));

        tblHoaDon.setBackground(new java.awt.Color(119, 161, 119));
        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Mã phiên", "Mã nhân viên", "Mã khách hàng", "Ngày tạo", "Tổng tiền máy", "Tổng tiền dịch vụ", "Tổng tiền", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tblHoaDon);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 1094, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane3.addTab("Hóa đơn ", jPanel14);

        jPanel15.setBackground(new java.awt.Color(25, 40, 50));

        tblhoaDonChiTiet.setBackground(new java.awt.Color(119, 161, 119));
        tblhoaDonChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Mã dịch vụ", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane10.setViewportView(tblhoaDonChiTiet);

        btnInHoaDon.setBackground(new java.awt.Color(34, 139, 34));
        btnInHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnInHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnInHoaDon.setText("In hóa đơn");
        btnInHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInHoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 1094, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnInHoaDon)
                .addGap(23, 23, 23))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnInHoaDon)
                .addGap(19, 19, 19))
        );

        jTabbedPane3.addTab("Hóa đơn chi tiết", jPanel15);

        jPanel30.setBackground(new java.awt.Color(25, 40, 50));

        tblLichSuGiaoDich.setBackground(new java.awt.Color(119, 161, 119));
        tblLichSuGiaoDich.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã giao dịch", "Mã khách hàng", "Loại giao dịch", "Số tiền", "Ngày giao dịch"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane11.setViewportView(tblLichSuGiaoDich);

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 1100, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane3.addTab("Lịch sử giao dịch", jPanel30);

        javax.swing.GroupLayout pnlQuanLyHoaDonLayout = new javax.swing.GroupLayout(pnlQuanLyHoaDon);
        pnlQuanLyHoaDon.setLayout(pnlQuanLyHoaDonLayout);
        pnlQuanLyHoaDonLayout.setHorizontalGroup(
            pnlQuanLyHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQuanLyHoaDonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3)
                .addContainerGap())
        );
        pnlQuanLyHoaDonLayout.setVerticalGroup(
            pnlQuanLyHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQuanLyHoaDonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3)
                .addContainerGap())
        );

        pnl1.add(pnlQuanLyHoaDon, "quanLyHoaDon");

        pnlQuanLyNhanVien.setBackground(new java.awt.Color(25, 40, 50));

        jScrollPane12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane12MouseClicked(evt);
            }
        });

        tblNhanVien.setBackground(new java.awt.Color(119, 161, 119));
        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã nhân viên", "Họ tên", "Số điện thoại", "Tên tài khoản", "Mật khẩu", "Chức vụ", "Trạng thái", "Ngày tạo", "Avata"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(tblNhanVien);

        jPanel1.setBackground(new java.awt.Color(25, 40, 50));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Thông tin nhân viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblAnhNV.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lblAnhNVAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        lblAnhNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhNVMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnhNV, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnhNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Mã nhân viên:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Họ tên:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Số điện thoại:");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("Chức vụ:");

        cboChucVuNV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Quản lý", "Nhân viên" }));

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("Tên tài khoản:");

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("Mật khẩu:");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("Trạng thái:");

        cboTrangThaiNV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Còn hoạt động", "Ngừng hoạt động" }));

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Ngày tạo:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSDTNV))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel34)
                            .addComponent(jLabel36))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenTaiKhoan)
                            .addComponent(cboTrangThaiNV, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtHoTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboChucVuNV, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel37)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(dchNgayTaoNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel35)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtMatKhauNV, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(91, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(txtHoTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtSDTNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33)
                            .addComponent(cboChucVuNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(txtTenTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35)
                            .addComponent(txtMatKhauNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel36)
                                .addComponent(cboTrangThaiNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel37))
                            .addComponent(dchNgayTaoNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(25, 40, 50));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Xử lý", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        btnThemNV.setBackground(new java.awt.Color(34, 139, 34));
        btnThemNV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemNV.setForeground(new java.awt.Color(255, 255, 255));
        btnThemNV.setText("Thêm");
        btnThemNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNVActionPerformed(evt);
            }
        });

        btnCapNhatNV.setBackground(new java.awt.Color(34, 139, 34));
        btnCapNhatNV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCapNhatNV.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhatNV.setText("Cập nhật");
        btnCapNhatNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatNVActionPerformed(evt);
            }
        });

        btnXoaNV.setBackground(new java.awt.Color(34, 139, 34));
        btnXoaNV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaNV.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaNV.setText("Xóa");
        btnXoaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaNVActionPerformed(evt);
            }
        });

        btnLamMoiNV.setBackground(new java.awt.Color(34, 139, 34));
        btnLamMoiNV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoiNV.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoiNV.setText("Làm mới");
        btnLamMoiNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiNVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThemNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCapNhatNV, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                    .addComponent(btnXoaNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLamMoiNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThemNV)
                .addGap(18, 18, 18)
                .addComponent(btnCapNhatNV)
                .addGap(18, 18, 18)
                .addComponent(btnXoaNV)
                .addGap(18, 18, 18)
                .addComponent(btnLamMoiNV)
                .addContainerGap(244, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlQuanLyNhanVienLayout = new javax.swing.GroupLayout(pnlQuanLyNhanVien);
        pnlQuanLyNhanVien.setLayout(pnlQuanLyNhanVienLayout);
        pnlQuanLyNhanVienLayout.setHorizontalGroup(
            pnlQuanLyNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQuanLyNhanVienLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlQuanLyNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane12)
                    .addGroup(pnlQuanLyNhanVienLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlQuanLyNhanVienLayout.setVerticalGroup(
            pnlQuanLyNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQuanLyNhanVienLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlQuanLyNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnl1.add(pnlQuanLyNhanVien, "quanLyNhanVien");

        pnlQuanLyBaoCao.setBackground(new java.awt.Color(25, 40, 50));

        jPanel35.setBackground(new java.awt.Color(100, 220, 140));
        jPanel35.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel111.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel111.setForeground(new java.awt.Color(255, 255, 255));
        jLabel111.setText("Doanh thu");

        lblDoanhThu.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblDoanhThu.setForeground(new java.awt.Color(255, 255, 255));
        lblDoanhThu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDoanhThu.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel111)
                .addContainerGap(85, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDoanhThu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel111)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDoanhThu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel26.setBackground(new java.awt.Color(60, 160, 90));
        jPanel26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel112.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel112.setForeground(new java.awt.Color(255, 255, 255));
        jLabel112.setText("Số máy");

        lblSoMay.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblSoMay.setForeground(new java.awt.Color(255, 255, 255));
        lblSoMay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSoMay.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(jLabel112)
                .addContainerGap(113, Short.MAX_VALUE))
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSoMay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel112)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSoMay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel33.setBackground(new java.awt.Color(80, 200, 120));
        jPanel33.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel118.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel118.setForeground(new java.awt.Color(255, 255, 255));
        jLabel118.setText("Số VIP");

        lblSoVIP.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblSoVIP.setForeground(new java.awt.Color(255, 255, 255));
        lblSoVIP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSoVIP.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(jLabel118, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(112, Short.MAX_VALUE))
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSoVIP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel118)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSoVIP, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel37.setBackground(new java.awt.Color(50, 120, 70));
        jPanel37.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel113.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel113.setForeground(new java.awt.Color(255, 255, 255));
        jLabel113.setText("Số dịch vụ");

        lblSoDichVu.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblSoDichVu.setForeground(new java.awt.Color(255, 255, 255));
        lblSoDichVu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSoDichVu.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(jLabel113)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSoDichVu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel113)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSoDichVu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel38.setBackground(new java.awt.Color(40, 50, 45));
        jPanel38.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel38.setPreferredSize(new java.awt.Dimension(159, 46));

        jLabel114.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel114.setForeground(new java.awt.Color(255, 255, 255));
        jLabel114.setText("Số nhân viên");

        lblSoNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblSoNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        lblSoNhanVien.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSoNhanVien.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSoNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel114)
                .addGap(59, 59, 59))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel114)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSoNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel39.setBackground(new java.awt.Color(34, 139, 34));
        jPanel39.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel117.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel117.setForeground(new java.awt.Color(255, 255, 255));
        jLabel117.setText("Số lần bảo trì");

        lblSoLanBaoTri.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblSoLanBaoTri.setForeground(new java.awt.Color(255, 255, 255));
        lblSoLanBaoTri.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSoLanBaoTri.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jLabel117)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSoLanBaoTri, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel117)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSoLanBaoTri, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel40.setBackground(new java.awt.Color(60, 160, 90));
        jPanel40.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel116.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel116.setForeground(new java.awt.Color(255, 255, 255));
        jLabel116.setText("Số lần nạp tiền");

        lblSoLanNap.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblSoLanNap.setForeground(new java.awt.Color(255, 255, 255));
        lblSoLanNap.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSoLanNap.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel116)
                .addContainerGap(69, Short.MAX_VALUE))
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSoLanNap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel116)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSoLanNap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel41.setBackground(new java.awt.Color(50, 120, 70));
        jPanel41.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel41.setPreferredSize(new java.awt.Dimension(159, 46));

        jLabel115.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel115.setForeground(new java.awt.Color(255, 255, 255));
        jLabel115.setText("Số giao dịch");

        lblSoGiaoDich.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblSoGiaoDich.setForeground(new java.awt.Color(255, 255, 255));
        lblSoGiaoDich.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSoGiaoDich.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSoGiaoDich, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel115)
                .addGap(61, 61, 61))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel115)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSoGiaoDich, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel42.setBackground(new java.awt.Color(40, 50, 45));
        jPanel42.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Lọc & Báo cáo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel119.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel119.setForeground(new java.awt.Color(255, 255, 255));
        jLabel119.setText("Loại báo cáo:");

        cboBaoCao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "VIP", "Máy", "Dịch vụ", "Nhân viên", "Doanh Thu", "Bảo trì", "Nạp tiền", "Giao dịch" }));
        cboBaoCao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboBaoCaoActionPerformed(evt);
            }
        });

        jLabel120.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel120.setForeground(new java.awt.Color(255, 255, 255));
        jLabel120.setText("Từ ngày:");

        jLabel121.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel121.setForeground(new java.awt.Color(255, 255, 255));
        jLabel121.setText("Đến ngày:");

        btnLoc.setBackground(new java.awt.Color(34, 139, 34));
        btnLoc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLoc.setForeground(new java.awt.Color(255, 255, 255));
        btnLoc.setText("Lọc");
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });

        btnLamMoi.setBackground(new java.awt.Color(34, 139, 34));
        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel119)
                .addGap(18, 18, 18)
                .addComponent(cboBaoCao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel120)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dateTuNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel121)
                .addGap(12, 12, 12)
                .addComponent(dateDenNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLoc)
                .addGap(18, 18, 18)
                .addComponent(btnLamMoi)
                .addContainerGap(254, Short.MAX_VALUE))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel42Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dateDenNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateTuNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel119)
                                .addComponent(cboBaoCao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel120)
                                .addComponent(jLabel121)))))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel43.setBackground(new java.awt.Color(25, 40, 50));
        jPanel43.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Chi tiết báo cáo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        tblBaoCao.setBackground(new java.awt.Color(119, 161, 119));
        tblBaoCao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã VIP", "Khách Hàng", "Loại VIP", "Tổng Chi Tiêu ", "Ngày Tham Gia", "Ngày Hết Hạn", "Trạng Thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane8.setViewportView(tblBaoCao);

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8)
                .addContainerGap())
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlQuanLyBaoCaoLayout = new javax.swing.GroupLayout(pnlQuanLyBaoCao);
        pnlQuanLyBaoCao.setLayout(pnlQuanLyBaoCaoLayout);
        pnlQuanLyBaoCaoLayout.setHorizontalGroup(
            pnlQuanLyBaoCaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQuanLyBaoCaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlQuanLyBaoCaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel42, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlQuanLyBaoCaoLayout.createSequentialGroup()
                        .addGroup(pnlQuanLyBaoCaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlQuanLyBaoCaoLayout.createSequentialGroup()
                                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlQuanLyBaoCaoLayout.createSequentialGroup()
                                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlQuanLyBaoCaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlQuanLyBaoCaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                            .addComponent(jPanel38, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pnlQuanLyBaoCaoLayout.setVerticalGroup(
            pnlQuanLyBaoCaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQuanLyBaoCaoLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(pnlQuanLyBaoCaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel38, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlQuanLyBaoCaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnl1.add(pnlQuanLyBaoCao, "quanLyBaoCao");

        pnlCaiDat.setBackground(new java.awt.Color(20, 35, 30));

        btnThongTinHeThong.setBackground(new java.awt.Color(34, 139, 34));
        btnThongTinHeThong.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnThongTinHeThong.setForeground(new java.awt.Color(255, 255, 255));
        btnThongTinHeThong.setText("THÔNG TIN HỆ THỐNG");
        btnThongTinHeThong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongTinHeThongActionPerformed(evt);
            }
        });

        btnDoiMatKhau.setBackground(new java.awt.Color(34, 139, 34));
        btnDoiMatKhau.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDoiMatKhau.setForeground(new java.awt.Color(255, 255, 255));
        btnDoiMatKhau.setText("ĐỔI MẬT KHẨU");
        btnDoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiMatKhauActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCaiDatLayout = new javax.swing.GroupLayout(pnlCaiDat);
        pnlCaiDat.setLayout(pnlCaiDatLayout);
        pnlCaiDatLayout.setHorizontalGroup(
            pnlCaiDatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCaiDatLayout.createSequentialGroup()
                .addGap(131, 131, 131)
                .addGroup(pnlCaiDatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDoiMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 852, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThongTinHeThong, javax.swing.GroupLayout.PREFERRED_SIZE, 852, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(135, Short.MAX_VALUE))
        );
        pnlCaiDatLayout.setVerticalGroup(
            pnlCaiDatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCaiDatLayout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(btnDoiMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98)
                .addComponent(btnThongTinHeThong, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(286, Short.MAX_VALUE))
        );

        pnl1.add(pnlCaiDat, "caiDat");

        pnlQuanLyBaoTri.setBackground(new java.awt.Color(25, 40, 50));
        pnlQuanLyBaoTri.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlQuanLyBaoTriMouseClicked(evt);
            }
        });

        jPanel16.setBackground(new java.awt.Color(25, 40, 50));
        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Bảo trì", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel16.setLayout(new java.awt.GridLayout(1, 0));

        pnlGridMayBaoTri.setBackground(new java.awt.Color(25, 40, 50));

        javax.swing.GroupLayout pnlGridMayBaoTriLayout = new javax.swing.GroupLayout(pnlGridMayBaoTri);
        pnlGridMayBaoTri.setLayout(pnlGridMayBaoTriLayout);
        pnlGridMayBaoTriLayout.setHorizontalGroup(
            pnlGridMayBaoTriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 706, Short.MAX_VALUE)
        );
        pnlGridMayBaoTriLayout.setVerticalGroup(
            pnlGridMayBaoTriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 733, Short.MAX_VALUE)
        );

        jPanel16.add(pnlGridMayBaoTri);

        jPanel32.setBackground(new java.awt.Color(25, 40, 50));
        jPanel32.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Máy 01", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Trạng thái:");

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Bộ phận bảo trì:");

        cboBoPhanBT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CPU", "VGA", "RAM", "HDD", "SSD", "Bàn phím", "Chuột", "Màn hình", "Phần mềm", "Khác", "Tai nghe", "Ghế" }));

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Mô tả(nếu có):");

        pnlDSBaoTri.setBackground(new java.awt.Color(25, 40, 50));
        pnlDSBaoTri.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Danh sách bảo trì", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        tblBaoTri.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Bộ phận", "Mô tả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblBaoTri);

        javax.swing.GroupLayout pnlDSBaoTriLayout = new javax.swing.GroupLayout(pnlDSBaoTri);
        pnlDSBaoTri.setLayout(pnlDSBaoTriLayout);
        pnlDSBaoTriLayout.setHorizontalGroup(
            pnlDSBaoTriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
        );
        pnlDSBaoTriLayout.setVerticalGroup(
            pnlDSBaoTriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
        );

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Tổng chi phí:");

        btnBTThanhToan.setBackground(new java.awt.Color(34, 139, 34));
        btnBTThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBTThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnBTThanhToan.setText("Thanh toán");
        btnBTThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBTThanhToanActionPerformed(evt);
            }
        });

        btnNutBaoTri.setBackground(new java.awt.Color(34, 139, 34));
        btnNutBaoTri.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNutBaoTri.setForeground(new java.awt.Color(255, 255, 255));
        btnNutBaoTri.setText("Bảo trì");
        btnNutBaoTri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNutBaoTriActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel32Layout.createSequentialGroup()
                            .addComponent(jLabel42)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtBTTongChiPhi, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(37, 37, 37)
                            .addComponent(btnBTThanhToan))
                        .addGroup(jPanel32Layout.createSequentialGroup()
                            .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel40)
                                .addComponent(jLabel41)
                                .addComponent(jLabel39))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cboBoPhanBT, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtMoTaBaoTri)
                                .addComponent(txtBTTrangThai))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnNutBaoTri)
                            .addGap(33, 33, 33)))
                    .addComponent(pnlDSBaoTri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(btnNutBaoTri)
                    .addComponent(txtBTTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(cboBoPhanBT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(txtMoTaBaoTri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlDSBaoTri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(txtBTTongChiPhi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBTThanhToan))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel109.setBackground(new java.awt.Color(255, 255, 255));
        jLabel109.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel109.setForeground(new java.awt.Color(255, 255, 255));
        jLabel109.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ql/net/icons/xanh_nhat.png"))); // NOI18N
        jLabel109.setText("Đang sử dụng");

        jLabel38.setBackground(new java.awt.Color(255, 255, 255));
        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ql/net/icons/mau_do.png"))); // NOI18N
        jLabel38.setText("Đang bảo trì");

        jLabel110.setBackground(new java.awt.Color(255, 255, 255));
        jLabel110.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel110.setForeground(new java.awt.Color(255, 255, 255));
        jLabel110.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ql/net/icons/mau_trong.png"))); // NOI18N
        jLabel110.setText("Máy trống");

        javax.swing.GroupLayout pnlQuanLyBaoTriLayout = new javax.swing.GroupLayout(pnlQuanLyBaoTri);
        pnlQuanLyBaoTri.setLayout(pnlQuanLyBaoTriLayout);
        pnlQuanLyBaoTriLayout.setHorizontalGroup(
            pnlQuanLyBaoTriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQuanLyBaoTriLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlQuanLyBaoTriLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel109)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel110)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlQuanLyBaoTriLayout.setVerticalGroup(
            pnlQuanLyBaoTriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQuanLyBaoTriLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlQuanLyBaoTriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel109)
                    .addComponent(jLabel38)
                    .addComponent(jLabel110))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlQuanLyBaoTriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnl1.add(pnlQuanLyBaoTri, "quanLyBaoTri");

        pnlQuanLyKhachHang.setBackground(new java.awt.Color(25, 40, 50));

        jPanel5.setBackground(new java.awt.Color(20, 35, 30));

        tblBangKhachHang.setBackground(new java.awt.Color(119, 161, 119));
        tblBangKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã khách hàng", "Họ tên", "Số điện thoại", "Số dư tài khoản", "Trạng thái", "Avata"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBangKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBangKhachHangMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblBangKhachHang);

        jPanel4.setBackground(new java.awt.Color(25, 40, 50));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Xử lý", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        btnThemKH.setBackground(new java.awt.Color(34, 139, 34));
        btnThemKH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemKH.setForeground(new java.awt.Color(255, 255, 255));
        btnThemKH.setText("Thêm");
        btnThemKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKHActionPerformed(evt);
            }
        });

        btnCapNhatKH.setBackground(new java.awt.Color(34, 139, 34));
        btnCapNhatKH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCapNhatKH.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhatKH.setText("Cập nhật");
        btnCapNhatKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatKHActionPerformed(evt);
            }
        });

        btnXoaKH.setBackground(new java.awt.Color(34, 139, 34));
        btnXoaKH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaKH.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaKH.setText("Xóa");
        btnXoaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaKHActionPerformed(evt);
            }
        });

        btnLamMoiKH.setBackground(new java.awt.Color(34, 139, 34));
        btnLamMoiKH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoiKH.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoiKH.setText("Làm mới");
        btnLamMoiKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiKHActionPerformed(evt);
            }
        });

        btnNapTienKH.setBackground(new java.awt.Color(34, 139, 34));
        btnNapTienKH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNapTienKH.setForeground(new java.awt.Color(255, 255, 255));
        btnNapTienKH.setText("Nạp tiền");
        btnNapTienKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNapTienKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThemKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCapNhatKH, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                    .addComponent(btnXoaKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLamMoiKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNapTienKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(btnThemKH)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCapNhatKH)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoaKH)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLamMoiKH)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNapTienKH)
                .addContainerGap(205, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(25, 40, 50));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Thông tin khách hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jPanel8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblAnhKH.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lblAnhKHAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        lblAnhKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhKHMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnhKH, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnhKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("Mã khách hàng:");

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("Số dư tài khoản:");

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("Số điện thoại:");

        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("Họ tên khách:");

        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("Trạng thái:");

        cboTrangThaiKH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hoạt động", "Ngừng hoạt động" }));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45)
                    .addComponent(jLabel46)
                    .addComponent(jLabel47)
                    .addComponent(jLabel44))
                .addGap(26, 26, 26)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboTrangThaiKH, 0, 455, Short.MAX_VALUE)
                    .addComponent(txtHoTenKH)
                    .addComponent(txtSDTKH)
                    .addComponent(txtSDTKKH)
                    .addComponent(txtMaKH))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(txtSDTKKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(txtSDTKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(txtHoTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(cboTrangThaiKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 204, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Danh sách khách hàng", jPanel5);

        jPanel6.setBackground(new java.awt.Color(20, 35, 30));

        tblBangKhachVIP.setBackground(new java.awt.Color(119, 161, 119));
        tblBangKhachVIP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã VIP", "Mã khách hàng", "Loại VIP", "Tổng chi tiêu", "Ngày tham gia", "Ngày hết hạn", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBangKhachVIP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBangKhachVIPMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblBangKhachVIP);

        jPanel9.setBackground(new java.awt.Color(25, 40, 50));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 100, 100), 2), "Xử lý", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        btnThemVIP.setBackground(new java.awt.Color(34, 139, 34));
        btnThemVIP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemVIP.setForeground(new java.awt.Color(255, 255, 255));
        btnThemVIP.setText("Thêm");
        btnThemVIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemVIPActionPerformed(evt);
            }
        });

        btnCapNhatVIP.setBackground(new java.awt.Color(34, 139, 34));
        btnCapNhatVIP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCapNhatVIP.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhatVIP.setText("Cập nhật");
        btnCapNhatVIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatVIPActionPerformed(evt);
            }
        });

        btnXoaVIP.setBackground(new java.awt.Color(34, 139, 34));
        btnXoaVIP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaVIP.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaVIP.setText("Xóa");
        btnXoaVIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaVIPActionPerformed(evt);
            }
        });

        btnLamMoiVIP.setBackground(new java.awt.Color(34, 139, 34));
        btnLamMoiVIP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoiVIP.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoiVIP.setText("Làm mới");
        btnLamMoiVIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiVIPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThemVIP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCapNhatVIP, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                    .addComponent(btnXoaVIP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLamMoiVIP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(btnThemVIP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCapNhatVIP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoaVIP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLamMoiVIP)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(25, 40, 50));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 100, 100), 2, true), "Thông tin khách VIP", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("Mã VIP:");

        jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setText("Mã khách hàng:");

        jLabel50.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText("Loại VIP:");

        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText("Trạng thái:");

        cboTrangThaiVip.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Còn hạn", "Hết hạn" }));

        jLabel52.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setText("Ngày tham gia:");

        jLabel53.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setText("Ngày hết hạn:");

        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setText("Tổng chi tiêu:");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel50))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtMaVIP)
                                    .addComponent(txtLoaiVip, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
                                .addGap(105, 105, 105))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel52)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNgayThamGiaVip, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                                .addGap(49, 49, 49)))
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel49)
                            .addComponent(jLabel51)
                            .addComponent(jLabel53))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaKH_VIP)
                            .addComponent(cboTrangThaiVip, 0, 195, Short.MAX_VALUE)
                            .addComponent(txtNgayHetHanVip, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel54)
                        .addGap(18, 18, 18)
                        .addComponent(txtTongChiTieu, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(txtMaVIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49)
                    .addComponent(txtMaKH_VIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(txtLoaiVip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51)
                    .addComponent(cboTrangThaiVip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel52)
                        .addComponent(jLabel53))
                    .addComponent(txtNgayThamGiaVip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgayHetHanVip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel54)
                    .addComponent(txtTongChiTieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(224, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1094, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Danh sách thành viên VIP", jPanel6);

        javax.swing.GroupLayout pnlQuanLyKhachHangLayout = new javax.swing.GroupLayout(pnlQuanLyKhachHang);
        pnlQuanLyKhachHang.setLayout(pnlQuanLyKhachHangLayout);
        pnlQuanLyKhachHangLayout.setHorizontalGroup(
            pnlQuanLyKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQuanLyKhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        pnlQuanLyKhachHangLayout.setVerticalGroup(
            pnlQuanLyKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQuanLyKhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pnl1.add(pnlQuanLyKhachHang, "quanLyKhachHang");

        getContentPane().add(pnl1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnQuanLyDichVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuanLyDichVuActionPerformed
        // TODO add your handling code here:
        cardLayout.show(pnl1, "quanLyDichVu");
        setActiveButton(btnQuanLyDichVu);
    }//GEN-LAST:event_btnQuanLyDichVuActionPerformed

    private void btnQuanLyMayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuanLyMayActionPerformed
        // TODO add your handling code here:
        cardLayout.show(pnl1, "quanLyMay");
        setActiveButton(btnQuanLyMay);
    }//GEN-LAST:event_btnQuanLyMayActionPerformed

    private void btnQuanLyKhachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuanLyKhachActionPerformed
        // TODO add your handling code here:
        cardLayout.show(pnl1, "quanLyKhachHang");
        setActiveButton(btnQuanLyKhach);
    }//GEN-LAST:event_btnQuanLyKhachActionPerformed

    private void btnQuanLyHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuanLyHoaDonActionPerformed
        // TODO add your handling code here:
        cardLayout.show(pnl1, "quanLyHoaDon");
        setActiveButton(btnQuanLyHoaDon);
    }//GEN-LAST:event_btnQuanLyHoaDonActionPerformed

    private void btnQuanLyNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuanLyNhanVienActionPerformed
        // TODO add your handling code here:
        cardLayout.show(pnl1, "quanLyNhanVien");
        setActiveButton(btnQuanLyNhanVien);
    }//GEN-LAST:event_btnQuanLyNhanVienActionPerformed

    private void btnCaiDatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCaiDatActionPerformed
        // TODO add your handling code here:
        cardLayout.show(pnl1, "caiDat");
        setActiveButton(btnCaiDat);
    }//GEN-LAST:event_btnCaiDatActionPerformed

    private void btnTieuChuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTieuChuanActionPerformed
        // TODO add your handling code here:
        switchToTab("tieuChuan"); 
//        loadMayFromSQl("Tiêu chuẩn");
        if (!loadedTieuChuan) {
            loadMayFromSQl("Tiêu chuẩn");
            loadedTieuChuan = true;
        }
    }//GEN-LAST:event_btnTieuChuanActionPerformed

    private void btnGamingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGamingActionPerformed
        // TODO add your handling code here:
        switchToTab("gaming"); 
        if (!loadedGaming) {
            loadMayFromSQl("Gaming");
            loadedGaming = true;
        }
    }//GEN-LAST:event_btnGamingActionPerformed

    private void btnChuyenNghiepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChuyenNghiepActionPerformed
        // TODO add your handling code here:
        switchToTab("chuyenNghiep"); 
        if (!loadedChuyenNghiep) {
            loadMayFromSQl("Chuyên nghiệp");
            loadedChuyenNghiep = true;
        }
    }//GEN-LAST:event_btnChuyenNghiepActionPerformed

    private void btnThiDauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThiDauActionPerformed
        // TODO add your handling code here:
        switchToTab("thiDau"); 
        if (!loadedThiDau) {
            loadMayFromSQl("Thi đấu");
            loadedThiDau = true;
        }
    }//GEN-LAST:event_btnThiDauActionPerformed

    private void btnCNThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCNThanhToanActionPerformed
        // TODO add your handling code here:
        thanhToanMay("Chuyên nghiệp");
    }//GEN-LAST:event_btnCNThanhToanActionPerformed

    private void btnLamMoiKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiKHActionPerformed
        // TODO add your handling code here:
        lamMoiFormKH();
    }//GEN-LAST:event_btnLamMoiKHActionPerformed

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        // TODO add your handling code here:
        int result = JOptionPane.showConfirmDialog(
            HomeJFrame.this,
            "Bạn có chắc chắn muốn đăng xuất không?",
            "Xác nhận đăng xuất",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        if (result == JOptionPane.OK_OPTION) {
            dispose();
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    ql.net.ui.LoginJDialog login = new ql.net.ui.LoginJDialog(null, true);
                    login.setVisible(true);
                    if (login.isLoginSuccess()) {
                        ql.net.entity.NhanVien nv = login.getLoggedNhanVien();
                        HomeJFrame home = new HomeJFrame(nv);
                        home.setVisible(true);
                    } 
                }
            });
        }
    }//GEN-LAST:event_btnDangXuatActionPerformed

    private void lblLogoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogoMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblLogoMouseEntered

    private void cboLoaiDVDUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLoaiDVDUActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboLoaiDVDUActionPerformed

    private void btnDoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiMatKhauActionPerformed
        // TODO add your handling code here:
        ChangePasswordJDialog dialog = new ChangePasswordJDialog(this,true,curentUser);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }//GEN-LAST:event_btnDoiMatKhauActionPerformed

    private void pnlQuanLyBaoTriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlQuanLyBaoTriMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlQuanLyBaoTriMouseClicked

    private void tblBangKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBangKhachHangMouseClicked
        // TODO add your handling code here:
        int row = tblBangKhachHang.getSelectedRow();
        if (row >= 0) {
            // Lấy thông tin từ bảng
            String maKH = tblBangKhachHang.getValueAt(row, 0).toString();
            String hoTen = tblBangKhachHang.getValueAt(row, 1).toString();
            String soDT = tblBangKhachHang.getValueAt(row, 2).toString();
            String soDu = tblBangKhachHang.getValueAt(row, 3).toString();
            String trangThai = tblBangKhachHang.getValueAt(row, 4).toString();
            Object imageObj = tblBangKhachHang.getValueAt(row, 5);

            txtMaKH.setText(maKH);
            txtHoTenKH.setText(hoTen);
            txtSDTKH.setText(soDT);
            txtSDTKKH.setText(soDu);
            cboTrangThaiKH.setSelectedItem(trangThai);

            // Hiển thị ảnh khách hàng (tự động thử cả .jpg và .png)
            if (imageObj != null && imageObj.toString().trim().length() > 0) {
                String imageName = imageObj.toString().trim();
                boolean found = false;

                // Thử đúng tên từ SQL
                java.net.URL resource = getClass().getResource("/ql/net/icons/" + imageName);
                if (resource != null) {
                    ql.net.util.XIcon.setIcon(lblAnhKH, "/ql/net/icons/" + imageName);
                    found = true;
                } else {
                    // Nếu không có, thử đổi đuôi .jpg/.png
                    String baseName = imageName.contains(".") ? imageName.substring(0, imageName.lastIndexOf('.')) : imageName;
                    String[] exts = {".jpg", ".png"};
                    for (String ext : exts) {
                        String tryName = baseName + ext;
                        resource = getClass().getResource("/ql/net/icons/" + tryName);
                        if (resource != null) {
                            ql.net.util.XIcon.setIcon(lblAnhKH, "/ql/net/icons/" + tryName);
                            found = true;
                            break;
                        }
                    }
                }
                if (!found) {
                    lblAnhKH.setIcon(null); // Không có ảnh
                }
            } else {
                lblAnhKH.setIcon(null); // Không có ảnh
            }
            txtMaKH_VIP.setText(maKH); // Đổ mã khách hàng sang
        }
    }//GEN-LAST:event_tblBangKhachHangMouseClicked

    private void tblBangKhachVIPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBangKhachVIPMouseClicked
        // TODO add your handling code here:
        int row = tblBangKhachVIP.getSelectedRow();
        if (row >= 0) {
            txtMaVIP.setText(tblBangKhachVIP.getValueAt(row, 0).toString());
            txtMaKH_VIP.setText(tblBangKhachVIP.getValueAt(row, 1).toString());
            txtLoaiVip.setText(tblBangKhachVIP.getValueAt(row, 2).toString());
            txtTongChiTieu.setText(tblBangKhachVIP.getValueAt(row, 3).toString());
//            txtNgayThamGiaVip.setText(tblBangKhachVIP.getValueAt(row, 4).toString());
//            txtNgayHetHanVip.setText(tblBangKhachVIP.getValueAt(row, 5).toString());
            
            String ngayThamGia = tblBangKhachVIP.getValueAt(row, 4) != null ? tblBangKhachVIP.getValueAt(row, 4).toString() : "";
            String ngayHetHan = tblBangKhachVIP.getValueAt(row, 5) != null ? tblBangKhachVIP.getValueAt(row, 5).toString() : "";    
            
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            try {
                if (!ngayThamGia.trim().isEmpty()) {
                    txtNgayThamGiaVip.setDate(sdf.parse(ngayThamGia));
                } else {
                    txtNgayThamGiaVip.setDate(null);
                }
                if (!ngayHetHan.trim().isEmpty()) {
                    txtNgayHetHanVip.setDate(sdf.parse(ngayHetHan));
                } else {
                    txtNgayHetHanVip.setDate(null);
                }
            } catch (Exception e) {
                txtNgayThamGiaVip.setDate(null);
                txtNgayHetHanVip.setDate(null);
                System.out.println("Lỗi"+e);
            }
            cboTrangThaiVip.setSelectedItem(tblBangKhachVIP.getValueAt(row, 6).toString());
        }
    }//GEN-LAST:event_tblBangKhachVIPMouseClicked

    private void lblAnhKHAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lblAnhKHAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_lblAnhKHAncestorAdded

    private void lblAnhKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhKHMouseClicked
        // TODO add your handling code here
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Chọn ảnh khách hàng");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Ảnh JPG, PNG", "jpg", "jpeg", "png"));
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            java.io.File selectedFile = chooser.getSelectedFile();
            String fileName = selectedFile.getName();
            // Copy file vào resource (nếu muốn quản lý tập trung)
            java.io.File copiedFile = ql.net.util.XIcon.copyTo(selectedFile, "src/main/resources/ql/net/icons");
            // Hiển thị ảnh mới lên label
            ql.net.util.XIcon.setIcon(lblAnhKH, copiedFile.getAbsolutePath());
            // Lưu tên file ảnh mới vào biến tạm
            fileAnhMoi = fileName;
            // Cập nhật tên file ảnh trên bảng (chỉ giao diện, chưa lưu DB)
            int row = tblBangKhachHang.getSelectedRow();
            if (row >= 0) {
                tblBangKhachHang.setValueAt(fileName, row, 5);
            }
        }
    }//GEN-LAST:event_lblAnhKHMouseClicked

    private void btnCapNhatKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatKHActionPerformed
        // TODO add your handling code here:
        String maKH = txtMaKH.getText().trim();
        String hoTen = txtHoTenKH.getText().trim();
        String soDT = txtSDTKH.getText().trim();
        BigDecimal soDu = new BigDecimal(txtSDTKKH.getText().trim().isEmpty() ? "0" : txtSDTKKH.getText().trim());
        boolean trangThai = cboTrangThaiKH.getSelectedItem().toString().equals("Hoạt động");

        // Giữ nguyên ảnh nếu không chọn mới
        String image;
        if (fileAnhMoi != null && !fileAnhMoi.trim().isEmpty()) {
            image = fileAnhMoi;
        } else {
            int row = tblBangKhachHang.getSelectedRow();
            image = (row >= 0 && tblBangKhachHang.getValueAt(row, 5) != null)
                ? tblBangKhachHang.getValueAt(row, 5).toString()
                : "default.png";
        }

        ql.net.entity.KhachHang kh = ql.net.entity.KhachHang.builder()
            .maKH(maKH)
            .hoTen(hoTen)
            .soDT(soDT)
            .soDuTaiKhoan(soDu)
            .trangThai(trangThai)
            .image(image)
            .build();

        boolean ok = new ql.net.dao.impl.KhachHangDaoImpl().update(kh);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công!");
            loadKhachHangToTable();
            lamMoiFormKH();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thất bại!");
        }
    }//GEN-LAST:event_btnCapNhatKHActionPerformed

    private void btnBatMay1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatMay1ActionPerformed
        // TODO add your handling code here:
        batMay(selectMaMay, txtGTrangThai, txtGTDBatDau, txtGTGSuDung, txtGTamTinh, pnl1);
    }//GEN-LAST:event_btnBatMay1ActionPerformed

    private void btnBatMayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatMayActionPerformed
        // TODO add your handling code here:
        batMay(selectMaMay, txtTCTrangThai, txtTCTDBatDau, txtTCTGSuDung, txtTCTamTinh, pnl1);
    }//GEN-LAST:event_btnBatMayActionPerformed

    private void btnBatMay2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatMay2ActionPerformed
        // TODO add your handling code here:
        batMay(selectMaMay, txtCNTrangThai, txtCNTDBatDau, txtCNTGSuDung, txtCNTamTinh, pnl1);
    }//GEN-LAST:event_btnBatMay2ActionPerformed

    private void btnTDBatMayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTDBatMayActionPerformed
        // TODO add your handling code here:
        batMay(selectMaMay, txtTDTrangThai, txtTDTDBatDau, txtTDTGSuDung, txtTDTamTinh, pnl1);
    }//GEN-LAST:event_btnTDBatMayActionPerformed

    private void tblDoAnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDoAnMouseClicked
        // TODO add your handling code here:
        int row = tblDoAn.getSelectedRow();
        if (row >= 0 ) {
            txtMaDVDA.setText(tblDoAn.getValueAt(row, 0).toString());
            txtTenDVDA.setText(tblDoAn.getValueAt(row, 1).toString());
            cboLoaiDVDA.setSelectedItem(tblDoAn.getValueAt(row, 2).toString());
            txtDonGiaDA.setText(tblDoAn.getValueAt(row, 3).toString());
            txtSoLuongTonDA.setText(tblDoAn.getValueAt(row, 4).toString());
            cboTrangThaiDA.setSelectedItem(tblDoAn.getValueAt(row, 5).toString());
            // Hiển thị ảnh dịch vụ
            String imageName = tblDoAn.getValueAt(row, 6).toString();
            ql.net.util.XIcon.setIcon(lblAnhDoAn, "/ql/net/icons/" + imageName);
            if (imageName.length() > 0) {
                java.net.URL resource = getClass().getResource("/ql/net/icons/" + imageName);
                if (resource != null) {
                    ql.net.util.XIcon.setIcon(lblAnhDoAn, "/ql/net/icons/" + imageName);
                } else {
                    lblAnhDoAn.setIcon(null); // Không có ảnh
                }
            } else {
                lblAnhDoAn.setIcon(null); // Không có ảnh
            }
            lblAnhDoAn.setText(""); // XÓA tên file, chỉ hiển thị ảnh
        }
    }//GEN-LAST:event_tblDoAnMouseClicked

    private void tblDoUongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDoUongMouseClicked
        // TODO add your handling code here:
        int row = tblDoUong.getSelectedRow();
        if (row >=0) {
            txtMaDVDU.setText(tblDoUong.getValueAt(row, 0).toString());
            txtTenDVDU.setText(tblDoUong.getValueAt(row, 1).toString());
            cboLoaiDVDU.setSelectedItem(tblDoUong.getValueAt(row, 2).toString());
            txtDonGiaDU.setText(tblDoUong.getValueAt(row, 3).toString());
            txtSoLuongTonDU.setText(tblDoUong.getValueAt(row, 4).toString());
            cboTrangThaiDU.setSelectedItem(tblDoUong.getValueAt(row, 5).toString());
            //Hiển thị ảnh dịch vụ
            String imageName = tblDoUong.getValueAt(row, 6).toString();
            ql.net.util.XIcon.setIcon(lblAnhDoUong, "/ql/net/icons/"+imageName);
            if (imageName.length() > 0) {
                java.net.URL resource = getClass().getResource("/ql/net/icons/" + imageName);
                if (resource != null) {
                    ql.net.util.XIcon.setIcon(lblAnhDoUong, "/ql/net/icons/" + imageName);
                } else {
                    lblAnhDoUong.setIcon(null); // Không có ảnh
                }
            } else {
                lblAnhDoUong.setIcon(null); // Không có ảnh
            }
            lblAnhDoUong.setText(""); // XÓA tên file, chỉ hiển thị ảnh
        }
    }//GEN-LAST:event_tblDoUongMouseClicked

    private void tblDichVuKhacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDichVuKhacMouseClicked
        // TODO add your handling code here:
        int row = tblDichVuKhac.getSelectedRow();
        if (row >=0){
            txtMaDVK.setText(tblDichVuKhac.getValueAt(row, 0).toString());
            txtTenDVK.setText(tblDichVuKhac.getValueAt(row, 1).toString());
            cboLoaiDVK.setSelectedItem(tblDichVuKhac.getValueAt(row, 2).toString());
            txtDonGiaDVK.setText(tblDichVuKhac.getValueAt(row, 3).toString());
            txtSoLuongDVK.setText(tblDichVuKhac.getValueAt(row, 4).toString());
            cboTrangThaiDVK.setSelectedItem(tblDichVuKhac.getValueAt(row, 5).toString());
            //hiển thị hình ảnh dịch vụ
            String imageName = tblDichVuKhac.getValueAt(row, 6).toString();
            ql.net.util.XIcon.setIcon(lblAnhDichVu, "/ql/net/icons/"+imageName);
            if (imageName.length() > 0) {
                java.net.URL resource = getClass().getResource("/ql/net/icons/" + imageName);
                if (resource != null) {
                    ql.net.util.XIcon.setIcon(lblAnhDichVu, "/ql/net/icons/" + imageName);
                } else {
                    lblAnhDichVu.setIcon(null); // Không có ảnh
                }
            } else {
                lblAnhDichVu.setIcon(null); // Không có ảnh
            }
            lblAnhDichVu.setText(""); // XÓA tên file, chỉ hiển thị ảnh
        }
    }//GEN-LAST:event_tblDichVuKhacMouseClicked

    private void btnThemKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKHActionPerformed
        // TODO add your handling code here:
        String hoTen = txtHoTenKH.getText().trim();
        String soDT = txtSDTKH.getText().trim();

        if (hoTen.isEmpty() || soDT.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ họ tên và số điện thoại!");
            return;
        }

        String maKH = generteMaKH();
        BigDecimal soDu = new BigDecimal(txtSDTKKH.getText().trim().isEmpty() ? "0" : txtSDTKKH.getText().trim());
        boolean trangThai = cboTrangThaiKH.getSelectedItem().toString().equals("Hoạt động");
        String image = "default.png";

        ql.net.entity.KhachHang kh = ql.net.entity.KhachHang.builder()
            .maKH(maKH)
            .hoTen(hoTen)
            .soDT(soDT)
            .soDuTaiKhoan(soDu)
            .trangThai(trangThai)
            .image(image)
            .build();

        boolean ok = new ql.net.dao.impl.KhachHangDaoImpl().insert(kh);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
            loadKhachHangToTable();
            lamMoiFormKH();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại!");
        }
    }//GEN-LAST:event_btnThemKHActionPerformed

    private void btnXoaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKHActionPerformed
        // TODO add your handling code here:
        String maKH = txtMaKH.getText().trim();
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa khách hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = new ql.net.dao.impl.KhachHangDaoImpl().delete(maKH);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!");
                loadKhachHangToTable();
                lamMoiFormKH();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thất bại!");
            }
        }
    }//GEN-LAST:event_btnXoaKHActionPerformed

    private void btnThemVIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemVIPActionPerformed
        // TODO add your handling code here:
        // Lấy dữ liệu từ form
        String maKH = txtMaKH_VIP.getText().trim();
        String loaiVIP = txtLoaiVip.getText().trim();
        String tongChiTieuStr = txtTongChiTieu.getText().trim();
        java.util.Date ngayThamGiaDate = txtNgayThamGiaVip.getDate();
        java.util.Date ngayHetHanDate = txtNgayHetHanVip.getDate();
        boolean trangThai = cboTrangThaiVip.getSelectedItem().toString().equals("Còn hiệu lực");

        // Kiểm tra dữ liệu bắt buộc
        if (maKH.isEmpty() || loaiVIP.isEmpty() || tongChiTieuStr.isEmpty() || ngayThamGiaDate == null || ngayHetHanDate == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin thành viên VIP!");
            return;
        }

        BigDecimal tongChiTieu;
        try {
            tongChiTieu = new BigDecimal(tongChiTieuStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tổng chi tiêu phải là số!");
            return;
        }

        LocalDate ngayThamGia = ngayThamGiaDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        LocalDate ngayHetHan = ngayHetHanDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

        // Sinh mã VIP tự động
        String maVIP = generateMaVIP();

        // Tạo đối tượng VIP
        ql.net.entity.ThanhVienVIP vip = ql.net.entity.ThanhVienVIP.builder()
            .maVIP(maVIP)
            .maKH(maKH)
            .loaiVIP(loaiVIP)
            .tongChiTieu(tongChiTieu)
            .ngayThamGia(ngayThamGia)
            .ngayHetHan(ngayHetHan)
            .trangThai(trangThai)
            .build();

        // Thêm vào database
        boolean ok = new ThanhVienVipDaoImpl().insert(vip);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Thêm thành viên VIP thành công!");
            loadVIPToTable();
            lamMoiFormVIP();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thành viên VIP thất bại!");
        }
    }//GEN-LAST:event_btnThemVIPActionPerformed

    private void btnCapNhatVIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatVIPActionPerformed
        // TODO add your handling code here:
        int row = tblBangKhachVIP.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thành viên VIP cần cập nhật!");
            return;
        }

        // Lấy dữ liệu cũ từ bảng
        String oldMaKH = tblBangKhachVIP.getValueAt(row, 1).toString();
        String oldLoaiVIP = tblBangKhachVIP.getValueAt(row, 2).toString();
        String oldTongChiTieu = tblBangKhachVIP.getValueAt(row, 3).toString();
        String oldNgayThamGia = tblBangKhachVIP.getValueAt(row, 4).toString();
        String oldNgayHetHan = tblBangKhachVIP.getValueAt(row, 5).toString();
        String oldTrangThai = tblBangKhachVIP.getValueAt(row, 6).toString();

        // Lấy dữ liệu mới từ form
        String maVIP = txtMaVIP.getText().trim();
        String maKH = txtMaKH_VIP.getText().trim();
        String loaiVIP = txtLoaiVip.getText().trim();
        String tongChiTieu = txtTongChiTieu.getText().trim();
        String ngayThamGia = txtNgayThamGiaVip.getDate() != null
            ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(txtNgayThamGiaVip.getDate())
            : "";
        String ngayHetHan = txtNgayHetHanVip.getDate() != null
            ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(txtNgayHetHanVip.getDate())
            : "";
        String trangThai = cboTrangThaiVip.getSelectedItem().toString();

        // Kiểm tra thay đổi
        if (maKH.equals(oldMaKH) &&
            loaiVIP.equals(oldLoaiVIP) &&
            tongChiTieu.equals(oldTongChiTieu) &&
            ngayThamGia.equals(oldNgayThamGia) &&
            ngayHetHan.equals(oldNgayHetHan) &&
            trangThai.equals(oldTrangThai)) {
            JOptionPane.showMessageDialog(this, "Không có thay đổi nào để cập nhật!");
            return;
        }

        // Thực hiện cập nhật nếu có thay đổi
        BigDecimal tongChiTieuBD = new BigDecimal(tongChiTieu.isEmpty() ? "0" : tongChiTieu);
        LocalDate ngayThamGiaLD = txtNgayThamGiaVip.getDate() != null
            ? txtNgayThamGiaVip.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate()
            : LocalDate.now();
        LocalDate ngayHetHanLD = txtNgayHetHanVip.getDate() != null
            ? txtNgayHetHanVip.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate()
            : LocalDate.now().plusMonths(1);
        boolean trangThaiBool = trangThai.equals("Còn hạn");

        ql.net.entity.ThanhVienVIP vip = ql.net.entity.ThanhVienVIP.builder()
            .maVIP(maVIP)
            .maKH(maKH)
            .loaiVIP(loaiVIP)
            .tongChiTieu(tongChiTieuBD)
            .ngayThamGia(ngayThamGiaLD)
            .ngayHetHan(ngayHetHanLD)
            .trangThai(trangThaiBool)
            .build();

        new ql.net.dao.impl.ThanhVienVipDaoImpl().update(vip);
        JOptionPane.showMessageDialog(this, "Cập nhật thành viên VIP thành công!");
        loadVIPToTable();
        lamMoiFormVIP();
    }//GEN-LAST:event_btnCapNhatVIPActionPerformed

    private void btnXoaVIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaVIPActionPerformed
        // TODO add your handling code here:
        String maVIP = txtMaVIP.getText().trim();
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa thành viên VIP này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            new ql.net.dao.impl.ThanhVienVipDaoImpl().delete(maVIP);
            JOptionPane.showMessageDialog(this, "Xóa thành viên VIP thành công!");
            loadVIPToTable();
            lamMoiFormVIP();
        }
    }//GEN-LAST:event_btnXoaVIPActionPerformed

    private void btnLamMoiVIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiVIPActionPerformed
        // TODO add your handling code here:
        lamMoiFormVIP();
    }//GEN-LAST:event_btnLamMoiVIPActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        // TODO add your handling code here:
        int row = tblHoaDon.getSelectedRow();
        if (row >= 0) {
            // Lấy mã hóa đơn từ dòng vừa chọn
            String maHD = tblHoaDon.getValueAt(row, 0).toString();
            // Đổ dữ liệu chi tiết hóa đơn lên bảng chi tiết
            loadChiTietHoaDonToTable(maHD);
            // Nếu muốn chuyển tab sang tab chi tiết hóa đơn:
            jTabbedPane3.setSelectedIndex(1); // tab thứ 2 là hóa đơn chi tiết
        }
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void btnInHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInHoaDonActionPerformed
        // TODO add your handling code here:
        try {
            int row = tblHoaDon.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần in!");
                return;
            }
            String maHD = tblHoaDon.getValueAt(row, 0).toString();

            // Lấy thông tin hóa đơn
            ResultSet rsHD = XJdbc.executeQuery("SELECT * FROM HoaDon WHERE MaHD = ?", maHD);
            if (!rsHD.next()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn!");
                return;
            }
            String maPhien = rsHD.getString("MaPhien");
            String maNV = rsHD.getString("MaNV");
            String maKH = rsHD.getString("MaKH");
            String ngayTao = new SimpleDateFormat("dd/MM/yyyy").format(rsHD.getTimestamp("NgayTao"));
            double tongTien = rsHD.getDouble("TongTien");

            // Lấy tên nhân viên
            ResultSet rsNV = XJdbc.executeQuery("SELECT HoTen FROM NhanVien WHERE MaNV = ?", maNV);
            String tenNV = rsNV.next() ? rsNV.getString("HoTen") : "";

            // Lấy thông tin phiên sử dụng
            ResultSet rsPhien = XJdbc.executeQuery("SELECT * FROM PhienSuDung WHERE MaPhien = ?", maPhien);
            String maMay = "";
            String zone = "";
            double donGiaGio = 0;
            int thoiGian = 0;
            double thanhTienMay = 0;
            if (rsPhien.next()) {
                maMay = rsPhien.getString("MaMay");
                thanhTienMay = rsPhien.getDouble("TongTien");
                thoiGian = (int) rsPhien.getDouble("SoGioSuDung");
                donGiaGio = thoiGian > 0 ? thanhTienMay / thoiGian : 0;
                // Xác định zone
                ResultSet rsMay = XJdbc.executeQuery("SELECT LoaiMay FROM MayTinh WHERE MaMay = ?", maMay);
                if (rsMay.next()) zone = rsMay.getString("LoaiMay");
            }

            // Lấy chi tiết hóa đơn dịch vụ
            ResultSet rsCT = XJdbc.executeQuery(
                "SELECT c.MaDV, d.TenDV, c.SoLuong, c.DonGia, c.ThanhTien " +
                "FROM ChiTietHoaDon c JOIN DichVu d ON c.MaDV = d.MaDV WHERE c.MaHD = ?", maHD);

            // Tạo file PDF
            String fileName = "HoaDon_" + maHD + ".pdf";
            Document doc = new Document(PageSize.A4);
            PdfWriter.getInstance(doc, new FileOutputStream(fileName));
            doc.open();

            // Font Unicode
            BaseFont bf = BaseFont.createFont("C:/Windows/Fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            com.itextpdf.text.Font fontBold = new com.itextpdf.text.Font(bf, 12, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font fontNormal = new com.itextpdf.text.Font(bf, 12, com.itextpdf.text.Font.NORMAL);
            com.itextpdf.text.Font fontTitle = new com.itextpdf.text.Font(bf, 16, com.itextpdf.text.Font.BOLD, BaseColor.RED);

            // Header
            Paragraph header = new Paragraph("Tiệm NET DTH Gaming", fontBold);
            header.setAlignment(Element.ALIGN_LEFT);
            doc.add(header);
            doc.add(new Paragraph("Điện Bàn - Quảng Nam", fontNormal));
            doc.add(new Paragraph("Điện thoại: 0795568318", fontNormal));
            doc.add(new Paragraph(" "));
            Paragraph title = new Paragraph("HÓA ĐƠN", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            doc.add(title);
            doc.add(new Paragraph(" "));

            // Thông tin hóa đơn
            doc.add(new Paragraph("Mã hóa đơn: " + maHD, fontNormal));
            doc.add(new Paragraph("Ngày lập: " + ngayTao, fontNormal));
            doc.add(new Paragraph("Nhân viên: " + tenNV, fontNormal));
            doc.add(new Paragraph(" "));

            // Bảng máy tính
            PdfPTable tblMay = new PdfPTable(5);
            tblMay.setWidthPercentage(100);
            tblMay.setSpacingBefore(10f);
            tblMay.setSpacingAfter(10f);
            tblMay.addCell(new PdfPCell(new Phrase("Mã máy tính", fontBold)));
            tblMay.addCell(new PdfPCell(new Phrase("Zone", fontBold)));
            tblMay.addCell(new PdfPCell(new Phrase("Đơn giá/ h", fontBold)));
            tblMay.addCell(new PdfPCell(new Phrase("Thời gian", fontBold)));
            tblMay.addCell(new PdfPCell(new Phrase("Thành tiền", fontBold)));
            tblMay.addCell(new PdfPCell(new Phrase(maMay, fontNormal)));
            tblMay.addCell(new PdfPCell(new Phrase(zone, fontNormal)));
            tblMay.addCell(new PdfPCell(new Phrase(String.valueOf((int)donGiaGio), fontNormal)));
            tblMay.addCell(new PdfPCell(new Phrase(String.valueOf(thoiGian), fontNormal)));
            tblMay.addCell(new PdfPCell(new Phrase(String.format("%.2f", thanhTienMay), fontNormal)));
            doc.add(tblMay);

            // Bảng dịch vụ
            PdfPTable tblDV = new PdfPTable(5);
            tblDV.setWidthPercentage(100);
            tblDV.setSpacingBefore(10f);
            tblDV.setSpacingAfter(10f);
            tblDV.addCell(new PdfPCell(new Phrase("STT", fontBold)));
            tblDV.addCell(new PdfPCell(new Phrase("Tên món", fontBold)));
            tblDV.addCell(new PdfPCell(new Phrase("Số lượng", fontBold)));
            tblDV.addCell(new PdfPCell(new Phrase("Đơn giá", fontBold)));
            tblDV.addCell(new PdfPCell(new Phrase("Thành tiền", fontBold)));
            int stt = 1;
            while (rsCT.next()) {
                tblDV.addCell(new PdfPCell(new Phrase(String.valueOf(stt), fontNormal)));
                tblDV.addCell(new PdfPCell(new Phrase(rsCT.getString("TenDV"), fontNormal)));
                tblDV.addCell(new PdfPCell(new Phrase(rsCT.getString("SoLuong"), fontNormal)));
                tblDV.addCell(new PdfPCell(new Phrase(rsCT.getString("DonGia"), fontNormal)));
                tblDV.addCell(new PdfPCell(new Phrase(rsCT.getString("ThanhTien"), fontNormal)));
                stt++;
            }
            doc.add(tblDV);

            // Tổng tiền
            Paragraph tongTienPara = new Paragraph("Tổng tiền: " + String.format("%.2f", tongTien), fontBold);
            tongTienPara.setAlignment(Element.ALIGN_RIGHT);
            doc.add(tongTienPara);

            doc.add(new Paragraph(" "));
//            doc.add(new Paragraph("Nhân viên:", fontNormal));
//            doc.add(new Paragraph(tenNV, fontNormal));

            // Nhân viên (căn phải, nằm dưới tổng tiền)
            Paragraph nvLabel = new Paragraph("Nhân viên:", fontNormal);
            nvLabel.setAlignment(Element.ALIGN_RIGHT);
            doc.add(nvLabel);

            Paragraph nvName = new Paragraph(tenNV, fontNormal);
            nvName.setAlignment(Element.ALIGN_RIGHT);
            doc.add(nvName);

            doc.close();

            // Mở file PDF
            Desktop.getDesktop().open(new File(fileName));

            rsHD.close();
            rsNV.close();
            rsPhien.close();
            rsCT.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi in hóa đơn: " + e.getMessage());
        }
    }//GEN-LAST:event_btnInHoaDonActionPerformed

    private void txtDonGiaNhapHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDonGiaNhapHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDonGiaNhapHangActionPerformed

    private void jScrollPane12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane12MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jScrollPane12MouseClicked

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        // TODO add your handling code here:
        int row = tblNhanVien.getSelectedRow();
        if (row >= 0) {
            txtMaNV.setText(tblNhanVien.getValueAt(row, 0).toString());
            txtHoTenNV.setText(tblNhanVien.getValueAt(row, 1).toString());
            txtSDTNV.setText(tblNhanVien.getValueAt(row, 2).toString());
            txtTenTaiKhoan.setText(tblNhanVien.getValueAt(row, 3).toString());
            txtMatKhauNV.setText(tblNhanVien.getValueAt(row, 4).toString());

            // Set combo box
            String chucVu = tblNhanVien.getValueAt(row, 5).toString();
            cboChucVuNV.setSelectedItem(chucVu);

            String trangThai = tblNhanVien.getValueAt(row, 6).toString();
            cboTrangThaiNV.setSelectedItem(trangThai);

            // Hiển thị ảnh nhân viên (cột 8 - image)
            Object imageObj = tblNhanVien.getValueAt(row, 8);
            if (imageObj != null && imageObj.toString().trim().length() > 0) {
                String imageName = imageObj.toString().trim();
                boolean found = false;

                // Thử đúng tên từ SQL
                java.net.URL resource = getClass().getResource("/ql/net/icons/" + imageName);
                if (resource != null) {
                    ql.net.util.XIcon.setIcon(lblAnhNV, "/ql/net/icons/" + imageName);
                    found = true;
                } else {
                    // Nếu không có, thử đổi đuôi .jpg/.png
                    String baseName = imageName.contains(".") ? imageName.substring(0, imageName.lastIndexOf('.')) : imageName;
                    String[] exts = {".jpg", ".png", ".jpeg"};
                    for (String ext : exts) {
                        String tryName = baseName + ext;
                        resource = getClass().getResource("/ql/net/icons/" + tryName);
                        if (resource != null) {
                            ql.net.util.XIcon.setIcon(lblAnhNV, "/ql/net/icons/" + tryName);
                            found = true;
                            break;
                        }
                    }
                }
                if (!found) {
                    lblAnhNV.setIcon(null); // Không có ảnh
                }
            } else {
                lblAnhNV.setIcon(null); // Không có ảnh
            }
        }
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void lblAnhNVAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lblAnhNVAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_lblAnhNVAncestorAdded

    private void lblAnhNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhNVMouseClicked
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Chọn ảnh nhân viên");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Ảnh JPG, PNG", "jpg", "jpeg", "png"));
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            java.io.File selectedFile = chooser.getSelectedFile();
            String fileName = selectedFile.getName();

            // Copy file vào resource
            java.io.File copiedFile = ql.net.util.XIcon.copyTo(selectedFile, "src/main/resources/ql/net/icons");

            // Hiển thị ảnh mới lên label
            ql.net.util.XIcon.setIcon(lblAnhNV, copiedFile.getAbsolutePath());

            // Cập nhật tên file ảnh trên bảng (chỉ giao diện, chưa lưu DB)
            int row = tblNhanVien.getSelectedRow();
            if (row >= 0) {
                tblNhanVien.setValueAt(fileName, row, 8); // Cột 8 là cột image
            }
        }
    }//GEN-LAST:event_lblAnhNVMouseClicked

    private void btnThemDAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemDAActionPerformed
        // TODO add your handling code here:
        String maDV = generateMaDV();
        String tenDV = txtTenDVDA.getText().trim();
        String loaiDV = cboLoaiDVDA.getSelectedItem().toString();
        String donGiaStr = txtDonGiaDA.getText().trim();
        String soLuongStr = txtSoLuongTonDA.getText().trim();
        boolean trangThai = cboTrangThaiDA.getSelectedItem().equals("Còn hàng");
        String image = lblAnhDoAn.getText().trim();
        
        //kiểm tra dữ liệu bắt buộc
        if (maDV.isEmpty() || tenDV.isEmpty() || donGiaStr.isEmpty() || soLuongStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        
        BigDecimal donGia;
        int SoLuong;
        try{
            donGia = new BigDecimal(donGiaStr);
            SoLuong = Integer.parseInt(soLuongStr);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Đơn giá và số lượng phải là số!");
            return;
        }
        
        ql.net.entity.DichVu dv = ql.net.entity.DichVu.builder()
                .maDV(maDV)
                .tenDV(tenDV)
                .loaiDV(loaiDV)
                .donGia(donGia)
                .soLuongTon(SoLuong)
                .trangThai(trangThai)
                .image(image)
                .build();
        
        boolean ok = new ql.net.dao.impl.DichVuDaoImpl().insert(dv);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Thêm dịch vụ thành công!");
            //gọi đúng hàm load bảng theo dịch vụ
            if (loaiDV.equals("Đồ ăn")) {
                loadDoAnToTable();
            } else if(loaiDV.equals("Đồ uống")){
                loadDoUongToTable();
            } else if(loaiDV.equals("Khác")){
                loadDichVuKhac();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Thêm dịch vụ thất bại");
        }
    }//GEN-LAST:event_btnThemDAActionPerformed

    private void btnThemDUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemDUActionPerformed
        // TODO add your handling code here:
        String maDV = generateMaDV();
        String tenDV = txtTenDVDU.getText().trim();
        String loaiDV = cboLoaiDVDU.getSelectedItem().toString();
        String donGiaStr = txtDonGiaDU.getText().trim();
        String soLuongStr = txtSoLuongTonDU.getText().trim();
        boolean trangThai = cboTrangThaiDU.getSelectedItem().equals("Còn hàng");
        String image = lblAnhDoUong.getText().trim();
        
        //kiểm tra dữ liệu bắt buộc
        if (maDV.isEmpty() || tenDV.isEmpty() || donGiaStr.isEmpty() || soLuongStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        
        BigDecimal donGia;
        int SoLuong;
        try{
            donGia = new BigDecimal(donGiaStr);
            SoLuong = Integer.parseInt(soLuongStr);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Đơn giá và số lượng phải là số!");
            return;
        }
        
        ql.net.entity.DichVu dv = ql.net.entity.DichVu.builder()
                .maDV(maDV)
                .tenDV(tenDV)
                .loaiDV(loaiDV)
                .donGia(donGia)
                .soLuongTon(SoLuong)
                .trangThai(trangThai)
                .image(image)
                .build();
        
        boolean ok = new ql.net.dao.impl.DichVuDaoImpl().insert(dv);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Thêm dịch vụ thành công!");
            //gọi đúng hàm load bảng theo dịch vụ
            if (loaiDV.equals("Đồ ăn")) {
                loadDoAnToTable();
            } else if(loaiDV.equals("Đồ uống")){
                loadDoUongToTable();
            } else if(loaiDV.equals("Khác")){
                loadDichVuKhac();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Thêm dịch vụ thất bại");
        }
    }//GEN-LAST:event_btnThemDUActionPerformed

    private void btnThemDVKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemDVKActionPerformed
        // TODO add your handling code here:
        String maDV = generateMaDV();
        String tenDV = txtTenDVK.getText().trim();
        String loaiDV = cboLoaiDVK.getSelectedItem().toString();
        String donGiaStr = txtDonGiaDVK.getText().trim();
        String soLuongStr = txtSoLuongDVK.getText().trim();
        boolean trangThai = cboTrangThaiDVK.getSelectedItem().equals("Còn hàng");
        String image = lblAnhDichVu.getText().trim();
        
        //kiểm tra dữ liệu bắt buộc
        if (maDV.isEmpty() || tenDV.isEmpty() || donGiaStr.isEmpty() || soLuongStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        
        BigDecimal donGia;
        int SoLuong;
        try{
            donGia = new BigDecimal(donGiaStr);
            SoLuong = Integer.parseInt(soLuongStr);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Đơn giá và số lượng phải là số!");
            return;
        }
        
        ql.net.entity.DichVu dv = ql.net.entity.DichVu.builder()
                .maDV(maDV)
                .tenDV(tenDV)
                .loaiDV(loaiDV)
                .donGia(donGia)
                .soLuongTon(SoLuong)
                .trangThai(trangThai)
                .image(image)
                .build();
        
        boolean ok = new ql.net.dao.impl.DichVuDaoImpl().insert(dv);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Thêm dịch vụ thành công!");
            //gọi đúng hàm load bảng theo dịch vụ
            if (loaiDV.equals("Đồ ăn")) {
                loadDoAnToTable();
            } else if(loaiDV.equals("Đồ uống")){
                loadDoUongToTable();
            } else if(loaiDV.equals("Khác")){
                loadDichVuKhac();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Thêm dịch vụ thất bại");
        }
    }//GEN-LAST:event_btnThemDVKActionPerformed

    private void btnCapNhatDAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatDAActionPerformed
        // TODO add your handling code here:
        int row = tblDoAn.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ cần cập nhật!");
            return;
        }
        // Lấy mã dịch vụ từ bảng (không cho sửa)
        String maDV = tblDoAn.getValueAt(row, 0).toString();
        String tenDV = txtTenDVDA.getText().trim();
        String loaiDV = cboLoaiDVDA.getSelectedItem().toString();
        String donGiaStr = txtDonGiaDA.getText().trim();
        String soLuongStr = txtSoLuongTonDA.getText().trim();
        boolean trangThai = cboTrangThaiDA.getSelectedItem().toString().equals("Còn hàng");
        String image = lblAnhDoAn.getText().trim();

        if (tenDV.isEmpty() || donGiaStr.isEmpty() || soLuongStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        BigDecimal donGia;
        int soLuong;
        try {
            donGia = new BigDecimal(donGiaStr);
            soLuong = Integer.parseInt(soLuongStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Đơn giá và số lượng phải là số!");
            return;
        }

        ql.net.entity.DichVu dv = ql.net.entity.DichVu.builder()
            .maDV(maDV) // Không cho sửa mã dịch vụ
            .tenDV(tenDV)
            .loaiDV(loaiDV)
            .donGia(donGia)
            .soLuongTon(soLuong)
            .trangThai(trangThai)
            .image(image)
            .build();

        boolean ok = new ql.net.dao.impl.DichVuDaoImpl().update(dv);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Cập nhật dịch vụ thành công!");
            loadDoAnToTable();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật dịch vụ thất bại!");
        }
    }//GEN-LAST:event_btnCapNhatDAActionPerformed

    private void btnCapNhatDUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatDUActionPerformed
        // TODO add your handling code here:
        int row = tblDoUong.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ cần cập nhật!");
            return;
        }
        // Lấy mã dịch vụ từ bảng (không cho sửa)
        String maDV = tblDoUong.getValueAt(row, 0).toString();
        String tenDV = txtTenDVDU.getText().trim();
        String loaiDV = cboLoaiDVDU.getSelectedItem().toString();
        String donGiaStr = txtDonGiaDU.getText().trim();
        String soLuongStr = txtSoLuongTonDU.getText().trim();
        boolean trangThai = cboTrangThaiDU.getSelectedItem().toString().equals("Còn hàng");
        String image = lblAnhDoUong.getText().trim();

        if (tenDV.isEmpty() || donGiaStr.isEmpty() || soLuongStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        BigDecimal donGia;
        int soLuong;
        try {
            donGia = new BigDecimal(donGiaStr);
            soLuong = Integer.parseInt(soLuongStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Đơn giá và số lượng phải là số!");
            return;
        }

        ql.net.entity.DichVu dv = ql.net.entity.DichVu.builder()
            .maDV(maDV) // Không cho sửa mã dịch vụ
            .tenDV(tenDV)
            .loaiDV(loaiDV)
            .donGia(donGia)
            .soLuongTon(soLuong)
            .trangThai(trangThai)
            .image(image)
            .build();

        boolean ok = new ql.net.dao.impl.DichVuDaoImpl().update(dv);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Cập nhật dịch vụ thành công!");
            loadDoUongToTable();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật dịch vụ thất bại!");
        }
    }//GEN-LAST:event_btnCapNhatDUActionPerformed

    private void btnCapNhatDVKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatDVKActionPerformed
        // TODO add your handling code here:
        int row = tblDichVuKhac.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ cần cập nhật!");
            return;
        }
        // Lấy mã dịch vụ từ bảng (không cho sửa)
        String maDV = tblDichVuKhac.getValueAt(row, 0).toString();
        String tenDV = txtTenDVK.getText().trim();
        String loaiDV = cboLoaiDVK.getSelectedItem().toString();
        String donGiaStr = txtDonGiaDVK.getText().trim();
        String soLuongStr = txtSoLuongDVK.getText().trim();
        boolean trangThai = cboTrangThaiDVK.getSelectedItem().toString().equals("Còn hàng");
        String image = lblAnhDichVu.getText().trim();

        if (tenDV.isEmpty() || donGiaStr.isEmpty() || soLuongStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        BigDecimal donGia;
        int soLuong;
        try {
            donGia = new BigDecimal(donGiaStr);
            soLuong = Integer.parseInt(soLuongStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Đơn giá và số lượng phải là số!");
            return;
        }

        ql.net.entity.DichVu dv = ql.net.entity.DichVu.builder()
            .maDV(maDV) // Không cho sửa mã dịch vụ
            .tenDV(tenDV)
            .loaiDV(loaiDV)
            .donGia(donGia)
            .soLuongTon(soLuong)
            .trangThai(trangThai)
            .image(image)
            .build();

        boolean ok = new ql.net.dao.impl.DichVuDaoImpl().update(dv);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Cập nhật dịch vụ thành công!");
            loadDichVuKhac();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật dịch vụ thất bại!");
        }
    }//GEN-LAST:event_btnCapNhatDVKActionPerformed

    private void btnXoaDVkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaDVkActionPerformed
        // TODO add your handling code here:
        int row = tblDichVuKhac.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ cần xóa!");
            return;
        }
        String maDV = tblDichVuKhac.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa dịch vụ này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = new ql.net.dao.impl.DichVuDaoImpl().delete(maDV);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Xóa dịch vụ thành công!");
                loadDichVuKhac();
                lamMoiFormDichVu(txtMaDVK, txtTenDVK, cboLoaiDVK, txtDonGiaDVK, txtSoLuongDVK, cboTrangThaiDVK, lblAnhDichVu);
            } else {
                JOptionPane.showMessageDialog(this, "Xóa dịch vụ thất bại!");
            }
        }
    }//GEN-LAST:event_btnXoaDVkActionPerformed

    private void btnLamMoiDVkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiDVkActionPerformed
        // TODO add your handling code here:
        lamMoiFormDichVu(txtMaDVK, txtTenDVK, cboLoaiDVK, txtDonGiaDVK, txtSoLuongDVK, cboTrangThaiDVK, lblAnhDichVu);
    }//GEN-LAST:event_btnLamMoiDVkActionPerformed

    private void btnLamMoiDUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiDUActionPerformed
        // TODO add your handling code here:
        lamMoiFormDichVu(txtMaDVDU, txtTenDVDU, cboLoaiDVDU, txtDonGiaDU, txtSoLuongTonDU, cboTrangThaiDU, lblAnhDoUong);
    }//GEN-LAST:event_btnLamMoiDUActionPerformed

    private void btnXoaDUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaDUActionPerformed
        // TODO add your handling code here:
        int row = tblDoUong.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ cần xóa!");
            return;
        }
        String maDV = tblDoUong.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa dịch vụ này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = new ql.net.dao.impl.DichVuDaoImpl().delete(maDV);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Xóa dịch vụ thành công!");
                loadDoUongToTable();
                lamMoiFormDichVu(txtMaDVDU, txtTenDVDU, cboLoaiDVDU, txtDonGiaDU, txtSoLuongTonDU, cboTrangThaiDU, lblAnhDoUong);
            } else {
                JOptionPane.showMessageDialog(this, "Xóa dịch vụ thất bại!");
            }
        }
    }//GEN-LAST:event_btnXoaDUActionPerformed

    private void btnLamMoiDAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiDAActionPerformed
        // TODO add your handling code here:
        lamMoiFormDichVu(txtMaDVDA, txtTenDVDA, cboLoaiDVDA, txtDonGiaDA, txtSoLuongTonDA, cboTrangThaiDA, lblAnhDoAn);
    }//GEN-LAST:event_btnLamMoiDAActionPerformed

    private void btnXoaDAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaDAActionPerformed
        // TODO add your handling code here:
        int row = tblDoAn.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ cần xóa!");
            return;
        }
        String maDV = tblDoAn.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa dịch vụ này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = new ql.net.dao.impl.DichVuDaoImpl().delete(maDV);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Xóa dịch vụ thành công!");
                loadDoAnToTable();
                lamMoiFormDichVu(txtMaDVDA, txtTenDVDA, cboLoaiDVDA, txtDonGiaDA, txtSoLuongTonDA, cboTrangThaiDA, lblAnhDoAn);
            } else {
                JOptionPane.showMessageDialog(this, "Xóa dịch vụ thất bại!");
            }
        }
    }//GEN-LAST:event_btnXoaDAActionPerformed

    private void lblAnhDichVuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhDichVuMouseClicked
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Chọn ảnh dịch vụ");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Ảnh JPG, PNG", "jpg", "jpeg", "png"));
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            java.io.File selectedFile = chooser.getSelectedFile();
            String fileName = selectedFile.getName();
            // Copy file vào thư mục resource (nếu muốn quản lý tập trung)
            java.io.File copiedFile = ql.net.util.XIcon.copyTo(selectedFile, "src/main/resources/ql/net/icons");
            // Hiển thị ảnh mới lên label
            ql.net.util.XIcon.setIcon(lblAnhDichVu, copiedFile.getAbsolutePath());
            // Lưu tên file ảnh vào text hoặc label để khi thêm/cập nhật dịch vụ sẽ lấy tên này
            lblAnhDichVu.setText(fileName);
        }
    }//GEN-LAST:event_lblAnhDichVuMouseClicked

    private void lblAnhDoUongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhDoUongMouseClicked
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Chọn ảnh dịch vụ");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Ảnh JPG, PNG", "jpg", "jpeg", "png"));
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            java.io.File selectedFile = chooser.getSelectedFile();
            String fileName = selectedFile.getName();
            // Copy file vào thư mục resource (nếu muốn quản lý tập trung)
            java.io.File copiedFile = ql.net.util.XIcon.copyTo(selectedFile, "src/main/resources/ql/net/icons");
            // Hiển thị ảnh mới lên label
            ql.net.util.XIcon.setIcon(lblAnhDoUong, copiedFile.getAbsolutePath());
            // Lưu tên file ảnh vào text hoặc label để khi thêm/cập nhật dịch vụ sẽ lấy tên này
            lblAnhDoUong.setText(fileName);
        }
    }//GEN-LAST:event_lblAnhDoUongMouseClicked

    private void lblAnhDoAnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhDoAnMouseClicked
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Chọn ảnh dịch vụ");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Ảnh JPG, PNG", "jpg", "jpeg", "png"));
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            java.io.File selectedFile = chooser.getSelectedFile();
            String fileName = selectedFile.getName();
            // Copy file vào thư mục resource (nếu muốn quản lý tập trung)
            java.io.File copiedFile = ql.net.util.XIcon.copyTo(selectedFile, "src/main/resources/ql/net/icons");
            // Hiển thị ảnh mới lên label
            ql.net.util.XIcon.setIcon(lblAnhDoAn, copiedFile.getAbsolutePath());
            // Lưu tên file ảnh vào text hoặc label để khi thêm/cập nhật dịch vụ sẽ lấy tên này
            lblAnhDoAn.setText(fileName);
        }
    }//GEN-LAST:event_lblAnhDoAnMouseClicked

    private void txtGTGSuDungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGTGSuDungActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGTGSuDungActionPerformed

    private void btnTCDichVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTCDichVuActionPerformed
        // TODO add your handling code here:
        xuLyDichVu();
    }//GEN-LAST:event_btnTCDichVuActionPerformed

    private void btnGDichVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGDichVuActionPerformed
        // TODO add your handling code here:
        xuLyDichVu();
    }//GEN-LAST:event_btnGDichVuActionPerformed

    private void btnCNDichVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCNDichVuActionPerformed
        // TODO add your handling code here:
        xuLyDichVu();
    }//GEN-LAST:event_btnCNDichVuActionPerformed

    private void btnTDDichVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTDDichVuActionPerformed
        // TODO add your handling code here:
        xuLyDichVu();
    }//GEN-LAST:event_btnTDDichVuActionPerformed

    private void btnBaoTriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaoTriActionPerformed
        // TODO add your handling code here:
        try{
            cardLayout.show(pnl1, "quanLyBaoTri");
            setActiveButton(btnBaoTri);
            loadMayBaoTri();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "lỗi tải tab bảo trì: "+e.getMessage());
        }
        //        loadMayDatMayToBaoTri();
    }//GEN-LAST:event_btnBaoTriActionPerformed

    private void btnBaoCaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaoCaoActionPerformed
        // TODO add your handling code here:
        cardLayout.show(pnl1, "quanLyBaoCao");
        setActiveButton(btnBaoCao);
    }//GEN-LAST:event_btnBaoCaoActionPerformed

    private void btnCapNhatNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatNVActionPerformed
        // TODO add your handling code here:
        // Kiểm tra xem có chọn nhân viên nào không
        int row = tblNhanVien.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần cập nhật!");
            return;
        }

        // Lấy mã nhân viên từ bảng (không cho sửa)
        String maNV = tblNhanVien.getValueAt(row, 0).toString();
        String tenDangNhap = txtTenTaiKhoan.getText().trim();
        String hoTen = txtHoTenNV.getText().trim();
        String soDT = txtSDTNV.getText().trim();
        String chucVu = cboChucVuNV.getSelectedItem().toString();
        boolean trangThai = cboTrangThaiNV.getSelectedItem().toString().equals("Hoạt động");
        String image = ""; // Có thể thêm xử lý upload ảnh
        if (fileAnhMoi != null && !fileAnhMoi.trim().isEmpty()) {
            // Nếu có chọn ảnh mới
            image = fileAnhMoi;
        } else {
            // Nếu không có ảnh mới, lấy ảnh cũ từ bảng
            Object imageObj = tblNhanVien.getValueAt(row, 8);
            if (imageObj != null) {
                image = imageObj.toString();
            }
        }

        // Validate dữ liệu
        if (tenDangNhap.isEmpty() || hoTen.isEmpty() || soDT.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        // Kiểm tra số điện thoại hợp lệ
        if (!soDT.matches("\\d{10,11}")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ!");
            return;
        }

        try {
            // Tạo đối tượng nhân viên để cập nhật
            ql.net.entity.NhanVien nv = ql.net.entity.NhanVien.builder()
                .maNV(maNV)
                .tenDangNhap(tenDangNhap)
                .hoTen(hoTen)
                .soDT(soDT)
                .chucVu(chucVu)
                .trangThai(trangThai)
                .image(image)
                .build();

            boolean ok = new ql.net.dao.impl.NhanVienDaoImpl().update(nv);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thành công!");
                loadNhanVienToTable();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thất bại!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnCapNhatNVActionPerformed

    private void btnXoaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaNVActionPerformed
        // TODO add your handling code here:
        int row = tblNhanVien.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa!");
            return;
        }

        String maNV = tblNhanVien.getValueAt(row, 0).toString();
        String hoTen = tblNhanVien.getValueAt(row, 1).toString();

        // Không cho phép xóa chính mình
        if (maNV.equals(curentUser.getMaNV())) {
            JOptionPane.showMessageDialog(this, "Không thể xóa tài khoản của chính mình!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Bạn có chắc chắn muốn XÓA VĨNH VIỄN nhân viên: " + hoTen + "?\nHành động này KHÔNG THỂ HOÀN TÁC!", 
            "Xác nhận xóa", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Xóa vĩnh viễn khỏi database (DELETE thay vì UPDATE TrangThai = 0)
                boolean ok = new ql.net.dao.impl.NhanVienDaoImpl().delete(maNV);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Đã xóa nhân viên vĩnh viễn khỏi hệ thống!");
                    loadNhanVienToTable();
                    lamMoiFormNV();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa nhân viên thất bại!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnXoaNVActionPerformed

    private void btnLamMoiNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiNVActionPerformed
        // TODO add your handling code here:
        lamMoiFormNV();
    }//GEN-LAST:event_btnLamMoiNVActionPerformed

    private void btnThemNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNVActionPerformed
        // TODO add your handling code here:
        String tenDangNhap = txtTenTaiKhoan.getText().trim();
        String hoTen = txtHoTenNV.getText().trim();
        String soDT = txtSDTNV.getText().trim();
        String matKhau = txtMatKhauNV.getText().trim();
        String chucVu = cboChucVuNV.getSelectedItem().toString();
        boolean trangThai = cboTrangThaiNV.getSelectedItem().toString().equals("Hoạt động");

        // Lấy ảnh từ lblAnhNV (nếu có)
        String image = "";
        Object imageObj = lblAnhNV.getClientProperty("imageName");
        if (imageObj != null) {
            image = imageObj.toString();
        }

        // Validate dữ liệu
        if (tenDangNhap.isEmpty() || hoTen.isEmpty() || soDT.isEmpty() || matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        // Kiểm tra số điện thoại hợp lệ
        if (!soDT.matches("\\d{10,11}")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ!");
            return;
        }

        // Kiểm tra mật khẩu tối thiểu 6 ký tự
        if (matKhau.length() < 6) {
            JOptionPane.showMessageDialog(this, "Mật khẩu phải có ít nhất 6 ký tự!");
            return;
        }

        try {
            // Tạo mã nhân viên tự động
            String maNV = generateMaNV();

            // Tạo đối tượng nhân viên
            ql.net.entity.NhanVien nv = ql.net.entity.NhanVien.builder()
                .maNV(maNV)
                .tenDangNhap(tenDangNhap)
                .matKhau(matKhau)
                .hoTen(hoTen)
                .soDT(soDT)
                .chucVu(chucVu)
                .trangThai(trangThai)
                .image(image)
                .build();

            boolean ok = new ql.net.dao.impl.NhanVienDaoImpl().insert(nv);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
                loadNhanVienToTable();
                lamMoiFormNV();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnThemNVActionPerformed

    private void btnTCXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTCXoaActionPerformed
        // TODO add your handling code here:
        xoaDichVuKhoiBang(tblTCBang,"Tiêu chuẩn");
    }//GEN-LAST:event_btnTCXoaActionPerformed

    private void btnGXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGXoaActionPerformed
        // TODO add your handling code here:
        xoaDichVuKhoiBang(tblGBang,"Gaming");
    }//GEN-LAST:event_btnGXoaActionPerformed

    private void btnCNXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCNXoaActionPerformed
        // TODO add your handling code here:
        xoaDichVuKhoiBang(tblCNBang,"Chuyên nghiệp");
    }//GEN-LAST:event_btnCNXoaActionPerformed

    private void btnTDXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTDXoaActionPerformed
        // TODO add your handling code here:
        xoaDichVuKhoiBang(tblTDBang,"Thi đấu");
    }//GEN-LAST:event_btnTDXoaActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        // TODO add your handling code here:
        thanhToanMay("Tiêu chuẩn");
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void btnThanhToan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToan1ActionPerformed
        // TODO add your handling code here:
        thanhToanMay("Gaming");
    }//GEN-LAST:event_btnThanhToan1ActionPerformed

    private void btnTDThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTDThanhToanActionPerformed
        // TODO add your handling code here:
        thanhToanMay("Thi đấu");
    }//GEN-LAST:event_btnTDThanhToanActionPerformed

    private void tblNhapHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhapHangMouseClicked
        // TODO add your handling code here:
        int row = tblNhapHang.getSelectedRow();
        if (row >= 0) {
            try {
                txtMaNhapHang.setText(tblNhapHang.getValueAt(row, 0).toString());
                txtMaNVNhapHang.setText(tblNhapHang.getValueAt(row, 1).toString());
                txtMaDVNhapHang.setText(tblNhapHang.getValueAt(row, 2).toString());
                String ngayNhapStr = tblNhapHang.getValueAt(row, 3).toString();
                if (ngayNhapStr != null && !ngayNhapStr.trim().isEmpty()) {
                    try {
                        // Parse ngày từ String (format: 2025-07-23T14:28:58)
                        if (ngayNhapStr.contains("T")) {
                            ngayNhapStr = ngayNhapStr.split("T")[0]; // Lấy phần ngày thôi
                        }
                        java.util.Date ngayNhap = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(ngayNhapStr);
                        txtNgayNhapHang.setDate(ngayNhap); // txtNgayNhap là JDateChooser
                    } catch (Exception e) {
                        System.err.println("Lỗi parse ngày nhập: " + e.getMessage());
                        txtNgayNhapHang.setDate(null);
                    }
                } else {
                    txtNgayNhapHang.setDate(null);
                }
                txtTenNhaCungCap.setText(tblNhapHang.getValueAt(row, 4).toString());
                txtSoLuongNhapHang.setText(tblNhapHang.getValueAt(row, 5).toString());
                txtDonGiaNhapHang.setText(tblNhapHang.getValueAt(row, 6).toString());
                txtThanhTienNhapHang.setText(tblNhapHang.getValueAt(row, 7).toString());

                System.out.println("✓ Đã load dữ liệu nhập hàng: " + txtMaNhapHang.getText());
            } catch (Exception e) {
                System.err.println("Lỗi load dữ liệu từ bảng nhập hàng: "+e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "lỗi load dữ liệu: "+ e.getMessage());
            }
        }
    }//GEN-LAST:event_tblNhapHangMouseClicked

    private void btnThemNhapHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNhapHangActionPerformed
        // TODO add your handling code here:
        try {
            String maNH = generateMaNhapHang();
            txtMaNhapHang.setText(maNH);

            String maNV = txtMaNVNhapHang.getText().trim();
            String maDV = txtMaDVNhapHang.getText().trim();
            java.util.Date ngayNhap = txtNgayNhapHang.getDate();
            String tenNhaCungCap = txtTenNhaCungCap.getText().trim();
            String soLuongStr = txtSoLuongNhapHang.getText().trim();
            String donGiaStr = txtDonGiaNhapHang.getText().trim();
            String thanhTienStr = txtThanhTienNhapHang.getText().trim();

            if (maNV.isEmpty() || maDV.isEmpty() || ngayNhap == null ||
                tenNhaCungCap.isEmpty() || soLuongStr.isEmpty() || 
                donGiaStr.isEmpty() || thanhTienStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đàay đủ thông tin ");
                return;
            }

            int soLuong = Integer.parseInt(soLuongStr);
            double donGia = Double.parseDouble(donGiaStr);
            double thanhTien = Double.parseDouble(thanhTienStr);

            java.time.LocalDateTime ngaNhapLD = ngayNhap.toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDateTime();

            ql.net.entity.NhapHang nhapHang = new ql.net.entity.NhapHang().builder()
                    .maNhapHang(maNH)
                    .maNV(maNV)
                    .maDV(maDV)
                    .ngayNhap(ngaNhapLD)
                    .tenNhaCungCap(tenNhaCungCap)
                    .soLuong(soLuong)
                    .donGiaNhap(java.math.BigDecimal.valueOf(donGia))
                    .thanhTien(java.math.BigDecimal.valueOf(thanhTien))
                    .build();

            boolean ok = new ql.net.dao.impl.NhapHangDaoImpl().insert(nhapHang);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                loadNhapHangToTable();
                lamMoiFormNhapHang();
            } else {
                JOptionPane.showMessageDialog(this, "thêm nhập hàng thất bại");
            }
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Lỗi định dạng số: "+e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi:  "+e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnThemNhapHangActionPerformed

    private void btnCapNhatNhapHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatNhapHangActionPerformed
        // TODO add your handling code here:
        int row = tblNhapHang.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhập hàng cần cập nhật!");
            return;
        }

        try {
            // 1. Lấy mã nhập hàng từ bảng (không cho sửa)
            String maNH = tblNhapHang.getValueAt(row, 0).toString();

            // 2. Lấy dữ liệu từ form
            String maNV = txtMaNVNhapHang.getText().trim();
            String maDV = txtMaDVNhapHang.getText().trim();
            java.util.Date ngayNhap = txtNgayNhapHang.getDate();
            String tenNhaCungCap = txtTenNhaCungCap.getText().trim();
            String soLuongStr = txtSoLuongNhapHang.getText().trim();
            String donGiaStr = txtDonGiaNhapHang.getText().trim();
            String thanhTienStr = txtThanhTienNhapHang.getText().trim();

            // 3. Validate
            if (maNV.isEmpty() || maDV.isEmpty() || ngayNhap == null || 
                tenNhaCungCap.isEmpty() || soLuongStr.isEmpty() || 
                donGiaStr.isEmpty() || thanhTienStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            // 4. Parse và tạo entity
            int soLuong = Integer.parseInt(soLuongStr);
            double donGia = Double.parseDouble(donGiaStr);
            double thanhTien = Double.parseDouble(thanhTienStr);

            java.time.LocalDateTime ngayNhapLD = ngayNhap.toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDateTime();

            ql.net.entity.NhapHang nhapHang = ql.net.entity.NhapHang.builder()
                .maNhapHang(maNH) // Giữ nguyên mã cũ
                .maNV(maNV)
                .maDV(maDV)
                .ngayNhap(ngayNhapLD)
                .tenNhaCungCap(tenNhaCungCap)
                .soLuong(soLuong)
                .donGiaNhap(java.math.BigDecimal.valueOf(donGia))
                .thanhTien(java.math.BigDecimal.valueOf(thanhTien))
                .build();

            // 5. Cập nhật database
            boolean ok = new ql.net.dao.impl.NhapHangDaoImpl().update(nhapHang);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Cập nhật nhập hàng thành công!");
                loadNhapHangToTable();
                lamMoiFormNhapHang();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật nhập hàng thất bại!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnCapNhatNhapHangActionPerformed

    private void btnXoaNhapHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaNhapHangActionPerformed
        // TODO add your handling code here:
        int row = tblNhapHang.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhập hàng cần xóa!");
            return;
        }

        String maNH = tblNhapHang.getValueAt(row, 0).toString();
        String tenNhaCungCap = tblNhapHang.getValueAt(row, 4).toString();
        String maDV = tblNhapHang.getValueAt(row, 2).toString();

        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Bạn có chắc chắn muốn xóa nhập hàng:\n" +
            "Mã: " + maNH + "\n" +
            "Nhà cung cấp: " + tenNhaCungCap + "\n" +
            "Mã dịch vụ: " + maDV + "?", 
            "Xác nhận xóa", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean ok = new ql.net.dao.impl.NhapHangDaoImpl().delete(maNH);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Xóa nhập hàng thành công!");
                    loadNhapHangToTable();
                    lamMoiFormNhapHang();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa nhập hàng thất bại!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnXoaNhapHangActionPerformed

    private void btnLamMoiNhapHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiNhapHangActionPerformed
        // TODO add your handling code here:
        lamMoiFormNhapHang();
    }//GEN-LAST:event_btnLamMoiNhapHangActionPerformed

    private void txtSoLuongNhapHangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoLuongNhapHangKeyReleased
        // TODO add your handling code here:
        tinhThanhTienNhapHang();
    }//GEN-LAST:event_txtSoLuongNhapHangKeyReleased

    private void txtDonGiaNhapHangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDonGiaNhapHangKeyReleased
        // TODO add your handling code here:
        tinhThanhTienNhapHang();
    }//GEN-LAST:event_txtDonGiaNhapHangKeyReleased

    private void btnNutBaoTriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNutBaoTriActionPerformed
        // TODO add your handling code here:
        try {
            // 1. Kiểm tra đã chọn máy chưa
            if (selectMaMay == null || selectMaMay.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn máy cần bảo trì!");
                return;
            }

            // 2. Lấy trạng thái hiện tại của máy
            String sqlTrangThai = "SELECT TrangThai FROM MayTinh WHERE MaMay = ?";
            String trangThaiHienTai = ql.net.util.XJdbc.getValue(sqlTrangThai, selectMaMay);

            // 3. Nếu máy đang sử dụng thì không cho bảo trì
            if ("Đang sử dụng".equalsIgnoreCase(trangThaiHienTai)) {
                JOptionPane.showMessageDialog(this, 
                    "Máy " + selectMaMay + " đang được sử dụng!\nKhông thể chuyển sang bảo trì.\nVui lòng thanh toán trước khi bảo trì.", 
                    "Không thể bảo trì", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 4. Nếu máy trống, cho phép bảo trì
            if ("Trống".equalsIgnoreCase(trangThaiHienTai)) {
                // 5. Kiểm tra bộ phận bảo trì
                String boPhanBaoTri = cboBoPhanBT.getSelectedItem().toString();
                if (boPhanBaoTri == null || boPhanBaoTri.isEmpty() || "Chọn".equals(boPhanBaoTri)) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn bộ phận bảo trì!");
                    return;
                }

                // 6. Lấy mô tả từ form
                String moTa = "";
                try {
                    if (txtMoTaBaoTri != null) {
                        moTa = txtMoTaBaoTri.getText().trim();
                    }
                } catch (Exception e) {
                    moTa = "";
                }

                // 7. Gộp bộ phận vào mô tả
                String moTaGop = "[Bộ phận: " + boPhanBaoTri + "] " + moTa;

                // 8. Sinh mã bảo trì tự động
                String maBaoTri = generateMaBaoTri(); // Bạn đã có hàm này hoặc có thể dùng tương tự như generateMaDV

                // 9. Lấy mã nhân viên hiện tại
                String maNV = curentUser.getMaNV();

                // 10. Ngày bảo trì
                java.sql.Date ngayBaoTri = new java.sql.Date(System.currentTimeMillis());

                // 11. Chi phí sửa mặc định = 0
                double chiPhiSua = 0;

                // 12. Trạng thái bảo trì
                String trangThai = "Bảo trì";

                // 13. Cập nhật trạng thái máy thành "Bảo trì"
                String sqlUpdateMay = "UPDATE MayTinh SET TrangThai = N'Bảo trì' WHERE MaMay = ?";
                boolean okUpdateMay = ql.net.util.XJdbc.executeUpdate(sqlUpdateMay, selectMaMay) > 0;
                if (!okUpdateMay) {
                    JOptionPane.showMessageDialog(this, "Lỗi cập nhật trạng thái máy!");
                    return;
                }

                // 14. Thêm vào bảng BaoTri (lưu bộ phận vào mô tả)
                String sqlInsertBaoTri = "INSERT INTO BaoTri (MaBaoTri, MaMay, MaNV, NgayBaoTri, MoTa, ChiPhiSua, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";
                int result = ql.net.util.XJdbc.executeUpdate(sqlInsertBaoTri, maBaoTri, selectMaMay, maNV, ngayBaoTri, moTaGop, chiPhiSua, trangThai);

                if (result > 0) {
                    // 15. Thông báo thành công
                    JOptionPane.showMessageDialog(this, 
                        "Đã thêm vào danh sách bảo trì!\n" +
                        "Mã bảo trì: " + maBaoTri + "\n" +
                        "Bộ phận: " + boPhanBaoTri + "\n" +
                        "Mô tả: " + (moTa.isEmpty() ? "Không có" : moTa));
                    // 16. Refresh giao diện
                    loadMayBaoTri();
                    loadDanhSachBaoTriToTable();

                    // 17. Clear form
                    cboBoPhanBT.setSelectedIndex(0);
                    try {
                        if (txtMoTaBaoTri != null) {
                            txtMoTaBaoTri.setText("");
                        }
                    } catch (Exception e) {
                        System.out.println("Không thể clear field mô tả");
                    }
                    // 18. Cập nhật trạng thái hiển thị máy
                    txtBTTrangThai.setText("Bảo trì");
                    txtBTTrangThai.setBackground(new java.awt.Color(255,255,0)); // Vàng
                } else {
                    // Rollback nếu thêm bảo trì thất bại
                    ql.net.util.XJdbc.executeUpdate("UPDATE MayTinh SET TrangThai = N'Trống' WHERE MaMay = ?", selectMaMay);
                    JOptionPane.showMessageDialog(this, "Lỗi thêm vào danh sách bảo trì!");
                }
            } else if ("Bảo trì".equalsIgnoreCase(trangThaiHienTai)) {
                JOptionPane.showMessageDialog(this, 
                    "Máy " + selectMaMay + " đã đang trong trạng thái bảo trì!", 
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Trạng thái máy không hợp lệ: " + trangThaiHienTai, 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.err.println("Lỗi bảo trì máy: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + e.getMessage());
        }
    }//GEN-LAST:event_btnNutBaoTriActionPerformed
    private javax.swing.JTextField timTextFieldMoTa() {
        try {
            // Thử các tên có thể có cho field mô tả
            String[] possibleNames = {
                "txtMoTaBaoTri",
                "txtMoTa", 
                "txtGhiChu",
                "txtNoiDung",
                "jTextField"  // Tên mặc định NetBeans
            };

            // Tìm trong container bảo trì
            java.awt.Container container = this.getContentPane();
            return timTextFieldByNames(container, possibleNames);

        } catch (Exception e) {
            System.err.println("Lỗi tìm TextField mô tả: " + e.getMessage());
            return null;
        }
    }

    // METHOD hỗ trợ tìm TextField theo tên
    private javax.swing.JTextField timTextFieldByNames(java.awt.Container container, String[] names) {
        for (java.awt.Component comp : container.getComponents()) {
            if (comp instanceof javax.swing.JTextField) {
                javax.swing.JTextField textField = (javax.swing.JTextField) comp;
                String componentName = textField.getName();

                if (componentName != null) {
                    for (String name : names) {
                        if (componentName.contains(name)) {
                            System.out.println("✓ Tìm thấy TextField: " + componentName);
                            return textField;
                        }
                    }
                }
            } else if (comp instanceof java.awt.Container) {
                javax.swing.JTextField found = timTextFieldByNames((java.awt.Container) comp, names);
                if (found != null) return found;
            }
        }
        return null;
    }
    private String generateMaBaoTri() {
        try {
            String sql = "SELECT COUNT(*) FROM BaoTri";
            String countStr = ql.net.util.XJdbc.getValue(sql);
            int count = Integer.parseInt(countStr != null ? countStr : "0");
            return String.format("BT%02d", count + 1);
        } catch (Exception e) {
            return "BT" + String.format("%02d", System.currentTimeMillis() % 100);
        }
    }
    private void btnBTThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBTThanhToanActionPerformed
        // TODO add your handling code here:
        try {
            // 1. Lấy chi phí bảo trì từ TextField
            String tongChiPhiStr = txtBTTongChiPhi.getText().trim();
            System.out.println("DEBUG: Giá trị nhập chi phí: '" + tongChiPhiStr + "'");
            
            if (tongChiPhiStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập chi phí bảo trì!");
                txtBTTongChiPhi.requestFocus();
                return;
            }

            // 2. Kiểm tra chi phí hợp lệ
            double tongChiPhi = 0;
            try {
                tongChiPhi = Double.parseDouble(tongChiPhiStr.replace(",", ""));
                if (tongChiPhi <= 0) {
                    JOptionPane.showMessageDialog(this, "Chi phí bảo trì phải lớn hơn 0!");
                    txtTongChiTieu.requestFocus();
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Chi phí bảo trì không hợp lệ!");
                txtTongChiTieu.requestFocus();
                return;
            }

            // 3. Lấy thông tin bộ phận, mô tả
            String boPhanBaoTri = cboBoPhanBT.getSelectedItem().toString();
            String moTa = txtMoTaBaoTri != null ? txtMoTaBaoTri.getText().trim() : "";

            // 4. Xác nhận hoàn thành bảo trì
            int confirm = JOptionPane.showConfirmDialog(this,
                String.format("Hoàn thành bảo trì máy %s?\nBộ phận: %s\nChi phí: %,.0f VNĐ\nMô tả: %s",
                    selectMaMay, boPhanBaoTri, tongChiPhi, moTa),
                "Xác nhận hoàn thành bảo trì", JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) return;

            // 5. Cập nhật trạng thái máy về "Trống"
            String sqlUpdateMay = "UPDATE MayTinh SET TrangThai = N'Trống' WHERE MaMay = ?";
            boolean okUpdateMay = ql.net.util.XJdbc.executeUpdate(sqlUpdateMay, selectMaMay) > 0;

            if (!okUpdateMay) {
                JOptionPane.showMessageDialog(this, "❌ Lỗi cập nhật trạng thái máy!");
                return;
            }

            // 6. Cập nhật chi phí bảo trì vào bảng BaoTri
            String sqlUpdateBaoTri = "UPDATE BaoTri SET TrangThai = N'Đã hoàn thành', ChiPhiSua = ? WHERE MaMay = ? AND TrangThai = N'Bảo trì'";
            boolean okUpdateBaoTri = ql.net.util.XJdbc.executeUpdate(sqlUpdateBaoTri,
                java.math.BigDecimal.valueOf(tongChiPhi),
                selectMaMay) > 0;

            // 7. Nếu cập nhật thành công, thông báo và làm mới giao diện
            if (okUpdateBaoTri) {
                JOptionPane.showMessageDialog(this,
                    "✅ Hoàn thành bảo trì thành công!\n" +
                    "Máy " + selectMaMay + " đã chuyển về trạng thái Trống.\n" +
                    "Chi phí bảo trì: " + String.format("%,.0f VNĐ", tongChiPhi));

                // 8. Làm mới giao diện tab bảo trì
                loadMayBaoTri();
                loadDanhSachBaoTriToTable();

                // 9. Clear form nhập liệu
                cboBoPhanBT.setSelectedIndex(0);
                txtTongChiTieu.setText("");
                txtMoTaBaoTri.setText("");
                txtBTTrangThai.setText("Trống");
                txtBTTrangThai.setBackground(new java.awt.Color(144, 238, 144)); // Xanh lá
            } else {
                // Rollback trạng thái máy nếu cập nhật bảo trì thất bại
                ql.net.util.XJdbc.executeUpdate("UPDATE MayTinh SET TrangThai = N'Bảo trì' WHERE MaMay = ?", selectMaMay);
                JOptionPane.showMessageDialog(this, "❌ Lỗi cập nhật thông tin bảo trì!");
            }
        } catch (Exception e) {
            System.err.println("Lỗi hoàn thành bảo trì: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + e.getMessage());
        }
    }//GEN-LAST:event_btnBTThanhToanActionPerformed

    private void btnNapTienKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNapTienKHActionPerformed
        // TODO add your handling code here:
        // Kiểm tra xem có khách hàng nào được chọn không
        int selectedRow = tblBangKhachHang.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn khách hàng cần nạp tiền!", 
                "Thông báo", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy thông tin khách hàng được chọn từ bảng
        String maKH = tblBangKhachHang.getValueAt(selectedRow, 0).toString(); // Cột 0: Mã khách hàng
        String hoTen = tblBangKhachHang.getValueAt(selectedRow, 1).toString(); // Cột 1: Họ tên
        String soDT = tblBangKhachHang.getValueAt(selectedRow, 2).toString();  // Cột 2: Số điện thoại
        String soDu = tblBangKhachHang.getValueAt(selectedRow, 3).toString();  // Cột 3: Số dư tài khoản

        // Mở RechargeJDialog
        openRechargeDialog(maKH, hoTen, soDT, soDu);
    }//GEN-LAST:event_btnNapTienKHActionPerformed

    private void btnThongTinHeThongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongTinHeThongActionPerformed
        // TODO add your handling code here:
        showThongTinHeThong();
    }//GEN-LAST:event_btnThongTinHeThongActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        lamMoiBaoCao();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void cboBaoCaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboBaoCaoActionPerformed
        // TODO add your handling code here:
        // Lấy giá trị được chọn từ combobox
        String loaiBaoCao = cboBaoCao.getSelectedItem().toString();

        // Xóa dữ liệu hiện tại trong bảng
        DefaultTableModel model = (DefaultTableModel) tblBaoCao.getModel();
        model.setRowCount(0);

        // Thay đổi tên cột dựa theo loại báo cáo
        switch (loaiBaoCao) {
            case "VIP":
                // Đặt tên cột cho báo cáo VIP
                model.setColumnIdentifiers(new Object[]{"Mã VIP", "Tên Khách Hàng", "Loại VIP", "Tổng Chi Tiêu", "Ngày Tham Gia", "Ngày Hết Hạn", "Trạng Thái"});
                // Gọi phương thức để lấy và hiển thị dữ liệu VIP
//                loadBaoCaoVIP();
                break;

            case "Máy":
                // Đặt tên cột cho báo cáo Máy
                model.setColumnIdentifiers(new Object[]{"Mã Máy", "Tên Máy", "Loại Máy", "Trạng Thái", "Giá/Giờ", "Thời Gian Sử Dụng"});
                // Gọi phương thức để lấy và hiển thị dữ liệu Máy
//                loadBaoCaoMay();
                break;

            case "Dịch vụ":
                // Đặt tên cột cho báo cáo Dịch vụ
                model.setColumnIdentifiers(new Object[]{"Mã DV", "Tên Dịch Vụ", "Loại DV", "Đơn Giá", "Số Lượng Đã Bán", "Doanh Thu"});
                // Gọi phương thức để lấy và hiển thị dữ liệu Dịch vụ
//                loadBaoCaoDichVu();
                break;

            case "Nhân viên":
                // Đặt tên cột cho báo cáo Nhân viên
                model.setColumnIdentifiers(new Object[]{"Mã NV", "Tên NV", "SDT", "Chức Vụ"});
                // Gọi phương thức để lấy và hiển thị dữ liệu Nhân viên
//                loadBaoCaoNhanVien();
                break;

            case "Doanh Thu":
                // Đặt tên cột cho báo cáo Doanh Thu
                model.setColumnIdentifiers(new Object[]{"Ngày", "Doanh Thu Máy", "Doanh Thu DV", "Doanh Thu Nạp Tiền", "Tổng"});
                // Gọi phương thức để lấy và hiển thị dữ liệu Doanh Thu
//                loadBaoCaoDoanhThu();
                break;

            case "Bảo trì":
                // Đặt tên cột cho báo cáo Bảo trì
                model.setColumnIdentifiers(new Object[]{"Mã Bảo Trì", "Mã Máy", "Tên Máy", "Ngày Bảo Trì", "Chi Phí", "Mô Tả"});
                // Gọi phương thức để lấy và hiển thị dữ liệu Bảo trì
//                loadBaoCaoBaoTri();
                break;

            case "Nạp tiền":
                // Đặt tên cột cho báo cáo Nạp tiền
                model.setColumnIdentifiers(new Object[]{"Mã Nạp Tiền", "Mã Khách Hàng", "Tên Khách Hàng", "Số Tiền", "Ngày Nạp", "Nhân Viên Thực Hiện"});
                // Gọi phương thức để lấy và hiển thị dữ liệu Nạp tiền
//                loadBaoCaoNapTien();
                break;

            case "Giao dịch":
                // Đặt tên cột cho báo cáo Giao dịch
                model.setColumnIdentifiers(new Object[]{"Mã GD", "Loại GD", "Mã KH", "Tên KH", "Số Tiền", "Thời Gian", "Mô Tả"});
                // Gọi phương thức để lấy và hiển thị dữ liệu Giao dịch
//                loadBaoCaoGiaoDich();
                break;
        }
    }//GEN-LAST:event_cboBaoCaoActionPerformed

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        // TODO add your handling code here:
        locDuLieuBaoCao();
    }//GEN-LAST:event_btnLocActionPerformed
    private void locDuLieuBaoCao() {
        Date tuNgay = dateTuNgay.getDate();
        Date denNgay = dateDenNgay.getDate();
        if (tuNgay == null) tuNgay = new Date(0);
        if (denNgay == null) denNgay = new Date();

        String loaiBaoCao = cboBaoCao.getSelectedItem().toString();

        switch (loaiBaoCao) {
            case "VIP":
                loadDuLieuVIP(thanhVienVipDao.getVIPByDateRange(tuNgay, denNgay));
                if (tabbedPaneBaoCao.getTabCount() > 0) {
                    tabbedPaneBaoCao.setSelectedIndex(0);
                }
                break;
            case "Máy":
                loadDuLieuMay(mayTinhDao.getMayByDateRange(tuNgay, denNgay));
                if (tabbedPaneBaoCao.getTabCount() > 1) {
                    tabbedPaneBaoCao.setSelectedIndex(1);
                }
                break;
            case "Dịch vụ":
                loadDuLieuDichVu(dichVuDao.getDichVuByDateRange(tuNgay, denNgay));
                if (tabbedPaneBaoCao.getTabCount() > 2) {
                    tabbedPaneBaoCao.setSelectedIndex(2);
                }
                break;
            case "Nhân viên":
                loadDuLieuNhanVien(nhanVienDao.getNhanVienByDateRange(tuNgay, denNgay));
                if (tabbedPaneBaoCao.getTabCount() > 3) {
                    tabbedPaneBaoCao.setSelectedIndex(3);
                }
                break;
            case "Doanh Thu":
                loadDuLieuDoanhThu(hoaDonDao.getDoanhThuByDateRange(tuNgay, denNgay));
                if (tabbedPaneBaoCao.getTabCount() > 4) {
                    tabbedPaneBaoCao.setSelectedIndex(4);
                }
                break;
            case "Bảo trì":
                loadDuLieuBaoTri(baoTriDao.getBaoTriByDateRange(tuNgay, denNgay));
                if (tabbedPaneBaoCao.getTabCount() > 5) {
                    tabbedPaneBaoCao.setSelectedIndex(5);
                }
                break;
            case "Nạp tiền":
                loadDuLieuNapTien(napTienDao.getNapTienByDateRange(tuNgay, denNgay));
                if (tabbedPaneBaoCao.getTabCount() > 6) {
                    tabbedPaneBaoCao.setSelectedIndex(6);
                }
                break;
            case "Giao dịch":
                loadDuLieuGiaoDich(lichSuGiaoDichDao.getGiaoDichByDateRange(tuNgay, denNgay));
                if (tabbedPaneBaoCao.getTabCount() > 7) {
                    tabbedPaneBaoCao.setSelectedIndex(7);
                }
                break;
        }
    }

    private void lamMoiBaoCao() {
        // Xóa dữ liệu trong bảng
        DefaultTableModel model = (DefaultTableModel) tblBaoCao.getModel();
        model.setRowCount(0);

        // Đặt lại các điều khiển về giá trị mặc định
        cboBaoCao.setSelectedIndex(0);
        dateTuNgay.setDate(new Date());
        dateDenNgay.setDate(new Date());
    }
    private void loadDuLieuGiaoDich(List<LichSuGiaoDich> list) {
        DefaultTableModel model = (DefaultTableModel) tblBaoCao.getModel();
        model.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        KhachHangDao khachHangDao = new KhachHangDaoImpl();

        for (LichSuGiaoDich gd : list) {
            String tenKH = "";
            KhachHang kh = khachHangDao.selectById(gd.getMaKH());
            if (kh != null) tenKH = kh.getHoTen();

            Object[] row = new Object[]{
                gd.getMaGiaoDich(),                  // Mã GD
                gd.getLoaiGiaoDich(),                // Loại GD
                gd.getMaKH(),                        // Mã KH
                tenKH,                               // Tên KH
                gd.getSoTien(),                      // Số Tiền
                sdf.format(java.sql.Timestamp.valueOf(gd.getNgayGiaoDich())), // Thời Gian
                ""                                   // Mô Tả (nếu có thì thay bằng gd.getMoTa())
            };
            model.addRow(row);
        }
    }

    private void loadDuLieuVIP(List<ThanhVienVIP> list) {
        DefaultTableModel model = (DefaultTableModel) tblBaoCao.getModel();
        model.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (ThanhVienVIP vip : list) {
            String tenKH = vip.getKhachHang() != null ? vip.getKhachHang().getHoTen() : "";
            Object[] row = new Object[]{
                vip.getMaVIP(), // Mã VIP
                tenKH,          // Tên Khách Hàng
                vip.getLoaiVIP(), // Loại VIP
                vip.getTongChiTieu(), // Tổng Chi Tiêu
                vip.getNgayThamGia() != null ? sdf.format(java.sql.Date.valueOf(vip.getNgayThamGia())) : "", // Ngày Tham Gia
                vip.getNgayHetHan() != null ? sdf.format(java.sql.Date.valueOf(vip.getNgayHetHan())) : "",   // Ngày Hết Hạn
                vip.isTrangThai() ? "Còn hiệu lực" : "Hết hạn" // Trạng Thái
            };
            model.addRow(row);
        }
    }

    private void loadDuLieuMay(List<MayTinh> list) {
        DefaultTableModel model = (DefaultTableModel) tblBaoCao.getModel();
        model.setRowCount(0);
        for (MayTinh mt : list) {
            Object[] row = new Object[]{
                mt.getMaMay(),           // Mã Máy
                mt.getTenMay(),          // Tên Máy
                mt.getLoaiMay(),         // Loại Máy
                mt.getTrangThai(),       // Trạng Thái
                mt.getGiaTheoGio(),      // Giá/Giờ
                mt.getThoiGianSuDung()  // Thời Gian Sử Dụng
            };
            model.addRow(row);
        }
    }

    private void loadDuLieuDichVu(List<DichVu> list) {
        DefaultTableModel model = (DefaultTableModel) tblBaoCao.getModel();
        model.setRowCount(0);
        for (DichVu dv : list) {
            Object[] row = new Object[]{
                dv.getMaDV(),           // Mã DV
                dv.getTenDV(),          // Tên Dịch Vụ
                dv.getLoaiDV(),         // Loại DV
                dv.getDonGia(),         // Đơn Giá
                dv.getSoLuong(),        // Số Lượng Đã Bán
                dv.getThanhTien()       // Doanh Thu
            };
            model.addRow(row);
        }
    }

    private void loadDuLieuNhanVien(List<NhanVien> list) {
        DefaultTableModel model = (DefaultTableModel) tblBaoCao.getModel();
        model.setRowCount(0);
        for (NhanVien nv : list) {
            model.addRow(new Object[]{
                nv.getMaNV(),
                nv.getHoTen(),
                nv.getSoDT(),
                nv.getChucVu(),
                nv.isTrangThai(),
                nv.getNgayTao(),
                nv.getImage()
            });
        }
    }

    private void loadDuLieuDoanhThu(List<HoaDon> list) {
        DefaultTableModel model = (DefaultTableModel) tblBaoCao.getModel();
        model.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        for (HoaDon hd : list) {
            Object[] row = new Object[5];
            // Chuyển LocalDateTime về Date để format
            Date ngayTaoDate = java.sql.Timestamp.valueOf(hd.getNgayTao());
            row[0] = sdf.format(ngayTaoDate); // Ngày tạo
            row[1] = hd.getTongTienMay();     // Doanh thu máy
            row[2] = hd.getTongTienDichVu();  // Doanh thu dịch vụ
            row[3] = hd.getTongTien().subtract(hd.getTongTienMay()).subtract(hd.getTongTienDichVu()); // Doanh thu nạp tiền (nếu có)
            row[4] = hd.getTongTien();        // Tổng
            model.addRow(row);
        }
    }

    private void loadDuLieuBaoTri(List<BaoTri> list) {
        DefaultTableModel model = (DefaultTableModel) tblBaoCao.getModel();
        model.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        for (BaoTri bt : list) {
            Object[] row = new Object[6];
            row[0] = bt.getMaBaoTri(); // Mã Bảo Trì
            row[1] = bt.getMaMay();    // Mã Máy
            row[2] = bt.getNhanVien() != null ? bt.getNhanVien().getHoTen() : ""; // Tên Nhân Viên
            row[3] = bt.getNgayBaoTri() != null ? sdf.format(java.sql.Date.valueOf(bt.getNgayBaoTri())) : ""; // Ngày Bảo Trì
            row[4] = bt.getChiPhiSua(); // Chi Phí
            row[5] = bt.getMoTa();      // Mô Tả
            model.addRow(row);
        }
    }

    private void loadDuLieuNapTien(List<NapTien> list) {
        DefaultTableModel model = (DefaultTableModel) tblBaoCao.getModel();
        model.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        KhachHangDao khachHangDao = new KhachHangDaoImpl();
        ql.net.dao.NhanVienDao nhanVienDao = new NhanVienDaoImpl();

        for (NapTien nt : list) {
            String tenKH = "";
            KhachHang kh = khachHangDao.selectById(nt.getMaKH());
            if (kh != null) tenKH = kh.getHoTen();

            String tenNV = "";
            NhanVien nv = nhanVienDao.selectById(nt.getMaNV());
            if (nv != null) tenNV = nv.getHoTen();

            Object[] row = new Object[]{
                nt.getMaNapTien(),
                nt.getMaKH(),
                tenKH,
                nt.getSoTienNap(),
                sdf.format(java.sql.Timestamp.valueOf(nt.getNgayNap())),
                tenNV // Hiển thị tên nhân viên thực hiện
            };
            model.addRow(row);
        }
    }


    // Thêm phương thức cập nhật số liệu
    public void capNhatSoLieuThongKe() {
        try {
            // Lấy dữ liệu từ DAO
            double doanhThu = thongKeDao.getDoanhThu();
            int soMay = thongKeDao.getSoMay();
            int soDichVu = thongKeDao.getSoDichVu();
            int soNhanVien = thongKeDao.getSoNhanVien();
            int soVIP = thongKeDao.getSoVIP();
            int soLanBaoTri = thongKeDao.getSoLanBaoTri();
            int soLanNapTien = thongKeDao.getSoLanNapTien();
            int soGiaoDich = thongKeDao.getSoGiaoDich();

            // Định dạng số tiền
            DecimalFormat formatter = new DecimalFormat("#,###");

            // Cập nhật lên giao diện (sử dụng đúng tên biến bạn đã đặt)
            // Giả sử các label của bạn có tên là:
            lblDoanhThu.setText(formatter.format(doanhThu) + " VNĐ");
            lblSoMay.setText(String.valueOf(soMay));
            lblSoDichVu.setText(String.valueOf(soDichVu));
            lblSoNhanVien.setText(String.valueOf(soNhanVien));
            lblSoVIP.setText(String.valueOf(soVIP));
            lblSoLanBaoTri.setText(String.valueOf(soLanBaoTri));
            lblSoLanNap.setText(String.valueOf(soLanNapTien));
            lblSoGiaoDich.setText(String.valueOf(soGiaoDich));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi lấy dữ liệu thống kê: " + e.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void showThongTinHeThong() {
        String info = """
            HỆ THỐNG QUẢN LÝ TIỆM NET
            Phiên bản: 1.3
            Tác giả: Đoàn Tấn Hiếu
            Ngày phát hành: 08/2025

            Chức năng chính:
            - Quản lý máy tính, khách hàng, dịch vụ, hóa đơn, bảo trì, báo cáo, nhân viên.
            - Hỗ trợ quản lý trạng thái máy, dịch vụ ăn uống, bảo trì máy, thống kê doanh thu.

            Liên hệ hỗ trợ: dtanhieu123@gmail.com - 0795 568 318
            Bản quyền © 2025 Đoàn Tấn Hiếu
            """;
        JOptionPane.showMessageDialog(this, info, "Thông tin hệ thống-Tấn Hiếu", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void openRechargeDialog(String maKH, String hoTen, String soDT, String soDu) {
        try {
            // Tạo RechargeJDialog
            ql.net.ui.RechargeJDialog rechargeDialog = new ql.net.ui.RechargeJDialog(this, true);

            // Thiết lập thông tin khách hàng vào dialog
            rechargeDialog.setThongTinKhachHang(maKH, hoTen, soDT, soDu);

            // Thiết lập thông tin nhân viên hiện tại
            if (curentUser != null) {
                rechargeDialog.setThongTinNhanVien(curentUser.getMaNV(), curentUser.getHoTen());
            }

            // Hiển thị dialog ở giữa màn hình
            rechargeDialog.setLocationRelativeTo(this);
            rechargeDialog.setVisible(true);

            // Sau khi đóng dialog, refresh lại bảng khách hàng để cập nhật số dư
            loadKhachHangToTable();

            System.out.println("✅ Đã mở RechargeJDialog cho khách hàng: " + maKH + " - " + hoTen);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Lỗi mở giao diện nạp tiền: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }


    // THÊM METHOD load danh sách bảo trì vào bảng
    private void loadDanhSachBaoTriToTable() {
        try {
            if (tblDanhSachBaoTri == null) return;
            if (selectMaMay == null || selectMaMay.isEmpty()) {
                ((javax.swing.table.DefaultTableModel) tblDanhSachBaoTri.getModel()).setRowCount(0);
                return;
            }
            String sql = "SELECT MoTa, FORMAT(NgayBaoTri, 'dd/MM/yyyy HH:mm') as NgayBaoTri, TrangThai " +
                         "FROM BaoTri WHERE TrangThai = N'Bảo trì' AND MaMay = ? ORDER BY NgayBaoTri DESC";
            java.sql.ResultSet rs = ql.net.util.XJdbc.executeQuery(sql, selectMaMay);
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblDanhSachBaoTri.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                String moTaGop = rs.getString("MoTa");
                String boPhan = "";
                String moTa = moTaGop;
                if (moTaGop != null && moTaGop.startsWith("[Bộ phận:")) {
                    int end = moTaGop.indexOf("]");
                    if (end > 0) {
                        boPhan = moTaGop.substring(10, end);
                        moTa = moTaGop.substring(end + 1).trim();
                    }
                }
                model.addRow(new Object[]{
                    boPhan,
                    moTa,
                    rs.getString("NgayBaoTri"),
                    rs.getString("TrangThai")
                });
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //cho số lượng và đơn giá tự tính thành tiền
    private void tinhThanhTienNhapHang(){
        try {
            String soLuongStr = txtSoLuongNhapHang.getText().trim();
            String donGiaStr = txtDonGiaNhapHang.getText().trim();
            
            if (!soLuongStr.isEmpty() && !donGiaStr.isEmpty()) {
                int soLuong = Integer.parseInt(soLuongStr);
                double donGia = Double.parseDouble(donGiaStr);
                double thanhTien = soLuong * donGia ;
                
                txtThanhTienNhapHang.setText(String.format("%.2f", thanhTien));
            }
        } catch (Exception e) {
            System.out.println("Lỗi: "+e);
        }
    }

    //đếm mã nhập hàng
    private String generateMaNhapHang(){
        try {
            // SỬA: Dùng DAO thay vì SQL trực tiếp để tránh lỗi tên cột
            java.util.List<ql.net.entity.NhapHang> list = new ql.net.dao.impl.NhapHangDaoImpl().selectAll();
            int max = 0;

            for (ql.net.entity.NhapHang nh : list) {
                try {
                    // Lấy số từ mã nhập hàng (NH01 → 1, NH08 → 8...)
                    String maNH = nh.getMaNhapHang();
                    if (maNH != null && maNH.startsWith("NH")) {
                        int num = Integer.parseInt(maNH.replace("NH", ""));
                        if (num > max) {
                            max = num;
                        }
                    }
                } catch (Exception e) {
                    // Bỏ qua mã không đúng định dạng
                    System.out.println("Mã NH không hợp lệ: " + nh.getMaNhapHang());
                }
            }

            // Trả về mã tiếp theo theo thứ tự: NH09, NH10...
            String nextMa = String.format("NH%02d", max + 1);
            System.out.println("✓ Sinh mã nhập hàng mới: " + nextMa + " (max hiện tại: " + max + ")");
            return nextMa;

        } catch (Exception e) {
            System.err.println("Lỗi generateMaNhapHang: " + e.getMessage());
            e.printStackTrace();

            // Fallback: Tìm mã cuối từ bảng hiển thị
            try {
                int rowCount = tblNhapHang.getRowCount();
                if (rowCount > 0) {
                    // Tìm số lớn nhất từ bảng
                    int maxFromTable = 0;
                    for (int i = 0; i < rowCount; i++) {
                        String maNH = tblNhapHang.getValueAt(i, 0).toString();
                        if (maNH.startsWith("NH")) {
                            int num = Integer.parseInt(maNH.replace("NH", ""));
                            if (num > maxFromTable) {
                                maxFromTable = num;
                            }
                        }
                    }
                    return String.format("NH%02d", maxFromTable + 1);
                }
            } catch (Exception ex) {
                System.err.println("Fallback cũng lỗi: " + ex.getMessage());
            }

            return "NH01"; // Fallback cuối cùng
        }
    }
    //làm mới
    private void lamMoiFormNhapHang(){
        txtMaNhapHang.setText("");
        txtMaNVNhapHang.setText("");
        txtMaDVNhapHang.setText("");
        txtNgayNhapHang.setDate(null);
        txtTenNhaCungCap.setText("");
        txtSoLuongNhapHang.setText("");
        txtDonGiaNhapHang.setText("");
        txtThanhTienNhapHang.setText("");
        
        txtMaNhapHang.setText(generateMaNhapHang());
        
        System.out.println("Đã làm mới from nhập hàng");
    }



    // Method chung xử lý thanh toán cho tất cả tab máy
    private void thanhToanMay(String loaiMay) {
        if (selectMaMay == null || selectMaMay.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn máy cần thanh toán!");
            return;
        }

        try {
            System.out.println("=== DEBUG THANH TOÁN ===");
            System.out.println("Máy được chọn: " + selectMaMay);
            System.out.println("Loại máy: " + loaiMay);

            // 1. Tìm phiên sử dụng đang hoạt động
            ql.net.entity.PhienSuDung phien = new ql.net.dao.impl.PhienSuDungDaoImpl().selectPhienDangSuDung(selectMaMay);
            if (phien == null) {
                JOptionPane.showMessageDialog(this, "Máy này chưa được sử dụng!");
                return;
            }

            // 2. Tìm hóa đơn
            String sql = "SELECT MaHD FROM HoaDon WHERE MaPhien = ? AND TrangThai IN (N'Chờ thanh toán', N'Chưa thanh toán')";
            String maHD = ql.net.util.XJdbc.getValue(sql, phien.getMaPhien());

            if (maHD == null) {
                maHD = taoHoaDonChoPhienThanhToan(phien);
                if (maHD == null) {
                    JOptionPane.showMessageDialog(this, "Không thể tạo hóa đơn cho phiên này!");
                    return;
                }
            }

            // 3. Tính thời gian sử dụng
            java.time.LocalDateTime batDau = phien.getThoiGianBatDau();
            java.time.LocalDateTime ketThuc = java.time.LocalDateTime.now();
            long phutSuDung = java.time.Duration.between(batDau, ketThuc).toMinutes();
            double gioSuDung = Math.ceil(phutSuDung / 60.0);

            // 4. Lấy giá máy
            String sqlGiaMay = "SELECT GiaTheoGio FROM MayTinh WHERE MaMay = ?";
            Object giaObj = ql.net.util.XJdbc.getValue(sqlGiaMay, selectMaMay);
            double giaMay = 0;

            if (giaObj != null) {
                try {
                    if (giaObj instanceof java.math.BigDecimal) {
                        giaMay = ((java.math.BigDecimal) giaObj).doubleValue();
                    } else if (giaObj instanceof String) {
                        giaMay = Double.parseDouble((String) giaObj);
                    } else if (giaObj instanceof Number) {
                        giaMay = ((Number) giaObj).doubleValue();
                    }
                } catch (Exception e) {
                    System.err.println("Lỗi parse giá máy: " + e.getMessage());
                    giaMay = 0;
                }
            }

            double tongTienMay = gioSuDung * giaMay;

            // 5. SỬA: Lấy tổng tiền dịch vụ từ TextField hiện tại (thay vì database)
            double tongTienDichVu = 0;

            // Xác định tab hiện tại và lấy tổng food từ TextField tương ứng
            String currentTab = getCurrentActiveTab();
            JTextField txtTongFood = null;

            switch (currentTab) {
                case "tieuChuan":
                    txtTongFood = txtTCFood;
                    break;
                case "gaming":
                    txtTongFood = txtGFood;
                    break;
                case "chuyenNghiep":
                    txtTongFood = txtCNFood;
                    break;
                case "thiDau":
                    txtTongFood = txtTDFood;
                    break;
            }

            // Lấy tiền dịch vụ từ TextField hiển thị
            if (txtTongFood != null && !txtTongFood.getText().trim().isEmpty()) {
                try {
                    String tongFoodStr = txtTongFood.getText().trim().replace(",", "");
                    tongTienDichVu = Double.parseDouble(tongFoodStr);
                    System.out.println("✅ Lấy tổng tiền dịch vụ từ TextField: " + tongTienDichVu);
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Lỗi parse tổng tiền dịch vụ từ TextField: " + e.getMessage());

                    // Fallback: Lấy từ database nếu TextField lỗi
                    String sqlTongDV = "SELECT ISNULL(SUM(ThanhTien), 0) as TongDV FROM ChiTietHoaDon WHERE MaHD = ?";
                    Object tongDVObj = ql.net.util.XJdbc.getValue(sqlTongDV, maHD);
                    if (tongDVObj != null) {
                        try {
                            if (tongDVObj instanceof java.math.BigDecimal) {
                                tongTienDichVu = ((java.math.BigDecimal) tongDVObj).doubleValue();
                            } else if (tongDVObj instanceof String) {
                                tongTienDichVu = Double.parseDouble((String) tongDVObj);
                            } else if (tongDVObj instanceof Number) {
                                tongTienDichVu = ((Number) tongDVObj).doubleValue();
                            }
                        } catch (Exception ex) {
                            tongTienDichVu = 0;
                        }
                    }
                }
            } else {
                // Nếu TextField trống, lấy từ database
                String sqlTongDV = "SELECT ISNULL(SUM(ThanhTien), 0) as TongDV FROM ChiTietHoaDon WHERE MaHD = ?";
                Object tongDVObj = ql.net.util.XJdbc.getValue(sqlTongDV, maHD);
                if (tongDVObj != null) {
                    try {
                        if (tongDVObj instanceof java.math.BigDecimal) {
                            tongTienDichVu = ((java.math.BigDecimal) tongDVObj).doubleValue();
                        } else if (tongDVObj instanceof String) {
                            tongTienDichVu = Double.parseDouble((String) tongDVObj);
                        } else if (tongDVObj instanceof Number) {
                            tongTienDichVu = ((Number) tongDVObj).doubleValue();
                        }
                    } catch (Exception e) {
                        tongTienDichVu = 0;
                    }
                }
            }

            System.out.println("✅ Tổng tiền máy: " + tongTienMay);
            System.out.println("✅ Tổng tiền dịch vụ: " + tongTienDichVu);

            // 6. Kiểm tra VIP và tính giảm giá (chỉ áp dụng cho tiền máy)
            double phanTramGiam = 0;
            boolean isVIP = false;
            String maKH = phien.getMaKH();
            String loaiVIP = "";

            String sqlVIP = "SELECT v.LoaiVIP FROM ThanhVienVIP v WHERE v.MaKH = ? AND v.TrangThai = 1 AND v.NgayHetHan >= GETDATE()";
            loaiVIP = ql.net.util.XJdbc.getValue(sqlVIP, maKH);

            if (loaiVIP != null && !loaiVIP.trim().isEmpty()) {
                isVIP = true;
                switch (loaiMay.toLowerCase()) {
                    case "tiêu chuẩn": phanTramGiam = 5.0; break;
                    case "gaming": phanTramGiam = 7.0; break;
                    case "chuyên nghiệp": phanTramGiam = 10.0; break;
                    case "thi đấu": phanTramGiam = 12.0; break;
                    default: phanTramGiam = 0;
                }
            }

            // 7. Tính tổng tiền sau giảm giá
            double tienGiam = (tongTienMay * phanTramGiam) / 100.0;
            double tongTienMaySauGiam = tongTienMay - tienGiam;
            double tongTienCuoi = tongTienMaySauGiam + tongTienDichVu; // CỘNG TIỀN DỊCH VỤ

            // 8. Hiển thị thông tin thanh toán chi tiết
            String thongTinVIP = "";
            if (isVIP) {
                thongTinVIP = String.format("🌟 Giảm giá VIP (%s): -%.0f%% = -%,.0f VNĐ\n", 
                    loaiMay, phanTramGiam, tienGiam);
            }

            String thongTinThanhToan = String.format(
                "=== THÔNG TIN THANH TOÁN ===\n" +
                "🖥️ Máy: %s (%s)\n" +
                "👤 Khách hàng: %s %s\n" +
                "⏰ Thời gian sử dụng: %.1f giờ\n" +
                "💰 Tiền máy gốc: %,.0f VNĐ\n" +
                "%s" +  // Thông tin VIP
                "💵 Tiền máy sau giảm: %,.0f VNĐ\n" +
                "🍔 Tiền dịch vụ: %,.0f VNĐ\n" +
                "================================\n" +
                "🧾 TỔNG CỘNG: %,.0f VNĐ\n" +
                "================================\n" +
                "Xác nhận thanh toán?",
                selectMaMay, loaiMay,
                maKH, (isVIP ? "(VIP " + loaiVIP + ")" : ""),
                gioSuDung, 
                tongTienMay,
                thongTinVIP,
                tongTienMaySauGiam, 
                tongTienDichVu,  // HIỂN THỊ TIỀN DỊCH VỤ ĐÚNG
                tongTienCuoi     // TỔNG TIỀN BAO GỒM CẢ DỊCH VỤ
            );

            int confirm = JOptionPane.showConfirmDialog(this, thongTinThanhToan, "Xác nhận thanh toán", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // 9. Cập nhật database với đầy đủ thông tin
                String updatePhien = "UPDATE PhienSuDung SET ThoiGianKetThuc = GETDATE(), SoGioSuDung = ?, TongTien = ?, TrangThai = N'Đã kết thúc' WHERE MaPhien = ?";
                ql.net.util.XJdbc.executeUpdate(updatePhien, 
                    java.math.BigDecimal.valueOf(gioSuDung), 
                    java.math.BigDecimal.valueOf(tongTienCuoi), // TỔNG TIỀN CUỐI
                    phien.getMaPhien());

                // 10. SỬA: Cập nhật hóa đơn với đầy đủ thông tin
                String updateHD = "UPDATE HoaDon SET TongTienMay = ?, TongTienDichVu = ?, TongTien = ?, TrangThai = N'Đã thanh toán' WHERE MaHD = ?";
                ql.net.util.XJdbc.executeUpdate(updateHD, 
                    java.math.BigDecimal.valueOf(tongTienMaySauGiam),  // Tiền máy sau giảm
                    java.math.BigDecimal.valueOf(tongTienDichVu),      // TIỀN DỊCH VỤ ĐÚNG
                    java.math.BigDecimal.valueOf(tongTienCuoi),        // TỔNG TIỀN ĐÚNG
                    maHD);

                // 11. Cập nhật trạng thái máy
                String updateMay = "UPDATE MayTinh SET TrangThai = N'Trống' WHERE MaMay = ?";
                ql.net.util.XJdbc.executeUpdate(updateMay, selectMaMay);

                // 12. Cập nhật tổng chi tiêu VIP (nếu có)
                if (isVIP) {
                    String updateVIP = "UPDATE ThanhVienVIP SET TongChiTieu = TongChiTieu + ? WHERE MaKH = ? AND TrangThai = 1";
                    ql.net.util.XJdbc.executeUpdate(updateVIP, java.math.BigDecimal.valueOf(tongTienCuoi), maKH);
                }

                // 13. Thêm giao dịch vào lịch sử
                String maGD = generateMaGiaoDich();
                String insertGD = "INSERT INTO LichSuGiaoDich (MaGiaoDich, MaKH, LoaiGiaoDich, SoTien) VALUES (?, ?, N'Thanh toán', ?)";
                ql.net.util.XJdbc.executeUpdate(insertGD, maGD, maKH, java.math.BigDecimal.valueOf(tongTienCuoi));

                // 14. Hiển thị kết quả
                JOptionPane.showMessageDialog(this, 
                    String.format("✅ THANH TOÁN THÀNH CÔNG!\n" +
                        "💰 Tiền máy: %,.0f VNĐ\n" +
                        "🍔 Tiền dịch vụ: %,.0f VNĐ\n" +
                        "🧾 Tổng tiền: %,.0f VNĐ%s", 
                        tongTienMaySauGiam, 
                        tongTienDichVu,
                        tongTienCuoi,
                        (isVIP ? "\n🌟 (Đã áp dụng giảm giá VIP " + String.format("%.0f", phanTramGiam) + "%)" : "")
                    )
                );

                // 15. Refresh giao diện
                refreshTatCaTabMay();
                lamMoiThongTinMay();
                loadHoaDonToTable();
            }

        } catch (Exception e) {
            System.err.println("❌ Lỗi thanh toán: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi thanh toán: " + e.getMessage());
        }
        System.out.println("=== END DEBUG THANH TOÁN ===");
    }
    private void refreshTatCaTabMay() {
        try {
            System.out.println("=== REFRESH TẤT CẢ TAB MÁY ===");

            // Refresh từng tab riêng biệt để giữ cấu trúc phân loại
            loadMayFromSQl("Tiêu chuẩn");
            System.out.println("✓ Đã refresh tab Tiêu chuẩn");

            loadMayFromSQl("Gaming");  
            System.out.println("✓ Đã refresh tab Gaming");

            loadMayFromSQl("Chuyên nghiệp");
            System.out.println("✓ Đã refresh tab Chuyên nghiệp");

            loadMayFromSQl("Thi đấu");
            System.out.println("✓ Đã refresh tab Thi đấu");

            System.out.println("=== END REFRESH TẤT CẢ TAB MÁY ===");

        } catch (Exception e) {
            System.err.println("Lỗi refresh tab máy: " + e.getMessage());
            e.printStackTrace();

            // Fallback: Nếu lỗi thì dùng cách cũ
            System.out.println("⚠️ Fallback: Sử dụng loadMayFromSQl('all')");
            loadMayFromSQl("all");
        }
    }
    // THÊM method này sau method thanhToanMay()
    private String taoHoaDonChoPhienThanhToan(ql.net.entity.PhienSuDung phien) {
        try {
            String maHD = generateMaHoaDon();
            System.out.println("🔥 TẠO HÓA ĐƠN BACKUP cho thanh toán: " + maHD);

            ql.net.entity.HoaDon hoaDon = ql.net.entity.HoaDon.builder()
                .maHD(maHD)
                .maPhien(phien.getMaPhien())
                .maKH(phien.getMaKH())
                .maNV(phien.getMaNV())
                .tongTienMay(java.math.BigDecimal.ZERO)
                .tongTienDichVu(java.math.BigDecimal.ZERO)
                .tongTien(java.math.BigDecimal.ZERO)
                .trangThai("Chờ thanh toán") // Đồng bộ với nút thanh toán
                .build();

            boolean ok = new ql.net.dao.impl.HoaDonDaoImpl().insert(hoaDon);
            if (ok) {
                System.out.println("✅ Tạo hóa đơn backup thành công: " + maHD);
                return maHD;
            } else {
                System.out.println("❌ Tạo hóa đơn backup thất bại");
                return null;
            }

        } catch (Exception e) {
            System.err.println("💥 Lỗi taoHoaDonChoPhienThanhToan: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // Method tạo mã giao dịch tự động
    private String generateMaGiaoDich() {
        try {
            String sql = "SELECT COUNT(*) FROM LichSuGiaoDich";
            Object countObj = ql.net.util.XJdbc.getValue(sql);
            int count = 0;
            if (countObj != null) {
                if (countObj instanceof String) {
                    count = Integer.parseInt((String) countObj);
                } else if (countObj instanceof Number) {
                    count = ((Number) countObj).intValue();
                }
            }
            return String.format("GD%02d", count + 1);
        } catch (Exception e) {
            return "GD01";
        }
    }

    // Method làm mới thông tin máy
    private void lamMoiThongTinMay() {
        selectMaMay = null;
        // Reset các textfield thông tin máy về trống
        try {
            // Tab Tiêu chuẩn
            if (txtTCTrangThai != null) txtTCTrangThai.setText("");
            if (txtTCTDBatDau != null) txtTCTDBatDau.setDate(null);
            if (txtTCTGSuDung != null) txtTCTGSuDung.setText("00:00:00");
            if (txtTCTamTinh != null) txtTCTamTinh.setText("0");

            // Tab Gaming
            if (txtGTrangThai != null) txtGTrangThai.setText("");
            if (txtGTDBatDau != null) txtGTDBatDau.setDate(null);
            if (txtGTGSuDung != null) txtGTGSuDung.setText("00:00:00");
            if (txtGTamTinh != null) txtGTamTinh.setText("0");

            // Tab Chuyên nghiệp
            if (txtCNTrangThai != null) txtCNTrangThai.setText("");
            if (txtCNTDBatDau != null) txtCNTDBatDau.setDate(null);
            if (txtCNTGSuDung != null) txtCNTGSuDung.setText("00:00:00");
            if (txtCNTamTinh != null) txtCNTamTinh.setText("0");

            // Tab Thi đấu
            if (txtTDTrangThai != null) txtTDTrangThai.setText("");
            if (txtTDTDBatDau != null) txtTDTDBatDau.setDate(null);
            if (txtTDTGSuDung != null) txtTDTGSuDung.setText("00:00:00");
            if (txtTDTamTinh != null) txtTDTamTinh.setText("0");

        } catch (Exception e) {
            System.err.println("Lỗi reset thông tin máy: " + e.getMessage());
        }

        // Clear bảng food của tất cả tab
        clearAllFoodTables();
    }

    // Method xóa tất cả bảng food
    private void clearAllFoodTables() {
        if (tblTCBang != null) ((javax.swing.table.DefaultTableModel) tblTCBang.getModel()).setRowCount(0);
        if (tblGBang != null) ((javax.swing.table.DefaultTableModel) tblGBang.getModel()).setRowCount(0);
        if (tblCNBang != null) ((javax.swing.table.DefaultTableModel) tblCNBang.getModel()).setRowCount(0);
        if (tblTDBang != null) ((javax.swing.table.DefaultTableModel) tblTDBang.getModel()).setRowCount(0);
    }




    // Method chung xử lý xóa dịch vụ khỏi bảng food của máy
    private void xoaDichVuKhoiBang(javax.swing.JTable table, String tabName) {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ cần xóa trong bảng " + tabName + "!");
            return;
        }

        if (selectMaMay == null || selectMaMay.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn máy trước!");
            return;
        }

        // Lấy thông tin dịch vụ từ bảng
        String tenDV = table.getValueAt(row, 0).toString();
        String donGiaStr = table.getValueAt(row, 1).toString();
        String soLuongStr = table.getValueAt(row, 2).toString();
        String thanhTienStr = table.getValueAt(row, 3).toString();

        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Bạn có chắc chắn muốn xóa dịch vụ:\n" + 
            tenDV + " (SL: " + soLuongStr + ") khỏi máy " + selectMaMay + "?", 
            "Xác nhận xóa", 
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Tìm mã dịch vụ dựa trên tên
                String maDV = timMaDichVuTheoTen(tenDV);
                if (maDV == null) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy mã dịch vụ!");
                    return;
                }

                // XÓA dịch vụ khỏi chi tiết hóa đơn - SỬA TÊN METHOD
                boolean ok = xoaDichVuKhoiChiTietHoaDon(maDV, selectMaMay);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Đã xóa " + tenDV + " khỏi máy " + selectMaMay + "!");
                    // Refresh lại bảng food của máy
                    loadFoodForMay(selectMaMay);
                    // Refresh tổng tiền máy
                    loadTongHoaDonForMay(selectMaMay);
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa dịch vụ thất bại!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // ĐỔI TÊN METHOD để không trùng
    private boolean xoaDichVuKhoiChiTietHoaDon(String maDV, String maMay) {
        try {
            // 1. Tìm phiên sử dụng hiện tại của máy
            ql.net.entity.PhienSuDung phien = new ql.net.dao.impl.PhienSuDungDaoImpl().selectPhienDangSuDung(maMay);
            if (phien == null) {
                JOptionPane.showMessageDialog(this, "Máy chưa được bật!");
                return false;
            }

            // 2. Tìm hóa đơn của phiên
            String sql = "SELECT MaHD FROM HoaDon WHERE MaPhien = ?";
            String maHD = ql.net.util.XJdbc.getValue(sql, phien.getMaPhien());
            if (maHD == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn!");
                return false;
            }

            // 3. Xóa dịch vụ khỏi chi tiết hóa đơn
            String deleteSql = "DELETE FROM ChiTietHoaDon WHERE MaHD = ? AND MaDV = ?";
            int result = ql.net.util.XJdbc.executeUpdate(deleteSql, maHD, maDV);

            // 4. Cập nhật lại tổng tiền hóa đơn
            if (result > 0) {
                capNhatTongTienHoaDon(maHD);
            }

            return result > 0;

        } catch (Exception e) {
            System.err.println("Lỗi xóa dịch vụ khỏi máy: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Method tìm mã dịch vụ theo tên
    private String timMaDichVuTheoTen(String tenDV) {
        try {
            String sql = "SELECT MaDV FROM DichVu WHERE TenDV = ?";
            return ql.net.util.XJdbc.getValue(sql, tenDV);
        } catch (Exception e) {
            System.err.println("Lỗi tìm mã dịch vụ: " + e.getMessage());
            return null;
        }
    }

    // Method xóa dịch vụ khỏi máy (xóa khỏi chi tiết hóa đơn)
    private void xoaDichVuKhoiMay(javax.swing.JTable table, String tabName) {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ cần xóa trong bảng " + tabName + "!");
            return;
        }

        if (selectMaMay == null || selectMaMay.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn máy trước!");
            return;
        }

        // Lấy thông tin dịch vụ từ bảng
        String tenDV = table.getValueAt(row, 0).toString();
        String donGiaStr = table.getValueAt(row, 1).toString();
        String soLuongStr = table.getValueAt(row, 2).toString();
        String thanhTienStr = table.getValueAt(row, 3).toString();

        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Bạn có chắc chắn muốn xóa dịch vụ:\n" + 
            tenDV + " (SL: " + soLuongStr + ") khỏi máy " + selectMaMay + "?", 
            "Xác nhận xóa", 
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Tìm mã dịch vụ dựa trên tên
                String maDV = timMaDichVuTheoTen(tenDV);
                if (maDV == null) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy mã dịch vụ!");
                    return;
                }

                // Xóa dịch vụ khỏi chi tiết hóa đơn
                boolean ok = xoaDichVuKhoiChiTietHoaDon(maDV, selectMaMay);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Đã xóa " + tenDV + " khỏi máy " + selectMaMay + "!");
                    // Refresh lại bảng food của máy
                    loadFoodForMay(selectMaMay);
                    // Refresh tổng tiền máy - SỬA: Dùng method đúng tên
                    loadTongHoaDonForMay(selectMaMay);
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa dịch vụ thất bại!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // Method cập nhật tổng tiền hóa đơn sau khi xóa dịch vụ
    private void capNhatTongTienHoaDon(String maHD) {
        try {
            String sql = "SELECT SUM(ThanhTien) FROM ChiTietHoaDon WHERE MaHD = ?";
            String tongTienStr = ql.net.util.XJdbc.getValue(sql, maHD);

            java.math.BigDecimal tongTien = java.math.BigDecimal.ZERO;
            if (tongTienStr != null && !tongTienStr.trim().isEmpty()) {
                tongTien = new java.math.BigDecimal(tongTienStr);
            }

            String updateSql = "UPDATE HoaDon SET TongTien = ? WHERE MaHD = ?";
            ql.net.util.XJdbc.executeUpdate(updateSql, tongTien, maHD);

        } catch (Exception e) {
            System.err.println("Lỗi cập nhật tổng tiền hóa đơn: " + e.getMessage());
        }
    }
    
    
    
    
    
    
    
    
    private void lblLogoMouseClicked(java.awt.event.MouseEvent evt) {
        // Chuyển về panel giới thiệu
        cardLayout.show(pnl1, "gioiThieu");
    }
    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {
        // Nếu chưa cần xử lý gì thì để trống
    }
    //đếm mã khách hàng tự động
    private String generteMaKH(){
        java.util.List<ql.net.entity.KhachHang> list = new ql.net.dao.impl.KhachHangDaoImpl().selectAll();
        int max =0;
        for (ql.net.entity.KhachHang kh: list) {
            try {
                int num = Integer.parseInt(kh.getMaKH().replace("KH", ""));
                if(num>max){
                    max = num;
                }
            } catch (Exception e) {
                System.out.println("Lỗi"+e);
            }
        }
        return String.format("KH%02d",max+1);
    }
    // Method làm mới form nhân viên
    private void lamMoiFormNV() {
        txtMaNV.setText("");
        txtTenTaiKhoan.setText("");
        txtHoTenNV.setText("");
        txtSDTNV.setText("");
        txtMatKhauNV.setText("");
        cboChucVuNV.setSelectedIndex(0);
        cboTrangThaiNV.setSelectedIndex(0);
        // Reset ảnh nếu có
        // lblAnhNV.setIcon(null);

        // Clear selection trong bảng
        tblNhanVien.clearSelection();
    }
    // Method tạo mã nhân viên tự động
    private String generateMaNV() {
        java.util.List<ql.net.entity.NhanVien> list = new ql.net.dao.impl.NhanVienDaoImpl().selectAll();
        int max = 0;
        for (ql.net.entity.NhanVien nv : list) {
            try {
                int num = Integer.parseInt(nv.getMaNV().replace("NV", ""));
                if (num > max) {
                    max = num;
                }
            } catch (Exception e) {
                System.out.println("Lỗi parse mã NV: " + e);
            }
        }
        return String.format("NV%02d", max + 1);
    }
    //đếm mã thành viên vip tự động
    private String generateMaVIP() {
        java.util.List<ql.net.entity.ThanhVienVIP> list = new ql.net.dao.impl.ThanhVienVipDaoImpl().selectAll();
        int max = 0;
        for (ql.net.entity.ThanhVienVIP vip : list) {
            try {
                int num = Integer.parseInt(vip.getMaVIP().replace("VIP", ""));
                if (num > max) max = num;
            } catch (Exception e) {}
        }
        return String.format("VIP%02d", max + 1);
    }
    //làm mới khách hàng
    private void lamMoiFormKH() {
        txtMaKH.setText("");
        txtHoTenKH.setText("");
        txtSDTKH.setText("");
        txtSDTKKH.setText("");
        cboTrangThaiKH.setSelectedIndex(0);
        // Nếu có label ảnh thì reset luôn
        // lblAnhKH.setIcon(null);
    }
    //làm mới vip
    private void lamMoiFormVIP() {
        txtMaVIP.setText("");
        txtMaKH_VIP.setText("");
        txtLoaiVip.setText("");
        txtTongChiTieu.setText("");
        txtNgayThamGiaVip.setDate(null);
        txtNgayHetHanVip.setDate(null);
        cboTrangThaiVip.setSelectedIndex(0);
    }
    //load dữ liệu hóa đơn
    private void loadHoaDonToTable() {
        List<ql.net.entity.HoaDon> danhSach = new ql.net.dao.impl.HoaDonDaoImpl().selectAll();
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        for (ql.net.entity.HoaDon hd : danhSach) {
            model.addRow(new Object[]{
                hd.getMaHD(),
                hd.getMaPhien(),
                hd.getMaNV(),
                hd.getMaKH(),
                hd.getNgayTao(),
                hd.getTongTienMay(),
                hd.getTongTienDichVu(),
                hd.getTongTien(),
                hd.getTrangThai()
            });
        }
    }
    //tải dữ liệu chi tiết hóa đơn
    private void loadChiTietHoaDonToTable(String maHD) {
        List<ql.net.entity.ChiTietHoaDon> dsCT = new ql.net.dao.impl.ChiTietHoaDonDaoImpl().selectByMaHD(maHD);
        DefaultTableModel model = (DefaultTableModel) tblhoaDonChiTiet.getModel();
        model.setRowCount(0);
        for (ql.net.entity.ChiTietHoaDon ct : dsCT) {
            model.addRow(new Object[]{
                ct.getMaHD(),
                ct.getMaDV(),
                ct.getSoLuong(),
                ct.getDonGia(),
                ct.getThanhTien()
            });
        }
    }
    
    // THÊM METHOD mới để load tất cả máy vào tab bảo trì
    private void loadMayBaoTri() {
        try {
            // Xóa tất cả máy cũ trong panel bảo trì
            pnlGridMayBaoTri.removeAll(); // Giả sử panel bảo trì tên là pnlBaoTri

            // Set layout cho panel bảo trì (6 cột)
            pnlGridMayBaoTri.setLayout(new java.awt.GridLayout(0, 6, 10, 10)); // 6 cột, khoảng cách 10px

            // Lấy TẤT CẢ máy từ database
            java.util.List<ql.net.entity.MayTinh> danhSachTatCaMay = mayTinhDao.selectAll();

            System.out.println("Đang load " + danhSachTatCaMay.size() + " máy vào tab bảo trì");

            // Tạo panel cho từng máy
            for (ql.net.entity.MayTinh may : danhSachTatCaMay) {
                JPanel pnlMay = createPanelMayBaoTri(may);
                pnlGridMayBaoTri.add(pnlMay);
            }

            // Cập nhật giao diện
            pnlGridMayBaoTri.revalidate();
            pnlGridMayBaoTri.repaint();

            System.out.println("Đã load xong " + danhSachTatCaMay.size() + " máy vào tab bảo trì");

        } catch (Exception e) {
            System.err.println("Lỗi loadMayBaoTri: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // THÊM METHOD tạo panel máy cho tab bảo trì
    private JPanel createPanelMayBaoTri(ql.net.entity.MayTinh may) {
        JPanel pnlMay = new JPanel();
        pnlMay.setLayout(new java.awt.BorderLayout());
        pnlMay.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.GRAY, 2));
        pnlMay.setPreferredSize(new java.awt.Dimension(150, 120));

        // Xác định màu nền theo trạng thái
        java.awt.Color bgColor;
        switch (may.getTrangThai().toLowerCase()) {
            case "đang sử dụng":
                bgColor = new java.awt.Color(173, 216, 230); // Xanh nhạt
                break;
            case "bảo trì":
                bgColor = java.awt.Color.RED; // Đỏ
                break;
            case "trống":
            default:
                bgColor = new java.awt.Color(192, 192, 192); // Xám
        }
        pnlMay.setBackground(bgColor);

        // Label tên máy
        JLabel lblTenMay = new JLabel(may.getTenMay(), JLabel.CENTER);
        lblTenMay.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        lblTenMay.setForeground(java.awt.Color.BLACK);

        // Label trạng thái
        JLabel lblTrangThai = new JLabel(may.getTrangThai(), JLabel.CENTER);
        lblTrangThai.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        lblTrangThai.setForeground(java.awt.Color.BLACK);

        // Panel chứa thông tin
        JPanel pnlThongTin = new JPanel(new java.awt.GridLayout(2, 1));
        pnlThongTin.setOpaque(false);
        pnlThongTin.add(lblTenMay);
        pnlThongTin.add(lblTrangThai);

        pnlMay.add(pnlThongTin, java.awt.BorderLayout.CENTER);

        // SỬA: Thêm sự kiện click giống như tab đặt máy
        pnlMay.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Gọi method hiển thị thông tin máy bảo trì giống như chonMay()
                chonMayBaoTri(may.getMaMay(), may.getTenMay(), may.getTrangThai());
            }
        });

        return pnlMay;
    }

    // THÊM METHOD xử lý click máy bảo trì - giống như chonMay()
    private void chonMayBaoTri(String maMay, String tenMay, String trangThai) {
        try {
            System.out.println("✓ Click chọn máy bảo trì: " + tenMay + " - " + trangThai);

            selectMaMay = maMay;

            // 1. Cập nhật title panel (hoạt động tốt)
            timVaCapNhatTatCaTitleBorder(this, tenMay);

            // 2. TRỌNG TÂM: TRUY CẬP TRỰC TIẾP txtBTTrangThai
            try {
                // Lấy trạng thái máy mới nhất từ DB
                ql.net.entity.MayTinh may = new ql.net.dao.impl.MayTinhDaoImpl().selectById(maMay);
                String trangThaiMoi = may != null ? may.getTrangThai() : trangThai;

                // TRUY CẬP TRỰC TIẾP txtBTTrangThai (thay thế this.txtBTTrangThai)
                txtBTTrangThai.setText(trangThaiMoi);

                // Đổi màu background theo trạng thái
                if ("Đang sử dụng".equalsIgnoreCase(trangThaiMoi)) {
                    txtBTTrangThai.setBackground(new java.awt.Color(173,216,230)); // Xanh nhạt
                } else if ("Trống".equalsIgnoreCase(trangThaiMoi)) {
                    txtBTTrangThai.setBackground(java.awt.Color.WHITE); // Trắng
                } else if ("Bảo trì".equalsIgnoreCase(trangThaiMoi)) {
                    txtBTTrangThai.setBackground(java.awt.Color.RED); // Đỏ
                }

                txtBTTrangThai.revalidate();
                txtBTTrangThai.repaint();

                System.out.println("✅ THÀNH CÔNG: Đã cập nhật txtBTTrangThai: " + trangThaiMoi);

            } catch (Exception e) {
                System.out.println("❌ LỖI khi truy cập txtBTTrangThai: " + e.getMessage());
                e.printStackTrace();

                // Nếu lỗi, thử tìm bằng cách khác
                javax.swing.JTextField txtFound = timTextFieldBTTrangThai();
                if (txtFound != null) {
                    txtFound.setText(trangThai);
                    System.out.println("✅ THÀNH CÔNG: Đã cập nhật qua tìm kiếm: " + trangThai);
                } else {
                    System.out.println("❌ THẤT BẠI: Không tìm thấy txtBTTrangThai bằng mọi cách!");
                }
            }
            loadDanhSachBaoTriToTable();

        } catch (Exception e) {
            System.err.println("Lỗi chọn máy bảo trì: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private javax.swing.JTextField timTextFieldBTTrangThai() {
        // Cách 1: Thử truy cập qua reflection với tên chính xác
        try {
            java.lang.reflect.Field field = this.getClass().getDeclaredField("txtBTTrangThai");
            field.setAccessible(true);
            Object fieldValue = field.get(this);
            if (fieldValue instanceof javax.swing.JTextField) {
                System.out.println("✓ Tìm thấy txtBTTrangThai qua reflection");
                return (javax.swing.JTextField) fieldValue;
            }
        } catch (Exception e) {
            System.out.println("Không tìm được txtBTTrangThai qua reflection: " + e.getMessage());
        }

        // Cách 2: Tìm theo component name
        javax.swing.JTextField found = timTextFieldTheoComponentName("txtBTTrangThai");
        if (found != null) {
            System.out.println("✓ Tìm thấy txtBTTrangThai qua component name");
            return found;
        }

        // Cách 3: Tìm tất cả textfield và chọn cái có vị trí phù hợp
        java.util.List<javax.swing.JTextField> allTextFields = new java.util.ArrayList<>();
        collectAllTextFields(this.getContentPane(), allTextFields);

        for (javax.swing.JTextField tf : allTextFields) {
            java.awt.Point location = getComponentAbsoluteLocation(tf);

            // txtBTTrangThai thường ở vị trí góc phải trên (x > 900, y < 150)
            if (location.x > 900 && location.y < 150 && 
                tf.isVisible() && tf.isEditable()) {

                System.out.println("✓ Tìm thấy txtBTTrangThai theo vị trí: x=" + location.x + ", y=" + location.y);
                return tf;
            }
        }

        return null;
    }

    // THÊM METHOD tìm textfield theo component name
    private javax.swing.JTextField timTextFieldTheoComponentName(String componentName) {
        return timComponentTheoName(this.getContentPane(), componentName);
    }

    private javax.swing.JTextField timComponentTheoName(java.awt.Container container, String name) {
        java.awt.Component[] components = container.getComponents();

        for (java.awt.Component comp : components) {
            if (comp instanceof javax.swing.JTextField && name.equals(comp.getName())) {
                return (javax.swing.JTextField) comp;
            }

            if (comp instanceof java.awt.Container) {
                javax.swing.JTextField found = timComponentTheoName((java.awt.Container) comp, name);
                if (found != null) return found;
            }
        }

        return null;
    }
    private void collectAllTextFields(java.awt.Container container, java.util.List<javax.swing.JTextField> list) {
        java.awt.Component[] components = container.getComponents();

        for (java.awt.Component comp : components) {
            if (comp instanceof javax.swing.JTextField) {
                list.add((javax.swing.JTextField) comp);
            }

            if (comp instanceof java.awt.Container) {
                collectAllTextFields((java.awt.Container) comp, list);
            }
        }
    }

    // THÊM METHOD lấy vị trí tuyệt đối của component
    private java.awt.Point getComponentAbsoluteLocation(java.awt.Component comp) {
        java.awt.Point location = new java.awt.Point(0, 0);
        while (comp != null && comp.getParent() != null) {
            location.x += comp.getX();
            location.y += comp.getY();
            comp = comp.getParent();
        }
        return location;
    }

    // THÊM METHOD debug chi tiết
    private void inTatCaTextFieldDeXacDinh(java.awt.Container container) {
        inTextFieldDeXacDinh(container, 0);
    }

    private void inTextFieldDeXacDinh(java.awt.Container container, int level) {
        String indent = "  ".repeat(level);
        java.awt.Component[] components = container.getComponents();

        for (java.awt.Component comp : components) {
            if (comp instanceof javax.swing.JTextField) {
                javax.swing.JTextField tf = (javax.swing.JTextField) comp;
                System.out.println(indent + "📝 TextField:");
                System.out.println(indent + "   - Text: '" + tf.getText() + "'");
                System.out.println(indent + "   - Bounds: " + tf.getBounds());
                System.out.println(indent + "   - Visible: " + tf.isVisible());
                System.out.println(indent + "   - Editable: " + tf.isEditable());
                System.out.println(indent + "   - Parent: " + tf.getParent().getClass().getSimpleName());
            }

            if (comp instanceof java.awt.Container) {
                inTextFieldDeXacDinh((java.awt.Container) comp, level + 1);
            }
        }
    }

    private void inTextFieldChiTiet(java.awt.Container container, int level) {
        String indent = "  ".repeat(level);
        java.awt.Component[] components = container.getComponents();

        for (java.awt.Component comp : components) {
            if (comp instanceof javax.swing.JTextField) {
                javax.swing.JTextField tf = (javax.swing.JTextField) comp;
                System.out.println(indent + "📝 TextField:");
                System.out.println(indent + "   - Name: '" + tf.getName() + "'");
                System.out.println(indent + "   - Text: '" + tf.getText() + "'");
                System.out.println(indent + "   - Bounds: " + tf.getBounds());
                System.out.println(indent + "   - Visible: " + tf.isVisible());
                System.out.println(indent + "   - Editable: " + tf.isEditable());
                System.out.println(indent + "   - Background: " + tf.getBackground());
            }

            if (comp instanceof java.awt.Container) {
                inTextFieldChiTiet((java.awt.Container) comp, level + 1);
            }
        }
    }
    // THÊM METHOD tìm textfield trạng thái theo vị trí (backup plan)
//    private javax.swing.JTextField timTextFieldTrangThaiChinhXac() {
//        // Tìm textfield trong tab bảo trì có vị trí phù hợp (gần góc phải trên)
//        return timTextFieldTheoViTri(this.getContentPane());
//    }
    // METHOD tìm và cập nhật TẤT CẢ TitledBorder
    private void timVaCapNhatTatCaTitleBorder(java.awt.Container container, String titleMoi) {
        java.awt.Component[] components = container.getComponents();

        for (java.awt.Component comp : components) {
            if (comp instanceof javax.swing.JPanel) {
                javax.swing.JPanel panel = (javax.swing.JPanel) comp;

                if (panel.getBorder() instanceof javax.swing.border.TitledBorder) {
                    javax.swing.border.TitledBorder border = (javax.swing.border.TitledBorder) panel.getBorder();
                    String currentTitle = border.getTitle();

                    System.out.println("Tìm thấy TitledBorder: '" + currentTitle + "'");

                    // Cập nhật BẤT KỲ TitledBorder nào có chữ "Máy"
                    if (currentTitle != null && currentTitle.contains("Máy")) {
                        panel.setBorder(
                            javax.swing.BorderFactory.createTitledBorder(
                                null, titleMoi,
                                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14),
                                java.awt.Color.WHITE
                            )
                        );

                        panel.revalidate();
                        panel.repaint();

                        System.out.println("✓ Cập nhật: '" + currentTitle + "' → '" + titleMoi + "'");
                    }
                }
            }

            // Đệ quy tìm trong container con
            if (comp instanceof java.awt.Container) {
                timVaCapNhatTatCaTitleBorder((java.awt.Container) comp, titleMoi);
            }
        }
    }
    
    
    


    
    //Load dữ liệu lịch sử giao dịch
    void loadLichSuGiaoDichToTable() {
        try{
            String sql = "SELECT MaGiaoDich, MaKH, LoaiGiaoDich, SoTien, NgayGiaoDich FROM LichSuGiaoDich ORDER BY NgayGiaoDich DESC";
            ResultSet rs = ql.net.util.XJdbc.executeQuery(sql);
            DefaultTableModel model = (DefaultTableModel) tblLichSuGiaoDich.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String loaiGD = rs.getString("LoaiGiaoDich");
                double soTien = rs.getDouble("SoTien");
                // Nếu là thanh toán hoặc sử dụng máy + DV thì để số âm
                if (loaiGD != null && (loaiGD.equalsIgnoreCase("Thanh toán") || loaiGD.toLowerCase().contains("dịch vụ") || loaiGD.toLowerCase().contains("sử dụng"))) {
                    soTien = -Math.abs(soTien);
                }
                model.addRow(new Object[]{
                    rs.getString("MaGiaoDich"),
                    rs.getString("MaKH"),
                    loaiGD,
                    String.format("%.2f", soTien),
                    rs.getString("NgayGiaoDich")
                });
            }
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    // Đổ tất cả máy có trạng thái "Đặt máy" lên panel bảo trì
    
    //load nhân viên
    private void loadNhanVienToTable(){
        java.util.List<ql.net.entity.NhanVien> danhSach = new ql.net.dao.impl.NhanVienDaoImpl().selectAll();
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
        for (ql.net.entity.NhanVien nv : danhSach) {
            model.addRow(new Object[]{
                nv.getMaNV(),
                nv.getHoTen(),
                nv.getSoDT(),
                nv.getTenDangNhap(),
                nv.getMatKhau(),
                nv.getChucVu(),
                nv.isTrangThai() ? "Hoạt động" : "Ngừng",
                nv.getNgayTao(),
                nv.getImage()
            });
        }
    }
    //làm mới bên tab dịch vụ
    private void lamMoiFormDichVu(
        JTextField txtMaDV,
        JTextField txtTenDV,
        JComboBox<String> cboLoaiDV,
        JTextField txtDonGia,
        JTextField txtSoLuongTon,
        JComboBox<String> cboTrangThai,
        JLabel lblAnh
    ) {
        txtMaDV.setText(""); // hoặc txtMaDV.setText(generateMaDV());
        txtTenDV.setText("");
        cboLoaiDV.setSelectedIndex(0);
        txtDonGia.setText("");
        txtSoLuongTon.setText("");
        cboTrangThai.setSelectedIndex(0);
        lblAnh.setText(""); // hoặc lblAnh.setIcon(null);
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HomeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(HomeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        // Hiện màn hình chào
        ql.net.ui.WelcomJDialog welcom = new ql.net.ui.WelcomJDialog(null, true);
        welcom.setVisible(true);

        // Hiện màn hình đăng nhập
        ql.net.ui.LoginJDialog login = new ql.net.ui.LoginJDialog(null, true);
        login.setVisible(true);

        // Nếu đăng nhập thành công thì vào HomeJFrame
        if (login.isLoginSuccess()) {
            ql.net.entity.NhanVien nv = login.getLoggedNhanVien();
            HomeJFrame home = new HomeJFrame(nv);
            home.setVisible(true);
        } else {
            System.exit(0); // Thoát nếu đăng nhập thất bại hoặc bị đóng
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBTThanhToan;
    private javax.swing.JButton btnBaoCao;
    private javax.swing.JButton btnBaoTri;
    private javax.swing.JButton btnBatMay;
    private javax.swing.JButton btnBatMay1;
    private javax.swing.JButton btnBatMay2;
    private javax.swing.JButton btnCNDichVu;
    private javax.swing.JButton btnCNThanhToan;
    private javax.swing.JButton btnCNXoa;
    private javax.swing.JButton btnCaiDat;
    private javax.swing.JButton btnCapNhatDA;
    private javax.swing.JButton btnCapNhatDU;
    private javax.swing.JButton btnCapNhatDVK;
    private javax.swing.JButton btnCapNhatKH;
    private javax.swing.JButton btnCapNhatNV;
    private javax.swing.JButton btnCapNhatNhapHang;
    private javax.swing.JButton btnCapNhatVIP;
    private javax.swing.JButton btnChuyenNghiep;
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JButton btnDoiMatKhau;
    private javax.swing.JButton btnGDichVu;
    private javax.swing.JButton btnGXoa;
    private javax.swing.JButton btnGaming;
    private javax.swing.JButton btnInHoaDon;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLamMoiDA;
    private javax.swing.JButton btnLamMoiDU;
    private javax.swing.JButton btnLamMoiDVk;
    private javax.swing.JButton btnLamMoiKH;
    private javax.swing.JButton btnLamMoiNV;
    private javax.swing.JButton btnLamMoiNhapHang;
    private javax.swing.JButton btnLamMoiVIP;
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnNapTienKH;
    private javax.swing.JButton btnNutBaoTri;
    private javax.swing.JButton btnQuanLyDichVu;
    private javax.swing.JButton btnQuanLyHoaDon;
    private javax.swing.JButton btnQuanLyKhach;
    private javax.swing.JButton btnQuanLyMay;
    private javax.swing.JButton btnQuanLyNhanVien;
    private javax.swing.JButton btnTCDichVu;
    private javax.swing.JButton btnTCXoa;
    private javax.swing.JButton btnTDBatMay;
    private javax.swing.JButton btnTDDichVu;
    private javax.swing.JButton btnTDThanhToan;
    private javax.swing.JButton btnTDXoa;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThanhToan1;
    private javax.swing.JButton btnThemDA;
    private javax.swing.JButton btnThemDU;
    private javax.swing.JButton btnThemDVK;
    private javax.swing.JButton btnThemKH;
    private javax.swing.JButton btnThemNV;
    private javax.swing.JButton btnThemNhapHang;
    private javax.swing.JButton btnThemVIP;
    private javax.swing.JButton btnThiDau;
    private javax.swing.JButton btnThongTinHeThong;
    private javax.swing.JButton btnTieuChuan;
    private javax.swing.JButton btnXoaDA;
    private javax.swing.JButton btnXoaDU;
    private javax.swing.JButton btnXoaDVk;
    private javax.swing.JButton btnXoaKH;
    private javax.swing.JButton btnXoaNV;
    private javax.swing.JButton btnXoaNhapHang;
    private javax.swing.JButton btnXoaVIP;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cboBaoCao;
    private javax.swing.JComboBox<String> cboBoPhanBT;
    private javax.swing.JComboBox<String> cboChucVuNV;
    private javax.swing.JComboBox<String> cboLoaiDVDA;
    private javax.swing.JComboBox<String> cboLoaiDVDU;
    private javax.swing.JComboBox<String> cboLoaiDVK;
    private javax.swing.JComboBox<String> cboTrangThaiDA;
    private javax.swing.JComboBox<String> cboTrangThaiDU;
    private javax.swing.JComboBox<String> cboTrangThaiDVK;
    private javax.swing.JComboBox<String> cboTrangThaiKH;
    private javax.swing.JComboBox<String> cboTrangThaiNV;
    private javax.swing.JComboBox<String> cboTrangThaiVip;
    private com.toedter.calendar.JDateChooser dateDenNgay;
    private com.toedter.calendar.JDateChooser dateTuNgay;
    private com.toedter.calendar.JDateChooser dchNgayTaoNV;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JLabel lblAnhDichVu;
    private javax.swing.JLabel lblAnhDoAn;
    private javax.swing.JLabel lblAnhDoUong;
    private javax.swing.JLabel lblAnhKH;
    private javax.swing.JLabel lblAnhNV;
    private javax.swing.JLabel lblChaoMung;
    private javax.swing.JLabel lblDoanhThu;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblNhanVien;
    private javax.swing.JLabel lblSoDichVu;
    private javax.swing.JLabel lblSoGiaoDich;
    private javax.swing.JLabel lblSoLanBaoTri;
    private javax.swing.JLabel lblSoLanNap;
    private javax.swing.JLabel lblSoMay;
    private javax.swing.JLabel lblSoNhanVien;
    private javax.swing.JLabel lblSoVIP;
    private javax.swing.JPanel pnl1;
    private javax.swing.JPanel pnlCNFood;
    private javax.swing.JPanel pnlCaiDat;
    private javax.swing.JPanel pnlCauHinh;
    private javax.swing.JPanel pnlCauHinhChuyenNghiep;
    private javax.swing.JPanel pnlCauHinhGaming;
    private javax.swing.JPanel pnlCauHinhThiDau;
    private javax.swing.JPanel pnlChuyenNghiep;
    private javax.swing.JPanel pnlDSBaoTri;
    private javax.swing.JPanel pnlGFood;
    private javax.swing.JPanel pnlGaming;
    private javax.swing.JPanel pnlGioiThieu;
    private javax.swing.JPanel pnlGridMay;
    private javax.swing.JPanel pnlGridMay1;
    private javax.swing.JPanel pnlGridMay2;
    private javax.swing.JPanel pnlGridMay3;
    private javax.swing.JPanel pnlGridMayBaoTri;
    private javax.swing.JPanel pnlHeaderNhanVien;
    private javax.swing.JPanel pnlMainChuyenNghiep;
    private javax.swing.JPanel pnlMainContainer;
    private javax.swing.JPanel pnlMainGaming;
    private javax.swing.JPanel pnlMainThiDau;
    private javax.swing.JPanel pnlMainTieuChuan;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlQuanLyBaoCao;
    private javax.swing.JPanel pnlQuanLyBaoTri;
    private javax.swing.JPanel pnlQuanLyDichVu;
    private javax.swing.JPanel pnlQuanLyHoaDon;
    private javax.swing.JPanel pnlQuanLyKhachHang;
    private javax.swing.JPanel pnlQuanLyMay;
    private javax.swing.JPanel pnlQuanLyNhanVien;
    private javax.swing.JPanel pnlSidebarChuyenNghiep;
    private javax.swing.JPanel pnlSidebarGaming;
    private javax.swing.JPanel pnlSidebarThiDau;
    private javax.swing.JPanel pnlSidebarTieuChuan;
    private javax.swing.JPanel pnlSildebar;
    private javax.swing.JPanel pnlTCFood;
    private javax.swing.JPanel pnlTDFood;
    private javax.swing.JPanel pnlTabContent;
    private javax.swing.JPanel pnlTabsBar;
    private javax.swing.JPanel pnlThiDau;
    private javax.swing.JPanel pnlThongTin1;
    private javax.swing.JPanel pnlThongTin2;
    private javax.swing.JPanel pnlThongTin3;
    private javax.swing.JPanel pnlThongTin4;
    private javax.swing.JPanel pnlTieuChuan;
    private javax.swing.JScrollPane scrCN;
    private javax.swing.JScrollPane scrG;
    private javax.swing.JScrollPane scrTC;
    private javax.swing.JScrollPane scrTD;
    private javax.swing.JTabbedPane tabDichVu;
    private javax.swing.JTable tblBangKhachHang;
    private javax.swing.JTable tblBangKhachVIP;
    private javax.swing.JTable tblBaoCao;
    private javax.swing.JTable tblBaoTri;
    private javax.swing.JTable tblCNBang;
    private javax.swing.JTable tblDichVuKhac;
    private javax.swing.JTable tblDoAn;
    private javax.swing.JTable tblDoUong;
    private javax.swing.JTable tblGBang;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblLichSuGiaoDich;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTable tblNhapHang;
    private javax.swing.JTable tblTCBang;
    private javax.swing.JTable tblTDBang;
    private javax.swing.JTable tblhoaDonChiTiet;
    private javax.swing.JTextField txtBTTongChiPhi;
    private javax.swing.JTextField txtBTTrangThai;
    private javax.swing.JTextField txtCNFood;
    private javax.swing.JTextField txtCNHoaDon;
    private com.toedter.calendar.JDateChooser txtCNTDBatDau;
    private javax.swing.JTextField txtCNTGSuDung;
    private javax.swing.JTextField txtCNTamTinh;
    private javax.swing.JTextField txtCNTrangThai;
    private javax.swing.JTextField txtDonGiaDA;
    private javax.swing.JTextField txtDonGiaDU;
    private javax.swing.JTextField txtDonGiaDVK;
    private javax.swing.JTextField txtDonGiaNhapHang;
    private javax.swing.JTextField txtGFood;
    private javax.swing.JTextField txtGHoaDon;
    private com.toedter.calendar.JDateChooser txtGTDBatDau;
    private javax.swing.JTextField txtGTGSuDung;
    private javax.swing.JTextField txtGTamTinh;
    private javax.swing.JTextField txtGTrangThai;
    private javax.swing.JTextField txtHoTenKH;
    private javax.swing.JTextField txtHoTenNV;
    private javax.swing.JTextField txtLoaiVip;
    private javax.swing.JTextField txtMaDVDA;
    private javax.swing.JTextField txtMaDVDU;
    private javax.swing.JTextField txtMaDVK;
    private javax.swing.JTextField txtMaDVNhapHang;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtMaKH_VIP;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtMaNVNhapHang;
    private javax.swing.JTextField txtMaNhapHang;
    private javax.swing.JTextField txtMaVIP;
    private javax.swing.JPasswordField txtMatKhauNV;
    private javax.swing.JTextField txtMoTaBaoTri;
    private com.toedter.calendar.JDateChooser txtNgayHetHanVip;
    private com.toedter.calendar.JDateChooser txtNgayNhapHang;
    private com.toedter.calendar.JDateChooser txtNgayThamGiaVip;
    private javax.swing.JTextField txtSDTKH;
    private javax.swing.JTextField txtSDTKKH;
    private javax.swing.JTextField txtSDTNV;
    private javax.swing.JTextField txtSoLuongDVK;
    private javax.swing.JTextField txtSoLuongNhapHang;
    private javax.swing.JTextField txtSoLuongTonDA;
    private javax.swing.JTextField txtSoLuongTonDU;
    private javax.swing.JTextField txtTCFood;
    private javax.swing.JTextField txtTCHoaDon;
    private com.toedter.calendar.JDateChooser txtTCTDBatDau;
    private javax.swing.JTextField txtTCTGSuDung;
    private javax.swing.JTextField txtTCTamTinh;
    private javax.swing.JTextField txtTCTrangThai;
    private javax.swing.JTextField txtTDFood;
    private javax.swing.JTextField txtTDHoaDon;
    private com.toedter.calendar.JDateChooser txtTDTDBatDau;
    private javax.swing.JTextField txtTDTGSuDung;
    private javax.swing.JTextField txtTDTamTinh;
    private javax.swing.JTextField txtTDTrangThai;
    private javax.swing.JTextField txtTenDVDA;
    private javax.swing.JTextField txtTenDVDU;
    private javax.swing.JTextField txtTenDVK;
    private javax.swing.JTextField txtTenNhaCungCap;
    private javax.swing.JTextField txtTenTaiKhoan;
    private javax.swing.JTextField txtThanhTienNhapHang;
    private javax.swing.JTextField txtTongChiTieu;
    // End of variables declaration//GEN-END:variables

    private void setActiveButton(javax.swing.JButton activeButton) {
        // Đổi màu nút btn menu
        java.awt.Color normalColor = new java.awt.Color(80, 200, 120);
        java.awt.Color activeColor = new java.awt.Color(34, 139, 34);

        btnQuanLyMay.setBackground(normalColor);
        btnQuanLyKhach.setBackground(normalColor);
        btnQuanLyDichVu.setBackground(normalColor);
        btnQuanLyHoaDon.setBackground(normalColor);
        btnQuanLyNhanVien.setBackground(normalColor);
        btnCaiDat.setBackground(normalColor);
        btnBaoTri.setBackground(normalColor);
        btnBaoCao.setBackground(normalColor);
        
        activeButton.setBackground(activeColor);
    }

    
}
