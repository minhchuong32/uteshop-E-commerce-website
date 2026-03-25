package ute.shop.service.proxy;

import ute.shop.entity.Product;
import ute.shop.entity.ProductVariant;
import ute.shop.service.IProductService;

import java.util.List;

/**
 * PROXY PATTERN:
 * - Bọc ngoài IProductService thật
 * - Tự động log thời gian + kết quả mà KHÔNG sửa code gốc
 */
public class ProductServiceLoggingProxy implements IProductService {

    // Real Subject — service thật bên trong
    private final IProductService realService;

    public ProductServiceLoggingProxy(IProductService realService) {
        this.realService = realService;
    }

    // ===== Phương thức được proxy (có log) =====

    @Override
    public List<Product> filterProducts(Integer categoryId, Double minPrice,
                                        Double maxPrice, String sortBy,
                                        int page, int size) {
        long start = System.currentTimeMillis();
        List<Product> result = realService.filterProducts(
                categoryId, minPrice, maxPrice, sortBy, page, size);
        long elapsed = System.currentTimeMillis() - start;

        System.out.printf("[PROXY] filterProducts | category=%s | page=%d" +
                          " | results=%d | time=%dms%n",
                categoryId, page, result.size(), elapsed);
        return result;
    }

    @Override
    public List<Product> searchByKeyword(String keyword, int page, int size) {
        long start = System.currentTimeMillis();
        List<Product> result = realService.searchByKeyword(keyword, page, size);
        long elapsed = System.currentTimeMillis() - start;

        System.out.printf("[PROXY] searchByKeyword | keyword='%s'" +
                          " | results=%d | time=%dms%n",
                keyword, result.size(), elapsed);
        return result;
    }

    // ===== Các phương thức còn lại ủy quyền thẳng =====

    @Override public List<Product> findAll() { return realService.findAll(); }
    @Override public Product findById(Integer id) { return realService.findById(id); }
    @Override public Product findByIdWithVariants(Integer id) { return realService.findByIdWithVariants(id); }
    @Override public List<Product> findTopProducts(int limit) { return realService.findTopProducts(limit); }
    @Override public long countAll() { return realService.countAll(); }
    @Override public List<Product> findByPage(int p, int s) { return realService.findByPage(p, s); }
    @Override public Product findById(int id) { return realService.findById(id); }
    @Override public List<Product> findByShopId(int shopId) { return realService.findByShopId(shopId); }
    @Override public void save(Product p) { realService.save(p); }
    @Override public void update(Product p) { realService.update(p); }
    @Override public void delete(int id) { realService.delete(id); }
    @Override public List<Product> getProductsByCategory(Integer cid) { return realService.getProductsByCategory(cid); }
    @Override public List<Product> findTopByCategoryId(Integer cid, int l) { return realService.findTopByCategoryId(cid, l); }
    @Override public long countByKeyword(String kw) { return realService.countByKeyword(kw); }
    @Override public long countFilterProducts(Integer cid, Double min, Double max) { return realService.countFilterProducts(cid, min, max); }
    @Override public List<Product> findByCategoryAndShop(int cid, int sid) { return realService.findByCategoryAndShop(cid, sid); }
    @Override public Product findByIdWithVariants(int id) { return realService.findByIdWithVariants(id); }
    @Override public long getTotalProducts(int shopId) { return realService.getTotalProducts(shopId); }
    @Override public List<Object[]> getTopSellingProducts(int shopId, int l) { return realService.getTopSellingProducts(shopId, l); }
    @Override public List<Object[]> getProductCountByCategory(int sid) { return realService.getProductCountByCategory(sid); }
    @Override public List<Object[]> findBestSellingProducts(int l) { return realService.findBestSellingProducts(l); }
    @Override public Product findById_fix(Integer id) { return realService.findById_fix(id); }
    @Override public void deleteExtraImage(Long id) { realService.deleteExtraImage(id); }
    @Override public ProductVariant findVariantById(Long id) { return realService.findVariantById(id); }
    @Override public List<Product> searchByKeywordAndShop(String kw, int sid, int p, int s) { return realService.searchByKeywordAndShop(kw, sid, p, s); }
    @Override public long countByKeywordAndShop(String kw, int sid) { return realService.countByKeywordAndShop(kw, sid); }
    @Override public List<Product> filterProductsByShop(int sid, Integer cid, Double min, Double max, String sort, int p, int s) { return realService.filterProductsByShop(sid, cid, min, max, sort, p, s); }
    @Override public long countFilterProductsByShop(int sid, Integer cid, Double min, Double max) { return realService.countFilterProductsByShop(sid, cid, min, max); }
}