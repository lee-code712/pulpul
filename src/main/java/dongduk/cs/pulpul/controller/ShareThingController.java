package dongduk.cs.pulpul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/market/shareThing")
public class ShareThingController {
	
	/*
	 * 마켓별 공유물품 목록 조회 - 마켓관리 > 공유물품 관리
	 */
	@GetMapping("/list")
	public String shareThingList() {
		//공유 물품 목록 페이지
		return "market/shareThingList";
	}

	/*
	 * 공유물품 등록 
	 */
	@GetMapping("/upload")
	public String uploadForm() {
		//공유물품 정보 폼
		return "market/shareThingForm";
	}

	@PostMapping("/upload")
	public void upload(){
		/*
		 //성공
		 return "redirect:/market/shareThing/list";
		 
		 //실패 - 공유 물품 정보 폼
		 return "market/shareThingForm";
		 */
	}
	
	/*
	 * 공유물품 수정
	 */
	@GetMapping("/update")
	public String updateForm() {
		//공유 물품 정보 폼
		return "market/shareThingForm";
	}
	
	@PostMapping("/update")
	public void update() {
		/*
		 //성공
		 return "redirect:/market/shareThing/list";
		 
		 //실패 - 공유 물품 정보 폼
		 return "market/shareThingForm";
		 */
	}
	
	/*
	 * 공유물품 삭제
	 */
	@GetMapping("/delete")
	public String delete() {
		return "redirect:/market/shareThing/list";
	}

}