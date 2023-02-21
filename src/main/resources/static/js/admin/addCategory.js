
radio3(); // 문서 불러오자마자 기본 발동

function radio1(){

	//$("#firstCate").css('display', 'inline')  //j쿼리 선택자
	//$("#secondCate").css('display', 'none')
	//$("#thirdCate").css('display', 'none')
	
	document.getElementById("firstCate").style.display = "inline";
	document.getElementById("secondCate").style.display = "none";
	document.getElementById("thirdCate").style.display = "none";
}

function radio2(){

	//$("#firstCate").css('display', 'none')  //j쿼리 선택자
	//$("#secondCate").css('display', 'inline')
	//$("#thirdCate").css('display', 'none')
	
	document.getElementById("firstCate").style.display = "none";
	document.getElementById("secondCate").style.display = "inline";
	document.getElementById("thirdCate").style.display = "none";
}

function radio3(){

	//$("#firstCate").css('display', 'none')  //j쿼리 선택자
	//$("#secondCate").css('display', 'none')
	//$("#thirdCate").css('display', 'inline')
	
	//onclick을 클릭 하지않아도 default로 radio3메소드 실행
	
	document.getElementById("firstCate").style.display = "none";
	document.getElementById("secondCate").style.display = "none";
	document.getElementById("thirdCate").style.display = "inline";
}




for(const radio of document.querySelectorAll(".radioCate") )	{ 
	// radio버튼을 누를 때 마다 input을 내용을 제거
	radio.addEventListener('change', function(event){
		document.querySelector('input[name="boardCateName"]').value="";
		document.querySelector('input[name="boardSubCateName"]').value="";
		document.querySelector('input[name="boardDeepSubCateName"]').value="";
		
	})
	
}


//------------------------------------------------------------




function aaa() {
	$.ajax({
		url: '/admin/selectBoardSubCate', //요청경로
		type: 'post',
		data:{'boardCateCode': document.querySelector('#firstSelectedBox').value }, //필요한 데이터
		// html에서 1계층 셀렉트태그 id를 쿼리셀렉터로 찾음
		
		success: function(subList) {
			
			//셀렉트태그를 qs(쿼리셀렉터)사용해서 불러옴.
			var subSelect= document.querySelector('#secondSelectedBox');	
					
			//셀렉트태그 innerHTML을 비운다.
			subSelect.innerHTML = '';
			
			//일단 셀렉트태그에 '<option value="">선택해주세요</option>'를 추가해준다.
			subSelect.innerHTML += `<option value="">선택해주세요</option>`;
			
			for(const sub of subList ) {
				//console.log(sub);
								
				// 셀렉트태그에 '<option value="${sub.boardCateCode}">${sub.boardCateName}</option>'를 추가해준다.
				subSelect.innerHTML += `<option value="${sub.boardCateCode}">${sub.boardCateName}</option>`;
				
			
			
			}
			
			


		},
		error: function() {
			alert('실패');
		}
	});
}


  
 


 

