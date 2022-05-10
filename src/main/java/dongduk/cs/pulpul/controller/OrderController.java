package dongduk.cs.pulpul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {

	/*
	 * 식물 구매 
	 */
	@GetMapping("/purchase")
	public String purchaseForm() {
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
