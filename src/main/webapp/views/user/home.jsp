<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Uteshop | Trang chủ</title>

</head>
<body>

	<!-- Banner -->
	<div id="bannerDiv" class="carousel slide mb-5" data-bs-ride="carousel">
		<div class="carousel-inner">
			<div class="carousel-item active">
				<img
					src="${pageContext.request.contextPath}/assets/images/banner3.png"
					class="d-block w-100 banner-img" alt="banner3">
			</div>
			<div class="carousel-item">
				<img
					src="${pageContext.request.contextPath}/assets/images/banner2.png"
					class="d-block w-100 banner-img" alt="banner2">
			</div>
			<div class="carousel-item">
				<img
					src="${pageContext.request.contextPath}/assets/images/banner1.png"
					class="d-block w-100 banner-img" alt="banner1">
			</div>
		</div>
		<button class="carousel-control-prev" type="button"
			data-bs-target="#bannerDiv" data-bs-slide="prev">
			<span class="carousel-control-prev-icon"></span>
		</button>
		<button class="carousel-control-next" type="button"
			data-bs-target="#bannerDiv" data-bs-slide="next">
			<span class="carousel-control-next-icon"></span>
		</button>
	</div>

	<div class="container">

		<!-- Categories -->
		<h3 class="section-title">
			<i class="bi bi-grid"></i> Danh mục
		</h3>
		<div class="row row-cols-2 row-cols-md-4 g-3" id="categoryContainer">
			<c:forEach var="c" items="${categories}" varStatus="status">
				<div
					class="col category-item <c:if test='${status.index >= 8}'>d-none extra-category</c:if>">
					<div class="card h-100 text-center shadow-sm">
						<img src="${pageContext.request.contextPath}/assets/images/categories/${c.image}"
							class="card-img-top" style="height: 150px; object-fit: cover;"
							alt="${c.name}">
						<div class="card-body p-2">
							<h6 class="card-title">${c.name}</h6>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="text-center mt-3">
			<button class="btn btn-outline-primary btn-sm" id="toggleCategories">Xem
				thêm</button>
		</div>

		<!-- Bộ lọc sản phẩm -->
		<div class="card mb-4 shadow-sm mt-4">
			<div class="card-body">
				<form action="${pageContext.request.contextPath}/user/home"
					method="get" class="row g-3 align-items-end">
					<div class="col-md-3">
						<label class="form-label fw-bold">Danh mục</label> <select
							class="form-select" name="categoryId">
							<option value="">Tất cả</option>
							<c:forEach var="c" items="${categories}">
								<option value="${c.categoryId}">${c.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-3">
						<label class="form-label fw-bold">Khoảng giá</label>
						<div class="input-group">
							<input type="number" class="form-control" placeholder="Từ"
								name="minPrice"> <span class="input-group-text">-</span>
							<input type="number" class="form-control" placeholder="Đến"
								name="maxPrice">
						</div>
					</div>
					<div class="col-md-3">
						<label class="form-label fw-bold">Sắp xếp</label> <select
							class="form-select" name="sortBy">
							<option value="newest">Mới nhất</option>
							<option value="priceAsc">Giá: Thấp → Cao</option>
							<option value="priceDesc">Giá: Cao → Thấp</option>
						</select>
					</div>
					<div class="col-md-3 d-flex gap-2">
						<button type="submit" class="btn btn-primary w-50">
							<i class="bi bi-filter"></i> Lọc
						</button>
						<a href="${pageContext.request.contextPath}/user/home"
							class="btn btn-outline-secondary w-50">Xóa lọc</a>
					</div>
				</form>
			</div>
		</div>

		<!-- Toggle view -->
		<div class="d-flex justify-content-end mb-3">
			<div class="btn-group" role="group">
				<button type="button" class="btn btn-primary active" id="btnGrid">
					<i class="bi bi-grid"></i>
				</button>
				<button type="button" class="btn btn-outline-secondary" id="btnList">
					<i class="bi bi-list"></i>
				</button>
			</div>
		</div>

		<!-- Grid View -->
		<div class="row g-4" id="gridView">
			<c:forEach var="p" items="${products}">
				<div class="col-12 col-sm-6 col-lg-4">
					<div
						class="card border shadow-sm h-100 overflow-hidden product-card">
						<a
							href="${pageContext.request.contextPath}/user/product/detail?id=${p.productId}">
							<img src="${pageContext.request.contextPath}${p.imageUrl}"
							class="card-img-top img-fluid product-img" alt="${p.name}">
						</a>
						<div class="card-body d-flex flex-column justify-content-between"
							style="height: 180px;">
							<h5 class="card-title mb-1" style="font-size: 16px;">${p.name}</h5>
							<div class="mb-1">
								<span class="text-warning">★★★★☆</span> <small
									class="text-muted">(${p.reviewsCount} đánh giá)</small>
							</div>
							<div class="d-flex align-items-center gap-2">
								<p class="text-danger fw-bold mb-0" style="font-size: 15px;">
									<fmt:formatNumber value="${p.price}" type="currency"
										currencySymbol="₫" />
								</p>
								<c:if test="${p.oldPrice ne null}">
									<p class="text-muted text-decoration-line-through mb-0 small">
										<fmt:formatNumber value="${p.oldPrice}" type="currency"
											currencySymbol="₫" />
									</p>
								</c:if>
							</div>
							<button class="btn btn-sm w-100"
								style="background-color: #00558D; color: #fff;">
								<i class="bi bi-cart-plus"></i> Thêm vào giỏ hàng
							</button>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>

		<!-- List View -->
		<div class="list-group d-none" id="listView">
			<c:forEach var="p" items="${products}">
				<div
					class="list-group-item d-flex align-items-center justify-content-between">
					<div class="d-flex align-items-center">
						<img src="${pageContext.request.contextPath}${p.imageUrl}"
							alt="${p.name}" class="img-thumbnail me-3"
							style="width: 80px; height: 80px; object-fit: cover;">
						<div>
							<h6 class="mb-1">${p.name}</h6>
							<small class="text-muted">${p.reviewsCount} đánh giá</small>
						</div>
					</div>
					<div class="text-end">
						<p class="text-danger fw-bold mb-0">
							<fmt:formatNumber value="${p.price}" type="currency"
								currencySymbol="₫" />
						</p>
						<c:if test="${p.oldPrice ne null}">
							<p class="text-muted text-decoration-line-through mb-0 small">
								<fmt:formatNumber value="${p.oldPrice}" type="currency"
									currencySymbol="₫" />
							</p>
						</c:if>
						<button class="btn btn-sm btn-primary mt-2">
							<i class="bi bi-cart-plus"></i> Thêm vào giỏ
						</button>
					</div>
				</div>
			</c:forEach>
		</div>

		<!-- DataTable Products -->
		<h3 class="section-title mt-5">
			<i class="bi bi-star-fill text-warning"></i> Sản phẩm nổi bật
		</h3>
		<div class="table-responsive">
			<table id="productTable" class="table table-borderless align-middle">
				<thead class="table-light">
					<tr>
						<th>Hình ảnh</th>
						<th>Tên sản phẩm</th>
						<th>Giá</th>
						<th>Còn lại</th>
						<th>Thao tác</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="p" items="${products}">
						<tr>
							<td width="120"><img
								src="${pageContext.request.contextPath}${p.imageUrl}"
								alt="${p.name}" class="img-thumbnail"
								style="height: 80px; object-fit: cover;"></td>
							<td><h6 class="mb-0">${p.name}</h6>
								<small class="text-muted">${p.reviewsCount} đánh giá</small></td>
							<td><span class="text-danger fw-bold"><fmt:formatNumber
										value="${p.price}" type="currency" currencySymbol="₫" /></span> <c:if
									test="${p.oldPrice ne null}">
									<br>
									<span class="text-muted text-decoration-line-through"><fmt:formatNumber
											value="${p.oldPrice}" type="currency" currencySymbol="₫" /></span>
								</c:if></td>
							<td>${p.stock}</td>
							<td>
								<form action="${pageContext.request.contextPath}/user/cart/add"
									method="post">
									<input type="hidden" name="productId" value="${p.productId}" />
									<button type="submit" class="btn btn-sm btn-primary">
										<i class="bi bi-cart-plus"></i> Thêm
									</button>
								</form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

	</div>

	<!-- Scripts -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	<script
		src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
	<script
		src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>

	<script>
	// Toggle categories
	document.addEventListener("DOMContentLoaded", function() {
		const btn = document.getElementById("toggleCategories");
		btn.addEventListener("click", function() {
			document.querySelectorAll(".extra-category").forEach(el => el.classList.toggle("d-none"));
			btn.textContent = btn.textContent === "Xem thêm" ? "Thu gọn" : "Xem thêm";
		});
	});

	// Toggle grid/list view
	const btnGrid = document.getElementById("btnGrid");
	const btnList = document.getElementById("btnList");
	const gridView = document.getElementById("gridView");
	const listView = document.getElementById("listView");

	btnGrid.addEventListener("click", function () {
		gridView.classList.remove("d-none");
		listView.classList.add("d-none");
		btnGrid.classList.add("btn-primary"); btnGrid.classList.remove("btn-outline-secondary");
		btnList.classList.remove("btn-primary"); btnList.classList.add("btn-outline-secondary");
	});

	btnList.addEventListener("click", function () {
		listView.classList.remove("d-none");
		gridView.classList.add("d-none");
		btnList.classList.add("btn-primary"); btnList.classList.remove("btn-outline-secondary");
		btnGrid.classList.remove("btn-primary"); btnGrid.classList.add("btn-outline-secondary");
	});

	// DataTable
	$(document).ready(function () {
		$('#productTable').DataTable({
			"pageLength": 6,
			"lengthMenu": [6, 12, 24],
			"language": {
				"lengthMenu": "Hiển thị _MENU_ sản phẩm",
				"zeroRecords": "Không tìm thấy sản phẩm nào",
				"info": "Trang _PAGE_ / _PAGES_",
				"infoEmpty": "Không có dữ liệu",
				"infoFiltered": "(lọc từ _MAX_ sản phẩm)",
				"search": "Tìm kiếm:",
				"paginate": { "first": "Đầu", "last": "Cuối", "next": "Sau", "previous": "Trước" }
			}
		});
	});
	</script>
</body>
</html>
