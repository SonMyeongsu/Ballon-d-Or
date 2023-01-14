package kh.study.soccer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
	
public class SecurityConfig {
	@Bean	//메소드의 리턴 타입에 대한 객체 생성 annotation, 주로 메소드 위에서 정의
	public SecurityFilterChain filterChain(HttpSecurity security) throws Exception{	//메소드 실행 때 예외처리 다른 곳에서 할게 (다른곳으로 던져 버리겠다)
	
		security.csrf().disable()
				.authorizeRequests()
				//로그인 회원가입 게시글 목록
				.antMatchers("/member/login"
							,"/member/join"
							,"/board/list").permitAll()  //, "/member/**" member들어가는거 다 허용할게라고 쓸 수 있다.
				.anyRequest().authenticated()
			.and()
				.formLogin()
				.loginPage("/member/login")
				.defaultSuccessUrl("/board/list")
				.failureUrl("/member/loginFail")
				.loginProcessingUrl("/member/login")  //실제 로그인을 진행할 요청 정보 th:action="@{/member/login}" 맞춰줌
			.and()
				.exceptionHandling()
				.accessDeniedPage("/member/accessDenied")	//인증했지만 권한이 없어서 못 들어갈 때 
			.and()
				.logout()
				.invalidateHttpSession(true)
				.logoutSuccessUrl("/board/list");
				
		return security.build();
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}

	//css, js 파일 서칭 무시하라
	//static 하위 폴더에 있는 js, css, img는 모든 사람이 접근해야하기 때문에 security 무시한다.

	 @Bean
	 public WebSecurityCustomizer webSecurityCustomizer() {
	      return (web) -> web.ignoring().antMatchers("/js/**",  "/css/**", "/img/**");
	 }
}
