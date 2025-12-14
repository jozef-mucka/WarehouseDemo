package application.services;

import java.util.List;

public interface ProductsServiceInterface<T> {
	public T getById(long id);

	public List<T> getProducts();

	public List<T> searchProducts(String searchTerm);

	public long addProduct(T newProduct);

	public boolean deleteProduct(long id);

	public T updateProduct(long id, T updatedProduct) throws RuntimeException;
}
