package dongduk.cs.pulpul.dao.mybatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dongduk.cs.pulpul.dao.ReviewDao;
import dongduk.cs.pulpul.dao.mybatis.mapper.ReviewMapper;
import dongduk.cs.pulpul.domain.Review;

@Repository
public class MybatisReviewDao implements ReviewDao {

	@Autowired
	private ReviewMapper reviewMapper;
	
	@Override
	public List<Review> findReviewByListItem(String itemId) {
		return reviewMapper.selectReviewByItem(itemId);
	}
	
	@Override
	public String findReviewImage(int reviewId) {
		return reviewMapper.selectReviewImage(reviewId);
	}

	@Override
	public int findOrderIdByNotReview(String itemId, String memberId) {
		String orderId = reviewMapper.selectOrderIdByNotReview(itemId, memberId);
		if (orderId == null)
			return 0;
		return Integer.parseInt(orderId);
	}
	
	@Override
	public boolean isExistReview(String itemId, int orderId) {
		int ck = reviewMapper.selectReviewCountByItemId(itemId, orderId);
		if (ck > 0) return true;
		return false;
	}

	@Override
	public void createReview(Review review) {
		reviewMapper.insertReview(review);
	}

	@Override
	public void createReviewImage(Review review) {
		reviewMapper.insertReviewImage(review);
	}

}
