<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="css/default.css">
<link href='https://fonts.googleapis.com/css?family=Roboto:500,900,100,300,700,400' rel='stylesheet' type='text/css'>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script> 
<meta charset="UTF-8">

</head>
<body>
<nav class="navbar navbar-expand-custom navbar-mainbg">
	<a class="navbar-brand navbar-logo" href="index.jsp">SMT</a>
	<button class="navbar-toggler" type="button" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
		<svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="#ffffff" class="bi bi-list" viewBox="0 0 16 16">
		  <path fill-rule="evenodd" d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5z"/>
		</svg>
	</button>
	<div class="collapse navbar-collapse" id="navbarSupportedContent">
	    <ul class="navbar-nav ml-auto">
	        <div class="hori-selector"><div class="left"></div><div class="right"></div></div>
	        <li class="nav-item ">
	            <a class="nav-link menu" href="index.jsp">HOME</a>
	        </li>
	        <li class="nav-item ">
	            <a class="nav-link menu" href="boardPage">문의</a>
	        </li>
	        <li class="nav-item">
	            <a class="nav-link menu" href="gptPage">Components</a>
	        </li>
	        <li class="nav-item">
	            <a class="nav-link menu" href="javascript:void(0);">Calendar</a>
	        </li>
	        <li class="nav-item">
	            <a class="nav-link menu" href="javascript:void(0);">Charts</a>
	        </li>
	        
	        <c:choose>
	        	<c:when test="${loginID ne null}">
 				    <li class="nav-item">
			            <a class="nav-link" >로그아웃</a>
			        </li>
	        	</c:when>
	        	<c:otherwise>
			        <li class="nav-item">
			            <a class="nav-link" data-target="#loginModal" data-toggle="modal" >로그인</a>
			        </li>
	        	</c:otherwise>
	        </c:choose>
	    </ul>
	</div>
</nav>

<!-- Modal -->
<div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content" style="padding: 20px; transform: translateY(100%);">
    	<div class="cloaseBtnBox" style="text-align: right;">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
    	</div>
     	
     	<div id="loginBox">
     		<h5>로그인</h5>
		     	<div class="loginInput">
		     		<div>아이디</div>
			     	<input type="text" class="form-control" placeholder="아이디" maxlength=12>
		     	</div>
				<div class="loginInput">
					<div>비밀번호</div>
			     	<input type="password" class="form-control" placeholder="비밀번호" maxlength=15>
				</div>
				<button type="button" class="btn">로그인</button>
     	</div>
     	
     </div>
     <div class="modal-body">
     </div>
    </div>
 </div>

<script src="js/board.js"></script>	
<script>
function test(){
	  var tabsNewAnim = $('#navbarSupportedContent');
	  var selectorNewAnim = $('#navbarSupportedContent').find('li').length;
	  var activeItemNewAnim = tabsNewAnim.find('.active');
	  var activeWidthNewAnimHeight = activeItemNewAnim.innerHeight();
	  var activeWidthNewAnimWidth = activeItemNewAnim.innerWidth();
	  var itemPosNewAnimTop = activeItemNewAnim.position();
	  var itemPosNewAnimLeft = activeItemNewAnim.position();
	  $(".hori-selector").css({
	    "top":itemPosNewAnimTop.top + "px", 
	    "left":itemPosNewAnimLeft.left + "px",
	    "height": activeWidthNewAnimHeight + "px",
	    "width": activeWidthNewAnimWidth + "px"
	  });
	  $("#navbarSupportedContent").on("click","li.menu",function(e){
	    $('#navbarSupportedContent ul li.menu').removeClass("active");
	    $(this).addClass('active');
	    var activeWidthNewAnimHeight = $(this).innerHeight();
	    var activeWidthNewAnimWidth = $(this).innerWidth();
	    var itemPosNewAnimTop = $(this).position();
	    var itemPosNewAnimLeft = $(this).position();
	    $(".hori-selector").css({
	      "top":itemPosNewAnimTop.top + "px", 
	      "left":itemPosNewAnimLeft.left + "px",
	      "height": activeWidthNewAnimHeight + "px",
	      "width": activeWidthNewAnimWidth + "px"
	    });
	  });
	}
	
	$(document).ready(function(){
		var path = window.location.pathname.split("/").pop();
		  
		  // Account for home page with empty path
		  if ( path == '' ) {
		    path = 'index.jsp';
		  }
		
		  if(path.toUpperCase().includes("BOARD")){
			  // navbar 아닌 다른 페이지 이동 시 (상세보기, 글쓰기 등)에도 선택 유지
	  		path = 'boardPage';		
  		}
		  
	  var target = $('#navbarSupportedContent ul li a[href="'+path+'"]');
	   if (target.length > 0) {
	    target.parent().addClass('active');
  		}
	  setTimeout(function(){ test(); });
	});
	
	$(window).on('resize', function(){
	  setTimeout(function(){ test(); }, 500);
	});
	
	$(".navbar-toggler").click(function(){
	  $(".navbar-collapse").slideToggle(300);
	  setTimeout(function(){ test(); });
	});



$('#loginModal').on('shown.bs.modal', function () {
	  $('#myInput').trigger('focus')
	})
</script>
</body>
</html>