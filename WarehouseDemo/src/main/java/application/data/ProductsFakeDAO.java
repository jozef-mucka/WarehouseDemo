package application.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import application.models.ProductModel;

@Repository
public class ProductsFakeDAO implements ProductsDataAccessInterface<ProductModel> {
	
	List<ProductModel> products = new ArrayList<>();
	
	public ProductsFakeDAO() {
		super();
		products.add(new ProductModel(0L, "000", "Cheese", 1500.0f, 1));
		products.add(new ProductModel(1L, "001", "Green jelly", 2000.0f, 2));
		products.add(new ProductModel(2L, "002", "Red jelly", 3200.0f, 1));
	}

	@Override
	public List<ProductModel> getProducts() {
		return products;
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
	public ProductModel updateProduct(long id, ProductModel updateProduct) {
		// TODO Auto-generated method stub
		return null;
	}


}
