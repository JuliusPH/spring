<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${loadPerson}">
    <data-info>
        <div class="header">
            Person Information
        </div>
        <div class="content">
            <div class="ui top attached segment">
                <div class="ui divided list">
                    <div class="item">
                        <span class="label">Full Name</span>:
                        <span class="value">${person.getFullName()}</span>
                    </div>
                    <div class="item">
                        <span class="label">Address</span>:
                        <span class="value">${person.getFullAddress()}</span>
                    </div>
                    <div class="item">
                        <span class="label">Birthday</span>:
                        <span class="value">${person.getFormattedBirthday()}</span>
                    </div>
                    <div class="item">
                        <span class="label">GWA</span>:
                        <span class="value">${person.getGwa()}</span>
                    </div>
                </div>
            </div>
            <div class="ui bottom attached four item menu actions">
                <a class="item cancel button">Close</a>
                <a class="item update button" data-id="${person.getId()}">Update</a>
                <a class="item contacts button" data-id="${person.getId()}">Update Contacts</a>
                <a class="item delete button" data-id="${person.getId()}">Delete</a>
            </div>
        </div>
    </data-info>
</c:if>