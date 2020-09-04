package com.example.demo.web.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.bootstrap.BeerLoader;

@SpringBootTest
class BeerInventoryServiceTest {

	@Autowired
	BeerInventoryService beerInventoryService;
	
	@BeforeEach
	void setup() {
		
	}
	
	@Test
	void testGetOnHandInventory() {
		Integer qoh = beerInventoryService.getOnHandInventory(BeerLoader.BEER_1_UUID);
		System.out.println(qoh);
	}

}
