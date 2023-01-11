package kh.study.board.member.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kh.study.board.member.vo.MemberVO;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	//회원가입
	@Override
	public void join(MemberVO member) {
		
		String pw = passwordEncoder.encode(member.getMemberPw());
		member.setMemberPw(pw);
		
		sqlSession.insert("memberMapper.join", member);
		
	}
	
	//로그인
	@Override
	public MemberVO login(String memberId) {
		
		return sqlSession.selectOne("memberMapper.login", memberId);
	}
}
