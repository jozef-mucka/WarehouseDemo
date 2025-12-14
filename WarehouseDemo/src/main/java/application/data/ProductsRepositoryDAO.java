package application.data;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import application.entities.ProductEntity;
import application.models.ProductModel;

public class ProductsRepositoryDAO implements ProductsDataAccessInterface<ProductModel> {

	@Autowired
	ProductsRepositoryInterface prouctsRepository;

	ModelMapper modelMapper = new ModelMapper();

	private JdbcTemplate jdbcTemplate;

	public ProductsRepositoryDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public ProductModel getById(long id) {
		ProductEntity entity = prouctsRepository.findById(id).orElse(null);
		if (entity == null) {
			return null;
		}
		ProductModel prouctModel = modelMapper.map(entity, ProductModel.class);
		return prouctModel;
	}

	@Override
	public List<ProductModel> getProducts() {
		Iterable<ProductEntity> proucts = prouctsRepository.findAll();
		List<ProductModel> models = new ArrayList<ProductModel>();
		for (ProductEntity item : proucts) {
			models.add(modelMapper.map(item, ProductModel.class));
		}
		return models;
	}

	@Override
	public List<ProductModel> searchProducts(String searchTerm) {
		Iterable<ProductEntity> proucts = prouctsRepository.findByProductNameContainingIgnoreCase(searchTerm);
		List<ProductModel> models = new ArrayList<ProductModel>();
		for (ProductEntity item : proucts) {
			models.add(modelMapper.map(item, ProductModel.class));
		}
		return models;
	}

	@Override
	public long addProduct(ProductModel newProduct) {
		ProductEntity entity = prouctsRepository.save(modelMapper.map(newProduct, ProductEntity.class));
		return entity == null ? 0 : entity.getId();
	}

	@Override
	public boolean deleteProduct(long id) {
		prouctsRepository.deleteById(id);
		return true;
	}

	@Override
	public ProductModel updateProduct(ProductModel updateProduct) {
		ProductEntity entity = prouctsRepository.save(modelMapper.map(updateProduct, ProductEntity.class));
		return modelMapper.map(entity, ProductModel.class);
	}

}
