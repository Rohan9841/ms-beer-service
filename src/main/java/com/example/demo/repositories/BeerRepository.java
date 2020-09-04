package com.example.demo.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.demo.domain.Beer;
import com.example.demo.web.model.BeerStyleEnum;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID>{
	
	Page<Beer> findAllByBeerName(String beerName, Pageable pageable);
	Page<Beer> findAllByBeerStyle(BeerStyleEnum beerStyle, Pageable pageable);
	Page<Beer> findAllByBeerNameAndBeerStyle(String beerName, BeerStyleEnum beerStyle, Pageable pageable);
}
 