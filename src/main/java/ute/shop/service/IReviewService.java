package ute.shop.service;

import ute.shop.entity.Review;
import java.util.List;

public interface IReviewService {
    List<Review> getByProductId(Integer productId);
    void addReview(Review review);
}
