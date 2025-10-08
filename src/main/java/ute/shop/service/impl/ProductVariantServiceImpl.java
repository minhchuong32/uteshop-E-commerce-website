package ute.shop.service.impl;

import ute.shop.dao.IProductVariantDao;
import ute.shop.dao.impl.ProductVariantDaoImpl;
import ute.shop.entity.ProductVariant;
import ute.shop.service.IProductVariantService;

public class ProductVariantServiceImpl implements IProductVariantService{

	private final IProductVariantDao variantDao = new ProductVariantDaoImpl();
	
	@Override
	public ProductVariant findById(Integer variantId) {
		return variantDao.findById(variantId);
	}

}
