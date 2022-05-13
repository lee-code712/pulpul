package dongduk.cs.pulpul.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import dongduk.cs.pulpul.domain.Review;

@Mapper
public interface ReviewMapper {
	
	List<Review> selectReviewByItem(String itemId);
	
	int selectExistReview(int orderId);
	
	int insertReview(Review review);
	
	int insertReviewImage(Review review);
	
}
