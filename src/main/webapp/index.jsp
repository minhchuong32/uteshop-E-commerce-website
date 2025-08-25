<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Uteshop | Trang chủ</title>
    <!-- Thêm Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    
</head>
<body>
    <%@ include file="/WEB-INF/views/layout/header.jsp" %>

    <!-- Banner -->
    <div class="bg-secondary text-white d-flex justify-content-center align-items-center" style="height: 250px;">
        <h1>Banner khuyến mãi / Slider</h1>
    </div>

    <!-- Danh mục -->
    <div class="container my-4">
        <h2 class="mb-3">Danh mục</h2>
        <div class="row g-3">
            <div class="col-6 col-md-2">
                <div class="p-3 bg-light text-center rounded">Điện thoại</div>
            </div>
            <div class="col-6 col-md-2">
                <div class="p-3 bg-light text-center rounded">Máy tính</div>
            </div>
            <div class="col-6 col-md-2">
                <div class="p-3 bg-light text-center rounded">Thời trang</div>
            </div>
            <div class="col-6 col-md-2">
                <div class="p-3 bg-light text-center rounded">Gia dụng</div>
            </div>
            <div class="col-6 col-md-2">
                <div class="p-3 bg-light text-center rounded">Mỹ phẩm</div>
            </div>
        </div>
    </div>

    <!-- Sản phẩm nổi bật -->
    <div class="container my-4">
        <h2 class="mb-3">Sản phẩm gợi ý</h2>
        <div class="row g-4">
            <div class="col-6 col-md-3">
                <div class="card h-100 text-center">
                    <img src="https://via.placeholder.com/200" class="card-img-top" alt="Sản phẩm 1">
                    <div class="card-body">
                        <h5 class="card-title">Điện thoại Samsung</h5>
                        <p class="card-text">5.000.000đ</p>
                    </div>
                </div>
            </div>
            <div class="col-6 col-md-3">
                <div class="card h-100 text-center">
                    <img src="https://via.placeholder.com/200" class="card-img-top" alt="Sản phẩm 2">
                    <div class="card-body">
                        <h5 class="card-title">Laptop Dell</h5>
                        <p class="card-text">15.000.000đ</p>
                    </div>
                </div>
            </div>
            <div class="col-6 col-md-3">
                <div class="card h-100 text-center">
                    <img src="https://via.placeholder.com/200" class="card-img-top" alt="Sản phẩm 3">
                    <div class="card-body">
                        <h5 class="card-title">Áo thun nam</h5>
                        <p class="card-text">200.000đ</p>
                    </div>
                </div>
            </div>
            <div class="col-6 col-md-3">
                <div class="card h-100 text-center">
                    <img src="https://via.placeholder.com/200" class="card-img-top" alt="Sản phẩm 4">
                    <div class="card-body">
                        <h5 class="card-title">Nồi cơm điện</h5>
                        <p class="card-text">1.000.000đ</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="/WEB-INF/views/layout/footer.jsp" %>

</body>
</html>
