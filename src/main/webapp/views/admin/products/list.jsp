<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid">
	<h3 class="text-primary-custom fw-bold mb-4">
		<i class="bi bi-box-seam me-2"></i> Quản lý sản phẩm
	</h3>

	<!-- Nút thêm -->
	<div class="mb-3">
		<a href="${pageContext.request.contextPath}/admin/products/add" class="btn btn-success">
			<i class="bi bi-plus"></i> Thêm sản phẩm
		</a>
	</div>

	<!-- Bảng sản phẩm -->
	<div class="card shadow-sm">
		<div class="card-body table-responsive">
			<table id="productTable" class="table table-hover align-middle mb-0">
				<thead class="table-light">
					<tr>
						<th class="text-center">ID</th>
						<th class="text-center">Ảnh</th>
						<th>Tên sản phẩm</th>
						<th class="text-center">Danh mục</th>
						<th class="text-center">Cửa hàng</th>
						<th class="text-center">Đánh giá</th>
						<th class="text-center">Hành động</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="p" items="${products}">
						<tr class="product-row" 
							data-id="${p.productId}" 
							data-name="${p.name}" 
							data-desc="${p.description}" 
							data-category="${p.category.name}" 
							data-shop="${p.shop.name}" 
							data-img="${pageContext.request.contextPath}/assets${p.imageUrl}">
							
							<td class="text-center">${p.productId}</td>
							<td class="text-center">
								<img src="${pageContext.request.contextPath}/assets${p.imageUrl}" 
									class="rounded border" width="70" height="70" style="object-fit:cover;">
							</td>
							<td>${p.name}</td>
							<td class="text-center"><span class="badge bg-info text-dark">${p.category.name}</span></td>
							<td class="text-center"><span class="badge bg-primary">${p.shop.name}</span></td>
							<td class="text-center">
								<i class="bi bi-star-fill text-warning"></i> 
								${p.averageRating > 0 ? String.format("%.1f", p.averageRating) : "N/A"}
							</td>
							<td class="text-center">
								<a href="${pageContext.request.contextPath}/admin/products/edit?id=${p.productId}" class="text-warning me-3">
									<i class="bi bi-pencil-square fs-5"></i>
								</a>
								<!-- Xóa --> <a href="javascript:void(0);"
								class="text-danger me-2" data-bs-toggle="modal"
								data-bs-target="#confirmDeleteModal" data-id="${p.productId}"
								data-url="${pageContext.request.contextPath}/admin/products/delete"
								title="Xóa"> <i class="bi bi-trash-fill fs-5"></i>
							</a>
							
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

<!--  Modal chi tiết sản phẩm -->
<div class="modal fade" id="productDetailModal" tabindex="-1" aria-hidden="true">
	<div class="modal-dialog modal-lg modal-dialog-centered">
		<div class="modal-content border-0 shadow-lg">
			<div class="modal-header bg-primary text-white">
				<h5 class="modal-title fw-bold"><i class="bi bi-info-circle me-2"></i>Chi tiết sản phẩm</h5>
				<button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-4 text-center mb-3">
						<img id="detailImage" src="" class="rounded border shadow-sm" width="100%" style="max-width:180px;object-fit:cover;">
					</div>
					<div class="col-md-8">
						<h5 id="detailName" class="fw-bold text-primary-custom mb-2"></h5>
						<p id="detailDesc" class="text-muted"></p>
						<p><strong>Danh mục:</strong> <span id="detailCategory" class="text-info fw-semibold"></span></p>
						<p><strong>Cửa hàng:</strong> <span id="detailShop" class="text-primary fw-semibold"></span></p>
					</div>
				</div>

				<hr>

				<h6 class="fw-bold text-primary-custom">Biến thể sản phẩm</h6>
				<div class="table-responsive">
					<table id="variantTable" class="table table-sm table-bordered mt-2">
						<thead class="table-light">
							<tr>
								<th>Ảnh</th>
								<th>Tên tùy chọn</th>
								<th>Giá trị</th>
								<th>Giá (₫)</th>
								<th>Giá cũ</th>
								<th>Tồn kho</th>
							</tr>
						</thead>
						<tbody id="variantBody">
							<tr><td colspan="5" class="text-center text-muted">Đang tải...</td></tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Đóng</button>
			</div>
		</div>
	</div>
</div>

<!--  Script DataTables + Xử lý Modal -->
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.13.7/js/dataTables.bootstrap5.min.js"></script>
<!-- JS Xử lý Modal Xóa -->
<script
	src="${pageContext.request.contextPath}/assets/js/admin/modal-delete.js"></script>

