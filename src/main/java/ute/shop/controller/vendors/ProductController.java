package ute.shop.controller.vendors;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.entity.*;
import ute.shop.service.*;
import ute.shop.service.impl.*;

@WebServlet(urlPatterns = {
    "/vendor/products",
    "/vendor/products/add",
    "/vendor/products/edit",
    "/vendor/products/delete"
})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class ProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private IProductService productService = new ProductServiceImpl();
    private ICategoryService categoryService = new CategoryServiceImpl();
    private static final int PAGE_SIZE = 6;

    // ✅ Đường dẫn cố định để lưu ảnh
    private static final String PROJECT_IMAGE_PATH =
            "D:\\Java\\LTWeb\\uteshop-E-commerce-website\\src\\main\\webapp\\assets\\images\\products";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String uri = req.getRequestURI();

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("account") : null;
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Shop shop = null;
        if ("VENDOR".equalsIgnoreCase(user.getRole())) {
            IShopService shopService = new ShopServiceImpl();
            shop = shopService.findByUserId(user.getUserId());
            if (shop != null) {
                session.setAttribute("currentShop", shop);
            }
        } else {
            shop = (Shop) session.getAttribute("currentShop");
        }

        if (uri.endsWith("/products")) {
            // --- Danh sách sản phẩm ---
            String categoryParam = req.getParameter("category");
            Integer categoryId = null;
            if (categoryParam != null && !categoryParam.isEmpty()) {
                categoryId = Integer.parseInt(categoryParam);
            }

            int currentPage = 1;
            String pageParam = req.getParameter("page");
            if (pageParam != null && !pageParam.isEmpty()) {
                currentPage = Integer.parseInt(pageParam);
            }

            List<Product> allProducts;
            if (categoryId != null) {
                allProducts = productService.findByCategoryAndShop(categoryId, shop.getShopId());
                req.setAttribute("selectedCategory", categoryId);
            } else {
                allProducts = productService.findByShopId(shop.getShopId());
            }

            int totalProducts = allProducts.size();
            int totalPages = (int) Math.ceil((double) totalProducts / PAGE_SIZE);

            int startIndex = (currentPage - 1) * PAGE_SIZE;
            int endIndex = Math.min(startIndex + PAGE_SIZE, totalProducts);

            List<Product> products = allProducts.subList(startIndex, endIndex);

            req.setAttribute("categories", categoryService.findAll());
            req.setAttribute("list", products);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("currentPage", currentPage);

            req.setAttribute("page", "products");
            req.setAttribute("view", "/views/vendor/products/list.jsp");
            req.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(req, resp);

        } else if (uri.endsWith("/add")) {
            // --- Hiển thị form thêm ---
            req.setAttribute("categories", categoryService.findAll());
            req.setAttribute("view", "/views/vendor/products/add.jsp");
            req.setAttribute("page", "products");
            req.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(req, resp);

        } else if (uri.endsWith("/edit")) {
            // --- Hiển thị form sửa ---
            String idParam = req.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                int id = Integer.parseInt(idParam);
                Product product = productService.findById(id);

                if (product != null && product.getShop().getShopId() == shop.getShopId()) {
                    req.setAttribute("product", product);
                    req.setAttribute("categories", categoryService.findAll());
                    req.setAttribute("view", "/views/vendor/products/edit.jsp");
                    req.setAttribute("page", "products");
                    req.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(req, resp);
                } else {
                    resp.sendRedirect(req.getContextPath() + "/vendor/products");
                }
            } else {
                resp.sendRedirect(req.getContextPath() + "/vendor/products");
            }

        } else if (uri.endsWith("/delete")) {
            // --- Xóa sản phẩm ---
            String idParam = req.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                int id = Integer.parseInt(idParam);
                Product p = productService.findById(id);
                if (p != null && p.getShop().getShopId().equals(shop.getShopId())) {
                    productService.delete(id);
                }
            }
            resp.sendRedirect(req.getContextPath() + "/vendor/products");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String uri = req.getRequestURI();

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("account") : null;
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Shop shop = (session != null) ? (Shop) session.getAttribute("currentShop") : null;
        if (shop == null) {
            resp.sendRedirect(req.getContextPath() + "/vendor/shop/register");
            return;
        }

        if (uri.endsWith("/add")) {
            // --- Xử lý thêm ---
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            BigDecimal price = new BigDecimal(req.getParameter("price"));
            int stock = Integer.parseInt(req.getParameter("stock"));
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));
            String imageUrl = null;

            // ✅ Upload ảnh
            Part filePart = req.getPart("imageFile");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();

                // --- Ghi file ---
                File projectDir = new File(PROJECT_IMAGE_PATH);
                if (!projectDir.exists()) projectDir.mkdirs();

                String deployPath = req.getServletContext().getRealPath("/assets/images/products");
                File deployDir = new File(deployPath);
                if (!deployDir.exists()) deployDir.mkdirs();

                filePart.write(projectDir + File.separator + fileName);
                filePart.write(deployDir + File.separator + fileName);

                imageUrl = "assets/images/products/" + fileName;
            }

            Category category = new Category();
            category.setCategoryId(categoryId);

            Product p = new Product();
            p.setName(name);
            p.setDescription(description);
            p.setPrice(price);
            //p.setStock(stock);
            p.setImageUrl(imageUrl);
            p.setCategory(category);
            p.setShop(shop);

            productService.save(p);
            resp.sendRedirect(req.getContextPath() + "/vendor/products");

        } else if (uri.endsWith("/edit")) {
            // --- Xử lý cập nhật ---
            int id = Integer.parseInt(req.getParameter("id"));
            Product p = productService.findById(id);
            if (p != null && p.getShop().getShopId() == shop.getShopId()) {
                p.setName(req.getParameter("name"));
                p.setDescription(req.getParameter("description"));
                p.setPrice(new BigDecimal(req.getParameter("price")));
                //p.setStock(Integer.parseInt(req.getParameter("stock")));

                int categoryId = Integer.parseInt(req.getParameter("categoryId"));
                Category c = new Category();
                c.setCategoryId(categoryId);
                p.setCategory(c);

                Part filePart = req.getPart("imageFile");
                if (filePart != null && filePart.getSize() > 0) {
                    String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();

                    File projectDir = new File(PROJECT_IMAGE_PATH);
                    if (!projectDir.exists()) projectDir.mkdirs();

                    String deployPath = req.getServletContext().getRealPath("/assets/images/products");
                    File deployDir = new File(deployPath);
                    if (!deployDir.exists()) deployDir.mkdirs();

                    filePart.write(projectDir + File.separator + fileName);
                    filePart.write(deployDir + File.separator + fileName);

                    p.setImageUrl("assets/images/products/" + fileName);
                }

                productService.update(p);
            }
            resp.sendRedirect(req.getContextPath() + "/vendor/products");
        }
    }
}
