<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/BoardList.css">
<link rel="stylesheet" href="css/BoardInfo.css">
<link rel="stylesheet" href="css/BoardInsert.css">
<meta charset="UTF-8">
<title>SMT 게시판</title>
</head>
<body>
<%@ include file = "../../../header.jsp" %>
<section class="board">

	<h2 class="boardInfoTitle"><span>문의 수정</span></h2>

	<form action="updateBoard" method="post" encType = "multipart/form-data">
	<div id="insBoardBox">
		<input type="hidden" name="board_ID" value="${list.board_ID}" readonly>
		<div>
			<span>작성자</span><input type="text" name="comment_ID" value="${list.comment_ID}" class="form-control" readonly>
		</div>
		<div>
			<span>제목</span><input type="text" name="title" class="form-control"
				placeholder="제목을 입력해주세요." value="${list.title}" required >
		</div>	
		<div>
			<span>문의<br>내용</span>
				<textarea id="quesContents" class="form-control" rows="10" name="content"
				placeholder="내용을 입력해주세요" required >${list.contents}</textarea>
				<img id="previewImg" style="height:auto; max-width:150px;">
				<label for="imageInput">
					<svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="#21252978" class="bi bi-paperclip" viewBox="0 0 16 16">
					  <path d="M4.5 3a2.5 2.5 0 0 1 5 0v9a1.5 1.5 0 0 1-3 0V5a.5.5 0 0 1 1 0v7a.5.5 0 0 0 1 0V3a1.5 1.5 0 1 0-3 0v9a2.5 2.5 0 0 0 5 0V5a.5.5 0 0 1 1 0v7a3.5 3.5 0 1 1-7 0V3z"/>
					</svg>
				</label>
				<input type="file" id="imageInput" name="file_Name" onChange="previewImage(event)" accept="image/*" style="display:none;">
		</div>
	</div>
	<div style="text-align: right;">
		<button type="submit" class="btn BoardMenuBtn" id="insDBBtn" onClick="checkBoard();return = false;">
			<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-list" viewBox="0 0 16 16">
			  <path fill-rule="evenodd" d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5z"/>
			</svg>
			등록
		</button>
		<button type="button" class="btn BoardMenuBtn" onClick="cancelBoard()">
			<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-door-open-fill" viewBox="0 0 16 16">
			  <path d="M1.5 15a.5.5 0 0 0 0 1h13a.5.5 0 0 0 0-1H13V2.5A1.5 1.5 0 0 0 11.5 1H11V.5a.5.5 0 0 0-.57-.495l-7 1A.5.5 0 0 0 3 1.5V15H1.5zM11 2h.5a.5.5 0 0 1 .5.5V15h-1V2zm-2.5 8c-.276 0-.5-.448-.5-1s.224-1 .5-1 .5.448.5 1-.224 1-.5 1z"/>
			</svg>
			취소
		</button>
	</div>
	</form>
</section>
<%@ include file = "../../../footer.jsp" %>
<script>
function previewImage(event) {
	  var input = event.target;
	  if (input.files && input.files[0]) {
	    var reader = new FileReader();

	    reader.onload = function(e) {
	      var previewElement = document.getElementById('previewImg');
	      previewElement.src = e.target.result;
	    };

	    reader.readAsDataURL(input.files[0]);
	  }
	}
	
function cancelBoard(){
	if(confirm("작성한 내용은 저장되지 않습니다.\n정말 취소하시겠습니까?")){
		window.history.back();
	}
}
</script>
</body>
</html>