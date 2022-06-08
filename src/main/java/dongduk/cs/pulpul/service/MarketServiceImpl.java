package dongduk.cs.pulpul.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import dongduk.cs.pulpul.controller.FileCommand;
import dongduk.cs.pulpul.dao.MarketDao;
import dongduk.cs.pulpul.domain.Market;

@Service
public class MarketServiceImpl implements MarketService {

	@Autowired
	private MarketDao marketDao;

	@Override
	public Market getMarket(int marketId) {
		Market market = marketDao.findMarket(marketId);
		String[] marketAddress = market.getMember().getAddress().split(" ");
		market.getMember().setAddress(marketAddress[0] + " " + marketAddress[1]);
		if (market != null) {
			market.setImageUrl(marketDao.findMarketImage(market.getMember().getId()));
		}
		return market;
	}

	@Override
	public Market getMarketByMember(String memberId) {
		Market market = marketDao.findMarketByMember(memberId);
		if (market != null) {
			market.setImageUrl(marketDao.findMarketImage(memberId));
		}
		return market;
	}

	@Override
	public void makeMarket(Market market, FileCommand uploadFile) throws DataAccessException {
		
		marketDao.createMarket(market);	// 마켓 레코드 생성
		
		if (!uploadFile.getFile().isEmpty()) {
			String filename = uploadFile(uploadFile, market.getId());	// 마켓 이미지 저장
			market.setImageUrl("/upload/" + filename);
			marketDao.createMarketImage(market);	// 마켓 이미지 레코드 생성
		}
	}

	@Override
	public void changeMarketInfo(Market market, FileCommand updateFile) throws DataAccessException {
		
		marketDao.changeMarketInfo(market);	// 마켓 레코드 수정

		if (!updateFile.getFile().isEmpty()) {
			String imageSrc = marketDao.findMarketImage(market.getMember().getId());
			if (imageSrc != null) {
				updateFile(updateFile, market.getId());	// 마켓 이미지 레코드가 존재하면 마켓 이미지 변경
			}
			else {
				String filename = uploadFile(updateFile, market.getId());
				market.setImageUrl("/upload/" + filename);
				marketDao.createMarketImage(market);	// 마켓 이미지 레코드가 존재하지 않으면 마켓 이미지 레코드 생성
			}
		}
	}
	
	// 이미지 파일 업로드 메소드
	public String uploadFile(FileCommand uploadFile, int marketId) {
		String newFilename = "";
		try {       
            newFilename = "MIMG-" + marketId + ".jpg";
            
            File newFile = new File(uploadFile.getPath() + newFilename);
            if (newFile.exists())
            	newFile.delete();
            uploadFile.getFile().transferTo(newFile);

        }catch(Exception e) {            
            e.printStackTrace();
        }
		
		return newFilename;
	}
	
	// 이미지 파일 업데이트 메소드
	public void updateFile(FileCommand updateFile, int marketId) {
		try {            
            String newFilename = "MIMG-" + marketId + ".jpg";
            
            File newFile = new File(updateFile.getPath() + newFilename);
            updateFile.getFile().transferTo(newFile);

        }catch(Exception e) {            
            e.printStackTrace();
        }
	}	
	
}
