package application;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import application.data.ProductsDataAccessInterface;
//import application.data.ProductsFakeDAO;
import application.data.ProductsRepositoryDAO;
import application.models.ProductModel;

@Configuration
public class SpringConfig {

	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter();
	}
	
	@Autowired
	DataSource dataSource;
	
	@Bean(name="productsDAO")
	public ProductsDataAccessInterface<ProductModel> getDataService() {
		//return new ProductsFakeDAO();
		return new ProductsRepositoryDAO(dataSource);
	}

}
