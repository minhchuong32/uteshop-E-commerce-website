package ute.shop.controller.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.shop.dao.ICarrierDao;
import ute.shop.dao.impl.CarrierDaoImpl;
import ute.shop.entity.Carrier;

@WebServlet(urlPatterns = {"/admin/carriers", "/admin/carriers/add", "/admin/carriers/edit", "/admin/carriers/delete"})
public class CarrierController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ICarrierDao carrierDao = new CarrierDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();

        try {
            if (url.endsWith("/delete")) {
                deleteCarrier(req, resp);
            } else {
                // Hiển thị danh sách
                List<Carrier> list = carrierDao.findAll();
                req.setAttribute("carriers", list);
                req.setAttribute("page", "carriers");
                req.setAttribute("view", "/views/admin/carriers.jsp");
                req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi xử lý yêu cầu!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();

        try {
            if (url.endsWith("/add")) {
                addCarrier(req, resp);
            } else if (url.endsWith("/edit")) {
                updateCarrier(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi xử lý yêu cầu!");
        }
    }

    // ====================== XỬ LÝ THÊM ======================
    private void addCarrier(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        String name = req.getParameter("carrierName");
        BigDecimal fee = new BigDecimal(req.getParameter("carrierFee"));
        String description = req.getParameter("carrierDescription");

        Carrier carrier = new Carrier();
        carrier.setCarrierName(name);
        carrier.setCarrierFee(fee);
        carrier.setCarrierDescription(description);

        carrierDao.save(carrier);

        resp.sendRedirect(req.getContextPath() + "/admin/carriers");
    }

    // ====================== XỬ LÝ SỬA ======================
    private void updateCarrier(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(req.getParameter("carrierId"));
        String name = req.getParameter("carrierName");
        BigDecimal fee = new BigDecimal(req.getParameter("carrierFee"));
        String description = req.getParameter("carrierDescription");

        Carrier carrier = new Carrier();
        carrier.setCarrierId(id);
        carrier.setCarrierName(name);
        carrier.setCarrierFee(fee);
        carrier.setCarrierDescription(description);

        carrierDao.update(carrier);

        resp.sendRedirect(req.getContextPath() + "/admin/carriers");
    }

    // ====================== XỬ LÝ XÓA ======================
    private void deleteCarrier(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        carrierDao.delete(id);
        resp.sendRedirect(req.getContextPath() + "/admin/carriers");
    }
}
