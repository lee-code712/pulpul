package dongduk.cs.pulpul.service;

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
	public boolean makeMarket(Market market, MultipartFile[] uploadFile) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeMarketInfo(Market market) {
		// TODO Auto-generated method stub
		return false;
	}
}
