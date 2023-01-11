package kh.study.board.board.vo;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardVO {

	private int boardNum;
	
	@NotBlank(message = "제목을 입력하세요")
	private String boardTitle;
	
	@NotBlank(message = "내용을 입력하세요")
	private String boardContent;
	
	private String memberId;
	private String createDate;
	
}
