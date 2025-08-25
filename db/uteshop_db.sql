-- Tạo Database
-- CREATE DATABASE uteshop_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE uteshop_db;

-- ========================
-- USERS
-- ========================
CREATE TABLE users (
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL, 
  email VARCHAR(100) NOT NULL UNIQUE,
  role ENUM('Guest','User','Vendor','Admin','Shipper') DEFAULT 'User',
  status ENUM('active','inactive','banned') DEFAULT 'active'
);

INSERT INTO users (username, password, email, role, status) VALUES
('admin', SHA2('2357',256), 'admin@ute.vn', 'Admin', 'active'),
('chuong', SHA2('123',256), 'chuong@ute.vn', 'User', 'active'),
('thuy', SHA2('123',256), 'thuy@ute.vn', 'Shipper', 'active'),
('minh', SHA2('123',256), 'minh@ute.vn', 'Vendor', 'active'),
('user01', SHA2('123456',256), 'user01@ute.vn', 'User', 'active'),
('user02', SHA2('123456',256), 'user02@ute.vn', 'User', 'inactive'),
('vendor02', SHA2('vendor456',256), 'vendor02@ute.vn', 'Vendor', 'active'),
('shipper02', SHA2('shipper456',256), 'shipper02@ute.vn', 'Shipper', 'active'),
('user03', SHA2('123456',256), 'user03@ute.vn', 'User', 'banned'),
('user04', SHA2('123456',256), 'user04@ute.vn', 'User', 'active');
-- ========================
-- SHOPS
-- ========================
CREATE TABLE shops (
  shop_id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  name VARCHAR(100) NOT NULL,
  description TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(user_id)
);

INSERT INTO shops (user_id, name, description) VALUES
(3, 'UTE Laptop Store', 'Chuyên bán laptop chính hãng'),
(3, 'UTE Accessories', 'Phụ kiện công nghệ'),
(7, 'Gaming Zone', 'Linh kiện và thiết bị gaming'),
(7, 'Smartphone World', 'Điện thoại và tablet'),
(7, 'Sound Studio', 'Âm thanh chất lượng cao'),
(7, 'Display Hub', 'Màn hình và TV'),
(7, 'Office Gear', 'Dụng cụ văn phòng'),
(7, 'Camera Shop', 'Máy ảnh và phụ kiện'),
(7, 'Smart Home', 'Thiết bị nhà thông minh'),
(7, 'Wearable Tech', 'Đồng hồ và thiết bị đeo thông minh');
-- ========================
-- CATEGORIES
-- ========================
CREATE TABLE categories (
  category_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  parent_id INT,
  FOREIGN KEY (parent_id) REFERENCES categories(category_id)
);

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

