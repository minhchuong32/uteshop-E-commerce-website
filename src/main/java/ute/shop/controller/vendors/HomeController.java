package ute.shop.controller.vendors;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.Shop;
import ute.shop.service.IOrderService;
import ute.shop.service.IProductService;
import ute.shop.service.IRevenueService;
import ute.shop.service.impl.OrderServiceImpl;
import ute.shop.service.impl.ProductServiceImpl;
import ute.shop.service.impl.RevenueServiceImpl;


@WebServlet(urlPatterns = {"/vendor/home"})
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final IProductService productService = new ProductServiceImpl();
    private final IOrderService orderService = new OrderServiceImpl();
    private final IRevenueService revenueService = new RevenueServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

    	//  LẤY USER VÀ SHOP TỪ REQUEST ATTRIBUTE (do Filter đặt vào)
        // User userLogin = (User) req.getAttribute("account"); // Không cần user nữa nếu chỉ dùng shopId
        Shop shop = (Shop) req.getAttribute("currentShop");
        
        // Filter đã đảm bảo shop không thể null khi request đến được đây.
        // Bạn không cần kiểm tra if (shop == null) nữa.

        int shopId = shop.getShopId();
        
        // Phần logic lấy dữ liệu giữ nguyên
        long totalProducts = productService.getTotalProducts(shopId);
        long totalOrders = orderService.getTotalOrders(shopId);
        long totalCustomers = orderService.getTotalCustomers(shopId);
        BigDecimal totalRevenue = revenueService.getTotalRevenue(shopId);
        List<Object[]> revenueByMonth = revenueService.getRevenueByMonthByShop(shopId);
        List<Object[]> topProducts = productService.getTopSellingProducts(shopId, 5);
        List<Object[]> orderTrend = orderService.getOrderTrendByShop(shopId);
        List<Object[]> orderStatus = orderService.getOrderStatusCountByShop(shopId);
        List<Object[]> categoryStats = productService.getProductCountByCategory(shopId);

        req.setAttribute("orderTrend", orderTrend);
        req.setAttribute("orderStatus", orderStatus);
        req.setAttribute("categoryStats", categoryStats);
        req.setAttribute("totalProducts", totalProducts);
        req.setAttribute("totalOrders", totalOrders);
        req.setAttribute("totalCustomers", totalCustomers);
        req.setAttribute("totalRevenue", totalRevenue);
        req.setAttribute("revenueByMonth", revenueByMonth);
        req.setAttribute("topProducts", topProducts);

        // Lấy tham số page
        String page = req.getParameter("page");

        if (page != null) {
            switch (page) {
                case "products":
                    resp.sendRedirect(req.getContextPath() + "/vendor/products");
                    return;
                case "orders":
                    resp.sendRedirect(req.getContextPath() + "/vendor/orders");
                    return;
                case "stats":
                    resp.sendRedirect(req.getContextPath() + "/vendor/stats");
                    return;
                case "settings":
                    resp.sendRedirect(req.getContextPath() + "/vendor/settings");
                    return;
                default:
                    resp.sendRedirect(req.getContextPath() + "/vendor/home");
                    return;
            }
        }

        req.setAttribute("page", "dashboard");
        req.setAttribute("view", "/views/vendor/dashboard.jsp");
        req.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(req, resp);
    }
}

// code cũ :
//package ute.shop.controller.vendors;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.util.List;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//import ute.shop.entity.Shop;
//import ute.shop.entity.User;
//import ute.shop.service.IOrderService;
//import ute.shop.service.IProductService;
//import ute.shop.service.IRevenueService;
//import ute.shop.service.IShopService;
//import ute.shop.service.impl.OrderServiceImpl;
//import ute.shop.service.impl.ProductServiceImpl;
//import ute.shop.service.impl.RevenueServiceImpl;
//import ute.shop.service.impl.ShopServiceImpl;
//
//
//@WebServlet(urlPatterns = {"/vendor/home"})
//public class HomeController extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//    
//    private final IProductService productService = new ProductServiceImpl();
//    private final IOrderService orderService = new OrderServiceImpl();
//    private final IRevenueService revenueService = new RevenueServiceImpl();
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//
//    	HttpSession session = req.getSession(false);
//        User userLogin = (session != null) ? (User) session.getAttribute("account") : null;
//        if (userLogin == null) {
//            resp.sendRedirect(req.getContextPath() + "/login");
//            return;
//        }
//
//        Shop shop = null;
//        if ("VENDOR".equalsIgnoreCase(userLogin.getRole())) {
//        	IShopService shopService = new ShopServiceImpl();
//            shop = shopService.findByUserId(userLogin.getUserId());
//            if (shop != null) {
//                session.setAttribute("currentShop", shop);
//            }
//        } else {
//            shop = (Shop) session.getAttribute("currentShop");
//        }
//
//        if (shop == null) {
//            resp.sendRedirect(req.getContextPath() + "/vendor/register-shop");
//            return;
//        }
//
//        int shopId = shop.getShopId();
//        
//        long totalProducts = productService.getTotalProducts(shopId);
//        long totalOrders = orderService.getTotalOrders(shopId);
//        long totalCustomers = orderService.getTotalCustomers(shopId);
//        BigDecimal totalRevenue = revenueService.getTotalRevenue(shopId);
//        List<Object[]> revenueByMonth = revenueService.getRevenueByMonthByShop(shopId);
//        List<Object[]> topProducts = productService.getTopSellingProducts(shopId, 5);
//        List<Object[]> orderTrend = orderService.getOrderTrendByShop(shopId);
//        List<Object[]> orderStatus = orderService.getOrderStatusCountByShop(shopId);
//        List<Object[]> categoryStats = productService.getProductCountByCategory(shopId);
//
//        req.setAttribute("orderTrend", orderTrend);
//        req.setAttribute("orderStatus", orderStatus);
//        req.setAttribute("categoryStats", categoryStats);
//
//        req.setAttribute("totalProducts", totalProducts);
//        req.setAttribute("totalOrders", totalOrders);
//        req.setAttribute("totalCustomers", totalCustomers);
//        req.setAttribute("totalRevenue", totalRevenue);
//        req.setAttribute("revenueByMonth", revenueByMonth);
//        req.setAttribute("topProducts", topProducts);
//
//        // Lấy tham số page
//        String page = req.getParameter("page");
//
//        if (page != null) {
//            switch (page) {
//                case "products":
//                    resp.sendRedirect(req.getContextPath() + "/vendor/products");
//                    return;
//                case "orders":
//                    resp.sendRedirect(req.getContextPath() + "/vendor/orders");
//                    return;
//                case "stats":
//                    resp.sendRedirect(req.getContextPath() + "/vendor/stats");
//                    return;
//                case "settings":
//                    resp.sendRedirect(req.getContextPath() + "/vendor/settings");
//                    return;
//                default:
//                    resp.sendRedirect(req.getContextPath() + "/vendor/home");
//                    return;
//            }
//        }
//
//        req.setAttribute("page", "dashboard");
//        req.setAttribute("view", "/views/vendor/dashboard.jsp");
//        req.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(req, resp);
//    }
//}
