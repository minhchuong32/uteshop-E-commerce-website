package ute.shop.controller.admin;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.shop.entity.Contact;
import ute.shop.service.IContactService;
import ute.shop.service.impl.ContactServiceImpl;
import ute.shop.utils.SendMail;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = { "/admin/contacts", "/admin/contacts/reply", "/admin/contacts/delete" })
public class ContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final IContactService contactService = new ContactServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		if (url.contains("/delete")) {
			deleteContact(req, resp);
		} else if (url.contains("/reply")) {
			showReplyForm(req, resp);
		} else {
			listContacts(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		if (url.contains("/reply")) {
			sendReply(req, resp);
		}
	}

	private void listContacts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Contact> contacts = contactService.findAll();
		req.setAttribute("contacts", contacts);

		// Sitemesh integration
		req.setAttribute("page", "contacts");
		req.setAttribute("view", "/views/admin/contacts/list.jsp");

		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp");
		rd.forward(req, resp);
	}

	private void showReplyForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			int contactId = Integer.parseInt(req.getParameter("id"));
			Contact contact = contactService.findById(contactId);
			req.setAttribute("contact", contact);

			// Sitemesh integration
			req.setAttribute("page", "contacts");
			req.setAttribute("view", "/views/admin/contacts/form.jsp");

			RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp");
			rd.forward(req, resp);
		} catch (NumberFormatException e) {
			resp.sendRedirect(req.getContextPath() + "/admin/contacts?error=invalidId");
		}
	}

	private void sendReply(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("UTF-8");
		try {
			// Lấy các tham số từ form
			int contactId = Integer.parseInt(req.getParameter("contactId"));
			String recipientEmail = req.getParameter("email");
			String subject = req.getParameter("subject");
			String adminReplyContent = req.getParameter("body");

			// Lấy lại thông tin đầy đủ của liên hệ từ DB
			Contact originalContact = contactService.findById(contactId);
			if (originalContact == null) {
				resp.sendRedirect(req.getContextPath() + "/admin/contacts?error=notFound");
				return;
			}

			// =================================================================
			// == BẮT ĐẦU TẠO URL TUYỆT ĐỐI CHO LOGO ==
			// =================================================================
			// Cách 2: Host trên server : (https://uteshop.com),Ví dụ:
			// https://uteshop.com/assets/images/logo/uteshop-logo.png
			// Lấy thông tin server để tạo Base URL (ví dụ:
			// http://localhost:8080/uteshop)
			String scheme = req.getScheme(); // http
			String serverName = req.getServerName(); // localhost
			int serverPort = req.getServerPort(); // 8080
			String contextPath = req.getContextPath(); // /uteshop

			StringBuilder urlBuilder = new StringBuilder();
			urlBuilder.append(scheme).append("://").append(serverName);

			// Chỉ thêm port vào URL nếu nó không phải là port mặc định (80 cho http, 443
			// cho https)
			if (serverPort != 80 && serverPort != 443) {
				urlBuilder.append(":").append(serverPort);
			}
			urlBuilder.append(contextPath);

			// =================================================================
			// == TIẾP TỤC XÂY DỰNG NỘI DUNG EMAIL HTML ==
			// =================================================================
			StringBuilder emailBodyHtml = new StringBuilder();
			// =================================================================
			// == BẮT ĐẦU NỘI DUNG EMAIL HTML ĐÃ CẬP NHẬT ==
			// =================================================================
			emailBodyHtml.append("<!DOCTYPE html><html lang='vi'><head><meta charset='UTF-8'>")
					// --- PHẦN CSS ---
					.append("<style>")
					.append("body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif; line-height: 1.6; color: #333; background-color: #f4f4f4; margin: 0; padding: 0; }")
					.append(".container { width: 100%; max-width: 600px; margin: 20px auto; padding: 25px; border-radius: 12px; background-color: #ffffff; box-shadow: 0 4px 15px rgba(0,0,0,0.1); }")

					// CSS CHO HEADER DẠNG BANNER
					.append(".header { background-color: #00558D; color: #ffffff; padding: 25px; text-align: center; margin: -25px -25px 25px -25px; border-radius: 12px 12px 0 0; }")
					.append(".header h1 { margin: 0; font-size: 28px; letter-spacing: 1px; }") // Style cho chữ
																								// "UteShop"

					.append(".content { margin-bottom: 20px; }").append(".content h3 { color: #222; }")
					.append(".admin-reply { background-color: #e7f3ff; border-left: 4px solid #007bff; padding: 16px; margin: 20px 0; border-radius: 8px; }")
					.append(".quote { background-color: #f8f9fa; border-left: 4px solid #6c757d; padding: 16px; margin-top: 20px; border-radius: 8px; font-style: italic; color: #495057; }")
					.append(".footer { font-size: 0.9em; text-align: center; color: #777; margin-top: 30px; }")
					.append("</style></head><body>")

					// --- PHẦN HTML BODY ---
					.append("<div class='container'>")

					// THÊM HEADER BANNER MỚI
					// Thay thế logo và tiêu đề cũ bằng banner này.
					.append("<div class='header'><h1>UteShop</h1></div>")

					.append("<div class='content'>").append("<h3>Chào ").append(originalContact.getFullName())
					.append(",</h3>")
					.append("<p>Cảm ơn bạn đã liên hệ với UteShop. Chúng tôi xin phản hồi về yêu cầu của bạn như sau:</p>")
					.append("<div class='admin-reply'>").append(adminReplyContent.replace("\n", "<br>"))
					.append("</div>").append("<p>Nếu có bất kỳ câu hỏi nào khác, vui lòng trả lời lại email này.</p>")
					.append("<div class='quote'>").append("<strong>Nội dung gốc bạn đã gửi:</strong><br><br>")
					.append("<em>").append(originalContact.getContent().replace("\n", "<br>")).append("</em>")
					.append("</div>").append("</div>").append("<div class='footer'>")
					.append("<p>Trân trọng,<br><strong>Đội ngũ UteShop</strong></p>").append("</div>")
					.append("</div></body></html>");
			// =================================================================
			// == KẾT THÚC NỘI DUNG EMAIL HTML ==
			// =================================================================
			// Gửi email
			SendMail mailer = new SendMail();
			mailer.sendMail(recipientEmail, subject, emailBodyHtml.toString());

			resp.sendRedirect(req.getContextPath() + "/admin/contacts?message=replySuccess");

		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(req.getContextPath() + "/admin/contacts?error=replyFailed");
		}
	}

	private void deleteContact(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
			int contactId = Integer.parseInt(req.getParameter("id"));
			contactService.delete(contactId);
			resp.sendRedirect(req.getContextPath() + "/admin/contacts?message=deleteSuccess");
		} catch (NumberFormatException e) {
			resp.sendRedirect(req.getContextPath() + "/admin/contacts?error=invalidId");
		}
	}
}