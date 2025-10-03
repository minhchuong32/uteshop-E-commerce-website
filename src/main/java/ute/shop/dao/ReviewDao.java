package ute.shop.dao;

import ute.shop.entity.Review;
import java.util.List;

public interface ReviewDao {
    List<Review> findByProductId(Integer productId);
    void insert(Review review);
}
