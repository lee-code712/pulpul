package dongduk.cs.pulpul.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Goods {

	private Item item; /*상품 품목 정보*/
	@NotBlank
	private String tags; /*상품 태그*/
	private int price;
	@NotBlank
	private String size;
	
	@PositiveOrZero(message = "수량은 0개 이상이어야 합니다.")
	private int salesQuantity; /*총 판매 수량*/
	private int remainQuantity; /*남은 수량*/
	private int shippingFee; /*배송비*/
	private int avgRating; /*평균 별점*/
	
}
