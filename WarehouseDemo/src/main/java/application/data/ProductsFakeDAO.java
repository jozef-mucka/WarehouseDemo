package application.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import application.models.ProductModel;

@Repository
public class ProductsFakeDAO implements ProductsDataAccessInterface<ProductModel> {
	
	List<ProductModel> products = new ArrayList<>();
	long newId;
	
	public ProductsFakeDAO() {
		super();
		products.add(new ProductModel(0L, "p-001", "Green jelly", 2000.0f, 2));
		products.add(new ProductModel(1L, "p-002", "Red jelly", 3200.0f, 1));
		products.add(new ProductModel(2L, "p-003", "Blueberry ink", 500.0f, 5));
		products.add(new ProductModel(3L, "p-004", "Chocolate bricks", 1200.0f, 3));
		products.add(new ProductModel(4L, "p-005", "Strawberry gel", 1800.0f, 2));
		products.add(new ProductModel(5L, "p-006", "Mint packet", 900.0f, 10));
		products.add(new ProductModel(6L, "p-007", "Vanilla pods", 2500.0f, 1));
		products.add(new ProductModel(7L, "p-008", "Peanut paste", 2100.0f, 4));
		products.add(new ProductModel(8L, "p-009", "Caramel cubes", 1400.0f, 6));
		products.add(new ProductModel(9L, "p-010", "Honey drops", 1600.0f, 8));
		products.add(new ProductModel(10L, "p-011", "Cinnamon sticks", 1100.0f, 5));
		newId = 10;
	}

	@Override
	public List<ProductModel> getProducts() {
		return products;
	}

	@Override
	public ProductModel getById(long id) {
		List<ProductModel> foundItems = products
				.stream()
				.filter(order -> order.getId()==id)
				.collect(Collectors.toList());
		if(!foundItems.isEmpty()) {
			return foundItems.get(0);
		}
		return null;
	}

	@Override
	public List<ProductModel> searchProducts(String searchTerm) {
		List<ProductModel> foundItems = products
				.stream()
				.filter(order -> order.getProductName().toLowerCase().contains(searchTerm.toLowerCase()))
				.collect(Collectors.toList());
		return foundItems;
	}

	@Override
	public long addProduct(ProductModel newProduct) {
		newProduct.setId(newId++);
		boolean success = products.add(newProduct);
		if(success) {
			return 1;
		}
		return 0;
	}

	@Override
	public boolean deleteProduct(long id) {
		for (int i = 0;i<products.size();i++) {
			if(products.get(i).getId()==id) {
				products.remove(i);
				return true;
			}
		}
		return false;
	}

	@Override
	public ProductModel updateProduct(long id, ProductModel updateProduct) {
		for (int i = 0;i<products.size();i++) {
			if(products.get(i).getId()==id) {
				products.set(i, updateProduct);
				return products.get(i);
			}
		}
		return null;
	}

}
