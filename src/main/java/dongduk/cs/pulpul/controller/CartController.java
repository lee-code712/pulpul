package dongduk.cs.pulpul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CartController {

	/*
	 * 장바구니 상품 추가
	 * 조회 - 상세정보 장바구니
	 */
	@RequestMapping("/cart/addItem")
	public String addCartItem() {
		return "redirect:/lookup/goodsDetail";
	}
	
	/*
	 * 장바구니 목록 조회
	 */
	@RequestMapping("/cart/cartList")
	public String cartList() {
		return "/cart/cart";
	}
	
	/*
	 * 특정 상품 장바구니에서 삭제
	 */
	@RequestMapping("/cart/deleteItem")
	public String deleteCartItem() {
		return "redirect:/cart/cartList";
	}

	/*
	 * 특정 마켓의 상품 장바구니에서 삭제
	 */
	@RequestMapping("/cart/deleteItemByMarket")
	public String deleteCartItemByMarket() {
		return "redirect:/cart/cartList";
	}

}
