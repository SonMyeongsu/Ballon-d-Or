package kh.study.soccer.admin.service;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.study.soccer.board.vo.BoardCategoryVO;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
	
	@Autowired SqlSessionTemplate sqlSession;

	
	//게시판 1계층 카테고리 조회
	@Override
	public List<BoardCategoryVO> selectBoardCate() {
		return sqlSession.selectList("adminMapper.selectBoardCate");
	}


	//게시판 1계층 카테고리 생성
	@Override
	public void regBoardCategory(Map<String, String> paramMap) {
		sqlSession.insert("adminMapper.insertBoardCate", paramMap);
	}

	//게시판 2계층 카테고리 조회
	@Override
	public List<BoardCategoryVO> selectBoardSubCate(String boardCateCode) {
		return sqlSession.selectList("adminMapper.selectBoardSubCate", boardCateCode);
	}

	//게시판 2계층 카테고리 생성
	@Override
	public void regBoardSubCategory(Map<String, String> paramMap) {
		sqlSession.insert("adminMapper.insertBoardSubCate", paramMap);
	}


	
}
