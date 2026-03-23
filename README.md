


# 🛒 UTESHOP-SERVLET PROJECT

Dự án **Uteshop-servlet** là một ứng dụng web thương mại điện tử mini được xây dựng bằng **Java  Servlet + JSP/JSTL + Bootstrap + JPA + SQLServer + Decorator Sitemesh + JWT**, triển khai theo mô hình **MVC + DAO + Service Layer**.  
Mục tiêu: xây dựng nền tảng mua hàng và quản lý sàn thương mại điện tử Mini chuyên về các sản phẩm công nghệ

## 📂 1. Cấu trúc dự án

```
Uteshop-servlet/
│── .git/
│── pom.xml                # Quản lý dependencies Maven
│── src/
│   ├── main/
│   │   ├── java/ute/shop/
│   │   │   ├── config/		# Cấu hình kết nối và quản lý JPA
│   │   │   ├── controller/   # Servlet xử lý request
│   │   │   ├── dao/          # Interface DAO
│   │   │   ├── dao/impl/     # DAO Implementation (JDBC)
│   │   │   ├── entity/       # Chứa các lớp mô hình dữ liệu.
│   │   │   ├── service/      # Interface Service
│   │   │   ├── service/impl/ # Business logic
│   │   │   ├── utils/        # Helper class
│   │   │   └── filter/       # AuthFilter, EncodingFilter
│   │   ├── resources/  
│   │   │   ├── META-INF/   
│   │   │   │    └──persistence.xml  #Cấu hình kết nối CSDL
│   │   │   ├── vnpay_config.properties #Cấu hình kết nối VNPAY
│   │   │   │
│   │   │   └── config.properties #Cấu hình xác thực JWT
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   ├── views/    # JSP pages
│   │       │   └── web.xml   # cấu hình Servlet
│   │       ├── assets/       # CSS, JS, images
│   │       └── index.jsp
│   └── test/                 # Unit test
│
├── docs/                     # UML, báo cáo
│
└── db/							#Query thêm dữ liệu mẫu
```

# Chú thích cấu trúc: 
controller/ → Servlet nhận request từ client, gọi service.

dao/ + dao/impl/ → Tầng truy xuất DB (JPA).

model/ → POJO class, ánh xạ bảng DB.

service/ + service/impl/ → Xử lý logic nghiệp vụ (gọi dao).

utils/ → DBConnect, helper (hash password, validate...).

filter/ → Lọc request (bảo mật, encoding UTF-8).

webapp/WEB-INF/views/ → JSP 

assets/ → CSS, JS, hình ảnh.

test/ → JUnit test DAO + Service.

## ⚙️ 2. Các bước cài đặt
- Cài **SQL Server** (tham khảo hướng dẫn trên YouTube).
- Tạo database uteshopdb
- Tải mã nguồn UTEShop từ GitHub nhóm.
- Chạy script /db/uteshop_insert_db_sqlserver.sql và edit.sql để thêm data vào CSDL (hoặc sử dụng file /db/uteshopdb.bak) 
- Khởi động Tomcat --> truy cập http://localhost:8080/uteshop.
- Đăng nhập bằng tài khoản Admin mặc định:
o	Username: chuongminh3225@gmail.com
o	Password: 123
- **HƯỚNG DẪN THANH TOÁN VNPAY:**

  B1: Chọn VNPAY ở trang thanh toán

  B2: Chọn thanh toán bằng thẻ nội địa

  B3: Nhập thông tin thanh toán như sau
_Ngân hàng	NCB_

_Số thẻ	9704198526191432198_

_Tên chủ thẻ	NGUYEN VAN A_

_Ngày phát hành	07/15_

_Mật khẩu OTP	123456_

---
## 👥 3. Phân chia công việc
### **Nguyễn Thị Thanh Thùy – Vendor + Shipper**
- **Tuần 1:**
  - Vẽ và đặc tả use case liên quan đến Vendor và Shipper.  
  - Phân tích luồng nghiệp vụ cho hai actor này.  
