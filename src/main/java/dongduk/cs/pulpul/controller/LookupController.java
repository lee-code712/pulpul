package dongduk.cs.pulpul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LookupController {

	/*
	 * 식물 검색 
	 */
	@RequestMapping("/lookup/goodsList")
	public String goodsList(){
		//식물 전체 목록 조회 페이지
		return "/lookup/goodsList";
	}

	/*
	 * 식물 상세 정보 조회
	 */
	@RequestMapping("/lookup/goodsDetail")
	public String goodsDetail(){
		//상품 상세 정보 페이지
		return "/lookup/goodsDetail";
	}

	/*
	 * 마켓 조회
	 */
	@RequestMapping("/lookup/marekt/goodsList")
	public String marketGoodsList(){
		//마켓 페이지
		return "/lookup/market";
	}
	
	/*
	 * 마켓 조회
	 */
	@RequestMapping("/lookup/marekt/sharedThingsList")
	public String marketSharedThingsList(){
		//마켓 페이지
		return "/lookup/market";
	}
	
	/*
	 * 공유물품 상세정보 조회
	 */
	@RequestMapping("/lookup/shareThingsDetail")
	public String shareThingsDetail() {
		//공유물품 상세 정보 조회 페이지
		return "/lookup/shareThingsDetail";
	}
	
	/*
	 * 모든 공유물품 목록 조회- 공유물품 전체 목록 조회
	 */
	@RequestMapping("/lookup/shareThingsList")
	public String shareThings() {
		//공유물품 전체 목록 조회 페이지
		return "/lookup/shareThingsList";
	}

}
