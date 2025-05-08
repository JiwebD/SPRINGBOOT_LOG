<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
	<title>Home</title>

	<style>
	.btn{
     display : flex;
     justify-content : center;
     item-align : center;
     width : 300px;
     gap : 20px;
    }

    .btn a{
     width : 120px;
     height : 50px;
     background-color : #dddddd;
     text-align : center;
     line-height : 50px;
     cursor : pointer;

    }
	</style>
</head>
<body>
<h1>
프로젝트 생성하기.
</h1>
<div class="btn">
<a href="/project/upload">프로젝트 생성</a>
</div>



</body>
</html>