- **Tuần 2:**
  - Vẽ lược đồ tuần tự cho các chức năng Vendor và Shipper.  
  - Thiết kế giao diện sơ bộ cho các màn hình quản lý cửa hàng và giao hàng.  
- **Tuần 3:**
  - Xây dựng các chức năng của Vendor: đăng ký cửa hàng, quản lý sản phẩm, quản lý đơn hàng.  
- **Tuần 4:**
  - Hoàn thiện chức năng thống kê doanh thu cho Vendor.  
  - Phát triển và kiểm thử nghiệp vụ Shipper: nhận đơn, cập nhật trạng thái, xác nhận hoàn tất.  
  - Tinh chỉnh giao diện liên quan.  
---
### **Nguyễn Thanh Bình Minh – User + Guest**
- **Tuần 1:**
  - Vẽ và đặc tả use case liên quan đến User và Guest.  
  - Phân tích hành vi người dùng và luồng đặt hàng.  
- **Tuần 2:**
  - Vẽ lược đồ tuần tự cho User và Guest.  
  - Thiết kế giao diện trang chính và trang sản phẩm.  
- **Tuần 3:**
  - Xây dựng chức năng Guest: xem, tìm kiếm, xem chi tiết sản phẩm.  
  - Xây dựng chức năng User: xem, tìm kiếm, xem chi tiết sản phẩm, quản lý giỏ hàng
- **Tuần 4:**
  - Phát triển các chức năng User nâng cao: đặt hàng, đánh giá/khiếu nại sản phẩm.  
  - Hoàn thiện giao diện giỏ hàng và thanh toán.  
---
### **Phạm Hàn Minh Chương – Admin + Login/Register**
- **Tuần 1:**
  - Vẽ và đặc tả use case cho Admin.  
  - Thiết kế sơ đồ cơ sở dữ liệu tổng thể (bảng, khóa, quan hệ).  
- **Tuần 2:**
  - Vẽ lược đồ tuần tự cho Admin.  
  - Xây dựng hệ thống đăng nhập, đăng ký, phân quyền người dùng.  
- **Tuần 3:**
  - Xây dựng chức năng Admin: quản lý người dùng, cửa hàng.  
  - Bắt đầu xử lý khiếu nại.  
- **Tuần 4:**
  - Hoàn thiện các chức năng quản trị.  
  - Kiểm thử hệ thống đăng nhập – phân quyền.  
  - Tối ưu và hoàn thiện cơ sở dữ liệu.  
---

## 🚀 4. Công nghệ sử dụng

**Ngôn ngữ lập trình chính**  
- **Java (JDK 22)**  
  → Xây dựng logic xử lý nghiệp vụ và các module quản lý người dùng, sản phẩm, đơn hàng,…

**Giao diện người dùng**  
- **JSP, HTML, CSS, Bootstrap 5**  
  → Thiết kế giao diện trực quan, thân thiện, tương thích đa nền tảng (PC, tablet, mobile).

**Cơ sở dữ liệu**  
- **SQL Server**  
  → Lưu trữ dữ liệu sản phẩm, người dùng, đơn hàng, khuyến mãi,…

**Truy cập dữ liệu**  
- **JPA**  
  → Kết nối, truy vấn và thao tác dữ liệu từ Java đến cơ sở dữ liệu.

**Template Decorator**  
- **Sitemesh**  
  → Quản lý layout tổng thể cho các trang JSP, đảm bảo tính nhất quán về giao diện.

**Phân tích & Thiết kế hệ thống**  
- **Enterprise Architect**  
  → Vẽ các sơ đồ UML: Use Case, Lược đồ lớp, Lược đồ tuần tự, Lược đồ hoạt động,…

**Công cụ phát triển**  
- **Spring Tool Suite (STS)**  
  → IDE chính để lập trình, quản lý project và chạy thử ứng dụng.

