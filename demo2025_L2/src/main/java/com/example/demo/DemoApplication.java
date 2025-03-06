package com.example.demo;

import com.example.demo.model.Owner;
import com.example.demo.repository.OwnerRepository;
import com.example.demo.service.OwnerService;
import com.example.demo.service.impl.OwnerServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		Owner owner = Owner.builder()
				.name("Test")
				.id(1L)
				.build();
		OwnerRepository ownerRepository = new OwnerRepository();
		OwnerService ownerService = new OwnerServiceImpl(ownerRepository);
		Owner savedOwner = ownerService.addOwner(owner);
		System.out.println(savedOwner);

	}

}
