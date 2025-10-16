<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>

<h3 class="fw-bold text-primary-custom mb-4">
	<i class="bi bi-gift"></i> Quản lý khuyến mãi
</h3>

<!-- ====== BIỂU ĐỒ ====== -->
<div class="row align-items-stretch mb-4">
	<!-- Biểu đồ 1 -->
	<div class="col-md-6 d-flex">
		<div class="card shadow-sm flex-fill h-100">
			<div class="card-body d-flex flex-column">
				<h5 class="text-primary-custom fw-bold mb-3">
					<i class="bi bi-pie-chart"></i> Phân bố loại khuyến mãi
				</h5>
				<div
					class="flex-grow-1 d-flex align-items-center justify-content-center">
					<canvas id="typeChart"></canvas>
				</div>
			</div>
		</div>
	</div>

	<!-- Biểu đồ 2 -->
	<div class="col-md-6 d-flex">
		<div class="card shadow-sm flex-fill h-100">
			<div class="card-body d-flex flex-column">
				<h5 class="text-primary-custom fw-bold mb-3">
					<i class="bi bi-graph-up"></i> Giá trị trung bình
				</h5>
				<div
					class="flex-grow-1 d-flex align-items-center justify-content-center">
					<canvas id="avgChart"></canvas>
				</div>
				<div class="mt-2 text-muted small">
					<i class="bi bi-info-circle"></i> <b>TB %:</b>
					<fmt:formatNumber value="${avgPercent}" pattern="#,##0.##" />
					% &nbsp; | &nbsp; <b>TB ₫:</b>
					<fmt:formatNumber value="${avgFixed}" type="currency"
						currencySymbol="₫" />
				</div>

			</div>
		</div>
	</div>
</div>

<!-- ====== NÚT THÊM ====== -->
<div class="text-end mb-3">
	<a href="${pageContext.request.contextPath}/admin/promotions/add"
		class="btn btn-success"> <i class="bi bi-plus-circle"></i> Thêm
		khuyến mãi
	</a>
</div>

<!-- ====== BẢNG DANH SÁCH ====== -->
<div class="card shadow-sm">
	<div class="card-body">
		<table id="promotionTable" class="table table-striped align-middle">
			<thead class="table-light">
  <tr>
    <th>ID</th>
    <th>Shop</th>
    <th>Sản phẩm</th> 
    <th>Loại giảm</th>
    <th>Giá trị</th>
    <th>Bắt đầu</th>
    <th>Kết thúc</th>
    <th class="text-center">Hành động</th>
  </tr>
</thead>
<tbody>
  <c:forEach var="p" items="${promotions}">
    <tr>
      <td>${p.promotionId}</td>
      <td>${p.shop.name}</td>
      <td>${p.product != null ? p.product.name : '<i>Toàn shop</i>'}</td> <!-- ✅ -->
      <td><span class="badge ${p.discountType eq 'percent' ? 'bg-info' : 'bg-success'}">
        ${p.discountType eq 'percent' ? 'Giảm %' : 'Giảm cố định'}
      </span></td>
      <td>
        <strong>
          <c:choose>
            <c:when test="${p.discountType eq 'percent'}">${p.value}%</c:when>
            <c:otherwise><fmt:formatNumber value="${p.value}" type="currency" currencySymbol="₫" /></c:otherwise>
          </c:choose>
        </strong>
      </td>
      <td><fmt:formatDate value="${p.startDate}" pattern="dd/MM/yyyy" /></td>
      <td><fmt:formatDate value="${p.endDate}" pattern="dd/MM/yyyy" /></td>
      <td class="text-center">
        <a href="${pageContext.request.contextPath}/admin/promotions/edit?id=${p.promotionId}" class="btn btn-sm btn-warning">
          <i class="bi bi-pencil"></i>
        </a>
        <button type="button" class="btn btn-sm btn-danger" data-bs-toggle="modal"
          data-bs-target="#confirmDeleteModal"
          data-delete-url="${pageContext.request.contextPath}/admin/promotions/delete?id=${p.promotionId}">
          <i class="bi bi-trash"></i>
        </button>
      </td>
    </tr>
  </c:forEach>
</tbody>

		</table>
	</div>
</div>

