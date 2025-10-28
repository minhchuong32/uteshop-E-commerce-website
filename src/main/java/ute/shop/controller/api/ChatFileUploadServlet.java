package ute.shop.controller.api;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@WebServlet(urlPatterns = "/api/upload-chat-file")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2 MB
    maxFileSize = 1024 * 1024 * 10,       // 10 MB
    maxRequestSize = 1024 * 1024 * 50     // 50 MB
)
public class ChatFileUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    //  Lưu file BÊN NGOÀI project
    // Windows: C:/uteshop-uploads/chat-files
    // Linux: /var/uteshop-uploads/chat-files
    private static final String UPLOAD_DIR = "C:/uteshop-uploads/chat-files";
    
    private static final Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        super.init();
        // Tạo thư mục nếu chưa tồn tại
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();
            if (created) {
                System.out.println(" Created upload directory: " + UPLOAD_DIR);
            } else {
                System.err.println(" Failed to create upload directory: " + UPLOAD_DIR);
            }
        } else {
            System.out.println(" Upload directory exists: " + UPLOAD_DIR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Map<String, Object> responseMap = new HashMap<>();

        try {
            // 1. Lấy file từ request
            Part filePart = req.getPart("file");
            if (filePart == null || filePart.getSize() == 0) {
                responseMap.put("success", false);
                responseMap.put("message", "Không có file nào được chọn.");
                resp.getWriter().write(gson.toJson(responseMap));
                return;
            }

            // 2. Lấy tên file gốc
            String originalFileName = getFileName(filePart);
            
            if (originalFileName == null || originalFileName.isEmpty()) {
                responseMap.put("success", false);
                responseMap.put("message", "Không thể xác định tên file.");
                resp.getWriter().write(gson.toJson(responseMap));
                return;
            }

            // 3. Kiểm tra định dạng file
            if (!isAllowedFile(originalFileName)) {
                responseMap.put("success", false);
                responseMap.put("message", 
                    "Loại file không được phép. Chỉ chấp nhận: JPG, PNG, GIF, PDF, DOC, DOCX, XLS, XLSX, TXT, PPT, PPTX");
                resp.getWriter().write(gson.toJson(responseMap));
                return;
            }

            // 4. Tạo tên file unique
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString() + extension;

            // 5. Tạo đường dẫn đầy đủ
            Path filePath = Paths.get(UPLOAD_DIR, uniqueFileName);

            // 6. Lưu file bằng NIO (hiệu quả hơn)
            Files.copy(filePart.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println(" File uploaded successfully: " + filePath.toString());
            System.out.println("   Original name: " + originalFileName);
            System.out.println("   File size: " + filePart.getSize() + " bytes");

            // 7. Tạo URL để truy cập file (qua ChatFileServlet)
            String publicUrl = "/chat-files/" + uniqueFileName;

            // 8. Trả về JSON response
            responseMap.put("success", true);
            responseMap.put("url", publicUrl);
            responseMap.put("filename", originalFileName);
            responseMap.put("fileSize", filePart.getSize());

            resp.getWriter().write(gson.toJson(responseMap));

        } catch (IllegalStateException e) {
            // Lỗi khi file vượt quá dung lượng cho phép
            e.printStackTrace();
            responseMap.put("success", false);
            responseMap.put("message", "Dung lượng file vượt quá giới hạn 10MB.");
            resp.setStatus(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE); // 413
            resp.getWriter().write(gson.toJson(responseMap));
            
        } catch (IOException e) {
            // Lỗi I/O khi lưu file
            e.printStackTrace();
            responseMap.put("success", false);
            responseMap.put("message", "Lỗi khi lưu file: " + e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson(responseMap));
            
        } catch (Exception e) {
            // Các lỗi khác
            e.printStackTrace();
            responseMap.put("success", false);
            responseMap.put("message", "Lỗi máy chủ: " + e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson(responseMap));
        }
    }

    /**
     * Lấy tên file từ Part header
     */
    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        if (contentDisposition == null) {
            return null;
        }
        
        for (String token : contentDisposition.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf('=') + 1)
                           .trim()
                           .replace("\"", "");
            }
        }
        return null;
    }

    /**
     * Kiểm tra file có được phép không
     */
    private boolean isAllowedFile(String filename) {
        if (filename == null || filename.isEmpty()) {
            return false;
        }
        
        String lowerName = filename.toLowerCase();
        String[] allowedExtensions = {
            ".jpg", ".jpeg", ".png", ".gif", ".webp",  // Hình ảnh
            ".pdf",                                     // PDF
            ".doc", ".docx",                           // Word
            ".xls", ".xlsx",                           // Excel
            ".ppt", ".pptx",                           // PowerPoint
            ".txt"                                      // Text
        };
        
        for (String ext : allowedExtensions) {
            if (lowerName.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }
}