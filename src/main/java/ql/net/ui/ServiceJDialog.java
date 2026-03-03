/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package ql.net.ui;

import javax.swing.JOptionPane;

/**
 *
 * @author ADMIN
 */
public class ServiceJDialog extends javax.swing.JDialog {
    private String maMayDangChon; // Thêm biến lưu mã máy

    /**
     * Creates new form ServiceJDialog
     */
    public ServiceJDialog(java.awt.Frame parent, boolean modal, String maMay) {
        super(parent, modal);
        initComponents();
        this.maMayDangChon = maMay;
        setLocationRelativeTo(null);//in ra giữa màn hình
        loadDoAnToTable();
        loadDoUongToTable();
        loadDichVuKhac();
    }
    
    // Constructor cũ để tương thích
    public ServiceJDialog(java.awt.Frame parent, boolean modal) {
        this(parent, modal, null);
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
    private void chonDichVu(String maDV, String tenDV, java.math.BigDecimal donGia) {
        if (maMayDangChon == null) {
            JOptionPane.showMessageDialog(this, "Không xác định được máy!");
            return;
        }
        
        // Hiển thị dialog nhập số lượng
        String soLuongStr = JOptionPane.showInputDialog(this, 
            "Nhập số lượng cho " + tenDV + ":", 
            "Nhập số lượng", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (soLuongStr == null || soLuongStr.trim().isEmpty()) {
            return; // Hủy bỏ
        }
        
        try {
            int soLuong = Integer.parseInt(soLuongStr.trim());
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
                return;
            }
            
            // Tính thành tiền
            java.math.BigDecimal thanhTien = donGia.multiply(new java.math.BigDecimal(soLuong));
            
            // Thêm vào database
            boolean ok = themDichVuVaoMay(maDV, maMayDangChon, soLuong, thanhTien);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Đã thêm " + tenDV + " vào máy " + maMayDangChon);
                dispose(); // Đóng dialog
            } else {
                JOptionPane.showMessageDialog(this, "Thêm dịch vụ thất bại!");
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên!");
        }
    }

    private boolean themDichVuVaoMay(String maDV, String maMay, int soLuong, java.math.BigDecimal thanhTien) {
        try {
            System.out.println("=== DEBUG themDichVuVaoMay ===");
            System.out.println("MaDV: " + maDV + ", MaMay: " + maMay + ", SoLuong: " + soLuong);
            System.out.println("ThanhTien: " + thanhTien);

            // 1. Lấy phiên sử dụng đang hoạt động của máy
            ql.net.entity.PhienSuDung phien = new ql.net.dao.impl.PhienSuDungDaoImpl().selectPhienDangSuDung(maMay);
            if (phien == null) {
                System.out.println("KHÔNG TÌM THẤY PHIÊN SỬ DỤNG cho máy: " + maMay);
                javax.swing.JOptionPane.showMessageDialog(this, 
                    "Máy " + maMay + " chưa được bật!\n" +
                    "Vui lòng:\n" +
                    "1. Chọn máy " + maMay + "\n" +
                    "2. Ấn nút 'Bật máy'\n" +
                    "3. Chọn khách hàng\n" +
                    "4. Sau đó mới gọi dịch vụ");
                return false;
            }
            System.out.println("✓ Tìm thấy phiên: " + phien.getMaPhien() + " của khách hàng: " + phien.getMaKH());

            // 2. Kiểm tra xem đã có hóa đơn cho phiên này chưa
            String maHD = kiemTraHoaDonChoPhien(phien.getMaPhien());
            System.out.println("Hóa đơn hiện tại: " + (maHD != null ? maHD : "CHƯA CÓ"));

            // 3. Nếu chưa có hóa đơn thì tạo mới
            if (maHD == null) {
                System.out.println("Tạo hóa đơn mới cho phiên: " + phien.getMaPhien());
                maHD = taoHoaDonChoPhien(phien);
                if (maHD == null) {
                    System.out.println("❌ Tạo hóa đơn thất bại!");
                    javax.swing.JOptionPane.showMessageDialog(this, "Không thể tạo hóa đơn!");
                    return false;
                }
                System.out.println("✓ Tạo hóa đơn mới thành công: " + maHD);
            } else {
                System.out.println("✓ Sử dụng hóa đơn có sẵn: " + maHD);
            }

            // 4. Kiểm tra dịch vụ đã tồn tại trong hóa đơn chưa
            ql.net.entity.ChiTietHoaDon chiTietCu = kiemTraChiTietDaCoTrong(maHD, maDV);

            boolean result = false;
            if (chiTietCu != null) {
                // 5. Nếu đã có thì cập nhật số lượng
                int soLuongCu = chiTietCu.getSoLuong();
                int soLuongMoi = soLuongCu + soLuong;
                java.math.BigDecimal donGia = chiTietCu.getDonGia();
                java.math.BigDecimal thanhTienMoi = donGia.multiply(new java.math.BigDecimal(soLuongMoi));

                System.out.println("Dịch vụ đã tồn tại - Cập nhật số lượng từ " + soLuongCu + " thành " + soLuongMoi);

                ql.net.entity.ChiTietHoaDon chiTietCapNhat = ql.net.entity.ChiTietHoaDon.builder()
                    .maHD(maHD)
                    .maDV(maDV)
                    .soLuong(soLuongMoi)
                    .donGia(donGia)
                    .thanhTien(thanhTienMoi)
                    .build();

                result = new ql.net.dao.impl.ChiTietHoaDonDaoImpl().update(chiTietCapNhat);
                System.out.println("Kết quả cập nhật: " + (result ? "THÀNH CÔNG" : "THẤT BẠI"));
            } else {
                // 6. Nếu chưa có thì thêm mới
                java.math.BigDecimal donGia = thanhTien.divide(new java.math.BigDecimal(soLuong));
                System.out.println("Thêm mới dịch vụ - Đơn giá: " + donGia + ", Số lượng: " + soLuong);

                ql.net.entity.ChiTietHoaDon chiTiet = ql.net.entity.ChiTietHoaDon.builder()
                    .maHD(maHD)
                    .maDV(maDV)
                    .soLuong(soLuong)
                    .donGia(donGia)
                    .thanhTien(thanhTien)
                    .build();

                result = new ql.net.dao.impl.ChiTietHoaDonDaoImpl().insert(chiTiet);
                System.out.println("Kết quả thêm mới: " + (result ? "THÀNH CÔNG" : "THẤT BẠI"));
            }

            System.out.println("=== Kết quả cuối cùng: " + (result ? "THÀNH CÔNG" : "THẤT BẠI") + " ===");
            System.out.println("=== END DEBUG themDichVuVaoMay ===");

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi thêm dịch vụ: " + e.getMessage());
            return false;
        }
    }

    // Method kiểm tra chi tiết hóa đơn đã tồn tại
    private ql.net.entity.ChiTietHoaDon kiemTraChiTietDaCoTrong(String maHD, String maDV) {
        try {
            String sql = "SELECT * FROM ChiTietHoaDon WHERE MaHD = ? AND MaDV = ?";
            return ql.net.util.XQuery.getSingleBean(ql.net.entity.ChiTietHoaDon.class, sql, maHD, maDV);
        } catch (Exception e) {
            return null;
        }
    }

    // Method kiểm tra hóa đơn đã tồn tại
    private String kiemTraHoaDonChoPhien(String maPhien) {
        try {
            String sql = "SELECT MaHD FROM HoaDon WHERE MaPhien = ?";
            return ql.net.util.XJdbc.getValue(sql, maPhien);
        } catch (Exception e) {
            return null;
        }
    }

    // Method tạo hóa đơn mới cho phiên
    private String taoHoaDonChoPhien(ql.net.entity.PhienSuDung phien) {
        try {
            String maHD = "HD" + String.format("%05d", System.currentTimeMillis() % 100000);
            System.out.println("Tạo hóa đơn với mã: " + maHD);

            // SỬA: Bỏ thuộc tính ngayTao để tránh lỗi Column not found
            ql.net.entity.HoaDon hoaDon = ql.net.entity.HoaDon.builder()
                .maHD(maHD)
                .maPhien(phien.getMaPhien())
                .maKH(phien.getMaKH())
                .maNV(phien.getMaNV())
                // BỎ DÒNG NÀY: .ngayTao(java.time.LocalDateTime.now())
                .tongTien(java.math.BigDecimal.ZERO)
                .trangThai("Chưa thanh toán")
                .build();

            // Thêm debug để xem entity trước khi insert
            System.out.println("HoaDon entity: " + hoaDon);

            boolean ok = new ql.net.dao.impl.HoaDonDaoImpl().insert(hoaDon);
            if (ok) {
                System.out.println("Tạo hóa đơn thành công: " + maHD);
                return maHD;
            } else {
                System.out.println("Tạo hóa đơn thất bại");
                return null;
            }

        } catch (Exception e) {
            System.err.println("Lỗi taoHoaDonChoPhien: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDoAn = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDoUong = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDichVuKhac = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Dịch vụ-Tấn Hiếu");

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        tblDoAn.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDoAn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDoAnMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDoAn);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Đồ ăn", jPanel1);

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
        jScrollPane2.setViewportView(tblDoUong);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Đồ uống", jPanel2);

        tblDichVuKhac.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDichVuKhac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDichVuKhacMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblDichVuKhac);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Dịch vụ khác", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void tblDoAnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDoAnMouseClicked
        // TODO add your handling code here:
        int row = tblDoAn.getSelectedRow();
        if (row >= 0) {
            String maDV = tblDoAn.getValueAt(row, 0).toString();
            String tenDV = tblDoAn.getValueAt(row, 1).toString();
            java.math.BigDecimal donGia = new java.math.BigDecimal(tblDoAn.getValueAt(row, 3).toString());
            chonDichVu(maDV, tenDV, donGia);
        }
    }//GEN-LAST:event_tblDoAnMouseClicked

