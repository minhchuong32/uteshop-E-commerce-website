<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>


<style>
/* Custom Styles */
.hero-section {
	background: linear-gradient(135deg, #517FA0 0%, #004474 100%);
	padding: 80px 0;
	color: white;
	margin-bottom: 60px;
	position: relative;
	overflow: hidden;
}

.hero-section::before {
	content: '';
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background: url('data:image/svg+xml,<svg width="100" height="100" xmlns="http://www.w3.org/2000/svg"><defs><pattern id="grid" width="100" height="100" patternUnits="userSpaceOnUse"><path d="M 100 0 L 0 0 0 100" fill="none" stroke="white" stroke-width="0.5" opacity="0.1"/></pattern></defs><rect width="100%" height="100%" fill="url(%23grid)"/></svg>');
	opacity: 0.3;
}

.hero-section .container {
	position: relative;
	z-index: 1;
}

.section-card {
	background: white;
	border-radius: 20px;
	padding: 40px;
	box-shadow: 0 10px 40px rgba(0,0,0,0.08);
	margin-bottom: 40px;
	transition: transform 0.3s ease, box-shadow 0.3s ease;
	border: 1px solid rgba(0,0,0,0.05);
}

.section-card:hover {
	transform: translateY(-5px);
	box-shadow: 0 15px 50px rgba(0,0,0,0.12);
}

.image-wrapper {
	position: relative;
	border-radius: 15px;
	overflow: hidden;
	box-shadow: 0 8px 30px rgba(0,0,0,0.12);
}

.image-wrapper img {
	width: 100%;
	height: 100%;
	object-fit: cover;
	transition: transform 0.5s ease;
}

.image-wrapper:hover img {
	transform: scale(1.08);
}

.image-overlay {
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background: linear-gradient(to top, rgba(0, 85, 141, 0.7), transparent);
	opacity: 0;
	transition: opacity 0.3s ease;
}

.image-wrapper:hover .image-overlay {
	opacity: 1;
}

.value-badge {
	display: inline-flex;
	align-items: center;
	background: linear-gradient(135deg, #00558D 0%, #004474 100%);
	color: white;
	padding: 15px 25px;
	border-radius: 50px;
	margin: 10px 10px 10px 0;
	font-weight: 600;
	box-shadow: 0 4px 15px rgba(0, 85, 141, 0.3);
	transition: transform 0.2s ease;
}

.value-badge:hover {
	transform: translateY(-3px);
	box-shadow: 0 6px 20px rgba(0, 85, 141, 0.4);
}

.value-badge i {
	margin-right: 10px;
	font-size: 1.2em;
}

.contact-section {
	background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
	padding: 60px 0;
	margin-top: 60px;
}

.contact-card {
	background: white;
	border-radius: 20px;
	padding: 35px;
	box-shadow: 0 10px 40px rgba(0,0,0,0.1);
	height: 100%;
	border: none;
}

.contact-info-item {
	display: flex;
	align-items: start;
	margin-bottom: 20px;
	padding: 15px;
	background: #f8f9fa;
	border-radius: 10px;
	transition: background 0.3s ease;
}

.contact-info-item:hover {
	background: #e9ecef;
}

.contact-info-item i {
	color: #00558D;
	font-size: 1.5em;
	margin-right: 15px;
	margin-top: 3px;
}

.form-control, .form-select {
	border-radius: 10px;
	border: 2px solid #e9ecef;
	padding: 12px 18px;
	transition: border-color 0.3s ease, box-shadow 0.3s ease;
}

.form-control:focus, .form-select:focus {
	border-color: #00558D;
	box-shadow: 0 0 0 0.2rem rgba(0, 85, 141, 0.15);
}

.btn-gradient {
	background: linear-gradient(135deg, #00558D 0%, #004474 100%);
	border: none;
	color: white;
	padding: 12px 35px;
	border-radius: 50px;
	font-weight: 600;
	transition: transform 0.2s ease, box-shadow 0.3s ease;
	box-shadow: 0 4px 15px rgba(0, 85, 141, 0.4);
}

.btn-gradient:hover {
	transform: translateY(-2px);
	box-shadow: 0 6px 20px rgba(0, 85, 141, 0.5);
	color: white;
}

.store-image-wrapper {
	border-radius: 15px;
	overflow: hidden;
	box-shadow: 0 8px 25px rgba(0,0,0,0.15);
	cursor: pointer;
	transition: transform 0.3s ease;
}

.store-image-wrapper:hover {
	transform: scale(1.03);
}

.map-container {
	border-radius: 20px;
	overflow: hidden;
	box-shadow: 0 10px 40px rgba(0,0,0,0.1);
	margin-top: 40px;
}

.section-badge {
	display: inline-block;
	background: linear-gradient(135deg, #00558D 0%, #004474 100%);
	color: white;
	padding: 8px 20px;
	border-radius: 50px;
	font-size: 0.85em;
	font-weight: 600;
	margin-bottom: 15px;
	text-transform: uppercase;
	letter-spacing: 1px;
}

.text-gradient {
	background: linear-gradient(135deg, #00558D 0%, #004474 100%);
	-webkit-background-clip: text;
	-webkit-text-fill-color: transparent;
	background-clip: text;
}

.animate-fade-in {
	animation: fadeInUp 0.8s ease-out;
}

@keyframes fadeInUp {
	from {
		opacity: 0;
		transform: translateY(30px);
	}
	to {
		opacity: 1;
		transform: translateY(0);
	}
}

.stats-box {
	text-align: center;
	padding: 25px;
	background: white;
	border-radius: 15px;
	box-shadow: 0 5px 20px rgba(0,0,0,0.08);
	transition: transform 0.3s ease;
}

.stats-box:hover {
	transform: translateY(-5px);
}

.stats-number {
	font-size: 2.5em;
	font-weight: bold;
	background: linear-gradient(135deg, #00558D 0%, #004474 100%);
	-webkit-background-clip: text;
	-webkit-text-fill-color: transparent;
	background-clip: text;
}

.divider {
	height: 3px;
	background: linear-gradient(to right, transparent, #00558D, #004474, transparent);
	margin: 50px 0;
	border: none;
}
</style>

<!-- Hero Section -->
<div class="hero-section">
	<div class="container">
		<div class="row justify-content-center text-center">
			<div class="col-lg-8">
				<h1 class="display-4 fw-bold mb-4 animate-fade-in">Về UteShop</h1>
				<p class="lead fs-4 mb-4 animate-fade-in">
					Khám phá hành trình, sứ mệnh và giá trị cốt lõi của cửa hàng 
					thương mại điện tử công nghệ hàng đầu
				</p>
			</div>
		</div>
	</div>
</div>

<div class="container mb-5">

	<!-- Giới thiệu Section -->
	<div class="section-card animate-fade-in">
		<div class="row align-items-center g-4">
			<div class="col-lg-6">
				<div class="image-wrapper">
					<img src="${pageContext.request.contextPath}/assets/images/about_intro.jpg"
						alt="Giới thiệu UteShop">
					<div class="image-overlay"></div>
				</div>
			</div>
			<div class="col-lg-6">
				<span class="section-badge">📱 Giới thiệu</span>
				<h2 class="fw-bold mb-4 text-gradient">Chào mừng đến với UteShop</h2>
				<p class="fs-5 mb-3 text-muted">
					<strong class="text-dark">UteShop</strong> là nền tảng thương mại điện tử chuyên về
					các sản phẩm công nghệ chính hãng. Từ laptop, smartphone, tablet, phụ kiện
					đến thiết bị thông minh, chúng tôi mang lại sự lựa chọn đa dạng, 
					giá cả minh bạch và dịch vụ hậu mãi tận tâm.
				</p>
				<p class="fs-5 text-muted">
					Chúng tôi tự hào là đối tác của nhiều thương hiệu toàn cầu, 
					cam kết về chất lượng, uy tín và trải nghiệm mua sắm tốt nhất cho khách hàng.
				</p>
			</div>
		</div>
	</div>

	<hr class="divider">

	<!-- Sứ mệnh Section -->
	<div class="section-card animate-fade-in">
		<div class="row align-items-center g-4 flex-lg-row-reverse">
			<div class="col-lg-6">
				<div class="image-wrapper">
					<img src="${pageContext.request.contextPath}/assets/images/about_mission.jpg"
						alt="Sứ mệnh UteShop">
					<div class="image-overlay"></div>
				</div>
			</div>
			<div class="col-lg-6">
				<span class="section-badge">🎯 Sứ mệnh</span>
				<h2 class="fw-bold mb-4 text-gradient">Cam kết của chúng tôi</h2>
				<p class="fs-5 mb-3 text-muted">
					Sứ mệnh của chúng tôi là mang đến trải nghiệm mua sắm 
					<strong class="text-dark">an toàn – tiện lợi – nhanh chóng</strong>. 
					Từ việc chọn sản phẩm, thanh toán, giao hàng đến chăm sóc khách hàng, 
					mọi quy trình đều được tối ưu.
				</p>
				<p class="fs-5 text-muted">
					Chúng tôi không chỉ bán hàng, mà còn mang đến <strong class="text-dark">giải pháp công nghệ</strong>
					giúp khách hàng nâng cao hiệu quả học tập, công việc và giải trí.
				</p>
			</div>
		</div>
	</div>

	<hr class="divider">

	<!-- Giá trị cốt lõi Section -->
	<div class="section-card animate-fade-in">
		<div class="row align-items-center g-4">
			<div class="col-lg-6">
				<div class="image-wrapper">
					<img src="${pageContext.request.contextPath}/assets/images/about_values.jpg"
						alt="Giá trị cốt lõi UteShop">
					<div class="image-overlay"></div>
				</div>
			</div>
			<div class="col-lg-6">
				<span class="section-badge">⭐ Giá trị</span>
				<h2 class="fw-bold mb-4 text-gradient">Giá trị cốt lõi</h2>
				<p class="fs-5 mb-4 text-muted">
					UteShop xây dựng thương hiệu dựa trên 3 giá trị cốt lõi:
				</p>
				<div class="mb-3">
					<div class="value-badge">
						<i class="bi bi-shield-check"></i>
						<span>Uy tín - Sản phẩm chính hãng</span>
					</div>
					<div class="value-badge">
						<i class="bi bi-lightbulb"></i>
						<span>Đổi mới - Công nghệ mới nhất</span>
					</div>
					<div class="value-badge">
						<i class="bi bi-heart"></i>
						<span>Khách hàng là ưu tiên hàng đầu</span>
					</div>
				</div>
				<p class="fs-5 text-muted mt-4">
					Với đội ngũ trẻ trung, sáng tạo và tận tâm, <strong class="text-dark">UteShop</strong>
					hứa hẹn mang đến dịch vụ vượt mong đợi cho mọi khách hàng.
				</p>
			</div>
		</div>
	</div>

	<!-- Stats Section -->
	<div class="row g-4 my-5">
		<div class="col-md-4">
			<div class="stats-box">
				<div class="stats-number">10K+</div>
				<div class="text-muted mt-2">Khách hàng tin tưởng</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="stats-box">
				<div class="stats-number">5K+</div>
				<div class="text-muted mt-2">Sản phẩm chính hãng</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="stats-box">
				<div class="stats-number">98%</div>
				<div class="text-muted mt-2">Khách hàng hài lòng</div>
			</div>
		</div>
	</div>

</div>

<!-- Contact Section -->
<div class="contact-section">
	<div class="container">
		<div class="text-center mb-5">
			<span class="section-badge">📞 Liên hệ</span>
			<h2 class="fw-bold mt-3 text-gradient display-5">Liên hệ với chúng tôi</h2>
			<p class="lead text-muted">Chúng tôi luôn sẵn sàng lắng nghe và hỗ trợ bạn</p>
		</div>

		<!-- Thông báo -->
		<c:if test="${not empty success}">
			<div class="alert alert-success text-center rounded-pill shadow-sm animate-fade-in">
				<i class="bi bi-check-circle me-2"></i>${success}
			</div>
		</c:if>
		<c:if test="${not empty error}">
			<div class="alert alert-danger text-center rounded-pill shadow-sm animate-fade-in">
				<i class="bi bi-exclamation-triangle me-2"></i>${error}
			</div>
		</c:if>

		<div class="row g-4">
			<!-- Thông tin cửa hàng -->
			<div class="col-lg-5">
				<div class="contact-card">
					<h4 class="fw-bold mb-4 text-gradient">
						<i class="bi bi-shop me-2"></i>Thông tin cửa hàng
					</h4>
					
					<div class="contact-info-item">
						<i class="bi bi-building"></i>
						<div>
							<strong class="d-block mb-1">Tên cửa hàng</strong>
							<span class="text-muted"><c:out value="${store.storeName}" /></span>
						</div>
					</div>

					<div class="contact-info-item">
						<i class="bi bi-telephone"></i>
						<div>
							<strong class="d-block mb-1">Hotline</strong>
							<span class="text-muted"><c:out value="${store.hotline}" /></span>
						</div>
					</div>

					<div class="contact-info-item">
						<i class="bi bi-envelope"></i>
						<div>
							<strong class="d-block mb-1">Email</strong>
							<span class="text-muted"><c:out value="${store.email}" /></span>
						</div>
					</div>

					<div class="contact-info-item">
						<i class="bi bi-geo-alt"></i>
						<div>
							<strong class="d-block mb-1">Địa chỉ</strong>
							<span class="text-muted"><c:out value="${store.address}" /></span>
						</div>
					</div>

					<!-- Ảnh cửa hàng -->
					<div class="store-image-wrapper mt-4">
						<img src="${pageContext.request.contextPath}/assets/images/hcmute.png"
							alt="Cửa hàng UteShop" class="w-100">
					</div>
				</div>
			</div>

			<!-- Form liên hệ -->
			<div class="col-lg-7">
				<div class="contact-card">
					<h4 class="fw-bold mb-4 text-gradient">
						<i class="bi bi-chat-dots me-2"></i>Gửi tin nhắn cho chúng tôi
					</h4>
					<form action="${pageContext.request.contextPath}/user/contact" method="post">
						<div class="row g-3">
							<div class="col-md-6">
								<label class="form-label fw-semibold">
									<i class="bi bi-person me-1"></i>Họ và tên
								</label>
								<input type="text" class="form-control" name="fullname" 
									placeholder="Nhập họ và tên của bạn" required>
							</div>
							<div class="col-md-6">
								<label class="form-label fw-semibold">
									<i class="bi bi-envelope me-1"></i>Email
								</label>
								<input type="email" class="form-control" name="email" 
									placeholder="example@email.com" required>
							</div>
							<div class="col-12">
								<label class="form-label fw-semibold">
									<i class="bi bi-chat-text me-1"></i>Nội dung
								</label>
								<textarea class="form-control" name="message" rows="6" 
									placeholder="Nhập nội dung bạn muốn gửi..." required></textarea>
							</div>
							<div class="col-12 text-end">
								<button type="submit" class="btn btn-gradient">
									<i class="bi bi-send me-2"></i>Gửi liên hệ
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- Google Map -->
<div class="container my-5">
	<div class="map-container">
		<iframe src="${mapEmbedUrl}" width="100%" height="450"
			style="border: 0;" allowfullscreen="" loading="lazy"></iframe>
	</div>
</div>

<!-- Script hiệu ứng -->
<script>
document.addEventListener("DOMContentLoaded", function() {
    // Intersection Observer for animations
    const observerOptions = {
        threshold: 0.1,
        rootMargin: '0px 0px -50px 0px'
    };

    const observer = new IntersectionObserver(function(entries) {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.style.opacity = '1';
                entry.target.style.transform = 'translateY(0)';
            }
        });
    }, observerOptions);

    // Observe all section cards
    document.querySelectorAll('.section-card').forEach(card => {
        card.style.opacity = '0';
        card.style.transform = 'translateY(30px)';
        card.style.transition = 'opacity 0.6s ease, transform 0.6s ease';
        observer.observe(card);
    });

    // Smooth scroll for store image
    document.querySelector('.store-image-wrapper')?.addEventListener('click', function() {
        this.style.transform = 'scale(1.05)';
        setTimeout(() => {
            this.style.transform = 'scale(1)';
        }, 200);
    });
});
</script>