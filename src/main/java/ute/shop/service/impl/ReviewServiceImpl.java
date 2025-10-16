package ute.shop.service.impl;

import ute.shop.dao.IReviewDao;
import ute.shop.dao.impl.ReviewDaoImpl;
import ute.shop.entity.Product;
import ute.shop.entity.Review;
import ute.shop.entity.User;
import ute.shop.service.IReviewService;

import java.util.List;

public class ReviewServiceImpl implements IReviewService {

    private IReviewDao reviewDao = new ReviewDaoImpl();

    @Override
    public List<Review> getByProductId(Integer productId) {
        return reviewDao.findByProductId(productId);
    }

    @Override
    public void addReview(Review review) {
        reviewDao.insert(review);
    }

	@Override
	public boolean hasReviewed(User user, Product product) {
		return reviewDao.hasReviewed(user, product);
	}

	@Override
	public void updateReview(Review review) {
		reviewDao.update(review);
	}

	@Override
	public void deleteReview(Integer id) {
		reviewDao.delete(id);
	}

	@Override
	public Review getById(Integer id) {
		return reviewDao.findById(id);
	}

	@Override
	public Review getByUserAndProduct(User user, Product product) {
		 return reviewDao.findByUserAndProduct(user, product);
	}

	@Override
	public List<Review> getByProduct(Product product) {
		 return reviewDao.findByProduct(product);
	}
}
