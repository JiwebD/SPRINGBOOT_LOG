<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.content{
margin : 140px auto;
padding : 100px;


}

</style>

</head>
<body>


<div class="content">
  <form action="/project/upload" method="post" enctype="multipart/form-data">
      <label>썸네일 업로드:</label>
      <input type="file" name="thumbnail" accept="image/*" required> <br/>

      <label for="project-title">제목</label>
      <input type="text" name="projectTitle" required> <br/>

      <label for="projectInfo">설명</label>
      <textarea name="projectInfo" cols="30" rows="8" required></textarea> <br/>

      <button type="submit">업로드</button>
  </form>
  <c:if test="${not empty error}">
      <div style="color:red;">${error}</div>
  </c:if>
</div>

</body>
</html>