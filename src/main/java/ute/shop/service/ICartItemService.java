package ute.shop.service;

import ute.shop.entity.CartItem;
import ute.shop.entity.User;
import ute.shop.entity.Product;
import ute.shop.entity.ProductVariant;

import java.util.List;

public interface ICartItemService {
    boolean addToCart(User user, ProductVariant variant, int quantity);
    boolean updateQuantity(User user, ProductVariant variant, int quantityChange);
    boolean removeFromCart(Integer cartItemId);
    List<CartItem> getCartByUser(User user);
   
}
