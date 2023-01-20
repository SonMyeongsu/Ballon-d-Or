package kh.study.soccer.board.controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.ResponseBody;

import kh.study.soccer.board.service.BoardService;
import kh.study.soccer.board.vo.BoardLikeVO;
import kh.study.soccer.board.vo.BoardVO;
import kh.study.soccer.member.vo.MemberVO;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Resource(name="boardService")
	private BoardService boardService;
	
	
	//게시글 목록
	@GetMapping("/boardList")
	public String boardList(BoardVO boardVO, Model model) {
		
		model.addAttribute("boardList", boardService.boardList(boardVO));
		
		return "pages/board/board_list";
	}
	
	//글쓰기
	@GetMapping("/regBoardForm")
	public String regBoardForm(BoardVO boardVO) {
		
		return "pages/board/reg_board";
	}
	
	//게시글 등록
	@PostMapping("/regBoard")
	public String regBoard(@Valid BoardVO boardVO
							, BindingResult bindingResult
							, Model model
							, Authentication authentication) {
		
		//에러발생
		if(bindingResult.hasErrors()) {
			return "pages/board/reg_board";
		}
		
		//글 등록
		else {
			
			//security를 사용하여 로그인한 정보 가져오는 방법
			User user = (User) authentication.getPrincipal();
			
			boardVO.setMemberId(user.getUsername());
			
			boardService.regBoard(boardVO);
			
			return"pages/board/reg_alert";
		}
	
	}

	
	
	//글 상세페이지 
	@GetMapping("/boardDetail")
	public String boardDetail(int boardNum, HttpServletRequest httpServletRequest, 
			HttpServletResponse httpServletResponse, Authentication authentication, BoardLikeVO boardLikeVO, Model model) {
		
		//ID 확인
		User user = (User)authentication.getPrincipal();
		
		//조회수 증가(중복 방지)
		Cookie oldCookie = null;
		
		Cookie[] cookies = httpServletRequest.getCookies();
		
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("postView")) {
					oldCookie = cookie;
				}
			}
		}
		
		if(oldCookie != null) {
			if(!oldCookie.getValue().contains("[" + Integer.toString(boardNum) + "/" + user.getUsername() + "]")) {
				boardService.updateReadCnt(boardNum);
				oldCookie.setValue(oldCookie.getValue() + "_[" + boardNum + "/" + user.getUsername() + "]");
				oldCookie.setPath("/");
				oldCookie.setMaxAge(60*60*24);
				httpServletResponse.addCookie(oldCookie);
				
			}
		} else {
			boardService.updateReadCnt(boardNum);
			Cookie newCookie = new Cookie("postView", "[" + boardNum + "/" + user.getUsername() + "]"); //
			newCookie.setPath("/");
			newCookie.setMaxAge(60*60*24);
			httpServletResponse.addCookie(newCookie);
		}
		
		//게시글 상세 조회
		model.addAttribute("detail", boardService.boardDetail(boardNum));
		
		//추천기능 상태 확인
		boardLikeVO.setMemberId(user.getUsername());
		
		BoardLikeVO result = boardService.boardLikeCheck(boardLikeVO);

		//좋아요가 눌리지 않은 상태
		if(result == null) {
			model.addAttribute("like", false);
		}
		else{
		//좋아요를 누른상태
			model.addAttribute("like", true);
		}
		
		
		return "pages/board/boardDetail";
	}
	
	//게시글 추천 기능(좋아요or싫어요버튼 클릭시)
	@ResponseBody
	@PostMapping("/likeOrHateProcess")
	public boolean likeAndHateProcess(BoardLikeVO boardLikeVO, Authentication authentication, Model model) {
		//id확인
		User user = (User)authentication.getPrincipal();
		boardLikeVO.setMemberId(user.getUsername());
		
		boardService.likeOrHateProcess(boardLikeVO);
		
		//추천기능 상태 확인
		BoardLikeVO isLike = boardService.boardLikeCheck(boardLikeVO);
		
		boolean result;
		
		//좋아요가 눌리지 않은 상태
		if(isLike == null) {
			result = false;
		}
		else{
		//좋아요를 누른상태
			result = true;
		}
		
		return result;
	}
	
	//수정페이지
	@GetMapping("/updateBoardForm")
	public String updateBoardForm(int boardNum, Model model) {
		
		model.addAttribute("update", boardService.boardDetail(boardNum));
		
		return "pages/board/updateBoard";
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
		
		return "redirect:/board/boardList";
	}

}
