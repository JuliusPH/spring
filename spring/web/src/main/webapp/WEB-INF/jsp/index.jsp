<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>List of Persons</title>
		<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/style.css' />">
		<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/semantic.min.css' />">
        <script src="<c:url value='/resources/js/jquery.min.js' />"></script>
        <script src="<c:url value='/resources/js/semantic.min.js' />"></script>
        <script src="<c:url value='/resources/js/functions.js' />"></script>
        <script type="text/javascript">
            $(document).ready(function(){
                activateSortDropdowns();
                $(".person.modal")
                    .modal({
                        closable: false,
                        onDeny : function() {
                            $(".person.modal").empty();
                            $(".delete.modal").empty();
                            $(".update.modal").empty();
                            $(".contacts.modal").empty();
                            activateSortDropdowns();
                        }
                    })
                ;
                
                $(".delete.modal")
                    .modal({
                        closable: false,
                        onDeny : function() {
                            $(".person.modal").modal("show");
                            activateSortDropdowns();
                        }
                    })
                ;
                
                $(".update.modal")
                    .modal({
                        closable: false,
                        onDeny : function() {
                            $(".person.modal").modal("show");
                            activateSortDropdowns();
                        },
                        onApprove : function() {
                            activateSortDropdowns();
                            return false;
                        }
                    })
                ;
                
                $(".contacts.modal")
                    .modal({
                        closable: false,
                        onDeny : function() {
                            $(".person.modal").modal("show");
                            activateSortDropdowns();
                        },
                        onApprove : function() {
                            activateSortDropdowns();
                            return false;
                        }
                    })
                ;
                
                $(".add.modal")
                    .modal({
                        closable: false,
                        onHide : function(){
                            $(".add.modal").empty();
                            activateSortDropdowns();
                        }
                    })
                ;
                
                $(document).on("click", ".name-link", function(e){
                    var personId = $(this).data("id");
                    $.get("", {action : "loadPerson", id : personId}, function(data){
                        $(".person.modal").html($(data).html());
                        $(".person.modal").modal("show");
                    });
                    e.preventDefault();
                });
                
                $(document).on("click", ".person.modal .update.button", function(e){
                    var personId = $(this).data("id");
                    $.get("", {action : "setupUpdate", id : personId}, function(data){
                        $(".update.modal").html($(data).html());
                        $(".update.modal").modal("show");
                    });
                });
                
                $(document).on("click", ".person.modal .contacts.button", function(e){
                    var personId = $(this).data("id");
                    $.get("", {action : "setupUpdateContacts", id : personId}, function(data){
                        $(".contacts.modal").html($(data).html());
                        $(".contacts.modal").modal("show");
                    });
                });

                $(document).on("click", ".person.modal .delete.button", function(e){
                    var personId = $(this).data("id");
                    $.get("", {action : "setupDelete", id : personId}, function(data){
                        $(".delete.modal").html($(data).html());
                        $(".delete.modal").modal("show");
                    });
                });
                
                $(document).on("click", ".add.button", function(){
                    $.get("", {action : "setupAdd"}, function(data){
                        $(".add.modal").html($(data).html());
                        $(".add.modal").modal("show");
                    });
                });

                $(document).on("submit", ".add.form", function(e){
                    $.post("", $(".add.form").serialize(), function(data) {
                        $(".result").html($(data).find("data-message").html());
                        $(".wrapper .segment .content").html($(data).find("data-list").html());
                        $(".add.modal").modal("hide");
                        $(".add.modal").empty();
                        initSortDropdowns();
                    });
                    e.preventDefault();
                });

                $(document).on("submit", ".update.form", function(e){
                    $.post("", $(".update.form").serialize(), function(data){
                        $(".result").html($(data).find("data-message").html());
                        $(".wrapper .segment .content").html($(data).find("data-list").html());
                        $(".update.modal").modal("hide");
                        $(".update.modal").empty();
                        $(".person.modal").modal("hide");
                        $(".person.modal").empty();
                        initSortDropdowns();
                    });
                    e.preventDefault();
                });

                $(document).on("submit", ".updatecontacts.form", function(e){
                    $.post("", $(".updatecontacts.form").serialize(), function(data){
                        $(".result").html($(data).find("data-message").html());
                        $(".wrapper .segment .content").html($(data).find("data-list").html());
                        $(".contacts.modal").modal("hide");
                        $(".contacts.modal").empty();
                        $(".person.modal").modal("hide");
                        $(".person.modal").empty();
                        initSortDropdowns();
                    });
                    e.preventDefault();
                });

                $(document).on("submit", ".delete.form", function(e){
                    $.post("", $(".delete.form").serialize(), function(data){
                        $(".result").html($(data).find("data-message").html());
                        $(".wrapper .segment .content").html($(data).find("data-list").html());
                        $(".delete.modal").modal("hide");
                        $(".delete.modal").empty();
                        $(".person.modal").empty();
                        $(".person.modal").modal("hide");
                        initSortDropdowns();
                    });
                    e.preventDefault();
                });
                
                $(document).on("click", ".message .close", function(){
                    $(this).closest('.message').transition('fade');
                });
            });
        </script>
    </head>
    <body>
        <div class="wrapper">
            <h1 class="ui header">
                List of Persons
            </h1>
            <div class="ui hidden divider"></div>
            <div class="result">
                <jsp:include page="message.jsp" />
            </div>
            <div class="ui top attached menu">
                <a class="item add button">
                    <i class="add user icon"></i>
                    Add Person
                </a>
                <div class="right menu">
                    <div class="ui floating labeled icon dropdown item sort">
                        <i class="sort icon"></i>
                        <span class="text">ID</span>
                        <div class="menu">
                            <div class="header">
                                Sort by
                            </div>
                            <div class="divider"></div>
                            <div class="item active selected">
                                ID
                            </div>
                            <div class="item">
                                GWA
                            </div>
                            <div class="item">
                                Last Name
                            </div>
                            <div class="item">
                                Date Hired
                            </div>
                        </div>
                    </div>
                    <div class="ui floating labeled icon dropdown item order">
                        <i class="list icon"></i>
                        <span class="text">Ascending</span>
                        <div class="menu">
                            <div class="header">
                                Order by
                            </div>
                            <div class="divider"></div>
                            <div class="item active selected">
                                Ascending
                            </div>
                            <div class="item">
                                Descending
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="ui bottom attached segment">
                <div class="content">
                    <jsp:include page="listperson.jsp" />
                </div>
            </div>
        </div>
        <div class="ui small modal person">
            <jsp:include page="person.jsp" />
        </div>

        <div class="ui small modal delete">
            <jsp:include page="deleteperson.jsp" />
        </div>
        
        <div class="ui long modal update">
            <jsp:include page="updateperson.jsp" />
        </div>
        
        <div class="ui small modal contacts">
            <jsp:include page="contacts.jsp" />
        </div>
        <div class="ui long modal add">
            <jsp:include page="addperson.jsp" />
        </div>
    </body>
</html>