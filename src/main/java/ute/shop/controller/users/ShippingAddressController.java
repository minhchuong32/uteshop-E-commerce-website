package ute.shop.controller.users;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.shop.entity.ShippingAddress;
import ute.shop.entity.User;
import ute.shop.service.IShippingAddressService;
import ute.shop.service.impl.ShippingAddressServiceImpl;

@WebServlet("/user/address")
public class ShippingAddressController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final IShippingAddressService addressService = new ShippingAddressServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User account = (User) req.getAttribute("account");
        if (account == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        List<ShippingAddress> addresses = addressService.getAddressesByUser(account.getUserId());
        req.setAttribute("addresses", addresses);

        RequestDispatcher rd = req.getRequestDispatcher("/views/user/address.jsp");
        rd.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
        User account = (User) req.getAttribute("account");

        if ("add".equals(action)) {
            ShippingAddress addr = new ShippingAddress();
            addr.setUser(account);
            addr.setRecipientName(req.getParameter("recipientName"));
            addr.setPhoneNumber(req.getParameter("phone"));
            addr.setAddressLine(req.getParameter("addressLine"));
            addr.setWard(req.getParameter("ward"));
            addr.setDistrict(req.getParameter("district"));
            addr.setCity(req.getParameter("city"));
            addr.setIsDefault("on".equals(req.getParameter("isDefault")));
            addressService.addAddress(addr);
        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(req.getParameter("addressId"));
            ShippingAddress addr = addressService.getById(id);
            if (addr != null && addr.getUser().getUserId() == account.getUserId()) {
                addr.setRecipientName(req.getParameter("recipientName"));
                addr.setPhoneNumber(req.getParameter("phone"));
                addr.setAddressLine(req.getParameter("addressLine"));
                addr.setWard(req.getParameter("ward"));
                addr.setDistrict(req.getParameter("district"));
                addr.setCity(req.getParameter("city"));
                addr.setIsDefault("on".equals(req.getParameter("isDefault")));
                addressService.updateAddress(addr);
            }

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            addressService.deleteAddress(id);
        }

        resp.sendRedirect(req.getContextPath() + "/user/address");
	}
}
