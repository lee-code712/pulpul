package dongduk.cs.pulpul.dao.mybatis.mapper;

import java.util.List;

import dongduk.cs.pulpul.domain.Review;

public interface ReviewMapper {
	
	List<Review> selectReviewByItem(String itemId);
	
	int selectExistReview(int orderId);
	
	int insertReview(Review review);
	
	int insertReviewImage(Review review);
	
}
