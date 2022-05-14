package dongduk.cs.pulpul.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dongduk.cs.pulpul.dao.mybatis.mapper.MarketMapper;
import dongduk.cs.pulpul.domain.Market;

@Component
public class MarketDaoImpl implements MarketDao {

	@Autowired
	private MarketMapper marketMapper;

	@Override
	public Market findMarket(int marketId) {
		return marketMapper.selectMarketByMarketId(marketId);
	}

	@Override
	public Market findMarketByMember(String memberId) {
		return marketMapper.selectMarketByMemberId(memberId);
	}

	@Override
	public int createMarket(Market market) {
		return marketMapper.insertMarket(market);
	}

	@Override
	public boolean createMarketImage(Market market) {
		int ck = marketMapper.insertMarketImage(market);
		if (ck > 0) return true;
		return false;
	}

	@Override
	public boolean changeMarketInfo(Market market) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeMarketImage(Market market) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
