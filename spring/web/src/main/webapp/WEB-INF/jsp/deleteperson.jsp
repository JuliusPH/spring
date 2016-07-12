<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${deletePerson}">
    <data-delete>
        <div class="header">
            Delete Person
        </div>
        <form class="ui delete form" action="${pageContext.request.contextPath}/" method="post">
            <div class="content">
                <div class="ui top attached segment">
                    <input id="id" name="id" type="hidden" value="${person.getId()}"/>
                    <input id="action" name="action" type="hidden" value="processDelete"/>
                    <div class="description">
                        <p>
                            Do you really want to delete ${person.getFullName()}?
                        </p>
                    </div>
                </div>
                <div class="ui bottom attached two item menu actions">
                    <a class="item cancel button">Back</a>
                    <button type="submit" class="item">Delete</button>
                </div>
            </div>
        </form>
    </data-delete>
</c:if>