package dongduk.cs.pulpul.service;

import java.util.List;

import dongduk.cs.pulpul.domain.Goods;
import dongduk.cs.pulpul.domain.ShareThings;

public interface ItemService {
	
	// 상품 목록 조회
	List<Goods> getGoodsList();
	
	// 회원 id로 상품 목록 조회
	List<Goods> getGoodsListByMember(String memberId);
	
	// 마켓 id로 상품 목록 조회
	List<Goods> getGoodsListByMarket(int marketId);
	
	// 새 상품 목록 조회
	List<Goods> getNewGoodsList();
	
	// 상품 상세정보 조회
	Goods getGoods(String itemId);
	
	// 상품 등록
	boolean uploadGoods(Goods goods);
	
	// 상품 정보 수정
	boolean changeGoodsInfo(Goods goods);
	
	// 전체 공유 물품 목록 조회
	List<ShareThings> getShareThingsList();
	
	// 회원 id로 공유 물품 목록 조회
	List<ShareThings> getShareThingsListByMember(String memberId);
	
	// 마켓 id로 공유 물품 목록 조회
	List<ShareThings> getShareThingsListByMarket(int marketId);
	
	// 공유 물품 상세정보 조회
	ShareThings getShareThings(String itemId);
	
	// 공유 물품 등록
	boolean uploadShareThings(ShareThings shareThings);
	
	// 공유 물품 정보 수정
	boolean changeShareThingsInfo(ShareThings shareThings);
	
	// 품목 삭제
	boolean deleteItem(String itemId);
	
}
