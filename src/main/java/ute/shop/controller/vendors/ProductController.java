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
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
    maxFileSize = 1024 * 1024 * 10,       // 10MB
    maxRequestSize = 1024 * 1024 * 50     // 50MB
)
public class ProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IProductService productService = new ProductServiceImpl();
    private final ICategoryService categoryService = new CategoryServiceImpl();
    private static final int PAGE_SIZE = 6;

    // 🖼️ Đường dẫn tuyệt đối trong project (máy dev)
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

        // ✅ Lấy shop của vendor
        Shop shop = (Shop) session.getAttribute("currentShop");
        if (shop == null && "VENDOR".equalsIgnoreCase(user.getRole())) {
            IShopService shopService = new ShopServiceImpl();
            shop = shopService.findByUserId(user.getUserId());
            if (shop != null) {
                session.setAttribute("currentShop", shop);
            }
        }

        if (shop == null) {
            resp.sendRedirect(req.getContextPath() + "/vendor/shop/register");
            return;
        }

        // ✅ Hiển thị danh sách sản phẩm
        if (uri.endsWith("/products")) {
            String categoryParam = req.getParameter("category");
            Integer categoryId = (categoryParam != null && !categoryParam.isEmpty())
                    ? Integer.parseInt(categoryParam)
                    : null;

            int currentPage = 1;
            String pageParam = req.getParameter("page");
            if (pageParam != null && !pageParam.isEmpty()) {
                try {
                    currentPage = Integer.parseInt(pageParam);
                } catch (NumberFormatException ignored) {}
            }

            List<Product> allProducts = (categoryId != null)
                    ? productService.findByCategoryAndShop(categoryId, shop.getShopId())
                    : productService.findByShopId(shop.getShopId());

            int totalProducts = allProducts.size();
            int totalPages = (int) Math.ceil((double) totalProducts / PAGE_SIZE);
            int startIndex = Math.max(0, (currentPage - 1) * PAGE_SIZE);
            int endIndex = Math.min(startIndex + PAGE_SIZE, totalProducts);
            List<Product> products = allProducts.subList(startIndex, endIndex);

            req.setAttribute("categories", categoryService.findAll());
            req.setAttribute("list", products);
            req.setAttribute("selectedCategory", categoryId);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("currentPage", currentPage);
            req.setAttribute("page", "products");
            req.setAttribute("view", "/views/vendor/products/list.jsp");
            req.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(req, resp);
        }

        // ✅ Trang thêm sản phẩm
        else if (uri.endsWith("/add")) {
            req.setAttribute("categories", categoryService.findAll());
            req.setAttribute("page", "products");
            req.setAttribute("view", "/views/vendor/products/add.jsp");
            req.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(req, resp);
        }

        // ✅ Trang sửa sản phẩm
        else if (uri.endsWith("/edit")) {
            String idParam = req.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                int id = Integer.parseInt(idParam);
                Product product = productService.findById_fix(id);

                if (product != null && product.getShop().getShopId().equals(shop.getShopId())) {
                    req.setAttribute("product", product);
                    req.setAttribute("categories", categoryService.findAll());
                    req.setAttribute("page", "products");
                    req.setAttribute("view", "/views/vendor/products/edit.jsp");
                    req.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(req, resp);
                    return;
                }
            }
            resp.sendRedirect(req.getContextPath() + "/vendor/products");
        }

        // ✅ Xóa sản phẩm
        else if (uri.endsWith("/delete")) {
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

        Shop shop = (Shop) session.getAttribute("currentShop");
        if (shop == null) {
            resp.sendRedirect(req.getContextPath() + "/vendor/shop/register");
            return;
        }

        // ✅ Xử lý thêm sản phẩm
        if (uri.endsWith("/add")) {
            try {
                String name = req.getParameter("name");
                String description = req.getParameter("description");
                BigDecimal price = new BigDecimal(req.getParameter("price"));
                int categoryId = Integer.parseInt(req.getParameter("categoryId"));
                String imageUrl = handleFileUpload(req);

                Category category = new Category();
                category.setCategoryId(categoryId);

                Product p = new Product();
                p.setName(name);
                p.setDescription(description);
                p.setPrice(price);
                p.setImageUrl(imageUrl);
                p.setCategory(category);
                p.setShop(shop);

                productService.save(p);
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.sendRedirect(req.getContextPath() + "/vendor/products");
        }

        // ✅ Xử lý sửa sản phẩm
        else if (uri.endsWith("/edit")) {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                Product p = productService.findById_fix(id);
                if (p != null && p.getShop().getShopId().equals(shop.getShopId())) {
                    p.setName(req.getParameter("name"));
                    p.setDescription(req.getParameter("description"));
                    p.setPrice(new BigDecimal(req.getParameter("price")));

                    int categoryId = Integer.parseInt(req.getParameter("categoryId"));
                    Category c = new Category();
                    c.setCategoryId(categoryId);
                    p.setCategory(c);

                    String newImageUrl = handleFileUpload(req);
                    if (newImageUrl != null) {
                        p.setImageUrl(newImageUrl);
                    }

                    productService.update(p);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.sendRedirect(req.getContextPath() + "/vendor/products");
        }
    }

    private String handleFileUpload(HttpServletRequest req) throws IOException, ServletException {
        Part filePart = req.getPart("imageFile");
        if (filePart == null || filePart.getSize() <= 0) {
            return null;
        }

        String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();

        File projectDir = new File(PROJECT_IMAGE_PATH);
        if (!projectDir.exists()) projectDir.mkdirs();

        String deployPath = req.getServletContext().getRealPath("/images/products");
        File deployDir = new File(deployPath);
        if (!deployDir.exists()) deployDir.mkdirs();

        // Ghi ảnh vào cả 2 nơi (local + server)
        filePart.write(projectDir + File.separator + fileName);
        filePart.write(deployDir + File.separator + fileName);

        return "/images/products/" + fileName;
    }
}
