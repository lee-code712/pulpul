package dongduk.cs.pulpul.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import dongduk.cs.pulpul.domain.Market;
import dongduk.cs.pulpul.service.MarketService;

@Controller
@RequestMapping("/market")
public class MarketController implements ApplicationContextAware {
	
	private final MarketService marketSvc;
	private WebApplicationContext context;	
	private String uploadDir;
	
	@Autowired
	public MarketController(MarketService marketSvc) {
		this.marketSvc = marketSvc;
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
		market.setOpenStatus("0"); //openStatus를 0(비공개)로 초기화
		return market;
	}
	
	/*
	 * 마켓 관리 > 마켓 정보 조회
	 */
	@GetMapping("/view")
	public String view(@ModelAttribute("market") Market market) {
		Market findMarket = marketSvc.getMarketByMember(market.getMemberId());
		if (findMarket != null) {
			BeanUtils.copyProperties(findMarket, market);
		}
		
		return "market/marketForm";
	}
	
	/*
	 * 마켓 등록
	 */ 
	@PostMapping("/create")
	public String create(@Valid @ModelAttribute("market") Market market, BindingResult result, 
			FileCommand uploadFile) {
		if (result.hasErrors()) {
			return "market/marketForm";
		}
		
		uploadFile.setPath(uploadDir);
		marketSvc.makeMarket(market, uploadFile); 

		return "redirect:/market/view";
	}
	
	/*
	 * 마켓 수정
	 */
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("market") Market market, BindingResult result,
			FileCommand updateFile) {
		if (result.hasErrors()) {
			return "market/marketForm";
		}
		
		updateFile.setPath(uploadDir);
		marketSvc.changeMarketInfo(market, updateFile); 

		return "redirect:/market/view";
	}

}
