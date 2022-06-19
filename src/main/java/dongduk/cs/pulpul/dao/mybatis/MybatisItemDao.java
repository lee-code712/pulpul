package dongduk.cs.pulpul.dao.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dongduk.cs.pulpul.dao.ItemDao;
import dongduk.cs.pulpul.dao.mybatis.mapper.ItemMapper;
import dongduk.cs.pulpul.domain.Goods;
import dongduk.cs.pulpul.domain.Item;
import dongduk.cs.pulpul.domain.ShareThing;

@Repository
public class MybatisItemDao implements ItemDao {

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
	public void createGoods(Goods goods) {
		itemMapper.insertGoods(goods);
	}

	@Override
	public void changeGoodsInfo(Goods goods) {
		itemMapper.updateGoods(goods);
	}

	@Override
	public void changeRemainQuantityByOrderStatus(String itemId, int orderStatus, int quantity) {
		itemMapper.updateRemainQuantity(itemId, orderStatus, quantity);
	}

	@Override
	public boolean isExistOrder(String itemId) {
		int ck = itemMapper.selectOrderCountByItemId(itemId);
		if (ck > 0) return true;
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
	public void createShareThing(ShareThing shareThing) {
		itemMapper.insertShareThing(shareThing);
	}

	@Override
	public void changeShareThingInfo(ShareThing shareThing) {
		itemMapper.updateShareThing(shareThing);
	}

	@Override
	public boolean changeIsBorrowed(ShareThing shareThing) {
		int ck = itemMapper.updateIsBorrowed(shareThing);
		if (ck > 0) return true;
		return false;
	}

	@Override
	public boolean isExistBorrow(String itemId) {
		int ck = itemMapper.selectBorrowCountByItemId(itemId);
		if (ck > 0) return true;
		return false;
	}
	
	@Override
	public void changeItemInfo(Item item) {
		itemMapper.updateItem(item);
	}

	@Override
	public void deleteItem(String itemId) {
		itemMapper.deleteItem(itemId);
	}

	@Override
	public void createItemImages(List<String> imageUrlList, String memberId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("memberId", memberId);
		param.put("imageUrlList", imageUrlList);
		itemMapper.insertItemImages(param);
	}

	@Override
	public int deleteItemImages(String itemId, String memberId) {
		return itemMapper.deleteItemImages(memberId, itemId);
	}

}
