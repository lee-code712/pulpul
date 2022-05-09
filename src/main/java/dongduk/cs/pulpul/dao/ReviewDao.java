package dongduk.cs.pulpul.dao;

import java.util.List;

import dongduk.cs.pulpul.domain.Review;

public interface ReviewDao {
	
	// 품목 id로 리뷰 목록 조회
	List<Review> findReviewByListItem(String itemId);
	
	// 리뷰할 주문 존재여부 확인
	boolean isExistReview(String orderId);
	
	// 리뷰 생성
	boolean createReview(Review review);
	
	// 리뷰 이미지 생성
	boolean createReviewImage(Review review);
	
}
