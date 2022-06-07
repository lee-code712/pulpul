package dongduk.cs.pulpul.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dongduk.cs.pulpul.domain.Goods;
import dongduk.cs.pulpul.service.ItemService;
import dongduk.cs.pulpul.service.exception.DeleteItemException;

@Controller
@RequestMapping("/market/goods")
public class GoodsController implements ApplicationContextAware {
	
	private WebApplicationContext context;	
	private String uploadDir;
	private final ItemService itemSvc;
	
	@Autowired
	public GoodsController(ItemService itemSvc) {
		this.itemSvc = itemSvc;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		this.context = (WebApplicationContext) appContext;
		this.uploadDir = context.getServletContext().getRealPath("/upload/");
		// System.out.println(this.uploadDir);
	}
	
	@ModelAttribute("goods")
	public Goods formBacking() {
		return new Goods();
	}
	
	/*
	 * 판매 식물 목록 조회 
	 */
	@GetMapping("/list")
	public String goodsList(HttpSession session, Model model) {
		
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
	public String uploadForm(@ModelAttribute("goods") Goods goods, HttpSession session) {
		
		String memberId = (String) session.getAttribute("id");
		if(memberId == null) {
			return "redirect:/home";
		}
		
		return "market/goodsForm";
	}

	@PostMapping("/upload")
	public String upload(@Valid @ModelAttribute("goods") Goods goods, BindingResult result,
			FileCommand uploadFiles, Model model) {

		if (result.hasErrors())
			return "market/goodsForm";
		
		uploadFiles.setPath(uploadDir);
		itemSvc.uploadGoods(goods, uploadFiles);
		
		return "redirect:/market/goods/list";
	}

	/*
	 * 판매 식물 수정
	 */
	@GetMapping("/update")
	public String updateForm(@ModelAttribute("goods") Goods goods, 
			@RequestParam("itemId") String id, HttpSession session) {
		
		String memberId = (String) session.getAttribute("id");
		if(memberId == null) {
			return "redirect:/home";
		}
		
		Goods findGoods = itemSvc.getGoods(id);
		if (findGoods != null) {
			BeanUtils.copyProperties(findGoods, goods);
		}
		
		return "market/goodsForm";
	}
	
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("goods") Goods goods, String[] deleteImages,
			BindingResult result, FileCommand updateFiles, Model model) {

		if (result.hasErrors())
			return "market/goodsForm";
		
		updateFiles.setPath(uploadDir);
		itemSvc.changeGoodsInfo(goods, updateFiles, deleteImages);

		return "redirect:/market/goods/list";
	}

	/*
	 * 판매 식물 삭제
	 */
	@GetMapping("/delete")
	public String delete(@RequestParam("itemId") String id, HttpSession session, 
			RedirectAttributes rttr) {

		try {
			itemSvc.deleteItem(id, (String)session.getAttribute("id"), uploadDir);
			
		} catch (DeleteItemException e) {
			rttr.addFlashAttribute("deleteFailed", true);
			rttr.addFlashAttribute("exception", e.getMessage());
		}

		return "redirect:/market/goods/list";
	}

}
