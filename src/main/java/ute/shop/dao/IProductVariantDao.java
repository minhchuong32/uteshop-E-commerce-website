package ute.shop.dao;

import java.util.Map;

import ute.shop.entity.ProductVariant;

public interface IProductVariantDao {
	ProductVariant findById(Integer variantId);
	ProductVariant findByOptions(Integer productId, Map<String, Object> selectedOptions);
}
