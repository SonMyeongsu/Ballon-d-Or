package kh.study.soccer.board.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardCategoryVO {

	private String boardCateCode;
	private String boardCateName;
	
	private String boardSubCateCode;
	private String boardSubCateName;
	
	private String boardDeepSubCateCode;
	private String boardDeepSubCateName;
	
	
}

