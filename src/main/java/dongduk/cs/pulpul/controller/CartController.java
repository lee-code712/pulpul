package dongduk.cs.pulpul.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dongduk.cs.pulpul.domain.CartItem;
import dongduk.cs.pulpul.service.OrderService;
import dongduk.cs.pulpul.service.exception.AddCartException;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	private final OrderService orderSvc;
	
	@Autowired
	public CartController(OrderService orderSvc) {
		this.orderSvc = orderSvc;
	}
	
	@ModelAttribute("cartItem")
	public CartItem formBacking() {
		CartItem cartItem = new CartItem();
		cartItem.setQuantity(1);
		return cartItem;
	}

	/*
	 * 장바구니 상품 추가
	 * 조회 - 상세정보 장바구니 
	 */
	@PostMapping("/addItem")
	public String addCartItem(@ModelAttribute("cartItem") CartItem cartItem,
			HttpSession session, RedirectAttributes rttr) {

		String memberId = (String) session.getAttribute("id");
		
		if(memberId == null) {
			rttr.addFlashAttribute("isNotLogined", true);
			return "redirect:/lookup/goodsDetail";
		}
		
		try {
			boolean successed = orderSvc.addCartItem(memberId, cartItem);
			if (!successed) {
				rttr.addFlashAttribute("addFailed", true);
			}
		} catch(AddCartException e) {
			rttr.addFlashAttribute("addFailed", true);
			rttr.addFlashAttribute("exception", e.getMessage());
		}
		
		return "redirect:/lookup/goodsDetail";
	}
	
	/*
	 * 장바구니 목록 조회
	 */
	@GetMapping("/cartList")
	public String cartList() {
		return "cart/cart";
	}
	
	/*
	 * 특정 상품 장바구니에서 삭제
	 */
	@GetMapping("/deleteItem")
	public String deleteCartItem() {
		return "redirect:/cart/cartList";
	}

	/*
	 * 특정 마켓의 상품 장바구니에서 삭제
	 */
	@GetMapping("/deleteItemByMarket")
	public String deleteCartItemByMarket() {
		return "redirect:/cart/cartList";
	}

}
