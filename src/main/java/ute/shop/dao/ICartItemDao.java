package ute.shop.dao;

import ute.shop.entity.CartItem;
import ute.shop.entity.User;
import ute.shop.entity.ProductVariant;

import java.util.List;

public interface ICartItemDao {
    boolean insert(CartItem item);
    boolean update(CartItem item);
    boolean delete(Integer cartItemId);
    CartItem findById(Integer cartItemId);
    List<CartItem> getCartByIds(String[] ids);

    List<CartItem> findByUser(User user);
//    CartItem findByUserAndProduct(User user, Product product);
    CartItem findByUserAndVariant(User user, ProductVariant variant);
}
