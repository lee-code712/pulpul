package dongduk.cs.pulpul.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dongduk.cs.pulpul.domain.Cart;
import dongduk.cs.pulpul.domain.CartItem;
import dongduk.cs.pulpul.domain.Member;
import dongduk.cs.pulpul.domain.Order;
import dongduk.cs.pulpul.service.MemberService;
import dongduk.cs.pulpul.service.OrderService;
import dongduk.cs.pulpul.service.exception.CancelOrderException;

@Controller
@SessionAttributes("order")
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
	
	@ModelAttribute("cardCompanyList")
	public String[] createCardType() {
		return new String[]{"KB 국민", "비씨(페이북)", "신한카드", "NH농협", "1QPay(하나)", "씨티카드", "롯데카드", "UnionPay"};
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
		if (cart == null) {
			return "redirect:/cart/cartList";
		}
		
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
	public String purchase(@Valid @ModelAttribute("order") Order order, BindingResult result,
			Model model, HttpSession session) {
		
		if (result.hasErrors())
			return "order/purchase";
		
//		System.out.println(order.toString());
		
		int orderId = orderSvc.order(order);
		if (orderId == 0) {
			model.addAttribute("orderFalid", true);
			return "order/purchase";
		}
		
		int newCartItemCnt = (int) session.getAttribute("cartItemCnt") - order.getGoodsList().size();
		session.setAttribute("cartItemCnt", newCartItemCnt);
		session.removeAttribute("cart"); // cart session attribute 삭제
		
		return "redirect:/order/orderDetail?orderId=" + orderId;
	}

	/*
	 * 구매 상세내역 조회
	 */
	@GetMapping("/orderDetail")
	public String orderDetail(@RequestParam("orderId") int orderId, Model model, SessionStatus status) {
		
		Order order = orderSvc.getOrder(orderId);
		model.addAttribute(order);
		
		if (!status.isComplete()) {
			status.setComplete(); // order 객체 참조 삭제
		}

		return "order/orderDetail";
	}
	
	/*
	 * 구매 취소
	 */
	@GetMapping("/orderCancel")
	public String cancel(@RequestParam("orderId") int orderId, HttpSession session,
			RedirectAttributes rttr) {
		
		try {
			boolean successed = orderSvc.cancelOrder(orderId);
			if (!successed) {
				rttr.addFlashAttribute("cancelFailed", true);
			}
		} catch (CancelOrderException e) {
			rttr.addFlashAttribute("cancelFailed", true);
			rttr.addFlashAttribute("exception", e.getMessage());
		}
			
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
