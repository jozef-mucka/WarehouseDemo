package application.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import application.entities.ProductEntity;

public interface ProductsRepositoryInterface extends CrudRepository<ProductEntity, Long>{
	
	List<ProductEntity> findByProductNameContainingIgnoreCase(String searchTerm);
}
