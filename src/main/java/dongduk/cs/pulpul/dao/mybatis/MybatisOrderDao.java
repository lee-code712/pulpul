package dongduk.cs.pulpul.dao.mybatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dongduk.cs.pulpul.dao.OrderDao;
import dongduk.cs.pulpul.dao.mybatis.mapper.OrderMapper;
import dongduk.cs.pulpul.domain.Cart;
import dongduk.cs.pulpul.domain.CartItem;
import dongduk.cs.pulpul.domain.Order;

@Repository
public class MybatisOrderDao implements OrderDao {
	
	@Autowired
    private OrderMapper orderMapper;

	@Override
	public Cart findCartByMember(String memberId) {
		return orderMapper.selectCartByMemberId(memberId);
	}

	@Override
	public int findNumberOfCartItemByMember(String memberId) {
		return orderMapper.selectNumberOfCart(memberId);
	}
	
	@Override
	public boolean isExistItem(String memberId, String goodsId) {
		int ck = orderMapper.selectCartCountBygoodsId(memberId, goodsId);
		if (ck > 0) return true;
		return false;
	}

	@Override
	public boolean createCartItem(String memberId, CartItem cartItem) {
		int ck = orderMapper.insertCart(memberId, cartItem);
		if (ck > 0) return true;
		return false;
	}

	@Override
	public boolean deleteOneCartItem(String memberId, String itemId) {
		int ck = orderMapper.deleteOneCartByItemId(memberId, itemId);
		if (ck > 0) return true;
		return false;
	}

	@Override
	public int deleteCartItemByMarket(String memberId, int marketId) {
		return orderMapper.deleteCartByMarketId(memberId, marketId);
	}

	@Override
	public Order findOrder(int orderId) {
		return orderMapper.selectOrder(orderId);
	}

	@Override
	public List<Order> findOrderByMember(String memberId, String identity) {
		return orderMapper.selectOrderByMemberId(memberId, identity);
	}

	@Override
	public boolean createOrder(Order order) {
		int ck = orderMapper.insertOrder(order);
		if (ck > 0) return true;
		return false;
	}

	@Override
	public boolean createOrderGoods(List<Map<String, Object>> orderGoodsList) {
		int ck = orderMapper.insertOrderGoods(orderGoodsList);
		if (ck > 0) return true;
		return false;
	}

	@Override
	public boolean changeTrackingNumber(int orderId, String trackingNumber) {
		int ck = orderMapper.updateTrackingNumber(orderId, trackingNumber);
		if (ck > 0) return true;
		return false;
	}

	@Override
	public boolean changeOrderStatus(int orderId, int orderStatus) {
		int ck = orderMapper.updateOrderStatus(orderId, orderStatus);
		if (ck > 0) return true;
		return false;
	}

}