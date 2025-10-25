package ute.shop.controller.vendors;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

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
    "/vendor/products/delete",
    "/vendor/products/detail"
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
    private final IProductVariantService variantService = new ProductVariantServiceImpl();
    private static final int PAGE_SIZE = 6;

    // ✅ Đường dẫn lưu file vật lý (vẫn lưu vào assets/images)
    private static final String PROJECT_IMAGE_PATH =
            "D:\\Java\\LTWeb\\uteshop-E-commerce-website\\src\\main\\webapp\\assets\\images\\products";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String uri = req.getRequestURI();
        Shop shop = (Shop) req.getAttribute("currentShop"); // ✅ Lấy từ filter

        if (uri.endsWith("/products")) {
            Integer categoryId = null;
            String catParam = req.getParameter("category");
            if (catParam != null && !catParam.isEmpty()) {
                categoryId = Integer.parseInt(catParam);
            }

            int currentPage = 1;
            String pageParam = req.getParameter("page");
            if (pageParam != null)
                currentPage = Integer.parseInt(pageParam);

            List<Product> allProducts = (categoryId != null)
                    ? productService.findByCategoryAndShop(categoryId, shop.getShopId())
                    : productService.findByShopId(shop.getShopId());

            int totalProducts = allProducts.size();
            int totalPages = (int) Math.ceil((double) totalProducts / PAGE_SIZE);
            int startIndex = Math.max(0, (currentPage - 1) * PAGE_SIZE);
            int endIndex = Math.min(startIndex + PAGE_SIZE, totalProducts);
            List<Product> products = allProducts.subList(startIndex, endIndex);

            req.setAttribute("selectedCategory", categoryId);
            req.setAttribute("categories", categoryService.findAll());
            req.setAttribute("list", products);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("currentPage", currentPage);
            req.setAttribute("page", "products");
            req.setAttribute("view", "/views/vendor/products/list.jsp");
            req.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(req, resp);
        }

        else if (uri.endsWith("/add")) {
            req.setAttribute("categories", categoryService.findAll());
            req.setAttribute("view", "/views/vendor/products/add.jsp");
            req.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(req, resp);
        }

        else if (uri.endsWith("/edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Product product = productService.findById_fix(id);
            if (product != null && product.getShop().getShopId().equals(shop.getShopId())) {
                req.setAttribute("product", product);
                req.setAttribute("categories", categoryService.findAll());
                req.setAttribute("view", "/views/vendor/products/edit.jsp");
                req.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/vendor/products");
            }
        }

        else if (uri.endsWith("/delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Product p = productService.findById(id);
            if (p != null && p.getShop().getShopId().equals(shop.getShopId())) {
                productService.delete(id);
            }
            resp.sendRedirect(req.getContextPath() + "/vendor/products");
        }

        else if (uri.endsWith("/detail")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Product product = productService.findById_fix(id);
            if (product != null && product.getShop().getShopId().equals(shop.getShopId())) {
                IReviewService reviewService = new ReviewServiceImpl();
                List<Review> reviews = reviewService.getByProductId(id);
                List<ProductVariant> variants = product.getVariants();

                BigDecimal minPrice = product.getPrice();
                int totalStock = 0;

                if (variants != null && !variants.isEmpty()) {
                    for (ProductVariant v : variants) {
                        totalStock += v.getStock();
                        if (minPrice == null || v.getPrice().compareTo(minPrice) < 0) {
                            minPrice = v.getPrice();
                        }
                    }
                }

                req.setAttribute("product", product);
                req.setAttribute("variants", variants);
                req.setAttribute("reviews", reviews);
                req.setAttribute("minPrice", minPrice);
                req.setAttribute("totalStock", totalStock);
                req.setAttribute("view", "/views/vendor/products/detail.jsp");
                req.getRequestDispatcher("/WEB-INF/decorators/vendor.jsp").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/vendor/products");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String uri = req.getRequestURI();
        Shop shop = (Shop) req.getAttribute("currentShop");

        if (uri.endsWith("/add")) {
            try {
                String name = req.getParameter("name");
                String desc = req.getParameter("description");
                BigDecimal price = new BigDecimal(req.getParameter("price"));
                int categoryId = Integer.parseInt(req.getParameter("categoryId"));

                String mainImageUrl = handleFileUpload(req, "imageFile", "products");

                Category category = new Category();
                category.setCategoryId(categoryId);

                Product p = new Product();
                p.setName(name);
                p.setDescription(desc);
                p.setPrice(price);
                p.setImageUrl(mainImageUrl);
                p.setCategory(category);
                p.setShop(shop);

                // Ảnh phụ
                for (Part part : req.getParts()) {
                    if ("extraImages".equals(part.getName()) && part.getSize() > 0) {
                        String imgUrl = saveFile(part, "products");
                        ProductImage img = new ProductImage();
                        img.setProduct(p);
                        img.setImageUrl(imgUrl);
                        img.setMain(false);
                        p.getImages().add(img);
                    }
                }

                productService.save(p);

                // Biến thể
                String variantsJson = req.getParameter("variantsJson");
                if (variantsJson != null && !variantsJson.isEmpty()) {
                    ObjectMapper mapper = new ObjectMapper();
                    List<Map<String, Object>> variantList = mapper.readValue(variantsJson, List.class);
                    List<Part> allParts = new ArrayList<>();
                    req.getParts().forEach(allParts::add);

                    for (int i = 0; i < variantList.size(); i++) {
                        Map<String, Object> variantData = variantList.get(i);
                        String variantImageUrl = null;

                        List<Part> variantImageParts = new ArrayList<>();
                        for (Part part : allParts) {
                            if (part.getName().startsWith("variantImage") && part.getSize() > 0) {
                                variantImageParts.add(part);
                            }
                        }
                        if (i < variantImageParts.size()) {
                            Part imagePart = variantImageParts.get(i);
                            variantImageUrl = saveFile(imagePart, "variants");
                        }

                        ProductVariant v = new ProductVariant();
                        v.setProduct(p);
                        v.setOptionName((String) variantData.get("optionName"));
                        v.setOptionValue((String) variantData.get("optionValue"));
                        v.setPrice(new BigDecimal(variantData.get("price").toString()));
                        v.setStock(Integer.parseInt(variantData.get("stock").toString()));
                        Object oldPriceObj = variantData.get("oldPrice");
                        if (oldPriceObj != null && !oldPriceObj.toString().isEmpty()) {
                            v.setOldPrice(new BigDecimal(oldPriceObj.toString()));
                        }
                        v.setImageUrl(variantImageUrl);
                        variantService.save(v);
                    }
                }

                resp.sendRedirect(req.getContextPath() + "/vendor/products");

            } catch (Exception e) {
                e.printStackTrace();
                resp.sendRedirect(req.getContextPath() + "/vendor/products?error=1");
            }
        }

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

                    String newImageUrl = handleFileUpload(req, "imageFile", "products");
                    if (newImageUrl != null)
                        p.setImageUrl(newImageUrl);

                    productService.update(p);
                }
                resp.sendRedirect(req.getContextPath() + "/vendor/products");
            } catch (Exception e) {
                e.printStackTrace();
                resp.sendRedirect(req.getContextPath() + "/vendor/products?error=1");
            }
        }
    }

    private String handleFileUpload(HttpServletRequest req, String fieldName, String folder)
            throws IOException, ServletException {
        Part filePart = req.getPart(fieldName);
        if (filePart == null || filePart.getSize() <= 0) return null;
        return saveFile(filePart, folder);
    }

    // ✅ Lưu vật lý vào thư mục cũ, nhưng lưu vào DB chỉ "/images/..."
    private String saveFile(Part filePart, String folder) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();

        // Lưu bản copy trong source project
        String projectPath = PROJECT_IMAGE_PATH.replace("products", folder);
        File projectDir = new File(projectPath);
        if (!projectDir.exists()) projectDir.mkdirs();
        filePart.write(projectDir + File.separator + fileName);

        // Lưu bản deploy
        String deployPath = getServletContext().getRealPath("/assets/images/" + folder);
        File deployDir = new File(deployPath);
        if (!deployDir.exists()) deployDir.mkdirs();
        filePart.write(deployDir + File.separator + fileName);

        // ✅ Trả về URL ngắn gọn cho DB
        return "/images/" + folder + "/" + fileName;
    }
}
