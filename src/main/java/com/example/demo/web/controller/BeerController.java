package com.example.demo.web.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.web.model.BeerDto;
import com.example.demo.web.model.BeerPagedList;
import com.example.demo.web.model.BeerStyleEnum;
import com.example.demo.web.services.BeerService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

	private static final Integer DEFAULT_PAGE_NUMBER = 0;
	private static final Integer DEFAULT_PAGE_SIZE = 25;

	private final BeerService beerService;

	@GetMapping(produces = { "application/json" })
	public ResponseEntity<BeerPagedList> listBeers(
			@RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand,
			@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "beerName", required = false) String beerName,
			@RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle) {
		if (pageNumber == null || pageNumber < 0) {
			pageNumber = DEFAULT_PAGE_NUMBER;
		}
		if (pageSize == null || pageSize < 0) {
			pageSize = DEFAULT_PAGE_SIZE;
		}

		if (showInventoryOnHand == null)
			showInventoryOnHand = false;

		BeerPagedList beerList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize),
				showInventoryOnHand);
		return new ResponseEntity<>(beerList, HttpStatus.OK);
	}

	@GetMapping("/{beerId}")
	public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId,
			@RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {
		return new ResponseEntity<>(beerService.getBeerById(beerId, showInventoryOnHand), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<BeerDto> saveNewBeer(@Valid @RequestBody BeerDto beerDto) {
		return new ResponseEntity<>(beerService.saveNewBeer(beerDto), HttpStatus.CREATED);
	}

	@PutMapping("/{beerId}")
	public ResponseEntity<BeerDto> updateBeerById(@PathVariable("beerId") UUID beerId,
			@Valid @RequestBody BeerDto beerDto) {
		return new ResponseEntity<>(beerService.updateBeer(beerId, beerDto), HttpStatus.NO_CONTENT);
	}

}