<!-- ====== MODAL XÓA ====== -->
<div class="modal fade" id="confirmDeleteModal" tabindex="-1">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content border-0 shadow-lg rounded-3">
			<div class="modal-header bg-danger text-white">
				<h5 class="modal-title">
					<i class="bi bi-exclamation-triangle-fill me-2"></i>Xác nhận xóa
				</h5>
				<button type="button" class="btn-close btn-close-white"
					data-bs-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				Bạn có chắc muốn xóa khuyến mãi này không?<br>
				<strong>Hành động này không thể hoàn tác.</strong>
			</div>
			<div class="modal-footer">
				<button class="btn btn-outline-secondary" data-bs-dismiss="modal">Hủy</button>
				<a id="deleteConfirmBtn" href="#" class="btn btn-danger"><i
					class="bi bi-trash-fill me-1"></i> Xóa</a>
			</div>
		</div>
	</div>
</div>

<!-- ====== SCRIPT ====== -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script
	src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.min.js"></script>
<script
	src="https://cdn.datatables.net/1.13.7/js/dataTables.bootstrap5.min.js"></script>

<script>
document.addEventListener('DOMContentLoaded', () => {
  const percentCount = ${percentCount}, fixedCount = ${fixedCount};
  const avgPercent = ${avgPercent}, avgFixed = ${avgFixed};

  // Biểu đồ phân bố loại (Doughnut)
  new Chart(document.getElementById('typeChart'), {
    type: 'doughnut',
    data: {
      labels: ['Giảm theo %', 'Giảm cố định'],
      datasets: [{
        data: [percentCount, fixedCount],
        backgroundColor: ['#36A2EB', '#4BC0C0']
      }]
    },
    options: {
      maintainAspectRatio: false,
      plugins: { legend: { position: 'bottom' } }
    }
  });

  // Biểu đồ giá trị trung bình (Bar)
  // ===== BIỂU ĐỒ GIÁ TRỊ TRUNG BÌNH (2 TRỤC Y) =====
const ctxAvg = document.getElementById('avgChart').getContext('2d');
new Chart(ctxAvg, {
  data: {
    labels: ['Giảm trung bình'],
    datasets: [
      {
        type: 'bar',
        label: 'Trung bình giảm (%)',
        data: [${avgPercent}],
        yAxisID: 'yPercent',
        backgroundColor: 'rgba(54, 162, 235, 0.7)',
        borderColor: 'rgb(54, 162, 235)',
        borderWidth: 2
      },
      {
        type: 'bar',
        label: 'Trung bình giảm (₫)',
        data: [${avgFixed}],
        yAxisID: 'yVND',
        backgroundColor: 'rgba(75, 192, 192, 0.7)',
        borderColor: 'rgb(75, 192, 192)',
        borderWidth: 2
      }
    ]
  },
  options: {
    maintainAspectRatio: false,
    plugins: {
      legend: { position: 'bottom' },
      tooltip: {
        callbacks: {
          label: function(context) {
            const val = context.parsed.y;
            if (context.dataset.yAxisID === 'yPercent') {
              return ' ' + val.toFixed(2) + '%';
            } else {
              return ' ' + new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(val);
            }
          }
        }
      }
    },
    scales: {
      yPercent: {
        position: 'left',
        beginAtZero: true,
        title: { display: true, text: 'Phần trăm (%)' },
        ticks: {
          callback: val => val + '%'
        }
      },
      yVND: {
        position: 'right',
        beginAtZero: true,
        grid: { drawOnChartArea: false }, // tránh chồng lưới
        title: { display: true, text: 'Giá trị (VNĐ)' },
        ticks: {
          callback: val => new Intl.NumberFormat('vi-VN').format(val)
        }
      }
    }
  }
});


  // DataTable
  $('#promotionTable').DataTable({
    pageLength: 5,
    lengthMenu: [[5, 10, 25, 50, -1], [5, 10, 25, 50, "Tất cả"]],
    language: {
      lengthMenu: "Hiển thị _MENU_ dòng",
      search: "Tìm kiếm:",
      paginate: { previous: "Trước", next: "Sau" },
      info: "Hiển thị _START_–_END_ / _TOTAL_ khuyến mãi",
      emptyTable: "Không có dữ liệu"
    }
  });

  // Modal xóa
  const modal = document.getElementById('confirmDeleteModal');
  modal.addEventListener('show.bs.modal', e => {
    document.getElementById('deleteConfirmBtn').href =
      e.relatedTarget.getAttribute('data-delete-url');
  });
});
</script>
