<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<!DOCTYPE html5>
<html>
<head>
<link rel="stylesheet" href="css/BoardList.css">
<link rel="stylesheet" href="css/BoardInfo.css">
<meta charset="UTF-8">
<title>SMT 게시판</title>
</head>
<body>
<%@ include file = "../../../header.jsp" %>
<section class="board">

<h2 class="boardInfoTitle"><span>상세보기</span></h2>

<div id="boardModiDelBtnBox">
	<button type="button" class="btn BoardMenuBtn" onClick="updateFrm(${list.board_ID})">
		<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
		  <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
		  <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
		</svg>
		수정
	</button>
	<button type="button" class="btn BoardMenuBtn" onClick="deleteBoard(${list.board_ID})">
		<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
		  <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6Z"/>
		  <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1ZM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118ZM2.5 3h11V2h-11v1Z"/>
		</svg>
		삭제
	</button>
</div>
<div id="boardInfoBox">
	<c:choose>
		<c:when test="${list.answerCnt ne null}">
			<div id="quesInfoStatus">
				<span class="ansStatusOK">답변완료</span>
			</div>
		</c:when>
		<c:otherwise>
			<div id="quesInfoStatus">>
				<span class="ansStatusNO">답변대기</span>
			</div>
		</c:otherwise>
	</c:choose>
	<div style="display: flex; border-bottom: 1px solid black;">
		<input type="text" id="boardTitle" name="title" class="form-control" value="${list.title}" readonly>
		<input style="text-align: right;" id="insDateTime" type="text" class="form-control" value="${list.ins_Date_Time}" readonly>
	</div>

	<div style="display: flex; justify-content: space-between;">
		<p>${list.comment_ID}</p>
	<%-- <c:if test="${list.file_ViewName ne null}"> --%>
	<c:if test="${list.file_Name ne null}">
 		<a class="downloadLink" href="downloadImg?file_Name=${list.file_ViewName}&board_ID=${list.board_ID}">[첨부파일]</a> 
	</c:if>
	</div>
	<c:if test="${list.file_ViewName ne null}">
 		<img src="data:image/png;base64,${list.file_ViewName}" alt="Image"> 
	</c:if>
	<p>
	${list.contents}
	</p>
</div>

<p id="AnsInsBoxTitle">답변</p>
<div id="AnsInsBox">
	<form id="frm" enctype="multipart/form-data">
		<div class="row">
			<div class="col-md-10 col-12">
				<input type="hidden" id="board_ID" name="board_ID" value="${list.board_ID}" readonly>
				<input type="hidden" id="comment_ID" name="comment_ID" value="${list.comment_ID}" readonly>
				<input type="text" id="answerID" class="form-control" placeholder="작성자" maxLength=10>
				<hr>
				<textarea id="ansContents" name="ansContent" class="form-control" rows="3" placeholder="댓글을 입력해주세요" maxLength=500></textarea>
				<div id="fileLabelBox">
					<label for="imageInput">
						<svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="#21252978" class="bi bi-paperclip" viewBox="0 0 16 16">
						  <path d="M4.5 3a2.5 2.5 0 0 1 5 0v9a1.5 1.5 0 0 1-3 0V5a.5.5 0 0 1 1 0v7a.5.5 0 0 0 1 0V3a1.5 1.5 0 1 0-3 0v9a2.5 2.5 0 0 0 5 0V5a.5.5 0 0 1 1 0v7a3.5 3.5 0 1 1-7 0V3z"/>
						</svg>
					</label>
					<p id="originName" style="display : inline-block; color: #21252978; font-size:0.8rem; margin-right:10px;"></p>
					<img id="previewImg" style="height:auto; max-width:720px;">
				</div>
				<input type="file" id="imageInput" name="file_Name" onChange="previewImage(event)" style="display:none;">
			</div>
			<div class="col-md-2 col-12">
				<button type="button" class="btn" id="insAnsBtn" onClick="insAns(${list.board_ID})">등록</button>
			</div>
		</div>
	</form>
</div>

<hr> 
<div class="comment_Box"> <!-- 댓글이 들어갈 박스 -->
</div>

<div id="preNextBoard_Box"><!-- 이전글, 다음글 박스 -->
	<p><span><strong>이전글</strong></span><a onClick="BoardInfo(${preBoard_ID})">${preTitle}</a></p>
	<p><span><strong>다음글</strong></span><a onClick="BoardInfo(${nextBoard_ID})">${nextTitle}</a></p>
</div>
</section>
<%@ include file = "../../../footer.jsp" %>
<script src="js/board.js"></script>	
<script>

$(document).ready(function() {
	getAnsList();
});

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
	
