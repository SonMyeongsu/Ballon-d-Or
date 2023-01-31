package kh.study.soccer.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.study.soccer.board.vo.BoardCategoryVO;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
	
	@Autowired SqlSessionTemplate sqlSession;

	
	//게시판 카테고리 조회
	@Override
	public List<BoardCategoryVO> selectBoardCate() {
		return sqlSession.selectList("adminMapper.selectBoardCate");
	}


	//게시판 카테고리 생성
	@Override
	public void regBoardCategory(BoardCategoryVO boardCategoryVO) {
		sqlSession.insert("adminMapper.insertBoardCate", boardCategoryVO);
	}


	
}
