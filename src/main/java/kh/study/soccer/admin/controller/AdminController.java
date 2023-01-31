package kh.study.soccer.admin.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
		
		return "pages/admin/reg_board_cate";
	};
	
	//게시판 카테고리 등록
	@PostMapping("/regBoardCategory")
	public String regBoardCategory(BoardCategoryVO boardCategoryVO) {
		
		adminService.regBoardCategory(boardCategoryVO);
		
		return "redirect:/admin/regBoardCatePage";
	};
	
	
	
}
