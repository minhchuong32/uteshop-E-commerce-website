package ute.shop.utils;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Tiện ích xác thực OAuth 2.0 Google và lấy thông tin người dùng
 */
public class GoogleOAuthUtils {
	
	// Khai báo các biến là private static final
	private static final String CLIENT_ID;
	private static final String CLIENT_SECRET;
	private static final String REDIRECT_URI;

	private static final List<String> SCOPES = Arrays.asList(
			"openid", 
			"https://www.googleapis.com/auth/userinfo.email",
			"https://www.googleapis.com/auth/userinfo.profile"
	);

	private static final NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final JacksonFactory JSON_FACTORY = new JacksonFactory(); // Cách khởi tạo mới, không bị deprecated
	
	private static final GoogleAuthorizationCodeFlow FLOW;

	// Sử dụng khối khởi tạo static để đọc file config
	static {
		try {
			Properties properties = new Properties();
			// Dùng getResourceAsStream để an toàn hơn
			InputStream input = GoogleOAuthUtils.class.getClassLoader().getResourceAsStream("config.properties");
			if (input == null) {
				throw new RuntimeException("Sorry, unable to find config.properties");
			}
			properties.load(input);

			CLIENT_ID = properties.getProperty("CLIENT_ID");
			CLIENT_SECRET = properties.getProperty("CLIENT_SECRET");
			REDIRECT_URI = properties.getProperty("REDIRECT_URI");
			
			// Khởi tạo FLOW ở đây sau khi đã có CLIENT_ID và SECRET
			FLOW = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, SCOPES)
					.setAccessType("offline")
					.build();

		} catch (IOException e) {
			// Nếu không đọc được file config, ứng dụng không thể chạy đúng
			throw new IllegalStateException("Failed to load Google OAuth properties", e);
		}
	}


	/** Tạo URL chuyển hướng đăng nhập Google */
	public static String getAuthorizationUrl() {
		return FLOW.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
	}

	/** Lấy thông tin người dùng từ authorization code */
	public static GoogleUserInfo getUserInfo(String code) throws IOException {
		// 1. Đổi authorization code lấy access token
		GoogleTokenResponse tokenResponse = FLOW.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();

		// 2. Tạo Credential để gọi API Google
		Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod())
				.setAccessToken(tokenResponse.getAccessToken());

		// 3. Tạo dịch vụ Oauth2
		Oauth2 oauth2 = new Oauth2.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName("uteshop")
				.build();

		// 4. Lấy thông tin người dùng
		Userinfo userinfo = oauth2.userinfo().get().execute();

		return new GoogleUserInfo(userinfo.getId(), userinfo.getEmail(), userinfo.getName(), userinfo.getPicture());
	}

	/** Lớp chứa thông tin người dùng Google */
	public static class GoogleUserInfo {
		private final String id;
		private final String email;
		private final String name;
		private final String picture;

		public GoogleUserInfo(String id, String email, String name, String picture) {
			this.id = id;
			this.email = email;
			this.name = name;
			this.picture = picture;
		}

		public String getId() { return id; }
		public String getEmail() { return email; }
		public String getName() { return name; }
		public String getPicture() { return picture; }
	}
}