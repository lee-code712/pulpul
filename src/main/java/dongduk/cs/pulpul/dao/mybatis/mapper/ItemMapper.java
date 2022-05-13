package dongduk.cs.pulpul.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import dongduk.cs.pulpul.domain.Goods;
import dongduk.cs.pulpul.domain.ShareThing;

@Mapper
public interface ItemMapper {
	
	List<Goods> selectAllGoods();
	
	List<Goods> selectGoodsByMemberId(String memberId);
	
	List<Goods> selectGoodsByMarketId(int marketId);
	
	List<Goods> selectNewGoods();
	
	List<Goods> selectGoodsByKeyword(String keyword);
	
	Goods selectGoodsByItem(String itemId);
	
	int insertGoods(Goods goods);
	
	int updateGoods(Goods goods);
	
	int updateSalesQuantity(Goods goods);
	
	int updateRemainQuantity(@Param("orderId") int orderId, 
			@Param("orderStatus") int orderStatus, 
			@Param("quantity") int quantity);
	
	int selectOrderIdByItemId(String itemId);
	
	List<ShareThing> selectAllShareThing();
	
	List<ShareThing> selectShareThingByMemberId(String memberId);
	
	List<ShareThing> selectShareThingByMarketId(int marketId);
	
	ShareThing selectShareThingByItem(String itemId);
	
	int insertShareThing(ShareThing shareThing);
	
	int updateShareThing(ShareThing shareThing);
	
	int updateIsBorrowed(ShareThing shareThing);
	
	int selectBorrowIdByItemId(String itemId);
	
	int insertItemImages(List<Map<String, String>> itemImages);
	
	int deleteItemImages(@Param("memberId") String memberId, 
			@Param("itemId") String itemId);
	
}
