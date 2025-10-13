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
public class ProductVariantController extends HttpServlet{
	private static final long serialVersionUID = 1L;
    private final IProductVariantService variantService = new ProductVariantServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	resp.setContentType("application/json;charset=UTF-8");
        ObjectMapper mapper = new ObjectMapper();

        try {
            // 🟢 Đọc JSON từ body
            Map<String, Object> selectedOptions = mapper.readValue(req.getReader(), Map.class);
            if (selectedOptions == null || selectedOptions.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Thiếu dữ liệu lựa chọn\"}");
                return;
            }

            // 🟢 Lấy productId (có thể là Integer hoặc String)
            Object pidObj = selectedOptions.remove("productId");
            if (pidObj == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Thiếu productId\"}");
                return;
            }

            int productId;
            if (pidObj instanceof Number) {
                productId = ((Number) pidObj).intValue();
            } else {
                productId = Integer.parseInt(pidObj.toString());
            }

            // 🟢 Gọi service tìm variant phù hợp
            ProductVariant variant = variantService.findByOptions(productId, selectedOptions);

            if (variant == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\":\"Không tìm thấy phân loại phù hợp\"}");
                return;
            }

            // 🟢 Ghi log debug
            System.out.printf("DEBUG variant: id=%d, stock=%d, price=%s, oldPrice=%s, option=%s:%s, imageUrl=%s%n",
                    variant.getId(),
                    variant.getStock(),
                    variant.getPrice(),
                    variant.getOldPrice(),
                    variant.getOptionName(),
                    variant.getOptionValue(),
                    variant.getImageUrl()
            );

            // 🟢 Chuẩn bị response JSON
            Map<String, Object> result = new HashMap<>();
            result.put("variantId", variant.getId());
            result.put("price", variant.getPrice());
            result.put("oldPrice", variant.getOldPrice());
            result.put("stock", variant.getStock());

            // Nếu không có ảnh variant, fallback sang ảnh mặc định
            String imageUrl = variant.getImageUrl();
            if (imageUrl == null || imageUrl.isBlank()) {
            	imageUrl = "/assets";
                imageUrl = imageUrl + variant.getProduct().getImageUrl(); // hoặc ảnh mặc định của product
            }
            result.put("imageUrl", imageUrl);

            resp.getWriter().write(mapper.writeValueAsString(result));

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"Lỗi xử lý dữ liệu variant\"}");
        }
    }

}
