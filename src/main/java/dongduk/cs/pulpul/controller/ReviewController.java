package dongduk.cs.pulpul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReviewController {

	/*
	 * 리뷰 작성 
	 */
	@PostMapping("/review")
	public String upload() {
		return "redirect:/lookup/goodsDetail";
	}
	
}
