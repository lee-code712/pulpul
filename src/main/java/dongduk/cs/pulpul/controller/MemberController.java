package dongduk.cs.pulpul.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dongduk.cs.pulpul.domain.Member;
import dongduk.cs.pulpul.domain.Order;
import dongduk.cs.pulpul.service.MemberService;
import dongduk.cs.pulpul.service.OrderService;
import dongduk.cs.pulpul.service.exception.LoginException;
import dongduk.cs.pulpul.validator.LoginValidator;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	private final MemberService memberService;
	private final OrderService orderService;
	
	@Autowired 
	public MemberController(MemberService memberService, OrderService orderService) {
		this.memberService = memberService;
		this.orderService = orderService;
	}
	
	@Autowired
	private LoginValidator loginValidator;
	public void setValidator(LoginValidator loginValidator) {
		this.loginValidator = loginValidator;
	}
	
	@ModelAttribute("member")
	public Member formBacking() {
		return new Member();
	}

	/* 
	 * 회원 가입
	 */
	@GetMapping("/register")
	public String registerForm() {
		//회원 가입 폼
		return "member/registerForm";
	}
	
	@PostMapping("/register")
	public void register() {
		/*
		 //성공
		 return "member/login";
		 //실패
		 return "member/registerForm";
		 */
	}

	/*
	 * 로그인
	 */
	@GetMapping("/login")
	public String loginForm() {
		return "/member/loginForm";
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute("member") Member member, Errors result, Model model, HttpServletRequest req, RedirectAttributes rttr) {
		/*
		 //성공
		 return "redirect:/home";
		 //실패 - 로그인 폼
		 return "member/loginForm";
		 */
		model.addAttribute("member", member);
		loginValidator.validate(member, result);
		if(result.hasErrors()) {
			return "member/loginForm";
		}
		
		try {
			member = memberService.login(member);
			
			HttpSession session = req.getSession();
			session.setAttribute("id", member.getId());
			
			return "redirect:/home";
		} catch (LoginException e) {
			e.printStackTrace(); 
			
			rttr.addFlashAttribute("loginFailed", true); 
			rttr.addFlashAttribute("exception", e.getMessage());
			
			result.reject("invalidIdOrPassword", new Object[] {member.getId()}, null);
			return "member/loginForm";
		}
	}
	
	/*
	 * 회원 정보 수정
	 */
	@GetMapping("/view")
	public String view() {
		//회원 정보 수정 폼
		return "member/myInfoForm";
	}
	
	@PostMapping("/update")
	public void update() {
		/*
		 //성공
		 return "redirect:/member/update";
		 //실패 - 회원 정보 수정 폼
		 return "member/myInfoForm";
		 */
	}
	
	/*
	 * 로그아웃
	 */
	@GetMapping("/logout")
	public String logout(HttpServletRequest req, RedirectAttributes rttr) {
		HttpSession session = req.getSession();
		session.invalidate();
		
		return "redirect:/home";
	}

	/*
	회원 탈퇴
	*/
	@PostMapping("/resign")
	public void resign() {
		/*
		 //성공
		 return "redirect:/home";
		 //실패
		 return "redirect:/member/mypage/orderList";
		 */
	}
	
	/*
	 * 마이 페이지 접근 - 구매 목록 조회
	 */
	@GetMapping("/mypage/orderList")
	public ModelAndView myOrderList(HttpServletRequest req, RedirectAttributes rttr, ModelAndView mav) {
		//마이 페이지
		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("id");
		
		List<Order> orderList = orderService.getOrderListByMember(id, "buyer");
		
		if (orderList != null) {
			mav.addObject("orderList", orderList);
		}
		
		mav.setViewName("member/mypage");
		return mav;
	}
	
	/*
	 * 마이 페이지 접근 - 대여, 예약 목록 조회
	 */
	@GetMapping("/mypage/borrowList")
	public String myBorrowList() {
		return "member/mypage";
	}


}
