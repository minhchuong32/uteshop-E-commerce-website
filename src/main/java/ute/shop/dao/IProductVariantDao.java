package ute.shop.dao;

import ute.shop.entity.ProductVariant;

public interface IProductVariantDao {
	ProductVariant findById(Integer variantId);
}
