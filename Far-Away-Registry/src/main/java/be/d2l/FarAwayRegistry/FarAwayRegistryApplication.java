package be.d2l.FarAwayRegistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class FarAwayRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(FarAwayRegistryApplication.class, args);
	}

}
