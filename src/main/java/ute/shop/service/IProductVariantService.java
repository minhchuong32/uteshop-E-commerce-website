package ute.shop.service;

import java.util.List;
import java.util.Map;
import ute.shop.entity.ProductVariant;

public interface IProductVariantService {

    ProductVariant findById(Integer variantId);

    Map<String, List<String>> getOptionMapByProductId(Integer productId);

    ProductVariant findByOptions(Integer productId, Map<String, Object> selectedOptions);

    void save(ProductVariant variant);

    void update(ProductVariant variant);

    void delete(Integer variantId);

    void deleteByProductId(Integer productId);

    List<ProductVariant> findByProductId(Integer productId);
}
