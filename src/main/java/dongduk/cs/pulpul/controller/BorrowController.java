package dongduk.cs.pulpul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/borrow")
public class BorrowController {
	/*
	 * 공유물품 대여 신청 
	 */	
	@PostMapping()
	public void borrow(){
		/*
		//성공
		return "redirect:/member/mypage/borrowList";
		
		//오류 - 공유물품 상세정보 페이지
		return "lookup/sharedThingDetail";
		*/
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
