package dongduk.cs.pulpul.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

import dongduk.cs.pulpul.domain.Borrow;
import dongduk.cs.pulpul.domain.Market;
import dongduk.cs.pulpul.domain.Order;
import dongduk.cs.pulpul.service.BorrowService;
import dongduk.cs.pulpul.service.MarketService;
import dongduk.cs.pulpul.service.OrderService;

@Controller
@RequestMapping("/market")
@SessionAttributes({"orderList", "borrowList"})
public class MarketController implements ApplicationContextAware {
	
	private WebApplicationContext context;	
	private String uploadDir;
	private final MarketService marketSvc;
	private final OrderService orderSvc;
	private final BorrowService borrowSvc;
	
	@Autowired
	public MarketController(MarketService marketSvc, OrderService orderSvc, BorrowService borrowSvc) {
		this.marketSvc = marketSvc;
		this.orderSvc = orderSvc;
		this.borrowSvc = borrowSvc;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		this.context = (WebApplicationContext) appContext;
		this.uploadDir = context.getServletContext().getRealPath("/upload/");
		// System.out.println(this.uploadDir);
	}
	
	@ModelAttribute("market")
	public Market formBacking(HttpSession session) {
		Market market = new Market();
		market.setMemberId((String) session.getAttribute("id"));
		market.setOpenStatus("0"); //openStatus를 0으로 초기화
		return market;
	}
	
	/*
	 * 마켓 관리 > 마켓 정보 조회
	 */
	@GetMapping("/view")
	public String view(@ModelAttribute("market") Market market) {
		
		String memberId = market.getMemberId();

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
     * 특정 공유 물품 대여 현황 조회
	 */
	@GetMapping("/shareThingBorrowManage")
	public String shareThingBorrowManageView(@RequestParam("itemId") String itemId, Model model) {
		//공유물품 대여 상세 정보 페이지
		PagedListHolder<Borrow> borrowList = new PagedListHolder<Borrow>(borrowSvc.getBorrowByItem(itemId));
		borrowList.setPageSize(5);
		model.addAttribute("borrowList", borrowList);
		
		return "market/shareThingBorrowManage";
	}
	
	@GetMapping("/shareThingBorrowManage2")
	public String shareThingBorrowManageView2(@RequestParam("pageType") String page, 
			@ModelAttribute("borrowList") PagedListHolder<Borrow> borrowList, Model model) {
		
		if ("next".equals(page)) {
			borrowList.nextPage();
		}
		else if ("previous".equals(page)) {
			borrowList.previousPage();
		}

		model.addAttribute("borrowList", borrowList);
		
		return "market/shareThingBorrowManage";
	}

}
