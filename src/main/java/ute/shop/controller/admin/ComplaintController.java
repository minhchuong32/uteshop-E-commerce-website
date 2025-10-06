package ute.shop.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.Complaint;
import ute.shop.service.impl.ComplaintServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {
    "/admin/complaints",
    "/admin/complaints/edit",
    "/admin/complaints/delete",
})
public class ComplaintController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ComplaintServiceImpl complaintService = new ComplaintServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String uri = req.getRequestURI();

        if (uri.endsWith("/complaints")) {
            List<Complaint> list = complaintService.findAll();
            req.setAttribute("complaints", list);
            req.setAttribute("page", "complaints");
            req.setAttribute("view", "/views/admin/complaints/dashboard.jsp");
            req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
        } 
        else if (uri.endsWith("/edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Complaint c = complaintService.findById(id);
            req.setAttribute("complaint", c);
            req.setAttribute("page", "complaints");
            req.setAttribute("view", "/views/admin/complaints/edit.jsp");
            req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
        } 
        else if (uri.endsWith("/delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            complaintService.delete(id);
            resp.sendRedirect(req.getContextPath() + "/admin/complaints");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("complaintId"));
        String status = req.getParameter("status");

        Complaint c = complaintService.findById(id);
        c.setStatus(status);
        complaintService.update(c);

        resp.sendRedirect(req.getContextPath() + "/admin/complaints");
    }
}
