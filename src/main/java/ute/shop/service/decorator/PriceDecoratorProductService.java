package ute.shop.service.decorator;

import ute.shop.entity.Product;
import ute.shop.entity.ProductVariant;
import ute.shop.service.IProductService;
import ute.shop.service.impl.ProductServiceImpl;

import java.math.BigDecimal;
import java.util.List;

public class PriceDecoratorProductService implements IProductService {

    // Bọc service gốc bên trong
    private final IProductService wrapped;

    public PriceDecoratorProductService() {
        this.wrapped = new ProductServiceImpl();
    }

    // Hàm tiện ích nội bộ: gán giá min-variant cho từng sản phẩm
    private List<Product> attachMinPrice(List<Product> products) {
        for (Product p : products) {
            if (p.getVariants() != null && !p.getVariants().isEmpty()) {
                ProductVariant minVariant = p.getVariants().stream()
                    .min((v1, v2) -> v1.getPrice().compareTo(v2.getPrice()))
                    .orElse(p.getVariants().get(0));
                p.setPrice(minVariant.getPrice());
            } else {
                p.setPrice(BigDecimal.ZERO);
            }
        }
        return products;
    }

    // Các phương thức trả về List<Product> → tự động gán giá
    @Override
    public List<Product> filterProducts(Integer categoryId, Double minPrice, Double maxPrice,
                                        String sortBy, int page, int size) {
        return attachMinPrice(wrapped.filterProducts(categoryId, minPrice, maxPrice, sortBy, page, size));
    }

    @Override
    public List<Product> searchByKeyword(String keyword, int page, int size) {
        return attachMinPrice(wrapped.searchByKeyword(keyword, page, size));
    }

    @Override
    public List<Product> filterProductsByShop(int shopId, Integer categoryId, Double minPrice,
                                               Double maxPrice, String sortBy, int page, int size) {
        return attachMinPrice(
            wrapped.filterProductsByShop(shopId, categoryId, minPrice, maxPrice, sortBy, page, size));
    }

    @Override
    public List<Product> searchByKeywordAndShop(String keyword, int shopId, int page, int size) {
        return attachMinPrice(wrapped.searchByKeywordAndShop(keyword, shopId, page, size));
    }

    // Tất cả các phương thức còn lại — ủy quyền thẳng cho wrapped
    @Override
    public List<Product> findAll() { return wrapped.findAll(); }

    @Override
    public Product findById(Integer id) { return wrapped.findById(id); }

    @Override
    public Product findByIdWithVariants(Integer id) { return wrapped.findByIdWithVariants(id); }

    @Override
    public long countAll() { return wrapped.countAll(); }

    @Override
    public long countByKeyword(String keyword) { return wrapped.countByKeyword(keyword); }

    @Override
    public long countFilterProducts(Integer categoryId, Double minPrice, Double maxPrice) {
        return wrapped.countFilterProducts(categoryId, minPrice, maxPrice);
    }

    @Override
    public long countFilterProductsByShop(int shopId, Integer categoryId,
                                          Double minPrice, Double maxPrice) {
        return wrapped.countFilterProductsByShop(shopId, categoryId, minPrice, maxPrice);
    }

    @Override
    public long countByKeywordAndShop(String keyword, int shopId) {
        return wrapped.countByKeywordAndShop(keyword, shopId);
    }

    // ... các phương thức khác ủy quyền tương tự
    @Override public List<Product> findTopProducts(int limit) { return wrapped.findTopProducts(limit); }
    @Override public List<Product> findByPage(int page, int pageSize) { return wrapped.findByPage(page, pageSize); }
    @Override public List<Product> findByShopId(int shopId) { return wrapped.findByShopId(shopId); }
    @Override public Product findById(int productId) { return wrapped.findById(productId); }
    @Override public void save(Product product) { wrapped.save(product); }
    @Override public void update(Product product) { wrapped.update(product); }
    @Override public void delete(int productId) { wrapped.delete(productId); }
    @Override public List<Product> getProductsByCategory(Integer categoryId) { return wrapped.getProductsByCategory(categoryId); }
    @Override public List<Product> findTopByCategoryId(Integer categoryId, int limit) { return wrapped.findTopByCategoryId(categoryId, limit); }
    @Override public List<Product> findByCategoryAndShop(int categoryId, int shopId) { return wrapped.findByCategoryAndShop(categoryId, shopId); }
    @Override public Product findByIdWithVariants(int productId) { return wrapped.findByIdWithVariants(productId); }
    @Override public long getTotalProducts(int shopId) { return wrapped.getTotalProducts(shopId); }
    @Override public List<Object[]> getTopSellingProducts(int shopId, int limit) { return wrapped.getTopSellingProducts(shopId, limit); }
    @Override public List<Object[]> getProductCountByCategory(int shopId) { return wrapped.getProductCountByCategory(shopId); }
    @Override public List<Object[]> findBestSellingProducts(int limit) { return wrapped.findBestSellingProducts(limit); }
    @Override public Product findById_fix(Integer id) { return wrapped.findById_fix(id); }
    @Override public void deleteExtraImage(Long imageId) { wrapped.deleteExtraImage(imageId); }
    @Override public ute.shop.entity.ProductVariant findVariantById(Long variantId) { return wrapped.findVariantById(variantId); }
}