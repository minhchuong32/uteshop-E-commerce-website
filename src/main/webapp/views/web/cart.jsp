<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="text-center my-5">
	<!-- Ảnh giỏ hàng trống -->
	<img src="${pageContext.request.contextPath}/assets/images/empty_cart.png" 
	     alt="Giỏ hàng trống" 
	     class="img-fluid mb-3" 
	     style="max-width: 300px;">

	<!-- Thông báo -->
	<h4 class="fw-bold">Giỏ hàng trống</h4>
	<p class="text-muted">Hãy đăng nhập để tiếp tục mua sắm tại <strong>UteShop</strong></p>

	<!-- Nút điều hướng -->
	<a href="${pageContext.request.contextPath}/login"
	   class="btn btn-primary-custom">Đăng nhập để mua sắm</a>
</div>
