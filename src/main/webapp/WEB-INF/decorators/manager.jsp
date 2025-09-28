<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglib.jsp"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <title>UteShop | User</title>

    <!-- Favicon -->
    <link rel="icon" type="image/png"
          href="${pageContext.request.contextPath}/assets/images/favicon.png">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">

    <!-- CSS riêng -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/index.css">

    <!-- Chèn head từ content -->
    <!--SITEMESH:HEAD-->
</head>
<body>

    <!-- Header -->
    <%@ include file="/commons/user/header.jsp"%>

    <!-- Nội dung động -->
    <div class="container-fluid px-0">
        <!--SITEMESH:BODY-->
        <sitemesh:write property="body" />
    </div>

    <!-- Footer -->
    <%@ include file="/commons/user/footer.jsp"%>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <!-- JS riêng -->
    <script src="${pageContext.request.contextPath}/assets/js/index.js"></script>
</body>
</html>