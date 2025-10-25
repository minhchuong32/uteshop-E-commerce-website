## Bước 1: Chạy Maven Clean (Dọn dẹp thư mục target)
Trước tiên, hãy đảm bảo thư mục target được dọn sạch.

Mở một terminal hoặc Command Prompt. (chuột phải project -> show in local terminal)

Gõ lệnh: mvn clean

##Bước 2: Xóa Project khỏi STS (Quan trọng!)
Đây là bước quan trọng nhất. Chúng ta chỉ xóa project khỏi giao diện làm việc của STS, chứ không xóa code.

Trong STS, tại cửa sổ "Package Explorer", nhấn chuột phải vào project uteshop-servlet của bạn.

Chọn Delete.

Một hộp thoại sẽ hiện ra. TUYỆT ĐỐI KHÔNG đánh dấu vào ô 'Delete project contents on disk (cannot be undone)'.

Hãy để trống ô này và nhấn OK.

Sau bước này, project sẽ biến mất khỏi STS, nhưng toàn bộ code của bạn vẫn an toàn trong thư mục G:\sts-workspace\uteshop-servlet.

##Bước 3: Xóa file Metadata cũ
Để chắc chắn STS "quên" hết mọi thứ, bạn có thể vào thư mục project và xóa các file cấu hình cũ của IDE.

Mở File Explorer, đi đến: G:\sts-workspace\uteshop-servlet (đường dẫn đến project theo máy tính của bạn)

Xóa các file và thư mục sau (nếu chúng tồn tại):

.project (file)

.classpath (file)

.settings (thư mục)

##Bước 4: Import lại Project vào STS
Bây giờ chúng ta sẽ đưa project trở lại STS như một project Maven mới.

Trong STS, vào menu File -> Import -> Maven -> Existing Maven Projects -> Browser -> chọn folder project : uteshop-servlet 
=> Finish


##Bước 5: Kiểm tra và Chờ Build
Sau khi nhấn Finish, STS sẽ đọc pom.xml và bắt đầu tải lại các dependency (thư viện) và build lại project. Quá trình này có thể mất vài giây đến một phút.


Kiểm tra cuối cùng (Rất quan trọng): Sau khi import, hãy chuột phải vào project một lần nữa, chọn Properties:

Vào mục Project Facets: Đảm bảo Dynamic Web Module là 5.0 (hoặc 6.0) và Java là 22.

Vào mục Targeted Runtimes: Đảm bảo bạn đã chọn một runtime (server) tương thích như Tomcat 10.1 (chứ không phải Tomcat 9).