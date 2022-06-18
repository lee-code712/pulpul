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
	public void createMarket(Market market) {
		marketMapper.insertMarket(market);
	}

	@Override
	public void createMarketImage(Market market) {
		marketMapper.insertMarketImage(market);
	}

	@Override
	public void changeMarketInfo(Market market) {
		marketMapper.updateMarket(market);
	}

	@Override
	public void deleteMarketImage(String memberId) {
		marketMapper.deleteMarketImage(memberId);
		
	}
	
}
