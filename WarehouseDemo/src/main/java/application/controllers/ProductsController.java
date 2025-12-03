package application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import application.models.ProductModel;
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
		List<ProductModel> products = productsService.getProducts();
		model.addAttribute("title", "List of products");
		model.addAttribute("products", products);
		return "products.html";
	}

}
