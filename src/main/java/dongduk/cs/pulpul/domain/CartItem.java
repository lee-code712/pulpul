package dongduk.cs.pulpul.domain;

import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItem {
	
	private Goods goods; /*상품 정보*/
	@Min(1)
	private int quantity; /*담은 수량*/
	private int price; /*상품 가격(단가x수량)*/

}
