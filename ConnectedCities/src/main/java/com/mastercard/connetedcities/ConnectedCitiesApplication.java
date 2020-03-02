package com.mastercard.connetedcities;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;

@SpringBootApplication
@ComponentScan("com.mastercard.connectedcities.controller")
public class ConnectedCitiesApplication implements CommandLineRunner {

	@Autowired
	ResourceLoader resourceLoader;

	@Autowired
	private CacheManager cacheManager;

	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager("cities");
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ConnectedCitiesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Resource resource = resourceLoader.getResource("classpath:cities.txt");
		InputStream inputStream = resource.getInputStream();

		try {
			byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
			String data = new String(bdata, StandardCharsets.UTF_8);
			String[] cities = data.split("\n");
			for (String city : cities) {
				String[] originAndDestinations = city.split(",");
				cacheManager.getCache("cities").put(originAndDestinations[0].trim(), (originAndDestinations[1].trim()));
				System.out.println(originAndDestinations[0] + ":" + originAndDestinations[1]);
			}

		} catch (IOException e) {

		}
	}

}
