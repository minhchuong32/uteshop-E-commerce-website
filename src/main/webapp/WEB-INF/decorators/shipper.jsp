<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>
<!DOCTYPE html>
<html>
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
	<%@ include file="/commons/shipper/header.jsp"%>
	<sitemesh:write property="body" />
	<%@ include file="/commons/shipper/footer.jsp"%>
</body>
</html>
