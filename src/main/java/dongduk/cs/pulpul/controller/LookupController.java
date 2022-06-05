package dongduk.cs.pulpul.controller;

import java.io.IOException;
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

import dongduk.cs.pulpul.domain.Borrow;
import dongduk.cs.pulpul.domain.CartItem;
import dongduk.cs.pulpul.domain.Goods;
import dongduk.cs.pulpul.domain.Market;
import dongduk.cs.pulpul.domain.Member;
import dongduk.cs.pulpul.domain.Review;
import dongduk.cs.pulpul.domain.ShareThing;
import dongduk.cs.pulpul.service.ItemService;
import dongduk.cs.pulpul.service.MarketService;
import dongduk.cs.pulpul.service.MemberService;
import dongduk.cs.pulpul.service.ReviewService;

@Controller
@RequestMapping("/lookup")
public class LookupController {

	private final ItemService itemSvc;
	private final ReviewService reviewSvc;
	private final MarketService marketSvc;
	private final MemberService memberSvc;
	
	@Autowired
	public LookupController(ItemService itemSvc, ReviewService reviewSvc,
			MarketService marketSvc, MemberService memberSvc) {
		this.itemSvc = itemSvc;
		this.reviewSvc = reviewSvc;
		this.marketSvc = marketSvc;
		this.memberSvc = memberSvc;
	}

	/*
	 * 식물 검색 
	 */
	@GetMapping("/goodsList")
	public String goodsList(Model model) {
		
		List<Goods> goodsList = itemSvc.getGoodsList();
		model.addAttribute("goodsList", goodsList);

		return "lookup/goodsList";
	}

	/*
	 * 식물 상세 정보 조회
	 */
	@GetMapping("/goodsDetail")
	public String goodsDetail(@RequestParam("itemId") String itemId, 
			HttpSession session, Model model) {
		
		// 상품 상세정보 반환
		Goods goods = itemSvc.getGoods(itemId);
		model.addAttribute("goods", goods);
		
		// 상품에 대한 리뷰 목록 반환
		List<Review> reviewList = reviewSvc.getReviewByItem(itemId);
		if (reviewList != null) {
			model.addAttribute("reviewList", reviewList);
		}
		
		// 로그인 한 경우, 리뷰할 건이 있는 주문 id 반환
		String memberId = (String) session.getAttribute("id");
		if (memberId != null) {
			int orderId = reviewSvc.getOrderIdByNotReview(itemId, memberId);
			if (orderId > 0) {
				model.addAttribute("orderId", orderId);
			}
		}
		
		// cartItem 내용을 담을 객체 생성 후 반환
		CartItem cartItem = new CartItem();
		cartItem.setQuantity(1);
		model.addAttribute("cartItem", cartItem);
		
		return "lookup/goodsDetail";
	}
	
	/*
	 * 모든 공유물품 목록 조회- 공유물품 전체 목록 조회
	 */
	@GetMapping("/shareThingList")
	public String shareThing(Model model) {
		//공유물품 전체 목록 조회 페이지
		
		List<ShareThing> shareThingList = itemSvc.getShareThingList();
		model.addAttribute("shareThingList", shareThingList);
		
		return "lookup/shareThingList";
	}
	
	/*
	 * 공유물품 상세정보 조회
	 */
	@GetMapping("/shareThingDetail")
	public String shareThingDetail(@RequestParam("itemId") String itemId, Model model) {
		//공유물품 상세 정보 조회 페이지
		
		ShareThing shareThing = itemSvc.getShareThing(itemId);
		Borrow borrow = new Borrow();
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
	 * 상품 클릭 시 상품목록 json으로 반환
	 */
	@GetMapping("/market/goodsList/{marketId}")
	@ResponseBody
	public List<Goods> marketGoodsList(@PathVariable("marketId") int marketId,
			HttpServletResponse response) throws IOException {
		
		List<Goods> goodsList = itemSvc.getGoodsListByMarket(marketId);
		if (goodsList == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		
		return goodsList;
	}
	
	/*
	 * 공유물품 클릭 시 공유물품 목록 json으로 반환
	 */
	@GetMapping("/market/shareThingList/{marketId}")
	@ResponseBody
	public List<ShareThing> marketShareThingList(@PathVariable("marketId") int marketId,
			HttpServletResponse response) throws IOException {

		List<ShareThing> shareThingList = itemSvc.getShareThingListByMarket(marketId);
		if (shareThingList == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		
		return shareThingList;
	}
	
	/*
	 * 알림 조회
	 */
	@GetMapping("/alertList")
	public String alertList(){
		return "lookup/alert";
	}

}
