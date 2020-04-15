package model;

/**
 * Class Client describes the attributes of a client, and have getters and setters for Client's attributes
 */
public class Client {
	private int idClient;
	private String name;
	private String address;
	private String city;
	private String email;
	private String telNumber;

	public Client() {}
	
	public Client(String name, String address, String city, String email, String telNumber) {
		this.name = name;
		this.address = address;
		this.city=city;
		this.email = email;
		this.telNumber = telNumber;
	}
	
	public int getIdClient() {
		return this.idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public String getClientName() {
		return this.name;
	}

	public void setClientName(String name) {
		this.name = name;
	}

	public String getClientAddress() {
		return this.address;
	}

	public void setClientAddress(String address) {
		this.address = address;
	}
	
	public String getClientCity() {
		return this.city;
	}

	public void setClientCity(String city) {
		this.city = city;
	}
	
	public String getClientEmail() {
		return this.email;
	}

	public void setClientEmail(String email) {
		this.email = email;
	}
	
	public String getClientTelNumber() {
		return this.telNumber;
	}

	public void setClientTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

}
