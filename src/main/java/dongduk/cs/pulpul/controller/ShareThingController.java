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

import dongduk.cs.pulpul.domain.Item;
import dongduk.cs.pulpul.domain.Market;
import dongduk.cs.pulpul.domain.Member;
import dongduk.cs.pulpul.domain.ShareThing;
import dongduk.cs.pulpul.service.ItemService;
import dongduk.cs.pulpul.service.exception.DeleteItemException;

@Controller
@RequestMapping("/market/shareThing")
public class ShareThingController implements ApplicationContextAware {
	
	private WebApplicationContext context;	
	private String uploadDir;
	private final ItemService itemSvc;
	
	@Autowired
	public ShareThingController(ItemService itemSvc) {
		this.itemSvc = itemSvc;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		this.context = (WebApplicationContext) appContext;
		this.uploadDir = context.getServletContext().getRealPath("/upload/");
		// System.out.println(this.uploadDir);
	}
	
	@ModelAttribute("shareThing")
	public ShareThing formBacking(HttpSession session) {
		Member member = new Member();
		member.setId((String) session.getAttribute("id"));
		Market market = new Market();
		market.setMember(member);
		Item item = new Item();
		item.setMarket(market);
		ShareThing shareThing = new ShareThing();
		shareThing.setItem(item);
		return shareThing;
	}
	
	/*
	 * 마켓별 공유물품 목록 조회 - 마켓관리 > 공유물품 관리
	 */
	@GetMapping("/list")
	public String shareThingList(HttpSession session, Model model) {
		
		String memberId = (String) session.getAttribute("id");
		
		if(memberId == null) {
			return "redirect:/home";
		}
		
		ArrayList<ShareThing> shareThingList = (ArrayList<ShareThing>) itemSvc.getShareThingListByMember(memberId);
		model.addAttribute("shareThingList", shareThingList);

		return "market/shareThingList";
	}

	/*
	 * 공유물품 등록 
	 */
	@GetMapping("/upload")
	public String uploadForm(@ModelAttribute("shareThing") ShareThing shareThing) {
		
		String memberId = shareThing.getItem().getMarket().getMember().getId();
		
		if(memberId == null) {
			return "redirect:/home";
		}

		return "market/shareThingForm";
	}

	@PostMapping("/upload")
	public String upload(@Valid @ModelAttribute("shareThing") ShareThing shareThing, BindingResult result,
			FileCommand uploadFiles, Model model) {
		
		if (result.hasErrors())
			return "market/shareThingForm";
		
		uploadFiles.setPath(uploadDir);
		boolean successed = itemSvc.uploadShareThing(shareThing, uploadFiles);
		if (!successed) {
			model.addAttribute("uplaodFalid", true);
			return "market/shareThingForm";
		}

		return "redirect:/market/shareThing/list";
	}
	
	/*
	 * 공유물품 수정
	 */
	@GetMapping("/update")
	public String updateForm(@ModelAttribute("shareThing") ShareThing shareThing,
			@RequestParam("itemId") String id) {
		
		String memberId = shareThing.getItem().getMarket().getMember().getId();
		
		if(memberId == null) {
			return "redirect:/home";
		}
		
		ShareThing findShareThing = itemSvc.getShareThing(id);
		if (findShareThing != null)
			BeanUtils.copyProperties(findShareThing, shareThing);
		
		return "market/shareThingForm";
	}
	
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("shareThing") ShareThing shareThing, BindingResult result,
			FileCommand updateFiles, Model model) {
		
		if (result.hasErrors())
			return "market/shareThingForm";
		
		updateFiles.setPath(uploadDir);
		boolean successed = itemSvc.changeShareThingInfo(shareThing, updateFiles);
		
		if (!successed) {
			model.addAttribute("updateFalid", true);
			return "market/shareThingForm";
		}

		return "redirect:/market/shareThing/list";
	}
	
	/*
	 * 공유물품 삭제
	 */
	@GetMapping("/delete")
	public String delete(@RequestParam("itemId") String id, HttpSession session, 
			RedirectAttributes rttr) {
		
		try {
			boolean successed = itemSvc.deleteItem(id, (String)session.getAttribute("id"), uploadDir);
			if (!successed) {
				rttr.addFlashAttribute("deleteFailed", true);
			}
			
		} catch (DeleteItemException e) {
			rttr.addFlashAttribute("deleteFailed", true);
			rttr.addFlashAttribute("exception", e.getMessage());
		}
		
		return "redirect:/market/shareThing/list";
	}

}