package application.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import application.data.ProductsDataAccessInterface;
import application.models.ProductModel;
import application.services.ProductsServiceInterface;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ProductsRestController.class)
@AutoConfigureMockMvc
public class ProductsRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockitoBean
	private ProductsServiceInterface<ProductModel> productsService;

	// SpringConfig wants these 2 vvv
	@MockitoBean
	DataSource dataSource;

	@MockitoBean
	ProductsDataAccessInterface<ProductModel> productsDAO;
	// SpringConfig wants these 2 ^^^

	@Test
	public void getProducts() throws Exception {
		List<ProductModel> products = new ArrayList<>();
		products.add(new ProductModel(1L, "p-011", "Box", 10.0f, 3));
		products.add(new ProductModel(2L, "p-012", "Box", 10.0f, 3));
		when(productsService.getProducts()).thenReturn(products);

		mockMvc.perform(get("/api/v1/products/")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2));
	}

	@Test
	public void getById() throws Exception {
		ProductModel mockProduct = new ProductModel(1L, "p-011", "Box", 10.0f, 3);
		when(productsService.getById(any(Long.class))).thenReturn(mockProduct);

		mockMvc.perform(get("/api/v1/products/" + mockProduct.getId())).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.productNumber").value(mockProduct.getProductNumber()))
				.andExpect(jsonPath("$.productName").value(mockProduct.getProductName()))
				.andExpect(jsonPath("$.price").value(mockProduct.getPrice()))
				.andExpect(jsonPath("$.quantity").value(mockProduct.getQuantity()));
	}

	@Test
	public void addProduct() throws Exception {
		ProductModel mockProduct = new ProductModel(null, "p-011", "Box", 10.0f, 3);
		when(productsService.addProduct(any())).thenReturn(1L);

		mockMvc.perform(post("/api/v1/products/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(mockProduct))).andExpect(status().isOk())
				.andExpect(jsonPath("$").value(1));
	}

	@Test
	void putProduct() throws Exception {
		ProductModel mockProduct = new ProductModel(1L, "p-011", "Box", 10.0f, 3);
		when(productsService.updateProduct(eq(mockProduct.getId()), any(ProductModel.class))).thenReturn(mockProduct);

		mockMvc.perform(put("/api/v1/products/" + mockProduct.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(mockProduct))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(mockProduct.getId()))
				.andExpect(jsonPath("$.productNumber").value(mockProduct.getProductNumber()))
				.andExpect(jsonPath("$.productName").value(mockProduct.getProductName()))
				.andExpect(jsonPath("$.price").value(mockProduct.getPrice()))
				.andExpect(jsonPath("$.quantity").value(mockProduct.getQuantity()));
	}

	@Test
	public void deleteProduct() throws Exception {
		when(productsService.deleteProduct(any(Long.class))).thenReturn(true);

		mockMvc.perform(delete("/api/v1/products/" + 1L)).andExpect(status().isOk())
				.andExpect(jsonPath("$").value(true));
	}
}
