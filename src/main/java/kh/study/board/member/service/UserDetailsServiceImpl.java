package kh.study.board.member.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kh.study.board.member.vo.MemberVO;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	//로그인 기능 실행 메소드(언제 쓰라고 코드 넣어주지 않아도 post방식으로 /login 요청 발생 시 자동으로 실행된다)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		MemberVO loginInfo = sqlSession.selectOne("memberMapper.login", username);
		
		//아이디 잘못 입력했을 때 실행되면 안되니까 예외 발생 처리
		if(loginInfo == null) {
			throw new UsernameNotFoundException("아이디가 없습니다");
		}
		
		UserDetails userDetails = User.withUsername(loginInfo.getMemberId())	
									  .password(loginInfo.getMemberPw())
									  .roles(loginInfo.getIsAdmin())
									  .build();
		
		return userDetails;
	}
	
	
	
	
}
