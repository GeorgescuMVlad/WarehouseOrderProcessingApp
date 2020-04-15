package model;

/**
 * Class Order describes the attributes of an order, and have getters and setters for Order's attributes
 */
public class Order {
	
	private int idOrder;
	private int idClient;
	private int idProduct;
	private double total;
	private double price;
	
	public Order(int  idClient, int idProduct, double total, double price){
		this.idClient=idClient;
		this.idProduct=idProduct;
		this.total=total;
		this.setPrice(price);
	}
	
	public int getIdOrder() {
		return this.idOrder;
	}

	public void setOrderIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}
	
	public int getOrderIdClient() {
		return this.idClient;
	}
	
	public void setOrderIdClient(int idClient) {
		this.idClient=idClient;
	}
	
	public int getOrderIdProduct() {
		return this.idProduct;
	}
	
	public void setOrderIdProduct(int idProduct) {
		this.idProduct=idProduct;
	}

	public double getTotal() {
		return this.total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}
