<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title12132</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/jquery/jquery-3.2.1.js" > </script>
    <script>
        function  click1() {
            alert("asdfasdfadf${pageContext.request.contextPath}/login.do");
        }
    </script>
</head>

<body>
<h2>
    <a href="${pageContext.request.contextPath}/login.do">Hello World12313login!</a>
    <%--<a href="javascript:click1();">Hello World12313login!</a>--%>
</h2>

</body>
</html>
