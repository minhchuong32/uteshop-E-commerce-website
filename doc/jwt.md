## 0 Luồng hoạt động:
Đăng nhập: Người dùng gửi email và mật khẩu. Server xác thực thông tin.

Tạo Token: Nếu xác thực thành công, server sẽ tạo một chuỗi JWT chứa thông tin của người dùng (ví dụ: email, vai trò) 
và ký bằng một khóa bí mật (SECRET_KEY).

Gửi Token cho Client: Server gửi chuỗi JWT này về cho client (trình duyệt). Thay vì lưu vào HttpSession trên server,
 chúng ta sẽ lưu nó vào một Cookie an toàn (HttpOnly).

Xác thực các yêu cầu sau: Với mỗi yêu cầu tiếp theo đến các trang cần bảo vệ (ví dụ: /admin/home), trình duyệt sẽ 
tự động gửi kèm cookie chứa JWT.

Filter xác thực: Một Filter trên server sẽ chặn các yêu cầu này lại, đọc JWT từ cookie, giải mã và xác thực chữ ký
 bằng SECRET_KEY. Nếu token hợp lệ, filter sẽ cho phép yêu cầu đi tiếp tới Servlet tương ứng. Nếu không, filter sẽ từ chối truy cập.
## FIX CODE 
## 1. Lấy thông tin User (Quan trọng nhất) 🧑‍💻
Đây là thay đổi phổ biến nhất. JwtSecurityFilter đã tự động xác thực và đặt thông tin user vào mỗi request.

Code cũ (Không dùng nữa):

Java

HttpSession session = req.getSession();
User user = (User) session.getAttribute("account");
✅ Code mới (Cách làm chuẩn):

Java

User user = (User) req.getAttribute("account");
Lý do: Filter của chúng ta đã làm hết việc nặng nhọc. Bạn chỉ cần lấy đối tượng User từ request attribute là được.

## 2. Gửi thông báo tạm thời (Flash Messages) 💬
Chúng ta không dùng session để lưu thông báo tạm thời nữa (ví dụ: "Cập nhật thành công!"). Thay vào đó, chúng ta sẽ truyền một mã trạng thái qua URL.

Bước A: Khi gửi thông báo (thường ở doPost)
Code cũ:

Java

session.setAttribute("successMessage", "Cập nhật thành công!");
resp.sendRedirect(req.getContextPath() + "/admin/products");
✅ Code mới:

Java

// Gắn mã trạng thái vào URL khi chuyển hướng
resp.sendRedirect(req.getContextPath() + "/admin/products?status=update_success");
Bước B: Khi nhận và hiển thị (thường ở doGet)
Code cũ:

Java

String msg = (String) session.getAttribute("successMessage");
req.setAttribute("message", msg);
session.removeAttribute("successMessage");
✅ Code mới:

Java

// Đọc mã trạng thái từ URL và "dịch" nó
String status = req.getParameter("status");
if ("update_success".equals(status)) {
    req.setAttribute("message", "Cập nhật sản phẩm thành công!");
}
## 3. Đăng xuất 🚪
Để đăng xuất, chỉ cần chuyển hướng người dùng đến URL /logout. LogoutController sẽ tự động xóa cookie và kết thúc phiên.

✅ Cách làm:

Java

<a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
## 📝 Tóm Lại
Tuyệt đối không dùng req.getSession() cho việc lấy thông tin đăng nhập hoặc gửi thông báo nữa.

Luôn lấy User qua req.getAttribute("account").

Truyền các thông báo tạm thời qua tham số URL (?status=...).