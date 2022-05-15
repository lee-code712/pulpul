package dongduk.cs.pulpul.dao.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;

import dongduk.cs.pulpul.domain.Market;

@Mapper
public interface MarketMapper {
	
	Market selectMarketByMarketId(int marketId);
	
	Market selectMarketByMemberId(String memberId);
	
	int insertMarket(Market market);
	
	int insertMarketImage(Market market);
	
	int updateMarket(Market market);
	
	int selectCountOfMarketImage(String memberId);
}
