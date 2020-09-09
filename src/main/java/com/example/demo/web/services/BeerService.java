package com.example.demo.web.services;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;

import com.example.demo.web.model.BeerDto;
import com.example.demo.web.model.BeerPagedList;
import com.example.demo.web.model.BeerStyleEnum;


public interface BeerService {
	
	BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest,Boolean showInventoryOnHand);
	BeerDto getBeerById(UUID beerId, Boolean showInventoryOnHand);
	BeerDto saveNewBeer(BeerDto beerDto);
	BeerDto updateBeer(UUID beerId, BeerDto beerDto);
	void deleteById(UUID beerId);
}
