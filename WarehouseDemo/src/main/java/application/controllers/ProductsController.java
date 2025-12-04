package application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import application.models.ProductModel;
import application.models.SearchModel;
import application.services.ProductsServiceInterface;

@Controller
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	ProductsServiceInterface<ProductModel> productsService;

	@Autowired
	public ProductsController(ProductsServiceInterface<ProductModel> productsService) {
		super();
		this.productsService = productsService;
	}

	@GetMapping("/")
	public String showAllProducts(Model model) {
		return buildProductsPage(model);
	}

	@PostMapping("/search")
	public String addNew(SearchModel searchModel, Model model) {
		String searchTerm = searchModel.getSearchTerm();
		searchTerm = searchTerm == null ? "" : searchTerm;
		if (searchTerm.isBlank()) {
			return buildProductsPage(model);
		}

		List<ProductModel> products = productsService.searchProducts(searchTerm);
		searchTerm = searchTerm.length() > 15 ? searchTerm.substring(0, 15) + "..." : searchTerm;
		return buildProductsPage(model, "Search results for: " + searchTerm, products);
	}

	@GetMapping("/newProductForm")
	public String showNewForm(Model model) {
		model.addAttribute("title", "Add product");
		model.addAttribute("product", new ProductModel());
		return "addProductForm";
	}

	@PostMapping("/addNew")
	public String addNew(ProductModel newProduct, Model model) {
		newProduct.setId(null);
		productsService.addProduct(newProduct);
		return buildProductsPage(model);
	}

	@PostMapping("/editForm/{id}")
	public String displayEditForm(@PathVariable(name = "id") Long id, Model model) {
		model.addAttribute("title", "Edit product");
		ProductModel productModel2 = productsService.GetById(id);
		model.addAttribute("product", productModel2);
		return "editProductForm";
	}

	@PostMapping("/doUpdate")
	public String updateProduct(ProductModel productModel, Model model) {
		productsService.updateProduct(productModel.getId(), productModel);
		return buildProductsPage(model);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") Long id, Model model) {
		productsService.deleteProduct(id);
		return buildProductsPage(model);
	}

	private String buildProductsPage(Model model) {
		return buildProductsPage(model, null, null);
	}

	private String buildProductsPage(Model model, String title, List<ProductModel> products) {
		title = title == null ? "Products" : title;
		products = products == null ? productsService.getProducts() : products;
		model.addAttribute("title", title);
		model.addAttribute("searchModel", new SearchModel());
		model.addAttribute("products", products);
		return "products";
	}

	@GetMapping("/spa")
	public String showSPA(Model model) {
		return "SPA/productsSPA";
	}
}
