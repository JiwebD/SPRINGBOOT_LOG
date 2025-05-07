<%@ page  language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

    <style>

.mypage_section>.content{
  width: 100%;
  margin-left: 33px;
}


.funding-card {
  position: relative;
  display: flex;
  border: 1px solid #bcbcbc;
  padding: 20px;
  border-radius: 12px;
  background-color: transparent;
  width: 100%;
  margin-bottom: 30px;
}

.funding-thumbnail img {
  width: 150px;
  height: 150px;
  border-radius: 6px;
}

.funding-info {
  display: flex;
  height: 150px;
  flex-direction: column;
  justify-content: space-between;
  flex-grow: 1;
  margin-left: 20px;
}

.funding-info p{
  font-weight: 500;
}

.funding-info .funding-id {
 font-size: 0.75rem;
 color: #999999;
}
.funding-info .funding-title {
 font-size: 1rem;
 color: #3d3d3d;

}

.funding-info .funding-goal{
  font-size: 1.125rem;
  color: #1a1a1a;
  padding-bottom: 14px;
  border-bottom: 1px solid #bcbcbc;
}

.funding-info .funding-supporter{
  font-size: 0.75rem;
  color: #999999;
}

.status{
  display: flex;
  gap: 10px;
}

.status-btn {
  width: 60px;
  padding: 3px 0;
  border: none;
  color: #fff;
  font-size: 0.688rem;
  margin-top: 5px;
}

.status-btn.selling {
  background-color: #0C7DFF;
}
.status-btn.waiting {
  background-color: #29C3FF;
}
.status-btn.end {
  background-color: #3D3D3D;
}
.status-btn.rejected {
  background-color: #7D7D7D;
}

.edit-dropdown {
  position: absolute;
  left: 100%;
  transform: translateX(-120%);
}

.edit-button {
  position: relative;
  width: 100px;
  height: 35px;
  border-radius: 10px;
  background: transparent;
  border: 1px solid #bcbcbc;
  cursor: pointer;
}

.dropdown-menu {
  position: absolute;
  top: 100%;
  right: 0;
  display: none;
  background: white;
  border: 1px solid #ddd;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
  margin-top: 4px;
  list-style: none;
  padding: 0;
  z-index: 999;
}

.dropdown-menu li {
  width: 75px;
  padding: 5px 10px;
  cursor: pointer;
  font-size:  0.688rem;
}

.dropdown-menu li:hover {
  background-color: #f3f3f3;
}



    </style>
</head>
<body>

    <div class="content">
    <c:forEach var="dto" items="${list}">
      <div class="funding-item">
        <div class="funding-card">
          <div class="funding-thumbnail">
            <img src="/${dto.imagePath}" alt="썸네일">
          </div>
          <div class="funding-info">
            <p class="funding-id">#${dto.formattedId}</p>
            <p class="funding-title">${dto.projectTitle}</p>
            <p class="funding-title">${dto.shortInfo}</p>
          </div>

        </div>
      </div>

    </c:forEach>


    </div>

</body>
</html>