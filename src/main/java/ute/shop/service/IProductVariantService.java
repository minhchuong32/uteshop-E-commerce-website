package ute.shop.service;

import ute.shop.entity.ProductVariant;

public interface IProductVariantService {
	ProductVariant findById(Integer variantId);
}
