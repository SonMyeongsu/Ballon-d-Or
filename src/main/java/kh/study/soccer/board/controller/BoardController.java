package kh.study.soccer.board.controller;

import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kh.study.soccer.board.service.BoardService;
import kh.study.soccer.board.vo.BoardHateVO;
import kh.study.soccer.board.vo.BoardLikeVO;
import kh.study.soccer.board.vo.BoardVO;
import kh.study.soccer.config.appDateUtil;
import kh.study.soccer.home.vo.PageVO;
import kh.study.soccer.member.vo.MemberVO;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	
	//boardService라는 이름으로 만들어진 객체를 로드해서 의존성 주입
	@Resource(name="boardService")
	private BoardService boardService;
	
	
	//게시글 목록
	@RequestMapping("/boardList")
	public String boardList(@RequestParam Map<String, Object> paramMap,BoardVO boardVO, Model model) {
		
		if(paramMap.get("orderBy") == null || paramMap.get("orderBy") =="") {
			paramMap.put("orderBy", "BOARD_NUM");
		}
		
		//map에 날짜 세팅
		// 현재 날짜
		String nowDate = appDateUtil.getNowDateToString("-");// 2020-10-10
		// 한달 전날짜
		String beforeDate = appDateUtil.getBeforeMonthDateToString();
		// 넘어오는 fromDate가 없다면 한달 전 날짜로 세팅
		if (paramMap.get("fromDate") == null) {
			paramMap.put("fromDate", beforeDate);
		}
		if (paramMap.get("toDate") == null) {
			paramMap.put("toDate", nowDate);
		}
		
		//조건검색 결과 데이터 수
		int totalCnt = boardService.boardList(paramMap).size();
		//페이지 정보 세팅
		boardVO.setTotalDataCnt(totalCnt);
		boardVO.setPageInfo();
		
		// 페이징에 따라 조회될 데이터를 넣어준다.
		paramMap.put("startNum",boardVO.getStartNum());
		paramMap.put("endNum", boardVO.getEndNum());
		
		//map 보내줌
		model.addAttribute("paramMap", paramMap);
		
		//게시판 검색및 조회
		model.addAttribute("boardList", boardService.boardList(paramMap));
		
		//페이징처리 vo보내줌
		model.addAttribute("boardVO", boardVO);
		
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
			HttpServletResponse httpServletResponse, Authentication authentication,
			BoardLikeVO boardLikeVO, BoardHateVO boardHateVO, Model model) {
		
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
		
		
		//////////////////////////////////////
		//좋아요 상태 확인
		boardLikeVO.setMemberId(user.getUsername());
		
		BoardLikeVO result = boardService.boardLikeCheck(boardLikeVO);

		//좋아요 누르지 않은 상태
		if(result == null) {
			model.addAttribute("like", false);
		}
		else{
		//좋아요를 누른상태
			model.addAttribute("like", true);
		}
		////////////////////////
		//싫어요 상태 확인
		boardHateVO.setMemberId(user.getUsername());
		
		BoardHateVO resultHate = boardService.boardHateCheck(boardHateVO);
		
		//싫어요 누르지 않은 상태
		if(resultHate == null) {
			model.addAttribute("hate", false);
		}
		else{
			//좋아요를 누른상태
			model.addAttribute("hate", true);
		}
		
		
		
		return "pages/board/boardDetail";
	}
	
	//게시글 좋아요 기능(좋아요버튼 클릭시)
	@ResponseBody
	@PostMapping("/likeProcess")
	public boolean likeProcess(BoardLikeVO boardLikeVO, Authentication authentication, Model model) {
		//id확인
		User user = (User)authentication.getPrincipal();
		boardLikeVO.setMemberId(user.getUsername());
		
		boardService.likeProcess(boardLikeVO);
		
		//좋아요 상태 확인
		BoardLikeVO isLike = boardService.boardLikeCheck(boardLikeVO);
		
		boolean result;
		
		//좋아요를 누르지 않은 상태
		if(isLike == null) {
			result = false;
		}
		else{
		//좋아요를 누른상태
			result = true;
		}
		
		return result;
	}
	
	//게시글 싫어요 기능(싫어요버튼 클릭시)
	@ResponseBody
	@PostMapping("/hateProcess")
	public boolean hateProcess(
			/* BoardLikeVO boardLikeVO, */BoardHateVO boardHateVO, Authentication authentication, Model model) {
		//id확인
		User user = (User)authentication.getPrincipal();
		boardHateVO.setMemberId(user.getUsername());
//		boardLikeVO.setMemberId(user.getUsername());
		
		boardService.hateProcess(boardHateVO);
		
		//싫어요 상태 확인
		BoardHateVO isHate = boardService.boardHateCheck(boardHateVO);
		
		boolean resultHate;
		
		//싫어요를 누르지 않은 상태
		if(isHate == null) {
			resultHate = false;
		}
		else{
			//싫어요를 누른상태
			resultHate = true;
		}
		
		return resultHate;
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
