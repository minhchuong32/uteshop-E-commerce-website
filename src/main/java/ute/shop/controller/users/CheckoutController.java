package ute.shop.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import ute.shop.entity.*;
import ute.shop.facade.CheckoutFacade;
import ute.shop.service.*;
import ute.shop.service.impl.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = { "/user/checkout" })
public class CheckoutController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ICartItemService cartService = new CartItemServiceImpl();
    private final IPromotionService promotionService = new PromotionServiceImpl();
    private final ICarrierService carrierService = new CarrierServiceImpl();
    private final IShippingAddressService addressService = new ShippingAddressServiceImpl();
    private final CheckoutFacade checkoutFacade = new CheckoutFacade();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getAttribute("account");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Lấy danh sách địa chỉ
        List<ShippingAddress> addresses =
                addressService.getAddressesByUser(user.getUserId());

        req.setAttribute("addresses", addresses);

        ShippingAddress defaultAddress =
                addresses.stream()
                        .filter(ShippingAddress::getIsDefault)
                        .findFirst()
                        .orElse(null);

        req.setAttribute("defaultAddress", defaultAddress);

        if (defaultAddress == null) {
            req.setAttribute("noDefaultAddress", true);
        }

        String[] selectedParams = req.getParameterValues("selectedItems");

        List<CartItem> cartItems;

        if (selectedParams != null && selectedParams.length > 0) {

            cartItems = cartService.getCartByIds(selectedParams);

        } else {

            cartItems = cartService.getCartByUser(user);
        }

        if (cartItems.isEmpty()) {

            req.setAttribute("message", "Giỏ hàng trống!");
            req.getRequestDispatcher("/views/user/order/checkout.jsp")
                    .forward(req, resp);
            return;
        }

        Map<Integer, List<CartItem>> itemsByShop =
                cartItems.stream().collect(
                        Collectors.groupingBy(
                                i -> i.getProductVariant()
                                        .getProduct()
                                        .getShop()
                                        .getShopId()
                        ));

        List<Carrier> carriers = carrierService.findAll();
        req.setAttribute("carriers", carriers);

        Map<Integer, List<Promotion>> promosByProduct = new HashMap<>();

        for (CartItem item : cartItems) {

            int productId =
                    item.getProductVariant().getProduct().getProductId();

            List<Promotion> promos =
                    promotionService.getValidPromotionsByProduct(productId);

            promosByProduct.put(productId, promos);
        }

        req.setAttribute("promosByProduct", promosByProduct);
        req.setAttribute("itemsByShop", itemsByShop);

        req.getRequestDispatcher("/views/user/order/checkout.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
   	
    	    User user = (User) req.getAttribute("account");
    	    if (user == null) { resp.sendRedirect(req.getContextPath() + "/login"); return; }

    	    req.setCharacterEncoding("UTF-8");
    	    String[] selectedIds   = req.getParameterValues("selectedItems");
    	    String   payment       = req.getParameter("paymentMethod");
    	    String   addressIdStr  = req.getParameter("selectedAddressId");
    	    String   carrierIdStr  = req.getParameter("carrierId");

    	    // Validate cơ bản
    	    if (selectedIds == null || selectedIds.length == 0) {
    	        req.setAttribute("error", "Không có sản phẩm nào được chọn!");
    	        doGet(req, resp); return;
    	    }
    	    if (addressIdStr == null || addressIdStr.isEmpty()) {
    	        req.setAttribute("error", "Vui lòng chọn địa chỉ giao hàng!");
    	        doGet(req, resp); return;
    	    }

    	    try {
    	        //  Toàn bộ nghiệp vụ chỉ còn 1 dòng
    	        String paymentUrl = checkoutFacade.placeOrder(
    	            user, selectedIds, addressIdStr, carrierIdStr, payment);

    	        resp.sendRedirect(paymentUrl);

    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        resp.sendRedirect(req.getContextPath() + "/user/orders?order_status=fail");
    	    }
    }
}