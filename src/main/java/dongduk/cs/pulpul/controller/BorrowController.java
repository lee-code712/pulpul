package dongduk.cs.pulpul.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
import org.springframework.web.bind.annotation.RequestParam;

import dongduk.cs.pulpul.domain.Borrow;
import dongduk.cs.pulpul.domain.Member;
import dongduk.cs.pulpul.domain.ShareThing;
import dongduk.cs.pulpul.service.BorrowService;
import dongduk.cs.pulpul.service.ItemService;
import dongduk.cs.pulpul.service.MemberService;
import dongduk.cs.pulpul.validator.BorrowValidator;

@Controller
@RequestMapping("/borrow")
public class BorrowController {
	
	private final BorrowService borrowService;
	private final ItemService itemService;
	private final MemberService memberService;
	
	@Autowired 
	public BorrowController(BorrowService borrowService, ItemService itemService, MemberService memberService) {
		this.borrowService = borrowService;
		this.itemService = itemService;
		this.memberService = memberService;
	}
	
	@Autowired
	private BorrowValidator borrowValidator;
	public void setValidator(BorrowValidator borrowValidator) {
		this.borrowValidator = borrowValidator;
	}
	
	@ModelAttribute("borrow")
	public Borrow formBacking() {
		return new Borrow();
	}
	
	/*
	 * 공유물품 대여 신청 
	 */	
	@PostMapping("borrow")
	public String borrow(@ModelAttribute("borrow") Borrow borrow, Errors result, Model model, HttpServletRequest req){
		/*
		//성공
		return "redirect:/member/mypage/borrowList";
		
		//오류 - 공유물품 상세정보 페이지
		return "lookup/sharedThingDetail";
		*/
		ShareThing shareThing = itemService.getShareThing(borrow.getShareThing().getItem().getId());
		borrowValidator.validate(borrow, result);
		if(result.hasErrors()) {
			borrow.setShareThing(shareThing);
			model.addAttribute("borrow", borrow);
			return "lookup/shareThingDetail";
		}
		
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar borrowCal = Calendar.getInstance();
		String borrowDate = transFormat.format(borrowCal.getTime());
		Calendar returnCal = Calendar.getInstance();
		returnCal.add(Calendar.DATE, Integer.parseInt(borrow.getDate()));
		String returnDate = transFormat.format(returnCal.getTime());

		borrow.setBorrowDate(borrowDate);
		borrow.setReturnDate(returnDate);
		
		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("id");
		Member borrower = new Member();
		borrower.setId(id);
		
		borrow.setBorrower(borrower);
		
		ShareThing tempShareThing = borrow.getShareThing();
		tempShareThing.setIsBorrowed(1);
		borrow.setShareThing(tempShareThing);
		
		boolean success = borrowService.borrow(borrow);
		
		if(success) {
			// lender에게 포인트 지급
			Member lender = memberService.getMember(borrow.getLender().getId());
			memberService.changePoint(lender, 1, 1000);
			
			return "redirect:/member/mypage";
		}
		else {
			borrow.setShareThing(shareThing);
			model.addAttribute("borrow", borrow);
			return "lookup/shareThingDetail";
		}
	}	

	/*
	 * 반납 처리
	 */
	@GetMapping("/return")
	public String returnItem(@RequestParam("borrowId") int borrowId, Model model) {
		Borrow borrow = borrowService.getBorrowById(borrowId);
		borrowService.returnShareThing(borrow);
		return "redirect:/market/shareThingBorrowManage?itemId=" + borrow.getShareThing().getItem().getId();
	}

	/*
	 * 공유물품 예약
	 */
	@PostMapping("/reservation")
	public String makeReservation(@ModelAttribute("borrow") Borrow borrow, Model model, Errors result, HttpServletRequest req){
		/*
		//성공
		return "redirect:/member/mypage";
		//오류
		return "lookup/sharedThingDetail";
		*/

		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("id");
		Member member = new Member();
		member.setId(id);
		borrow.setBorrower(member);
		boolean success = borrowService.makeBorrowReservation(borrow);
		
		if (success) {
			return "redirect:/member/mypage";
		}
		
		result.reject("fullBooked", new Object[] {borrow.getShareThing().getItem().getName()}, null);
		return "lookup/sharedThingDetail";
		
	}

	/*
	 * 공유물품 예약 취소
	 */
	@GetMapping("/reservation/cancel")
	public String cancel() {
		return "redirect:/member/mypage/borrowList";
	}

	/*
	 * 대여 연장
	 */
	@GetMapping("/extend")
	public String extend(@ModelAttribute("borrow") Borrow borrow, Errors result, HttpServletRequest req, Model model){
		Borrow borrowInfo = borrowService.getBorrowById(borrow.getId());
		
		if (borrowInfo.getIsExtended() == 1) {
			result.reject("alreadyExtended", new Object[] {borrowInfo.getShareThing().getItem().getName()}, null);
			System.out.println("연장 실패");
		}
		else {
			borrowService.extendBorrow(borrowInfo);
			System.out.println("연장 성공");
		}
		
		return "redirect:/member/mypage";
	}	

	/*
	 * 마켓에서
	 * 특정 공유물품 대여 내역에 운송장 번호 입력 시
	 */
	@PostMapping("/startDeliver")
	public String startDeliver(){
		return "redirect:/market/shareThingManage";
	}

}
