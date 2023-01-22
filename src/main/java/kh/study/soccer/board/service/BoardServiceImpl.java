package kh.study.soccer.board.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.study.soccer.board.vo.BoardHateVO;
import kh.study.soccer.board.vo.BoardLikeVO;
import kh.study.soccer.board.vo.BoardVO;
import kh.study.soccer.member.vo.MemberVO;

@Service("boardService")
public class BoardServiceImpl implements BoardService{
//	Add unimplemented methods 하는 단축키는 아래와 같습니다. 
//	1.  Ctrl + .      (오류가 난 부분 선택)
//	2.  Ctrl + 1     (오류 해결방안)
//	3.  Enter        (적용)
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	//게시글 등록
	@Override
	public void regBoard(BoardVO boardVO) {
		sqlSession.insert("boardMapper.regBoard", boardVO);
		
	}

	//게시글 목록
	@Override
	public List<BoardVO> boardList(BoardVO boardVO) {
		
		return sqlSession.selectList("boardMapper.selectBoardList", boardVO);
	}

	//상세페이지
	@Override
	public BoardVO boardDetail(int boardNum) {
		return sqlSession.selectOne("boardMapper.selectBoardDetail", boardNum);
	}
	
	//추천 기능
	//////////////
		//좋아요 상태확인
		@Override
		public BoardLikeVO boardLikeCheck(BoardLikeVO boardLikeVO) {
			return sqlSession.selectOne("boardMapper.boardLikeCheck", boardLikeVO);
		}
		//좋아요 실행
		@Override
		public void likeProcess(BoardLikeVO boardLikeVO) {
			sqlSession.insert("boardMapper.insertLike", boardLikeVO);
		}
		
		//싫어요 상태확인
		@Override
		public BoardHateVO boardHateCheck(BoardHateVO boardHateVO) {
			return sqlSession.selectOne("boardMapper.boardHateCheck", boardHateVO);
		}
		//싫어요 실행
		@Override
		public void hateProcess(BoardHateVO boardHateVO) {
			sqlSession.insert("boardMapper.insertHate", boardHateVO);
		}
		///////////////
		
		
		
		
	
	// 조회수 증가
	@Override
	public void updateReadCnt(int boardNum) {
		sqlSession.update("boardMapper.updateReadCnt", boardNum);
	}
	
	//수정하기
	@Override
	public void updateBoard(BoardVO boardVO) {
		sqlSession.update("boardMapper.updateBoard", boardVO);
	}
	
	//삭제하기
	@Override
	public void deleteBoard(int boardNum) {
		sqlSession.delete("boardMapper.deleteBoard", boardNum);
	}






}
