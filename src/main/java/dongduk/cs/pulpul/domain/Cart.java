package dongduk.cs.pulpul.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Cart {

	private String memberId; /*회원 아이디*/
	private List<CartItem> cartItemList; /*담긴 상품*/

}
