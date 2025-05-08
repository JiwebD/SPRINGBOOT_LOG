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
  <form action="/project/update" method="post" enctype="multipart/form-data">
      <input type="hidden" name="projectId" value="${dto.projectId}">
      <input type="hidden" name="existingImagePath" value="${dto.imagePath}">

      <p>기존 썸네일: <img src="/${dto.imagePath}" width="100"></p>

      <label>새 이미지:</label>
      <input type="file" name="thumbnail" accept="image/*"> <br/>

      <label for="project-title">제목</label>
      <input type="text" name="projectTitle"  value="${dto.projectTitle}"> <br/>

      <label for="projectInfo">설명</label>
      <textarea name="projectInfo" cols="30" rows="8">${dto.projectInfo}</textarea> <br/>



      <button type="submit">수정완료</button>
  </form>
</div>

</body>
</html>