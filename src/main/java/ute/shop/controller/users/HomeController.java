package ute.shop.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.shop.entity.Product;
import ute.shop.entity.ProductVariant;
import ute.shop.service.ICategoryService;
import ute.shop.service.IProductService;
import ute.shop.service.impl.CategoryServiceImpl;
import ute.shop.service.impl.ProductServiceImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(urlPatterns = { "/user/home" })
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ICategoryService categoryService = new CategoryServiceImpl();
    private final IProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        int size = 18;
        try {
            page = Integer.parseInt(req.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        String keyword = req.getParameter("keyword");
        String categoryIdStr = req.getParameter("categoryId");
        String minPriceStr = req.getParameter("minPrice");
        String maxPriceStr = req.getParameter("maxPrice");
        String sortBy = req.getParameter("sortBy");

        Integer categoryId = (categoryIdStr != null && !categoryIdStr.isEmpty()) ? Integer.parseInt(categoryIdStr) : null;
        Double minPrice = (minPriceStr != null && !minPriceStr.isEmpty()) ? Double.parseDouble(minPriceStr) : null;
        Double maxPrice = (maxPriceStr != null && !maxPriceStr.isEmpty()) ? Double.parseDouble(maxPriceStr) : null;

        List<Product> products;
        long totalProducts;

        if (keyword != null && !keyword.isEmpty()) {
            totalProducts = productService.countByKeyword(keyword);
            products = productService.searchByKeyword(keyword, page, size);
            req.setAttribute("keyword", keyword);
        } else {
            totalProducts = productService.countFilterProducts(categoryId, minPrice, maxPrice);
            products = productService.filterProducts(categoryId, minPrice, maxPrice, sortBy, page, size);
        }

        // Gán giá hiển thị từ variant rẻ nhất
        for (Product p : products) {
            if (p.getVariants() != null && !p.getVariants().isEmpty()) {
                BigDecimal minVariantPrice = p.getVariants().stream()
                        .map(ProductVariant::getPrice)
                        .min(BigDecimal::compareTo)
                        .orElse(BigDecimal.ZERO);
                p.setPrice(minVariantPrice);
            } else {
                p.setPrice(BigDecimal.ZERO);
            }
        }

        int totalPages = (int) Math.ceil((double) totalProducts / size);

        req.setAttribute("categories", categoryService.findAll());
        req.setAttribute("products", products);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);

        req.setAttribute("selectedCategoryId", categoryId);
        req.setAttribute("minPrice", minPrice);
        req.setAttribute("maxPrice", maxPrice);
        req.setAttribute("sortBy", sortBy);

        req.getRequestDispatcher("/views/user/home.jsp").forward(req, resp);
    }
}
