package com.example.demo.web.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDto {

	UUID id;
	Integer version;
	OffsetDateTime createdDate;
	OffsetDateTime lastModifiedDate;
    String beerName;
    BeerStyleEnum beerStyle;
    Long upc;
    BigDecimal price; 
    Integer quanityOnHand;
}
