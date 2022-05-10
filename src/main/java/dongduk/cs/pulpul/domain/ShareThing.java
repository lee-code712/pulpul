package dongduk.cs.pulpul.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ShareThing {

	private Item item; /*공유물품 품목 정보*/
	private String precaution; /*대여 시 유의사항*/
	private int isBorrowed; /*대여 여부*/
	private int reservationNumber; /*예약자 수*/
	
}
