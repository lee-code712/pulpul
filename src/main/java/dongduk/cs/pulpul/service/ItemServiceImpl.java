package dongduk.cs.pulpul.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import dongduk.cs.pulpul.controller.FileCommand;
import dongduk.cs.pulpul.dao.BorrowDao;
import dongduk.cs.pulpul.dao.ItemDao;
import dongduk.cs.pulpul.dao.MemberDao;
import dongduk.cs.pulpul.domain.Goods;
import dongduk.cs.pulpul.domain.Market;
import dongduk.cs.pulpul.domain.Member;
import dongduk.cs.pulpul.domain.ShareThing;
import dongduk.cs.pulpul.domain.Item;
import dongduk.cs.pulpul.service.exception.DeleteItemException;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemDao itemDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private BorrowDao borrowDao;

	@Override
	public List<Goods> getGoodsList() {	// 전체 상품 목록 조회
		return itemDao.findAllGoods();
	}

	@Override
	public List<Goods> getGoodsListByMember(String memberId) {	// 회원이 업로드한 상품목록 조회
		return itemDao.findGoodsByMember(memberId);
	}

	@Override
	public List<Goods> getGoodsListByMarket(int marketId) {	// 마켓의 상품목록 조회
		return itemDao.findGoodsByMarket(marketId);
	}

	@Override
	public List<Goods> getNewGoodsList() {	// 최근 업로드된 상품 3개 조회
		return itemDao.findNewGoods();
	}

	@Override
	public Goods getGoods(String itemId) {	// 상품 상세정보 조회
		return itemDao.findGoodsByItem(itemId);
	}

	@Override
	@Transactional
	public void uploadGoods(Goods goods, FileCommand uploadFiles) throws DataAccessException {	// 상품 등록
		
		itemDao.createGoods(goods);	// 상품 레코드 생성
		
		// 상품 이미지 저장, 이미지 레코드 생성
		String itemId = goods.getItem().getId();
		if (itemId != null) {
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
			itemDao.createItemImages(imageUrlList, memberId); // 상품 이미지 레코드 생성
			
			memberDao.changePoint(memberId, 1, 500);	// 포인트 +500
		}
	}

	@Override
	@Transactional
	public void changeGoodsInfo(Goods goods, FileCommand updateFiles, String[] deleteImages)
			 throws DataAccessException {	// 상품 정보 수정
		
		itemDao.chageItemInfo(goods.getItem());	// 품목 레코드 수정
		itemDao.changeGoodsInfo(goods);	// 상품 레코드 수정
		
		// 상품 이미지 삭제, 이미지 레코드 삭제
		String itemId = goods.getItem().getId();
		String memberId = goods.getItem().getMarket().getMember().getId();
		itemDao.deleteItemImages(itemId, memberId); // 상품 이미지 레코드 삭제
		if (deleteImages != null)
			deleteFileByImages(updateFiles.getPath(), deleteImages);
		
		// 새로운 상품 이미지 저장, 이미지 레코드 생성
		List<String> imageUrlList = updateFileByItem(updateFiles.getPath(), itemId);
		int newCnt = imageUrlList.size() + 1;
		for (MultipartFile updateFile : updateFiles.getFiles()) {
			if (!updateFile.isEmpty()) {
				updateFiles.setFile(updateFile);
				String filename = uploadFile(updateFiles, itemId, newCnt);
				imageUrlList.add("/upload/" + filename);
				newCnt++;
			}
		}
		itemDao.createItemImages(imageUrlList, memberId); // 상품 이미지 레코드 생성
	}

	@Override
	public List<ShareThing> getShareThingList() {	// 공유물품 목록 조회
		List<ShareThing> shareThingList = itemDao.findAllShareThing();
		if (shareThingList != null) {
			for (ShareThing shareThing : shareThingList) {
				String itemId = shareThing.getItem().getId();
				shareThing.setReservationNumber(borrowDao.checkNumberBorrowReservation(itemId));
				
				Member lenderInfo = memberDao.findMember(shareThing.getItem().getMarket().getMember().getId());
				String[] addressSplit = lenderInfo.getAddress().split(" ");
				String addressInfo = addressSplit[0] + " " + addressSplit[1];
				Item item = shareThing.getItem();
				Market market = item.getMarket();
				Member lender = market.getMember();
				lender.setAddress(addressInfo);
				market.setMember(lender);
				item.setMarket(market);
				shareThing.setItem(item);
			}
		}
		return shareThingList;
	}
	
	@Override
	public List<ShareThing> getShareThingListByMember(String memberId) {	// 회원이 업로드한 공유물품 목록 조회
		List<ShareThing> shareThingList = itemDao.findShareThingByMember(memberId);
		if (shareThingList != null) {
			for (ShareThing shareThing : shareThingList) {
				String itemId = shareThing.getItem().getId();
				shareThing.setReservationNumber(borrowDao.checkNumberBorrowReservation(itemId));
				
				Member lenderInfo = memberDao.findMember(memberId);
				String[] addressSplit = lenderInfo.getAddress().split(" ");
				String addressInfo = addressSplit[0] + " " + addressSplit[1];
				Item item = shareThing.getItem();
				Market market = item.getMarket();
				Member lender = market.getMember();
				lender.setAddress(addressInfo);
				market.setMember(lender);
				item.setMarket(market);
				shareThing.setItem(item);
			}
		}
		return shareThingList;
	}

	@Override
	public List<ShareThing> getShareThingListByMarket(int marketId) {	// 마켓의 공유물품 조회
		return itemDao.findShareThingByMarket(marketId);
	}

	@Override
	public ShareThing getShareThing(String itemId) {	// 공유물품 상세정보 조회
		ShareThing shareThing = itemDao.findShareThingByItem(itemId);
		if (shareThing != null) {
			shareThing.setReservationNumber(borrowDao.checkNumberBorrowReservation(itemId));
			
			Member lenderInfo = memberDao.findMember(shareThing.getItem().getMarket().getMember().getId());
			String[] addressSplit = lenderInfo.getAddress().split(" ");
			String addressInfo = addressSplit[0] + " " + addressSplit[1];
			Item item = shareThing.getItem();
			Market market = item.getMarket();
			Member lender = market.getMember();
			lender.setAddress(addressInfo);
			market.setMember(lender);
			item.setMarket(market);
			shareThing.setItem(item);
		}
		return shareThing;
	}
	
	@Override
	@Transactional
	public void uploadShareThing(ShareThing shareThing, FileCommand uploadFiles) throws DataAccessException {	// 공유물품 등록
		
		itemDao.createShareThing(shareThing);	// 공유물품 레코드 생성
		
		// 공유물품 이미지 저장, 이미지 레코드 생성
		String itemId = shareThing.getItem().getId();
		if (itemId != null) {
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
			itemDao.createItemImages(imageUrlList, memberId);	// 공유물품 이미지 레코드 생성
		}
	}

	@Override
	@Transactional
	public void changeShareThingInfo(ShareThing shareThing, FileCommand updateFiles, String[] deleteImages) 
			 throws DataAccessException {	// 공유물품 정보 수정
		
		itemDao.chageItemInfo(shareThing.getItem());	// 품목 레코드 수정
		itemDao.changeShareThingInfo(shareThing);	// 공유물품 레코드 수정	
		
		// 공유물품 이미지 삭제, 이미지 레코드 삭제
		String itemId = shareThing.getItem().getId();
		String memberId = shareThing.getItem().getMarket().getMember().getId();
		itemDao.deleteItemImages(itemId, memberId); // 공유물품 이미지 레코드 삭제
		if (deleteImages != null)
			deleteFileByImages(updateFiles.getPath(), deleteImages);
		
		// 새로운 공유물품 이미지 저장, 이미지 레코드 생성
		List<String> imageUrlList = updateFileByItem(updateFiles.getPath(), itemId);
		int newCnt = imageUrlList.size() + 1;
		for (MultipartFile updateFile : updateFiles.getFiles()) {
			if (!updateFile.isEmpty()) {
				updateFiles.setFile(updateFile);
				String filename = uploadFile(updateFiles, itemId, newCnt);
				imageUrlList.add("/upload/" + filename);
				newCnt++;
			}
		}
		itemDao.createItemImages(imageUrlList, memberId); // 공유물품 이미지 레코드 생성
	}

	@Override
	@Transactional
	public void deleteItem(String itemId, String memberId, String uploadDir) 
			throws DataAccessException, DeleteItemException {	// 업로드한 물품 삭제
		
		// 주문 내역 또는 대여 내역이 존재하는지 확인
		if (itemId.substring(0, 1).equals("G") && itemDao.isExistOrder(itemId)) { // 품목이 상품이면
			System.out.println("주문내역 있음");
			throw new DeleteItemException("주문내역이 존재하여 삭제할 수 없습니다. 비공개로 돌려주시기 바랍니다.");
		}
		else if (itemId.substring(0, 1).equals("S") && itemDao.isExistBorrow(itemId)) { // 품목이 공유물품이면
			System.out.println("대여내역 있음");
			throw new DeleteItemException("대여내역이 존재하여 삭제할 수 없습니다. 비공개로 돌려주시기 바랍니다.");
		}

		
		itemDao.deleteItem(itemId);	// 품목 삭제
		int cnt = itemDao.deleteItemImages(itemId, memberId);	// 품목 이미지 레코드 삭제
		deleteFileByItem(itemId, uploadDir, cnt); // 품목 이미지 삭제
	}
	
	public String uploadFile(FileCommand uploadFiles, String itemId, int cnt) {	// 이미지 파일 업로드 메소드
		String newFilename = "";

		try {       
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
	
	public ArrayList<String> updateFileByItem(String path, String itemId) {	// 특정 상품에 대한 이미지 파일 이름 변경 메소드
		ArrayList<String> imageUrlList = new ArrayList<String>();
		try {
			File f = new File(path);

            if(f.isDirectory()) {
                File[] fList = f.listFiles();
                int cnt = 1;
                for(int i = 0; i < fList.length; i++) {
                	String filename = fList[i].getName();
                	if (filename.contains(itemId)) {
                		String newFilename = itemId + "-" + cnt + ".jpg";
                		File newFile = new File(path, newFilename);
                		fList[i].renameTo(newFile);
                		imageUrlList.add("/upload/" + newFilename);
                		cnt++;
                	}
                }
            }
        }catch(Exception e) {            
            e.printStackTrace();
        }
		
		return imageUrlList;
	}
	
	public void deleteFileByImages(String uploadDir, String[] deleteImages) {	// 특정 이미지에 대한 이미지 파일 삭제 메소드
		try {       
			for (String deleteImage : deleteImages) {
				File file = new File(uploadDir, deleteImage.substring(8));
				if (file.exists())
					file.delete();
			}
		}catch(Exception e) {            
			e.printStackTrace();
		}
	}
	
	public void deleteFileByItem(String itemId, String uploadDir, int cnt) {	// 특정 상품에 대한 이미지 파일 삭제 메소드
		try {       
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
