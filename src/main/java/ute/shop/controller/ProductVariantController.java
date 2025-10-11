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

        Map<String, Object> selectedOptions = mapper.readValue(req.getReader(), Map.class);

        // Lấy productId (có thể là Integer hoặc String)
        Object pidObj = selectedOptions.remove("productId");
        int productId = 0;
        if (pidObj instanceof Number) {
            productId = ((Number) pidObj).intValue();
        } else if (pidObj instanceof String) {
            productId = Integer.parseInt((String) pidObj);
        }

        // Tìm variant theo lựa chọn
        ProductVariant variant = variantService.findByOptions(productId, selectedOptions);
     // trong ProductVariantController.doPost, ngay sau khi có 'variant':
        System.out.println("DEBUG variant - id=" + variant.getId() + ", stock=" + variant.getStock()
            + ", price=" + variant.getPrice() + ", oldPrice=" + variant.getOldPrice()
            + ", optionName=" + variant.getOptionName() + ", optionValue=" + variant.getOptionValue());

        if (variant != null) {
            Map<String, Object> result = new HashMap<>();
            result.put("variantId", variant.getId());
            result.put("price", variant.getPrice());
            result.put("oldPrice", variant.getOldPrice()); // thêm nếu cần hiển thị giá cũ
            result.put("stock", variant.getStock());
            result.put("imageUrl", variant.getImageUrl());
            resp.getWriter().write(mapper.writeValueAsString(result));
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\":\"Không tìm thấy phân loại phù hợp\"}");
        }
    }

}
