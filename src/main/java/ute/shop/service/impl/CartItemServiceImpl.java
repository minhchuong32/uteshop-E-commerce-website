package ute.shop.service.impl;

import ute.shop.dao.ICartItemDao;
import ute.shop.dao.impl.CartItemDaoImpl;
import ute.shop.entity.CartItem;
import ute.shop.entity.User;
import ute.shop.entity.ProductVariant;
import ute.shop.service.ICartItemService;

import java.util.List;

public class CartItemServiceImpl implements ICartItemService {

    private final ICartItemDao cartItemDao = new CartItemDaoImpl();

    @Override
    public boolean addToCart(User user, ProductVariant variant, int quantity) {
    	if (variant == null || quantity <= 0) return false;

        CartItem existing = cartItemDao.findByUserAndVariant(user, variant);
        if (existing != null) {
            int newQty = existing.getQuantity() + quantity;
            if (newQty > variant.getStock()) newQty = variant.getStock();
            existing.setQuantity(newQty);
            return cartItemDao.update(existing);
        } else {
            if (quantity > variant.getStock()) quantity = variant.getStock();
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
                if (newQty > variant.getStock()) newQty = variant.getStock();
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

	@Override
	public List<CartItem> getCartByIds(String[] ids) {
		return cartItemDao.getCartByIds(ids);
	}
}
