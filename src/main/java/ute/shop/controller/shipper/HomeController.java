package ute.shop.controller.shipper;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import ute.shop.entity.User;
import ute.shop.service.IDeliveryService;
import ute.shop.service.IProductService;
import ute.shop.service.impl.DeliveryServiceImpl;
import ute.shop.service.impl.ProductServiceImpl;

@WebServlet(urlPatterns = {"/shipper/home"})
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IDeliveryService deliveryService = new DeliveryServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

    	HttpSession session = req.getSession(false);
        User userLogin = (session != null) ? (User) session.getAttribute("account") : null;
        if (userLogin == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // ✅ Lấy dữ liệu thống kê
        long assignedCount = deliveryService.countByStatus(userLogin.getUserId(), null); // tất cả đơn
        long deliveringCount = deliveryService.countByStatus(userLogin.getUserId(), "Đang giao");
        long deliveredCount = deliveryService.countByStatus(userLogin.getUserId(), "Đã giao");
        long canceledCount = deliveryService.countByStatus(userLogin.getUserId(), "Đã Hủy");
        
        double successRate = 0;
        if (assignedCount > 0) {
            successRate = (double) deliveredCount / assignedCount * 100;
        }

        req.setAttribute("successRate", successRate);
        req.setAttribute("failedRate", 100 - successRate);

        req.setAttribute("assignedCount", assignedCount);
        req.setAttribute("deliveringCount", deliveringCount);
        req.setAttribute("deliveredCount", deliveredCount);
        req.setAttribute("canceledCount", canceledCount);

        // ✅ Tỷ lệ giao thành công theo tháng
        List<Object[]> successRateByMonth = deliveryService.getSuccessRateByMonth(userLogin.getUserId());
        req.setAttribute("successRateByMonth", successRateByMonth);

        // Lấy tham số page
        String page = req.getParameter("page");

        if (page != null) {
            switch (page) {               
                case "orders":
                    resp.sendRedirect(req.getContextPath() + "/shipper/orders");
                    return;
                case "settings":
                    resp.sendRedirect(req.getContextPath() + "/shipper/settings");
                    return;
                default:
                    resp.sendRedirect(req.getContextPath() + "/shipper/home");
                    return;
            }
        }

        // Nếu không có page → mặc định Dashboard
        req.setAttribute("page", "dashboard");
        req.setAttribute("view", "/views/shipper/dashboard.jsp");
        req.getRequestDispatcher("/WEB-INF/decorators/shipper.jsp").forward(req, resp);
    }
}
