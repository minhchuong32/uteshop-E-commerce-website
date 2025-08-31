package ute.shop.models;

public class Product {
	private int product_id;
	private int shop_id;
	private int category_id;
	private String name;
	private double price;
	private int stock;
	private String description;
	private String image_url;
	
	
	public Product(int product_id, int shop_id, int category_id, String name, double price, int stock,
			String description, String image_url) {
		super();
		this.product_id = product_id;
		this.shop_id = shop_id;
		this.category_id = category_id;
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.description = description;
		this.image_url = image_url;
	}


	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}


	public int getProduct_id() {
		return product_id;
	}


	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}


	public int getShop_id() {
		return shop_id;
	}


	public void setShop_id(int shop_id) {
		this.shop_id = shop_id;
	}


	public int getCategory_id() {
		return category_id;
	}


	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public int getStock() {
		return stock;
	}


	public void setStock(int stock) {
		this.stock = stock;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getImage_url() {
		return image_url;
	}


	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}


	@Override
	public String toString() {
		return "Product [product_id=" + product_id + ", shop_id=" + shop_id + ", category_id=" + category_id + ", name="
				+ name + ", price=" + price + ", stock=" + stock + ", description=" + description + ", image_url="
				+ image_url + "]";
	}
	
	
	
}
