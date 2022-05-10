package dongduk.cs.pulpul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/market/goods")
public class GoodsController {
	/*
	 * 판매 식물 목록 조회 
	 */
	@GetMapping("/list")
	public String goodsList(){
		//마켓 정보 폼
		return "market/goodsList";
	}

	/*
	 * 판매 식물 등록
	 */
	@GetMapping("/upload")
	public String uploadForm(){
		//판매 식물 목록 페이지
		return "market/goodsForm";
	}

	@PostMapping("/upload")
	public void upload(){
		/*
		//성공
		return "redirect:/market/goods/list";
		//오류
		return "market/goodsForm";
		*/
	}

	/*
	 * 판매 식물 수정
	 */
	@GetMapping("/update")
	public String updateForm(){
		//판매 식물 목록 페이지
		return "market/goodsForm";
	}
	
	@PostMapping("/update")
	public void update(){
		/*
		//성공
		return "redirect:/market/goods/list";
		//오류
		return "market/goodsForm";
		*/
	}

	/*
	 * 판매 식물 삭제
	 */
	@GetMapping("/delete")
	public String delete(){
		//판매 식물 목록 페이지
		return "redirect:/market/goods/list";
	}


}
