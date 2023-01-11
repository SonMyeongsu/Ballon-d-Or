package kh.study.board.board.service;

import java.util.List;

import kh.study.board.board.vo.BoardVO;


public interface BoardService {

	//게시글 목록
	List<BoardVO> boardList(BoardVO board);
	
	//게시글 등록
	void regBoard(BoardVO board);
	
	//상세페이지

	BoardVO boardDetail(int boardNum);
	
	//수정하기
	void updateBoard(BoardVO board);
	
	//삭제하기
	void deleteBoard(int boardNum);
	
}
