<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header style="background:#ee4d2d; color:white; padding:15px; display:flex; justify-content:space-between; align-items:center;">
    <div class="logo" style="font-size:22px; font-weight:bold;">
        <a href="/uteshop/" style="text-decoration:none; color:white;">UTESHOP</a>
    </div>
    <div class="search-bar">
        <form action="search" method="get">
            <input type="text" name="keyword" placeholder="Tìm sản phẩm, thương hiệu..." style="padding:5px; width:300px;">
            <button type="submit" style="padding:5px 10px;">Tìm</button>
        </form>
    </div>
    <div class="user-actions">
        <a href="/auth/login.jsp" style="color:white; margin-right:10px;">Đăng nhập</a>
        <a href="/auth/register.jsp" style="color:white; margin-right:10px;">Đăng ký</a>
        <a href="/order/cart.jsp" style="color:white;">Giỏ hàng (0)</a>
    </div>
</header>
