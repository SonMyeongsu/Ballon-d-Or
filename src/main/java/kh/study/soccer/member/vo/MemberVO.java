package kh.study.soccer.member.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberVO {

	private String memberId;
	private String memberPw;
	private String memberName;
	private String memberTell;
	private String isAdmin;
	private String memberEmail;
	private String memberCreateDate;
}
