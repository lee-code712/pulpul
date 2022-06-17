package dongduk.cs.pulpul.service;

import java.io.File;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dongduk.cs.pulpul.controller.FileCommand;
import dongduk.cs.pulpul.dao.MarketDao;
import dongduk.cs.pulpul.domain.Image;
import dongduk.cs.pulpul.domain.Market;
import dongduk.cs.pulpul.repository.ImageRepository;
import dongduk.cs.pulpul.repository.MarketRepository;

@Service
public class MarketServiceImpl implements MarketService {
	
	@Autowired
	private MarketRepository marketRepository;
	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private MarketDao marketDao;

	@Override
	public Market getMarket(int marketId) {
		Optional<Market> result = marketRepository.findById(marketId);
		Market market = null;
		if(result.isPresent()) market = result.get();
		if (market != null) {
			String[] marketAddress = market.getMember().getAddress().split(" ");
			market.getMember().setAddress(marketAddress[0] + " " + marketAddress[1]);
			
			Image image = imageRepository.findByMemberIdAndCategoryId(market.getMember().getId(), "MIMG");
			market.setImageUrl(image.getImageUrl());
		}
		return market;
	}

	@Override
	public Market getMarketByMember(String memberId) {
		Market market = marketDao.findMarketByMember(memberId);
		if (market != null) {
//			market.setImageUrl(marketDao.findMarketImage(memberId));
		}
		return market;
	}

	@Override
	@Transactional
	public void makeMarket(Market market, FileCommand uploadFile) throws DataAccessException {
		
		marketDao.createMarket(market);	// 마켓 레코드 생성
		
		if (!uploadFile.getFile().isEmpty()) {
			String filename = uploadFile(uploadFile, market.getId());	// 마켓 이미지 저장
//			market.setImageUrl("/upload/" + filename);
			marketDao.createMarketImage(market);	// 마켓 이미지 레코드 생성
		}
	}

	@Override
	@Transactional
	public void changeMarketInfo(Market market, FileCommand updateFile) throws DataAccessException {
		
		marketDao.changeMarketInfo(market);	// 마켓 레코드 수정

		if (!updateFile.getFile().isEmpty()) {
			String imageSrc = marketDao.findMarketImage(market.getMember().getId());
			if (imageSrc != null) {
				updateFile(updateFile, market.getId());	// 마켓 이미지 레코드가 존재하면 마켓 이미지 변경
			}
			else {
				String filename = uploadFile(updateFile, market.getId());
//				market.setImageUrl("/upload/" + filename);
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
