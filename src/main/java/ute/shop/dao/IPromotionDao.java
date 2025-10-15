package ute.shop.dao;

import java.util.List;

import ute.shop.entity.Promotion;

public interface IPromotionDao {
	Promotion findById(int id);

	List<Promotion> findAll();

	List<Promotion> findValidByShop(int shopId);
	// Lấy khuyến mãi hợp lệ theo sản phẩm (nếu sản phẩm có khuyến mãi riêng)
    List<Promotion> findValidByProduct(int productId);

    // Lấy tất cả khuyến mãi hợp lệ (nếu cần)
    List<Promotion> findAllValid();

	void insert(Promotion p);

	void update(Promotion p);

	void delete(int id);

	long countAll();
}
