package dongduk.cs.pulpul.dao.mybatis.mapper;

import dongduk.cs.pulpul.domain.Market;

public interface MarketMapper {
	
	Market selectMarketByMarketId(int marketId);
	
	Market selectMarketByMemberId(String memberId);
	
	int insertMarket(Market market);
	
	int insertMarketImage(Market market);
	
	int updateMarket(Market market);
	
	int updateMarketImage(Market market);
	
}
