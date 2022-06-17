package dongduk.cs.pulpul.service;

import java.io.File;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dongduk.cs.pulpul.controller.FileCommand;
import dongduk.cs.pulpul.domain.Image;
import dongduk.cs.pulpul.domain.Market;
import dongduk.cs.pulpul.repository.ImageRepository;
import dongduk.cs.pulpul.repository.MarketRepository;

@Service
public class MarketServiceImpl implements MarketService {
	
	@Autowired
	private MarketRepository marketRepo;
	@Autowired
	private ImageRepository imageRepo;

	@Override
	public Market getMarket(int marketId) {
		Optional<Market> result = marketRepo.findById(marketId);
		Market market = null;
		if(result.isPresent()) market = result.get();
		if (market != null) {
			String[] marketAddress = market.getMember().getAddress().split(" ");
			market.getMember().setAddress(marketAddress[0] + " " + marketAddress[1]);
			
			Image image = imageRepo.findByMemberIdAndCategoryId(market.getMember().getId(), "MIMG");
			if (image != null) {
				market.setImageUrl(image.getImageSrc());
			}
		}
		return market;
	}

	@Override
	public Market getMarketByMember(String memberId) {
		Market market = marketRepo.findByMemberId(memberId);
		if (market != null) {
			Image image = imageRepo.findByMemberIdAndCategoryId(market.getMember().getId(), "MIMG");
			if (image != null) {
				market.setImageUrl(image.getImageSrc());
			}
		}
		return market;
	}

	@Override
	@Transactional
	public void makeMarket(Market market, FileCommand uploadFile) throws DataAccessException {
		
		marketRepo.save(market);	// 마켓 레코드 생성
		
		if (!uploadFile.getFile().isEmpty()) {
			String filename = uploadFile(uploadFile, market.getId());	// 마켓 이미지 저장
			Image image = new Image(market.getMember().getId(), "MIMG", "/upload/" + filename);
			imageRepo.save(image);	// 마켓 이미지 레코드 생성
		}
	}

	@Override
	@Transactional
	public void changeMarketInfo(Market market, FileCommand updateFile) throws DataAccessException {
		
		marketRepo.updateMarket(market.getName(), market.getIntro(), market.getContactableTime(),
				market.getPolicy(), market.getPrecaution(), market.getOpenStatus(), market.getId());	// 마켓 레코드 수정

		if (!updateFile.getFile().isEmpty()) {
			Image image = imageRepo.findByMemberIdAndCategoryId(market.getMember().getId(), "MIMG");
			if (image != null) {
				updateFile(updateFile, market.getId());	// 마켓 이미지 레코드가 존재하면 마켓 이미지 변경
			}
			else {
				String filename = uploadFile(updateFile, market.getId());
				Image newImage = new Image(market.getMember().getId(), "MIMG", "/upload/" + filename);
				imageRepo.save(newImage);	// 마켓 이미지 레코드 생성
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