-- ========================
-- PRODUCTS
-- ========================
CREATE TABLE products (
  product_id INT AUTO_INCREMENT PRIMARY KEY,
  shop_id INT,
  category_id INT,
  name VARCHAR(100) NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  stock INT DEFAULT 0,
  description TEXT,
  image_url VARCHAR(255),
  FOREIGN KEY (shop_id) REFERENCES shops(shop_id),
  FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

INSERT INTO products (shop_id, category_id, name, price, stock, description, image_url) VALUES
(1, 2, 'Laptop Dell XPS 13', 25000000, 5, 'Laptop cao cấp mỏng nhẹ', 'dellxps13.jpg'),
(2, 4, 'Chuột Logitech MX Master', 2000000, 10, 'Chuột không dây cao cấp', 'logitechmx.jpg'),
(2, 5, 'Bàn phím cơ Keychron K2', 1800000, 15, 'Bàn phím cơ bluetooth', 'keychronk2.jpg'),
(1, 2, 'MacBook Air M2', 28000000, 7, 'Laptop Apple chip M2 hiệu năng cao', 'macbookairm2.jpg'),
(5, 6, 'Tai nghe Sony WH-1000XM4', 6000000, 12, 'Tai nghe chống ồn cao cấp', 'sonywh1000xm4.jpg'),
(6, 7, 'Màn hình LG UltraWide 34"', 9500000, 4, 'Màn hình cong 34 inch', 'lgultrawide34.jpg'),
(4, 8, 'iPhone 14 Pro', 32000000, 8, 'Điện thoại cao cấp của Apple', 'iphone14pro.jpg'),
(2, 4, 'Chuột Razer DeathAdder V2', 1500000, 20, 'Chuột gaming nổi tiếng', 'razerdeathadderv2.jpg'),
(3, 5, 'Bàn phím Logitech G Pro X', 2800000, 10, 'Bàn phím cơ gaming', 'logitechgprox.jpg'),
(10, 10, 'Apple Watch Series 8', 11000000, 6, 'Đồng hồ thông minh Apple', 'applewatch8.jpg');


-- ========================
-- ORDERS
-- ========================
CREATE TABLE orders (
  order_id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  total_amount DECIMAL(10,2),
  status ENUM('new','confirmed','shipping','delivered','canceled','returned') DEFAULT 'new',
  payment_method ENUM('COD','MoMo','VNPAY') DEFAULT 'COD',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(user_id)
);

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

-- ========================
-- ORDER_DETAILS
-- ========================
CREATE TABLE order_details (
  order_detail_id INT AUTO_INCREMENT PRIMARY KEY,
  order_id INT,
  product_id INT,
  quantity INT,
  price DECIMAL(10,2),
  FOREIGN KEY (order_id) REFERENCES orders(order_id),
  FOREIGN KEY (product_id) REFERENCES products(product_id)
);

INSERT INTO order_details (order_id, product_id, quantity, price) VALUES
(1, 1, 1, 25000000),
(2, 2, 1, 2000000),
(2, 3, 1, 1800000),
(3, 5, 1, 6000000),
(4, 6, 1, 9500000),
(5, 7, 1, 32000000),
(6, 8, 1, 1500000),
(7, 10, 1, 11000000),
(8, 3, 1, 1800000),
(9, 4, 1, 28000000);

-- ========================
-- REVIEWS
-- ========================
CREATE TABLE reviews (
  review_id INT AUTO_INCREMENT PRIMARY KEY,
  product_id INT,
  user_id INT,
  rating INT CHECK(rating >= 1 AND rating <= 5),
  comment TEXT,
  media_url VARCHAR(255),
  FOREIGN KEY (product_id) REFERENCES products(product_id),
  FOREIGN KEY (user_id) REFERENCES users(user_id)
);

INSERT INTO reviews (product_id, user_id, rating, comment) VALUES
(1, 2, 5, 'Laptop chạy rất mượt'),
(2, 2, 4, 'Chuột dùng tốt nhưng hơi nặng'),
(3, 1, 5, 'Bàn phím gõ sướng, pin trâu'),
(4, 3, 4, 'MacBook đẹp nhưng giá cao'),
(5, 2, 5, 'Tai nghe chống ồn cực tốt'),
(6, 5, 4, 'Màn hình rộng, hiển thị đẹp'),
(7, 6, 5, 'iPhone 14 Pro quá đỉnh'),
(8, 9, 3, 'Chuột gaming bình thường'),
(9, 10, 5, 'Bàn phím cơ cực chất'),
(10, 2, 4, 'Apple Watch nhiều tính năng');

-- ========================
-- PROMOTIONS
-- ========================
CREATE TABLE promotions (
  promotion_id INT AUTO_INCREMENT PRIMARY KEY,
  shop_id INT,
  discount_type ENUM('percent','fixed') NOT NULL,
  value DECIMAL(10,2) NOT NULL,
  start_date DATE,
  end_date DATE,
  FOREIGN KEY (shop_id) REFERENCES shops(shop_id)
);

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

-- ========================
-- SHIPPERS
-- ========================
CREATE TABLE shippers (
  shipper_id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  name VARCHAR(100),
  phone VARCHAR(20),
  FOREIGN KEY (user_id) REFERENCES users(user_id)
);

INSERT INTO shippers (user_id, name, phone) VALUES
(4, 'Shipper Nguyễn Văn A', '0909123456'),
(8, 'Shipper Trần Văn B', '0909345678'),
(4, 'Shipper Lê Văn C', '0909567890'),
(8, 'Shipper Phạm Văn D', '0909789012'),
(4, 'Shipper Nguyễn Thị E', '0909234567'),
(8, 'Shipper Trần Thị F', '0909345679'),
(4, 'Shipper Lê Thị G', '0909567891'),
(8, 'Shipper Phạm Thị H', '0909789013'),
(4, 'Shipper Hoàng Văn I', '0909234568'),
(8, 'Shipper Đỗ Văn K', '0909345680');

-- ========================
-- DELIVERIES
-- ========================
CREATE TABLE deliveries (
  delivery_id INT AUTO_INCREMENT PRIMARY KEY,
  shipper_id INT,
  order_id INT,
  status ENUM('assigned','delivering','delivered') DEFAULT 'assigned',
  FOREIGN KEY (shipper_id) REFERENCES shippers(shipper_id),
  FOREIGN KEY (order_id) REFERENCES orders(order_id)
);

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
