package application.data;

import java.util.List;

public interface ProductsDataAccessInterface<T> {
	public T GetById(long id);

	public List<T> getProducts();

	public List<T> searchProducts(String searchTerm);

	public long addProduct(T newProduct);

	public boolean deleteProduct(long id);

	public T updateProduct(long id, T updateProduct);
}