**Công cụ quản lý mã nguồn**  
- **Git + GitHub**  
  → Hỗ trợ làm việc nhóm, kiểm soát phiên bản và triển khai thử nghiệm.

## 🚀 5. Target
### Làm chuẩn theo yêu cầu cơ bản
_Chức năng chung_
- Tìm kiếm và lọc sản phẩm.  
- Đăng ký tài khoản có gửi mã OTP kích hoạt qua Email.  
- Đăng nhập, đăng xuất, quên mật khẩu (gửi mã OTP qua Email).  
- Mật khẩu được mã hóa để đảm bảo an toàn.
--- 
**Guest (Khách truy cập)**
- Xem trang chủ, hiển thị các sản phẩm bán chạy (trên 10 sản phẩm) của các shop, sắp xếp theo doanh số giảm dần.  
- Xem chi tiết sản phẩm nhưng không thể mua hoặc đánh giá.  
**User (Người dùng)**
- Truy cập trang chủ và trang sản phẩm theo danh mục.  
- Hiển thị danh sách 20 sản phẩm mới, bán chạy, đánh giá cao, yêu thích, có phân trang hoặc lazy loading.  
- Trang hồ sơ cá nhân (Profile):  
- Quản lý thông tin người dùng.  
- Quản lý nhiều địa chỉ nhận hàng khác nhau.  
- Trang chi tiết sản phẩm: xem, thích sản phẩm, bình luận và đánh giá.  
- Giỏ hàng được lưu trên database.  
- Thanh toán: hỗ trợ COD, VNPAY, MOMO.  
- Quản lý lịch sử mua hàng theo trạng thái:  
  - Đơn hàng mới  
  - Đã xác nhận  
  - Đang giao  
  - Đã giao  
  - Đã hủy  
  - Trả hàng / Hoàn tiền  
- Tương tác sản phẩm:  
  - Thích sản phẩm.  
  - Xem lại sản phẩm đã xem.  
  - Đánh giá sản phẩm đã mua.  
  - Bình luận sản phẩm (tối thiểu 50 ký tự, có thể kèm hình ảnh / video).  
  - Chọn và sử dụng mã giảm giá khi thanh toán.
---
**Vendor (Chủ cửa hàng / Seller)**
- Có toàn bộ quyền của User.  
- Đăng ký shop và quản lý trang chủ của shop.  
- Quản lý sản phẩm của mình (thêm, sửa, xóa, ẩn/hiện).  
- Quản lý đơn hàng của shop theo trạng thái:  
  - Đơn hàng mới  
  - Đã xác nhận  
  - Đang giao  
  - Đã giao  
  - Đã hủy  
  - Trả hàng / Hoàn tiền  
- Tạo chương trình khuyến mãi cho sản phẩm.  
- Thống kê doanh thu và hiệu suất bán hàng của shop.  
---
**Admin (Quản trị viên)**
- Tìm kiếm và quản lý người dùng.  
- Quản lý sản phẩm của từng shop.  
- Quản lý danh mục sản phẩm.  
- Quản lý chiết khấu của ứng dụng cho các shop.  
- Quản lý chương trình khuyến mãi chung:  
  - Giảm % giá sản phẩm.  
  - Giảm phí vận chuyển.  
- Quản lý nhà vận chuyển:  
  - Tên nhà vận chuyển.  
  - Phí vận chuyển.  
---
**Shipper (Người giao hàng)**
- Quản lý đơn hàng được phân công giao.
- Cập nhật trạng thái giao hàng. 
- Thống kê số lượng đơn hàng được giao.

### Điểm cộng sáng tạo (đã thực hiện)
- Tích hợp thanh toán giả lập: VNPAY
- Xuất file pdf báo cáo theo tiêu chí
- Sử dụng websocket trong liên hệ giữa admin và user/guest
- Ứng dụng chatbot hỏi đáp cho guest/user
- Đăng nhập/Đăng ký bằng tài khoản google


