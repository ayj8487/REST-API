package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller // 컨트롤러 어노테이션으로 스프링 빈 임을 인식                                                                                                                                                                                                                                                                                                                                                                 
@RequestMapping("/board/*") // "/board/" 로 시작하는 모든 처리를 BoardController 가 하도록 지정
@AllArgsConstructor
@Log4j
/*
'/board/list' 를 실행시 BoardController는 Model을 이용해서 게시물의 목록을 'list'라는 이름으로 담아서
전달했으므로 list.jsp에서는 이를 출력한다. list.jsp의 출력은 JSTL 을 이용한다.
*/
public class BoardController {
	// BoardController는 BoardService 에 의존적이므로 @AllArgsConstructor를 이용해서 생성자 자동주입
	// 만약 생성자를 만들지 않을 경우 @Setter(onMethod_ = @Autowired) 처리

	private BoardService service; //BoardService 타입의 객체와 같이 연동

	
	// 게시물 전체 목록
	// list는 나중에 게시물목록을 전달해야 하므로 Model을 파라미터로 지정 하고 이를통해 BoardServiceImpl 객체의 getList() 결과를 담아 전달(addAttribute) 함
//	@GetMapping("/list")
//	public void list (Model model) { 
//		log.info("list");
//		
//		model.addAttribute("list", service.getList());
//	}
	
	// 게시물 전체 목록 (페이징)
	// Criteria 클래스를 만들어두었기 때문에 편리하게 하나의 타입만으로 파라미터와 리턴타입을 사용 가능
	@GetMapping("/list")
	public void list (Criteria cri, Model model) {
		log.info("list : "+ cri);
		model.addAttribute("list", service.getList(cri));
		// pageMaker 라는 이름으로 PageDTO 클래스에서 객체를 만들어 Model에 담아줌
		model.addAttribute("pageMaker", new PageDTO(cri, 123));
		
		// 전체 게시물 수를 호출하기 위해 추가
		int total = service.getTotal(cri);
		log.info("total : "+ total);
		model.addAttribute("pageMaker", new PageDTO(cri, total));
	}
	
	// 게시물 등록
	// String  타입으로 지정하고 RedirectAttributes 를 파라미터로 지정
	// RedirectAttributes의 addFlashAttribute()는 일회성으로 데이터 전달(도배 방지) 
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		log.info("regist : " + board);
		
		service.register(board);
		
		rttr.addFlashAttribute("result",board.getBno());
		
		return "redirect:/board/list"; //redirect: 접두어 사용시 내부적으로 response.secdRedirect() 처리
	}
	// 게시물 조회
	// bno 값을 더 명시적으로 처리하기위해 @RequestParam사용, 화면쪽으로 해당 번호의 게시물을 전달해야하므로 Model파라미터 지정
	// 조회 페이지는 수정삭제 페이지와 같기 때문에 GetMapping 경로 다중으로 수정(GetMapping,PostMapping 은 URL을 배열로 처리할 수 있음)
	// 페이징 목록으로 이동하기 위해 @ModelAttribute("cri")Criteria cri 추가
	@GetMapping({"/get", "/modify"})
	public void get(@RequestParam("bno") Long bno,@ModelAttribute("cri")Criteria cri, Model model) { 
		log.info("/get or modify");
		model.addAttribute("board",service.get(bno));
	}
	// 게시물 수정
	// 변경된 내용을 수집하여 BoardVO 파라미터로 처리하고 BoardService를 호출
	// 수정 작업을 시작하는 화면의 경우 GET방식이지만 실제 작업은 POST이기에 POSTMapping사용
	// @ModelAttribute 는 Model에서 데이터를 지정한 이름으로 담아줌
	@PostMapping("/modify")
	public String modify(BoardVO board,@ModelAttribute("cri")Criteria cri, RedirectAttributes rttr) {
		log.info("modify :" + board);
		
		//수정 여부를 boolean으로 처리하므로 이를 성공한경우에만 RedirectAttributes에 추가
		if(service.modify(board)) {
			rttr.addFlashAttribute("result","success");
		}
		// 수정 후 현재 페이징 넘버를 가져와 파라미터 변경후 리다이렉트에 포함시켜 처리
		rttr.addAttribute("pageNum",cri.getPageNum());
		rttr.addAttribute("amount",cri.getAmount());
		
		// 수정 후 현재 검색 키워드와 타입을 리다이렉트에 포함시켜 처리
		rttr.addAttribute("keyword",cri.getKeyword());
		rttr.addAttribute("type",cri.getType());
		
		
		return "redirect:/board/list";
	}
	// 게시물 삭제
	// 삭제는 반드시 post 방식으로만 처리,
	// remove()메서드로 삭제 시킨 후 이동할 페이지가 필요하기에 RedirectAttributes 파라미터 사용
//	@PostMapping("/remove")
//	public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
//		log.info("remove :" + bno);
//		if(service.remove(bno)) {
//			rttr.addFlashAttribute("result","success");
//		}
//		// 삭제 후 현재 페이징 넘버를 가져와 파라미터 변경후 리다이렉트에 포함시켜 처리
//		rttr.addAttribute("pageNum", cri.getPageNum());
//		rttr.addAttribute("amount", cri.getAmount());
//
//		// 삭제 후 현재 검색 키워드와 타입을 리다이렉트에 포함시켜 처리
//		rttr.addAttribute("keyword",cri.getKeyword());
//		rttr.addAttribute("type",cri.getType());
//
//		return "redirect:/board/list";
//	}
	
	// 게시물 삭제 (UriComponentsBuilder 를 이용 해서 검색조건과 페이징 유지 처리)
	// getListLink() 를 이용한 게시물 삭제 로직(modify또한 기존 로직보다 코드를 줄일 수 있음)
	// 주로 JavaScript를 사용 할 수 없는 상황에서 링크처리를 할때 사용	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, Criteria cri, RedirectAttributes rttr) {
		log.info("remove---" + bno);
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result","success");
		}
		return "redirect:/board/list" + cri.getListLink();
	}
	
	// 작성 페이지 이동
	@GetMapping("/register")
	public void register() {
	}
}
