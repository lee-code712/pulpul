package dongduk.cs.pulpul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lookup")
public class LookupController {

	/*
	 * 식물 검색 
	 */
	@GetMapping("/goodsList")
	public String goodsList(){
		//식물 전체 목록 조회 페이지
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
	@GetMapping("/marekt/goodsList")
	public String marketGoodsList(){
		//마켓 페이지
		return "lookup/market";
	}
	
	/*
	 * 마켓 조회
	 */
	@GetMapping("/marekt/shareThingList")
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
