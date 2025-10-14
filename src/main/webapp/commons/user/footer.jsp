
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<footer class="pt-5 pb-3" style="background: #111827; color: #fff;">
	<div class="container">
		<div class="row text-start">

			<!-- Company Info -->
			<div class="col-md-3 mb-4">
				<!-- Logo dẫn về trang chủ -->
				<a href="${pageContext.request.contextPath}/user/home"
					class="d-inline-flex align-items-center mb-2 text-decoration-none">
					<img
					src="${pageContext.request.contextPath}/assets/images/logo_footer_strong.png"
					alt="UTESHOP Logo"
					style="height: 60px; width: auto; margin-right: 8px; margin-left: -12px;">

				</a>

				<!-- Mô tả ngắn -->
				<p class="small text-light mb-3">Nền tảng mua sắm trực tuyến uy
					tín, mang đến sản phẩm chất lượng với giá tốt nhất.</p>

				<!-- Mạng xã hội -->
				<div class="d-flex gap-3">
					<a href="https://www.facebook.com/dhspkt.hcmute/?locale=vi_VN"
						target="_blank" class="text-light fs-5 social-link"> <i
						class="bi bi-facebook"></i>
					</a> <a href="https://www.tiktok.com/@dhspkt.hcmute" target="_blank"
						class="text-light fs-5 social-link"> <i class="bi bi-tiktok"></i>
					</a> <a href="https://www.instagram.com/dhspkt.hcmute/" target="_blank"
						class="text-light fs-5 social-link"> <i
						class="bi bi-instagram"></i>
					</a>
				</div>
			</div>

			<!-- Thêm hiệu ứng hover -->
			<style>
.social-link {
	transition: color 0.3s ease, transform 0.3s ease;
}

.social-link:hover {
	color: #004474 !important; /* Vàng nhẹ khi hover */
	transform: scale(1.2);
}
</style>


			<!-- Quick Links -->
			<div class="col-md-3 mb-4">
				<h6 class="fw-semibold text-white">Liên kết nhanh</h6>
				<ul class="list-unstyled">
					<li><a href="#product" class="text-light text-decoration-none">Tất
							cả sản phẩm</a></li>
					<li><a
						href="${pageContext.request.contextPath}/user/products?categoryId=1"
						class="text-light text-decoration-none">Laptop</a></li>
					<li><a
						href="${pageContext.request.contextPath}/user/products?categoryId=2"
						class="text-light text-decoration-none">Điện Thoại</a></li>
					<li><a
						href="${pageContext.request.contextPath}/user/products?categoryId=3"
						class="text-light text-decoration-none">Tablet</a></li>
				</ul>
			</div>

			<!-- Customer Service -->
			<div class="col-md-3 mb-4">
				<h6 class="fw-semibold text-white">Hỗ trợ khách hàng</h6>
				<ul class="list-unstyled">
					<li><a href="${pageContext.request.contextPath}/user/contact"
						class="text-light text-decoration-none">Liên hệ chúng tôi</a></li>
					<li><a
						href="${pageContext.request.contextPath}/user/complaints"
						class="text-light text-decoration-none">Đổi trả và Hoàn tiền</a></li>
					<li><a href="${pageContext.request.contextPath}/user/cart"
						class="text-light text-decoration-none">Danh sách đơn hàng</a></li>
					<li><a href="${pageContext.request.contextPath}/user/orders"
						class="text-light text-decoration-none">Theo dõi đơn hàng</a></li>
				</ul>
			</div>

			<!-- Contact Info -->
			<div class="col-md-3 mb-4">
				<h6 class="fw-semibold text-white">Liên hệ</h6>
				<ul class="list-unstyled small text-light">
					<li><i class="bi bi-envelope me-2"></i> support@hcmute.edu.vn</li>
					<li><i class="bi bi-telephone me-2"></i> +84 028 3896 8641</li>
					<li><i class="bi bi-geo-alt me-2"></i> 01 Đ. Võ Văn Ngân, Linh
						Chiểu, <br> Thủ Đức, Hồ Chí Minh</li>
				</ul>
			</div>

		</div>

		<!-- Bottom -->
		<div class="text-center border-top pt-3 mt-3"
			style="border-color: rgba(255, 255, 255, 0.2) !important;">
			<p class="mb-0 small text-light">© 2025 Uteshop. All rights
				reserved.</p>
		</div>
	</div>
</footer>
