package kh.study.soccer.admin.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kh.study.soccer.admin.service.AdminService;
import kh.study.soccer.board.vo.BoardCategoryVO;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Resource(name = "adminService")
	private AdminService adminService;
	
	//게시판 카테고리 페이지 이동
	@GetMapping("/regBoardCatePage")
	public String regBoardCatePage(Model model) {
	
		model.addAttribute("cateList",adminService.selectBoardCate());
		//model.addAttribute("subList", adminService.selectBoardSubCate(boardCateCode));
		
		return "pages/admin/reg_board_cate";
	};
	
	//비동기적 서브 카테고리 리스트 조회
	//받는값 : String boardCateCode(1계층의 카테고리 코드)
	//반환값 : List<BoardCategoryVO> (선택한 1계층의 하위 BoardCategoryVO 리스트)
	@ResponseBody //ajax사용시 써야함.
	@PostMapping("/selectBoardSubCate")
	public List<BoardCategoryVO> selectBoardSubCate(String boardCateCode, Model model){
		
		model.addAttribute("subList", adminService.selectBoardSubCate(boardCateCode));
		
		return null; // 카테고리 3계층 생성시(1계층 셀박 클릭으로 인한 ajax함수 발동시)
	}
	
	//게시판 카테고리 등록
	@PostMapping("/regBoardCategory")
	public String regBoardCategory(@RequestParam Map<String,String> paramMap) {
		
		//1계층값을 들고 왔을때 
		if(paramMap.get("boardCateName") != null && !paramMap.get("boardCateName").equals("") ) {
			
			adminService.regBoardCategory(paramMap);
		}
		
		//2계층값을 들고 왔을때 
		else if(paramMap.get("boardSubCateName") != null && !paramMap.get("boardSubCateName").equals("")) {
			adminService.regBoardSubCategory(paramMap);
		}
		
		return "redirect:/admin/regBoardCatePage";
	};
	
	
	
}
