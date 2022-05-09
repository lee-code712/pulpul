package dongduk.cs.pulpul.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import dongduk.cs.pulpul.domain.Goods;
import dongduk.cs.pulpul.domain.ShareThings;

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
	
	List<ShareThings> selectAllShareThings();
	
	List<ShareThings> selectShareThingsByMemberId(String memberId);
	
	List<ShareThings> selectShareThingsByMarketId(int marketId);
	
	ShareThings selectShareThingsByItem(String itemId);
	
	int insertShareThings(ShareThings shareThings);
	
	int updateShareThings(ShareThings shareThings);
	
	int updateIsBorrowed(ShareThings shareThings);
	
	int selectBorrowIdByItemId(String itemId);
	
	int insertItemImages(List<Map<String, String>> itemImages);
	
	int deleteItemImages(@Param("memberId") String memberId, 
			@Param("itemId") String itemId);
	
}
