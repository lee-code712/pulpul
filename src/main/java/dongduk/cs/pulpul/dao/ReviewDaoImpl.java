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
	public boolean isExistReview(String orderId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createReview(Review review) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createReviewImage(Review review) {
		// TODO Auto-generated method stub
		return false;
	}

}
