<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- JSTL 태그라이브러리 추가(출력과 포맷 활용) -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<%@include file="../includes/header.jsp"%>

<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">Tables</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				Board List Page
				<button id='regBtn' type="button" class="btn btn-xs pull-right">
					<p style="color: #337ab7;">Register New Board</p>
				</button>
			</div>

			<!-- /.panel-heading -->
			<div class="panel-body">
				<table class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>#번호</th>
							<th>제목</th>
							<th>작성자</th>
							<th>작성일</th>
							<th>수정일</th>
						</tr>
					</thead>

					<c:forEach items="${list}" var="board">
						<tr>
							<td><c:out value="${board.bno}" /></td>

							<!--조회 페이지 이동시 현재 페이지 번호를 담을 수 있게 수정 -->							
							<%-- <td><a href='/board/get?bno=<c:out value="${board.bno}"/>'><c:out value="${board.title}"/></a></td> --%>

							<!--<a>태그 속성 target="_blank" 으로 전환시 조회페이지 새창 으로 이동-->
							<td><a class='move' href='<c:out value="${board.bno}"/>'>
									<c:out value="${board.title}" />
							</a></td>

							<td><c:out value="${board.writer}" /></td>
							<td><fmt:formatDate pattern="yyyy-MM-dd"
									value="${board.regdate}" /></td>
							<td><fmt:formatDate pattern="yyyy-MM-dd"
									value="${board.updateDate}" /></td>
						</tr>
					</c:forEach>
							
				</table>
				<!-- 검색 -->
				<!-- 삼항 연산자를 이용해 selected라는 문자열을 이용해 검색 후에도 검색조건과 키워드 남기기 -->
				<div class='row'>
					<div class="col-lg-12">
						<form id='searchForm' action="/board/list" method='get'>
							<select name='type'>
								<option value=""
									<c:out value="${pageMaker.cri.type == null?'selected':''}"/>>--</option>
								<option value="T"
									<c:out value="${pageMaker.cri.type eq 'T'?'selected':''}"/>>제목</option>
								<option value="C"
									<c:out value="${pageMaker.cri.type eq 'C'?'selected':''}"/>>내용</option>
								<option value="W"
									<c:out value="${pageMaker.cri.type eq 'W'?'selected':''}"/>>작성자</option>
								<option value="TC"
									<c:out value="${pageMaker.cri.type eq 'TC'?'selected':''}"/>>제목
									or 내용</option>
								<option value="TW"
									<c:out value="${pageMaker.cri.type eq 'TW'?'selected':''}"/>>제목
									or 작성자</option>
								<option value="TWC"
									<c:out value="${pageMaker.cri.type eq 'TWC'?'selected':''}"/>>제목
									or 내용 or 작성자</option>
							</select> <input type='text' name='keyword'
								value='<c:out value="${pageMaker.cri.keyword}"/>' /> <input
								type='hidden' name='pageNum'
								value='<c:out value="${pageMaker.cri.pageNum}"/>' /> <input
								type='hidden' name='amount'
								value='<c:out value="${pageMaker.cri.amount}"/>' />
							<button class='btn btn-default'>Search</button>
						</form>
					</div>
				</div>

				<!-- 페이징 -->
				<div class='pull-right'>
					<ul class="pagination">

						<!-- 이전 페이지 버튼 -->
						<c:if test="${pageMaker.prev}">
							<li class="paginate_button previous"><a
								href="${pageMaker.startPage -1}">Previous</a></li>
						</c:if>

						<!-- 시작 페이지, 끝 페이지 -->
						<c:forEach var="num" begin="${pageMaker.startPage}"
							end="${pageMaker.endPage}">
							<li class="paginate_button ${pageMaker.cri.pageNum == num ? "active":""} ">
								<a href="${num}">${num}</a>
							</li>
						</c:forEach>

						<!-- 다음 페이지버튼 -->
						<c:if test="${pageMaker.next}">
							<li class="paginate_button next"><a
								href="${pageMaker.endPage +1}">Next</a></li>
						</c:if>

					</ul>
				</div>
				<!--  end Pagination -->
			</div>
			
			<!-- 페이지이동 form (페이징의 a태그가 동작하지 못 하도록 js처리)-->
			<form id='actionForm' action="/board/list" method="get"> 
				<input type='hidden' name='pageNum' value='${pageMaker.cri.pageNum}'>
				<input type='hidden' name='amount' value='${pageMaker.cri.amount}'>
				
				<!-- 검색 기능 추가후  페이지 이동시 검색조건과 키워드를 유지시켜주기 위해 추가 -->			
				<input type='hidden' name='type' value='<c:out value="${ pageMaker.cri.type }"/>'> 
				<input type='hidden' name='keyword' value='<c:out value="${ pageMaker.cri.keyword }"/>'>
			</form>

			<!-- Modal창 추가 -->
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">Modal Title</h4>
						</div>
						<div class="modal-body">처리가 완료 되었습니다.</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
							<button type="button" class="btn btn-primary">Save
								changes</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<!-- /.modal -->



		</div>
		<!--  end panel-body -->
	</div>
	<!-- end panel -->
