package ute.shop.utils;

public class Constant {
	public static final String SESSION_EMAIL = "email";
	public static final String COOKIE_REMEMBER = "email";
	public static final String REGISTER = "/views/auth/register.jsp";
	public static final String LOGIN = "/views/auth/login.jsp";
	 // Đường dẫn thư mục thực tế trên máy (lưu file thật)
    public static final String UPLOAD_DIRECTORY = "G:/uploads";

    // URL mapping để hiển thị ảnh (Tomcat sẽ serve thư mục /uploads)
    public static final String UPLOAD_URL = "/uploads";

    // Ảnh mặc định (nằm trong assets/images)
    public static final String DEFAULT_AVATAR = "/assets/images/default_avatar.png";
}
