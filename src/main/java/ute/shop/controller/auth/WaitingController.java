package ute.shop.controller.auth;

import java.io.IOException;

import ute.shop.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/waiting" })
public class WaitingController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession();
		if (session != null && session.getAttribute("account") != null) {
			User u = (User) session.getAttribute("account");
			req.setAttribute("username", u.getUsername());
			if ("Admin".equals(u.getRole())) {
				resp.sendRedirect(req.getContextPath() + "/admin/home");
			} else if ("Vendor".equals(u.getRole())) {
				resp.sendRedirect(req.getContextPath() + "/vendor/home");
			} else if ("User".equals(u.getRole())) {
				resp.sendRedirect(req.getContextPath() + "/user/home");
			}

		}

		else {
			resp.sendRedirect(req.getContextPath() + "/login");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
}
