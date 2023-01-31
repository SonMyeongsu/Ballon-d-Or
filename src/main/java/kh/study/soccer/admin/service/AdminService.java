package kh.study.soccer.admin.service;

import java.util.List;


import kh.study.soccer.board.vo.BoardCategoryVO;

public interface AdminService {

	//게시판 카테고리 조회
	List<BoardCategoryVO> selectBoardCate();

	//게시판 카테고리 생성
	void regBoardCategory(BoardCategoryVO boardCategoryVO);
	
}
