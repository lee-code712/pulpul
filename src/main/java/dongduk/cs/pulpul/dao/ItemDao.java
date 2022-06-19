package dongduk.cs.pulpul.dao;

import java.util.List;

import dongduk.cs.pulpul.domain.Goods;
import dongduk.cs.pulpul.domain.Item;
import dongduk.cs.pulpul.domain.ShareThing;

public interface ItemDao {
	
	/*
	 * 품목 관련 dao method
	 */
	
	// 품목 이미지 목록 생성
	void createItemImages(List<String> imageUrlList, String memberId);
	
	// 품목 이미지 목록 삭제(삭제 개수 반환)
	int deleteItemImages(String itemId, String memberId);
	
	// 품목 정보 수정
	void changeItemInfo(Item item);
	
	// 품목 삭제
	void deleteItem(String itemId);
	
	
	/*
	 * 상품 관련 dao method
	 */
	
	// 전체 상품 목록 조회
	List<Goods> findAllGoods();
	
	// 회원 id로 상품 목록 조회
	List<Goods> findGoodsByMember(String memberId);
	
	// 마켓 id로 상품 목록 조회
	List<Goods> findGoodsByMarket(int marketId);
	
	// 새 상품 목록 조회
	List<Goods> findNewGoods();
	
	// 상품 id로 상품 상세 조회
	Goods findGoodsByItem(String itemId);
	
	// 상품 id로 남은 수량 조회
	int findRemainQuantityByGoods(String itemId);
	
	// 상품 생성
	void createGoods(Goods goods);
	
	// 상품 정보 수정
	void changeGoodsInfo(Goods goods);
	
	// 주문 상태에 따라 남은 상품 수량 변경
	void changeRemainQuantityByOrderStatus(String itemId, int orderStatus, int quantity);
	
	// 상품에 대한 주문 내역 존재여부 확인
	boolean isExistOrder(String itemId);
	
	
	/*
	 * 공유물품 관련 dao method
	 */
	
	// 전체 공유물품 목록 조회
	List<ShareThing> findAllShareThing();
	
	// 회원 id로 공유물품 목록 조회
	List<ShareThing> findShareThingByMember(String memberId);
	
	// 마켓 id로 공유물품 목록 조회
	List<ShareThing> findShareThingByMarket(int marketId);
	
	// 공유물품 id로 공유물품 상세 조회
	ShareThing findShareThingByItem(String itemId);
	
	// 공유물품 생성
	void createShareThing(ShareThing shareThing);
	
	// 공유물품 정보 수정
	void changeShareThingInfo(ShareThing shareThing);
	
	// 공유물품의 대여 여부 변경
	boolean changeIsBorrowed(ShareThing shareThing);  // borrow service 수정 시 void로 변경해야 함
	
	// 공유물품에 대한 대여 내역 존재여부 확인
	boolean isExistBorrow(String itemId);
	
}
