package dongduk.cs.pulpul.dao;

import java.util.List;

import dongduk.cs.pulpul.domain.Review;

public interface ReviewDao {
	
	// 품목 id로 리뷰 목록 조회
	List<Review> findReviewByListItem(String itemId);
	
	// 리뷰할 주문 id 1개 조회
	int findOrderIdByNotReview(String itemId, String memberId);
	
	// 리뷰 생성
	int createReview(Review review);
	
	// 리뷰 이미지 생성
	boolean createReviewImages(List<String> imageUrlList, String memberId);
	
}
