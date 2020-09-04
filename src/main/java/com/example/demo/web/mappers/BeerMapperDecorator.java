package com.example.demo.web.mappers;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.domain.Beer;
import com.example.demo.web.model.BeerDto;
import com.example.demo.web.services.BeerInventoryService;

public abstract class BeerMapperDecorator implements BeerMapper{

	@Autowired
	private BeerInventoryService beerInventoryService;
	
	@Autowired
	private BeerMapper beerMapper;
	
	@Override
	public Beer beerDtoToBeer(BeerDto beerDto) {
		return beerMapper.beerDtoToBeer(beerDto);
	}

	@Override
    public BeerDto beerToBeerDto(Beer beer) {
       return beerMapper.beerToBeerDto(beer);
    }

    @Override
    public BeerDto beerToBeerDtoWithInventory(Beer beer) {
        BeerDto dto = beerMapper.beerToBeerDto(beer);
        dto.setQuantityOnHand(beerInventoryService.getOnHandInventory(beer.getId()));
        return dto;
    }
	
}
