package ute.shop.dao;

import ute.shop.entity.ProductImage;
import java.util.List;

public interface ProductImageDao {
    List<ProductImage> findByProductId(Long productId);
    ProductImage findById(Long id);
    ProductImage save(ProductImage image);
    void delete(Long id);
}
