package dongduk.cs.pulpul.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dongduk.cs.pulpul.domain.Alert;
import dongduk.cs.pulpul.domain.Borrow;
import dongduk.cs.pulpul.domain.CartItem;
import dongduk.cs.pulpul.domain.Goods;
import dongduk.cs.pulpul.domain.Market;
import dongduk.cs.pulpul.domain.Review;
import dongduk.cs.pulpul.domain.ShareThing;
import dongduk.cs.pulpul.service.BorrowService;
import dongduk.cs.pulpul.service.ItemService;
import dongduk.cs.pulpul.service.MarketService;
import dongduk.cs.pulpul.service.ReviewService;

@Controller
@RequestMapping("/lookup")
public class LookupController {

	private final ItemService itemSvc;
	private final ReviewService reviewSvc;
	private final MarketService marketSvc;
	private final BorrowService borrowSvc;
	
	@Autowired
	public LookupController(ItemService itemSvc, ReviewService reviewSvc,
			MarketService marketSvc, BorrowService borrowSvc) {
		this.itemSvc = itemSvc;
		this.reviewSvc = reviewSvc;
		this.marketSvc = marketSvc;
		this.borrowSvc = borrowSvc;
	}

	/*
	 * 식물 검색 
	 */
	@GetMapping("/goodsList")
	public String goodsList(Model model) {
		List<Goods> goodsList = itemSvc.getGoodsList();
		if (goodsList != null) {
			model.addAttribute("goodsList", goodsList);
		}
		
		return "lookup/goodsList";
	}

	/*
	 * 식물 상세정보 조회
	 */
	@GetMapping("/goodsDetail")
	public String goodsDetail(@RequestParam("itemId") String itemId, HttpSession session, Model model) {
		Goods goods = itemSvc.getGoods(itemId);
		if(goods != null) {
			model.addAttribute("goods", goods);
			
			List<Review> reviewList = reviewSvc.getReviewByItem(itemId);	// 상품에 대한 리뷰 목록 반환
			if (reviewList != null) {
				model.addAttribute("reviewList", reviewList);
			}
			
			String memberId = (String) session.getAttribute("id");
			if (memberId != null) {
				int orderId = reviewSvc.getOrderIdByNotReview(itemId, memberId); // 로그인 한 경우, 리뷰할 건이 있는 주문 id 반환
				if (orderId > 0) {
					model.addAttribute("orderId", orderId);
				}
			}
			
			CartItem cartItem = new CartItem();	// 장바구니 추가에 필요한 cartItem 객체 반환
			cartItem.setQuantity(1);
			model.addAttribute("cartItem", cartItem);
		}
		
		return "lookup/goodsDetail";
	}
	
	/*
	 * 공유물품 전체 목록 조회
	 */
	@GetMapping("/shareThingList")
	public String shareThing(Model model) {
		List<ShareThing> shareThingList = itemSvc.getShareThingList();
		if(shareThingList != null) {
			model.addAttribute("shareThingList", shareThingList);
		}
		
		return "lookup/shareThingList";
	}
	
	/*
	 * 공유물품 상세정보 조회
	 */
	@GetMapping("/shareThingDetail")
	public String shareThingDetail(@RequestParam("itemId") String itemId, Model model) {
		ShareThing shareThing = itemSvc.getShareThing(itemId);
		Borrow borrow = borrowSvc.getCurrBorrowByItem(itemId);
		if (borrow == null) {
			borrow = new Borrow();
			// 예약 레코드가 있으면 그거 넣어서 셋해줘야 함 
		}
		borrow.setShareThing(shareThing);
		model.addAttribute("borrow", borrow);
		
		return "lookup/shareThingDetail";
	}
	
	/*
	 * 마켓 조회
	 */
	@GetMapping("/market")
	public String market(@RequestParam("marketId") int marketId, Model model) {
		Market market = marketSvc.getMarket(marketId);
		if (market != null) {
			model.addAttribute(market);
		}
		
		return "lookup/market";
	}

	/*
	 * 상품목록 json으로 반환
	 */
	@GetMapping("/market/goodsList/{marketId}")
	@ResponseBody
	public List<Goods> marketGoodsList(@PathVariable("marketId") int marketId,
			HttpServletResponse response) throws IOException {
		List<Goods> goodsList = itemSvc.getGoodsListByMarket(marketId);	
		
		return goodsList;
	}
	
	/*
	 * 공유물품 목록 json으로 반환
	 */
	@GetMapping("/market/shareThingList/{marketId}")
	@ResponseBody
	public List<ShareThing> marketShareThingList(@PathVariable("marketId") int marketId,
			HttpServletResponse response) throws IOException {
		List<ShareThing> shareThingList = itemSvc.getShareThingListByMarket(marketId);
		
		return shareThingList;
	}
	
	/*
	 * 알림 조회
	 */
	@GetMapping("/alertList")
	public String alertList(HttpSession session, Model model) {
		String memberId = (String) session.getAttribute("id");
		if (memberId != null) {
			List<Alert> alertList = borrowSvc.getAlertByMember(memberId);
			List<Alert> updatedAlertList = new ArrayList<Alert>();
			
			for (Alert a : alertList) {
				borrowSvc.changeIsRead(a);
				a.setIsRead(1);
				ShareThing st = itemSvc.getShareThing(a.getShareThingId());
				a.setContent("대여를 예약하신 공유물품 " + st.getItem().getName() + "이(가) 대여 가능합니다. <\br> 3일 내로 대여 신청하시지 않으면 자동으로 대여 예약이 취소됩니다.");
				a.setPageUrl("shareThingDetail?itemId=" + st.getItem().getId());
				updatedAlertList.add(a);
			}

			model.addAttribute("alertList", updatedAlertList);
		}
		
		return "lookup/alert";
	}

}
