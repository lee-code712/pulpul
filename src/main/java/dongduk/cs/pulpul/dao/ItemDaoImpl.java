package dongduk.cs.pulpul.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dongduk.cs.pulpul.dao.mybatis.mapper.ItemMapper;
import dongduk.cs.pulpul.domain.Goods;
import dongduk.cs.pulpul.domain.Item;
import dongduk.cs.pulpul.domain.ShareThing;

@Component
public class ItemDaoImpl implements ItemDao {

	@Autowired
    private ItemMapper itemMapper;
	
	@Override
	public List<Goods> findAllGoods() {
		return itemMapper.selectAllGoods();
	}

	@Override
	public List<Goods> findGoodsByMember(String memberId) {
		return itemMapper.selectGoodsByMemberId(memberId);
	}

	@Override
	public List<Goods> findGoodsByMarket(int marketId) {
		return itemMapper.selectGoodsByMarketId(marketId);
	}

	@Override
	public List<Goods> findNewGoods() {
		return itemMapper.selectNewGoods();
	}

	@Override
	public List<Goods> findGoodsByKeyword(String keyword) {
		return itemMapper.selectGoodsByKeyword(keyword);
	}

	@Override
	public Goods findGoodsByItem(String itemId) {
		return itemMapper.selectGoodsByItemId(itemId);
	}
	
	@Override
	public int findRemainQuantityByGoods(String itemId) {
		String remainQuantity = itemMapper.selectRemainQuantityByGoodsId(itemId);
		if (remainQuantity == null)
			return 0;
		return Integer.parseInt(remainQuantity);
	}

	@Override
	public boolean createGoods(Goods goods) {
		int ck = itemMapper.insertGoods(goods);
		if (ck < 0) return false;
		return true;
	}

	@Override
	public boolean changeGoodsInfo(Goods goods) {
		int ck = itemMapper.updateGoods(goods);
		if (ck < 0) return false;
		return true;
	}

	@Override
	public boolean changeSalesQuantity(Goods goods) {
		int ck = itemMapper.updateSalesQuantity(goods);
		if (ck < 0) return false;
		return true;
	}

	@Override
	public boolean changeRemainQuantityByOrderStatus(int orderId, int orderStatus, int quantity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isExistOrder(String itemId) {
		int cnt = itemMapper.selectOrderCountByItemId(itemId);
		if (cnt > 0) return true;
		return false;
	}

	@Override
	public List<ShareThing> findAllShareThing() {
		return itemMapper.selectAllShareThing();
	}

	@Override
	public List<ShareThing> findShareThingByMember(String memberId) {
		return  itemMapper.selectShareThingByMemberId(memberId);
	}

	@Override
	public List<ShareThing> findShareThingByMarket(int marketId) {
		return itemMapper.selectShareThingByMarketId(marketId);
	}

	@Override
	public ShareThing findShareThingByItem(String itemId) {
		return itemMapper.selectShareThingByItem(itemId);
	}

	@Override
	public boolean createShareThing(ShareThing shareThing) {
		int ck = itemMapper.insertShareThing(shareThing);
		if (ck < 0) return false;
		return true;
	}

	@Override
	public boolean changeShareThingInfo(ShareThing shareThing) {
		int ck = itemMapper.updateShareThing(shareThing);
		if (ck < 0) return false;
		return true;
	}

	@Override
	public boolean changeIsBorrowed(ShareThing shareThing) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isExistBorrow(String itemId) {
		int cnt = itemMapper.selectBorrowCountByItemId(itemId);
		if (cnt > 0) return true;
		return false;
	}
	
	@Override
	public boolean chageItemInfo(Item item) {
		int ck = itemMapper.updateItem(item);
		if (ck < 0) return false;
		return true;
	}

	@Override
	public boolean deleteItem(String itemId) {
		int ck = itemMapper.deleteItem(itemId);
		if (ck < 0) return false;
		return true;
	}

	@Override
	public boolean createItemImages(List<String> imageUrlList, String memberId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("memberId", memberId);
		param.put("imageUrlList", imageUrlList);
		int ck = itemMapper.insertItemImages(param);
		if (ck < 0) return false;
		return true;
	}

	@Override
	public int deleteItemImages(String itemId, String memberId) {
		return itemMapper.deleteItemImages(memberId, itemId);
	}

}
