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

import dongduk.cs.pulpul.domain.Borrow;
import dongduk.cs.pulpul.domain.Member;
import dongduk.cs.pulpul.domain.Order;
import dongduk.cs.pulpul.service.BorrowService;
import dongduk.cs.pulpul.service.MemberService;
import dongduk.cs.pulpul.service.OrderService;
import dongduk.cs.pulpul.service.exception.LoginException;
import dongduk.cs.pulpul.validator.LoginValidator;
import dongduk.cs.pulpul.validator.MemberValidator;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	private final MemberService memberService;
	private final OrderService orderService;
	private final BorrowService borrowService;
	
	@Autowired 
	public MemberController(MemberService memberService, OrderService orderService, BorrowService borrowService) {
		this.memberService = memberService;
		this.orderService = orderService;
		this.borrowService = borrowService;
	}
	
	@Autowired
	private LoginValidator loginValidator;
	public void setValidator(LoginValidator loginValidator) {
		this.loginValidator = loginValidator;
	}
	
	@Autowired
	private MemberValidator memberValidator;
	public void setValidator(MemberValidator memberValidator) {
		this.memberValidator = memberValidator;
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
	public String register(@ModelAttribute("member") Member member, Errors result, Model model, HttpServletRequest req) {
		/*
		 //성공
		 return "member/login";
		 //실패
		 return "member/registerForm";
		 */
		model.addAttribute("member", member);
		memberValidator.validate(member, result);
		if(result.hasErrors()) {
			return "member/registerForm";
		}
		else {
			memberService.register(member);
			return "member/loginForm";
		}
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
	public ModelAndView view(HttpServletRequest req, ModelAndView mav) {
		//회원 정보 수정 폼
		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("id");
		Member member = memberService.getMember(id);
		if (member != null) {
			mav.addObject("member", member);
			
		}
		mav.setViewName("member/myInfoForm");
		return mav;
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
	public ModelAndView myOrderList(HttpServletRequest req, ModelAndView mav) {
		//마이 페이지
		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("id");
		
		// 로그인한 상태인지 확인
		if (id == null) {
			mav.addObject("isNotLogined", true);
			mav.setViewName("redirect:/home");
			return mav;
		}
		
		List<Order> orderList = orderService.getOrderListByMember(id, "buyer");
		if (orderList != null) {
			for (Order order : orderList) {
				System.out.println(order.getId());
			}
			mav.addObject("orderList", orderList);
		}
		
		mav.setViewName("member/mypage");
		return mav;
	}
	
	/*
	 * 마이 페이지 접근 - 대여, 예약 목록 조회
	 */
	@GetMapping("/mypage/borrowList")
	public ModelAndView myBorrowList(HttpServletRequest req, ModelAndView mav) {
		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("id");
		
		List<Borrow> borrowList = borrowService.getBorrowByMember(id, "borrower");

		if (borrowList != null) {
			
			for (Borrow borrow : borrowList) {
				System.out.println(borrow.getId());
			}
			mav.addObject("borrowList", borrowList);
		}
		
		List<Borrow> reservationList = borrowService.getBorrowReservationByMember(id);
		
		if (reservationList != null) {
			for (Borrow borrow : reservationList) {
				System.out.println(borrow.getId());
			}
			mav.addObject("reservationList", reservationList);
		}
		
		mav.setViewName("member/mypage");
		return mav;
	}


}
