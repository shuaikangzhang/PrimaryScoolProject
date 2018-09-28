<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../common/meta.jsp" flush="true"/>
<c:set var="CTP" value="${pageContext.request.contextPath}"></c:set>
<c:set var="CTP_HOME" value="${pageContext.request.contextPath}/resources/home"></c:set>
<title>Insert title here</title>
    <link rel="stylesheet" href="${CTP_HOME}/css/classdetails.css" />
    <link rel="stylesheet" href="${CTP_HOME}/css/liMarqueeImg.css" />
    <link rel="stylesheet" href="${CTP_HOME}/css/list.css" />
</head>
<body>
    <jsp:include page="../common/classHeader.jsp"></jsp:include>
    
    <div id="mianBag">
        <!--详细新闻-->
        <div class="container-bottom-lift">
        <c:set var="newsinfo" value="${newsinfo }"></c:set>
            <!--描述：新闻标题 -->
            <div class="news-title">
                <span> ${newsinfo.itemTitle }</span>
            </div>
            <!--描述：新闻作者，发布时间 -->
            <div class="new-attribute">
                <span class="new-attribute-span">作者：</span>
                <span class="new-attribute-name">${newsinfo.itemAuthor }</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <span class="new-attribute-span">发布时间：</span>
                <span class="new-attribute-time"> ${newsinfo.addTime }</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <span class="new-attribute-span">浏览次数：</span>
                <span class="new-attribute-times">${newsinfo.viewCount }</span>
            </div>
            <!--描述：新闻具体内容 -->
            <div class="new-details">
                <span>${newsinfo.itemContent }</span>
            </div>
        </div>
        <!--详细新闻end-->
    </div>
    <div id="footer">
        <jsp:include page="../common/classFooter.jsp"></jsp:include>
    </div>
</body>
</html>
