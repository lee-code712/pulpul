package dongduk.cs.pulpul.controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dongduk.cs.pulpul.domain.Review;
import dongduk.cs.pulpul.service.ReviewService;

@Controller
public class ReviewController implements ApplicationContextAware {

	private WebApplicationContext context;	
	private String uploadDir;
	private final ReviewService reviewSvc;
	
	@Autowired
	public ReviewController(ReviewService reviewSvc) {
		this.reviewSvc = reviewSvc;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		this.context = (WebApplicationContext) appContext;
		this.uploadDir = context.getServletContext().getRealPath("/upload/");
		// System.out.println(this.uploadDir);
	}
	
	/*
	 * 리뷰 작성 
	 */
	@PostMapping("/review")
	public String upload(Review review, FileCommand uploadFiles, Model model,
			RedirectAttributes rttr) {
		
		uploadFiles.setPath(uploadDir);
		boolean successed = reviewSvc.addReview(review, uploadFiles);
		if (!successed) {
			rttr.addFlashAttribute("uplaodFalid", true);
		}
		
		return "redirect:/lookup/goodsDetail?itemId=" + review.getItem().getId();
	}
	
}
