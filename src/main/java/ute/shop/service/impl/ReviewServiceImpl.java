package ute.shop.service.impl;

import ute.shop.dao.ReviewDao;
import ute.shop.dao.impl.ReviewDaoImpl;
import ute.shop.entity.Review;
import ute.shop.service.IReviewService;

import java.util.List;

public class ReviewServiceImpl implements IReviewService {

    private ReviewDao reviewDao = new ReviewDaoImpl();

    @Override
    public List<Review> getByProductId(Integer productId) {
        return reviewDao.findByProductId(productId);
    }

    @Override
    public void addReview(Review review) {
        reviewDao.insert(review);
    }
}
