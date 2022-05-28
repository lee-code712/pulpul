package dongduk.cs.pulpul.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dongduk.cs.pulpul.domain.Cart;
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

	/*
	 * 장바구니 상품 추가
	 * 조회 - 상세정보 장바구니 
	 */
	@PostMapping("/addItem")
	public String addCartItem(CartItem cartItem, HttpSession session, RedirectAttributes rttr) {

		String memberId = (String) session.getAttribute("id");
				
		if(memberId == null) {
			rttr.addFlashAttribute("isNotLogined", true);
			return "redirect:/lookup/goodsDetail?itemId=" + cartItem.getGoods().getItem().getId();
		}
		
		try {
			boolean successed = orderSvc.addCartItem(memberId, cartItem);
			if (!successed) {
				rttr.addFlashAttribute("addFailed", true);
			}
			else {
				int newCartItemCnt = (int) session.getAttribute("cartItemCnt") + 1;
				session.setAttribute("cartItemCnt", newCartItemCnt);
			}
		} catch(AddCartException e) {
			rttr.addFlashAttribute("addFailed", true);
			rttr.addFlashAttribute("exception", e.getMessage());
		}
		
		return "redirect:/lookup/goodsDetail?itemId=" + cartItem.getGoods().getItem().getId();
	}
	
	/*
	 * 장바구니 목록 조회
	 */
	@GetMapping("/cartList")
	public String cartList(HttpSession session, Model model) {
		
		String memberId = (String) session.getAttribute("id");
		
		if(memberId == null) {
			return "redirect:/home";
		}
		
		Cart cart = orderSvc.getCart(memberId);
		if (cart != null) {
			model.addAttribute(cart);
		}	
		return "cart/cart";
	}
	
	/*
	 * 특정 상품 장바구니에서 삭제
	 */
	@GetMapping("/deleteItem")
	public String deleteCartItem(@RequestParam("itemId") String itemId, HttpSession session,
			RedirectAttributes rttr) {
		
		String memberId = (String) session.getAttribute("id");
		if(memberId == null) {
			return "redirect:/home";
		}
		
		boolean successed = orderSvc.deleteCartItem(memberId, itemId);
		if (!successed) {
			rttr.addFlashAttribute("deleteFailed", true);
		}
		else {
			int newCartItemCnt = (int) session.getAttribute("cartItemCnt") - 1;
			session.setAttribute("cartItemCnt", newCartItemCnt);
		}
		
		return "redirect:/cart/cartList";
	}

	/*
	 * 특정 마켓의 상품 장바구니에서 삭제
	 */
	@GetMapping("/deleteItemByMarket")
	public String deleteCartItemByMarket(@RequestParam("marketId") int marketId, HttpSession session,
			RedirectAttributes rttr) {
		
		String memberId = (String) session.getAttribute("id");
		if(memberId == null) {
			return "redirect:/home";
		}
		
		int deleteCnt = orderSvc.deleteCartItemByMarket(memberId, marketId);
		if (deleteCnt <= 0) {
			rttr.addFlashAttribute("deleteFailed", true);
		}
		else {
			int newCartItemCnt = (int) session.getAttribute("cartItemCnt") - deleteCnt;
			session.setAttribute("cartItemCnt", newCartItemCnt);
		}
		return "redirect:/cart/cartList";
	}

}
