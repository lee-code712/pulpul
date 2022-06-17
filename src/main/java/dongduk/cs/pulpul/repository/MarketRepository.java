package dongduk.cs.pulpul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import dongduk.cs.pulpul.domain.Market;

public interface MarketRepository extends JpaRepository<Market, Integer> {
	
	Market findByMemberId(String memberId);
	
	@Modifying(clearAutomatically=true)
	@Query("UPDATE Market m " +
			"SET m.name=?1, m.intro=?2, m.contactableTime=?3, m.policy=?4, m.precaution=?5, m.openStatus=?6 " +
			"WHERE m.id=?7")
	void updateMarket(String name, String intro, String contactableTime, 
			String policy, String precaution, String openStatus, int marketId);
	
}