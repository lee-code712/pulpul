package dongduk.cs.pulpul.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dongduk.cs.pulpul.controller.FileCommand;
import dongduk.cs.pulpul.dao.BorrowDao;
import dongduk.cs.pulpul.dao.ItemDao;
import dongduk.cs.pulpul.dao.MemberDao;
import dongduk.cs.pulpul.domain.Goods;
import dongduk.cs.pulpul.domain.ShareThing;
import dongduk.cs.pulpul.service.exception.DeleteItemException;

@Service
public class ItemServiceImpl implements ItemService {

	private final ItemDao itemDao;
	private final MemberDao memberDao;
	private final BorrowDao borrowDao;

	@Autowired
	public ItemServiceImpl(ItemDao itemDao, MemberDao memberDao, BorrowDao borrowDao) {
		this.itemDao = itemDao;
		this.memberDao = memberDao;
		this.borrowDao = borrowDao;
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
	public boolean uploadGoods(Goods goods, FileCommand uploadFiles) {
		// 상품 레코드 생성
		boolean successed = itemDao.createGoods(goods);
		if (!successed) return false;
		
		// 상품 이미지 저장, 이미지 레코드 생성
		String itemId = goods.getItem().getId();
		if (itemId != null) { // 정상적으로 레코드를 생성했다면
			goods.getItem().setId(itemId);
			List<String> imageUrlList = new ArrayList<String>();
			int cnt = 1;
			for (MultipartFile uploadFile : uploadFiles.getFiles()) {
				if (!uploadFile.isEmpty()) {
					uploadFiles.setFile(uploadFile);
					String filename = uploadFile(uploadFiles, goods.getItem().getId(), cnt);
					imageUrlList.add("/upload/" + filename);
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
	public boolean changeGoodsInfo(Goods goods, FileCommand updateFiles, String[] deleteImages) {
		// 품목 레코드 수정
		boolean successed = itemDao.chageItemInfo(goods.getItem());
		if (!successed) return false;
		// 상품 레코드 수정
		successed = itemDao.changeGoodsInfo(goods);
		if (!successed) return false;
//		// 상품 판매수량 수정
//		successed = itemDao.changeSalesQuantity(goods);
//		if (!successed) return false;
		
		
		// 상품 이미지 삭제, 이미지 레코드 삭제
		String itemId = goods.getItem().getId();
		String memberId = goods.getItem().getMarket().getMember().getId();
		int cnt = itemDao.deleteItemImages(itemId, memberId);
		if (cnt < 0) return false;
		deleteFileByImages(updateFiles.getPath(), deleteImages);
		
		// 새로운 상품 이미지 저장, 이미지 레코드 생성
		List<String> imageUrlList = new ArrayList<String>();
		int newCnt = 1;
		for (MultipartFile updateFile : updateFiles.getFiles()) {
			if (!updateFile.isEmpty()) {
				updateFiles.setFile(updateFile);
				String filename = uploadFile(updateFiles, itemId, newCnt);
				imageUrlList.add("/upload/" + filename);
				newCnt++;
			}
		}
		return itemDao.createItemImages(imageUrlList, memberId);
	}

	// 공유물품목록 조회
	public List<ShareThing> getShareThingList() {
		List<ShareThing> shareThingList = itemDao.findAllShareThing();
		if (shareThingList != null) {
			for (ShareThing shareThing : shareThingList) {
				String itemId = shareThing.getItem().getId();
				shareThing.setReservationNumber(borrowDao.checkNumberBorrowReservation(itemId));
			}
		}
		return shareThingList;
	}

	// 회원이 업로드한 공유물품목록 조회
	public List<ShareThing> getShareThingListByMember(String memberId) {
		List<ShareThing> shareThingList = itemDao.findShareThingByMember(memberId);
		if (shareThingList != null) {
			for (ShareThing shareThing : shareThingList) {
				String itemId = shareThing.getItem().getId();
				shareThing.setReservationNumber(borrowDao.checkNumberBorrowReservation(itemId));
			}
		}
		return shareThingList;
	}

	// 마켓의 공유물품 조회
	public List<ShareThing> getShareThingListByMarket(int marketId) {
		return itemDao.findShareThingByMarket(marketId);
	}

	// 공유물품 상세정보 조회
	public ShareThing getShareThing(String itemId) {
		ShareThing shareThing = itemDao.findShareThingByItem(itemId);
		if (shareThing != null) {
			shareThing.setReservationNumber(borrowDao.checkNumberBorrowReservation(itemId));
		}
		return shareThing;
	}

	// 공유물품 등록
	public boolean uploadShareThing(ShareThing shareThing, FileCommand uploadFiles) {
		// 공유물품 레코드 생성
		boolean successed = itemDao.createShareThing(shareThing);
		if (!successed) return false;
		
		// 공유물품 이미지 저장, 이미지 레코드 생성
		String itemId = shareThing.getItem().getId();
		if (itemId != null) { // 정상적으로 레코드를 생성했다면
			shareThing.getItem().setId(itemId);
			List<String> imageUrlList = new ArrayList<String>();
			int cnt = 1;
			for (MultipartFile uploadFile : uploadFiles.getFiles()) {
				if (!uploadFile.isEmpty()) {
					uploadFiles.setFile(uploadFile);
					String filename = uploadFile(uploadFiles, shareThing.getItem().getId(), cnt);
					imageUrlList.add("/upload/" + filename);
					cnt++;
				}
			}
			String memberId = shareThing.getItem().getMarket().getMember().getId();
			return itemDao.createItemImages(imageUrlList, memberId);
		}
		return false;
	}

	// 공유물품정보 수정
	public boolean changeShareThingInfo(ShareThing shareThing, FileCommand updateFiles, String[] deleteImages) {
		// 품목 레코드 수정
		boolean successed = itemDao.chageItemInfo(shareThing.getItem());
		if (!successed) return false;
		// 공유물품 레코드 수정
		successed = itemDao.changeShareThingInfo(shareThing);
		if (!successed) return false;		
		
		// 상품 이미지 삭제, 이미지 레코드 삭제
		String itemId = shareThing.getItem().getId();
		String memberId = shareThing.getItem().getMarket().getMember().getId();
		int cnt = itemDao.deleteItemImages(itemId, memberId);
		if (cnt < 0) return false;
		deleteFileByImages(updateFiles.getPath(), deleteImages);
		
		// 새로운 상품 이미지 저장, 이미지 레코드 생성
		List<String> imageUrlList = new ArrayList<String>();
		int newCnt = 1;
		for (MultipartFile updateFile : updateFiles.getFiles()) {
			if (!updateFile.isEmpty()) {
				updateFiles.setFile(updateFile);
				String filename = uploadFile(updateFiles, itemId, newCnt);
				imageUrlList.add("/upload/" + filename);
				newCnt++;
			}
		}
		return itemDao.createItemImages(imageUrlList, memberId);
	}

	// 업로드한 물품 삭제
	public boolean deleteItem(String itemId, String memberId, String uploadDir) throws DeleteItemException {
		// 주문 내역 또는 대여 내역이 존재하는지 확인
		if (itemId.substring(0, 1).equals("G") && itemDao.isExistOrder(itemId)) { // 품목이 상품이면
			System.out.println("주문내역 있음");
			throw new DeleteItemException("주문내역이 존재하여 삭제할 수 없습니다. 비공개로 돌려주시기 바랍니다.");
		}
		else if (itemId.substring(0, 1).equals("S") && itemDao.isExistBorrow(itemId)) { // 품목이 공유물품이면
			System.out.println("대여내역 있음");
			throw new DeleteItemException("대여내역이 존재하여 삭제할 수 없습니다. 비공개로 돌려주시기 바랍니다.");
		}

		// 품목 삭제
		boolean successed = itemDao.deleteItem(itemId);
		if (!successed) return false;

		// 품목 이미지 삭제
		int cnt = itemDao.deleteItemImages(itemId, memberId);
		if (cnt < 0) return false;
		deleteFileByItem(itemId, uploadDir, cnt);
		return true;
	}
	
	// 파일 업로드 메소드
	public String uploadFile(FileCommand uploadFiles, String itemId, int cnt) {
		String newFilename = "";
		// System.out.println(path);
		try {       
			// 확장자를 jpg로 제한
            newFilename = itemId + "-" + cnt + ".jpg";
            
            File newFile = new File(uploadFiles.getPath(), newFilename);
            if (newFile.exists())
            	newFile.delete();
            uploadFiles.getFile().transferTo(newFile);

        }catch(Exception e) {            
            e.printStackTrace();
        }
		
		return newFilename;
	}
	
	// 파일 업로드 메소드
	public String updateFile(String itemId, int cnt) {
		String newFilename = "";
		// System.out.println(path);
		try {       
			// 확장자를 jpg로 제한
            newFilename = itemId + "-" + cnt + ".jpg";
            
            File newFile = new File(uploadFiles.getPath(), newFilename);
            if (newFile.exists())
            	newFile.delete();
            uploadFiles.getFile().transferTo(newFile);

        }catch(Exception e) {            
            e.printStackTrace();
        }
		
		return newFilename;
	}
	
	// 특정 상품에 대한 파일 삭제 메소드
	public void deleteFileByImages(String uploadDir, String[] deleteImages) {
		try {       
			// 확장자를 jpg로 제한
			for (String deleteImage : deleteImages) {
				File file = new File(uploadDir, deleteImage);
				if (file.exists())
					file.delete();
			}
		}catch(Exception e) {            
			e.printStackTrace();
		}
	}
	
	// 특정 상품에 대한 파일 삭제 메소드
	public void deleteFileByItem(String itemId, String uploadDir, int cnt) {
		try {       
			// 확장자를 jpg로 제한
			for (int i = 1; i <= cnt; i++) {
				String filename = itemId + "-" + cnt + ".jpg"; 
				File file = new File(uploadDir, filename);
				if (file.exists())
					file.delete();
			}
		}catch(Exception e) {            
			e.printStackTrace();
		}
	}
	
	
	
}
