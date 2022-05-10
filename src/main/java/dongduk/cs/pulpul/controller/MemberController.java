package dongduk.cs.pulpul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {


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
		return "/member/registerForm";
	}
	
	@PostMapping("/login")
	public void login() {
		/*
		 //성공
		 return "redirect:/home";
		 //실패 - 로그인 폼
		 return "member/loginForm";
		 */
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
	public String logout() {
		
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
	public String myOrderList() {
		//마이 페이지
		return "member/mypage";
	}
	
	/*
	 * 마이 페이지 접근 - 대여, 예약 목록 조회
	 */
	@GetMapping("/mypage/borrowList")
	public String myBorrowList() {
		return "member/mypage";
	}


}
