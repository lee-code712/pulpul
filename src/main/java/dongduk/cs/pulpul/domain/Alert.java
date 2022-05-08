package dongduk.cs.pulpul.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Alert {

	private String memberId; /*회원 아이디*/
	private String shareThingsId; /*예약한 공유물품 식별번호*/
	private String alertDate; /*알림일*/
	private String content; /*알림 내용*/
	private int isRead; /*알림 읽음 여부*/
	private String pageUrl; /*공유물품 상세정보 페이지 경로*/
	
}
