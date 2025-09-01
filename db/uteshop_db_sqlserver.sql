-- ========================
-- Tạo Database
-- ========================
CREATE DATABASE uteshop_db;
GO

USE uteshop_db;
GO

-- ========================
-- USERS
-- ========================
CREATE TABLE users (
  user_id INT IDENTITY(1,1) PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL, 
  email VARCHAR(100) NOT NULL UNIQUE,
  role VARCHAR(20) DEFAULT 'User' CHECK (role IN ('Guest','User','Vendor','Admin','Shipper')),
  status VARCHAR(20) DEFAULT 'active' CHECK (status IN ('active','inactive','banned'))
);
GO

-- ========================
-- SHOPS
-- ========================
CREATE TABLE shops (
  shop_id INT IDENTITY(1,1) PRIMARY KEY,
  user_id INT,
  name VARCHAR(100) NOT NULL,
  description NVARCHAR(MAX),
  created_at DATETIME DEFAULT GETDATE(),
  FOREIGN KEY (user_id) REFERENCES users(user_id)
);
GO

-- ========================
-- CATEGORIES
-- ========================
CREATE TABLE categories (
  category_id INT IDENTITY(1,1) PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  parent_id INT NULL,
  FOREIGN KEY (parent_id) REFERENCES categories(category_id)
);
GO

