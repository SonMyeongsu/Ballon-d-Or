package kh.study.soccer.member.service;

import kh.study.soccer.member.vo.MemberVO;

public interface MemberService {

	
	//회원가입
	void join(MemberVO memberVO);
	
	
	//로그인
	MemberVO login(String memberId);
}
