package dongduk.cs.pulpul.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dongduk.cs.pulpul.dao.ItemDao;
import dongduk.cs.pulpul.dao.MemberDao;
import dongduk.cs.pulpul.domain.Goods;
import dongduk.cs.pulpul.domain.ShareThing;

@Service
public class ItemServiceImpl implements ItemService {

	private final ItemDao itemDao;
	private final MemberDao memberDao;

	@Autowired
	public ItemServiceImpl(ItemDao itemDao, MemberDao memberDao) {
		this.itemDao = itemDao;
		this.memberDao = memberDao;
	}

	// 전체 상품 목록 조회
	@Override
	public List<Goods> getGoodsList() {
		return itemDao.findAllGoods();
	}
	
	// 상품목록 조회
	public List<Goods> getGoodsList(String keyword) {
		if (keyword != null) {
			return itemDao.findGoodsByKeyword(keyword);
		}
		return itemDao.findAllGoods();
	}

	// 회원이 업로드한 상품목록 조회
	public List<Goods> getGoodsListByMember(String memberId) {
		return itemDao.findGoodsByMember(memberId);
	}

	// 마켓의 상품목록 조회
	public List<Goods> getGoodsListByMarket(int marketId) {
		return itemDao.findGoodsByMarket(marketId);
	}

	// 새로 업로드된 상품 3개 조회
	public List<Goods> getNewGoodsList() {
		return itemDao.findNewGoods();
	}

	// 상품 상세정보 조회
	public Goods getGoods(String itemId) {
		return itemDao.findGoodsByItem(itemId);
	}

	// 상품 등록
	public boolean uploadGoods(Goods goods, MultipartFile[] uploadFiles) {
		// 상품 레코드 생성
		boolean successed = itemDao.createGoods(goods);
		if (!successed) return false;
		
		// 상품 이미지 저장, 이미지 레코드 생성
		String itemId = goods.getItem().getId();
		if (itemId != null) { // 정상적으로 레코드를 생성했다면
			goods.getItem().setId(itemId);
			List<String> imageUrlList = new ArrayList<String>();
			int cnt = 1;
			for (MultipartFile uploadFile : uploadFiles) {
				if (!uploadFile.isEmpty()) {
					String fileUrl = uploadFile(uploadFile, goods.getItem().getId(), cnt);
					imageUrlList.add("/upload/" + fileUrl);
					cnt++;
				}
			}
			String memberId = goods.getItem().getMarket().getMember().getId();
			successed = itemDao.createItemImages(imageUrlList, memberId);
			if (!successed) return false;
			
			// 포인트 +500
			return memberDao.changePoint(memberId, 1, 500);
		}
		return false;
	}

	// 상품정보 수정
	public boolean changeGoodsInfo(Goods goods, MultipartFile[] updateFiles) {
		// 품목 레코드 수정
		boolean successed = itemDao.chageItemInfo(goods.getItem());
		if (!successed) return false;
		// 상품 레코드 수정
		successed = itemDao.changeGoodsInfo(goods);
		if (!successed) return false;
		// 상품 판매수량 수정
		successed = itemDao.changeSalesQuantity(goods);
		if (!successed) return false;
		
		
		// 상품 이미지 삭제, 이미지 레코드 삭제
		String itemId = goods.getItem().getId();
		String memberId = goods.getItem().getMarket().getMember().getId();
		int cnt = itemDao.deleteItemImages(itemId, memberId);
		if (cnt < 0) return false;
		deleteFile(itemId, cnt);
		
		// 새로운 상품 이미지 저장, 이미지 레코드 생성
		List<String> imageUrlList = new ArrayList<String>();
		int newCnt = 1;
		for (MultipartFile updateFile : updateFiles) {
			if (!updateFile.isEmpty()) {
				String fileUrl = uploadFile(updateFile, itemId, newCnt);
				imageUrlList.add("/upload/" + fileUrl);
				newCnt++;
			}
		}
		return itemDao.createItemImages(imageUrlList, memberId);
	}

// 공유물품목록 조회
	public List<ShareThing> getShareThingList() {
		return itemDao.findAllShareThing();
	}

	// 회원이 업로드한 공유물품목록 조회
	public List<ShareThing> getShareThingListByMember(String memberId) {
		return itemDao.findShareThingByMember(memberId);
	}

	// 마켓의 공유물품 조회
	public List<ShareThing> getShareThingListByMarket(int marketId) {
		return itemDao.findShareThingByMarket(marketId);
	}

	// 공유물품 상세정보 조회
	public ShareThing getShareThing(String itemId) {
		return itemDao.findShareThingByItem(itemId);
	}

	// 공유물품 등록
	public boolean uploadShareThing(ShareThing ShareThing) {
		// 공유물품 레코드 생성에 필요한 데이터 가공
		/*boolean success = itemDao.createShareThing(ShareThing);
		if (!success)
			return false;
		if (ShareThing.getItem().getImageUrlList().size() > 0) {
			return itemDao.createItemImages(ShareThing.getItem());
		} */
		return true;
	}

	// 공유물품정보 수정
	public boolean changeShareThingInfo(ShareThing ShareThing) {
		// 공유물품 레코드 수정에 필요한 데이터 가공
		/* boolean success = itemDao.changeShareThingInfo(ShareThing);
		if (!success)
			return false;
		success = itemDao.deleteItemImages(memberId, ShareThing.getItem().getId());
		if (!success)
			return false;
		return itemDao.createItemImages(ShareThing.getItem()); */
		return false;
	}

	// 업로드한 물품 삭제
	public boolean deleteItem(String itemId) {
		/*boolean isExist = true;
		if (itemId.substring(0, 1).equals("G")) {
			isExist = itemDao.isExistOrder(itemId);
		} else if (itemId.substring(0, 1).equals("S")) {
			isExist = itemDao.isExistBorrow(itemId);
		}
		if (isExist)
			return false;
		boolean success = itemDao.deleteItem(itemId);
		if (!success)
			return false;
		return itemDao.deleteItemImages(itemId); */
		
		return false;
	}
	
	// 파일 업로드 메소드
	public String uploadFile(MultipartFile uploadFile, String itemId, int cnt) {
		String newFilename = "";
		String absolutePath = new File("").getAbsolutePath() + "\\";
		String path = absolutePath + "src\\main\\resources\\static\\upload";

		// System.out.println(path);
		try {       
//			  // 확장자를 jpg로 제한
//            String filename = uploadFile.getOriginalFilename();
//            String ext = filename.substring(filename.lastIndexOf( "." ));
            newFilename = itemId + "-" + cnt + ".jpg";
            
            File newFile = new File(path, newFilename);
            if (newFile.exists())
            	newFile.delete();
            uploadFile.transferTo(newFile);

        }catch(Exception e) {            
            e.printStackTrace();
        }
		
		return newFilename;
	}
	
	// 특정 상품에 대한 파일 삭제 메소드
	public void deleteFile(String itemId, int cnt) {
		String absolutePath = new File("").getAbsolutePath() + "\\";
		String path = absolutePath + "src\\main\\resources\\static\\upload";
		
		try {       
//			  // 확장자를 jpg로 제한
//          String filename = uploadFile.getOriginalFilename();
//          String ext = filename.substring(filename.lastIndexOf( "." ));
			for (int i = 1; i <= cnt; i++) {
				String filename = itemId + "-" + cnt + ".jpg";
				File file = new File(path, filename);
				if (file.exists())
					file.delete();
			}
		}catch(Exception e) {            
			e.printStackTrace();
		}
	}
	
}
