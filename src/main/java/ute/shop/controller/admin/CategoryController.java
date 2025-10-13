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
import ute.shop.entity.Category;
import ute.shop.service.impl.CategoryServiceImpl;

@WebServlet(urlPatterns = {
    "/admin/categories",
    "/admin/categories/add",
    "/admin/categories/edit",
    "/admin/categories/delete"
})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 10)
public class CategoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CategoryServiceImpl categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        try {
            if (uri.endsWith("/categories")) {
                List<Category> categories = categoryService.findAll();
                req.setAttribute("categories", categories);
                req.setAttribute("page", "categories");
                req.setAttribute("view", "/views/admin/categories/list.jsp");
                req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);

            } else if (uri.endsWith("/add")) {
                req.setAttribute("page", "categories");
                req.setAttribute("view", "/views/admin/categories/add.jsp");
                req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);

            } else if (uri.endsWith("/edit")) {
                int id = Integer.parseInt(req.getParameter("id"));
                Category category = categoryService.findById(id);
                req.setAttribute("category", category);
                req.setAttribute("page", "categories");
                req.setAttribute("view", "/views/admin/categories/edit.jsp");
                req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);

            } else if (uri.endsWith("/delete")) {
                int id = Integer.parseInt(req.getParameter("id"));
                categoryService.delete(id);
                req.getSession().setAttribute("success", "Xóa danh mục thành công!");
                resp.sendRedirect(req.getContextPath() + "/admin/categories");
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi xử lý: " + e.getMessage());
            req.setAttribute("view", "/views/admin/categories/list.jsp");
            req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String uploadDir = req.getServletContext().getRealPath("/assets/images/categories");
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) uploadDirFile.mkdirs();

        try {
            if (uri.endsWith("/add")) {
                String name = req.getParameter("name");
               

                Part filePart = req.getPart("image");
                String fileName = null;
                if (filePart != null && filePart.getSize() > 0) {
                    fileName = UUID.randomUUID() + "_" + filePart.getSubmittedFileName();
                    filePart.write(uploadDir + File.separator + fileName);
                }

                Category c = new Category();
                c.setName(name);
                c.setImage(fileName != null ? "/images/categories/" + fileName : "/images/categories/default-category.png");

                categoryService.save(c);
                req.getSession().setAttribute("success", "Thêm danh mục thành công!");
                resp.sendRedirect(req.getContextPath() + "/admin/categories");
            }

            if (uri.endsWith("/edit")) {
                int id = Integer.parseInt(req.getParameter("id"));
                String name = req.getParameter("name");
              

                Category c = categoryService.findById(id);
                if (c != null) {
                    Part filePart = req.getPart("image");
                    if (filePart != null && filePart.getSize() > 0) {
                        String fileName = UUID.randomUUID() + "_" + filePart.getSubmittedFileName();
                        filePart.write(uploadDir + File.separator + fileName);
                        c.setImage("/images/categories/" + fileName);
                    }
                    c.setName(name);
                    categoryService.update(c);
                    req.getSession().setAttribute("success", "Cập nhật danh mục thành công!");
                    resp.sendRedirect(req.getContextPath() + "/admin/categories");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi khi xử lý dữ liệu: " + e.getMessage());
            req.setAttribute("view", "/views/admin/categories/list.jsp");
            req.getRequestDispatcher("/WEB-INF/decorators/admin.jsp").forward(req, resp);
        }
    }
}
