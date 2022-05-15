package dongduk.cs.pulpul.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import dongduk.cs.pulpul.domain.Goods;
import dongduk.cs.pulpul.service.ItemService;
import dongduk.cs.pulpul.service.MemberService;

@Controller
public class HomeController {

	private final ItemService itemService;
	
	@Autowired 
	public HomeController(ItemService itemService) {
		this.itemService = itemService;
	}
	
	@GetMapping("/") 
	public String index() {
		return "redirect:/home";
	}
	
	@GetMapping("/home")
	public ModelAndView home(HttpServletRequest req, ModelAndView mav) {
		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("id");
		
		// 로그인한 상태인지 확인
		if (id == null) {
			mav.addObject("isNotLogined", true);
		}
		
		mav.setViewName("home");
		
		// 가장 최근 3개의 상품 가져오기
		List<Goods> newGoodsList = itemService.getNewGoodsList();
		if (newGoodsList != null) {
			mav.addObject("newGoodsList", newGoodsList);
		}
		
		return mav;
	}
}
