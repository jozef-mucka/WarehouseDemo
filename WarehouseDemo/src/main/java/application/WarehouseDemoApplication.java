package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("application")
public class WarehouseDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehouseDemoApplication.class, args);
		System.out.print("hello");
	}

}
