package application.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("T_PRODUCTS")
public class ProductEntity {
	
	@Id
	@Column("ID")
	Long id = 0L;
	
	@Column("PRODUCT_NUMBER")
	String productNumber = "";
	
	@Column("PRODUCT_NAME")
	String productName = "";
	
	@Column("PRICE")
	float price = 0;
	
	@Column("QUANTITY")
	int quantity = 0;
	
	@Override
	public String toString() {
		return "OrderModel [id=" + id + ", productName=" + productName + ", productName=" + productName + ", price="
				+ price + ", quantity=" + quantity + "]";
	}
	
	public ProductEntity() {
		//super();
	}

	public ProductEntity(Long id, String productNumber, String productName, float price, int quantity) {
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
