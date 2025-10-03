package ute.shop.test;

import ute.shop.dao.ProductImageDao;
import ute.shop.dao.impl.ProductImageDaoImpl;
import ute.shop.entity.ProductImage;

import java.util.List;

public class TestProductImageDao {
    public static void main(String[] args) {
        ProductImageDao dao = new ProductImageDaoImpl();

        // ✅ Test findById
        Long testId = 1L; // đổi ID này theo DB thực tế
        ProductImage img = dao.findById(testId);
        if (img != null) {
            System.out.println("findById(" + testId + "): " + img.getImageUrl() 
                               + " | productId=" + img.getProduct().getProductId() 
                               + " | isMain=" + img.isMain());
        } else {
            System.out.println("Không tìm thấy ảnh với id = " + testId);
        }

        // ✅ Test findByProductId
        Long productId = 1L; // đổi ID sản phẩm thực tế
        List<ProductImage> images = dao.findByProductId(productId);
        System.out.println("Sản phẩm " + productId + " có " + images.size() + " ảnh:");
        for (ProductImage pi : images) {
            System.out.println(" - " + pi.getImageUrl() 
                               + " | main=" + pi.isMain());
        }
    }
}
