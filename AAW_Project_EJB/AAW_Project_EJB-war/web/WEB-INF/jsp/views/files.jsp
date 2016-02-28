<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/foundation.css">
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/app.css">
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/icon/foundation-icons.css">
        <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/foundation.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Files - Social Network Project</title>
    </head>

    <body>
        <%@ include file="../fragments/_header.jsp" %>

        <div class="primary callout text-center size-36">
            <p>Files</p>
        </div>
        
        <h5 class="text-center" name="uploadMessage">${uploadMessage}</h5>

        <div class="medium-9 medium-centered columns">
            <div class="row">
                <form method="post" action="${pageContext.request.contextPath}/${userId}/uploadFile.htm" enctype="multipart/form-data">
                    <div class="column">
                        <input type="file" class="text-center" name="fileToUpload">
                        <button type="submit" class="button expanded">Upload file</button>
                    </div>
                </form>
            </div>
        </div>

        <!-- 
        .fi-archive 
        .fi-book
        .fi-download
        .fi-folder
        .fi-photo
        .fi-play-video
        .fi-page
        -->
        <hr/>
        
        <div class="row small-up-1 medium-up-2 large-up-4">
            <c:choose>
                <c:when test="${files.size() == 0}">
                    <div class="text-center">
                        You haven't uploaded any files.
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach var="file" items="${files}">
                        <div class="column media-object">
                            <div class="media-object-section left">
                                <div>
                                    <c:choose>
                                        <c:when test="${file.getType().startsWith('image')}">
                                            <i class="fi-photo size-60"></i>
                                        </c:when>
                                        <c:otherwise>
                                            <i class="fi-page size-60"></i>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="media-object-section">
                                <a class="size-36" href="<%=request.getContextPath()%>/${file.getId()}/downloadFile.htm" class="margin-bottom-1 text-left">${file.getName()}</a>
                                <a class="size-24" style="color: red; font-weight: bold" href="<%=request.getContextPath()%>/${file.getId()}/removeFile.htm">X</a>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
        
        <%@ include file="../fragments/_footer.jsp" %>
    </body>
</html>
