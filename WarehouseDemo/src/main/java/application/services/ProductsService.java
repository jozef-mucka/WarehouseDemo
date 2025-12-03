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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductModel> searchProducts(String searchTerm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long addProduct(ProductModel newProduct) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean deleteProduct(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ProductModel updateProducts(long id, ProductModel updatedProduct) {
		// TODO Auto-generated method stub
		return null;
	}

}
