package dongduk.cs.pulpul.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import dongduk.cs.pulpul.domain.Cart;
import dongduk.cs.pulpul.domain.CartItem;
import dongduk.cs.pulpul.domain.Order;

@Mapper
public interface OrderMapper {
	
	Cart selectCartByMemberId(String memberId);
	
	int selectNumberOfCart(String memberId);
	
	int insertCart(@Param("memberId") String memberId,
			@Param("cartItem") CartItem cartItem);
	
	int deleteOneCartByItemId(@Param("memberId") String memberId, 
			@Param("itemId") String itemId);
	
	int deleteCartByMarketId(@Param("memberId") String memberId,
			@Param("marketId") int marketId);
	
	Order selectOrder(int orderId);
	
	List<Order> selectOrderByMemberId(String memberId, String identity);
	
	int insertOrder(Order order);
	
	int insertOrderGoods(List<Map<String, Object>> orderGoods);
	
	int updateTrackingNumber(@Param("orderId") int orderId, 
			@Param("trackingNumber") int trackingNumber);
	
	int updateOrderStatus(@Param("orderId") int orderId, 
			@Param("orderStatus") int orderStatus);
	
}