</div>
<!-- /.row -->

<!-- JQuery 처리 Modal -->
<!-- addFlashAttribute 로 만들어졌기 때문에 Session 에 데이터 보관,  
		한번도 사용된적 없다면 번호 호출, 그렇지않다면 ''빈값 반환 -->
<!-- checkModal() 함수는 파라미터에 따라 모달창을 보여주거나 내용을 수정한 뒤 보이도록 설계 -->
<script type="text/javascript">
	$(document).ready(function() { //modal 창 띄우기 js
		var result = '<c:out value="${result}"/>';

		checkModal(result);

		//modal 창이 /board/list로 이동할때만 동작하게 window.history 조작, || history.state 추가
		history.replaceState({}, null, null);
		
		function checkModal(result){
			if (result === '' || history.state ){  
				return;			 
			} 	
			if (parseInt(result) > 0 ){
				$(".modal-body").html("게시글 "+ parseInt(result) + " 번이 등록 되었습니다.");
			}
			
			$("#myModal").modal("show")
		}
	$("#regBtn").on("click", function(){ // 등록페이지 이동
		self.location ="/board/register";
	});
	
	// 페이징의 a태그가 동작하지 못 하도록 js처리
	var actionForm = $("#actionForm");

	$(".paginate_button a").on("click",function(e) { //a 태그를 클릭해도 이동이 없도록 preventDefault 처리
			e.preventDefault();

			console.log('click');

			actionForm.find("input[name='pageNum']").val($(this).attr("href")); // pageNum 값을 href 속성값으로 변경
			actionForm.submit(); // actionForm 자체를 submit
		});
	
	$(".move").on("click", function(e){ // list에서 조회(get)페이지 이동시 현재 페이징번호 값을 갖기위해 추가
										
		e.preventDefault();
	
		actionForm.append("<input type='hidden' name='bno' value='"+ $(this).attr("href")+ "'>");
		actionForm.attr("action","/board/get");
		actionForm.submit();

	});
	// 검색 버튼 제어 (form태그 전송 막음)

	var searchForm = $("#searchForm");

		$("#searchForm button").on(
				"click",
				function(e) {

					if (!searchForm.find("option:selected")
							.val()) {
						alert("검색종류를 선택하세요");
						return false;
					}

					if (!searchForm.find(
							"input[name='keyword']").val()) {
						alert("키워드를 입력하세요");
						return false;
					}

					searchForm.find("input[name='pageNum']") // 검색 후 페이징 번호가 1이 되게
							.val("1");
					e.preventDefault();

					searchForm.submit();

				});
		});
</script>


<%@include file="../includes/footer.jsp"%>


