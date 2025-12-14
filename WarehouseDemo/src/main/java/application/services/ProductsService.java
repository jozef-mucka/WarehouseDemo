package application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.data.ProductsDataAccessInterface;
import application.models.ProductModel;

@Service
public class ProductsService implements ProductsServiceInterface<ProductModel> {

	@Autowired
	ProductsDataAccessInterface<ProductModel> productsDAO;

	@Override
	public List<ProductModel> getProducts() {
		return productsDAO.getProducts();
	}

	@Override
	public ProductModel GetById(long id) {
		return productsDAO.getById(id);
	}

	@Override
	public List<ProductModel> searchProducts(String searchTerm) {
		return productsDAO.searchProducts(searchTerm);
	}

	@Override
	public long addProduct(ProductModel newProduct) {
		return productsDAO.addProduct(newProduct);
	}

	@Override
	public boolean deleteProduct(long id) {
		return productsDAO.deleteProduct(id);
	}

	@Override
	public ProductModel updateProduct(long id, ProductModel updatedProduct) {
		ProductModel existing = productsDAO.getById(id);
		if(existing == null) {
			new Exception("Product with id " + id + " not found");
		}
        existing.setProductNumber(updatedProduct.getProductNumber());
        existing.setProductName(updatedProduct.getProductName());
        existing.setPrice(updatedProduct.getPrice());
        existing.setQuantity(updatedProduct.getQuantity());
		return productsDAO.updateProduct(existing);
	}

}
