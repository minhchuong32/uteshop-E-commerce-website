package ute.shop.service.impl;

import ute.shop.dao.ICartItemDao;
import ute.shop.dao.impl.CartItemDaoImpl;
import ute.shop.entity.CartItem;
import ute.shop.entity.User;
import ute.shop.entity.Product;
import ute.shop.entity.ProductVariant;
import ute.shop.service.ICartItemService;

import java.util.List;

public class CartItemServiceImpl implements ICartItemService {

    private final ICartItemDao cartItemDao = new CartItemDaoImpl();

    @Override
    public boolean addToCart(User user, ProductVariant variant, int quantity) {
    	// Kiểm tra xem user đã có item này trong giỏ chưa
        CartItem existing = cartItemDao.findByUserAndVariant(user, variant);
        if (existing != null) {
            // Cộng dồn số lượng
            existing.setQuantity(existing.getQuantity() + quantity);
            return cartItemDao.update(existing);
        } else {
            // Tạo cart item mới với giá hiện tại của variant
            CartItem newItem = new CartItem();
            newItem.setUser(user);
            newItem.setProductVariant(variant);
            newItem.setQuantity(quantity);
            newItem.setPrice(variant.getPrice());
            return cartItemDao.insert(newItem);
        }
    }

    @Override
    public boolean updateQuantity(User user, ProductVariant variant, int quantityChange) {
    	CartItem item = cartItemDao.findByUserAndVariant(user, variant);
        if (item != null) {
            int newQty = item.getQuantity() + quantityChange;
            if (newQty <= 0) {
                return cartItemDao.delete(item.getCartItemId());
            } else {
                item.setQuantity(newQty);
                return cartItemDao.update(item);
            }
        }
        return false;
    }

    @Override
    public boolean removeFromCart(Integer cartItemId) {
        return cartItemDao.delete(cartItemId);
    }

    @Override
    public List<CartItem> getCartByUser(User user) {
        return cartItemDao.findByUser(user);
    }
}
