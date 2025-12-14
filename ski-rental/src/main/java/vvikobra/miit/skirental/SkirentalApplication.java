package vvikobra.miit.skirental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication(scanBasePackages = {"vvikobra.miit.skirental", "com.example.skirentalcontracts", "vvikobra.events"})
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class SkirentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkirentalApplication.class, args);
	}

}
