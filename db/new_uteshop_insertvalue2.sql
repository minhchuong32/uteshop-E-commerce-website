-- ============================
-- 6. PROMOTIONS
-- ============================
INSERT INTO promotions (shop_id, discount_type, value, start_date, end_date) VALUES
(1, N'percent', 10, '2025-10-01', '2025-10-31'),
(1, N'fixed', 500000, '2025-11-01', '2025-11-15'),
(2, N'percent', 15, '2025-10-05', '2025-10-20'),
(3, N'fixed', 200000, '2025-10-10', '2025-10-25');

-- ============================
-- 7. REVIEWS
-- ============================
INSERT INTO reviews (product_id, user_id, rating, comment, media_url) VALUES
(1, 1, 5, N'Sản phẩm tuyệt vời, rất đáng tiền', NULL),
(2, 1, 4, N'Máy dùng tốt, màu sắc đẹp', NULL),
(4, 1, 5, N'MacBook mạnh, pin trâu', NULL),
(7, 1, 3, N'Tai nghe ổn, âm bass chưa đủ sâu', NULL),
(10, 1, 4, N'Loa JBL âm thanh tốt', NULL);

-- ============================
-- 8. CART_ITEMS
-- ============================
INSERT INTO cart_items (user_id, product_variant_id, quantity, price) VALUES
(1, 1, 1, 25000000),
(1, 4, 1, 20000000),
(1, 7, 2, 1200000);

-- ============================
-- 9. ORDERS
-- ============================
INSERT INTO orders (user_id, total_amount, status, payment_method, created_at, address) VALUES
(1, 27000000, N'new', N'COD', GETDATE(), N'123 Đường A, TP.HCM'),
(1, 22000000, N'new', N'VNPay', GETDATE(), N'123 Đường A, TP.HCM');

-- ============================
-- 10. ORDER_DETAILS
-- ============================
INSERT INTO order_details (order_id, product_variant_id, quantity, price) VALUES
(1, 1, 1, 25000000),
(1, 7, 1, 1200000),
(2, 4, 1, 20000000);

-- ============================
-- 11. DELIVERIES
-- ============================
INSERT INTO deliveries (order_id, shipper_id, status, note_text, delivery_note, created_at) VALUES
(1, 4, N'Đang giao', N'Giao trước 17h', NULL, GETDATE()),
(2, 4, N'Đã gán', N'', NULL, GETDATE());

-- ============================
-- 12. COMPLAINTS
-- ============================
INSERT INTO complaints (order_id, user_id, title, content, status, created_at) VALUES
(1, 1, N'Sản phẩm bị trầy xước', N'Khi nhận hàng, sản phẩm bị trầy xước ở mặt lưng', N'Chờ xử lý', GETDATE());

-- ============================
-- 13. STORE_SETTINGS
-- ============================
INSERT INTO store_settings (store_name, email, hotline, address, logo, theme, cod_enabled, momo_enabled, vnpay_enabled, created_at, updated_at) VALUES
(N'UTeShop', N'support@uteshop.com', N'19001234', N'123 Đường A, TP.HCM', NULL, N'default', 1, 1, 1, GETDATE(), GETDATE());

-- ============================
-- 14. CONTACTS
-- ============================
INSERT INTO contact (userID, fullName, email, content, createdAt) VALUES
(1, N'Nguyen Van A', N'customer1@example.com', N'Tôi muốn hỏi về đơn hàng #1', GETDATE()),
(1, N'Nguyen Van A', N'customer1@example.com', N'Tôi muốn phản hồi về sản phẩm iPhone 15', GETDATE());

-- ============================
-- 15. PRODUCT_IMAGES
-- ============================
INSERT INTO ProductImage (product_id, image_url, is_main) VALUES
(1, N'/uploads/iphone15_black.png', 1),
(1, N'/uploads/iphone15_white.png', 0),
(2, N'/uploads/samsung_s23_black.png', 1),
(4, N'/uploads/macbook_pro16.png', 1),
(7, N'/uploads/bluetooth_headphone_black.png', 1);