<!-- Modal xác nhận xóa -->
<div class="modal fade" id="confirmDeleteModal" tabindex="-1"
	aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content shadow-lg border-0 rounded-3">
			<div class="modal-header bg-danger text-white">
				<h5 class="modal-title">
					<i class="bi bi-exclamation-triangle-fill me-2"></i> Xác nhận xóa
				</h5>
				<button type="button" class="btn-close btn-close-white"
					data-bs-dismiss="modal" aria-label="Đóng"></button>
			</div>
			<div class="modal-body">
				<p>
					Bạn có chắc muốn xóa cửa hàng này không? Hành động này <strong>không
						thể hoàn tác</strong>.
				</p>
			</div>
			<div class="modal-footer">
				<button type="button"
					class="btn btn-outline-secondary rounded-pill px-4"
					data-bs-dismiss="modal">Hủy</button>
				<a id="deleteConfirmBtn" href="#"
					class="btn btn-danger rounded-pill px-4"> <i
					class="bi bi-trash-fill me-1"></i> Xóa
				</a>
			</div>
		</div>
	</div>
</div>
<script>
$(document).ready(function() {
	const table = $('#productTable').DataTable({
		"pageLength": 10,
		"lengthChange": false,
		"ordering": true,
		"language": {
			"search": "Tìm kiếm:",
			"paginate": { "next": "›", "previous": "‹" },
			"info": "Hiển thị _START_ - _END_ / _TOTAL_ sản phẩm",
			"infoEmpty": "Không có dữ liệu",
			"zeroRecords": "Không tìm thấy sản phẩm phù hợp"
		},
		columnDefs: [{ orderable: false, targets: [1,6] }]
	});

	// Khi click dòng sản phẩm
	$('#productTable tbody').on('click', 'tr.product-row', function(e) {
		// Bỏ qua nếu click vào nút xóa / sửa
		if ($(e.target).closest('a').length > 0) return;

		var id = $(this).data('id');
		var name = $(this).data('name');
		var desc = $(this).data('desc') || 'Chưa có mô tả';
		var category = $(this).data('category');
		var shop = $(this).data('shop');
		var img = $(this).data('img');

		// Gán vào modal
		$('#detailImage').attr('src', img);
		$('#detailName').text(name);
		$('#detailDesc').text(desc);
		$('#detailCategory').text(category);
		$('#detailShop').text(shop);

		// Gọi AJAX lấy biến thể
		$('#variantBody').html('<tr><td colspan="6" class="text-center text-muted">Đang tải...</td></tr>');
		$.ajax({
			url: '${pageContext.request.contextPath}/admin/products/variants?productId=' + id,
			type: 'GET',
			dataType: 'json',
			cache: false,
			success: function(data) {
				console.log("Raw data:", data);
				console.log("Type:", typeof data);
				
				// Nếu data là string, parse nó
				if (typeof data === 'string') {
					try {
						data = JSON.parse(data);
					} catch(e) {
						console.error("JSON parse error:", e);
						$('#variantBody').html('<tr><td colspan="6" class="text-danger text-center">Lỗi định dạng dữ liệu!</td></tr>');
						return;
					}
				}
				
				if (!data || data.length === 0) {
					$('#variantBody').html('<tr><td colspan="6" class="text-center text-muted">Không có biến thể</td></tr>');
					return;
				}
				
				var contextPath = '${pageContext.request.contextPath}';
				var rows = '';
				for (var i = 0; i < data.length; i++) {
				    var v = data[i];
				    // Chuyển giá trị chuỗi sang số để format đúng
				    var price = parseFloat(v.price) || 0;
				    var oldPrice = v.oldPrice ? parseFloat(v.oldPrice) : 0;
				    
				    // Xử lý URL ảnh
				    var imgUrl = v.imageUrl || '/images/products/default-product.jpg';
				    // Nếu imageUrl chưa có contextPath, thêm vào
				    if (imgUrl.indexOf(contextPath) === -1) {
				        imgUrl = contextPath + '/assets' + imgUrl;
				    }

				    rows += '<tr>' +
				        '<td class="text-center"><img src="' + imgUrl + '" class="rounded border" width="50" height="50" style="object-fit: cover;"></td>' +
				        '<td>' + (v.optionName || '-') + '</td>' +
				        '<td>' + (v.optionValue || '-') + '</td>' +
				        '<td>' + (price ? price.toLocaleString('vi-VN') + '₫' : '-') + '</td>' +
				        '<td>' + (oldPrice ? oldPrice.toLocaleString('vi-VN') + '₫' : '-') + '</td>' +
				        '<td>' + (v.stock || 0) + '</td>' +
				        '</tr>';
				}
				$('#variantBody').html(rows);
			},
			error: function(xhr, status, error) {
				console.error("AJAX Error:", status, error);
				$('#variantBody').html('<tr><td colspan="6" class="text-danger text-center">Lỗi tải dữ liệu!</td></tr>');
			}
		});

		var modal = new bootstrap.Modal(document.getElementById('productDetailModal'));
		modal.show();
	});
});
</script>

