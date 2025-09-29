package ute.shop.service;

import ute.shop.entity.CartItem;
import ute.shop.entity.User;
import ute.shop.entity.Product;

import java.util.List;

public interface ICartItemService {
    boolean addToCart(User user, Product product, int quantity);
    boolean updateQuantity(User user, Product product, int quantity);
    boolean removeFromCart(Integer cartItemId);
    List<CartItem> getCartByUser(User user);
}
