package application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.models.ProductModel;
import application.services.ProductsServiceInterface;

@RestController
@RequestMapping("/api/v1/products/")
public class ProductsRestController {
	
	ProductsServiceInterface<ProductModel> productsService;
	
	@Autowired
	public ProductsRestController(ProductsServiceInterface<ProductModel>  productsService) {
		super();
		this.productsService = productsService;
	}

	@GetMapping("/")
	public List<ProductModel> showAllProducts(Model model) {
		List<ProductModel> products = productsService.getProducts();
		return products;
	}
	
	@GetMapping("/search/")
	public List<ProductModel> searchProductsEmpty() {
		return productsService.getProducts();
	}
	
	@GetMapping("/search/{searchTerm}")
	public List<ProductModel> searchProducts(@PathVariable(name="searchTerm", required = false) String searchTerm) {
		if (searchTerm==null||searchTerm.isEmpty()) {
			return productsService.getProducts();
		} else {
			return productsService.searchProducts(searchTerm);
		}
	}
	
	@GetMapping("/{id}")
	public ProductModel getById(@PathVariable(name="id") Long id) {
		return productsService.GetById(id);
	}
	
	@PostMapping("/")
	public long addProduct(@RequestBody ProductModel model) {
		return productsService.addProduct(model);
	}
	
	@PutMapping("/{id}")
	public ProductModel addProduct(@PathVariable(name="id") Long id, @RequestBody ProductModel model) {
		return productsService.updateProduct(id, model);
	}
	
	@DeleteMapping("/{id}")
	public boolean deleteProduct(@PathVariable(name="id") Long id) {
		return productsService.deleteProduct(id);
	}

}
