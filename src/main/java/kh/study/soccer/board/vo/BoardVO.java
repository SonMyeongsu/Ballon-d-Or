package kh.study.soccer.board.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardVO {

	private int boardNum;
	private String memberId;
	private String boardTitle;
	private String boardContent;
	private String isSecret;
	private String boardReadCnt;
	private String boardRegDate;
	
}

