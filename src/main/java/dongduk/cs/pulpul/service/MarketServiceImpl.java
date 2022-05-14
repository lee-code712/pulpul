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
	public boolean makeMarket(Market market, MultipartFile[] uploadFile) throws IOException {
		String fileUrl = "";
		for(MultipartFile report : uploadFile) {
			if (!report.isEmpty()) {
				fileUrl = uploadFile(report, market.getId());
				
			}
		}
		return false;
	}

	@Override
	public boolean changeMarketInfo(Market market) {
		// TODO Auto-generated method stub
		return false;
	}
	
	// 파일 업로드 메소드
	public String uploadFile(MultipartFile report, int marketId) {
		String newFilename = "";
		String absolutePath = new File("").getAbsolutePath() + "\\";
		String path = absolutePath + "src\\main\\resources\\static\\uploads";

		// System.out.println(path);
		try {            
            String filename = report.getOriginalFilename();
            String ext = filename.substring(filename.lastIndexOf( "." ));
            newFilename = "MIMG-" + marketId + ext;
            
            File newFile = new File(path, newFilename);
            if (newFile.exists())
            	newFile.delete();
            report.transferTo(newFile);

        }catch(Exception e) {            
            e.printStackTrace();
        }
		
		return newFilename;
	}
}
