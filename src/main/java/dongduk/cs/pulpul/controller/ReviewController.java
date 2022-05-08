package dongduk.cs.pulpul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReviewController {

	/*
	 * 리뷰 작성
	 */
	@RequestMapping("/review")
	public String upload() {
		return "redirect:/lookup/goodsDetail";
	}
	
}
