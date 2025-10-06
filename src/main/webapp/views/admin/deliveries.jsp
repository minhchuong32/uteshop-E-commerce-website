<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Quản lý vận chuyển</title>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">

<style>
:root {
    --primary-color: #00558D;
    --primary-hover: #004474;
}
.bg-primary-custom { background-color: var(--primary-color)!important; color:white!important; }
.text-primary-custom { color: var(--primary-color)!important; }
.btn-primary-custom { background-color: var(--primary-color)!important; border-color: var(--primary-color)!important; color: #fff; }
.btn-primary-custom:hover { background-color: var(--primary-hover)!important; }
table th { background-color: var(--primary-color)!important; color: white; text-align: center; vertical-align: middle; }
table td { vertical-align: middle; }

.alert-download {
    position: fixed; top: 80px; right: 20px; z-index: 9999;
    min-width: 350px; box-shadow: 0 4px 12px rgba(0,0,0,0.15);
    animation: slideIn 0.3s ease-out;
}
@keyframes slideIn {
    from { transform: translateX(400px); opacity: 0; }
    to { transform: translateX(0); opacity: 1; }
}
</style>
</head>

<body class="bg-light">
<div class="container mt-4">

    <!-- ====================== THÔNG BÁO THÀNH CÔNG + LINK DOWNLOAD ====================== -->
    <c:if test="${not empty sessionScope.message}">
        <div class="alert alert-success alert-dismissible fade show alert-download" role="alert">
            <h5 class="alert-heading">
                <i class="bi bi-check-circle-fill me-2"></i>${sessionScope.message}
            </h5>
            <hr>
            <p class="mb-2">
                <i class="bi bi-file-earmark-pdf-fill me-1"></i>
                <strong>File:</strong> ${sessionScope.fileName}
            </p>
            <div class="d-grid gap-2">
                <!-- Link tải PDF -->
                <a href="${sessionScope.downloadLink}" class="btn btn-primary btn-sm" download>
                    <i class="bi bi-download me-1"></i>Tải xuống PDF
                </a>
                <!-- Link xem trước PDF -->
                <a href="${sessionScope.downloadLink}" class="btn btn-outline-primary btn-sm" target="_blank">
                    <i class="bi bi-eye me-1"></i>Xem trước
                </a>
            </div>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <c:remove var="message" scope="session"/>
        <c:remove var="downloadLink" scope="session"/>
        <c:remove var="fileName" scope="session"/>
    </c:if>

    <c:if test="${not empty sessionScope.error}">
        <div class="alert alert-danger alert-dismissible fade show alert-download" role="alert">
            <i class="bi bi-exclamation-triangle-fill me-2"></i>${sessionScope.error}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <c:remove var="error" scope="session"/>
    </c:if>

    <!-- ====================== BIỂU ĐỒ HIỆU SUẤT ====================== -->
    <h4 class="fw-bold text-primary-custom mb-3">
        <i class="bi bi-bar-chart-fill me-2"></i>Hiệu suất giao hàng
    </h4>

    <div class="card shadow-sm border-0 mb-4">
        <div class="card-body">
            <canvas id="deliveryChart" height="100"></canvas>
            <script>
                const shipperNames = [];
                const totalDeliveries = [];
                const successRates = [];

                <c:forEach var="s" items="${stats}">
                    shipperNames.push("${s[0]}");
                    totalDeliveries.push(${s[1]});
                    successRates.push(${s[2]});
                </c:forEach>;

                const ctx = document.getElementById('deliveryChart');
                new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: shipperNames,
                        datasets: [
                            {
                                label: 'Tổng đơn hàng',
                                data: totalDeliveries,
                                backgroundColor: 'rgba(0, 85, 141, 0.6)',
                                borderColor: '#00558D',
                                borderWidth: 1
                            },
                            {
                                label: 'Tỷ lệ giao thành công (%)',
                                data: successRates,
                                backgroundColor: 'rgba(40, 167, 69, 0.6)',
                                borderColor: '#28a745',
                                borderWidth: 1,
                                yAxisID: 'y1'
                            }
                        ]
                    },
                    options: {
                        responsive: true,
                        interaction: { mode: 'index', intersect: false },
                        scales: {
                            y: { beginAtZero: true, title: { display: true, text: 'Tổng số đơn' } },
                            y1: {
                                beginAtZero: true, position: 'right',
                                grid: { drawOnChartArea: false },
                                title: { display: true, text: 'Tỷ lệ (%)' }, max: 100
                            }
                        },
                        plugins: { legend: { position: 'top' }, tooltip: { enabled: true } }
                    }
                });
            </script>
        </div>
    </div>

    <!-- ====================== BẢNG QUẢN LÝ VẬN CHUYỂN ====================== -->
    <h3 class="fw-bold text-primary-custom mb-3">📦 Quản lý vận chuyển</h3>

    <div class="card shadow-sm border-0">
        <div class="card-body table-responsive">
            <table class="table table-bordered table-hover align-middle mb-0">
                <thead class="text-center">
                    <tr>
                        <th>ID</th>
                        <th>Shipper</th>
                        <th>Đơn hàng</th>
                        <th>Trạng thái</th>
                        <th>Phiếu giao hàng</th>
                        <th>Ngày tạo</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="d" items="${deliveries}">
                        <tr>
                            <td class="text-center">${d.deliveryId}</td>
                            <td>
                                <b>${d.shipper.name}</b><br>
                                <small>${d.shipper.email}<br>${d.shipper.phone}</small>
                            </td>
                            <td>
                                <b>#${d.order.orderId}</b> - ${d.order.status}<br>
                                ${d.order.paymentMethod}<br>${d.order.address}<br>
                                <fmt:formatNumber value="${d.order.totalAmount}" type="currency" currencySymbol="₫"/>
                            </td>
                            <td class="text-center">
                                <span class="badge bg-${d.status == 'Delivered' ? 'success' : d.status == 'In Transit' ? 'warning' : 'secondary'}">
                                    ${d.status}
                                </span>
                            </td>
                            <td class="text-center">
                                <c:choose>
                                    <c:when test="${not empty d.deliveryNote}">
                                        <!-- Link tải PDF dùng đúng đường dẫn -->
                                        <a href="${pageContext.request.contextPath}${d.deliveryNote}"
                                           class="btn btn-outline-success btn-sm rounded-pill"
                                           download>
                                           <i class="bi bi-download me-1"></i>Tải
                                        </a>
                                        <a href="${pageContext.request.contextPath}${d.deliveryNote}"
                                           class="btn btn-outline-primary btn-sm rounded-pill"
                                           target="_blank">
                                           <i class="bi bi-eye me-1"></i>Xem
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <button class="btn btn-outline-success btn-sm rounded-pill"
                                                data-bs-toggle="modal"
                                                data-bs-target="#addNoteModal"
                                                data-id="${d.deliveryId}"
                                                data-note="${d.noteText}">
                                            <i class="bi bi-plus-circle me-1"></i>Tạo phiếu
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                                <c:if test="${not empty d.noteText}">
                                    <div class="mt-2 text-muted small">
                                        <i class="bi bi-chat-left-text me-1"></i>${d.noteText}
                                    </div>
                                </c:if>
                            </td>
                            <td class="text-center">
                                <fmt:formatDate value="${d.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
                            </td>
                            <td class="text-center">
                                <a href="javascript:void(0);" class="btn btn-danger btn-sm rounded-pill"
                                   data-bs-toggle="modal"
                                   data-bs-target="#confirmDeleteModal"
                                   data-id="${d.deliveryId}"
                                   data-url="${pageContext.request.contextPath}/admin/deliveries/delete">
                                   <i class="bi bi-trash-fill"></i>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- ====================== MODAL TẠO PHIẾU ====================== -->
