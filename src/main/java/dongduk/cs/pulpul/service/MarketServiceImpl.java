package dongduk.cs.pulpul.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dongduk.cs.pulpul.dao.MarketDao;
import dongduk.cs.pulpul.domain.Market;

@Service
public class MarketServiceImpl implements MarketService {

	private final MarketDao marketDao;
	
	@Autowired
	public MarketServiceImpl(MarketDao marketDao) {
		this.marketDao = marketDao;
	}

	@Override
	public Market getMarket(int marketId, String memberId) {
		Market market = marketDao.findMarket(marketId);
		if (market != null)
			market.setImageUrl(marketDao.findMarketImage(memberId));
		return market;
	}

	@Override
	public Market getMarketByMember(String memberId) {
		Market market = marketDao.findMarketByMember(memberId);
		if (market != null)
			market.setImageUrl(marketDao.findMarketImage(memberId));
		return market;
	}

	@Override
	public boolean makeMarket(Market market, MultipartFile uploadFile, String uploadDir) throws IOException {
		int marketId = marketDao.createMarket(market);
		if (marketId > 0) { // 정상적으로 레코드를 생성했다면
			market.setId(marketId);
			if (!uploadFile.isEmpty()) {
				String filename = uploadFile(uploadFile, market.getId(), uploadDir);
				market.setImageUrl("/upload/" + filename);
				return marketDao.createMarketImage(market);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean changeMarketInfo(Market market, MultipartFile updateFile, String uploadDir) throws IOException {
		boolean successed = marketDao.changeMarketInfo(market);
		if (!successed) return false;
		if (!updateFile.isEmpty()) {
			String imageSrc = marketDao.findMarketImage(market.getMember().getId());
			if (imageSrc != null) {
				updateFile(updateFile, market.getId(), uploadDir);
			}
			else {
				String filename = uploadFile(updateFile, market.getId(), uploadDir);
				market.setImageUrl("/upload/" + filename);
				return marketDao.createMarketImage(market);
			}
			return true;
		}
		return false;
	}
	
	// 파일 업로드 메소드
	public String uploadFile(MultipartFile uploadFile, int marketId, String uploadDir) {
		String newFilename = "";
		// System.out.println(path);
		try {       
//			  // 확장자를 jpg로 제한
//            String filename = uploadFile.getOriginalFilename();
//            String ext = filename.substring(filename.lastIndexOf( "." ));
            newFilename = "MIMG-" + marketId + ".jpg";
            
            File newFile = new File(uploadDir + newFilename);
            if (newFile.exists())
            	newFile.delete();
            uploadFile.transferTo(newFile);

        }catch(Exception e) {            
            e.printStackTrace();
        }
		
		return newFilename;
	}
	
	// 파일 업데이트 메소드
	public void updateFile(MultipartFile updateFile, int marketId, String uploadDir) {
		// System.out.println(path);
		try {            
//			  // 확장자를 jpg로 제한
//            String filename = updateFile.getOriginalFilename();
//            String ext = filename.substring(filename.lastIndexOf( "." ));
            String newFilename = "MIMG-" + marketId + ".jpg";
            
            File newFile = new File(uploadDir + newFilename);
            updateFile.transferTo(newFile);

        }catch(Exception e) {            
            e.printStackTrace();
        }
	}	
	
}
