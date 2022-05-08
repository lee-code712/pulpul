package dongduk.cs.pulpul.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Market {

	private int id; /*마켓 식별 번호*/
	private String name; /*마켓 이름*/
	private String intro; /*마켓 소개*/
	private String contactableTime; /*연락가능 시간*/
	private String policy; /*교환/반품/환불 정책*/
	private String precaution; /*구매 전 유의사항*/
	private int openStatus; /*마켓 공개여부*/
	private Member member; /*회원정보*/
	private String imageUrl; /*마켓 이미지 경로*/
	
}
