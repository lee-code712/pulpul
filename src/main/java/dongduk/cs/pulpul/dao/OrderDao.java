package dongduk.cs.pulpul.dao;

import java.util.List;

import dongduk.cs.pulpul.domain.Cart;
import dongduk.cs.pulpul.domain.CartItem;
import dongduk.cs.pulpul.domain.Order;

public interface OrderDao {
	
	// 회원 카트 조회
	Cart findCartByMember(String memberId);
	
	// 회원 카트 상품 수 조회
	int findNumberOfCartItemByMember(String memberId);
	
	// 회원 장바구니에 특정 상품이 있는지 확인
	boolean isExistItem(String memberId, String goodsId);
	
	// 카트 상품 생성
	boolean createCartItem(String memberId, CartItem cartItem);
	
	// 품목 id로 카트 상품 삭제
	boolean deleteOneCartItem(String memberId, String itemId);
	
	// 마켓 id로 카트 상품 목록 삭제
	int deleteCartItemByMarket(String memberId, int marketId);
	
	// 주문 id로 주문 조회
	Order findOrder(int orderId);
	
	// 회원 id로 주문 목록 조회
	List<Order> findOrderByMember(String memberId, String identity);
	
	// 주문 생성
	int createOrder(Order orders);
	
	// 주문 상품 목록 생성
	boolean createOrderGoods(Cart cart);
	
	// 주문 운송장번호 입력
	boolean changeTrackingNumber(int orderId, String trackingNumber);
	
	// 주문 상태 변경
	boolean changeOrderStatus(int orderId, int orderStatus);
	
}
