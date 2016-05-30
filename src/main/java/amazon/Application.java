/*
 *
 *  * Copyright 2003-2015 Monitise Group Limited. All Rights Reserved.
 *  *
 *  * Save to the extent permitted by law, you may not use, copy, modify,
 *  * distribute or create derivative works of this material or any part
 *  * of it without the prior written consent of Monitise Group Limited.
 *  * Any reproduction of this material must contain this notice.
 *
 */

package amazon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner loadData(ProductRepository repository) {
		return (args) -> {
			// save a couple of products
			// repository.save(new Product("Samsung EVO 500GB", "https://www.amazon.co.uk/Samsung-inch-Solid-State-Drive/dp/B00P73B1E4", "90"));

			// fetch all customers
			log.info("Products found with findAll():");
			log.info("-------------------------------");
			for (Product product: repository.findAll()) {
				log.info(product.toString());
			}
			log.info("");
		};
	}

}
