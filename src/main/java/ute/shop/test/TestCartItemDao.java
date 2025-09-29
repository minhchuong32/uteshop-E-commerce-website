package ute.shop.test;

import ute.shop.dao.ICartItemDao;
import ute.shop.dao.impl.CartItemDaoImpl;
import ute.shop.entity.CartItem;
import ute.shop.entity.User;

import java.util.List;

public class TestCartItemDao {
    public static void main(String[] args) {
        ICartItemDao cartItemDao = new CartItemDaoImpl();

        // Giả sử User có id = 1 (khachhang1) đã tồn tại trong DB
        User user = new User();
        user.setUserId(1);

        // Gọi findByUser
        List<CartItem> cartItems = cartItemDao.findByUser(user);

        if (cartItems == null || cartItems.isEmpty()) {
            System.out.println("❌ Không tìm thấy sản phẩm nào trong giỏ hàng của user " + user.getUserId());
        } else {
            System.out.println("✅ Giỏ hàng của user " + user.getUserId() + ":");
            for (CartItem item : cartItems) {
                System.out.println("- CartItemID: " + item.getCartItemId() +
                                   ", ProductID: " + item.getProduct().getProductId() +
                                   ", ProductName: " + item.getProduct().getName() +
                                   ", Quantity: " + item.getQuantity());
            }
        }
    }
}
