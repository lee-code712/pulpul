package dongduk.cs.pulpul.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dongduk.cs.pulpul.domain.Cart;
import dongduk.cs.pulpul.domain.CartItem;
import dongduk.cs.pulpul.domain.Member;
import dongduk.cs.pulpul.domain.Order;
import dongduk.cs.pulpul.service.MemberService;
import dongduk.cs.pulpul.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {

	private final OrderService orderSvc;
	private final MemberService memberSvc;
	
	@Autowired
	public OrderController(OrderService orderSvc, MemberService memberSvc) {
		this.orderSvc = orderSvc;
		this.memberSvc = memberSvc;
	}
	
	@ModelAttribute("order")
	public Order formBacking() {
		return new Order();
	}
	
	/*
	 * 식물 구매 
	 */
	@GetMapping("/purchase")
	public String purchaseForm(@RequestParam("orderGoods") List<String> orderGoods,
			@ModelAttribute("order") Order order, HttpSession session) {
		
		String memberId = (String) session.getAttribute("id");
		
		if (memberId == null) {
			return "redirect:/home";
		}
		
		Cart cart = (Cart) session.getAttribute("cart");
		
		List<CartItem> goodsList = new ArrayList<CartItem>();
		for (CartItem cartItem : cart.getCartItemList()) {
			for (String goodsId : orderGoods) {
				if (goodsId.equals(cartItem.getGoodsId())) {
					goodsList.add(cartItem);
				}
			}
		}
		order.setGoodsList(goodsList);
		
		Member member = memberSvc.getMember(memberId);
		order.setBuyer(member);
		
		//결제 페이지
		return "order/purchase";
	}
	
	@PostMapping("/purchase")
	public void purchase() {
		/*
		 //성공
		 return "redirect:/order/orderDetail";
		 //실패 - 결제 페이지
		 return "order/purchase";
		 */
	}

	/*
	 * 구매 상세내역 조회
	 */
	@GetMapping("/orderDetail")
	public String orderDetail() {
		//주문 상세 정보 조회 페이지
		return "order/orderDetail";
	}
	
	/*
	 * 구매 취소
	 */
	@GetMapping("/orderCancel")
	public String cancel() {
		return "redirect:/member/mypage";
	}
	
	


	/*
	 * 특정 상품 주문 내역에 운송장 번호 입력
	 */
	@PostMapping("/startDeliver")
	public String startDeliver() {
		return "redirect:/market/orderListManage";
	}

}
