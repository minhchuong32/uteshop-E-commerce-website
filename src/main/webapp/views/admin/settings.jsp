<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container-fluid">
    <h3 class="mb-4 fw-bold">Cài đặt hệ thống</h3>

    <div class="card shadow-sm mb-4">
        <div class="card-header fw-bold bg-light">
            Thông tin cửa hàng
        </div>
        <div class="card-body">
            <form>
                <div class="mb-3">
                    <label class="form-label">Tên cửa hàng</label>
                    <input type="text" class="form-control" name="store_name" value="UteShop">
                </div>
                <div class="mb-3">
                    <label class="form-label">Email liên hệ</label>
                    <input type="email" class="form-control" name="email" value="support@uteshop.com">
                </div>
                <div class="mb-3">
                    <label class="form-label">Hotline</label>
                    <input type="text" class="form-control" name="hotline" value="0123 456 789">
                </div>
                <div class="mb-3">
                    <label class="form-label">Địa chỉ</label>
                    <textarea class="form-control" name="address" rows="2">123 Nguyễn Văn Bảo, Q. Gò Vấp, TP.HCM</textarea>
                </div>
                <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
            </form>
        </div>
    </div>

    <div class="card shadow-sm mb-4">
        <div class="card-header fw-bold bg-light">
            Cấu hình thanh toán
        </div>
        <div class="card-body">
            <form>
                <div class="form-check form-switch mb-2">
                    <input class="form-check-input" type="checkbox" id="cod" checked>
                    <label class="form-check-label" for="cod">Thanh toán khi nhận hàng (COD)</label>
                </div>
                <div class="form-check form-switch mb-2">
                    <input class="form-check-input" type="checkbox" id="momo" checked>
                    <label class="form-check-label" for="momo">Thanh toán qua MoMo</label>
                </div>
                <div class="form-check form-switch mb-2">
                    <input class="form-check-input" type="checkbox" id="vnpay">
                    <label class="form-check-label" for="vnpay">Thanh toán qua VNPAY</label>
                </div>
                <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
            </form>
        </div>
    </div>

    <div class="card shadow-sm">
        <div class="card-header fw-bold bg-light">
            Cấu hình giao diện
        </div>
        <div class="card-body">
            <form>
                <div class="mb-3">
                    <label class="form-label">Chọn theme</label>
                    <select class="form-select" name="theme">
                        <option value="default" selected>Mặc định</option>
                        <option value="dark">Tối</option>
                        <option value="light">Sáng</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label">Logo</label>
                    <input type="file" class="form-control" name="logo">
                </div>
                <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
            </form>
        </div>
    </div>
</div>
