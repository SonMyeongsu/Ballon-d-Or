package kh.study.soccer.home.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageVO extends SearchVO {

	private int nowPage; //현재 선택된 페이지
	private int totalDataCnt; //전체 데이터 수
	private int beginPage; //화면에 보이는 첫 페이지
	private int endPage; //화면에 보이는 마지막 페이지
	private int displayCnt; //한 페이지에 보여지는 게시글 수
	private int displayPageCnt; //한 화면에 보여지는 페이지 수
	private boolean prev; //이전 버튼의 유무
	private boolean next; //다음 버튼 유무
	private int startNum; //시작 row_num
	private int endNum;//마지막 row_num 
	
	
	//생성자
	public PageVO()	{
		nowPage = 1;
		displayCnt = 5; //한 페이지에 보여지는 게시글 수
		displayPageCnt = 3; //한 화면에 보여지는 페이지 수
	}
	
	public void setPageInfo() {
		//화면에 보이는 마지막 페이지 번호
		// ceil : 전달된 double형 인자값을 소수부분이 있을시 무조건 올림하여 double형 실수로 반환함. 2.3 -> 3.0
		endPage = displayPageCnt * (int)Math.ceil(nowPage/(double)displayPageCnt);
		beginPage = endPage - displayPageCnt + 1;
		
		//전체 페이지 수
		int totalPageCnt = (int)Math.ceil(totalDataCnt/(double)displayCnt);
		
		//next 버튼의 유무
		if(endPage < totalPageCnt) {
			next = true;
		}
		else {
			next = false;
			endPage = totalPageCnt;
		}
	
		//prev 유무
		prev = beginPage == 1 ? false : true; // 화면에 보이는 페이지가 1이면 prev는 false가 되고 html에서 prev를 못씀, 거짓이면 prev는 true가 되고 html페이지서 prev사용가능
		
		
		//페이지 시작 게시글행
		startNum = (nowPage -1) * displayCnt + 1; // 예) 1페이지일 경우 (1-1)*10 +1; --> 1페이지 시작행은 1번째 게시물
												   // 예2) 2페이지일 경우 (2-1)*10 +1; --> 2페이지 시작행은 11번째 게시물
		//페이지 끝 게시글행
		endNum = nowPage * displayCnt; // endNum = 현재2페이지 * 한페이지당게시물수(10개) ---> 20번째게시물이 endNum
	}
	
}


