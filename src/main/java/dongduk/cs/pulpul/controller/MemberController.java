package dongduk.cs.pulpul.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dongduk.cs.pulpul.domain.Borrow;
import dongduk.cs.pulpul.domain.Market;
import dongduk.cs.pulpul.domain.Member;
import dongduk.cs.pulpul.domain.Order;
import dongduk.cs.pulpul.service.BorrowService;
import dongduk.cs.pulpul.service.MemberService;
import dongduk.cs.pulpul.service.OrderService;
import dongduk.cs.pulpul.service.exception.LoginException;
import dongduk.cs.pulpul.validator.ChangeMemberInfoValidator;
import dongduk.cs.pulpul.validator.LoginValidator;
import dongduk.cs.pulpul.validator.RegisterValidator;

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
	private RegisterValidator registerValidator;
	public void setValidator(RegisterValidator registerValidator) {
		this.registerValidator = registerValidator;
	}
	
	@Autowired
	private ChangeMemberInfoValidator changeMemberInfoValidator;
	public void setValidator(ChangeMemberInfoValidator changeMemberInfoValidator) {
		this.changeMemberInfoValidator = changeMemberInfoValidator;
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
		registerValidator.validate(member, result);
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
			
			// 장바구니 상품 수 세션에 저장
			if (member.getId() != null) {
				int numberOfCartItem = orderService.getNumberOfCartItemByMember(member.getId());
				session.setAttribute("cartItemCnt", numberOfCartItem);
				System.out.println(numberOfCartItem);
			}
			
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
			String birth = member.getBirth().split(" ")[0];
			member.setBirth(birth.replaceAll("-", ""));
			mav.addObject("member", member);
		}
		mav.setViewName("member/myInfoForm");
		return mav;
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute("member") Member member, Errors result, Model model, HttpServletRequest req) {
		/*
		 //성공
		 return "redirect:/member/update";
		 //실패 - 회원 정보 수정 폼
		 return "member/myInfoForm";
		 */
		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("id");
		if (member != null) {
			member.setId(id);
			model.addAttribute("member", member);
			changeMemberInfoValidator.validate(member, result);
			if(result.hasErrors()) {
				return "member/myInfoForm";
			}
			else {
				boolean success = memberService.changeMemberInfo(member);
				if (!success) {
					return "member/myInfoForm";
				}
				else {
					return "redirect:/member/view";
				}
			}
		}
		return "member/myInfoForm";
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
	
	@GetMapping("/mypage")
	public String mypage(HttpServletRequest req, Model model) {
		//마이 페이지
		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("id");
		
		// 로그인한 상태인지 확인
		if (id == null) {
			model.addAttribute("isNotLogined", true);
			return "redirect:/home";
		}
		
		return "member/mypage";
	}
	
	/*
	 * 마이 페이지 접근 - 구매 목록 조회
	 */
	@GetMapping("/mypage/orderList")
	@ResponseBody
	public List<Order> myOrderList(HttpServletRequest req, HttpServletResponse response) throws IOException {
		//마이 페이지
		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("id");
		
		/*
		 * // 로그인한 상태 확인 if (id == null) { mav.addObject("isNotLogined", true);
		 * mav.setViewName("redirect:/home"); return mav; }
		 */
		
		List<Order> orderList = orderService.getOrderListByMember(id, "buyer");
		if (orderList == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		
		return orderList;
	}
	
	/*
	 * 마이 페이지 접근 - 대여, 예약 목록 조회
	 */
	@GetMapping("/mypage/borrowList")
	@ResponseBody
	public List<Borrow> myBorrowList(HttpServletRequest req, HttpServletResponse response) throws IOException {
		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("id");
		
		List<Borrow> borrowList = borrowService.getBorrowByMember(id, "borrower");

		if (borrowList == null) {
			// response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		
		List<Borrow> reservationList = borrowService.getBorrowReservationByMember(id);
		
		if (reservationList == null) {
			// response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		else {
			for (Borrow r : reservationList) {
				borrowList.add(r);
			}
		}
		
		return borrowList;
	}


}
