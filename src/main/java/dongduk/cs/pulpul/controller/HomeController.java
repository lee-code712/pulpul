package dongduk.cs.pulpul.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@GetMapping("/") 
	public String index() {
		return "redirect:/home";
	}
	
	@GetMapping("/home")
	public ModelAndView home(HttpServletRequest req, ModelAndView mav) {
		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("id");
		
		mav.setViewName("home");
		
		return mav;
	}
}
