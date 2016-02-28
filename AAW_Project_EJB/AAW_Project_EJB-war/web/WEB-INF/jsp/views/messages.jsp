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
        <title>Messages - Social Network Project</title>
    </head>

    <body>
        <%@ include file="../fragments/_header.jsp" %>

        <c:choose>
            <c:when test="${showFriends == true}">
                <div class="primary callout text-center size-36">
                    <p>Messages</p>
                </div>

                <div class="row small-up-1 medium-up-2 large-up-4">
                    <c:choose>
                        <c:when test="${friends.size() == 0}">
                            <div class="text-center">
                                You don't have any friends!
                            </div>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="friend" items="${friends}">
                                <div class="column media-object">
                                    <div class="media-object-section left">
                                        <div class="thumbnail ">
                                            <img src= "../resources/img/profile.png" class="profile_picture">
                                        </div>
                                    </div>
                                    <div class="media-object-section">
                                        <c:choose>
                                            <c:when test="${nbUnreadMessages.get(friend) > 0}">
                                                <a style="font-weight: bold" class="size-36" href="<%=request.getContextPath()%>/${friend.getId()}/messages.htm" class="margin-bottom-1 text-left">${friend.getName()}(${nbUnreadMessages.get(friend)})</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a class="size-36" href="<%=request.getContextPath()%>/${friend.getId()}/messages.htm" class="margin-bottom-1 text-left">${friend.getName()}</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:when>
            <c:otherwise>
                <div class="primary callout text-center size-36">
                    <p>Messages with ${friend.getName()}</p>
                </div>
                
                <div class="medium-9 medium-centered columns">
                    <div class="row">
                        <div class="column text-center medium-1">
                            <img src= "${pageContext.request.contextPath}/resources/img/profile.png" >
                        </div>

                        <form method="post" action="${pageContext.request.contextPath}/${userId}/createMessage.htm">
                            <div class="column medium-11">
                                <label>
                                    <input type="text" placeholder="Express yourself" name="messageContent">
                                </label>
                            </div>

                            <div class="column">
                                <button type="submit" class="button expanded">Send a private message</button>
                            </div>
                        </form>
                    </div>
                </div>
                <hr/>
                <div class="rows ">
                    <div class="row small-uncollapse large-collapse">
                        <c:choose>
                            <c:when test="${messages.size() == 0}">
                                <div class="text-center">
                                    No messages to show.
                                </div>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="message" items="${messages}">
                                    <article class="column media-object">
                                        <div class="media-object-section left">
                                            <div class="thumbnail">
                                                <img src= "${pageContext.request.contextPath}/resources/img/profile.png" class="profile_picture">
                                            </div>
                                        </div>
                                        <div class="media-object-section">
                                            <a class="size-21" href="<%=request.getContextPath()%>/${message.getSender().getId()}/profile.htm">${message.getSender().getName()}</a>
                                            <span class="size-12">(${message.getDate()})</span>
                                            <c:if test="${message.getSender() == currentUser}">
                                                <a class="size-12" style="color: red; font-weight: bold" href="<%=request.getContextPath()%>/${message.getId()}/removeMessage.htm">Remove</a>
                                            </c:if>
                                            <div>
                                                ${message.getContent()}
                                            </div>
                                        </div>
                                    </article>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
        

        <%@ include file="../fragments/_footer.jsp" %>

    </body>
</html>
