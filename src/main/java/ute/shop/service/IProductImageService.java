package ute.shop.service;

import ute.shop.entity.Product;
import ute.shop.entity.ProductImage;

import java.util.List;

public interface IProductImageService {
    List<ProductImage> getImagesByProduct(Long productId);
    ProductImage getImageById(Long id);
    ProductImage addImage(Product product, String imageUrl, boolean isMain);
    void deleteImage(Long id);
	ProductImage update(ProductImage image);
}
