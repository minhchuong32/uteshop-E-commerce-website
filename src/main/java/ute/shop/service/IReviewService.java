package ute.shop.service;

import ute.shop.entity.Product;
import ute.shop.entity.Review;
import ute.shop.entity.User;

import java.util.List;

public interface IReviewService {
    List<Review> getByProductId(Integer productId);
    void addReview(Review review);
    void updateReview(Review review);
    void deleteReview(Integer id);
    Review getById(Integer id);
    Review getByUserAndProduct(User user, Product product);
    List<Review> getByProduct(Product product);
    boolean hasReviewed(User user, Product product);
}
