package dongduk.cs.pulpul.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import dongduk.cs.pulpul.domain.Market;
import dongduk.cs.pulpul.service.MarketService;

@Controller
@RequestMapping("/market")
public class MarketController {

	private final MarketService marketSvc;
	
	@Autowired
	public MarketController(MarketService marketSvc) {
		this.marketSvc = marketSvc;
	}
	
	@ModelAttribute("market")
	public Market formBacking() {
		return new Market();
	}
	
	/*
	 * 마켓 관리 > 마켓 정보 조회
	 */
	@GetMapping("/view")
	public String view(@ModelAttribute("market") Market market, 
			HttpSession session) {
		
		String memberId = (String) session.getAttribute("id");
		
		if(memberId == null) {
			return "redirect:/home";
		}
		
		Market findMarket = marketSvc.getMarketByMember(memberId);
		if (findMarket != null)
			BeanUtils.copyProperties(findMarket, market);
		return "market/marketForm";
		
	}
	
	/*
	 * 마켓 등록
	 */ 
	@PostMapping("/create")
	public void create(@Valid @ModelAttribute("market") Market market, 
			@RequestParam MultipartFile[] uploadFile) {
		
		/*
		 //성공
		 return "redirect:/market/view";
		 //실패
		 return "market/marketForm";
		 */
	}
	
	/*
	 * 마켓 수정
	 */
	@PostMapping("/update")
	public void update() {
		/*
		 //성공
		 return "redirect:/market/view";
		 //실패
		 return "market/marketForm";
		 */
	}
	
	/*
	 * 판매 현황 조회
	 */
	@GetMapping("/orderListManage")
	public String orderListManage(){
		//전체 구매 현황 페이지
		return "market/orderListManage";
	}
	
	/*
     * 판매 상세 내역 조회
	 */
	@GetMapping("/orderDetailManage")
	public String orderDetailManage(){
		//구매 현황 상세내역 조회 페이지
		return "market/orderDetailManage";
	}

	/*
     * 특정 공유 물품 대여 현황 조회
	 */
	@GetMapping("/shareThingBorrowManage")
	public String shareThingBorrowManage(){
		//공유물품 대여 상세 정보 페이지
		return "market/shareThingBorrowManage";
	}


}
