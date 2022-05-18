package dongduk.cs.pulpul.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import dongduk.cs.pulpul.domain.Goods;
import dongduk.cs.pulpul.domain.Item;
import dongduk.cs.pulpul.domain.Market;
import dongduk.cs.pulpul.domain.Member;
import dongduk.cs.pulpul.service.ItemService;

@Controller
@RequestMapping("/market/goods")
public class GoodsController {
	
	private final ItemService itemSvc;
	
	@Autowired
	public GoodsController(ItemService itemSvc) {
		this.itemSvc = itemSvc;
	}
	
	@ModelAttribute("goods")
	public Goods formBacking(HttpSession session) {
		Member member = new Member();
		member.setId((String) session.getAttribute("id"));
		Market market = new Market();
		market.setMember(member);
		Item item = new Item();
		item.setMarket(market);
		Goods goods = new Goods();
		goods.setItem(item);
		return goods;
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
	public String uploadForm(@ModelAttribute("goods") Goods goods){
		
		String memberId = goods.getItem().getMarket().getMember().getId();
		
		if(memberId == null) {
			return "redirect:/home";
		}
		
		return "market/goodsForm";
	}

	@PostMapping("/upload")
	public String upload(@Valid @ModelAttribute("goods") Goods goods, BindingResult result,
			@RequestParam("report") MultipartFile[] uploadFiles, Model model){
		
		if (result.hasErrors())
			return "market/goodsForm";

		boolean successed = itemSvc.uploadGoods(goods, uploadFiles);
		if (!successed) {
			model.addAttribute("uplaodFalid", true);
			return "market/goodsForm";
		}
		
		return "redirect:/market/goods/list";

	}

	/*
	 * 판매 식물 수정
	 */
	@GetMapping("/update")
	public String updateForm(@ModelAttribute("goods") Goods goods, 
			@RequestParam("goodsId") String id){
		
		String memberId = goods.getItem().getMarket().getMember().getId();
		
		if(memberId == null) {
			return "redirect:/home";
		}
		
		Goods findGoods = itemSvc.getGoods(id);
		if (findGoods != null)
			BeanUtils.copyProperties(findGoods, goods);
		return "market/goodsForm";
	}
	
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("goods") Goods goods, BindingResult result,
			@RequestParam("report") MultipartFile[] updateFiles, Model model){

		if (result.hasErrors())
			return "market/goodsForm";
		
		boolean successed = itemSvc.changeGoodsInfo(goods, updateFiles);
		if (!successed) {
			model.addAttribute("updateFalid", true);
			return "market/goodsForm";
		}

		return "redirect:/market/goods/list";
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
