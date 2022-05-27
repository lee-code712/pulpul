package dongduk.cs.pulpul.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import dongduk.cs.pulpul.domain.Review;

@Mapper
public interface ReviewMapper {
	
	List<Review> selectReviewByItem(String itemId);
	
	int selectOrderIdByNotReview(@Param("itemId") String itemId, 
			@Param("memberId") String memberId);
	
	int insertReview(Review review);
	
	int insertReviewImages(Map<String, Object> reviewImages);
	
}
