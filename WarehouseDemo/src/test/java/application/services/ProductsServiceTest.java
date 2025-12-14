package application.services;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import application.data.ProductsDataAccessInterface;
import application.models.ProductModel;

@ExtendWith(MockitoExtension.class)
public class ProductsServiceTest {

	@Mock
	private ProductsDataAccessInterface<ProductModel> productsDAO;
	
	@InjectMocks
	private ProductsService productsService;
	
	@Test
	public void getById() {
		ProductModel mockProduct = new ProductModel(null, "p-011", "Box", 10.0f, 3);
		when(productsDAO.getById(Mockito.any(Long.class))).thenReturn(mockProduct);
		
		ProductModel newProduct = productsService.GetById(1L);
		Assertions.assertThat(newProduct).isNotNull();
	}
	
	@Test
	public void getProducts() {
		List<ProductModel> mockProducts = new ArrayList<>();
		when(productsDAO.getProducts()).thenReturn(mockProducts);
		
		List<ProductModel> products = productsService.getProducts();
		Assertions.assertThat(products).isNotNull();
	}
	
	@Test
	public void searchProducts() {
		List<ProductModel> mockProducts = new ArrayList<>();
		when(productsDAO.searchProducts(Mockito.any(String.class))).thenReturn(mockProducts);
		
		List<ProductModel> products = productsService.searchProducts("text");
		Assertions.assertThat(products).isNotNull();
	}
	
	@Test
	public void addProduct() {
		Long mockId = 1L;
		when(productsDAO.addProduct(Mockito.any(ProductModel.class))).thenReturn(mockId);
		ProductModel newProduct = new ProductModel(null, "p-011", "Box", 10.0f, 3);
		
		Long newId = productsService.addProduct(newProduct);
		Assertions.assertThat(newId).isNotNull();
	}
	
	@Test
	public void deleteProduct() {
		Boolean mockDeleted = true;
		when(productsDAO.deleteProduct(Mockito.any(Long.class))).thenReturn(mockDeleted);
		
		Boolean deleted = productsService.deleteProduct(1L);
		Assertions.assertThat(deleted).isNotNull();
	}

	@Test
	public void updateProduct() {
		ProductModel mockProduct = new ProductModel(null, "p-011", "Box", 10.0f, 3);
		Long existingID = 1L;
		Long missingID = -1L;
		
		ProductModel toUpdateProduct = new ProductModel(null, "p-011", "Box", 10.0f, 3);
		when(productsDAO.updateProduct(Mockito.any(ProductModel.class))).thenReturn(mockProduct);
		when(productsDAO.getById(existingID)).thenReturn(mockProduct);
		when(productsDAO.getById(missingID)).thenReturn(null);
		
		ProductModel updatedProduct = productsService.updateProduct(existingID, toUpdateProduct);
		Assertions.assertThat(updatedProduct).isNotNull();
		
		assertThatThrownBy(() ->
	        productsService.updateProduct(missingID, toUpdateProduct)
	    )
	    .isInstanceOf(Exception.class)
	    .hasMessageContaining("not found").hasMessageContaining(Long.toString(missingID) );
	}
}
