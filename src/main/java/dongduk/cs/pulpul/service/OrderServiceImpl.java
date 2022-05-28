package dongduk.cs.pulpul.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dongduk.cs.pulpul.dao.ItemDao;
import dongduk.cs.pulpul.dao.OrderDao;
import dongduk.cs.pulpul.domain.Cart;
import dongduk.cs.pulpul.domain.CartItem;
import dongduk.cs.pulpul.domain.Order;
import dongduk.cs.pulpul.service.exception.AddCartException;

@Service
public class OrderServiceImpl implements OrderService {
	private final OrderDao orderDao;
	private final ItemDao itemDao;

	@Autowired
	public OrderServiceImpl(OrderDao orderDao, ItemDao itemDao) {
		this.orderDao = orderDao;
		this.itemDao = itemDao;
	}

	// 회원의 장바구니 목록 조회
	public Cart getCart(String memberId) {
		return orderDao.findCartByMember(memberId);
	}
	
	public int getNumberOfCartItemByMember(String memberId) {
		return orderDao.findNumberOfCartItemByMember(memberId);
	}

	// 장바구니 목록 추가
	public boolean addCartItem(String memberId, CartItem cartItem) throws AddCartException {
		
		// 장바구니에 넣기 전 남은 수량 한번 더 확인
		int remainQuantity = itemDao.findRemainQuantityByGoods(cartItem.getGoods().getItem().getId());
		if (remainQuantity == 0) {
			System.out.println("남은 수량 없음");
			throw new AddCartException("남은 상품이 모두 팔렸습니다.");
		}
		else if (cartItem.getQuantity() > remainQuantity) {
			System.out.println("남은 수량 초과");
			throw new AddCartException("남은 상품보다 많은 수량을 선택했습니다. 수량을 다시 선택해주세요.");
		}
		
		return orderDao.createCartItem(memberId, cartItem);
		
	}

	// 장바구니 특정 상품 삭제
	public boolean deleteCartItem(String memberId, String itemId) {
		return orderDao.deleteOneCartItem(memberId, itemId);
	}

	// 장바구니 특정 마켓의 상품 삭제(삭제 개수 반환)
	public int deleteCartItemByMarket(String memberId, int marketId) {
		return orderDao.deleteCartItemByMarket(memberId, marketId);
	}

	// 회원의 주문목록 조회
	public List<Order> getOrderListByMember(String memberId, String identity) {
		return orderDao.findOrderByMember(memberId, identity);
	}

// 주문 상세정보 조회
	public Order getOrder(int orderId) {
		return orderDao.findOrder(orderId);
	}

	// 주문 생성
	public boolean order(Order order) {
		// 주문 레코드 생성에 필요한 데이터 가공
		/* int orderId = orderDao.createOrder(order);
		if (orderId == 0) return false;
		boolean success = orderDao.createOrderGoods(orderId, order.getGoodsList())
		if (!success) return false;
		for (CartItem item : order.getGoodsList()) {
			success = itemDao.changeRemainQuantityByOrder(orderId, item.getQuantity(), 1);
			if (!success) return false;
		} */
		return true;
}

// 운송장번호 입력
	public boolean changeTrackingNumber(Order order) {
		/* boolean success = orderDao.changeTrackingNumber(orderId, trackingNumber);
		if (!success)
			return false;
		return orderDao.changeOrderStatus(orderId, 2); */
		
		return false;
	}

// 주문 취소
	public boolean cancelOrder(Order order) {
		/* boolean success = orderDao.changeOrderStatus(orderId, 0);
		Order order = getOrder(orderId);
		if (order != null) {
			for (CartItem item : order.getGoodsList()) {
				success = itemDao.changeRemainQuantityByOrder(orderId, item.getQuantity(), 0);
				if (!success)
					return false;
			}
		} */
		return true;
	}

}
