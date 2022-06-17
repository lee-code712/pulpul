package dongduk.cs.pulpul.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dongduk.cs.pulpul.dao.ItemDao;
import dongduk.cs.pulpul.dao.MemberDao;
import dongduk.cs.pulpul.dao.OrderDao;
import dongduk.cs.pulpul.dao.ReviewDao;
import dongduk.cs.pulpul.domain.Cart;
import dongduk.cs.pulpul.domain.CartItem;
import dongduk.cs.pulpul.domain.Order;
import dongduk.cs.pulpul.service.exception.AddCartException;
import dongduk.cs.pulpul.service.exception.CancelOrderException;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private ItemDao itemDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private ReviewDao reviewDao;

	@Override
	public Cart getCart(String memberId) {
		return orderDao.findCartByMember(memberId);
	}
	
	@Override
	public int getNumberOfCartItemByMember(String memberId) {
		return orderDao.findNumberOfCartItemByMember(memberId);
	}

	@Override
	@Transactional
	public void addCartItem(String memberId, CartItem cartItem) 
			throws DataAccessException, AddCartException {
		
		String goodsId = cartItem.getGoods().getItem().getId();

		int remainQuantity = itemDao.findRemainQuantityByGoods(goodsId);
		if (remainQuantity == 0) {	// 남은 수량이 없으면 에러 메시지 전달
			System.out.println("남은 수량 없음");
			throw new AddCartException("남은 상품이 모두 팔렸습니다.");
		}
		else if (cartItem.getQuantity() > remainQuantity) {	// 선택한 수량보다 남은 수량을 초과하면 에러 메시지 전달
			System.out.println("남은 수량 초과");
			throw new AddCartException("남은 상품보다 많은 수량을 선택했습니다. 수량을 다시 선택해주세요.");
		}
		else if (orderDao.isExistItem(memberId, goodsId)) {	// 이미 장바구니에 해당 상품이 존재하면 에러 메시지 전달
			System.out.println("장바구니에 존재하는 항목");
			throw new AddCartException("이미 장바구니에 존재하는 상품입니다.");
		}
		
		orderDao.createCartItem(memberId, cartItem);	// 장바구니 레코드 생성
		
	}

	@Override
	public void deleteCartItem(String memberId, String itemId) {
		orderDao.deleteOneCartItem(memberId, itemId);
	}

	@Override
	public int deleteCartItemByMarket(String memberId, int marketId) {
		return orderDao.deleteCartItemByMarket(memberId, marketId);
	}

	@Override
	public List<Order> getOrderListByMember(String memberId, String keyword) {
		return orderDao.findOrderByMember(memberId, keyword);
	}

	@Override
	public Order getOrder(int orderId) {
		
		Order order = orderDao.findOrder(orderId);
		if (order != null) {
			for (CartItem cartItem : order.getGoodsList()) {	// 주문 상품별 리뷰 존재 여부 확인해서 저장
				cartItem.getGoods().setExistReview(reviewDao.isExistReview(cartItem.getGoodsId(), orderId));
			}
		}
		return order;
	}

	@Override
	@Transactional
	public int order(Order order) throws DataAccessException {
		
		orderDao.createOrder(order);	// 주문 레코드 생성
		
		int orderId = order.getId();
		List<Map<String, Object>> orderGoodsList = new ArrayList<Map<String, Object>>();
		for (CartItem cartItem : order.getGoodsList()) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("orderId", orderId);
			param.put("cartItem", cartItem);
			orderGoodsList.add(param);	
		}
		orderDao.createOrderGoods(orderGoodsList);	// 주문 상품 레코드 생성

		if (order.getUsedPoint() > 0) {
			memberDao.changePoint(order.getBuyer().getId(), -1, order.getUsedPoint());	// -사용 포인트
		}
		
		for (CartItem cartItem : order.getGoodsList()) {
			itemDao.changeRemainQuantityByOrderStatus(cartItem.getGoodsId(), 1, cartItem.getQuantity());	// 주문 상품별 남은 수량 컬럼 수정
			deleteCartItem(order.getBuyer().getId(), cartItem.getGoodsId());	// 상품에 대한 장바구니 레코드 삭제
		}
		
		return orderId;
	}

	@Override
	@Transactional
	public void changeTrackingNumber(Order order) throws DataAccessException {
		
		orderDao.changeTrackingNumber(order.getId(), order.getTrackingNumber());	// 운송장번호 컬럼 수정
		orderDao.changeOrderStatus(order.getId(), 2);	// 주문상태 컬럼을 2(배송시작)으로 변경
	}

	@Override
	@Transactional
	public void cancelOrder(int orderId) throws DataAccessException, CancelOrderException {
		
		Order order = getOrder(orderId);	// 주문 조회 메소드 호출
		if (order != null) {
			if (order.getTrackingNumber() != null) {	// 운송장 번호가 존재하면 에러 메시지 전달
				System.out.println("배송 시작됨");
				throw new CancelOrderException("배송이 시작되어 취소할 수 없습니다. 마켓에 문의하시기 바랍니다.");
			}
			
			orderDao.changeOrderStatus(orderId, 0);	// 주문상태 컬럼을 0(주문취소)으로 변경
			
			for (CartItem item : order.getGoodsList()) {
				itemDao.changeRemainQuantityByOrderStatus(item.getGoodsId(), 0, item.getQuantity());	// 주문 상품별 남은 수량 컬럼 수정
			}
			
			if (order.getUsedPoint() > 0) {
				memberDao.changePoint(order.getBuyer().getId(), 1, order.getUsedPoint());	// +사용 포인트
			}
		}
	}

	@Override
	public void finalizeOrder(int orderId) {
		orderDao.changeOrderStatus(orderId, 3);
	}
	

}
