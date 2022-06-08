package dongduk.cs.pulpul.dao;

import java.util.List;

import dongduk.cs.pulpul.domain.Review;

public interface ReviewDao {
	
	// 품목 id로 리뷰 목록 조회
	List<Review> findReviewByListItem(String itemId);
	
	// 리뷰 이미지 조회
	String findReviewImage(int reviewId);
	
	// 리뷰할 주문 id 1개 조회
	int findOrderIdByNotReview(String itemId, String memberId);
	
	// 주문내역의 상품에 리뷰한 내역이 있는지 확인
	boolean isExistReview(String itemId, int orderId);
	
	// 리뷰 생성
	void createReview(Review review);
	
	// 리뷰 이미지 생성
	void createReviewImage(Review review);
	
}
