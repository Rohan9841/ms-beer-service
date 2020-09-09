package com.example.demo.web.services.implementations;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.demo.domain.Beer;
import com.example.demo.exceptionHandlers.NotFoundException;
import com.example.demo.repositories.BeerRepository;
import com.example.demo.web.mappers.BeerMapper;
import com.example.demo.web.model.BeerDto;
import com.example.demo.web.model.BeerPagedList;
import com.example.demo.web.model.BeerStyleEnum;
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
	public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {
		BeerPagedList beerPagedList;
		Page<Beer> beerPage;

		if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
			beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
		} else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
			beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
		} else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
			beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
		} else {
			beerPage = beerRepository.findAll(pageRequest);
		}

		if(showInventoryOnHand) {
			beerPagedList = new BeerPagedList(
					beerPage.getContent()
					.stream()
					.map(beerMapper::beerToBeerDtoWithInventory)
					.collect(Collectors.toList()),
					PageRequest.of(
							beerPage.getPageable().getPageNumber(),
							beerPage.getPageable().getPageSize()
							),
					beerPage.getTotalElements());
		}else {
			beerPagedList = new BeerPagedList(
					beerPage.getContent()
					.stream()
					.map(beerMapper::beerToBeerDto)
					.collect(Collectors.toList()),
					PageRequest.of(
							beerPage.getPageable().getPageNumber(),
							beerPage.getPageable().getPageSize()
							),
					beerPage.getTotalElements());
		}
		
		return beerPagedList;

	}

	@Override
	public BeerDto getBeerById(UUID beerId, Boolean showInventoryOnHand) {
		Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);
		if(showInventoryOnHand) return beerMapper.beerToBeerDtoWithInventory(beer);
		else return beerMapper.beerToBeerDto(beer);
	}

	@Override
	public BeerDto saveNewBeer(BeerDto beerDto) {
		return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
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
