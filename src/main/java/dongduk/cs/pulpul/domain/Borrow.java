package dongduk.cs.pulpul.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Borrow {

	private int id; /*대여 식별번호*/
	private ShareThings shareThings; /*물품 정보*/
	private String borrowDate; /*대여일*/
	private String returnDate; /*반납일*/
	private String trackingNumber; /*운송장 번호*/
	private int borrowStatus; /*대여 상태*/
	private int isExtended; /*대여기한 연장 여부*/
	private Member lender; /*대여해 준 회원 정보*/
	private Member borrower; /*대여받은 회원정보*/
	private int isFirstBooker; /*첫번째 예약자 여부*/
	
}
