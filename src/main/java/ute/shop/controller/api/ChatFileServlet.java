package ute.shop.controller.api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet(urlPatterns = "/chat-files/*")
public class ChatFileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // ✅ PHẢI TRÙNG với UPLOAD_DIR trong ChatFileUploadServlet
    private static final String UPLOAD_DIR = "C:/uteshop-uploads/chat-files";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        // 1. Lấy tên file từ URL
        // Ví dụ: /uteshop/chat-files/abc-123.png → pathInfo = /abc-123.png
        String pathInfo = req.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tên file không hợp lệ");
            return;
        }
        
        // Loại bỏ dấu / đầu tiên
        String filename = pathInfo.substring(1);
        
        // 2. Kiểm tra tên file có chứa ký tự nguy hiểm không (path traversal attack)
        if (filename.contains("..") || filename.contains("/") || filename.contains("\\")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tên file không hợp lệ");
            return;
        }
        
        // 3. Tạo đường dẫn đầy đủ đến file
        Path filePath = Paths.get(UPLOAD_DIR, filename);
        File file = filePath.toFile();
        
        // 4. Kiểm tra file có tồn tại không
        if (!file.exists() || !file.isFile()) {
            System.err.println("❌ File not found: " + filePath.toString());
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "File không tồn tại");
            return;
        }
        
        // 5. Xác định Content-Type
        String contentType = getServletContext().getMimeType(filename);
        if (contentType == null) {
            contentType = "application/octet-stream"; // Default binary
        }
        
        // 6. Set response headers
        resp.setContentType(contentType);
        resp.setContentLengthLong(file.length());
        
        // Nếu là hình ảnh → hiển thị inline
        // Nếu là file khác → download
        if (contentType.startsWith("image/")) {
            resp.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");
        } else {
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        }
        
        // Enable caching for better performance
        resp.setHeader("Cache-Control", "public, max-age=31536000"); // 1 year
        
        // 7. Ghi file vào response output stream
        try (OutputStream out = resp.getOutputStream()) {
            Files.copy(filePath, out);
            out.flush();
        }
        
        System.out.println("✅ Served file: " + filename + " (" + file.length() + " bytes)");
    }
}