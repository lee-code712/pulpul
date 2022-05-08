package dongduk.cs.pulpul.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Goods {

	private Item item; /*상품 품목 정보*/
	private String tags; /*상품 태그*/
	private int price;
	private String size;
	private int salesQuantity; /*총 판매 수량*/
	private int remainQuantity; /*남은 수량*/
	private int shippingFee; /*배송비*/
	private int avgRating; /*평균 별점*/
	
}
