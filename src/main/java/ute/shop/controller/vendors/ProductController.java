package ute.shop.controller.vendors;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.shop.dao.impl.ProductVariantDaoImpl;
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
    private static final int PAGE_SIZE = 6;

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

        IShopService shopService = new ShopServiceImpl();
        Shop shop = (Shop) session.getAttribute("currentShop");
        if (shop == null) {
            shop = shopService.findByUserId(user.getUserId());
            session.setAttribute("currentShop", shop);
        }

        if (shop == null) {
            resp.sendRedirect(req.getContextPath() + "/vendor/shop/register");
            return;
        }

        if (uri.endsWith("/products")) {
            Integer categoryId = null;
            String catParam = req.getParameter("category");
            if (catParam != null && !catParam.isEmpty()) {
                categoryId = Integer.parseInt(catParam);
            }

            int currentPage = 1;
            String pageParam = req.getParameter("page");
            if (pageParam != null) currentPage = Integer.parseInt(pageParam);

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
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/vendor/products");
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
                return;
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

                for (Part part : req.getParts()) {
                    if ("extraImages".equals(part.getName()) && part.getSize() > 0) {
                        String imgUrl = saveFile(part, "products");
                        ProductImage img = new ProductImage();
                        img.setProduct(p);
                        img.setImageUrl(imgUrl);
                        img.setMain(false);
                        p.getImages().add(img);
                        System.out.println("🖼️  [Extra Image] " + imgUrl);
                    }
                }
                
                productService.save(p);

                ProductVariantDaoImpl variantDao = new ProductVariantDaoImpl();
                int variantCount = 0;

                int index = 0;
                while (true) {
                    String optionName = req.getParameter("variantOptionName_" + index);
                    if (optionName == null) break;

                    String optionValue = req.getParameter("variantOptionValue_" + index);
                    String priceStr = req.getParameter("variantPrice_" + index);
                    String oldPriceStr = req.getParameter("variantOldPrice_" + index);
                    String stockStr = req.getParameter("variantStock_" + index);

                    BigDecimal variantPrice = (priceStr != null && !priceStr.isEmpty())
                            ? new BigDecimal(priceStr)
                            : BigDecimal.ZERO;
                    BigDecimal oldPrice = (oldPriceStr != null && !oldPriceStr.isEmpty())
                            ? new BigDecimal(oldPriceStr)
                            : null;
                    int stock = (stockStr != null && !stockStr.isEmpty())
                            ? Integer.parseInt(stockStr)
                            : 0;

                    Part variantPart = req.getPart("variantImage_" + index);
                    String variantImg = null;
                    if (variantPart != null && variantPart.getSize() > 0) {
                        variantImg = saveFile(variantPart, "variants");
                    }

                    ProductVariant v = new ProductVariant();
                    v.setProduct(p);
                    v.setOptionName(optionName);
                    v.setOptionValue(optionValue);
                    v.setPrice(variantPrice);
                    v.setOldPrice(oldPrice);
                    v.setStock(stock);
                    v.setImageUrl(variantImg);

                    variantDao.insertVariant(v);
                    variantCount++;

                    System.out.printf("🧩 Variant inserted [%d] %s=%s | %s VND | Stock=%d%n",
                            variantCount, optionName, optionValue, variantPrice, stock);

                    index++;
                }

                
                resp.sendRedirect(req.getContextPath() + "/vendor/products");
            } catch (Exception e) {
                e.printStackTrace();
                resp.sendRedirect(req.getContextPath() + "/vendor/products?error=1");
            }
        }

        // ✅ EDIT PRODUCT
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

                    // Ảnh đại diện mới
                    String newImageUrl = handleFileUpload(req, "imageFile", "products");
                    if (newImageUrl != null) p.setImageUrl(newImageUrl);

                    // Xóa ảnh phụ cũ nếu chọn xóa
                    String[] delImgs = req.getParameterValues("deleteExtraImageIds");
                    if (delImgs != null) {
                        for (String s : delImgs) {
                            productService.deleteExtraImage(Long.parseLong(s));
                            System.out.println("🗑️ [Deleted extra image ID] " + s);
                        }
                    }

                    // Thêm ảnh phụ mới
                    for (Part part : req.getParts()) {
                        if ("extraImages".equals(part.getName()) && part.getSize() > 0) {
                            String imgUrl = saveFile(part, "products");
                            ProductImage img = new ProductImage();
                            img.setProduct(p);
                            img.setImageUrl(imgUrl);
                            img.setMain(false);
                            p.getImages().add(img);
                            System.out.println("🖼️ [New extra image added] " + imgUrl);
                        }
                    }

                    // ✅ Cập nhật biến thể
                    List<Part> parts = new ArrayList<>(req.getParts());
                    List<ProductVariant> newVariants = new ArrayList<>();
                    int index = 0;
                    while (true) {
                        String optName = req.getParameter("variantOptionName_" + index);
                        if (optName == null) break;

                        String optValue = req.getParameter("variantOptionValue_" + index);
                        String priceStr = req.getParameter("variantPrice_" + index);
                        String oldStr = req.getParameter("variantOldPrice_" + index);
                        String stockStr = req.getParameter("variantStock_" + index);

                        BigDecimal price = new BigDecimal(priceStr);
                        BigDecimal oldPrice = (oldStr != null && !oldStr.isEmpty())
                                ? new BigDecimal(oldStr) : null;
                        int stock = Integer.parseInt(stockStr);

                        // Lấy part ảnh biến thể
                        String fieldName = "variantImage_" + index;
                        Part variantPart = parts.stream()
                                .filter(pp -> fieldName.equals(pp.getName()) && pp.getSize() > 0)
                                .findFirst()
                                .orElse(null);

                        String variantImg = null;
                        if (variantPart != null) {
                            variantImg = saveFile(variantPart, "variants");
                        }

                        String idParam = req.getParameter("variantId_" + index);
                        ProductVariant v = (idParam != null && !idParam.isEmpty())
                                ? productService.findVariantById(Long.parseLong(idParam))
                                : new ProductVariant();

                        v.setProduct(p);
                        v.setOptionName(optName);
                        v.setOptionValue(optValue);
                        v.setPrice(price);
                        v.setOldPrice(oldPrice);
                        v.setStock(stock);
                        if (variantImg != null) v.setImageUrl(variantImg);

                        newVariants.add(v);

                        System.out.printf("🧩 [Edit Variant %d] %s=%s | Price=%s | Stock=%d | Img=%s%n",
                                index, optName, optValue, price, stock, variantImg);
                        index++;
                    }

                    p.setVariants(newVariants);
                    productService.update(p);
                    System.out.println("✅ Product updated successfully. Variants=" + newVariants.size());
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

    private String saveFile(Part filePart, String folder) throws IOException {
    	String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();

        // Lưu bản copy trong source project (để không mất khi deploy)
        String projectPath = PROJECT_IMAGE_PATH.replace("products", folder);
        File projectDir = new File(projectPath);
        if (!projectDir.exists()) projectDir.mkdirs();
        filePart.write(projectDir + File.separator + fileName);

        // Lưu vào thư mục deploy thực tế (Tomcat chạy)
        String deployPath = getServletContext().getRealPath("/assets/images/" + folder);
        File deployDir = new File(deployPath);
        if (!deployDir.exists()) deployDir.mkdirs();
        filePart.write(deployDir + File.separator + fileName);

        // Trả về đường dẫn đúng để JSP load
        return "images/" + folder + "/" + fileName;
    }
}
