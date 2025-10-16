package ute.shop.controller.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.*;
import ute.shop.service.*;
import ute.shop.service.impl.*;

@WebServlet(urlPatterns = {
    "/admin/promotions",
    "/admin/promotions/add",
    "/admin/promotions/edit",
    "/admin/promotions/delete"
})
public class PromotionController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IPromotionService promoService = new PromotionServiceImpl();
    private IShopService shopService = new ShopServiceImpl();
    private IProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uri = req.getRequestURI();

        if (uri.endsWith("/admin/promotions")) {
            List<Promotion> list = promoService.findAll();

            long percentCount = list.stream().filter(p -> "percent".equals(p.getDiscountType())).count();
            long fixedCount = list.stream().filter(p -> "fixed".equals(p.getDiscountType())).count();

            double avgPercent = list.stream()
                    .filter(p -> "percent".equals(p.getDiscountType()))
                    .map(Promotion::getValue).mapToDouble(BigDecimal::doubleValue).average().orElse(0.0);

            double avgFixed = list.stream()
                    .filter(p -> "fixed".equals(p.getDiscountType()))
                    .map(Promotion::getValue).mapToDouble(BigDecimal::doubleValue).average().orElse(0.0);

            req.setAttribute("promotions", list);
            req.setAttribute("percentCount", percentCount);
            req.setAttribute("fixedCount", fixedCount);
            req.setAttribute("avgPercent", avgPercent);
            req.setAttribute("avgFixed", avgFixed);
            req.setAttribute("page", "promotions");
            req.setAttribute("view", "/views/admin/promotions/list.jsp");
            req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
        }

        else if (uri.endsWith("/add")) {
            req.setAttribute("shops", shopService.getAll());
            req.setAttribute("products", productService.findAll()); // ✅ thêm danh sách sản phẩm
            req.setAttribute("page", "promotions");
            req.setAttribute("view", "/views/admin/promotions/form.jsp");
            req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
        }

        else if (uri.endsWith("/edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Promotion p = promoService.findById(id);
            req.setAttribute("promotion", p);
            req.setAttribute("shops", shopService.getAll());
            req.setAttribute("products", productService.findAll());
            req.setAttribute("page", "promotions");
            req.setAttribute("view", "/views/admin/promotions/form.jsp");
            req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
        }

        else if (uri.endsWith("/delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            promoService.delete(id);
            resp.sendRedirect(req.getContextPath() + "/admin/promotions");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int shopId = Integer.parseInt(req.getParameter("shopId"));
        String discountType = req.getParameter("discountType");
        BigDecimal value = new BigDecimal(req.getParameter("value"));
        Date start = Date.valueOf(req.getParameter("startDate"));
        Date end = Date.valueOf(req.getParameter("endDate"));

        // nhận productId (có thể null)
        String productParam = req.getParameter("productId");
        Integer productId = (productParam != null && !productParam.isEmpty())
                ? Integer.parseInt(productParam)
                : null;

        int id = req.getParameter("promotionId") != null && !req.getParameter("promotionId").isEmpty()
                ? Integer.parseInt(req.getParameter("promotionId"))
                : -1;

        Promotion p = new Promotion();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        p.setShop(shop);

        if (productId != null) {
            Product product = new Product();
            product.setProductId(productId);
            p.setProduct(product);
        }

        p.setDiscountType(discountType);
        p.setValue(value);
        p.setStartDate(start);
        p.setEndDate(end);

        if (id > 0) {
            p.setPromotionId(id);
            promoService.update(p);
        } else {
            promoService.insert(p);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/promotions");
    }
}

