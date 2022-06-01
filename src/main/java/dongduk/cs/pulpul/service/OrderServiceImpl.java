package dongduk.cs.pulpul.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dongduk.cs.pulpul.dao.ItemDao;
import dongduk.cs.pulpul.dao.MemberDao;
import dongduk.cs.pulpul.dao.OrderDao;
import dongduk.cs.pulpul.domain.Cart;
import dongduk.cs.pulpul.domain.CartItem;
import dongduk.cs.pulpul.domain.Order;
import dongduk.cs.pulpul.service.exception.AddCartException;

@Service
public class OrderServiceImpl implements OrderService {
	private final OrderDao orderDao;
	private final ItemDao itemDao;
	private final MemberDao memberDao;

	@Autowired
	public OrderServiceImpl(OrderDao orderDao, ItemDao itemDao,
			MemberDao memberDao) {
		this.orderDao = orderDao;
		this.itemDao = itemDao;
		this.memberDao = memberDao;
	}

	// 회원의 장바구니 목록 조회
	public Cart getCart(String memberId) {
		return orderDao.findCartByMember(memberId);
	}
	
	// 회원의 장바구니 상품 수 조회
	public int getNumberOfCartItemByMember(String memberId) {
		return orderDao.findNumberOfCartItemByMember(memberId);
	}

	// 장바구니 목록 추가
	public boolean addCartItem(String memberId, CartItem cartItem) throws AddCartException {
		
		String goodsId = cartItem.getGoods().getItem().getId();

		int remainQuantity = itemDao.findRemainQuantityByGoods(goodsId);
		if (remainQuantity == 0) {
			System.out.println("남은 수량 없음");
			throw new AddCartException("남은 상품이 모두 팔렸습니다.");
		}
		else if (cartItem.getQuantity() > remainQuantity) {
			System.out.println("남은 수량 초과");
			throw new AddCartException("남은 상품보다 많은 수량을 선택했습니다. 수량을 다시 선택해주세요.");
		}
		else if (orderDao.isExistItem(memberId, goodsId)) {
			System.out.println("장바구니에 존재하는 항목");
			throw new AddCartException("이미 장바구니에 존재하는 상품입니다.");
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
		// 주문 정보 저장
		boolean successed = orderDao.createOrder(order);
		if (!successed) return false;
		
		// 주문 상품 저장
		int quantity = 0;
		List<Map<String, Object>> orderGoodsList = new ArrayList<Map<String, Object>>();
		for (CartItem cartItem : order.getGoodsList()) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("orderId", order.getId());
			param.put("cartItem", cartItem);
			orderGoodsList.add(param);
			
			// 수량 합계 계산
			quantity += cartItem.getQuantity();
			// 장바구니에서 해당 상품 삭제
			successed = deleteCartItem(order.getBuyer().getId(), cartItem.getGoodsId());
			if (!successed) return false;
		}
		successed = orderDao.createOrderGoods(orderGoodsList);
		if (!successed) return false;

		// 회원 포인트 변경
		successed = memberDao.changePoint(order.getBuyer().getId(), -1, order.getUsedPoint());
		if (!successed) return false;
		
		// 남은 수량 변경
		successed = itemDao.changeRemainQuantityByOrderStatus(order.getId(), 1, quantity);
		if (!successed) return false;
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
