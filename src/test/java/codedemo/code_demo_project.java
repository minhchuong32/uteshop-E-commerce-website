//# =========================
//# uteshop-servlet (Demo Code Skeleton)
//# Tech: Maven, Jakarta Servlet/JSP/JSTL, JDBC MySQL, MVC, Filters, Session/Cookie
//# NOTE: This is a minimal, runnable starter you can import into STS/IntelliJ.
//#       Search for TODO tags to extend. Uses jakarta.* (Tomcat 10+).
//# =========================


//# -------------------------
//# File: src/main/resources/db.properties
//# -------------------------
//jdbc.url=jdbc:mysql://localhost:3306/uteshop_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
//jdbc.username=root
//jdbc.password=your_password
//
//# -------------------------
//# File: src/main/java/ute/shop/utils/DBConnect.java
//# -------------------------
//package ute.shop.utils;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.Properties;
//
//public class DBConnect {
//    private static final String PROPS = "/db.properties";
//
//    private static Properties loadProps() {
//        try (InputStream in = DBConnect.class.getResourceAsStream(PROPS)) {
//            Properties p = new Properties();
//            p.load(in);
//            return p;
//        } catch (IOException e) {
//            throw new RuntimeException("Cannot load db.properties", e);
//        }
//    }
//
//    public static Connection getConnection() throws SQLException {
//        Properties p = loadProps();
//        return DriverManager.getConnection(p.getProperty("jdbc.url"), p.getProperty("jdbc.username"), p.getProperty("jdbc.password"));
//    }
//}
//
//# -------------------------
//# File: src/main/java/ute/shop/utils/PasswordHash.java
//# -------------------------
//package ute.shop.utils;
//
//import org.mindrot.jbcrypt.BCrypt;
//
//public class PasswordHash {
//    public static String hash(String plain) {
//        return BCrypt.hashpw(plain, BCrypt.gensalt(10));
//    }
//    public static boolean verify(String plain, String hashed) {
//        return BCrypt.checkpw(plain, hashed);
//    }
//}
//
//# -------------------------
//# File: src/main/java/ute/shop/utils/Validator.java
//# -------------------------
//package ute.shop.utils;
//
//public class Validator {
//    public static boolean notBlank(String s) { return s != null && !s.trim().isEmpty(); }
//}
//
//# -------------------------
//# Models: src/main/java/ute/shop/model/*.java
//# -------------------------
//package ute.shop.model;
//public class User {
//    private int id; private String email; private String passwordHash; private String fullName; private String role;
//    public int getId() {return id;} public void setId(int id){this.id=id;}
//    public String getEmail(){return email;} public void setEmail(String e){this.email=e;}
//    public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String p){this.passwordHash=p;}
//    public String getFullName(){return fullName;} public void setFullName(String f){this.fullName=f;}
//    public String getRole(){return role;} public void setRole(String r){this.role=r;}
//}
//
//package ute.shop.model;
//public class Category { private int id; private String name; public int getId(){return id;} public void setId(int id){this.id=id;} public String getName(){return name;} public void setName(String name){this.name=name;} }
//
//package ute.shop.model;
//public class Product {
//    private int id, categoryId, shopId; private String name, description, image; private double price;
//    public int getId(){return id;} public void setId(int id){this.id=id;}
//    public int getCategoryId(){return categoryId;} public void setCategoryId(int c){this.categoryId=c;}
//    public int getShopId(){return shopId;} public void setShopId(int s){this.shopId=s;}
//    public String getName(){return name;} public void setName(String n){this.name=n;}
//    public String getDescription(){return description;} public void setDescription(String d){this.description=d;}
//    public String getImage(){return image;} public void setImage(String i){this.image=i;}
//    public double getPrice(){return price;} public void setPrice(double p){this.price=p;}
//}
//
//package ute.shop.model;
//public class Shop { private int id, ownerId; private String name; private boolean active; public int getId(){return id;} public void setId(int id){this.id=id;} public int getOwnerId(){return ownerId;} public void setOwnerId(int o){this.ownerId=o;} public String getName(){return name;} public void setName(String n){this.name=n;} public boolean isActive(){return active;} public void setActive(boolean a){this.active=a;} }
//
//package ute.shop.model;
//public class Order { private int id, userId; private String status, paymentMethod; private double total; public int getId(){return id;} public void setId(int id){this.id=id;} public int getUserId(){return userId;} public void setUserId(int u){this.userId=u;} public String getStatus(){return status;} public void setStatus(String s){this.status=s;} public String getPaymentMethod(){return paymentMethod;} public void setPaymentMethod(String p){this.paymentMethod=p;} public double getTotal(){return total;} public void setTotal(double t){this.total=t;} }
//
//package ute.shop.model;
//public class OrderDetail { private int id, orderId, productId, quantity; private double price; public int getId(){return id;} public void setId(int id){this.id=id;} public int getOrderId(){return orderId;} public void setOrderId(int o){this.orderId=o;} public int getProductId(){return productId;} public void setProductId(int p){this.productId=p;} public int getQuantity(){return quantity;} public void setQuantity(int q){this.quantity=q;} public double getPrice(){return price;} public void setPrice(double p){this.price=p;} }
//
//# -------------------------
//# DAO Interfaces: src/main/java/ute/shop/dao/*.java
//# -------------------------
//package ute.shop.dao;
//import ute.shop.model.User;
//public interface IUserDao {
//    User findByEmail(String email);
//    boolean create(User u);
//}
//
//package ute.shop.dao;
//import ute.shop.model.Product; import java.util.List;
//public interface IProductDao {
//    List<Product> findAll();
//    Product findById(int id);
//    boolean create(Product p);
//}
//
//package ute.shop.dao;
//import ute.shop.model.Order; import java.util.List;
//public interface IOrderDao { int create(Order o); List<Order> findByUser(int userId); }
//
//package ute.shop.dao;
//import ute.shop.model.Category; import java.util.List;
//public interface ICategoryDao { List<Category> findAll(); }
//
//# -------------------------
//# DAO Impl: src/main/java/ute/shop/dao/impl/*.java (minimal JDBC)
//# -------------------------
//package ute.shop.dao.impl;
//import ute.shop.dao.IUserDao; import ute.shop.model.User; import ute.shop.utils.DBConnect;
//import java.sql.*;
//public class UserDaoImpl implements IUserDao {
//    @Override public User findByEmail(String email){
//        String sql="SELECT id,email,password_hash,full_name,role FROM users WHERE email=?";
//        try(Connection c=DBConnect.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
//            ps.setString(1,email); try(ResultSet rs=ps.executeQuery()){ if(rs.next()){ User u=new User(); u.setId(rs.getInt("id")); u.setEmail(rs.getString("email")); u.setPasswordHash(rs.getString("password_hash")); u.setFullName(rs.getString("full_name")); u.setRole(rs.getString("role")); return u; } }
//        }catch(Exception e){e.printStackTrace();}
//        return null;
//    }
//    @Override public boolean create(User u){
//        String sql="INSERT INTO users(email,password_hash,full_name,role) VALUES(?,?,?,?)";
//        try(Connection c=DBConnect.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
//            ps.setString(1,u.getEmail()); ps.setString(2,u.getPasswordHash()); ps.setString(3,u.getFullName()); ps.setString(4,u.getRole());
//            return ps.executeUpdate()>0;
//        }catch(Exception e){e.printStackTrace();}
//        return false;
//    }
//}
//
//package ute.shop.dao.impl;
//import ute.shop.dao.IProductDao; import ute.shop.model.Product; import ute.shop.utils.DBConnect; import java.sql.*; import java.util.*;
//public class ProductDaoImpl implements IProductDao {
//    @Override public List<Product> findAll(){ List<Product> list=new ArrayList<>(); String sql="SELECT id,name,price,image,description,category_id,shop_id FROM products ORDER BY id DESC"; try(Connection c=DBConnect.getConnection(); PreparedStatement ps=c.prepareStatement(sql); ResultSet rs=ps.executeQuery()){ while(rs.next()){ Product p=new Product(); p.setId(rs.getInt("id")); p.setName(rs.getString("name")); p.setPrice(rs.getDouble("price")); p.setImage(rs.getString("image")); p.setDescription(rs.getString("description")); p.setCategoryId(rs.getInt("category_id")); p.setShopId(rs.getInt("shop_id")); list.add(p);} }catch(Exception e){e.printStackTrace();} return list; }
//    @Override public Product findById(int id){ String sql="SELECT id,name,price,image,description,category_id,shop_id FROM products WHERE id=?"; try(Connection c=DBConnect.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){ ps.setInt(1,id); try(ResultSet rs=ps.executeQuery()){ if(rs.next()){ Product p=new Product(); p.setId(rs.getInt("id")); p.setName(rs.getString("name")); p.setPrice(rs.getDouble("price")); p.setImage(rs.getString("image")); p.setDescription(rs.getString("description")); p.setCategoryId(rs.getInt("category_id")); p.setShopId(rs.getInt("shop_id")); return p; } } }catch(Exception e){e.printStackTrace();} return null; }
//    @Override public boolean create(Product p){ String sql="INSERT INTO products(name,price,image,description,category_id,shop_id) VALUES(?,?,?,?,?,?)"; try(Connection c=DBConnect.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){ ps.setString(1,p.getName()); ps.setDouble(2,p.getPrice()); ps.setString(3,p.getImage()); ps.setString(4,p.getDescription()); ps.setInt(5,p.getCategoryId()); ps.setInt(6,p.getShopId()); return ps.executeUpdate()>0; }catch(Exception e){e.printStackTrace();} return false; }
//}
//
//package ute.shop.dao.impl;
//import ute.shop.dao.IOrderDao; import ute.shop.model.Order; import ute.shop.utils.DBConnect; import java.sql.*; import java.util.*;
//public class OrderDaoImpl implements IOrderDao {
//    @Override public int create(Order o){ String sql="INSERT INTO orders(user_id,status,payment_method,total) VALUES(?,?,?,?)"; try(Connection c=DBConnect.getConnection(); PreparedStatement ps=c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){ ps.setInt(1,o.getUserId()); ps.setString(2,o.getStatus()); ps.setString(3,o.getPaymentMethod()); ps.setDouble(4,o.getTotal()); ps.executeUpdate(); try(ResultSet rs=ps.getGeneratedKeys()){ if(rs.next()) return rs.getInt(1);} }catch(Exception e){e.printStackTrace();} return 0; }
//    @Override public List<Order> findByUser(int userId){ List<Order> list=new ArrayList<>(); String sql="SELECT id,user_id,status,payment_method,total FROM orders WHERE user_id=? ORDER BY id DESC"; try(Connection c=DBConnect.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){ ps.setInt(1,userId); try(ResultSet rs=ps.executeQuery()){ while(rs.next()){ Order o=new Order(); o.setId(rs.getInt("id")); o.setUserId(rs.getInt("user_id")); o.setStatus(rs.getString("status")); o.setPaymentMethod(rs.getString("payment_method")); o.setTotal(rs.getDouble("total")); list.add(o);} } }catch(Exception e){e.printStackTrace();} return list; }
//}
//
//package ute.shop.dao.impl;
//import ute.shop.dao.ICategoryDao; import ute.shop.model.Category; import ute.shop.utils.DBConnect; import java.sql.*; import java.util.*;
//public class CategoryDaoImpl implements ICategoryDao { @Override public List<Category> findAll(){ List<Category> list=new ArrayList<>(); String sql="SELECT id,name FROM categories ORDER BY name"; try(Connection c=DBConnect.getConnection(); PreparedStatement ps=c.prepareStatement(sql); ResultSet rs=ps.executeQuery()){ while(rs.next()){ Category c1=new Category(); c1.setId(rs.getInt("id")); c1.setName(rs.getString("name")); list.add(c1);} }catch(Exception e){e.printStackTrace();} return list; } }
//
//# -------------------------
//# Service Interfaces & Impl: src/main/java/ute/shop/service/*
//# -------------------------
//package ute.shop.service; import ute.shop.model.User; public interface IUserService { User login(String email, String password); boolean register(String email,String password,String fullName); }
//
//package ute.shop.service; import ute.shop.model.Product; import java.util.List; public interface IProductService { List<Product> all(); Product byId(int id); boolean create(Product p); }
//
//package ute.shop.service; import ute.shop.model.Order; import java.util.List; public interface IOrderService { int create(Order o); List<Order> byUser(int userId); }
//
//package ute.shop.service.impl;
//import ute.shop.service.IUserService; import ute.shop.dao.IUserDao; import ute.shop.dao.impl.UserDaoImpl; import ute.shop.model.User; import ute.shop.utils.PasswordHash;
//public class UserServiceImpl implements IUserService {
//    private final IUserDao userDao = new UserDaoImpl();
//    @Override public User login(String email, String password){ User u = userDao.findByEmail(email); if(u!=null && PasswordHash.verify(password, u.getPasswordHash())) return u; return null; }
//    @Override public boolean register(String email,String password,String fullName){ User exist = userDao.findByEmail(email); if(exist!=null) return false; User u=new User(); u.setEmail(email); u.setPasswordHash(PasswordHash.hash(password)); u.setFullName(fullName); u.setRole("USER"); return userDao.create(u); }
//}
//
//package ute.shop.service.impl;
//import ute.shop.service.IProductService; import ute.shop.dao.IProductDao; import ute.shop.dao.impl.ProductDaoImpl; import ute.shop.model.Product; import java.util.List;
//public class ProductServiceImpl implements IProductService { private final IProductDao dao=new ProductDaoImpl(); public List<Product> all(){return dao.findAll();} public Product byId(int id){return dao.findById(id);} public boolean create(Product p){return dao.create(p);} }
//
//package ute.shop.service.impl;
//import ute.shop.service.IOrderService; import ute.shop.dao.IOrderDao; import ute.shop.dao.impl.OrderDaoImpl; import ute.shop.model.Order; import java.util.List;
//public class OrderServiceImpl implements IOrderService { private final IOrderDao dao=new OrderDaoImpl(); public int create(Order o){return dao.create(o);} public List<Order> byUser(int userId){return dao.findByUser(userId);} }
//
//# -------------------------
//# Filters: src/main/java/ute/shop/filter/*.java
//# -------------------------
//package ute.shop.filter;
//import jakarta.servlet.*; import jakarta.servlet.annotation.WebFilter; import jakarta.servlet.http.*; import java.io.IOException;
//@WebFilter(filterName="EncodingFilter", urlPatterns={"/*"})
//public class EncodingFilter implements Filter {
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException { req.setCharacterEncoding("UTF-8"); res.setCharacterEncoding("UTF-8"); chain.doFilter(req,res);} }
//
//package ute.shop.filter;
//import jakarta.servlet.*; import jakarta.servlet.annotation.WebFilter; import jakarta.servlet.http.*; import java.io.IOException;
//@WebFilter(filterName="AuthFilter", urlPatterns={"/checkout","/order/*","/shop/*","/admin/*"})
//public class AuthFilter implements Filter {
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest req=(HttpServletRequest)request; HttpServletResponse resp=(HttpServletResponse)response;
//        HttpSession session=req.getSession(false);
//        boolean logged = session!=null && session.getAttribute("USER")!=null;
//        if(!logged){ resp.sendRedirect(req.getContextPath()+"/login"); return; }
//        chain.doFilter(request,response);
//    }
//}
//
//# -------------------------
//# Controllers: src/main/java/ute/shop/controller/*.java
//# -------------------------
//package ute.shop.controller;
//import jakarta.servlet.*; import jakarta.servlet.annotation.WebServlet; import jakarta.servlet.http.*; import java.io.IOException; import ute.shop.service.IUserService; import ute.shop.service.impl.UserServiceImpl; import ute.shop.model.User;
//@WebServlet(urlPatterns={"/login","/register","/logout"})
//public class AuthController extends HttpServlet {
//    private final IUserService userService = new UserServiceImpl();
//    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String uri=req.getServletPath(); if("/logout".equals(uri)){ req.getSession().invalidate(); resp.sendRedirect(req.getContextPath()+"/"); return; }
//        if("/register".equals(uri)) req.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(req,resp);
//        else req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req,resp);
//    }
//    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String uri=req.getServletPath();
//        if("/register".equals(uri)){
//            String email=req.getParameter("email"), pass=req.getParameter("password"), name=req.getParameter("fullName");
//            boolean ok = userService.register(email,pass,name);
//            if(ok) resp.sendRedirect(req.getContextPath()+"/login"); else { req.setAttribute("error","Email đã tồn tại"); doGet(req,resp);} return;
//        }
//        // login
//        String email=req.getParameter("email"), pass=req.getParameter("password");
//        User u = userService.login(email,pass);
//        if(u!=null){ req.getSession(true).setAttribute("USER",u); resp.sendRedirect(req.getContextPath()+"/"); }
//        else { req.setAttribute("error","Sai tài khoản hoặc mật khẩu"); doGet(req,resp);}    }
//}
//
//package ute.shop.controller;
//import jakarta.servlet.*; import jakarta.servlet.annotation.WebServlet; import jakarta.servlet.http.*; import java.io.IOException; import java.util.*; import ute.shop.service.IProductService; import ute.shop.service.impl.ProductServiceImpl; import ute.shop.model.Product;
//@WebServlet(urlPatterns={"/products","/product"})
//public class CategoryProductController extends HttpServlet {
//    private final IProductService productService = new ProductServiceImpl();
//    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String id=req.getParameter("id");
//        if(id!=null){ Product p=productService.byId(Integer.parseInt(id)); req.setAttribute("p",p); req.getRequestDispatcher("/WEB-INF/views/product/detail.jsp").forward(req,resp); return; }
//        List<Product> list = productService.all(); req.setAttribute("list", list); req.getRequestDispatcher("/WEB-INF/views/product/list.jsp").forward(req,resp);
//    }
//}
//
//package ute.shop.controller;
//import jakarta.servlet.*; import jakarta.servlet.annotation.WebServlet; import jakarta.servlet.http.*; import java.io.IOException; import java.util.*; import ute.shop.model.Product; import ute.shop.service.impl.ProductServiceImpl;
//@WebServlet(urlPatterns={"/cart","/cart/add","/cart/remove"})
//public class CartController extends HttpServlet {
//    @SuppressWarnings("unchecked")
//    private Map<Integer,Integer> cart(HttpSession s){ Object o=s.getAttribute("CART"); if(o==null){ Map<Integer,Integer> m=new HashMap<>(); s.setAttribute("CART",m); return m;} return (Map<Integer,Integer>)o; }
//    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { req.getRequestDispatcher("/WEB-INF/views/order/checkout.jsp").forward(req,resp); }
//    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String uri=req.getServletPath(); HttpSession session=req.getSession(true); Map<Integer,Integer> m=cart(session);
//        if("/cart/add".equals(uri)){ int id=Integer.parseInt(req.getParameter("id")); m.put(id, m.getOrDefault(id,0)+1); }
//        if("/cart/remove".equals(uri)){ int id=Integer.parseInt(req.getParameter("id")); m.remove(id); }
//        resp.sendRedirect(req.getContextPath()+"/cart");
//    }
//}
//
//package ute.shop.controller;
//import jakarta.servlet.*; import jakarta.servlet.annotation.WebServlet; import jakarta.servlet.http.*; import java.io.IOException; import java.util.*; import ute.shop.model.*; import ute.shop.service.IOrderService; import ute.shop.service.impl.OrderServiceImpl; import ute.shop.service.impl.ProductServiceImpl;
//@WebServlet(urlPatterns={"/checkout","/orders"})
//public class DeliveryController extends HttpServlet {
//    private final IOrderService orderService = new OrderServiceImpl();
//    @SuppressWarnings("unchecked")
//    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        User u=(User)req.getSession().getAttribute("USER"); if(u==null){ resp.sendRedirect(req.getContextPath()+"/login"); return; }
//        String method=req.getParameter("payment"); Map<Integer,Integer> cart=(Map<Integer,Integer>)req.getSession().getAttribute("CART"); if(cart==null||cart.isEmpty()){ resp.sendRedirect(req.getContextPath()+"/products"); return; }
//        double total=cart.entrySet().stream().mapToDouble(e->{ Product p=new ProductServiceImpl().byId(e.getKey()); return p.getPrice()*e.getValue(); }).sum();
//        Order o=new Order(); o.setUserId(u.getId()); o.setPaymentMethod(method==null?"COD":method); o.setStatus("PENDING"); o.setTotal(total);
//        int orderId=orderService.create(o); req.getSession().removeAttribute("CART"); resp.sendRedirect(req.getContextPath()+"/orders");
//    }
//    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        User u=(User)req.getSession().getAttribute("USER"); if(u==null){ resp.sendRedirect(req.getContextPath()+"/login"); return; }
//        req.setAttribute("orders", orderService.byUser(u.getId())); req.getRequestDispatcher("/WEB-INF/views/order/history.jsp").forward(req,resp);
//    }
//}
//
//package ute.shop.controller;
//import jakarta.servlet.*; import jakarta.servlet.annotation.WebServlet; import jakarta.servlet.http.*; import java.io.IOException;
//@WebServlet(urlPatterns={"/shop","/admin"})
//public class ShopController extends HttpServlet {
//    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String uri=req.getServletPath(); if("/admin".equals(uri)) req.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(req,resp); else req.getRequestDispatcher("/WEB-INF/views/shop/dashboard.jsp").forward(req,resp);
//    }
//}
//
//# -------------------------
//# WEB XML: src/main/webapp/WEB-INF/web.xml
//# -------------------------
//<?xml version="1.0" encoding="UTF-8"?>
//<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
//         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
//         xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd"
//         version="6.0">
//  <display-name>uteshop</display-name>
//  <welcome-file-list>
//    <welcome-file>index.jsp</welcome-file>
//  </welcome-file-list>
//</web-app>
//
//# -------------------------
//# JSP Views (Bootstrap minimal)
//# -------------------------
//# File: src/main/webapp/index.jsp
//<%@ page contentType="text/html; charset=UTF-8" %>
//<%@ taglib uri="https://jakarta.ee/jstl/core" prefix="c" %>
//<!DOCTYPE html><html><head><meta charset="UTF-8"><title>UTE Shop</title>
//<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"/>
//</head><body class="container py-4">
//  <nav class="mb-3 d-flex gap-2">
//    <a class="btn btn-link" href="${pageContext.request.contextPath}/">Home</a>
//    <a class="btn btn-link" href="${pageContext.request.contextPath}/products">Products</a>
//    <a class="btn btn-link" href="${pageContext.request.contextPath}/cart">Cart</a>
//    <c:choose>
//      <c:when test="${not empty sessionScope.USER}">
//        <span class="ms-auto">Xin chào, ${sessionScope.USER.fullName}</span>
//        <a class="btn btn-sm btn-outline-secondary" href="${pageContext.request.contextPath}/orders">Đơn hàng</a>
//        <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
//      </c:when>
//      <c:otherwise>
//        <a class="ms-auto btn btn-primary" href="${pageContext.request.contextPath}/login">Đăng nhập</a>
//      </c:otherwise>
//    </c:choose>
//  </nav>
//  <div class="p-5 bg-light rounded-3">
//    <h1 class="display-6">UTE Shop (Servlet/JSP Demo)</h1>
//    <p class="lead">Danh mục, sản phẩm, giỏ hàng, đặt hàng cơ bản.</p>
//    <a class="btn btn-success" href="${pageContext.request.contextPath}/products">Mua ngay</a>
//  </div>
//</body></html>
//
//# File: src/main/webapp/WEB-INF/views/auth/login.jsp
//<%@ page contentType="text/html; charset=UTF-8" %>
//<!DOCTYPE html><html><head><meta charset="UTF-8"><title>Đăng nhập</title>
//<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"/>
//</head><body class="container py-4">
//  <h2>Đăng nhập</h2>
//  <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
//  <form method="post" action="${pageContext.request.contextPath}/login" class="col-4">
//    <div class="mb-3"><label>Email</label><input name="email" type="email" class="form-control" required></div>
//    <div class="mb-3"><label>Mật khẩu</label><input name="password" type="password" class="form-control" required></div>
//    <button class="btn btn-primary">Đăng nhập</button>
//    <a class="btn btn-link" href="${pageContext.request.contextPath}/register">Đăng ký</a>
//  </form>
//</body></html>
//
//# File: src/main/webapp/WEB-INF/views/auth/register.jsp
//<%@ page contentType="text/html; charset=UTF-8" %>
//<!DOCTYPE html><html><head><meta charset="UTF-8"><title>Đăng ký</title>
//<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"/>
//</head><body class="container py-4">
//  <h2>Đăng ký</h2>
//  <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
//  <form method="post" action="${pageContext.request.contextPath}/register" class="col-4">
//    <div class="mb-3"><label>Họ tên</label><input name="fullName" class="form-control" required></div>
//    <div class="mb-3"><label>Email</label><input name="email" type="email" class="form-control" required></div>
//    <div class="mb-3"><label>Mật khẩu</label><input name="password" type="password" class="form-control" required></div>
//    <button class="btn btn-success">Tạo tài khoản</button>
//  </form>
//</body></html>
//
//# File: src/main/webapp/WEB-INF/views/product/list.jsp
//<%@ page contentType="text/html; charset=UTF-8" %>
//<%@ taglib uri="https://jakarta.ee/jstl/core" prefix="c" %>
//<!DOCTYPE html><html><head><meta charset="UTF-8"><title>Sản phẩm</title>
//<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"/>
//</head><body class="container py-4">
//  <h2>Danh sách sản phẩm</h2>
//  <div class="row g-3">
//    <c:forEach var="p" items="${list}">
//      <div class="col-12 col-md-4">
//        <div class="card h-100">
//          <img class="card-img-top" src="${empty p.image ? 'https://placehold.co/600x400' : p.image}" alt="">
//          <div class="card-body">
//            <h5 class="card-title">${p.name}</h5>
//            <p class="card-text fw-bold">${p.price} đ</p>
//            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/product?id=${p.id}">Chi tiết</a>
//            <form method="post" action="${pageContext.request.contextPath}/cart/add" class="d-inline">
//              <input type="hidden" name="id" value="${p.id}">
//              <button class="btn btn-success">Thêm vào giỏ</button>
//            </form>
//          </div>
//        </div>
//      </div>
//    </c:forEach>
//  </div>
//</body></html>
//
//# File: src/main/webapp/WEB-INF/views/product/detail.jsp
//<%@ page contentType="text/html; charset=UTF-8" %>
//<!DOCTYPE html><html><head><meta charset="UTF-8"><title>Chi tiết</title>
//<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"/>
//</head><body class="container py-4">
//  <h2>${p.name}</h2>
//  <img class="mb-3" style="max-width:400px" src="${empty p.image ? 'https://placehold.co/600x400' : p.image}">
//  <p class="lead">${p.description}</p>
//  <p class="fw-bold">Giá: ${p.price} đ</p>
//  <form method="post" action="${pageContext.request.contextPath}/cart/add">
//    <input type="hidden" name="id" value="${p.id}">
//    <button class="btn btn-success">Thêm vào giỏ</button>
//  </form>
//</body></html>
//
//# File: src/main/webapp/WEB-INF/views/order/checkout.jsp
//<%@ page contentType="text/html; charset=UTF-8" %>
//<%@ taglib uri="https://jakarta.ee/jstl/core" prefix="c" %>
//<!DOCTYPE html><html><head><meta charset="UTF-8"><title>Giỏ hàng</title>
//<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"/>
//</head><body class="container py-4">
//  <h2>Giỏ hàng</h2>
//  <c:set var="service" value="${pageContext.request.contextPath}"/>
//  <c:set var="productService" value="new ute.shop.service.impl.ProductServiceImpl()"/>
//  <c:set var="cart" value="${sessionScope.CART}"/>
//  <c:choose>
//    <c:when test="${empty cart}">
//      <div class="alert alert-info">Giỏ hàng trống.</div>
//    </c:when>
//    <c:otherwise>
//      <table class="table">
//        <thead><tr><th>Tên</th><th>SL</th><th>Giá</th><th>Tổng</th><th></th></tr></thead>
//        <tbody>
//          <c:set var="sum" value="0"/>
//          <c:forEach var="e" items="${cart}">
//            <c:set var="p" value="${productService.byId(e.key)}"/>
//            <tr>
//              <td>${p.name}</td>
//              <td>${e.value}</td>
//              <td>${p.price}</td>
//              <td><c:set var="line" value="${p.price * e.value}"/>${line}</td>
//              <td>
//                <form method="post" action="${pageContext.request.contextPath}/cart/remove">
//                  <input type="hidden" name="id" value="${p.id}">
//                  <button class="btn btn-sm btn-outline-danger">Xóa</button>
//                </form>
//              </td>
//            </tr>
//            <c:set var="sum" value="${sum + line}"/>
//          </c:forEach>
//        </tbody>
//        <tfoot>
//          <tr><th colspan="3" class="text-end">Tổng:</th><th>${sum}</th><th></th></tr>
//        </tfoot>
//      </table>
//      <form method="post" action="${pageContext.request.contextPath}/checkout" class="col-4">
//        <label class="form-label">Phương thức thanh toán (fake)</label>
//        <select class="form-select" name="payment">
//          <option value="COD">COD</option>
//          <option value="MOMO">MoMo (fake)</option>
//          <option value="VNPAY">VNPAY (fake)</option>
//        </select>
//        <button class="btn btn-primary mt-3">Đặt hàng</button>
//      </form>
//    </c:otherwise>
//  </c:choose>
//</body></html>
//
//# File: src/main/webapp/WEB-INF/views/order/history.jsp
//<%@ page contentType="text/html; charset=UTF-8" %>
//<%@ taglib uri="https://jakarta.ee/jstl/core" prefix="c" %>
//<!DOCTYPE html><html><head><meta charset="UTF-8"><title>Đơn hàng</title>
//<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"/>
//</head><body class="container py-4">
//  <h2>Lịch sử đơn hàng</h2>
//  <c:choose>
//    <c:when test="${empty orders}"><div class="alert alert-info">Chưa có đơn hàng.</div></c:when>
//    <c:otherwise>
//      <table class="table"><thead><tr><th>ID</th><th>Trạng thái</th><th>Thanh toán</th><th>Tổng</th></tr></thead>
//      <tbody>
//        <c:forEach var="o" items="${orders}">
//          <tr><td>#${o.id}</td><td>${o.status}</td><td>${o.paymentMethod}</td><td>${o.total}</td></tr>
//        </c:forEach>
//      </tbody></table>
//    </c:otherwise>
//  </c:choose>
//</body></html>
//
//# File: src/main/webapp/WEB-INF/views/shop/dashboard.jsp
//<%@ page contentType="text/html; charset=UTF-8" %>
//<!DOCTYPE html><html><head><meta charset="UTF-8"><title>Shop Dashboard</title>
//<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"/>
//</head><body class="container py-4"><h2>Shop Dashboard</h2><p>TODO: quản lý sản phẩm của Vendor</p></body></html>
//
//# File: src/main/webapp/WEB-INF/views/admin/dashboard.jsp
//<%@ page contentType="text/html; charset=UTF-8" %>
//<!DOCTYPE html><html><head><meta charset="UTF-8"><title>Admin</title>
//<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"/>
//</head><body class="container py-4"><h2>Admin Dashboard</h2><p>TODO: quản lý users/shops/orders</p></body></html>
//
//# -------------------------
//# (Optional) Tests placeholders: src/test/java/*
//# -------------------------
//// TODO: Add JUnit tests for DAO & Service
