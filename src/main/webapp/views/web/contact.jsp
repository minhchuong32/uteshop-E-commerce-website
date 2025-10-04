<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<!-- Link CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/theme.css">

<div class="container mt-5 mb-5">

	<!-- Về chúng tôi -->
	<div class="row mt-5">
		<div class="col-12 text-center mb-5">
			<h3 class="fw-bold text-primary-custom">Về chúng tôi</h3>
			<p class="lead text-muted">
				Khám phá hành trình, sứ mệnh và giá trị cốt lõi của <strong>UteShop</strong>
				– cửa hàng thương mại điện tử công nghệ hàng đầu.
			</p>
		</div>
	</div>

	<!-- Giới thiệu -->
	<div class="row align-items-center mb-5">
		<div class="col-md-6 mb-3 mb-md-0">
			<img src="${pageContext.request.contextPath}/assets/images/about_intro.jpg"
			     class="img-fluid rounded shadow-sm rotate-on-scroll"
			     alt="Giới thiệu UteShop">
		</div>
		<div class="col-md-6">
			<h4 class="fw-bold text-primary-custom">Giới thiệu</h4>
			<p class="fs-5 border-start ps-3">
				<strong>UteShop</strong> là nền tảng thương mại điện tử chuyên về
				các sản phẩm công nghệ chính hãng. Từ laptop, smartphone, tablet,
				phụ kiện đến thiết bị thông minh, chúng tôi cam kết mang lại lựa chọn đa dạng,
				giá cả minh bạch và dịch vụ hậu mãi tận tâm.
			</p>
		</div>
	</div>

	<hr class="hr-primary">

	<!-- Sứ mệnh -->
	<div class="row align-items-center flex-md-row-reverse mb-5">
		<div class="col-md-6 mb-3 mb-md-0">
			<img src="${pageContext.request.contextPath}/assets/images/about_mission.jpg"
			     class="img-fluid rounded shadow-sm rotate-on-scroll"
			     alt="Sứ mệnh UteShop">
		</div>
		<div class="col-md-6">
			<h4 class="fw-bold text-primary-custom">Sứ mệnh</h4>
			<p class="fs-5 border-start ps-3">
				Mang đến trải nghiệm mua sắm <em>an toàn – tiện lợi – nhanh chóng</em>,
				từ khâu chọn sản phẩm, thanh toán, giao hàng đến chăm sóc khách hàng.
			</p>
		</div>
	</div>

	<hr class="hr-primary">

	<!-- Giá trị cốt lõi -->
	<div class="row align-items-center mb-5">
		<div class="col-md-6 mb-3 mb-md-0">
			<img src="${pageContext.request.contextPath}/assets/images/about_values.jpg"
			     class="img-fluid rounded shadow-sm rotate-on-scroll"
			     alt="Giá trị cốt lõi UteShop">
		</div>
		<div class="col-md-6">
			<h4 class="fw-bold text-primary-custom">Giá trị cốt lõi</h4>
			<ul class="fs-5 border-start ps-4 list-unstyled">
				<li>✔️ <strong>Uy tín:</strong> Sản phẩm chính hãng, bảo hành minh bạch.</li>
				<li>✔️ <strong>Đổi mới:</strong> Cập nhật công nghệ mới nhất.</li>
				<li>✔️ <strong>Khách hàng:</strong> Sự hài lòng là ưu tiên hàng đầu.</li>
			</ul>
		</div>
	</div>

	<hr class="hr-primary">

	<!-- Liên hệ -->
	<h2 class="fw-bold text-center my-5 text-primary-custom">Liên hệ với chúng tôi</h2>

	<!-- Thông báo -->
	<c:if test="${not empty success}">
		<div class="alert alert-success text-center">${success}</div>
	</c:if>
	<c:if test="${not empty error}">
		<div class="alert alert-danger text-center">${error}</div>
	</c:if>

	<div class="row g-4">
		<!-- Thông tin cửa hàng -->
		<div class="col-md-5">
			<div class="card shadow-sm rounded-3">
				<div class="card-body">
					<h5 class="fw-bold mb-3 text-primary-custom">Thông tin cửa hàng</h5>
					<p><strong>Tên:</strong> <c:out value="${store.storeName}" /></p>
					<p><strong>Hotline:</strong> <c:out value="${store.hotline}" /></p>
					<p><strong>Email:</strong> <c:out value="${store.email}" /></p>
					<p><strong>Địa chỉ:</strong> <c:out value="${store.address}" /></p>
				</div>
				<div class="text-center p-3 border-top">
					<img src="${pageContext.request.contextPath}/assets/images/hcmute.png"
					     alt="Cửa hàng UteShop"
					     class="img-fluid rounded shadow-sm store-image"
					     style="max-height: 300px; object-fit: cover;">
				</div>
			</div>
		</div>

		<!-- Form liên hệ -->
		<div class="col-md-7">
			<div class="card shadow-sm rounded-3">
				<div class="card-body">
					<h5 class="fw-bold mb-3 text-primary-custom">Gửi tin nhắn</h5>
					<form action="${pageContext.request.contextPath}/web/contact" method="post">
						<div class="mb-3">
							<label class="form-label">Họ và tên</label>
							<input type="text" class="form-control" name="fullname" required>
						</div>
						<div class="mb-3">
							<label class="form-label">Email</label>
							<input type="email" class="form-control" name="email" required>
						</div>
						<div class="mb-3">
							<label class="form-label">Nội dung</label>
							<textarea class="form-control" name="message" rows="4" required></textarea>
						</div>
						<div class="text-end">
							<button type="submit" class="btn btn-primary-custom px-4">Gửi liên hệ</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- Google Map -->
<div class="container mt-5">
	<div class="ratio ratio-16x9">
		<iframe src="${mapEmbedUrl}" width="100%" height="450" style="border: 0;" allowfullscreen="" loading="lazy"></iframe>
	</div>
</div>

<!-- Script hiệu ứng xoay ảnh -->
<script>
document.addEventListener("DOMContentLoaded", function() {
    const images = document.querySelectorAll(".rotate-on-scroll");
    function checkScroll() {
        images.forEach(img => {
            const rect = img.getBoundingClientRect();
            if (rect.top < window.innerHeight - 100) {
                img.classList.add("visible");
            }
        });
    }
    window.addEventListener("scroll", checkScroll);
    checkScroll();
});
</script>
