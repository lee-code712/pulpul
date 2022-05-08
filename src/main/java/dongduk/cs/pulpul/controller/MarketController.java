package dongduk.cs.pulpul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MarketController {

	/*
	 * 마켓 등록
	 */
	@RequestMapping("/market/create")
	public void create() {
		/*
		 //성공
		 return "redirect:/market/view";
		 //실패
		 return "/market/marketForm";
		 */
	}
	
	/*
	 * 마켓 수정
	 */
	@RequestMapping("/market/update")
	public void update() {
		/*
		 //성공
		 return "redirect:/market/view";
		 //실패
		 return "/market/marketForm";
		 */
	}
	
	/*
	 * 마켓 관리 > 마켓 정보 조회
	 */
	@RequestMapping("/market/view")
	public String view() {
		//마켓 정보 폼
		return "/market/marketForm";
	}
	
	/*
     * 판매 상세 내역 조회
	 */
	@RequestMapping("/market/orderDetailManage")
	public String orderDetailManage(){
		//구매 현황 상세내역 조회 페이지
		return "/market/orderDetailManage";
	}

	/*
     * 대여 상세 조회
	 */
	@RequestMapping("/market/shareThingsManage")
	public String shareThingsManage(){
		//공유물품 대여 상세 정보 페이지
		return "/market/shareThingsManage";
	}


}