-- ========================
-- PRODUCTS
-- ========================
CREATE TABLE products (
  product_id INT IDENTITY(1,1) PRIMARY KEY,
  shop_id INT,
  category_id INT,
  name VARCHAR(100) NOT NULL,
  price DECIMAL(18,2) NOT NULL,
  stock INT DEFAULT 0,
  description NVARCHAR(MAX),
  image_url VARCHAR(255),
  FOREIGN KEY (shop_id) REFERENCES shops(shop_id),
  FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

-- ... (INSERT tương tự, không cần chỉnh nhiều ngoài dấu nháy và số)
GO

-- ========================
-- ORDERS
-- ========================
CREATE TABLE orders (
  order_id INT IDENTITY(1,1) PRIMARY KEY,
  user_id INT,
  total_amount DECIMAL(18,2),
  status VARCHAR(20) DEFAULT 'new' CHECK (status IN ('new','confirmed','shipping','delivered','canceled','returned')),
  payment_method VARCHAR(20) DEFAULT 'COD' CHECK (payment_method IN ('COD','MoMo','VNPAY')),
  created_at DATETIME DEFAULT GETDATE(),
  FOREIGN KEY (user_id) REFERENCES users(user_id)
);
GO

-- ========================
-- ORDER_DETAILS
-- ========================
CREATE TABLE order_details (
  order_detail_id INT IDENTITY(1,1) PRIMARY KEY,
  order_id INT,
  product_id INT,
  quantity INT,
  price DECIMAL(18,2),
  FOREIGN KEY (order_id) REFERENCES orders(order_id),
  FOREIGN KEY (product_id) REFERENCES products(product_id)
);
GO

-- ========================
-- REVIEWS
-- ========================
CREATE TABLE reviews (
  review_id INT IDENTITY(1,1) PRIMARY KEY,
  product_id INT,
  user_id INT,
  rating INT CHECK(rating BETWEEN 1 AND 5),
  comment NVARCHAR(MAX),
  media_url VARCHAR(255),
  FOREIGN KEY (product_id) REFERENCES products(product_id),
  FOREIGN KEY (user_id) REFERENCES users(user_id)
);
GO

-- ========================
-- PROMOTIONS
-- ========================
CREATE TABLE promotions (
  promotion_id INT IDENTITY(1,1) PRIMARY KEY,
  shop_id INT,
  discount_type VARCHAR(20) CHECK (discount_type IN ('percent','fixed')),
  value DECIMAL(18,2) NOT NULL,
  start_date DATE,
  end_date DATE,
  FOREIGN KEY (shop_id) REFERENCES shops(shop_id)
);
GO

-- ========================
-- SHIPPERS
-- ========================
CREATE TABLE shippers (
  shipper_id INT IDENTITY(1,1) PRIMARY KEY,
  user_id INT,
  name VARCHAR(100),
  phone VARCHAR(20),
  FOREIGN KEY (user_id) REFERENCES users(user_id)
);
GO

-- ========================
-- DELIVERIES
-- ========================
CREATE TABLE deliveries (
  delivery_id INT IDENTITY(1,1) PRIMARY KEY,
  shipper_id INT,
  order_id INT,
  status VARCHAR(20) DEFAULT 'assigned' CHECK (status IN ('assigned','delivering','delivered')),
  FOREIGN KEY (shipper_id) REFERENCES shippers(shipper_id),
  FOREIGN KEY (order_id) REFERENCES orders(order_id)
);
GO


-- ========================
-- USERS (10 bản ghi)
-- ========================
INSERT INTO users (username, password, email, role, status) VALUES
('admin', CONVERT(VARCHAR(255), HASHBYTES('SHA2_256','2357'),2), 'admin@ute.vn', 'Admin', 'active'),
('chuong', CONVERT(VARCHAR(255), HASHBYTES('SHA2_256','123'),2), 'chuong@ute.vn', 'User', 'active'),
('thuy', CONVERT(VARCHAR(255), HASHBYTES('SHA2_256','123'),2), 'thuy@ute.vn', 'Shipper', 'active'),
('minh', CONVERT(VARCHAR(255), HASHBYTES('SHA2_256','123'),2), 'minh@ute.vn', 'Vendor', 'active'),
('user01', CONVERT(VARCHAR(255), HASHBYTES('SHA2_256','123456'),2), 'user01@ute.vn', 'User', 'active'),
('user02', CONVERT(VARCHAR(255), HASHBYTES('SHA2_256','123456'),2), 'user02@ute.vn', 'User', 'inactive'),
('vendor02', CONVERT(VARCHAR(255), HASHBYTES('SHA2_256','vendor456'),2), 'vendor02@ute.vn', 'Vendor', 'active'),
('shipper02', CONVERT(VARCHAR(255), HASHBYTES('SHA2_256','shipper456'),2), 'shipper02@ute.vn', 'Shipper', 'active'),
('user03', CONVERT(VARCHAR(255), HASHBYTES('SHA2_256','123456'),2), 'user03@ute.vn', 'User', 'banned'),
('user04', CONVERT(VARCHAR(255), HASHBYTES('SHA2_256','123456'),2), 'user04@ute.vn', 'User', 'active');
GO

-- ========================
-- SHOPS (10 bản ghi)
-- ========================
INSERT INTO shops (user_id, name, description) VALUES
(4, 'UTE Laptop Store', N'Chuyên bán laptop chính hãng'),
(4, 'UTE Accessories', N'Phụ kiện công nghệ'),
(7, 'Gaming Zone', N'Linh kiện và thiết bị gaming'),
(7, 'Smartphone World', N'Điện thoại và tablet'),
(7, 'Sound Studio', N'Âm thanh chất lượng cao'),
(7, 'Display Hub', N'Màn hình và TV'),
(7, 'Office Gear', N'Dụng cụ văn phòng'),
(7, 'Camera Shop', N'Máy ảnh và phụ kiện'),
(7, 'Smart Home', N'Thiết bị nhà thông minh'),
(7, 'Wearable Tech', N'Đồng hồ và thiết bị đeo thông minh');
GO

-- ========================
-- CATEGORIES (10 bản ghi)
-- ========================
INSERT INTO categories (name, parent_id) VALUES
('Electronics', NULL),
('Laptops', 1),
('Accessories', 1),
('Mice', 3),
('Keyboards', 3),
('Headphones', 1),
('Monitors', 1),
('Smartphones', 1),
('Cameras', 1),
('Wearables', 1);
GO

-- ========================
-- PRODUCTS (15 bản ghi)
-- ========================
INSERT INTO products (shop_id, category_id, name, price, stock, description, image_url) VALUES
(1, 2, 'Laptop Dell XPS 13', 25000000, 5, N'Laptop cao cấp mỏng nhẹ', 'dellxps13.jpg'),
(1, 2, 'MacBook Air M2', 28000000, 7, N'Laptop Apple chip M2 hiệu năng cao', 'macbookairm2.jpg'),
(1, 2, 'Asus ROG Zephyrus', 32000000, 3, N'Laptop gaming hiệu năng cao', 'asusrog.jpg'),
(2, 4, 'Chuột Logitech MX Master', 2000000, 10, N'Chuột không dây cao cấp', 'logitechmx.jpg'),
(2, 4, 'Chuột Razer DeathAdder V2', 1500000, 20, N'Chuột gaming nổi tiếng', 'razerdeathadderv2.jpg'),
(2, 5, 'Bàn phím cơ Keychron K2', 1800000, 15, N'Bàn phím cơ bluetooth', 'keychronk2.jpg'),
(2, 5, 'Bàn phím Logitech G Pro X', 2800000, 10, N'Bàn phím cơ gaming', 'logitechgprox.jpg'),
(5, 6, 'Tai nghe Sony WH-1000XM4', 6000000, 12, N'Tai nghe chống ồn cao cấp', 'sonywh1000xm4.jpg'),
(5, 6, 'Tai nghe AirPods Pro 2', 5500000, 14, N'Tai nghe không dây Apple', 'airpodspro2.jpg'),
(6, 7, 'Màn hình LG UltraWide 34"', 9500000, 4, N'Màn hình cong 34 inch', 'lgultrawide34.jpg'),
(6, 7, 'Màn hình Samsung Odyssey G9', 28000000, 2, N'Màn hình gaming cong 49 inch', 'odysseyg9.jpg'),
(4, 8, 'iPhone 14 Pro', 32000000, 8, N'Điện thoại cao cấp của Apple', 'iphone14pro.jpg'),
(4, 8, 'Samsung Galaxy S23', 26000000, 10, N'Điện thoại flagship Samsung', 'galaxys23.jpg'),
(9, 9, 'Canon EOS R6', 52000000, 5, N'Máy ảnh không gương lật', 'canoneosr6.jpg'),
(10, 10, 'Apple Watch Series 8', 11000000, 6, N'Đồng hồ thông minh Apple', 'applewatch8.jpg');
GO

-- ========================
-- ORDERS (10 bản ghi)
-- ========================
INSERT INTO orders (user_id, total_amount, status, payment_method) VALUES
(2, 25000000, 'new', 'COD'),
(2, 3800000, 'confirmed', 'MoMo'),
(5, 6000000, 'shipping', 'VNPAY'),
(6, 9500000, 'delivered', 'COD'),
(9, 32000000, 'canceled', 'MoMo'),
(10, 1500000, 'new', 'COD'),
(2, 11000000, 'confirmed', 'VNPAY'),
(5, 1800000, 'returned', 'MoMo'),
(6, 28000000, 'shipping', 'COD'),
(2, 2000000, 'delivered', 'MoMo');
GO

-- ========================
-- ORDER_DETAILS (10 bản ghi)
-- ========================
INSERT INTO order_details (order_id, product_id, quantity, price) VALUES
(1, 1, 1, 25000000),
(2, 4, 1, 2000000),
(2, 6, 1, 1800000),
(3, 8, 1, 6000000),
(4, 10, 1, 9500000),
(5, 12, 1, 32000000),
(6, 5, 1, 1500000),
(7, 15, 1, 11000000),
(8, 6, 1, 1800000),
(9, 2, 1, 28000000);
GO

-- ========================
-- REVIEWS (10 bản ghi)
-- ========================
INSERT INTO reviews (product_id, user_id, rating, comment) VALUES
(1, 2, 5, N'Laptop chạy rất mượt'),
(4, 2, 4, N'Chuột dùng tốt nhưng hơi nặng'),
(6, 1, 5, N'Bàn phím gõ sướng, pin trâu'),
(2, 3, 4, N'MacBook đẹp nhưng giá cao'),
(8, 2, 5, N'Tai nghe chống ồn cực tốt'),
(10, 5, 4, N'Màn hình rộng, hiển thị đẹp'),
(12, 6, 5, N'iPhone 14 Pro quá đỉnh'),
(5, 9, 3, N'Chuột gaming bình thường'),
(7, 10, 5, N'Bàn phím cơ cực chất'),
(15, 2, 4, N'Apple Watch nhiều tính năng');
GO

-- ========================
-- PROMOTIONS (10 bản ghi)
-- ========================
INSERT INTO promotions (shop_id, discount_type, value, start_date, end_date) VALUES
(1, 'percent', 10, '2025-08-01', '2025-08-31'),
(2, 'fixed', 50000, '2025-08-01', '2025-08-15'),
(3, 'percent', 15, '2025-09-01', '2025-09-30'),
(4, 'percent', 5, '2025-08-10', '2025-08-20'),
(5, 'fixed', 100000, '2025-07-01', '2025-07-15'),
(6, 'percent', 20, '2025-10-01', '2025-10-31'),
(7, 'fixed', 75000, '2025-09-05', '2025-09-25'),
(8, 'percent', 8, '2025-11-01', '2025-11-15'),
(9, 'percent', 12, '2025-12-01', '2025-12-31'),
(10, 'fixed', 200000, '2025-08-15', '2025-08-30');
GO

-- ========================
-- SHIPPERS (10 bản ghi)
-- ========================
INSERT INTO shippers (user_id, name, phone) VALUES
(3, N'Shipper Nguyễn Văn A', '0909123456'),
(8, N'Shipper Trần Văn B', '0909345678'),
(3, N'Shipper Lê Văn C', '0909567890'),
(8, N'Shipper Phạm Văn D', '0909789012'),
(3, N'Shipper Nguyễn Thị E', '0909234567'),
(8, N'Shipper Trần Thị F', '0909345679'),
(3, N'Shipper Lê Thị G', '0909567891'),
(8, N'Shipper Phạm Thị H', '0909789013'),
(3, N'Shipper Hoàng Văn I', '0909234568'),
(8, N'Shipper Đỗ Văn K', '0909345680');
GO

-- ========================
-- DELIVERIES (10 bản ghi)
-- ========================
INSERT INTO deliveries (shipper_id, order_id, status) VALUES
(1, 1, 'assigned'),
(1, 2, 'delivering'),
(2, 3, 'delivered'),
(3, 4, 'assigned'),
(4, 5, 'delivering'),
(5, 6, 'delivered'),
(6, 7, 'assigned'),
(7, 8, 'delivering'),
(8, 9, 'delivered'),
(9, 10, 'assigned');
GO


-- ========================
-- Thêm bảng thông tin settings cho admin (chuong/31/8/2025)
-- ========================

CREATE TABLE StoreSettings (
    id INT IDENTITY(1,1) PRIMARY KEY,          -- Khóa chính
    store_name NVARCHAR(255) NOT NULL,         -- Tên cửa hàng
    email NVARCHAR(255) NULL,                  -- Email liên hệ
    hotline NVARCHAR(50) NULL,                 -- Hotline
    address NVARCHAR(500) NULL,                -- Địa chỉ cửa hàng
    logo NVARCHAR(500) NULL,                   -- Đường dẫn logo
    theme NVARCHAR(50) DEFAULT 'default',      -- Giao diện (default, dark, light...)
    cod_enabled BIT DEFAULT 1,                 -- Cho phép COD
    momo_enabled BIT DEFAULT 0,                -- Cho phép MoMo
    vnpay_enabled BIT DEFAULT 0,               -- Cho phép VNPAY
    created_at DATETIME DEFAULT GETDATE(),     -- Ngày tạo
    updated_at DATETIME DEFAULT GETDATE()      -- Ngày cập nhật
);


INSERT INTO StoreSettings 
(store_name, email, hotline, address, logo, theme, cod_enabled, momo_enabled, vnpay_enabled)
VALUES 
(N'UteShop', N'support@uteshop.com', N'0123 456 789', 
 N'123 Nguyễn Văn Bảo, Q. Gò Vấp, TP.HCM',
 N'/assets/images/logo.png', 'default', 1, 1, 0);
