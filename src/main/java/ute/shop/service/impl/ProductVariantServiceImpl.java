package ute.shop.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ute.shop.dao.IProductDao;
import ute.shop.dao.IProductVariantDao;
import ute.shop.dao.impl.ProductDaoImpl;
import ute.shop.dao.impl.ProductVariantDaoImpl;
import ute.shop.entity.Product;
import ute.shop.entity.ProductVariant;
import ute.shop.service.IProductVariantService;

public class ProductVariantServiceImpl implements IProductVariantService {

    private final IProductVariantDao variantDao = new ProductVariantDaoImpl();
    private final IProductDao productDao = new ProductDaoImpl();

    @Override
    public ProductVariant findById(Integer variantId) {
        return variantDao.findById(variantId);
    }

    @Override
    public Map<String, List<String>> getOptionMapByProductId(Integer productId) {
        Product product = productDao.findByIdWithVariants(productId);
        Map<String, Set<String>> map = new LinkedHashMap<>();

        for (ProductVariant v : product.getVariants()) {
            String optionName = v.getOptionName();
            String optionValue = v.getOptionValue();
            map.computeIfAbsent(optionName, k -> new LinkedHashSet<>()).add(optionValue);
        }

        // Chuyển Set -> List để giữ thứ tự
        Map<String, List<String>> result = new LinkedHashMap<>();
        for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
            result.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return result;
    }

    @Override
    public ProductVariant findByOptions(Integer productId, Map<String, Object> selectedOptions) {
        return variantDao.findByOptions(productId, selectedOptions);
    }

    //  Thêm mới variant
    @Override
    public void save(ProductVariant variant) {
        variantDao.save(variant);
    }

    //  Cập nhật variant
    @Override
    public void update(ProductVariant variant) {
        variantDao.update(variant);
    }

    // Xóa variant theo id
    @Override
    public void delete(Integer variantId) {
        variantDao.delete(variantId);
    }

    //  Xóa tất cả variant theo productId (khi xóa hoặc cập nhật sản phẩm)
    @Override
    public void deleteByProductId(Integer productId) {
        variantDao.deleteByProductId(productId);
    }

    //  Lấy danh sách variant của 1 sản phẩm
    @Override
    public List<ProductVariant> findByProductId(Integer productId) {
        return variantDao.findByProductId(productId);
    }
}
