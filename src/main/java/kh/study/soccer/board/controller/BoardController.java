package kh.study.soccer.board.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kh.study.soccer.board.service.BoardService;
import kh.study.soccer.board.vo.BoardVO;
import kh.study.soccer.member.vo.MemberVO;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Resource(name="boardService")
	private BoardService boardService;
	
	
	//게시글 목록
	@GetMapping("/list")
	public String boardList(BoardVO boardVO, Model model) {
		
		model.addAttribute("boardList", boardService.boardList(boardVO));
	
		return "content/board_list";
	}
	
	//글쓰기
	@GetMapping("/writeBoard")
	public String writeBoard(BoardVO boardVO) {
		
		return "content/reg_board";
	}
	
	//게시글 등록
	@PostMapping("/regBoard")
	public String regBoard(@Valid BoardVO boardVO
							, BindingResult bindingResult
							, Model model
							, Authentication authentication) {
		
		//에러발생
		if(bindingResult.hasErrors()) {
			return "content/reg_board";
		}
		
		//글 등록
		else {
			
			//security를 사용하여 로그인한 정보 가져오는 방법
			User user = (User) authentication.getPrincipal();
			
			boardVO.setMemberId(user.getUsername());
			
			boardService.regBoard(boardVO);
			
			return"content/reg_alert";
		}
	
	}

	
	
	//글 상세페이지 
	@GetMapping("/boardDetail")
	public String boardDetail(int boardNum, Model model) {
		
		model.addAttribute("boardDetail", boardService.boardDetail(boardNum));
		
		return "content/boardDetail";
	}
	
	
	//수정페이지
	@GetMapping("/updatePage")
	public String updateBoard(int boardNum, Model model) {
		
		model.addAttribute("update", boardService.boardDetail(boardNum));
		
		return "content/updateBoard";
	}
	
	
	//수정하기
	@PostMapping("/updateBoard")
	public String updateBoard(BoardVO boardVO) {
		
		boardService.updateBoard(boardVO);
		
	  	return "redirect:/board/boardDetail?boardNum="+ boardVO.getBoardNum();
	
	}
	
	
	//삭제하기
	@GetMapping("/deleteBoard")
	public String deleteBoard(int boardNum) {
		
		boardService.deleteBoard(boardNum);
		
		return "redirect:/board/list";
	}

}
