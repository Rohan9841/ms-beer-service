package com.example.demo.web.services.implementations;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Beer;
import com.example.demo.exceptionHandlers.NotFoundException;
import com.example.demo.repositories.BeerRepository;
import com.example.demo.web.mappers.BeerMapper;
import com.example.demo.web.model.BeerDto;
import com.example.demo.web.services.BeerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

	private final BeerRepository beerRepository;
	private final BeerMapper beerMapper;
	
	@Override
	public BeerDto getBeerById(UUID beerId) {
		return beerMapper.beerToBeerDto(
				beerRepository.findById(beerId).orElseThrow(NotFoundException::new)
				);
	}

	@Override
	public BeerDto saveNewBeer(BeerDto beerDto) {
		return beerMapper.beerToBeerDto(
				beerRepository.save(beerMapper.beerDtoToBeer(beerDto))
				);
	}

	@Override
	public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
		Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);
		
		beer.setBeerName(beerDto.getBeerName());
		beer.setBeerStyle(beerDto.getBeerStyle().name());
		beer.setPrice(beerDto.getPrice());
		beer.setUpc(beerDto.getUpc());
		
		return beerMapper.beerToBeerDto(beerRepository.save(beer));
	}

	@Override
	public void deleteById(UUID beerId) {
		log.debug("Beer deleted succesfully");	
	}
	
	
}

