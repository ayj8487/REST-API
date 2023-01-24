<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- JSTL 태그라이브러리 추가(출력과 포맷 활용) -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<%@include file="../includes/header.jsp"%>

<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">Board Register</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
		
			<div class="panel-heading">Board Register</div>
			<!-- /.panel-heading -->
			<div class="panel-body">

		<!-- input 이나 textarea 태그의 name 속성은 BoardVO 클래스의 변수와 일치시켜 줌 -->
				<form role="form" action="/board/register" method="post">
				<div class="form-group">
					<label>Title</label> <input class="form-control" name="title">
				</div>
				
				<div class="form-group">
					<label>Text area</label> <textarea class="form-control" rows="3" name="content"></textarea>
				</div>
				
				<div class="form-group">
					<label>Writer</label> <input class="form-control" name="writer">
				</div>
				<button type="submit" class="btn btn-default">Submit Button	</button>
				<button type="reset" class="btn btn-default">Reset Button</button>
				
				</form>
			</div>
			<!--  end panel-body -->
		</div>
		<!-- end panel -->
	</div>
</div>
<!-- /.row -->


<%@include file="../includes/footer.jsp"%>


