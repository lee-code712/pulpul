package dongduk.cs.pulpul.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import antlr.collections.List;
import dongduk.cs.pulpul.domain.Goods;
import dongduk.cs.pulpul.domain.Member;
import dongduk.cs.pulpul.service.ItemService;
import dongduk.cs.pulpul.service.ItemServiceImpl;

@Controller
@RequestMapping("/market/goods")
public class GoodsController {
	
	private final ItemService itemSvc;
	
	@Autowired
	public GoodsController(ItemService itemSvc) {
		this.itemSvc = itemSvc;
	}
	
	@ModelAttribute("goods")
	public Goods formBacking() {
		return new Goods();
	}
	
	/*
	 * 판매 식물 목록 조회 
	 */
	@GetMapping("/list")
	public String goodsList(HttpSession session, Model model){
		
		String memberId = (String) session.getAttribute("id");
		
		if(memberId == null) {
			return "redirect:/home";
		}
		
		ArrayList<Goods> goodsList = (ArrayList<Goods>) itemSvc.getGoodsListByMember(memberId);
		model.addAttribute("goodsList", goodsList);

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
