package dongduk.cs.pulpul.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dongduk.cs.pulpul.domain.Goods;
import dongduk.cs.pulpul.service.ItemService;

@Controller
@RequestMapping("/lookup")
public class LookupController {

	private final ItemService itemSvc;
	
	@Autowired
	public LookupController(ItemService itemSvc) {
		this.itemSvc = itemSvc;
	}
	
	/*
	 * 식물 검색 
	 */
	@GetMapping("/goodsList")
	public String goodsList(@RequestParam(required = false) String keyword, Model model) {
		
		List<Goods> goodsList = itemSvc.getGoodsList(keyword);
		model.addAttribute("goodsList", goodsList);

		return "lookup/goodsList";
	}

	/*
	 * 식물 상세 정보 조회
	 */
	@GetMapping("/goodsDetail")
	public String goodsDetail(){
		//상품 상세 정보 페이지
		return "lookup/goodsDetail";
	}
	
	/*
	 * 모든 공유물품 목록 조회- 공유물품 전체 목록 조회
	 */
	@GetMapping("/shareThingList")
	public String shareThing() {
		//공유물품 전체 목록 조회 페이지
		return "lookup/shareThingList";
	}
	
	/*
	 * 공유물품 상세정보 조회
	 */
	@GetMapping("/shareThingDetail")
	public String shareThingDetail() {
		//공유물품 상세 정보 조회 페이지
		return "lookup/shareThingDetail";
	}

	/*
	 * 마켓 조회
	 */
	@GetMapping("/market/goodsList")
	public String marketGoodsList(){
		//마켓 페이지
		return "lookup/market";
	}
	
	/*
	 * 마켓 조회
	 */
	@GetMapping("/market/shareThingList")
	public String marketshareThingList(){
		//마켓 페이지
		return "lookup/market";
	}
	
	/*
	 * 알림 조회
	 */
	@GetMapping("/alertList")
	public String alertList(){
		return "lookup/alert";
	}

}
