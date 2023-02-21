package kh.study.soccer.admin.service;

import java.util.List;
import java.util.Map;

import kh.study.soccer.board.vo.BoardCategoryVO;

public interface AdminService {

	//게시판 1계층 카테고리 조회
	List<BoardCategoryVO> selectBoardCate();

	//게시판 1계층 카테고리 생성
	void regBoardCategory(Map<String, String> paramMap);
	
	//게시판 2계층 카테고리 조회
	List<BoardCategoryVO> selectBoardSubCate(String boardCateCode);
	
	//게시판 2계층 카테고리 생성
	void regBoardSubCategory(Map<String, String> paramMap);
	
	//게시판 3계층 카테고리 생성
	void regBoardDeepSubCategory(Map<String, String> paramMap);
	
}
