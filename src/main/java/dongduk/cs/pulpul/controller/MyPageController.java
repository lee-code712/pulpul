package dongduk.cs.pulpul.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dongduk.cs.pulpul.domain.Borrow;
import dongduk.cs.pulpul.domain.Member;
import dongduk.cs.pulpul.domain.Order;
import dongduk.cs.pulpul.service.BorrowService;
import dongduk.cs.pulpul.service.MemberService;
import dongduk.cs.pulpul.service.OrderService;

@Controller
@RequestMapping("/member/mypage")
public class MyPageController {
	
	private final MemberService memberSvc;
	private final OrderService orderSvc;
	private final BorrowService borrowSvc;
	
	@Autowired 
	public MyPageController(MemberService memberSvc, OrderService orderSvc, BorrowService borrowSvc) {
		this.memberSvc = memberSvc;
		this.orderSvc = orderSvc;
		this.borrowSvc = borrowSvc;
	}
	
	@GetMapping("")
	public String mypage(HttpServletRequest req, Model model) {
		//마이 페이지
		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("id");
		
		// 내정보 반환
		Member member = memberSvc.getMember(id);
		model.addAttribute(member);
		
		return "member/mypage";
	}
	
	/*
	 * 마이 페이지 접근 - 구매 목록 조회
	 */
	@GetMapping("/orderList")
	@ResponseBody
	public PagedListHolder<Order> myOrderList(HttpSession session) throws IOException {

		String id = (String) session.getAttribute("id");
		
		PagedListHolder<Order> orderList = new PagedListHolder<Order>(orderSvc.getOrderListByMember(id, "buyer"));
		orderList.setPageSize(5);
		session.setAttribute("orderList", orderList);
		
		return orderList;
	}
	
	@GetMapping("/orderList/{page}")
	@ResponseBody
	public PagedListHolder<Order> myOrderList2(@PathVariable("page") String page,
			HttpSession session) throws IOException {

		@SuppressWarnings("unchecked")
		PagedListHolder<Order> orderList = (PagedListHolder<Order>) session.getAttribute("orderList");
		
		if ("next".equals(page)) {
			orderList.nextPage();
		}
		else if ("previous".equals(page)) {
			orderList.previousPage();
		}

		return orderList;
	}
	
	/*
	 * 마이 페이지 접근 - 대여, 예약 목록 조회
	 */
	@GetMapping("/borrowList")
	@ResponseBody
	public PagedListHolder<Borrow> myBorrowList(HttpSession session) throws IOException {
		
		String id = (String) session.getAttribute("id");
		
		List<Borrow> borrows = borrowSvc.getBorrowByMember(id, "borrower");		
		List<Borrow> reservations = borrowSvc.getBorrowReservationByMember(id);
		
		PagedListHolder<Borrow> borrowList = null;
		
		if (borrows == null) {
			if(reservations != null) {
				borrowList = new PagedListHolder<Borrow>(reservations);
			}
		}
		else {
			if(reservations != null) {
				for (Borrow r : reservations) {
					borrows.add(r);
				}
			}
			borrowList = new PagedListHolder<Borrow>(borrows);
		}
		
		borrowList.setPageSize(5);
		session.setAttribute("borrowList", borrowList);
		
		return borrowList;
	}
	
	@GetMapping("/borrowList/{page}")
	@ResponseBody
	public PagedListHolder<Borrow> myBorrowList2(@PathVariable("page") String page,
			HttpSession session) throws IOException {
		
		@SuppressWarnings("unchecked")
		PagedListHolder<Borrow> borrowList = (PagedListHolder<Borrow>) session.getAttribute("borrowList");
			
		if ("next".equals(page)) {
			borrowList.nextPage();
		}
		else if ("previous".equals(page)) {
			borrowList.previousPage();
		}
		
		return borrowList;
	}
}
