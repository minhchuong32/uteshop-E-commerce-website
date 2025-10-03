package ute.shop.service.impl;

import ute.shop.dao.ProductImageDao;
import ute.shop.dao.impl.ProductImageDaoImpl;
import ute.shop.entity.Product;
import ute.shop.entity.ProductImage;
import ute.shop.service.IProductImageService;

import java.util.List;

public class ProductImageServiceImpl implements IProductImageService {

    private final ProductImageDao productImageDAO = new ProductImageDaoImpl();

    @Override
    public List<ProductImage> getImagesByProduct(Long productId) {
        return productImageDAO.findByProductId(productId);
    }

    @Override
    public ProductImage getImageById(Long id) {
        return productImageDAO.findById(id);
    }

    @Override
    public ProductImage addImage(Product product, String imageUrl, boolean isMain) {
        ProductImage img = ProductImage.builder()
                .product(product)
                .imageUrl(imageUrl)
                .isMain(isMain)
                .build();
        return productImageDAO.save(img);
    }

    @Override
    public void deleteImage(Long id) {
        productImageDAO.delete(id);
    }
}
