package dongduk.cs.pulpul.domain;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Goods {

	@Valid
	private Item item; /*상품 품목 정보*/
	@NotBlank
	private String tags; /*상품 태그*/
	private int price;
	@NotBlank
	private String size;
	
	@Min(1)
	private int salesQuantity; /*총 판매 수량*/
	private int remainQuantity; /*남은 수량*/
	private int shippingFee; /*배송비*/
	private int avgRating; /*평균 별점*/
	
}
