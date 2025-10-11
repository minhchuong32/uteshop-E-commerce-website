package ute.shop.controller.admin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import ute.shop.entity.Product;
import ute.shop.entity.Category;
import ute.shop.entity.Shop;
import ute.shop.service.impl.ProductServiceImpl;
import ute.shop.service.impl.CategoryServiceImpl;
import ute.shop.service.impl.ShopServiceImpl;

@WebServlet(urlPatterns = {
        "/admin/products",
        "/admin/products/add",
        "/admin/products/edit",
        "/admin/products/delete"
})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,    // 1MB
        maxFileSize = 1024 * 1024 * 5,      // 5MB
        maxRequestSize = 1024 * 1024 * 10   // 10MB
)
public class ProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProductServiceImpl productService = new ProductServiceImpl();
    private final CategoryServiceImpl categoryService = new CategoryServiceImpl();
    private final ShopServiceImpl shopService = new ShopServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String uri = req.getRequestURI();

        try {
            if (uri.endsWith("/products")) {
                // Hiển thị danh sách sản phẩm
                List<Product> allProducts = productService.findAll();
                req.setAttribute("products", allProducts);
                req.setAttribute("page", "products");
                req.setAttribute("view", "/views/admin/products/dashboard.jsp");
                req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);

            } else if (uri.endsWith("/add")) {
                // Form thêm sản phẩm
                List<Category> categories = categoryService.findAll();
                List<Shop> shops = shopService.getAll();

                req.setAttribute("categories", categories);
                req.setAttribute("shops", shops);
                req.setAttribute("page", "products");
                req.setAttribute("view", "/views/admin/products/add.jsp");
                req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);

            } else if (uri.endsWith("/edit")) {
                // Form chỉnh sửa sản phẩm
                int id = Integer.parseInt(req.getParameter("id"));
                Product product = productService.findById(id);

                if (product != null) {
                    List<Category> categories = categoryService.findAll();
                    List<Shop> shops = shopService.getAll();

                    req.setAttribute("product", product);
                    req.setAttribute("categories", categories);
                    req.setAttribute("shops", shops);
                } else {
                    req.setAttribute("error", "Không tìm thấy sản phẩm!");
                }

                req.setAttribute("page", "products");
                req.setAttribute("view", "/views/admin/products/edit.jsp");
                req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);

            } else if (uri.endsWith("/delete")) {
            
            	    System.out.println("🗑️ [ProductController] Request DELETE nhận được: " + uri);

            	    String idStr = req.getParameter("id");
            	    System.out.println("🧩 ID sản phẩm nhận được từ request: " + idStr);

            	    if (idStr == null || idStr.isEmpty()) {
            	        System.out.println("⚠️ Không nhận được ID từ request!");
            	        req.getSession().setAttribute("error", "Không nhận được ID sản phẩm để xóa!");
            	        resp.sendRedirect(req.getContextPath() + "/admin/products");
            	        return;
            	    }

            	    int id = Integer.parseInt(idStr);
            	    System.out.println("➡️ Đang gọi productService.delete(" + id + ")");
            	    try {
            	        productService.delete(id);
            	        System.out.println("✅ Đã xóa sản phẩm thành công!");
            	        req.getSession().setAttribute("success", "Xóa sản phẩm thành công!");
            	    } catch (Exception e) {
            	        System.out.println("❌ Lỗi khi xóa sản phẩm: " + e.getMessage());
            	        e.printStackTrace();
            	        req.getSession().setAttribute("error", "Lỗi khi xóa sản phẩm!");
            	    }

            	    resp.sendRedirect(req.getContextPath() + "/admin/products");
            	
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Đã xảy ra lỗi: " + e.getMessage());
            req.setAttribute("page", "products");
            req.setAttribute("view", "/views/admin/products/dashboard.jsp");
            req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String uri = req.getRequestURI();

        try {
            String uploadDir = req.getServletContext().getRealPath("/assets/images/products");
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) uploadDirFile.mkdirs();

            if (uri.endsWith("/add")) {
                // ===== Xử lý thêm sản phẩm =====
                String name = req.getParameter("name");
                String description = req.getParameter("description");
                int categoryId = Integer.parseInt(req.getParameter("categoryId"));
                int shopId = Integer.parseInt(req.getParameter("shopId"));

                // Upload hình ảnh
                Part filePart = req.getPart("imageUrl");
                String imageFileName = null;
                if (filePart != null && filePart.getSize() > 0) {
                    imageFileName = UUID.randomUUID() + "_" + filePart.getSubmittedFileName();
                    filePart.write(uploadDir + File.separator + imageFileName);
                }

                Product product = new Product();
                product.setName(name);
                product.setDescription(description);
                product.setImageUrl(imageFileName);

                Category category = categoryService.findById(categoryId);
                Shop shop = shopService.getById(shopId);

                product.setCategory(category);
                product.setShop(shop);

                productService.save(product);
                req.getSession().setAttribute("success", "Thêm sản phẩm thành công!");
                resp.sendRedirect(req.getContextPath() + "/admin/products");

            } else if (uri.endsWith("/edit")) {
                // ===== Xử lý cập nhật sản phẩm =====
                int id = Integer.parseInt(req.getParameter("id"));
                String name = req.getParameter("name");
                String description = req.getParameter("description");
                int categoryId = Integer.parseInt(req.getParameter("categoryId"));
                int shopId = Integer.parseInt(req.getParameter("shopId"));

                Product product = productService.findById(id);

                if (product != null) {
                    Part filePart = req.getPart("imageUrl");
                    if (filePart != null && filePart.getSize() > 0) {
                        String newFileName = UUID.randomUUID() + "_" + filePart.getSubmittedFileName();
                        filePart.write(uploadDir + File.separator + newFileName);
                        product.setImageUrl(newFileName);
                    }

                    product.setName(name);
                    product.setDescription(description);
                    product.setCategory(categoryService.findById(categoryId));
                    product.setShop(shopService.getById(shopId));

                    productService.update(product);
                    req.getSession().setAttribute("success", "Cập nhật sản phẩm thành công!");
                    resp.sendRedirect(req.getContextPath() + "/admin/products");
                } else {
                    req.setAttribute("error", "Không tìm thấy sản phẩm cần chỉnh sửa!");
                    req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi khi xử lý dữ liệu: " + e.getMessage());
            req.setAttribute("page", "products");
            req.setAttribute("view", "/views/admin/products/dashboard.jsp");
            req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
        }
    }
}
