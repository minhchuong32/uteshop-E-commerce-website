package ute.shop.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.shop.entity.ProductVariant;
import ute.shop.service.IProductVariantService;
import ute.shop.service.impl.ProductVariantServiceImpl;

@WebServlet("/api/variant/select")
public class ProductVariantController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IProductVariantService variantService = new ProductVariantServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        ObjectMapper mapper = new ObjectMapper();

        try {
            Map<String, Object> payload = mapper.readValue(req.getReader(), Map.class);
            if (payload == null || payload.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Thiếu dữ liệu lựa chọn\"}");
                return;
            }

            // Lấy productId
            Object pidObj = payload.get("productId");
            if (pidObj == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Thiếu productId\"}");
                return;
            }
            int productId = (pidObj instanceof Number)
                    ? ((Number) pidObj).intValue()
                    : Integer.parseInt(pidObj.toString());

            // Xóa productId ra khỏi map để còn lại 1 cặp key-value duy nhất
            payload.remove("productId");

            // Nếu payload trống -> lỗi
            if (payload.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Thiếu thuộc tính sản phẩm\"}");
                return;
            }

            // Lấy cặp đầu tiên (1 option duy nhất)
            Map.Entry<String, Object> entry = payload.entrySet().iterator().next();
            Map<String, Object> selectedOption = new HashMap<>();
            selectedOption.put(entry.getKey(), entry.getValue());

            // Gọi service tìm variant
            ProductVariant variant = variantService.findByOptions(productId, selectedOption);

            if (variant == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\":\"Không tìm thấy phân loại phù hợp\"}");
                return;
            }

            // Kết quả
            Map<String, Object> result = new HashMap<>();
            result.put("variantId", variant.getId());
            result.put("price", variant.getPrice());
            result.put("oldPrice", variant.getOldPrice());
            result.put("stock", variant.getStock());

            String imageUrl = variant.getImageUrl();
            if (imageUrl == null || imageUrl.isBlank()) {
                imageUrl = variant.getProduct().getImageUrl();
            }

            if (imageUrl != null && !imageUrl.isBlank()) {
                imageUrl = imageUrl.trim();
                if (!imageUrl.startsWith("/assets/")) {
                    if (imageUrl.startsWith("/images/"))
                        imageUrl = "/assets" + imageUrl;
                    else if (imageUrl.startsWith("images/"))
                        imageUrl = "/assets/" + imageUrl;
                    else if (!imageUrl.startsWith("/"))
                        imageUrl = "/assets/" + imageUrl;
                    else
                        imageUrl = "/assets" + imageUrl;
                }
            }
            result.put("imageUrl", imageUrl);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(mapper.writeValueAsString(result));

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"Lỗi xử lý dữ liệu variant\"}");
        }
    }
}
