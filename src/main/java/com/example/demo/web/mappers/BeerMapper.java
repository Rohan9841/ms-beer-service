package com.example.demo.web.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

import com.example.demo.domain.Beer;
import com.example.demo.web.model.BeerDto;

@Mapper(uses= {DateMapper.class})
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {

	Beer beerDtoToBeer(BeerDto beerDto);
	BeerDto beerToBeerDto(Beer beer);
	BeerDto beerToBeerDtoWithInventory(Beer beer);
}
