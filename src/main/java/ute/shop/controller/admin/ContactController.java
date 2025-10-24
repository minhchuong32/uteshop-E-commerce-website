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
	
@WebServlet(urlPatterns = {"/admin/contacts", "/admin/contacts/reply", "/admin/contacts/delete"})
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
        String recipientEmail = req.getParameter("email");
        String subject = req.getParameter("subject");
        String body = req.getParameter("body");

        try {
            SendMail mailer = new SendMail();
            mailer.sendMail(recipientEmail, subject, body);
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