package dongduk.cs.pulpul.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dongduk.cs.pulpul.dao.mybatis.mapper.ReviewMapper;
import dongduk.cs.pulpul.domain.Review;

@Component
public class ReviewDaoImpl implements ReviewDao {

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
	public int createReview(Review review) {
		return reviewMapper.insertReview(review);
	}

	@Override
	public boolean createReviewImage(Review review) {
		int ck = reviewMapper.insertReviewImage(review);
		if (ck > 0) return true;
		return false;
	}

}