function deleteBoard(boardID){
	var board_ID = boardID;
	if(confirm("해당 문의를 삭제하시겠습니까?")){
		$.ajax({
		    type: "POST",
		    url: "deleteBoard", 
		    data: {
		    	"board_ID" : board_ID
		    },
		    contentType : false,
		    processData : false,
		    success: function(data) {
		    	if(data.result == "suc"){
		    		alert("삭제가 완료되었습니다.");
		    		location.href = "boardPage";
		    	}else{
		    		alert("오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
		    		return;
		    	}
		    },
		    error: function(xhr, status, error) {
		      alert("에러가 발생했습니다.");
		    }
		  });
	}
}

function insAns(board_ID) {
	  var answerID = $("#answerID").val();
	  var ansContents = $("#ansContents").val();
	  var fileInput = $("#imageInput")[0];
	  var file = fileInput.files[0];
      var commentID = $("#comment_ID").val();
	  
      if(answerID == ""){
    	  alert("댓글 작성자를 작성해주세요.");
    	  $("#answerID").focus();
    	  return;
      }
      
      if(ansContents == ""){
    	  alert("내용을 작성해주세요.");
    	  $("#ansContents").focus();
    	  return;
      }
      
	  var formData = new FormData();
	  formData.append("board_ID", board_ID);
	  formData.append("comment_ID",commentID);
	  formData.append("answerID", answerID);
	  formData.append("ansContents", ansContents);
	  if (file) {
	    formData.append("fileNm", file);
	  }

	  $.ajax({
	    type: "POST",
	    url: "insAns", 
	    data: formData,
	    processData: false,
	    contentType: false,
	    success: function(response) {
	    	getAnsList();
	    	var previewElement = document.getElementById('previewImg');
	        previewElement.src = ''; // src를 비우는 부분
	    	frm.reset();
	    	
	    },
	    error: function(xhr, status, error) {
	      alert("댓글 등록 실패");
	    }
	  });
	}

function getAnsList() {
	
	const board_ID = $("#board_ID").val();
	const answer_ID = $("#answerID").val();
	
	var formData = new FormData();
    formData.append("board_ID", board_ID);
    
    $.ajax({
		type: "POST",
	    url: "listAns",
	    data: formData,
	    processData: false,
	    contentType: false,
	    success: function(data) {
			if(Array.isArray(data) && data.length > 0){
				var list = data;
				
				var comment_html = "<div>";
				
				for(i = 0;i < list.length;i++){
					var content = list[i].Contents;
					var writer = list[i].anser_ID;
					var ins_Date_Time = list[i].ins_Date_Time;
					var file_ViewName = list[i].file_ViewName;
					var file_Name = list[i].file_Name;
					comment_html += "<div>";
					comment_html += "<form enctype='multipart/form-data'>" //폼시작
					comment_html += "<input type='hidden' class='board_ID form-control' name='board_ID' value='${list.board_ID}'>";
					comment_html += "<div class='ansWriterDate'>";
					comment_html += "<input type='text' class='answerID form-control' value='" + writer + "' style='display:block;' readonly>";
					comment_html += "<input type='text' class='insDateTime form-control' value='" + ins_Date_Time + "' style='display:block;' readonly>";
					comment_html += "</div>";
					
					if(file_Name != null){
						comment_html += "<div style='text-align:right;'><a class='downloadLink' href='downloadImg?board_ID=${list.board_ID}&reply=1'>[첨부파일]</a></div>"; 
					}
					
					if(file_ViewName != null){
						comment_html += "<img id='ansImge' src='data:image/png;base64,"+ file_ViewName + "' alt='Image'><br>";
					}
					
					comment_html += "<textarea class='Anscontent form-control' readonly>" + content + "</textarea>";
					
					comment_html += "<div class='ansUpdfileLabelBox' style='display:none;'>";
					comment_html += "<label for='ansUpdimageInput" + i +"'>";
					comment_html += "<svg xmlns='http://www.w3.org/2000/svg' width='25' height='25' fill='#21252978' class='bi bi-paperclip' viewBox='0 0 16 16'>";
					comment_html += "<path d='M4.5 3a2.5 2.5 0 0 1 5 0v9a1.5 1.5 0 0 1-3 0V5a.5.5 0 0 1 1 0v7a.5.5 0 0 0 1 0V3a1.5 1.5 0 1 0-3 0v9a2.5 2.5 0 0 0 5 0V5a.5.5 0 0 1 1 0v7a3.5 3.5 0 1 1-7 0V3z'/>";
					comment_html += "</svg>";
					comment_html += "</label>";
					comment_html += "<img class='ansUpdpreviewImg' style='max-height:50px; width:auto;'>";
					comment_html += "<input type='file' id='ansUpdimageInput" + i + "' class='fileNm' name='file_Name' style='display:none;'>";
					comment_html += "</div>";
					comment_html += "<div class='ansUpdDBCancle' style='display:none;'>";
					comment_html += "<button type='button' class='btn updDBBtn'>등록</button>";
					comment_html += "<span class='btnLine' style='display:inline-block!important;'>|</span>"
					comment_html += "<button type='button' class='btn updDelBtn'>취소</button>";
					comment_html += "</div>";
					comment_html += "</form>"; //폼끝

					
					comment_html += "<div class='ansUpdDelBtn'>"
					comment_html += "<button type='button' class='btn updBtn'>수정</button>";
					comment_html += "<span class='btnLine1'>|</span>"
					comment_html += "<button type='button' class='btn delBtn'>삭제</button><br></div>";
					comment_html += "</div>"
					comment_html += "<hr class='ansHr'>";
					}
				
					comment_html += "</div>";
					
					$(".comment_Box").html(comment_html);
					
					// 수정/삭제 버튼 클릭 이벤트 핸들러
					$(".updBtn").click(function() {//수정
					    var $container = $(this).closest("div").parent();
					    var $textarea = $container.find(".Anscontent");//textarea
					    var $saveBtn = $container.find(".updDBBtn");//수정완료버튼
					    var $delBtn = $container.find(".delBtn");//삭제버튼
					    var $btnLine = $container.find(".btnLine");//수정 삭제 구분선
					    var $updDelBtn = $container.find(".updDelBtn");//수정취소버튼
					    var $ansUpdDelBtn = $container.find(".ansUpdDelBtn");//수정취소버튼
					    var $ansUpdfileLabelBox = $container.find(".ansUpdfileLabelBox");//수정 파일 올릴 수 있는 div
					    var $ansUpdDBCancle = $container.find(".ansUpdDBCancle");//수정, 취소버튼 있는 div
					    
					    $ansUpdDelBtn.hide();
					    $textarea.prop("readonly", false);
					    $textarea.focus();
					    /* $saveBtn.show();*/
					    $updDelBtn.show(); 
					    $ansUpdfileLabelBox.show();
					    $ansUpdDBCancle.show();
					});
					
					$(".updDelBtn").click(function() {//수정취소
					    var $container = $(this).closest("div").parent().parent();
					    var $textarea = $container.find(".Anscontent");//textarea
					    var $ansUpdDelBtn = $container.find(".ansUpdDelBtn");//수정|삭제 div
					    var $ansUpdfileLabelBox = $container.find(".ansUpdfileLabelBox");//수정 파일 올릴 수 있는 div
					    var $ansUpdDBCancle = $container.find(".ansUpdDBCancle");//수정, 취소버튼 있는 div
					    
					    $textarea.prop("readonly", true);
					    /* $saveBtn.hide();
					    $(this).hide(); */
					    $ansUpdfileLabelBox.hide();
					    $ansUpdDBCancle.hide();
					    $ansUpdDelBtn.show();
					    
					    $textarea.val($textarea.prop("defaultValue")); //초기값으로 돌리기
					});
					
					$(".updDBBtn").click(function() {//수정 DB입력
						if(confirm("정말 수정하시겠습니까?")){
						    var $container = $(this).closest("div").parent();
						    var $textarea = $container.find(".Anscontent");
						    
						    var boardID = $container.find(".board_ID").val();
						    var content = $textarea.val();
						    var fileInput = $container.find(".ansUpdfileLabelBox").find(".fileNm")[0];
						    var file = fileInput.files[0];
						    var answerID = $container.find(".answerID").val();
							  
						    var formData = new FormData();
						    formData.append("board_ID", board_ID);
						    formData.append("answerID", answerID);
						    formData.append("content", content);
						    console.log(file);
						    if (file != null) {
						      formData.append("fileNm", file);
						    }
						    
						    $.ajax({
						        url: "updAns", 
						        type: "POST",
						        data: formData,
						        processData: false,
							    contentType: false,
						        success: function(response) {
									alert("수정이 완료되었습니다.");
									getAnsList();
						        },
						        error: function(xhr, status, error) {
						        	alert("수정에 실패하였습니다.");
						        }
						    });
						}
					});
					
					$(".delBtn").click(function() {//삭제
						if(confirm("댓글을 삭제하시겠습니까?\n 삭제 후 되돌릴 수 없습니다.")){
						    var $container = $(this).closest("div");
						    var $span = $container.find(".answerID");
						    var boardID = $("#board_ID").val();
						    var answerID = writer;
						    
						    $.ajax({
						        type: "POST",
						        url: "delAns", 
						        data: { "board_ID": boardID,
						        		"answer_ID": answerID }, 
						        success: function(response) {
									alert("댓글이 삭제되었습니다.");
									getAnsList();
						        },
						        error: function(xhr, status, error) {
						        	alert("댓글이 삭제가 실패하였습니다.");
						        }
						    });
						}
					});
					
					$(".fileNm").change(function(event) {//댓글 수정에 사진 미리보기
						  var input = event.target;
						  var $container = $(this).closest("div"); // 파일 입력 요소를 감싸는 가장 가까운 <div>
						  
						  if (input.files && input.files[0]) {
						    var reader = new FileReader();

						    reader.onload = function(e) {
						      var $previewImg = $container.find(".ansUpdpreviewImg"); // 해당 위치의 미리보기 이미지 요소
						      $previewImg.attr("src", e.target.result);
						    };

						    reader.readAsDataURL(input.files[0]);
						  }
						});
				}
			else{
				var comment_html = "<div>등록된 댓글이 없습니다.</div>";
				$(".comment_Box").html(comment_html);
			}
	    }
	});
}


</script>
</body>
</html>