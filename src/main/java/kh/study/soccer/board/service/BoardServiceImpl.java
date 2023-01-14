package kh.study.soccer.board.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.study.soccer.board.vo.BoardVO;
import kh.study.soccer.member.vo.MemberVO;

@Service("boardService")
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	//게시글 등록
	@Override
	public void regBoard(BoardVO board) {
		sqlSession.insert("boardMapper.regBoard", board);
		
	}

	//게시글 목록
	@Override
	public List<BoardVO> boardList(BoardVO board) {
		
		return sqlSession.selectList("boardMapper.boardList", board);
	}

	//상세페이지
	@Override
	public BoardVO boardDetail(int boardNum) {
		
		return sqlSession.selectOne("boardMapper.boardDetail", boardNum);
	}
	
	//수정하기
	@Override
	public void updateBoard(BoardVO board) {
		sqlSession.update("boardMapper.updateBoard", board);
	}
	
	//삭제하기
	@Override
	public void deleteBoard(int boardNum) {
		sqlSession.delete("boardMapper.deleteBoard", boardNum);
	}

}
