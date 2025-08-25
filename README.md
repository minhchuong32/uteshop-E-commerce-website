
 # Chú thích cấu trúc: 
 controller/ → Servlet nhận request từ client, gọi service.

dao/ + dao/impl/ → Tầng truy xuất DB (JDBC).

model/ → POJO class, ánh xạ bảng DB.

service/ + service/impl/ → Xử lý logic nghiệp vụ (gọi dao).

utils/ → DBConnect, helper (hash password, validate...).

filter/ → Lọc request (bảo mật, encoding UTF-8).

webapp/WEB-INF/views/ → JSP (theo module: auth, product, order...).

assets/ → CSS, JS, hình ảnh.

test/ → JUnit test DAO + Service.



# 🛒 UTESHOP-SERVLET PROJECT

Dự án **Uteshop-servlet** là một ứng dụng web thương mại điện tử mini được xây dựng bằng **Java Servlet/JSP + JDBC + MySQL**, triển khai theo mô hình **MVC + DAO + Service Layer**.  
Mục tiêu: cung cấp môi trường học tập, thực hành với công nghệ Servlet, JDBC, JSP, JSTL và kiến trúc phần mềm theo lớp.

---

## 📌 0. Cơ sở dữ liệu (Database Schema)
**Bảng chính:**
- **users**: quản lý tài khoản
- **shops**: cửa hàng của vendor
- **categories**: danh mục sản phẩm (có self-reference)
- **products**: sản phẩm
- **orders**: đơn hàng
- **order_details**: chi tiết đơn hàng
- **reviews**: đánh giá sản phẩm
- **promotions**: khuyến mãi theo shop
- **shippers**: nhân viên giao hàng
- **deliveries**: giao hàng đơn

**Quan hệ chính:**
- users (1) — (N) shops
- shops (1) — (N) products
- categories (1) — (N) products
- users (1) — (N) orders
- orders (1) — (N) order_details
- products (1) — (N) order_details
- products (1) — (N) reviews
- shops (1) — (N) promotions
- orders (1) — (1) deliveries — (1) shippers

---

## 📂 1. Cấu trúc dự án

```
Uteshop-servlet/
│── .git/
│── pom.xml                # Quản lý dependencies Maven
│── src/
│   ├── main/
│   │   ├── java/ute/shop/
│   │   │   ├── controller/   # Servlet xử lý request
│   │   │   ├── dao/          # Interface DAO
│   │   │   ├── dao/impl/     # DAO Implementation (JDBC)
│   │   │   ├── model/        # JavaBean (User, Product, Order,…)
│   │   │   ├── service/      # Interface Service
│   │   │   ├── service/impl/ # Business logic
│   │   │   ├── utils/        # DB Connect, Helper class
│   │   │   └── filter/       # AuthFilter, EncodingFilter
│   │   ├── resources/        # config (nếu có)
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   ├── views/    # JSP pages
│   │       │   └── web.xml   # cấu hình Servlet
│   │       ├── assets/       # CSS, JS, images
│   │       └── index.jsp
│   └── test/                 # Unit test
│
└── docs/                     # UML, báo cáo
```

