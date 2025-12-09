package application.controllers;

import java.beans.PropertyEditorSupport;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import application.models.ProductModel;
import application.models.SearchModel;
import application.services.ProductsServiceInterface;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductsController {
	
	@Autowired
    private MessageSource messageSource;

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
	public String showNewForm(Model model, Locale locale) {
		model.addAttribute("title", messageSource.getMessage("addProduct", null, locale));
		model.addAttribute("product", new ProductModel());
		return "addProductForm";
	}

	@PostMapping("/addNew")
	public String addNew(@Valid ProductModel newProduct, BindingResult bindingResult, Model model, Locale locale) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("title", messageSource.getMessage("addProduct", null, locale));
			model.addAttribute("product", newProduct);
			model.addAttribute(
		        org.springframework.validation.BindingResult.MODEL_KEY_PREFIX + "product",
		        bindingResult
			);
            return "addProductForm";
        }
		newProduct.setId(null);
		productsService.addProduct(newProduct);
		return buildProductsPage(model);
	}

	@PostMapping("/editForm/{id}")
	public String displayEditForm(@PathVariable(name = "id") Long id, Model model, Locale locale) {
		model.addAttribute("title", messageSource.getMessage("addProduct", null, locale));
		ProductModel productModel2 = productsService.GetById(id);
		model.addAttribute("product", productModel2);
		return "editProductForm";
	}

	@PostMapping("/doUpdate")
	public String updateProduct(@Valid ProductModel productModel, BindingResult bindingResult, Model model, Locale locale) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("title", messageSource.getMessage("addProduct", null, locale));
			model.addAttribute("product", productModel);
			model.addAttribute(
		        org.springframework.validation.BindingResult.MODEL_KEY_PREFIX + "product",
		        bindingResult
			);
            return "editProductForm";
        }
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
	
	//custom message for quantity value being float, not ideal, shows both type mismatch and this custom message
	@InitBinder
    public void initBinder(WebDataBinder binder, Locale locale) {
        binder.registerCustomEditor(Integer.class, "quantity", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                try {
                    setValue(Integer.valueOf(text));
                } catch (NumberFormatException e) {
                    String message = messageSource.getMessage("typeMismatch.wholeNumber", null, locale);
                    throw new IllegalArgumentException(message);
                }
            }
        });
    }
}
