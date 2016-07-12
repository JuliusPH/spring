<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${updateContacts}">
    <data-contacts>
        <script>
            prepareForm();
            validateForm();
        </script>
        <div class="header">
            Update Contacts
        </div>
        <form class="ui updatecontacts form" action="${pageContext.request.contextPath}/" method="post">
            <div class="content">
                <div class="ui top attached segment">
                    <input id="action" name="action" type="hidden" value="processUpdateContacts"/>
                    <input id="id" name="id" type="hidden" value="${person.getId()}"/>
                    <h4 class="ui dividing header first">Contacts</h4>
                    <c:if test="${person.getEmail() != null}">
                        <div class="field">
                            <label>E-mail</label>
                            <div class="two fields">
                                <div class="thirteen wide field">
                                    <input type="hidden" name="emailid" value="${person.getEmail().getId()}">
                                    <input type="text" name="email" id="email" placeholder="E-mail" value="${person.getEmail().getValue()}">
                                </div>
                                <div class="three wide field">
                                    <div class="ui toggle checkbox" id="deleteemail">
                                        <input type="checkbox" name="deletecontact" value="email">
                                        <label>Delete</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${person.getMobile() != null}">
                        <div class="field">
                            <label>Mobile number</label>
                            <div class="two fields">
                                <div class="thirteen wide field">
                                    <input type="hidden" name="mobilenumberid" value="${person.getMobile().getId()}">
                                    <input type="text" name="mobilenumber" id="mobile" placeholder="Mobile Number" value="${person.getMobile().getValue()}">
                                </div>
                                <div class="three wide field">
                                    <div class="ui toggle checkbox" id="deletemobile">
                                        <input type="checkbox" name="deletecontact" value="mobile">
                                        <label>Delete</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${person.getLandline() != null}">
                        <div class="field">
                            <label>Landline number</label>
                            <div class="two fields">
                                <div class="thirteen wide field">
                                    <input type="hidden" name="landlinenumberid" value="${person.getLandline().getId()}">
                                    <input type="text" name="landlinenumber" id="landline" placeholder="Landline Number" value="${person.getLandline().getValue()}">
                                </div>
                                <div class="three wide field">
                                    <div class="ui toggle checkbox" id="deletelandline">
                                        <input type="checkbox" name="deletecontact" value="landline">
                                        <label>Delete</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </div>

                <div class="ui bottom attached two item menu actions">
                    <a class="item cancel button">Back</a>
                    <button type="submit" class="item">Update</button>
                </div>
            </div>
        </form>
    </data-contacts>
</c:if>