package ute.shop.service.impl;

import ute.shop.dao.ICartItemDao;
import ute.shop.dao.impl.CartItemDaoImpl;
import ute.shop.entity.CartItem;
import ute.shop.entity.User;
import ute.shop.entity.Product;
import ute.shop.service.ICartItemService;

import java.util.List;

public class CartItemServiceImpl implements ICartItemService {

    private final ICartItemDao cartItemDao = new CartItemDaoImpl();

    @Override
    public boolean addToCart(User user, Product product, int quantity) {
        CartItem existing = cartItemDao.findByUserAndProduct(user, product);
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            return cartItemDao.update(existing);
        } else {
            CartItem newItem = new CartItem(null, user, product, quantity);
            return cartItemDao.insert(newItem);
        }
    }

    @Override
    public boolean updateQuantity(User user, Product product, int quantityChange) {
        CartItem item = cartItemDao.findByUserAndProduct(user, product);
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
