package application.data;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import application.models.ProductModel;

@SpringBootTest
@ActiveProfiles("test")
public class ProductsRepositoryTests {

	@Autowired
	private ProductsRepositoryDAO productsDAO;
	
	@Test
	@Transactional
	public void addProduct() {
		ProductModel newproduct = new ProductModel(null, "p-011", "Box", 10.0f, 3);
		Long id = productsDAO.addProduct(newproduct);
		Assertions.assertThat(id).isNotNull();
	}
	
	@Test
	@Transactional
	public void getById() {
		ProductModel newProduct = new ProductModel(null, "p-011", "Box", 10.0f, 3);
		Long id = productsDAO.addProduct(newProduct);
		ProductModel foundProduct = productsDAO.getById(id);
		Assertions.assertThat(foundProduct).isNotNull();
	}
	
	@Test
	@Transactional
	public void getProducts() {
		productsDAO.addProduct(new ProductModel(null, "p-011", "Box", 10.0f, 3));
		productsDAO.addProduct(new ProductModel(null, "p-012", "Ball", 20.0f, 4));
		
		List<ProductModel> products = productsDAO.getProducts();
		Assertions.assertThat(products).isNotNull();
		Assertions.assertThat(products.size()).isEqualTo(2);
	}
	
	@Test
	@Transactional
	public void searchProducts() {
		productsDAO.addProduct(new ProductModel(null, "p-011", "Box", 10.0f, 3));
		productsDAO.addProduct(new ProductModel(null, "p-012", "Ball", 20.0f, 4));
		String term1 = "o";
		String term2 = "al";
		String term3 = "xx";
		
		List<ProductModel> products = productsDAO.searchProducts(term1);
		Assertions.assertThat(products).isNotNull();
		Assertions.assertThat(products.getFirst().getProductName()).contains(term1);
		
		products = productsDAO.searchProducts(term2);
		Assertions.assertThat(products).isNotEmpty();
		Assertions.assertThat(products.getFirst().getProductName()).contains(term2);
		
		products = productsDAO.searchProducts(term3);
		Assertions.assertThat(products).isEmpty();
	}
	
	@Test
	@Transactional
	public void updateProduct() {
		Long id = productsDAO.addProduct(new ProductModel(null, "p-011", "Box", 10.0f, 3));
		ProductModel inProduct = new ProductModel(id, "p-012", "Ball", 20.0f, 4);
		
		productsDAO.updateProduct(inProduct);
		ProductModel outProduct = productsDAO.getById(id);
		
		Assertions.assertThat(outProduct.getProductNumber()).isEqualTo(inProduct.getProductNumber());
		Assertions.assertThat(outProduct.getProductName()).isEqualTo(inProduct.getProductName());
		Assertions.assertThat(outProduct.getPrice()).isEqualTo(inProduct.getPrice());
		Assertions.assertThat(outProduct.getQuantity()).isEqualTo(inProduct.getQuantity());		
	}
	
	@Test
	@Transactional
	public void deleteProduct() {
		Long id = productsDAO.addProduct(new ProductModel(null, "p-011", "Box", 10.0f, 3));
		ProductModel outProduct = productsDAO.getById(id);
		Assertions.assertThat(outProduct).isNotNull();
		productsDAO.deleteProduct(id);
		outProduct = productsDAO.getById(id);
		Assertions.assertThat(outProduct).isNull();
	}
}
