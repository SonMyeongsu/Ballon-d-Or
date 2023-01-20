package kh.study.soccer.member.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberVO {
	@NotBlank(message = "ID는 필수입력입니다.")
	private String memberId;
	
	@NotBlank
	private String memberPw;
	
	@NotBlank(message = "이름은 필수입력입니다.")
	@Size(max = 5, message = "이름의 길이를 초과했습니다") //max = 5 글자 길이 5개까지 가능(영어 숫자 관계없이)
	private String memberName;
	
	@Size(min = 9, max = 11)
	private String memberTell;
	
	private String isAdmin;
	private String memberEmail;
	private String memberCreateDate;

	private String[] memberTells;
	
	public String getMemberTell() {
		//return memberTells[0] + memberTells[1] + memberTells[2];
		
		if(memberTells == null) {	//배열의 값이 없다면 
			return null;
		}
		else {					//연락처 칸에 데이터가 있다면 
			String result = "";
			for(String tell : memberTells) {
				result += tell;		//result =01011112222 이렇게 쌓인다.
			}
			return result;
		}
	}
}
