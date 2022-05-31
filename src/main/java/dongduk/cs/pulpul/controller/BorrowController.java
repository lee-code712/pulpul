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

import dongduk.cs.pulpul.domain.Borrow;
import dongduk.cs.pulpul.domain.Member;
import dongduk.cs.pulpul.domain.ShareThing;
import dongduk.cs.pulpul.service.BorrowService;
import dongduk.cs.pulpul.service.ItemService;
import dongduk.cs.pulpul.validator.BorrowValidator;

@Controller
@RequestMapping("/borrow")
public class BorrowController {
	
	private final BorrowService borrowService;
	private final ItemService itemService;
	
	@Autowired 
	public BorrowController(BorrowService borrowService, ItemService itemService) {
		this.borrowService = borrowService;
		this.itemService = itemService;
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
			return "redirect:/member/mypage/borrowList";
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
	public String returnItem() {
		return "redirect:/market/shareThingManage";
	}

	/*
	 * 공유물품 예약
	 */
	@PostMapping("/reservation")
	public void makeReservation(){
		/*
		//성공
		return "redirect:/member/mypage/borrowList";
		//오류
		return "lookup/sharedThingDetail";
		*/
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
	@PostMapping("/extend")
	public String extend(){
		return "redirect:/member/mypage/borrowList";
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