---
```
* detail 
uteshop-servlet/
│── .git/                          # Thư mục Git (theo dõi code, không deploy)
│── pom.xml                        # Cấu hình Maven (dependencies, build)
│
│── db/
│   └── uteshop_db.sql             # File script tạo database (tables, data mẫu)
│
│── src/
│   ├── main/
│   │   ├── java/                  # Code Java (Controller, Service, DAO, Model…)
│   │   │   └── ute/shop/
│   │   │       ├── controller/     
│   │   │       │   ├── AuthController.java     # Servlet xử lý login/register/logout
│   │   │       │   ├── ProductController.java  # Servlet xử lý CRUD sản phẩm
│   │   │       │   ├── CartController.java     # Servlet giỏ hàng (add/remove/view)
│   │   │       │   ├── OrderController.java    # Servlet đặt hàng, xem đơn
│   │   │       │   └── ShopController.java     # Servlet quản lý shop (vendor)
│   │   │       │
│   │   │       ├── dao/                        # Interface cho tầng DAO
│   │   │       │   ├── IUserDao.java           # Giao tiếp DB cho User
│   │   │       │   ├── IProductDao.java        # Giao tiếp DB cho Product
│   │   │       │   ├── IOrderDao.java          # Giao tiếp DB cho Order
│   │   │       │   └── ICategoryDao.java       # Giao tiếp DB cho Category
│   │   │       │
│   │   │       ├── dao/impl/                   # Triển khai DAO
│   │   │       │   ├── UserDaoImpl.java
│   │   │       │   ├── ProductDaoImpl.java
│   │   │       │   ├── OrderDaoImpl.java
│   │   │       │   └── CategoryDaoImpl.java
│   │   │       │
│   │   │       ├── model/                      # Các class mô tả entity
│   │   │       │   ├── User.java
│   │   │       │   ├── Shop.java
│   │   │       │   ├── Category.java
│   │   │       │   ├── Product.java
│   │   │       │   ├── Order.java
│   │   │       │   ├── OrderDetail.java
│   │   │       │   ├── Review.java
│   │   │       │   ├── Promotion.java
│   │   │       │   └── Shipper.java
│   │   │       │
│   │   │       ├── service/                    # Interface cho tầng Service
│   │   │       │   ├── IUserService.java
│   │   │       │   ├── IProductService.java
│   │   │       │   └── IOrderService.java
│   │   │       │
│   │   │       ├── service/impl/               # Triển khai service (business logic)
│   │   │       │   ├── UserServiceImpl.java
│   │   │       │   ├── ProductServiceImpl.java
│   │   │       │   └── OrderServiceImpl.java
│   │   │       │
│   │   │       ├── utils/                      # Tiện ích chung
│   │   │       │   ├── DBConnect.java          # Tạo kết nối MySQL qua JDBC
│   │   │       │   ├── PasswordHash.java       # Mã hóa/so khớp password
│   │   │       │   └── Validator.java          # Hàm kiểm tra dữ liệu input
│   │   │       │
│   │   │       └── filter/                     # Bộ lọc request/response
│   │   │           ├── AuthFilter.java         # Kiểm tra login trước khi vào trang cần quyền
│   │   │           └── EncodingFilter.java     # Bắt UTF-8 cho request/response
│   │   │
│   │   ├── resources/
│   │   │   └── db.properties                   # Cấu hình DB (jdbc.url, user, password)
│   │   │
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   ├── views/                      # Chứa các JSP view
│   │       │   │   ├── auth/                   # Giao diện login/register/profile
│   │       │   │   │   ├── login.jsp
│   │       │   │   │   ├── register.jsp
│   │       │   │   │   └── profile.jsp
│   │       │   │   ├── cart/
│   │       │   │   │   └── cart.jsp            # Trang giỏ hàng
│   │       │   │   ├── product/
│   │       │   │   │   ├── list.jsp            # Danh sách sản phẩm
│   │       │   │   │   ├── detail.jsp          # Chi tiết sản phẩm
│   │       │   │   │   └── form.jsp            # Thêm/sửa sản phẩm
│   │       │   │   ├── order/
│   │       │   │   │   ├── checkout.jsp        # Trang thanh toán
│   │       │   │   │   ├── history.jsp         # Lịch sử đơn hàng
│   │       │   │   │   └── detail.jsp          # Chi tiết đơn
│   │       │   │   ├── shop/
│   │       │   │   │   ├── dashboard.jsp       # Trang chủ shop
│   │       │   │   │   └── manage-products.jsp # Quản lý sản phẩm của shop
│   │       │   │   └── admin/
│   │       │   │       └── dashboard.jsp       # Trang quản trị admin
│   │       │   │
│   │       │   ├── index.jsp                   # Trang chủ
│   │       │   ├── layout.jsp                  # Layout chung (header/footer)
│   │       │   └── web.xml                     # Cấu hình Servlet, Filter, Mapping
│   │       │
│   │       ├── assets/                         # Tài nguyên tĩnh
│   │       │   ├── css/                        # CSS
│   │       │   ├── js/                         # Javascript
│   │       │   └── images/                     # Hình ảnh                    
│   │
│   └── test/                                  
│       ├── UserDaoTest.java
│       ├── ProductDaoTest.java
│       └── OrderServiceTest.java
│
└── docs/                                     
    ├── diagrams/ (UML, Use Case, ERD)
    └── report.docx (báo cáo nộp GV)
```

## ⚙️ 2. Thiết lập Database

1. Cài **MySQL** (tham khảo hướng dẫn trên YouTube).
2. Tạo database:
```sql
CREATE DATABASE uteshop_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```
3. Import file SQL:
```bash
cd db
mysql -u root -p uteshop_db < uteshop_db.sql
```
4. Hoặc trong MySQL Shell:
```sql
\connect root@localhost:3306
use uteshop_db;
source path_file_sql;
```

---

## 👥 3. Phân chia công việc

### 📍 **Chương (Tài khoản & Quản lý người dùng)**
- **Tuần 1:** Đăng ký / Đăng nhập (Servlet + JSP + JDBC), session + cookie, hash password.
- **Tuần 2:** Phân quyền (Guest/User/Vendor/Admin/Shipper), AuthFilter.
- **Tuần 3:** Admin CRUD User, Remember Me.
- **Tuần 4:** Fix bug, viết báo cáo.

