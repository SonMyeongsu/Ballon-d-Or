package kh.study.soccer.member.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kh.study.soccer.member.vo.MemberVO;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	//회원가입
	@Override
	public void join(MemberVO memberVO) {
		
		String pw = passwordEncoder.encode(memberVO.getMemberPw());
		memberVO.setMemberPw(pw);
		
		sqlSession.insert("memberMapper.join", memberVO);
		
	}
	
	//로그인
	@Override
	public MemberVO login(String memberId) {
		
		return sqlSession.selectOne("memberMapper.login", memberId);
	}
}
