
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

 














