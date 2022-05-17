package dongduk.cs.pulpul.dao;

import java.util.List;

import dongduk.cs.pulpul.domain.Goods;
import dongduk.cs.pulpul.domain.ShareThing;

public interface ItemDao {
	
	// 전체 상품 목록 조회
	List<Goods> findAllGoods();
	
	// 회원 id로 상품 목록 조회
	List<Goods> findGoodsByMember(String memberId);
	
	// 마켓 id로 상품 목록 조회
	List<Goods> findGoodsByMarket(int marketId);
	
	// 새 상품 목록 조회
	List<Goods> findNewGoods();
	
	// 키워드로 상품 목록 조회
	List<Goods> findGoodsByKeyword(String keyword);
	
	// 품목 id로 상품 목록 조회
	Goods findGoodsByItem(String itemId);
	
	// 상품 생성
	boolean createGoods(Goods goods);
	
	// 상품 정보 수정
	boolean changeGoodsInfo(Goods goods);
	
	// 상품 판매 수량 변경
	boolean changeSalesQuantity(Goods goods);
	
	// 주문 상태에 따라 남은 상품 수량 변경
	boolean changeRemainQuantityByOrderStatus(int orderId, int orderStatus, int quantity);
	
	// 상품에 대한 주문 내역 존재여부 확인
	boolean isExistOrder(String itemId);
	
	// 전체 공유물품 목록 조회
	List<ShareThing> findAllShareThing();
	
	// 회원 id로 공유 물품 목록 조회
	List<ShareThing> findShareThingByMember(String memberId);
	
	// 마켓 id로 공유 물품 목록 조회
	List<ShareThing> findShareThingByMarket(int marketId);
	
	// 품목 id로 공유 물품 목록 조회
	ShareThing findShareThingByItem(String itemId);
	
	// 공유 물품 생성
	int createShareThing(ShareThing shareThing);
	
	// 공유 물품 정보 수정
	boolean changeShareThingInfo(ShareThing shareThing);
	
	// 공유 물품의 대여 여부 변경
	boolean changeIsBorrowed(ShareThing shareThing);
	
	// 공유 물품에 대한 대여 내역 존재여부 확인
	boolean isExistBorrow(String itemId);
	
	// 품목 삭제
	boolean deleteItem(String itemId);
	
	// 품목 이미지 목록 생성
	boolean createItemImages(List<String> imageUrlList, String memberId);
	
	// 품목 이미지 목록 생성
	boolean deleteItemImages(String itemId, String memberId);
	
}
