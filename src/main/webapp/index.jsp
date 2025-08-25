<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>UTESHOP - Trang chủ</title>
    <style>
        body { font-family: Arial, sans-serif; margin:0; padding:0; }
        .banner { background:#ccc; height:250px; display:flex; justify-content:center; align-items:center; font-size:30px; }
        .categories { display:flex; justify-content:space-around; margin:20px 0; }
        .categories div { background:#f5f5f5; padding:20px; border-radius:5px; width:18%; text-align:center; }
        .products { display:grid; grid-template-columns:repeat(4, 1fr); gap:20px; margin:20px; }
        .product { border:1px solid #ddd; border-radius:5px; padding:10px; text-align:center; }
        .product img { width:100%; height:150px; object-fit:cover; }
    </style>
</head>
<body>
    <%@ include file="/WEB-INF/views/layout/header.jsp" %>

    <!-- Banner -->
    <div class="banner">
        Banner khuyến mãi / Slider
    </div>

    <!-- Danh mục -->
    <h2 style="margin-left:20px;">Danh mục</h2>
    <div class="categories">
        <div>Điện thoại</div>
        <div>Máy tính</div>
        <div>Thời trang</div>
        <div>Gia dụng</div>
        <div>Mỹ phẩm</div>
    </div>

    <!-- Sản phẩm nổi bật -->
    <h2 style="margin-left:20px;">Sản phẩm gợi ý</h2>
    <div class="products">
        <div class="product">
            <img src="https://via.placeholder.com/200" alt="Sản phẩm 1">
            <h3>Điện thoại Samsung</h3>
            <p>5.000.000đ</p>
        </div>
        <div class="product">
            <img src="https://via.placeholder.com/200" alt="Sản phẩm 2">
            <h3>Laptop Dell</h3>
            <p>15.000.000đ</p>
        </div>
        <div class="product">
            <img src="https://via.placeholder.com/200" alt="Sản phẩm 3">
            <h3>Áo thun nam</h3>
            <p>200.000đ</p>
        </div>
        <div class="product">
            <img src="https://via.placeholder.com/200" alt="Sản phẩm 4">
            <h3>Nồi cơm điện</h3>
            <p>1.000.000đ</p>
        </div>
    </div>

    <%@ include file="/WEB-INF/views/layout/footer.jsp" %>
</body>
</html>
