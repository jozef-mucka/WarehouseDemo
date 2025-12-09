package application.models;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

@Valid
public class ProductModel {

	Long id = 0L;
	@Pattern(regexp = "^p-.*$", message = "{product.productNumber.startsWith}")
	String productNumber = "p-";
	@NotBlank
	String productName = "";
	@NotNull
	@PositiveOrZero
	float price = 0;
	@NotNull
	@PositiveOrZero
	int quantity = 0;

	@Override
	public String toString() {
		return "ProductModel [id=" + id + ", productNumber=" + productNumber + ", productName=" + productName
				+ ", price=" + price + ", quantity=" + quantity + "]";
	}

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
