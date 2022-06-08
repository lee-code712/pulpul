package dongduk.cs.pulpul.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import dongduk.cs.pulpul.domain.Market;
import dongduk.cs.pulpul.domain.Member;
import dongduk.cs.pulpul.domain.Order;
import dongduk.cs.pulpul.service.MarketService;
import dongduk.cs.pulpul.service.OrderService;

@Controller
@RequestMapping("/market")
public class MarketController implements ApplicationContextAware {
	
	private WebApplicationContext context;	
	private String uploadDir;
	private final MarketService marketSvc;
	private final OrderService orderSvc;
	
	@Autowired
	public MarketController(MarketService marketSvc, OrderService orderSvc) {
		this.marketSvc = marketSvc;
		this.orderSvc = orderSvc;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		this.context = (WebApplicationContext) appContext;
		this.uploadDir = context.getServletContext().getRealPath("/upload/");
		// System.out.println(this.uploadDir);
	}
	
	@ModelAttribute("market")
	public Market formBacking(HttpSession session) {
		Member member = new Member();
		member.setId((String) session.getAttribute("id"));	// memberId 저장
		Market market = new Market();
		market.setMember(member);
		market.setOpenStatus("0"); //openStatus를 0으로 초기화
		return market;
	}
	
	/*
	 * 마켓 관리 > 마켓 정보 조회
	 */
	@GetMapping("/view")
	public String view(@ModelAttribute("market") Market market) {
		
		String memberId = market.getMember().getId();

		Market findMarket = marketSvc.getMarketByMember(memberId);
		if (findMarket != null)
			BeanUtils.copyProperties(findMarket, market);
		return "market/marketForm";
		
	}
	
	/*
	 * 마켓 등록
	 */ 
	@PostMapping("/create")
	public String create(@Valid @ModelAttribute("market") Market market, BindingResult result,
			FileCommand uploadFile, Model model) {
		
		if (result.hasErrors())
			return "market/marketForm";
		
		uploadFile.setPath(uploadDir);
		marketSvc.makeMarket(market, uploadFile); 

		return "redirect:/market/view";
		
	}
	
	/*
	 * 마켓 수정
	 */
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("market") Market market, BindingResult result,
			FileCommand updateFile, Model model) {

		if (result.hasErrors())
			return "market/marketForm";
		
		updateFile.setPath(uploadDir);
		marketSvc.changeMarketInfo(market, updateFile); 

		return "redirect:/market/view";
	}
	
	/*
	 * 판매 현황 조회
	 */
	@GetMapping("/orderListManage")
	public String orderListManage(HttpSession session, Model model) {
		
		String memberId = (String) session.getAttribute("id");
		
		List<Order> orderList = orderSvc.getOrderListByMember(memberId, "seller");
		if (orderList != null) {
			model.addAttribute("orderList", orderList);
		}

		return "market/orderListManage";
	}
	
	/*
     * 판매 상세 내역 조회
	 */
	@GetMapping("/orderDetailManage")
	public String orderDetailManage(@RequestParam("orderId") int orderId, Model model) {
		
		Order order = orderSvc.getOrder(orderId);
		model.addAttribute("order", order);
		
		return "market/orderDetailManage";
	}

	/*
     * 특정 공유 물품 대여 현황 조회
	 */
	@GetMapping("/shareThingBorrowManage")
	public String shareThingBorrowManage() {
		//공유물품 대여 상세 정보 페이지
		return "market/shareThingBorrowManage";
	}

}
