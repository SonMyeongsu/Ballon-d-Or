package kh.study.soccer.board.service;

import java.util.List;

import kh.study.soccer.board.vo.BoardLikeVO;
import kh.study.soccer.board.vo.BoardVO;


public interface BoardService {

	//게시글 목록
	List<BoardVO> boardList(BoardVO board);
	
	//게시글 등록
	void regBoard(BoardVO boardVO);
	
	//상세페이지
	BoardVO boardDetail(int boardNum);
	
	//조회수 증가
	void updateReadCnt(int boardNum);

	//추천 기능
	//////////////////
		//추천기능 상태 확인
		BoardLikeVO boardLikeCheck(BoardLikeVO boardLikeVO);
		// 추천기능 실행(좋아요or싫어요버튼 클릭시)
		void likeOrHateProcess(BoardLikeVO boardLikeVO);
		
	//수정하기
	void updateBoard(BoardVO boardVO);
	
	//삭제하기
	void deleteBoard(int boardNum);
	
}
