package application.models;

public class ProductModel {

	Long id = 0L;
	String productNumber = "";
	String productName = "";
	float price = 0;
	int quantity = 0;

	public ProductModel() {
		super();
	}

	public ProductModel(Long id, String productNumber, String productName, float price, int quantity) {
		super();
		this.id = id;
		this.productNumber = productNumber;
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
