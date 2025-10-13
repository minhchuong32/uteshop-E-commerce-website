package ute.shop.dao;

import java.util.List;
import java.util.Map;

import ute.shop.entity.ProductVariant;

public interface IProductVariantDao {
	ProductVariant findById(Integer variantId);

	ProductVariant findByOptions(Integer productId, Map<String, Object> selectedOptions);

	void save(ProductVariant variant);

	void update(ProductVariant variant);

	void delete(Integer variantId);

	void deleteByProductId(Integer productId);

	List<ProductVariant> findByProductId(Integer productId);
}
