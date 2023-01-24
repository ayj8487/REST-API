<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- JSTL 태그라이브러리 추가(출력과 포맷 활용) -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<%@include file="../includes/header.jsp"%>

<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">Board Modify</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
		
			<div class="panel-heading">Board Modify</div>
			<!-- /.panel-heading -->
			<div class="panel-body">

		<form role="form" action="/board/modify" method="post">
				
				<!-- 수정/삭제 후 현재 페이징 목록을 가져오게 추가 -->
				<input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum}"/>'>
				<input type='hidden' name='amount' value='<c:out value="${cri.amount}"/>'>

				<!-- 검색 추가 후 키워드, 타입을 같이 넘김 -->	
				<input type="hidden" name='keyword' value='<c:out value="${cri.keyword }"/>'>
				<input type="hidden"  name='type' value='<c:out value="${cri.type }"/>'>


				<div class="form-group">
					<label>Bno</label> 
					<input class="form-control" name="bno" value='<c:out value="${board.bno}"/>' readonly="readonly">
				</div>

				<div class="form-group">
					<label>Title</label> 
					<input class="form-control" name="title" value='<c:out value="${board.title}"/>'>
				</div>
				
				<div class="form-group">
					<label>Text area</label> 
					<textarea class="form-control" rows="3" name="content" ><c:out value="${board.content}"/></textarea>
				</div>
				
				<div class="form-group">
					<label>Writer</label> 
					<input class="form-control" name="writer" value='<c:out value="${board.writer}"/>' readonly="readonly">
				</div>
				
				<div class="form-group">
					<label>RegDate</label>
					<input class="form-control" name='regDate' value='<fmt:formatDate pattern="yyyy/MM/dd" value="${board.regdate}"/>' readonly="readonly">
				</div>				

				<div class="form-group">
					<label>Update Date</label>
					<input class="form-control" name='updateDate' value='<fmt:formatDate pattern="yyyy/MM/dd" value="${board.updateDate}"/>' readonly="readonly">
				</div>				

				<button type="submit" data-oper='modify' class="btn btn-default" >Modify</button>
				<button type="submit" data-oper='remove' class="btn btn-default" >Remove</button>
				<button type="submit" data-oper='list'  class="btn btn-info" >List</button>
			
			</form>				
			</div>
			<!--  end panel-body -->
		</div>
		<!-- end panel -->
	</div>
</div>
<!-- /.row -->

<script type="text/javascript">  // 수정, 삭제 처리 js
$(document).ready(function(){  
	var formObj = $("form");
	
	$('button').on("click", function(e){
		e.preventDefault(); //submit 속성이 여러개 이기 때문에 기본 동작을 막고 직접 submit
		
		var operation = $(this).data("oper");
		
		console.log(operation);
		
		if(operation === 'remove'){
			formObj.attr("action", "/board/remove");
		}else if (operation === 'list'){
			//move to list
			// 수정된 내용은 클릭한 버튼이 List인경우 action 속성과 method속성을 변경하여 form태그의 모든 내용 삭제후 submit 진행,
			// 이후 코드는 실행 되지 않게 return으로 제어
			formObj.attr("action", "/board/list").attr("method","get");
			
			// 수정 페이지에서 리스트 이동시 현재 페이징번호를 갖게끔 처리
			// 사용자가 리스트 이동시 form 태그의 필요한 부분만 잠시 복사 보관(clone)후 form 태그의 내용을 지움(empty). 이후에 필요한 태그들만 추가해서 list를 호출
			var pageNumTag = $("input[name='pageNum']").clone();
			var amountTag = $("input[name='amount']").clone();			
			
			// 수정 페이지에서 리스트 이동시 현재 검색어와 타입을 가져가게끔 처리
			var keywordTag = $("input[name='keyword']").clone();			
			var typeTag = $("input[name='type']").clone();			

			formObj.empty();
			// 수정 페이지에서 리스트 이동시 현재 페이징번호를 갖게끔 처리
			formObj.append(pageNumTag);
			formObj.append(amountTag);
			
			// 수정 페이지에서 리스트 이동시 검색어와 타입을 갖게 처리
			formObj.append(keywordTag);
			formObj.append(typeTag);
			
		}
		formObj.submit();
	});
});

</script>

<%@include file="../includes/footer.jsp"%>

