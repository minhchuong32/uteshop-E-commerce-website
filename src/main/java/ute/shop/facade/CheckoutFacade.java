package ute.shop.facade;

import ute.shop.entity.*;
import ute.shop.factory.*;
import ute.shop.service.*;
import ute.shop.service.impl.*;
import ute.shop.service.payment.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class CheckoutFacade {

    private final ICartItemService         cartService    = new CartItemServiceImpl();
    private final IOrderService            orderService   = new OrderServiceImpl();
    private final ICarrierService          carrierService = new CarrierServiceImpl();
    private final IShippingAddressService  addressService = new ShippingAddressServiceImpl();
    private final IShopService             shopService    = new ShopServiceImpl();

    /**
     * Thực hiện toàn bộ quy trình đặt hàng.
     * @return URL để redirect sang cổng thanh toán
     */
    public String placeOrder(User user,
                             String[] selectedIds,
                             String addressIdStr,
                             String carrierIdStr,
                             String payment) throws Exception {

        // Bước 1: Lấy địa chỉ giao hàng
        ShippingAddress address = addressService.getById(Integer.parseInt(addressIdStr));

        // Bước 2: Tính phí ship
        BigDecimal shippingFee = new BigDecimal("30000");
        Carrier carrier = null;
        if (carrierIdStr != null && !carrierIdStr.isEmpty()) {
            carrier = carrierService.findById(Integer.parseInt(carrierIdStr));
            if (carrier != null) shippingFee = carrier.getCarrierFee();
        }

        // Bước 3: Lấy danh sách sản phẩm đã chọn, gom theo shop
        List<CartItem> selectedItems = cartService.getCartByIds(selectedIds);
        Map<Integer, List<CartItem>> itemsByShop = selectedItems.stream()
            .collect(Collectors.groupingBy(
                i -> i.getProductVariant().getProduct().getShop().getShopId()));

        // Bước 4: Tạo đơn hàng cho từng shop
        StringBuilder orderIds   = new StringBuilder();
        StringBuilder shopNames  = new StringBuilder();
        BigDecimal    totalAll   = BigDecimal.ZERO;

        for (Map.Entry<Integer, List<CartItem>> entry : itemsByShop.entrySet()) {
            List<CartItem> shopItems = entry.getValue();

            BigDecimal subtotal = shopItems.stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal total = subtotal.add(shippingFee);
            totalAll = totalAll.add(total);

            Order order = buildOrder(user, entry.getKey(), payment, address, total, shopItems);
            Order saved = orderService.save(order);

            if (saved == null) throw new RuntimeException("Lỗi lưu đơn hàng shopId=" + entry.getKey());

            // Xóa cart sau khi đặt thành công
            shopItems.forEach(ci -> cartService.removeFromCart(ci.getCartItemId()));

            if (orderIds.length() > 0) { orderIds.append(","); shopNames.append(","); }
            orderIds.append(saved.getOrderId());
            shopNames.append(saved.getShop().getName());
        }

        // Bước 5: Chọn cổng thanh toán và trả URL
        PaymentContext context = new PaymentContext(
            totalAll, orderIds.toString(), shopNames.toString(), "");

        PaymentAbstractFactory factory = PaymentFactoryProducer.getFactory(payment.toUpperCase());
        if (factory == null) throw new Exception("Phương thức thanh toán không hợp lệ: " + payment);

        return factory.createPaymentService().createPaymentUrl(context);
    }

    // Hàm nội bộ: tạo đối tượng Order từ các thông tin đầu vào
    private Order buildOrder(User user, int shopId, String payment,
                             ShippingAddress address, BigDecimal total,
                             List<CartItem> items) {
        Order order = new Order();
        order.setUser(user);
        order.setShop(shopService.getById(shopId));
        order.setPaymentMethod(payment.toUpperCase());
        order.setStatus("VNPAY".equals(payment.toUpperCase()) ? "Chờ thanh toán VNPAY" : "Mới");
        order.setCreatedAt(new Date());
        order.setShippingAddress(address);
        order.setTotalAmount(total);

        for (CartItem ci : items) {
            OrderDetail od = new OrderDetail();
            od.setOrder(order);
            od.setProductVariant(ci.getProductVariant());
            od.setQuantity(ci.getQuantity());
            od.setPrice(ci.getPrice());
            order.getOrderDetails().add(od);
        }
        return order;
    }
}