package dongduk.cs.pulpul.service;

import java.util.List;

import dongduk.cs.pulpul.controller.FileCommand;
import dongduk.cs.pulpul.domain.Goods;
import dongduk.cs.pulpul.domain.ShareThing;
import dongduk.cs.pulpul.service.exception.DeleteItemException;

public interface ItemService {
	
	// 상품 목록 조회
	List<Goods> getGoodsList();
	
	// 상품목록 조회
	List<Goods> getGoodsList(String keyword);
	
	// 회원 id로 상품 목록 조회
	List<Goods> getGoodsListByMember(String memberId);
	
	// 마켓 id로 상품 목록 조회
	List<Goods> getGoodsListByMarket(int marketId);
	
	// 새 상품 목록 조회
	List<Goods> getNewGoodsList();
	
	// 상품 상세정보 조회
	Goods getGoods(String itemId);
	
	// 상품 등록
	boolean uploadGoods(Goods goods, FileCommand uploadFiles);
	
	// 상품 정보 수정
	boolean changeGoodsInfo(Goods goods, FileCommand updateFiles, String[] deleteImages);
	
	// 전체 공유 물품 목록 조회
	List<ShareThing> getShareThingList();
	
	// 회원 id로 공유 물품 목록 조회
	List<ShareThing> getShareThingListByMember(String memberId);
	
	// 마켓 id로 공유 물품 목록 조회
	List<ShareThing> getShareThingListByMarket(int marketId);
	
	// 공유 물품 상세정보 조회
	ShareThing getShareThing(String itemId);
	
	// 공유 물품 등록
	boolean uploadShareThing(ShareThing shareThing, FileCommand uploadFiles);
	
	// 공유 물품 정보 수정
	boolean changeShareThingInfo(ShareThing shareThing, FileCommand updateFiles, String[] deleteImages);
	
	// 품목 삭제
	boolean deleteItem(String itemId, String memberId, String uploadDir) throws DeleteItemException;
	
}
