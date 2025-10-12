<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>UteShop</title>

<link rel="icon" type="image/png"
      href="${pageContext.request.contextPath}/assets/images/favicon.png">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
      rel="stylesheet">

<style>
body {
    background-color: #ffffff;
}
#preloader {
    position: fixed;
    top: 0; left: 0; width: 100%; height: 100%;
    background: white;
    display: flex; flex-direction: column;
    align-items: center; justify-content: center;
    z-index: 9999;
    transition: opacity 0.5s ease;
}
.fade-out {
    opacity: 0;
    visibility: hidden;
}
</style>
</head>

<body>
    <!-- 🔹 Loading -->
    <div id="preloader">
        <img src="${pageContext.request.contextPath}/assets/images/logo_strong.png"
             alt="UteShop" width="100" class="mb-3">
        <div class="spinner-border text-primary mb-3" style="width: 3rem; height: 3rem;" role="status">
            <span class="visually-hidden">Đang tải...</span>
        </div>
        <h5 class="fw-bold text-primary">Đang khởi động UteShop...</h5>
    </div>

    <script>
        // Hiển thị loading một chút rồi mới redirect
        window.addEventListener("load", () => {
            setTimeout(() => {
                document.getElementById("preloader").classList.add("fade-out");
                // sau khi fade-out xong mới chuyển trang
                setTimeout(() => {
                    window.location.href = "<%=request.getContextPath()%>/web/home";
                }, 400);
            }, 500); // chờ 0.5s cho logo hiển thị
        });
    </script>
</body>
</html>
