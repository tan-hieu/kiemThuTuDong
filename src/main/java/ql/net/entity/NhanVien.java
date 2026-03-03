
package ql.net.entity;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 *
 * @author ADMIN
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhanVien {
    private String maNV;
    private String tenDangNhap;
    private String matKhau;
    private String hoTen;
    private String soDT;
    private String chucVu;
    
    @Builder.Default
    private boolean trangThai = true;
    
    @Builder.Default
    private LocalDateTime ngayTao = LocalDateTime.now();
    
    private String image;
    
    private NhanVien nhanVien;

    public NhanVien getNhanVien() { 
        return nhanVien; 
    }
    public void setNhanVien(NhanVien nhanVien) { 
        this.nhanVien = nhanVien; 
    }
}
