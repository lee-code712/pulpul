package dongduk.cs.pulpul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BorrowController {
	/*
	 * 공유물품 대여 신청 
	 */	
	@RequestMapping("/member/shareThings/borrow")
	public void borrow(){
		/*
		//성공
		return "redirect:/member/mypage/borrowList";
		
		//오류 - 공유물품 상세정보 페이지
		return "/lookup/sharedThingsDetail";
		*/
	}	

	/*
	 * 반납 처리
	 */
	@RequestMapping("/borrow/return")
	public String returnItem() {
		return "redirect:/market/shareThingsManage";
	}

	/*
	 * 공유물품 예약
	 */
	@RequestMapping("/member/shareThings/reservation")
	public void makeReservation(){
		/*
		//성공
		return "redirect:/member/mypage/borrowList";
		//오류
		return "/lookup/sharedThingsDetail";
		*/
	}

	/*
	 * 공유물품 예약 취소
	 */
	@RequestMapping("/borrow/reservation/cancel")
	public String cancel() {
		return "redirect:/member/mypage/borrowList";
	}

	/*
	 * 대여 연장
	 */
	@RequestMapping("/borrow/extend")
	public String extend(){
		return "redirect:/member/mypage/borrowList";
	}	

	/*
	 * 마켓에서
	 * 특정 공유물품 대여 내역에 운송장 번호 입력 시
	 */
	@RequestMapping("/borrow/startDeliver")
	public String startDeliver(){
		return "redirect:/market/shareThingsManage";
	}

	/*
	 * 알림 조회
	 */
	@RequestMapping("/borrow/alertList")
	public String alertList(){
		return "/lookup/alert";
	}


}
