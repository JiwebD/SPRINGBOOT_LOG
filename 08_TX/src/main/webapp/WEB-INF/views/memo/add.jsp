<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>MEMO ADD(/memo/add)</h1>
	
	<form action="/memo/add" method="post">
		<div>
			<label>id : </label> <span>${id}</span><br>
			<input name="id" />
		</div>
		<div>
			<label>text : </label>  <span>${text}</span><br>
			<textarea name="text" /></textarea>
		</div>
		<div>
			<label>writer : </label>  <span>${writer}</span><br>
			<input name="writer" />
		</div>
		
		<div>
			<label>email : </label>  <span>${email}</span><br>
			<input name="email" />
		</div>
		
		<div>
			<label>createAt : </label>  <span>${createAt}</span><br>
			<input type="datetime-local" name="createAt" />
		</div>
		
		
		<div>
			<label>dateTest(customFormat) : </label>  <span></span><br>
			<!-- name="dateTest" placeHolder="yyyy#MM#dd" : 날짜형태placeHolder="yyyy#MM#dd로 받는 문자열name="dateTest" -->
			<input type="text" name="dateTest" placeHolder="yyyy#MM#dd" />
		</div>
			
		<div>
			<input type="submit" value="메모쓰기" />
		</div>
	</form>
</body>
</html>