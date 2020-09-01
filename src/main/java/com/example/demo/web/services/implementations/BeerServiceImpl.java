package com.example.demo.web.services.implementations;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.web.model.BeerDto;
import com.example.demo.web.model.BeerStyleEnum;
import com.example.demo.web.services.BeerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

	@Override
	public BeerDto getBeerById(UUID beerId) {
		return BeerDto.builder()
				.id(beerId)
				.beerName("Galaxy Cat")
				.beerStyle(BeerStyleEnum.ALE)
				.build();
	}

	@Override
	public BeerDto save(BeerDto beerDto) {
		return BeerDto.builder()
				.id(UUID.randomUUID())
				.build();
	}

	@Override
	public void updateBeer(UUID beerId, BeerDto beerDto) {
		
	}

	@Override
	public void deleteById(UUID beerId) {
		log.debug("Beer deleted succesfully");	
	}
	
	
}

