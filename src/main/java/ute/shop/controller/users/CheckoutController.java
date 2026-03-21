package ute.shop.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import ute.shop.entity.*;
import ute.shop.facade.CheckoutFacade;
import ute.shop.factory.PaymentAbstractFactory;
import ute.shop.factory.PaymentFactoryProducer;
import ute.shop.service.*;
import ute.shop.service.impl.*;
import ute.shop.service.payment.IPaymentService;
import ute.shop.service.payment.PaymentContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = { "/user/checkout" })
public class CheckoutController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final IOrderService orderService = new OrderServiceImpl();
    private final ICartItemService cartService = new CartItemServiceImpl();
    private final IPromotionService promotionService = new PromotionServiceImpl();
    private final IShopService shopService = new ShopServiceImpl();
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

//        User user = (User) req.getAttribute("account");
//
//        if (user == null) {
//            resp.sendRedirect(req.getContextPath() + "/login");
//            return;
//        }
//
//        req.setCharacterEncoding("UTF-8");
//
//        String[] selectedIds = req.getParameterValues("selectedItems");
//
//        if (selectedIds == null || selectedIds.length == 0) {
//            req.setAttribute("error", "Không có sản phẩm nào được chọn!");
//            doGet(req, resp);
//            return;
//        }
//
//        String payment = req.getParameter("paymentMethod");
//
//        if (payment == null) {
//            req.setAttribute("error", "Vui lòng chọn phương thức thanh toán!");
//            doGet(req, resp);
//            return;
//        }
//
//        payment = payment.toUpperCase();
//
//        String addressIdStr = req.getParameter("selectedAddressId");
//
//        ShippingAddress shippingAddress = null;
//
//        if (addressIdStr != null && !addressIdStr.isEmpty()) {
//            shippingAddress =
//                    addressService.getById(Integer.parseInt(addressIdStr));
//        }
//
//        if (shippingAddress == null) {
//
//            req.setAttribute("error", "Vui lòng chọn địa chỉ giao hàng!");
//            doGet(req, resp);
//            return;
//        }
//
//        List<CartItem> selectedItems =
//                cartService.getCartByIds(selectedIds);
//
//        Carrier carrier = null;
//
//        BigDecimal shippingFee = new BigDecimal("30000");
//
//        String carrierIdStr = req.getParameter("carrierId");
//
//        if (carrierIdStr != null && !carrierIdStr.isEmpty()) {
//
//            carrier =
//                    carrierService.findById(Integer.parseInt(carrierIdStr));
//
//            if (carrier != null) {
//                shippingFee = carrier.getCarrierFee();
//            }
//        }
//
//        Map<Integer, List<CartItem>> itemsByShop =
//                selectedItems.stream().collect(
//                        Collectors.groupingBy(
//                                i -> i.getProductVariant()
//                                        .getProduct()
//                                        .getShop()
//                                        .getShopId()
//                        ));
//
//        BigDecimal allShopTotal = BigDecimal.ZERO;
//
//        boolean allSuccess = true;
//
//        StringBuilder orderIds = new StringBuilder();
//        StringBuilder shopNames = new StringBuilder();
//
//        for (Map.Entry<Integer, List<CartItem>> entry : itemsByShop.entrySet()) {
//
//            Integer shopId = entry.getKey();
//            List<CartItem> shopItems = entry.getValue();
//
//            BigDecimal subtotal =
//                    shopItems.stream()
//                            .map(i -> i.getPrice()
//                                    .multiply(BigDecimal.valueOf(i.getQuantity())))
//                            .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//            BigDecimal total = subtotal.add(shippingFee);
//
//            allShopTotal = allShopTotal.add(total);
//
//            Order order = new Order();
//
//            order.setUser(user);
//            order.setShop(shopService.getById(shopId));
//            order.setPaymentMethod(payment);
//
//            if ("VNPAY".equals(payment)) {
//                order.setStatus("Chờ thanh toán VNPAY");
//            } else {
//                order.setStatus("Mới");
//            }
//
//            order.setCreatedAt(new Date());
//            order.setShippingAddress(shippingAddress);
//            order.setTotalAmount(total);
//
//            for (CartItem ci : shopItems) {
//
//                OrderDetail od = new OrderDetail();
//
//                od.setOrder(order);
//                od.setProductVariant(ci.getProductVariant());
//                od.setQuantity(ci.getQuantity());
//                od.setPrice(ci.getPrice());
//
//                order.getOrderDetails().add(od);
//            }
//
//            Order savedOrder = orderService.save(order);
//
//            if (savedOrder != null) {
//
//                for (CartItem ci : shopItems) {
//                    cartService.removeFromCart(ci.getCartItemId());
//                }
//
//                if (orderIds.length() > 0) {
//                    orderIds.append(",");
//                    shopNames.append(",");
//                }
//
//                orderIds.append(savedOrder.getOrderId());
//                shopNames.append(savedOrder.getShop().getName());
//
//            } else {
//
//                allSuccess = false;
//            }
//        }
//        
////        if (allSuccess) {
////	        switch (payment) {
////	        	case "COD" -> {
////	            String redirectUrl = req.getContextPath() + "/user/orders?order_status=success";
////	            resp.sendRedirect(redirectUrl);
////	        	}
////	            case "MOMO" -> resp.sendRedirect(req.getContextPath() + "/user/payment/momo?paymentTotal="
////	                    + allShopTotal + "&orderIds=" + orderIds + "&shopNames=" + shopNames);
////	            case "VNPAY" -> {
////	                String redirectUrl = req.getContextPath() + "/user/payment/vnpay"
////	                    + "?paymentTotal=" + allShopTotal
////	                    + "&orderIds=" + URLEncoder.encode(orderIds.toString(), StandardCharsets.UTF_8)
////	                    + "&shopNames=" + URLEncoder.encode(shopNames.toString(), StandardCharsets.UTF_8);
////	                resp.sendRedirect(redirectUrl);
////	            }
////	            default -> {
////	                req.setAttribute("error", "Phương thức thanh toán không hợp lệ!");
////	                resp.sendRedirect(req.getContextPath() + "/user/checkout");
////	            }
////	        }
////	    }
////	    else {
////	        String redirectUrl = req.getContextPath() + "/user/orders?order_status=fail";
////	        resp.sendRedirect(redirectUrl);
////	    }
//
//
//        if (allSuccess) {
//
//            try {
//
//                PaymentContext context =
//                        new PaymentContext(
//                                allShopTotal,
//                                orderIds.toString(),
//                                shopNames.toString(),
//                                req.getContextPath()
//                        );
//
//                PaymentAbstractFactory factory =
//                        PaymentFactoryProducer.getFactory(payment);
//                
//                if(factory == null){
//                    throw new ServletException("Payment method not supported");
//                }
//                
//                IPaymentService paymentService =
//                        factory.createPaymentService();
//
//                String paymentUrl = paymentService.createPaymentUrl(context);
//              
//                resp.sendRedirect(paymentUrl);
//
//            } catch (Exception e) {
//
//                throw new ServletException(e);
//            }
//
//        } else {
//
//            resp.sendRedirect(
//                    req.getContextPath()
//                            + "/user/orders?order_status=fail"
//            );
//        }
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

    	        // Thêm contextPath vào URL nếu cần
    	        resp.sendRedirect(req.getContextPath() + paymentUrl
    	            .replace(req.getContextPath(), ""));

    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        resp.sendRedirect(req.getContextPath() + "/user/orders?order_status=fail");
    	    }
    }
}