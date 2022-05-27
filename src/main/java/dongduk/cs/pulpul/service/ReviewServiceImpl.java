package dongduk.cs.pulpul.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dongduk.cs.pulpul.dao.ReviewDao;
import dongduk.cs.pulpul.domain.Review;

@Service
public class ReviewServiceImpl implements ReviewService {
	private final ReviewDao reviewDao;
	
	@Autowired
	public ReviewServiceImpl(ReviewDao reviewDao) {
		this.reviewDao = reviewDao;
	}

	@Override
	public List<Review> getReviewByItem(String itemId) {
		return reviewDao.findReviewByListItem(itemId);
	}

	@Override
	public int getOrderIdOfDoNotReview(String memberId, String itemId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean addReview(Review review) {
		// TODO Auto-generated method stub
		return false;
	}

}
