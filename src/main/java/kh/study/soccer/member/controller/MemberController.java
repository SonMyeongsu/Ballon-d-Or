package kh.study.soccer.member.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kh.study.soccer.member.service.MemberService;
import kh.study.soccer.member.vo.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {
	@Resource(name ="memberService")
	private MemberService memberService;
	// 커맨드 객체 (요청들어오면 어떤 페이지로 넘어가는 mapping있을 때 매개변수)
	// 커맨드 객체는 html로 데이터를 전달하는 코드를 작성하지 않아도 자동으로 넘어간다
	// 이때 데이터가 넘어가는 이름은 클래스명에서 앞글자만 소문자로 변경된 이름으로 넘어간다
	

	// 회원가입 페이지 이동
	 @GetMapping("/join") 
	 public String join(MemberVO memberVO) {
	 
		 return "pages/member/join"; 
	 }
	 
	// 회원가입
	 
	// @Valid : post로 전달된 데이터가 검증 규칙을 따르는지 판단하는 역할, 기본모양 @Valid 검증객체
	// 해당 annotation 다음에는 반드시 검증할 객체가 선언되어야 한다.
	// BindingResult : 검증 대상 객체(memberVO)와 검증 결과에 대한 정보를 담고 있는 객체
	// (예시 memberVO)검증 객체 바로 다음에 선언되어야 한다.
	// Model bindingResult를 html로 자동으로 보내준다 그래서 object가 memberVO 받는다 (없어도 됌)
	@PostMapping("/joinProcess")
	public String joinProcess(@Valid MemberVO memberVO
								, BindingResult bindingResult
	/* , Model model */) {
			
		// validation체크 (데이터 유효성 검증 잘못입력 시 다시 페이지로 이동하도록)
		if (bindingResult.hasErrors()) { // 오류가 생겼니?
			System.out.println("에러가 발생했습니다.");
			 return "pages/member/join"; 
		}
		// 회원가입 쿼리 실행
		else {
			memberService.join(memberVO);
			// 게시글 목록 페이지로 이동
			return "redirect:/board/list";
		}
			
	}
	
	//로그인 페이지 이동
	@GetMapping("/login")
	public String login(MemberVO memberVO) {
	
		return "content/login";
	}
	
	/*	userDetailsService 때문에 필요없게 된다.
	 * // 로그인
	 * 
	 * @PostMapping("/login") public String loginProcess(String memberId,
	 * HttpSession session) {
	 * 
	 * MemberVO loginInfo = memberService.login(memberId);
	 * 
	 * //로그인 성공 if(loginInfo != null) { session.setAttribute("loginInfo",
	 * loginInfo); return "redirect:/board/list"; } //로그인 실패 다시 로그인 페이지 (되돌아 왔을때 id,
	 * pw 그대로 남아 있도록) //방법1 (데이터 가져 감) 입력한 데이터 가지고 가기 편한 것 선택 return
	 * "content/login"; //커맨드 객체라 데이터 자동으로 넘어감 (모델 사용하지 않아도 가능) //(그런데 커맨드 객체 안에 내가
	 * 작성한 아이디랑 비번 value값이 포함된 채로 넘어감)
	 * 
	 * //방법2 (데이터 못가져 감) //return "redirect:/member/login"; //get방식으로 넘기는 것
	 * 
	 * }
	 */
	
	/*
	 * //로그아웃
	 * 
	 * @GetMapping("/logout") public String logout(HttpSession session) {
	 * session.removeAttribute("loginInfo"); return "redirect:/board/list"; }
	 */
	

}
