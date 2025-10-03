package ute.shop.controller.users;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.shop.entity.Product;
import ute.shop.entity.Shop;
import ute.shop.service.IProductService;
import ute.shop.service.IShopService;
import ute.shop.service.impl.ProductServiceImpl;
import ute.shop.service.impl.ShopServiceImpl;

@WebServlet("/user/shop/detail")
public class ShopDetailController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IShopService shopService = new ShopServiceImpl();
    private IProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int shopId = Integer.parseInt(req.getParameter("id"));

        Shop shop = shopService.getById(shopId);
        List<Product> products = productService.findByShopId(shopId);

        req.setAttribute("shop", shop);
        req.setAttribute("products", products);

        req.getRequestDispatcher("/views/user/shop-detail.jsp").forward(req, resp);
    }
}
