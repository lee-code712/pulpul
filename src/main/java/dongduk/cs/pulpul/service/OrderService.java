package dongduk.cs.pulpul.service;

import java.util.List;

import dongduk.cs.pulpul.domain.Cart;
import dongduk.cs.pulpul.domain.Orders;

public interface OrderService {
	
	// 장바구니 조회
	Cart getCart(String memberId);
	
	// 장바구니에 상품 추가
	boolean addCartItem(Cart cart);
	
	// 장바구니에서 상품 삭제
	boolean deleteCartItem(String memberId, String itemId);
	
	// 장바구니에서 특정 마켓의 상품 목록 삭제
	boolean deleteCartItemByMarket(String memberId, int marketId);
	
	// 회원 id로 주문 목록 조회
	List<Orders> getOrderListByMember(String memberId);
	
	// 주문 상세내역 조회
	Orders getOrder(int orderId);
	
	// 주문
	boolean order(Orders order);
	
	// 주문에 대한 운송장번호 입력
	boolean changeTrackingNumber(Orders order);
	
	// 주문 취소
	boolean cancelOrder(Orders order);
	
}