    private void tblDoUongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDoUongMouseClicked
        // TODO add your handling code here:
        int row = tblDoUong.getSelectedRow();
        if (row >= 0) {
            String maDV = tblDoUong.getValueAt(row, 0).toString();
            String tenDV = tblDoUong.getValueAt(row, 1).toString();
            java.math.BigDecimal donGia = new java.math.BigDecimal(tblDoUong.getValueAt(row, 3).toString());
            chonDichVu(maDV, tenDV, donGia);
        }
    }//GEN-LAST:event_tblDoUongMouseClicked

    private void tblDichVuKhacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDichVuKhacMouseClicked
        // TODO add your handling code here:
        int row = tblDichVuKhac.getSelectedRow();
        if (row >= 0) {
            String maDV = tblDichVuKhac.getValueAt(row, 0).toString();
            String tenDV = tblDichVuKhac.getValueAt(row, 1).toString();
            java.math.BigDecimal donGia = new java.math.BigDecimal(tblDichVuKhac.getValueAt(row, 3).toString());
            chonDichVu(maDV, tenDV, donGia);
        }
    }//GEN-LAST:event_tblDichVuKhacMouseClicked

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
            java.util.logging.Logger.getLogger(ServiceJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServiceJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServiceJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServiceJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ServiceJDialog dialog = new ServiceJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblDichVuKhac;
    private javax.swing.JTable tblDoAn;
    private javax.swing.JTable tblDoUong;
    // End of variables declaration//GEN-END:variables
}