<div class="modal fade" id="addNoteModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <form class="modal-content" 
              action="${pageContext.request.contextPath}/admin/deliveries/note"
              method="post" enctype="multipart/form-data">
            <div class="modal-header bg-primary-custom text-white">
                <h5 class="modal-title">
                    <i class="bi bi-file-earmark-text me-2"></i>Tạo phiếu giao hàng
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <input type="hidden" name="deliveryId" id="noteDeliveryId">
                <div class="mb-3">
                    <label class="form-label fw-bold">Ghi chú</label>
                    <textarea class="form-control" id="noteText" name="noteText" rows="3"
                              placeholder="Nhập ghi chú cho phiếu giao hàng..."></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="submit" name="actionType" value="generate"
                        class="btn btn-primary-custom rounded-pill px-4">
                    <i class="bi bi-file-earmark-plus me-1"></i>Tạo PDF tự động
                </button>
            </div>
        </form>
    </div>
</div>

<!-- ====================== MODAL XÓA ====================== -->
<div class="modal fade" id="confirmDeleteModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header bg-danger text-white">
                <h5 class="modal-title">
                    <i class="bi bi-exclamation-triangle-fill me-2"></i>Xác nhận xóa
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <p>Bạn có chắc muốn xóa phiếu giao hàng này không?</p>
                <p class="text-danger small mb-0">⚠️ File PDF đính kèm cũng sẽ bị xóa!</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary rounded-pill px-4" data-bs-dismiss="modal">Hủy</button>
                <a id="deleteConfirmBtn" href="#" class="btn btn-danger rounded-pill px-4">
                    <i class="bi bi-trash-fill me-1"></i>Xóa
                </a>
            </div>
        </div>
    </div>
</div>

<script>
document.addEventListener("DOMContentLoaded", function() {
    const addNoteModal = document.getElementById('addNoteModal');
    addNoteModal.addEventListener('show.bs.modal', function(event) {
        const button = event.relatedTarget;
        document.getElementById('noteDeliveryId').value = button.getAttribute('data-id');
        document.getElementById('noteText').value = button.getAttribute('data-note') || "";
    });

    const deleteModal = document.getElementById('confirmDeleteModal');
    const confirmBtn = document.getElementById('deleteConfirmBtn');
    deleteModal.addEventListener('show.bs.modal', function(event) {
        const button = event.relatedTarget;
        confirmBtn.setAttribute('href', button.getAttribute('data-url') + '?id=' + button.getAttribute('data-id'));
    });

    setTimeout(() => {
        document.querySelectorAll('.alert-download').forEach(alert => new bootstrap.Alert(alert).close());
    }, 10000);
});
</script>

</body>
</html>
