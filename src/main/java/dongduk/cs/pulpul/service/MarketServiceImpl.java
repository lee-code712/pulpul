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
	public Market getMarket(int marketId) {
		return marketDao.findMarket(marketId);
	}

	@Override
	public Market getMarketByMember(String memberId) {
		return marketDao.findMarketByMember(memberId);
	}

	@Override
	public boolean makeMarket(Market market, MultipartFile uploadFile) throws IOException {
		int marketId = marketDao.createMarket(market);
		if (marketId > 0) { // 정상적으로 레코드를 생성했다면
			market.setId(marketId);
			if (!uploadFile.isEmpty()) {
				String fileUrl = uploadFile(uploadFile, market.getId());
				market.setImageUrl(fileUrl);
				return marketDao.createMarketImage(market);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean changeMarketInfo(Market market, MultipartFile updateFile) throws IOException {
		boolean successed = marketDao.changeMarketInfo(market);
		if (!successed) return false;
		if (!updateFile.isEmpty()) {
			updateFile(updateFile, market.getId());
			return true;
		}
		return false;
	}
	
	// 파일 업로드 메소드
	public String uploadFile(MultipartFile uploadFile, int marketId) {
		String newFilename = "";
		String absolutePath = new File("").getAbsolutePath() + "\\";
		String path = absolutePath + "src\\main\\resources\\static\\uploads";

		// System.out.println(path);
		try {            
            String filename = uploadFile.getOriginalFilename();
            String ext = filename.substring(filename.lastIndexOf( "." ));
            newFilename = "MIMG-" + marketId + ext;
            
            File newFile = new File(path, newFilename);
            if (newFile.exists())
            	newFile.delete();
            uploadFile.transferTo(newFile);

        }catch(Exception e) {            
            e.printStackTrace();
        }
		
		return newFilename;
	}
	
	// 파일 업데이트 메소드
	public void updateFile(MultipartFile updateFile, int marketId) {
		String absolutePath = new File("").getAbsolutePath() + "\\";
		String path = absolutePath + "src\\main\\resources\\static\\uploads";

		// System.out.println(path);
		try {            
            String filename = updateFile.getOriginalFilename();
            String ext = filename.substring(filename.lastIndexOf( "." ));
            String newFilename = "MIMG-" + marketId + ext;
            
            File newFile = new File(path, newFilename);
            updateFile.transferTo(newFile);

        }catch(Exception e) {            
            e.printStackTrace();
        }
	}	
	
}
