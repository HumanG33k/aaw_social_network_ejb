<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="medium-9 medium-centered columns">
    <div class="row">
        <div class="column text-center medium-1">
            <img src= "${pageContext.request.contextPath}/${currentUser.getId()}/showProfilePicture.htm">
        </div>

        <c:choose>
            <c:when test="${messages == true}">
                <form method="post" action="${pageContext.request.contextPath}/${userId}/createMessage.htm">
            </c:when>
            <c:otherwise>
                <form method="post" action="${pageContext.request.contextPath}/${userId}/createPost.htm">
            </c:otherwise>
        </c:choose>
            <div class="large-7 columns">
                <label>
                    <input type="text" placeholder="Express yourself" name="postContent">
                </label>
            </div>
            <div class="large-4 columns">
                <label>
                    <select name="fileToLink">
                        <option value="">Select a file</option>
                        <c:forEach var="file" items="${files}">
                            <option value="${file.getId()}">${file.getName()}</option>
                        </c:forEach>
                    </select>
                </label>
            </div>

            <div class="column">
                <button type="submit" class="button expanded">Send a public message</button>
            </div>
        </form>
    </div>
</div>
<hr/>
<div class="rows ">
    <div class="row small-uncollapse large-collapse">
        <c:choose>
            <c:when test="${posts.size() == 0}">
                <div class="text-center">
                    <c:choose>
                        <c:when test="${messages == true}">
                            No messages to show.
                        </c:when>
                        <c:otherwise>
                            No posts to show.
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="post" items="${posts}">
                    <article class="column media-object">
                        <div class="media-object-section left">
                            <div class="thumbnail">
                                <img src= "${pageContext.request.contextPath}/${post.getSender().getId()}/showProfilePicture.htm" class="profile_picture">
                            </div>
                        </div>
                        <div class="media-object-section">
                            <a class="size-21" href="<%=request.getContextPath()%>/${post.getSender().getId()}/profile.htm">${post.getSender().getName()}</a>
                            <c:if test="${post.getSender() != post.getTarget() && messages == false}">
                                => 
                                <a class="size-21" href="<%=request.getContextPath()%>/${post.getTarget().getId()}/profile.htm">${post.getTarget().getName()}</a>
                            </c:if>
                            <span class="size-12">(${post.getDate()})</span>
                            <c:if test="${post.getSender() == currentUser || post.getTarget() == currentUser}">
                                <a class="size-12" style="color: red; font-weight: bold" href="<%=request.getContextPath()%>/${post.getId()}/removePost.htm">Remove</a>
                            </c:if>
                            <div>
                                ${post.getContent()}<br/>
                                <c:if test="${post.getFile() != null}">
                                    <c:choose>
                                        <c:when test="${post.getFile().getType().startsWith('image')}">
                                            <img style="max-height: 500px; max-width: 500px" src="${pageContext.request.contextPath}/${post.getFile().getId()}/showFile.htm">
                                        </c:when>
                                        <c:when test="${post.getFile().getType().startsWith('video')}">
                                            <video controls style="max-height: 500px; max-width: 500px" src="${pageContext.request.contextPath}/${post.getFile().getId()}/showFile.htm">
                                        </c:when>
                                        <c:otherwise>
                                            <a class="size-21" href="<%=request.getContextPath()%>/${post.getFile().getId()}/showFile.htm">${post.getFile().getName()}</a>&nbsp;
                                            <a class="size-21 fi-download" style="color: black; font-weight: bold" href="<%=request.getContextPath()%>/${post.getFile().getId()}/downloadFile.htm"></a>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </div>
                        </div>
                    </article>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>