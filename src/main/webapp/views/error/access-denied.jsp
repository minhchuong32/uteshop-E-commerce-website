<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Truy cập bị từ chối</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
	rel="stylesheet">
</head>
<body
	class="d-flex flex-column justify-content-center align-items-center vh-100 bg-light">

	<form class="text-center shadow-sm p-5 bg-white rounded-4 border"
		method="post"
		action="${pageContext.request.contextPath}/access-denied">
		<h1 class="text-danger fw-bold mb-3">
			<i class="bi bi-shield-lock-fill"></i> Truy cập bị từ chối
		</h1>
		<p class="text-muted mb-4">Bạn không có quyền truy cập vào khu vực
			này.</p>

		<button type="submit" class="btn btn-primary rounded-pill px-4">
			<i class="bi bi-house-door-fill me-2"></i> Về trang chủ
		</button>
	</form>

</body>
</html>