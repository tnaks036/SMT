<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/BoardList.css">
<meta charset="UTF-8">
<title>SMT 게시판</title>
</head>
<body>
<%@ include file = "../../../header.jsp" %>

<section class="board">
	<form>	
		<div class="searchBox">
				<select class="custom-select" id="condition" name="condition">
					<option value="title">제목</option>
					<option value="contents">내용</option>
					<option value="comment_ID">작성자명</option>
				</select>
				<input type="text" id="searchKeyword" class="form-control" name="searchKeyword" maxlength="20">
				<button type="button" class="btn" onClick="getQuesList()">
				<svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
				  <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
				</svg>
				</button>
		</div>
	</form>
		
<p id = "boardCnt"></p>
<div id = "boardListDiv">
</div>

<div id = "pageBtns">
</div>
</section>
<%@ include file = "../../../footer.jsp" %>
<script src="js/board.js"></script>	
<script>
$(document).ready(function() {
	getQuesList();
});

document.addEventListener("keydown", function(event) {
    if (event.key === "Enter") {
    	event.preventDefault(); // 폼 제출 방지
        getQuesList(); // Enter 키 누르면 함수 호출
    }
});

function getQuesList(nNum){ //페이징 + 리스트 
	var condition = $("#condition").val();
	var searchKeyword = $("#searchKeyword").val();
	var nowNum = 0;
	if(nNum != null){
		nowNum = nNum;
	}
	
	$.ajax({
	    type: "POST",
	    url: "goBoard", 
	    data: {
	    	"condition" : condition,
	    	"searchKeyword" : searchKeyword,
	    	"nowNum" : nowNum
	    },
	   /*  processData: false,
	    contentType: false, */
	    success: function(data) {
	    	var bList = data.list;
	    	var pageInfo = data.pageInfo;
	    	var comment_html = "";
	    	var pageBtn_html = "";
	    	
	    	if(Array.isArray(bList) && bList.length > 0){
				comment_html = "<table class='board-table'>";
				comment_html += "<thead>";
				comment_html += "<tr>";
				comment_html += "<th scope='col' class='th-num'>번호</th>";
				comment_html += "<th scope='col' class='th-status'>상태</th>";
				comment_html += "<th scope='col' class='th-title'>제목</th>";
				comment_html += "<th scope='col' class='th-writer'>작성자</th>";
				comment_html += "<th scope='col' class='th-date'>등록일</th>";
				comment_html += "</tr>";
				comment_html += "</thead>";
				comment_html += "<tr>";
				comment_html += "<tbody>";
				
				for(i = 0;i < bList.length;i++){
					var board_ID = bList[i].board_ID;
					var comment_ID = bList[i].comment_ID;
					var title = bList[i].title;
					var ins_Date_Time = bList[i].ins_Date_Time;
					var answerCnt = bList[i].answerCnt;
					
					comment_html += "<tr OnClick='BoardInfo("+ board_ID + ")' class='boardList'>";
					comment_html += "<td class='td-num'>" + board_ID + "</td>";

					if(answerCnt > 0){
						comment_html += "<td><span class='ansStatusOK'>답변완료</span></td>";
					}else{
						comment_html += "<td><span class='ansStatusNO'>답변대기</span></td>";
					}
					
					comment_html += "<td>" + title + "</td>";
					comment_html += "<td>" + comment_ID + "</td>";
					comment_html += "<td>" + ins_Date_Time + "</td>";
					comment_html += "</tr>";
					
					}
				
					comment_html += "</tbody>";
					comment_html += "</table>";
					comment_html += "<div class='insBtnBox'>";
					comment_html += "<button class='btn BoardMenuBtn' onClick='insertBoard()'>";
					comment_html += "<svg xmlns='http://www.w3.org/2000/svg' width='16' height='16' fill='currentColor' class='bi bi-pencil-fill' viewBox='0 0 16 16'>";
					comment_html += "<path d='M12.854.146a.5.5 0 0 0-.707 0L10.5 1.793 14.207 5.5l1.647-1.646a.5.5 0 0 0 0-.708l-3-3zm.646 6.061L9.793 2.5 3.293 9H3.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.207l6.5-6.5zm-7.468 7.468A.5.5 0 0 1 6 13.5V13h-.5a.5.5 0 0 1-.5-.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.5-.5V10h-.5a.499.499 0 0 1-.175-.032l-.179.178a.5.5 0 0 0-.11.168l-2 5a.5.5 0 0 0 .65.65l5-2a.5.5 0 0 0 .168-.11l.178-.178z'/>";
					comment_html += "</svg>";
					comment_html += "<span class='insBtnSpan'>문의하기</span>";
					comment_html += "</button>";
					comment_html += "</div>";
					
					if(pageInfo.listNum != 0){
			    		
			    		if(!(pageInfo.nowNum == 1 || pageInfo.endNum == 1)){
				    		pageBtn_html += "<a onClick='getQuesList(1)' class='pBtn'>&lt;&lt;</a>";
				    		pageBtn_html += "<a class='pBtn' onClick='getQuesList(" + (pageInfo.nowNum - 1) + ")'>이전</a>";
			    		}

			    		for (i = pageInfo.startNum; i <= pageInfo.endNum; i++) {
			    			if(i == pageInfo.nowNum){
				    		    pageBtn_html += "<button class='pBtn pNumBtn selectedNumBtn' onClick='getQuesList(" + i + ")'>" + i + "</button>";
			    			}else{
				    		    pageBtn_html += "<button class='pBtn pNumBtn' onClick='getQuesList(" + i + ")'>" + i + "</button>";
			    			}
			    		}

			    		if(!(pageInfo.nowNum == pageInfo.totalNum || pageInfo.endNum == 1)){
				    		pageBtn_html += "<a class='pBtn' onClick='getQuesList(" + (pageInfo.nowNum + 1) + ")'>다음</a>";
				    		pageBtn_html += "<a class='pBtn' onClick='getQuesList(" + pageInfo.totalNum + ")'>&gt;&gt;</a>";
			    		}
			    	}
					
					
	    }else{
			comment_html += "등록된 결과가 없습니다.";  	
			pageBtn_html += "";    	
	    }
					$("#boardListDiv").html(comment_html);
			    	$("#pageBtns").html(pageBtn_html);
					var boardCnt = "총 게시글\t" + pageInfo.listNum;
					$("#boardCnt").html(boardCnt);
    },
	    error: function(xhr, status, error) {
	      alert("목록을 불러오는 중 에러가 발생했습니다.");
	    }
	  });
}
</script>
</body>
</html>