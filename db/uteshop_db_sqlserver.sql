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
  status VARCHAR(20) DEFAULT 'active' CHECK (status IN ('active','inactive','banned')),
  avatar VARCHAR(500) NOT NUL
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
-- Thêm bảng thông tin STORESETTINGS  [/31/8/2025]
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

-- ========================
-- Thêm bảng thông tin CONTACTS  [/28/09/2025]
-- ========================
 CREATE TABLE Contact (
    ContactID INT PRIMARY KEY IDENTITY(1,1),
    UserID INT NULL,                         -- Nếu user đã đăng nhập
    FullName NVARCHAR(100) NOT NULL,
    Email NVARCHAR(100) NOT NULL,
    Content NVARCHAR(MAX) NOT NULL,
    CreatedAt DATETIME DEFAULT GETDATE(),

    CONSTRAINT FK_Contact_User FOREIGN KEY (UserID) REFERENCES users(user_id)
);

