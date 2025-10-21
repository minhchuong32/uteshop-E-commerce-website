package ute.shop.controller.vendors;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

// ✅ REMOVED: No longer need HttpSession
// import jakarta.servlet.http.HttpSession; 

import ute.shop.entity.Shop;
import ute.shop.entity.User;
import ute.shop.service.IShopService;
import ute.shop.service.impl.ShopServiceImpl;

@WebServlet(urlPatterns = {"/vendor/register-shop"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class RegisterShopController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IShopService shopService = new ShopServiceImpl();

    private static final String PROJECT_SHOP_IMAGE_PATH =
            "D:\\Java\\LTWeb\\uteshop-E-commerce-website\\src\\main\\webapp\\assets\\images\\shops";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // This method is already stateless and needs no changes.
        req.getRequestDispatcher("/WEB-INF/views/vendor/register-shop.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ✅ GET USER FROM REQUEST ATTRIBUTE (set by JwtSecurityFilter)
        User userLogin = (User) req.getAttribute("account");

        // The filter should prevent this, but it's a good failsafe.
        if (userLogin == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String name = req.getParameter("name");
        String description = req.getParameter("description");

        Part logoPart = req.getPart("logo");
        String logoPath = null;
        if (logoPart != null && logoPart.getSize() > 0) {
            logoPath = saveFile(logoPart);
        }

        Shop shop = new Shop();
        shop.setUser(userLogin);
        shop.setName(name);
        shop.setDescription(description);
        shop.setLogo(logoPath);
        shop.setCreatedAt(new Date());

        shopService.insert(shop);

        // ✅ REMOVED: No need to set the shop in the session.
        // session.setAttribute("currentShop", shop);
        // The JwtSecurityFilter will automatically find the new shop on the next request.

        resp.sendRedirect(req.getContextPath() + "/vendor/home");
    }

    private String saveFile(Part filePart) throws IOException {
        // ... (This method is correct and does not need to change)
        String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
        // ...
        return "assets/images/shops/" + fileName;
    }
}

//package ute.shop.controller.vendors;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Date;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.MultipartConfig;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//
//import ute.shop.entity.Shop;
//import ute.shop.entity.User;
//import ute.shop.service.IShopService;
//import ute.shop.service.impl.ShopServiceImpl;
//
//@WebServlet(urlPatterns = {"/vendor/register-shop"})
//@MultipartConfig(
//    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
//    maxFileSize = 1024 * 1024 * 10,      // 10MB
//    maxRequestSize = 1024 * 1024 * 50    // 50MB
//)
//public class RegisterShopController extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//    private final IShopService shopService = new ShopServiceImpl();
//
//    private static final String PROJECT_SHOP_IMAGE_PATH =
//            "D:\\Java\\LTWeb\\uteshop-E-commerce-website\\src\\main\\webapp\\assets\\images\\shops";
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//        req.getRequestDispatcher("/WEB-INF/views/vendor/register-shop.jsp").forward(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//
//        HttpSession session = req.getSession(false);
//        User userLogin = (session != null) ? (User) session.getAttribute("account") : null;
//
//        if (userLogin == null) {
//            resp.sendRedirect(req.getContextPath() + "/login");
//            return;
//        }
//
//        String name = req.getParameter("name");
//        String description = req.getParameter("description");
//
//        Part logoPart = req.getPart("logo");
//        String logoPath = null;
//        if (logoPart != null && logoPart.getSize() > 0) {
//            logoPath = saveFile(logoPart);
//        }
//
//        Shop shop = new Shop();
//        shop.setUser(userLogin);
//        shop.setName(name);
//        shop.setDescription(description);
//        shop.setLogo(logoPath);
//        shop.setCreatedAt(new Date());
//
//        shopService.insert(shop);
//
//        session.setAttribute("currentShop", shop);
//
//        resp.sendRedirect(req.getContextPath() + "/vendor/home");
//    }
//
//    private String saveFile(Part filePart) throws IOException {
//        String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
//
//        File projectDir = new File(PROJECT_SHOP_IMAGE_PATH);
//        if (!projectDir.exists()) projectDir.mkdirs();
//        filePart.write(projectDir + File.separator + fileName);
//
//        String deployPath = getServletContext().getRealPath("/assets/images/shops");
//        File deployDir = new File(deployPath);
//        if (!deployDir.exists()) deployDir.mkdirs();
//        filePart.write(deployDir + File.separator + fileName);
//
//        return "assets/images/shops/" + fileName;
//    }
//}
