package dongduk.cs.pulpul.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dongduk.cs.pulpul.dao.mybatis.mapper.ItemMapper;
import dongduk.cs.pulpul.domain.Goods;
import dongduk.cs.pulpul.domain.ShareThing;

@Component
public class ItemDaoImpl implements ItemDao {

	@Autowired
    private ItemMapper itemMapper;
	
	@Override
	public List<Goods> findAllGoods() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Goods> findGoodsByMember(String memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Goods> findGoodsByMarket(int marketId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Goods> findNewGoods() {
		return itemMapper.selectNewGoods();
	}

	@Override
	public List<Goods> findGoodsByKeyword(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Goods findGoodsByItem(String itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createGoods(Goods goods) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean changeGoodsInfo(Goods goods) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeSalesQuantity(Goods goods) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeRemainQuantityByOrderStatus(int orderId, int orderStatus, int quantity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isExistOrder(String itemId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ShareThing> findAllShareThing() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ShareThing> findShareThingByMember(String memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ShareThing> findShareThingByMarket(int marketId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShareThing findShareThingByItem(String itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createShareThing(ShareThing shareThing) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean changeShareThingInfo(ShareThing shareThing) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeIsBorrowed(ShareThing shareThing) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isExistBorrow(String itemId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteItem(String itemId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createItemImages(List<String> imageUrlList, String memberId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteItemImages(String itemId, String memberId) {
		// TODO Auto-generated method stub
		return false;
	}

}
