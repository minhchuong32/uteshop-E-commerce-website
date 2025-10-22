package ute.shop.controller.api; // Tạo một package mới cho các API nội bộ

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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@WebServlet(urlPatterns = "/api/upload-chat-file")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2 MB
    maxFileSize = 1024 * 1024 * 10,   // 10 MB
    maxRequestSize = 1024 * 1024 * 50  // 50 MB
)
public class ChatImageUploadServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR = "/uploads/chat-files"; 
    private static final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Map<String, Object> responseMap = new HashMap<>();

        try {
            Part filePart = req.getPart("file");
            if (filePart == null || filePart.getSize() == 0) {
                responseMap.put("success", false);
                responseMap.put("message", "No file uploaded.");
                resp.getWriter().write(gson.toJson(responseMap));
                return;
            }

            String originalFileName = filePart.getSubmittedFileName();
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            // Sử dụng UUID để tên file gần như không bao giờ trùng
            String fileName = UUID.randomUUID().toString() + extension;

            String uploadPath = getServletContext().getRealPath(UPLOAD_DIR);
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            filePart.write(uploadPath + File.separator + fileName);

            String publicUrl = UPLOAD_DIR + "/" + fileName; // Chỉ trả về đường dẫn tương đối

            responseMap.put("success", true);
            responseMap.put("url", publicUrl);
            responseMap.put("filename", originalFileName); // TRẢ VỀ CẢ TÊN FILE GỐC
            resp.getWriter().write(gson.toJson(responseMap));

        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("success", false);
            responseMap.put("message", "Error uploading file: " + e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson(responseMap));
        }
    }
}