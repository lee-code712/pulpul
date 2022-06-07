package dongduk.cs.pulpul.dao.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dongduk.cs.pulpul.dao.MarketDao;
import dongduk.cs.pulpul.dao.mybatis.mapper.MarketMapper;
import dongduk.cs.pulpul.domain.Market;

@Repository
public class MybatisMarketDao implements MarketDao {

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
	public String findMarketImage(String memberId) {
		return marketMapper.selectMarketImage(memberId);
	}

	@Override
	public boolean createMarket(Market market) {
		int ck = marketMapper.insertMarket(market);
		if (ck > 0) return true;
		return false;
	}

	@Override
	public boolean createMarketImage(Market market) {
		int ck = marketMapper.insertMarketImage(market);
		if (ck > 0) return true;
		return false;
	}

	@Override
	public boolean changeMarketInfo(Market market) {
		int ck = marketMapper.updateMarket(market);
		if (ck > 0) return true;
		return false;
	}
	
}