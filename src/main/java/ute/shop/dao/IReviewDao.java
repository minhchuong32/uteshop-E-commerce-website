package ute.shop.dao;

import ute.shop.entity.Product;
import ute.shop.entity.Review;
import ute.shop.entity.User;

import java.util.List;

public interface IReviewDao {
    List<Review> findByProductId(Integer productId);
    void insert(Review review);
    void update(Review review);
    void delete(Integer id);
    boolean hasReviewed(User user, Product product);
    Review findById(Integer id);
    Review findByUserAndProduct(User user, Product product);
    List<Review> findByProduct(Product product);
}