**File chính:**
```
controller/AuthController.java
dao/IUserDao.java, dao/impl/UserDaoImpl.java
model/User.java, Shipper.java
service/IUserService.java, service/impl/UserServiceImpl.java
filter/AuthFilter.java, filter/EncodingFilter.java
utils/PasswordHash.java, Validator.java
views/auth/login.jsp, register.jsp, profile.jsp
test/UserDaoTest.java
```

---

### 📍 **Thùy (Sản phẩm & Shop/Vendor)**
- **Tuần 1:** Thiết kế bảng, CRUD sản phẩm.
- **Tuần 2:** Upload ảnh, phân loại, phân trang.
- **Tuần 3:** Quản lý Shop/Vendor, Admin quản lý danh mục.
- **Tuần 4:** Tìm kiếm nâng cao, kiểm thử + UI.

**File chính:**
```
controller/ProductController.java, ShopController.java
dao/IProductDao.java, ICategoryDao.java
dao/impl/ProductDaoImpl.java, CategoryDaoImpl.java
model/Product.java, Category.java, Shop.java
service/IProductService.java, service/impl/ProductServiceImpl.java
views/product/list.jsp, detail.jsp, form.jsp
views/shop/dashboard.jsp, manage-products.jsp
test/ProductDaoTest.java
```

---

### 📍 **Minh (Đơn hàng & Giỏ hàng & Thanh toán)**
- **Tuần 1:** Giỏ hàng (session-based).
- **Tuần 2:** Checkout + chọn phương thức thanh toán, quản lý đơn hàng.
- **Tuần 3:** Fake Payment (COD, MoMo, VNPAY), Review & Rating.
- **Tuần 4:** Giao hàng (Shipper update), Dashboard báo cáo.

**File chính:**
```
controller/CartController.java, OrderController.java
dao/IOrderDao.java, dao/impl/OrderDaoImpl.java
model/Order.java, OrderDetail.java, Review.java, Promotion.java
service/IOrderService.java, service/impl/OrderServiceImpl.java
views/order/checkout.jsp, history.jsp, detail.jsp
views/admin/dashboard.jsp
test/OrderServiceTest.java
```

---

## 📑 4. Tài liệu & Báo cáo

- **docs/diagrams/** → UML, Use Case, ERD, Sequence.
- **docs/report.docx** → Báo cáo cuối kỳ.

---

## 🚀 5. Công nghệ sử dụng

- Java Servlet + JSP + JSTL
- JDBC + MySQL
- Maven (quản lý dependencies)
- Bootstrap + jQuery (UI)
- JUnit (Unit Test)
- GitHub/GitLab (quản lý code)

## 🚀 6. Target

🔹 1. Làm chuẩn theo yêu cầu cơ bản

Quản lý tài khoản: Đăng ký/Đăng nhập (session + cookie), phân quyền (Guest/User/Vendor/Admin/Shipper).

Quản lý sản phẩm: CRUD sản phẩm, hình ảnh, mô tả, phân loại theo danh mục.

Giỏ hàng + Đặt hàng: Thêm vào giỏ, cập nhật số lượng, thanh toán, chọn phương thức thanh toán.

Quản lý đơn hàng: Người dùng xem lịch sử đơn hàng, admin/vendor quản lý trạng thái đơn hàng.

Quản lý shop/vendor: Mỗi vendor có thể đăng bán sản phẩm riêng.

Review & Rating: Người mua đánh giá sản phẩm (giúp tăng tính chân thực).

👉 Đây là mức nền – nếu làm tốt (giao diện rõ ràng, DB chặt chẽ, code sạch) thì đã đủ qua môn.

🔹 2. Điểm cộng kỹ thuật (giúp thầy/cô đánh giá cao hơn)

Sử dụng Maven + Servlet + JSP/JSTL theo đúng mô hình MVC.

JDBC + MySQL với DAO pattern rõ ràng.

Session + Cookie + Filter (ghi nhớ đăng nhập, chặn trang không đúng quyền).

Upload ảnh sản phẩm (multipart/form-data).

Hash mật khẩu (MD5/SHA-256) thay vì lưu plain text.

Phân trang (Pagination) sản phẩm.

🔹 3. Điểm cộng giao diện

Dùng Bootstrap để làm UI responsive.

Giao diện chia layout: header (menu), sidebar (danh mục), body (danh sách sản phẩm), footer.

Trang quản trị riêng cho Admin (quản lý users, shops, orders, reports).

🔹 4. Điểm cộng sáng tạo (tùy chọn)

Tích hợp thanh toán giả lập: (COD, MoMo, VNPAY – chỉ cần fake flow, không cần cổng thật).

Hệ thống khuyến mãi (Promotion): giảm giá theo % hoặc số tiền cố định.

Hệ thống giao hàng (Delivery): Shipper nhận đơn và cập nhật trạng thái.

Tìm kiếm nâng cao: theo tên, giá, category.

Dashboard báo cáo: Doanh thu theo tháng, số lượng đơn hàng, top sản phẩm bán chạy (chart JS).

