package dongduk.cs.pulpul.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import dongduk.cs.pulpul.domain.Cart;
import dongduk.cs.pulpul.domain.Order;

@Mapper
public interface OrderMapper {
	
	Cart selectCartByMemberId(String memberId);
	
	int insertCart(Cart cart);
	
	int deleteOneCartByItemId(String itemId);
	
	int deleteCartByMarketId(int marketId);
	
	Order selectOrder(int orderId);
	
	List<Order> selectOrderByMemberId(String memberId);
	
	int insertOrder(Order order);
	
	int insertOrderGoods(List<Map<String, Object>> orderGoods);
	
	int updateTrackingNumber(@Param("orderId") int orderId, 
			@Param("trackingNumber") int trackingNumber);
	
	int updateOrderStatus(@Param("orderId") int orderId, 
			@Param("orderStatus") int orderStatus);
	
}
