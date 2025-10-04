package ute.shop.service.impl;

import ute.shop.dao.IProductDao;
import ute.shop.dao.impl.ProductDaoImpl;
import ute.shop.entity.Product;
import ute.shop.service.IProductService;

import java.util.List;

public class ProductServiceImpl implements IProductService {

	private final IProductDao productDao = new ProductDaoImpl();

	@Override
	public List<Product> findAll() {
		return productDao.findAll();
	}

	@Override
	public Product findById(Integer id) {
		return productDao.findById(id);
	}

	@Override
	public List<Product> findTopProducts(int limit) {
		return productDao.findTopProducts(limit);
	}

	// Đếm tổng số sản phẩm
	@Override
	public long countAll() {
		return productDao.countAll();
	}

	// Lấy sản phẩm phân trang
	@Override
	public List<Product> findByPage(int page, int pageSize) {
		return productDao.findByPage(page, pageSize);
	}

	@Override
	public Product findById(int productId) {
		return productDao.findById(productId);
	}

	@Override
	public List<Product> findByShopId(int shopId) {
		return productDao.findByShopId(shopId);
	}

	@Override
	public void save(Product product) {
		productDao.save(product);
	}

	@Override
	public void update(Product product) {
		productDao.update(product);
	}

	@Override
	public void delete(int productId) {
		productDao.delete(productId);

	}

	@Override
	public List<Product> getProductsByCategory(Integer categoryId) {
		return productDao.findByCategoryId(categoryId);
	}
}
