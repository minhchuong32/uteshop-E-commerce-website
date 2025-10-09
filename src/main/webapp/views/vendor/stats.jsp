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

</div>
