package dongduk.cs.pulpul.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public boolean createReviewImages(List<String> imageUrlList, String memberId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("memberId", memberId);
		param.put("imageUrlList", imageUrlList);
		int ck = reviewMapper.insertReviewImages(param);
		if (ck < 0) return false;
		return true;
	}

}
