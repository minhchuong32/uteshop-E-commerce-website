<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="container-fluid">
    <h3 class="mb-4 fw-bold">Báo cáo &amp; Phân tích</h3>

    <div class="row g-4 align-items-stretch">
    <!-- Doanh thu -->
    <div class="col-md-6 d-flex">
	        <div class="card shadow-sm rounded-3 w-100 h-100">
	            <div class="card-body">
	                <h5 class="fw-bold mb-4">Tổng quan doanh thu</h5>
	
	                <div class="d-flex justify-content-between mb-3">
	                    <span>Tháng này</span>
	                    <span class="fw-bold">
	                        <c:out value="${currentMonthRevenue}" />₫
	                    </span>
	                </div>
	                <div class="d-flex justify-content-between mb-3">
	                    <span>Tháng trước</span>
	                    <span class="fw-bold">
	                        <c:out value="${previousMonthRevenue}" />₫
	                    </span>
	                </div>
	                <div class="d-flex justify-content-between">
	                    <span>Tăng trưởng</span>
	                    <span class="fw-bold 
	                        <c:if test='${changePercent >= 0}'>text-success</c:if>
	                        <c:if test='${changePercent < 0}'>text-danger</c:if>">
	                        <c:choose>
	                            <c:when test="${changePercent >= 0}">+${changePercent}%</c:when>
	                            <c:otherwise>${changePercent}%</c:otherwise>
	                        </c:choose>
	                    </span>
	                </div>
	            </div>
	        </div>
	    </div>
	
	    <!-- Danh mục hàng đầu -->
	    <div class="col-md-6 d-flex">
	        <div class="card shadow-sm rounded-3 w-100 h-100">
	            <div class="card-body">
	                <h5 class="fw-bold mb-4">Danh mục hàng đầu</h5>
	
	                <c:forEach var="cat" items="${topCategories}" varStatus="i">
	                    <div class="d-flex justify-content-between mb-3">
	                        <span>${cat.name}</span>
	                        <span class="fw-bold">
	                            <fmt:formatNumber value="${cat.percent}" maxFractionDigits="1"/>%
	                        </span>
	                    </div>
	                </c:forEach>
	            </div>
	        </div>
	    </div>
	</div>

    <div class="row g-3">
        <!--  Biểu đồ phương thức thanh toán -->
        <div class="col-md-6">
            <div class="card shadow-sm">
                <div class="card-body">
                    <h6 class="card-title">Phương thức thanh toán</h6>
                    <canvas id="paymentChart"></canvas>
                </div>
            </div>
        </div>

        <!--  Biểu đồ hoàn/hủy -->
	<div class="col-md-6">
	    <div class="card shadow-sm">
	        <div class="card-body">
	            <div class="d-flex justify-content-between align-items-center mb-3">
	                <h6 class="card-title mb-0">Tỷ lệ hoàn / hủy đơn hàng</h6>
	
	                <!-- Bộ lọc tháng/năm -->
	                <form method="get" class="d-flex gap-2 align-items-center">
	                    <select name="month" class="form-select form-select-sm" style="width: 100px;">
	                        <c:forEach var="i" begin="1" end="12">
	                            <option value="${i}" ${i == selectedMonth ? 'selected' : ''}>Tháng ${i}</option>
	                        </c:forEach>
	                    </select>
	                    <input type="number" name="year" class="form-control form-control-sm" value="${selectedYear}" min="2020" max="2030" style="width: 90px;">
	                    <button class="btn btn-sm btn-primary" type="submit">Xem</button>
	                </form>
	            </div>
	
	             <canvas id="returnCancelChart"></canvas>

	        </div>
	    </div>
	</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    const paymentLabels = [<c:forEach var="r" items="${paymentStats}" varStatus="loop">'${r[0]}'<c:if test="${!loop.last}">,</c:if></c:forEach>];
    const paymentData = [<c:forEach var="r" items="${paymentStats}" varStatus="loop">${r[1]}<c:if test="${!loop.last}">,</c:if></c:forEach>];

    new Chart(document.getElementById('paymentChart'), {
        type: 'pie',
        data: {
            labels: paymentLabels,
            datasets: [{ data: paymentData, backgroundColor: ['#36a2eb', '#ffcd56'] }]
        },
        options: { responsive: true }
    });
</script>
<script>
const canceledCount = ${canceledCount != null ? canceledCount : 0};
const notCanceledCount = ${notCanceledCount != null ? notCanceledCount : 0};
const totalOrders = ${totalOrders != null ? totalOrders : 0};

const cancelLabels = ['Đã hủy', 'Còn lại'];
const cancelData = [canceledCount, notCanceledCount];

console.log("Canceled:", canceledCount, "NotCanceled:", notCanceledCount, "Total:", totalOrders);

new Chart(document.getElementById('returnCancelChart'), {
  type: 'pie',
  data: {
    labels: cancelLabels,
    datasets: [{
      data: cancelData,
      backgroundColor: ['#ff6384', '#36a2eb']
    }]
  },
  options: {
    responsive: true,
    plugins: {
      legend: { position: 'bottom' },
      tooltip: {
    	  callbacks: {
    	    label: function(context) {
    	      const total = totalOrders;
    	      const value = context.dataset.data[context.dataIndex]; // ✅ cách an toàn nhất
    	      const percent = total > 0 ? (value / total * 100).toFixed(1) : 0;
    	      console.log("Tooltip data:", context.label, value, percent);
    	      return `\${context.label}: \${value} đơn (\${percent}%)`;
    	    }
    	  }
    	}
    }
  }
});
</script>

