package kh.study.board.member.service;

import kh.study.board.member.vo.MemberVO;

public interface MemberService {

	
	//회원가입
	void join(MemberVO member);
	
	
	//로그인
	MemberVO login(String memberId);
}
