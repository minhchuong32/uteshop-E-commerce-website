package ute.shop.controller.vendors;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.shop.entity.Product;
import ute.shop.entity.Promotion;
import ute.shop.entity.Shop;
import ute.shop.service.IProductService;
import ute.shop.service.IPromotionService;
import ute.shop.service.impl.ProductServiceImpl;
import ute.shop.service.impl.PromotionServiceImpl;

@WebServlet(urlPatterns = {
    "/vendor/promotions",
    "/vendor/promotions/add",
    "/vendor/promotions/edit",
    "/vendor/promotions/delete",
    "/vendor/promotions/detail"
})
public class PromotionController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private IPromotionService promotionService = new PromotionServiceImpl();
    private IProductService productService = new ProductServiceImpl();

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        Shop shop = (Shop) req.getAttribute("currentShop");
        int shopId = shop.getShopId();

        if (uri.endsWith("/promotions")) {
            List<Promotion> promotions = promotionService.getValidPromotionsByShop(shopId);
            req.setAttribute("promotions", promotions);

            req.setAttribute("page", "promotions");
            req.setAttribute("view", "/views/vendor/promotions/list-promotion.jsp");
            req.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(req, resp);

        } else if (uri.endsWith("/add")) {
            List<Product> products = productService.findByShopId(shopId);
            req.setAttribute("products", products);

            req.setAttribute("page", "promotions");
            req.setAttribute("view", "/views/vendor/promotions/add.jsp");
            req.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(req, resp);

        } else if (uri.endsWith("/edit")) {
            String idParam = req.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                int id = Integer.parseInt(idParam);
                Promotion p = promotionService.findById(id);
                if (p != null && p.getShop().getShopId() == shopId) {
                    List<Product> products = productService.findByShopId(shopId);
                    req.setAttribute("products", products);
                    req.setAttribute("promotion", p);

                    req.setAttribute("page", "promotions");
                    req.setAttribute("view", "/views/vendor/promotions/add.jsp");
                    req.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(req, resp);
                    return;
                }
            }
            resp.sendRedirect(req.getContextPath() + "/vendor/promotions");

        } else if (uri.endsWith("/detail")) {
            String idParam = req.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                int id = Integer.parseInt(idParam);
                Promotion p = promotionService.findById(id);
                req.setAttribute("promotion", p);

                req.setAttribute("page", "promotions");
                req.setAttribute("view", "/views/vendor/promotions/detail.jsp");
                req.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(req, resp);
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/vendor/promotions");

        } else if (uri.endsWith("/delete")) {
            String idParam = req.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                try {
                    int id = Integer.parseInt(idParam);
                    Promotion p = promotionService.findById(id);
                    if (p != null && p.getShop().getShopId() == shopId) {
                        promotionService.delete(id);
                    }
                } catch (NumberFormatException e) {
                    // ignore invalid id
                }
            }
            resp.sendRedirect(req.getContextPath() + "/vendor/promotions");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        // Lấy shop hiện tại
        Shop shop = (Shop) req.getAttribute("currentShop");
        int shopId = shop.getShopId();

        if (uri.endsWith("/add") || uri.endsWith("/edit")) {
            // Lấy dữ liệu từ form
            String idParam = req.getParameter("promotion_id");
            String productIdParam = req.getParameter("product_id");
            String discountType = req.getParameter("discount_type"); // "PERCENT" hoặc "FIXED"
            String valueParam = req.getParameter("value");
            String startDateParam = req.getParameter("start_date"); // yyyy-MM-dd
            String endDateParam = req.getParameter("end_date");     // yyyy-MM-dd

            try {
                Promotion p;
                boolean isEdit = idParam != null && !idParam.isEmpty();
                if (isEdit) {
                    int id = Integer.parseInt(idParam);
                    p = promotionService.findById(id);
                    if (p == null) {
                        resp.sendRedirect(req.getContextPath() + "/vendor/promotions");
                        return;
                    }
                } else {
                    p = new Promotion();
                }

                // gán shop (chỉ set id reference để tránh query thêm)
                Shop s = new Shop();
                s.setShopId(shopId);
                p.setShop(s);

                // product optional
                if (productIdParam != null && !productIdParam.isEmpty()) {
                    Product prod = new Product();
                    prod.setProductId(Integer.parseInt(productIdParam));
                    p.setProduct(prod);
                } else {
                    p.setProduct(null);
                }

                p.setDiscountType(discountType != null ? discountType : "PERCENT");

                BigDecimal val = new BigDecimal(valueParam.replace(",", "").trim());
                p.setValue(val);

                LocalDate sd = LocalDate.parse(startDateParam, dtf);
                LocalDate ed = LocalDate.parse(endDateParam, dtf);
                p.setStartDate(java.sql.Date.valueOf(sd));
                p.setEndDate(java.sql.Date.valueOf(ed));

                if (isEdit) {
                    promotionService.update(p);
                } else {
                    promotionService.insert(p);
                }
            } catch (Exception e) {
                e.printStackTrace();
                // có thể set error message vào session/req để hiện lên trang form
                req.setAttribute("error", "Có lỗi khi lưu khuyến mãi: " + e.getMessage());
            }

            resp.sendRedirect(req.getContextPath() + "/vendor/promotions");
        } else {
            resp.sendRedirect(req.getContextPath() + "/vendor/promotions");
        }
    }
}
