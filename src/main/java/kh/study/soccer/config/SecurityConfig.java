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
	
		security.csrf().disable() /* disable(): 태그의 disabled 속성은 해당 태그 요소가 비활성화됨을 명시함. */ 
						   		  /* csrf().disable()은 csrf의 공격을 막기위한 기본설정을 해제함을 의미함. */
				.authorizeRequests() /* authorizeRequests() : 권한(인증,인가 모두 지칭) 에 대한 설정을 시작 */
				//로그인 회원가입 게시글 목록
				.antMatchers(//"/member/login",
							"/member/loginPage" /* antMatchers() : 특정 리소스에 대해서 권한을 설정합니다. */
							, "/member/joinPage" /* permitAll() : antMatchers 설정한 리소스의 접근을 인증절차 없이 허용한다는 의미 입니다. */
							, "/member/joinProcess"
							,"/board/boardList").permitAll()  //, "/member/**" member들어가는거 다 허용할게라고 쓸 수 있다.
				.anyRequest().authenticated() /* anyRequest() : 그외 나머지 모든 리소스 의미, anyRequest().authenticated() : 무조건 인증을 완료해야만 접근 가능*/ 
				/* .antMatchers().hasAnyRole() : 사용자가 주어진 어떤권한이라도 있으면 접근허용 */
				/* .antMatchers().hasRole() : 사용자가 주어진 역할이 있으면 접근허용 */
			.and()
				.formLogin() /* formLogin() : Spring Security에서 제공하는 인증방식임. */
				.loginPage("/member/loginPage") // loginPage() :  spring 로그인 페이지가 아닌 커스터마이징한 로그인 페이지로 이동함.
				.defaultSuccessUrl("/board/boardList") // defaultSuccessUrl() : 정상적으로 인증성공 했을 경우 이동 페이지를 설정함.
				.failureUrl("/member/loginFail")  //failureUrl() : 인증이 실패 했을 경우 이동하는 페이지를 설정함.
				.loginProcessingUrl("/member/login")  // loginProcessingUrl() : 실제 로그인을 진행할 요청 정보 th:action="@{/member/login}" 맞춰줌.
											  // 예제) loginProcessingUrl("/login-process") :로그인 즉 인증 처리를 하는 URL을 설정합니다. “/login-process” 가 호출되면 인증처리를 수행하는 필터가 호출됩니다.
			.and()
				.exceptionHandling() // 예외 처리기능이 작동함.
				.accessDeniedPage("/member/accessDenied") // accessDeniedPage() : 인증했지만 권한이 없는 페이지 접근시 호출할 페이지 경로. 
			.and()
				.logout() // 로그아웃기능을 작동함.
				.invalidateHttpSession(true) // invalidateHttpSession() : 로그아웃 이후 세션 전체 삭제 여부.
				.logoutSuccessUrl("/board/boardList"); // logoutSuccessUrl() : 로그아웃 성공시 리다이렉트 주소.
				
		return security.build();
		
	}
	
	// 비밀번호 암호화
	// 
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
