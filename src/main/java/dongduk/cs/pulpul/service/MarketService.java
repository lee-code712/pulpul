package dongduk.cs.pulpul.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import dongduk.cs.pulpul.domain.Market;

public interface MarketService {
	
	// 마켓 id로 마켓 조회
	Market getMarket(int marketId);
	
	// 회원 id로 마켓 조회
	Market getMarketByMember(String memberId);
	
	// 마켓 생성
	boolean makeMarket(Market market, MultipartFile uploadfile) throws IOException;
	
	// 마켓 정보 수정
	boolean changeMarketInfo(Market market, MultipartFile uploadfile)  throws IOException;
	
}
