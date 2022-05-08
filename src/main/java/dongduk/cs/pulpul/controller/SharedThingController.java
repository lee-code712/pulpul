package dongduk.cs.pulpul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SharedThingController {

	/*
	 * 공유물품 등록 
	 */
	@RequestMapping("/market/shareThings/upload")
	public String uploadForm() {
		//공유물품 정보 폼
		return "/market/shareThingsForm";
	}

	@RequestMapping("/market/goods/upload")
	public void upload(){
		/*
		 //성공
		 return "redirect:/market/shareThings/list";
		 
		 //실패 - 공유 물품 정보 폼
		 return "/market/shareThingsForm";
		 */
	}
	
	/*
	 * 공유물품 수정
	 */
	@GetMapping("/market/shareThings/update")
	public String updateForm() {
		//공유 물품 정보 폼
		return "/market/shareThingsForm";
	}
	
	@PostMapping("/market/shareThings/update")
	public void update() {
		/*
		 //성공
		 return "redirect:/market/shareThings/list";
		 
		 //실패 - 공유 물품 정보 폼
		 return "/market/shareThingsForm";
		 */
	}
	
	/*
	 * 마켓별 공유물품 목록 조회 - 마켓관리 > 공유물품 관리
	 */
	@RequestMapping("/market/shareThings/list")
	public String shareThingsList() {
		//공유 물품 목록 페이지
		return "/market/shareThingsList";
	}
	
	/*
	 * 공유물품 삭제
	 */
	@RequestMapping("/market/sharedThings/delete")
	public String delete() {
		return "redirect:/market/shareThings/list";
	}

}
