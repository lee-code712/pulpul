package dongduk.cs.pulpul.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dongduk.cs.pulpul.dao.mybatis.mapper.OrderMapper;
import dongduk.cs.pulpul.domain.Cart;
import dongduk.cs.pulpul.domain.Order;

@Component
public class OrderDaoImpl implements OrderDao {
	
	@Autowired
    private OrderMapper orderMapper;

	@Override
	public Cart findCartByMember(String memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createCartItem(Cart cart) {
		int ck = orderMapper.insertCart(cart);
		if (ck > 0) return true;
		return false;
	}

	@Override
	public boolean deleteOneCartItem(String memberId, String itemId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteCartItemByMarket(String memberId, int marketId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Order findOrder(int orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findOrderByMember(String memberId, String identity) {
		return orderMapper.selectOrderByMemberId(memberId, identity);
	}

	@Override
	public int createOrder(Order orders) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean createOrderGoods(Cart cart) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeTrackingNumber(int orderId, String trackingNumber) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeOrderStatus(int orderId, int orderStatus) {
		// TODO Auto-generated method stub
		return false;
	}

}
