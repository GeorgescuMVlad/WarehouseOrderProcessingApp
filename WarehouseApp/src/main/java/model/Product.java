package model;

/**
 * Class Product describes the attributes of a product, and have getters and setters for Product's attributes
 */
public class Product {
	
	private int idProduct;
	private String name;
	private int quantity;
	private double price;
	
	public Product() {}
	
	public Product(String name, int quantity, double price) {
		this.name=name;
		this.quantity=quantity;
		this.price=price;
	}

	public int getIdProduct() {
		return this.idProduct;
	}

	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}
	
	public String getProductName() {
		return this.name;
	}

	public void setProductName(String name) {
		this.name = name;
	}
	
	public int getProductQuantity() {
		return this.quantity;
	}
	
	public void setProductQuantity(int quantity) {
		this.quantity=quantity;
	}
	
	public double getProductPrice(){
		return this.price;
	}
	
	public void setProductPrice(double price) {
		this.price=price;
	}
	
}
