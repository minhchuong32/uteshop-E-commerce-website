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
		return productDao.findByCategory(categoryId);
	}

	@Override
	public List<Product> findTopByCategoryId(Integer categoryId, int limit) {
		return productDao.findTopByCategoryId(categoryId, limit);
	}


	@Override
	public List<Product> searchByKeyword(String keyword, int page, int size) {
		return productDao.searchByKeyword(keyword, page, size);
	}

	@Override
	public long countByKeyword(String keyword) {
		return productDao.countByKeyword(keyword);
	}

	@Override
	public long countFilterProducts(Integer categoryId, Double minPrice, Double maxPrice) {
		return productDao.countFilterProducts(categoryId, minPrice, maxPrice);
	}

	@Override
	public List<Product> filterProducts(Integer categoryId, Double minPrice, Double maxPrice, String sortBy, int page,
			int size) {
		return productDao.filterProducts(categoryId, minPrice, maxPrice, sortBy, page, size);
	}
	
	@Override
    public List<Product> findByCategoryAndShop(int categoryId, int shopId) {
        return productDao.findByCategory(categoryId)
                         .stream()
                         .filter(p -> p.getShop().getShopId() == shopId)
                         .toList();
    }


	@Override
	public Product findByIdWithVariants(int productId) {
		return productDao.findByIdWithVariants(productId);
	}
	
	
	@Override
	public Product findByIdWithVariants(Integer id) {
		return productDao.findByIdWithVariants(id);
	}

	//Vendor dashboard
	public long getTotalProducts(int shopId) {
        return productDao.findByShopId(shopId).size();
    }

	@Override
	public List<Object[]> getTopSellingProducts(int shopId, int limit) {
		return productDao.getTopSellingProducts(shopId, limit);
	}

	@Override
	public List<Object[]> getProductCountByCategory(int shopId) {
		return productDao.getProductCountByCategory(shopId);
	}

	@Override
	public List<Object[]> findBestSellingProducts(int limit) {
		return productDao.findBestSellingProducts(limit);
	}


}
