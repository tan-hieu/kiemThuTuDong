package ql.net.util;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class XIcon {
    /**
     * Đọc icon từ resource hoặc file
     * @param path đường dẫn file, đường dẫn resource hoặc tên resource
     * @return ImageIcon
     */
    public static ImageIcon getIcon(String path) {
        try {
            if(!path.contains("/") && !path.contains("\\")){ // resource name
                return XIcon.getIcon("/ql/net/icons/" + path);
            }
            if(path.startsWith("/")){ // resource path
                java.net.URL resource = XIcon.class.getResource(path);
                if (resource != null) {
                    return new ImageIcon(resource);
                } else {
                    System.err.println("Resource not found: " + path);
                    return new ImageIcon(); // Empty icon instead of null
                }
            }
            // File path
            File file = new File(path);
            if (file.exists()) {
                return new ImageIcon(path);
            } else {
                System.err.println("File not found: " + path);
                return new ImageIcon(); // Empty icon instead of null
            }
        } catch (Exception e) {
            System.err.println("Error loading icon: " + path + " - " + e.getMessage());
            return new ImageIcon(); // Empty icon instead of null
        }
    }
    /**
     * Đọc icon theo kích thước
     * @param path đường dẫn file hoặc tài nguyên
     * @param width chiều rộng
     * @param height chiều cao
     * @return Icon
     */
    public static ImageIcon getIcon(String path, int width, int height) {
        try {
            ImageIcon originalIcon = getIcon(path);
            if (originalIcon.getIconWidth() <= 0 || originalIcon.getIconHeight() <= 0) {
                return originalIcon; // Return original if it's empty or invalid
            }
            
            // Use original size if width or height is 0 or negative
            int targetWidth = width > 0 ? width : originalIcon.getIconWidth();
            int targetHeight = height > 0 ? height : originalIcon.getIconHeight();
            
            Image image = originalIcon.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
            return new ImageIcon(image);
        } catch (Exception e) {
            System.err.println("Error scaling icon: " + path + " - " + e.getMessage());
            return new ImageIcon();
        }
    }
    /**
     * Thay đổi icon của JLabel
     * @param label JLabel cần thay đổi
     * @param path đường dẫn file hoặc tài nguyên
     */
    public static void setIcon(JLabel label, String path) {
        try {
            // Get label size, use default if not set
            int width = label.getWidth();
            int height = label.getHeight();
            
            if (width <= 0 || height <= 0) {
                // Use original icon size if label size is not available
                label.setIcon(XIcon.getIcon(path));
            } else {
                label.setIcon(XIcon.getIcon(path, width, height));
            }
        } catch (Exception e) {
            System.err.println("Error setting icon for label: " + path + " - " + e.getMessage());
        }
    }
    /**
     * Thay đổi icon của JLabel
     * @param label JLabel cần thay đổi
     * @param file file icon
     */
    public static void setIcon(JLabel label, File file) {
        XIcon.setIcon(label, file.getAbsolutePath());
    }
    /**
     * Sao chép file vào thư mục với tên file mới là duy nhất
     * @param fromFile file cần sao chép
     * @param folder thư mục đích
     * @return File đã sao chép
     */
    public static File copyTo(File fromFile, String folder) {
        String fileExt = fromFile.getName().substring(fromFile.getName().lastIndexOf("."));
        File toFile = new File(folder, XStr.getKey() + fileExt);
        toFile.getParentFile().mkdirs();
        try {
            Files.copy(fromFile.toPath(), toFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return toFile;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    /**
     * Test method để kiểm tra việc load icon
     * @param iconName tên file icon cần test
     */
    public static void testIcon(String iconName) {
        System.out.println("Testing icon: " + iconName);
        
        // Test load bằng tên file
        ImageIcon icon1 = getIcon(iconName);
        System.out.println("Load by name - Width: " + icon1.getIconWidth() + ", Height: " + icon1.getIconHeight());
        
        // Test load bằng đường dẫn đầy đủ
        ImageIcon icon2 = getIcon("/ql/net/icons/" + iconName);
        System.out.println("Load by full path - Width: " + icon2.getIconWidth() + ", Height: " + icon2.getIconHeight());
        
        // Test xem resource có tồn tại không
        java.net.URL resource = XIcon.class.getResource("/ql/net/icons/" + iconName);
        System.out.println("Resource URL: " + resource);
        
        if (resource == null) {
            System.err.println("Resource not found! Check if file exists in: src/main/resources/ql/net/icons/" + iconName);
        }
    }
    public static File copyTo(File fromFile) {
        return copyTo(fromFile, "files");
    }
}
