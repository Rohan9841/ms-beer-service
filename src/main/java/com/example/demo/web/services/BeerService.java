package com.example.demo.web.services;

import java.util.UUID;

import com.example.demo.web.model.BeerDto;


public interface BeerService {
	BeerDto getBeerById(UUID beerId);
	BeerDto saveNewBeer(BeerDto beerDto);
	BeerDto updateBeer(UUID beerId, BeerDto beerDto);
	void deleteById(UUID beerId);
}
