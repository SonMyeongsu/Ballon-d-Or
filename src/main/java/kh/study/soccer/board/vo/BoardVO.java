package kh.study.soccer.board.vo;


import kh.study.soccer.home.vo.PageVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardVO extends PageVO {

	private int boardNum;
	private String memberId;
	private String boardTitle;
	private String boardContent;
	private String isSecret;
	private String boardReadCnt;
	private String boardRegDate;
	
	private int likeCount;
	private int hateCount;
	
	
}

