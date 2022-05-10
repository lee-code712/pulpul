package dongduk.cs.pulpul.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Order {

	private int id; /*주문 식별번호*/
	private String orderDate; /*주문일*/
	private int totalPrice; /*총 지불 금액*/
	private int usedPoint; /*사용 포인트*/
	private String cardCompany; /*카드사*/
	private String cardNumber; /*카드 번호*/
	private String expiryDate; /*카드 만료일*/
	private String trakingNumber; /*운송장 번호*/
	private int orderStatus; /*주문 상태*/
	private Member seller; /*판매자 정보*/
	private Member buyer; /*구매자 정보*/
	private List<CartItem> goodsList; /*주문상품정보 리스트*/
	
}
