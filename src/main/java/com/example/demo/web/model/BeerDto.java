package com.example.demo.web.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDto {
	
	@Null
	UUID id;
	
	@Null
	Integer version;
	
	@Null
	OffsetDateTime createdDate;
	
	@Null
	OffsetDateTime lastModifiedDate;
	
	@NotBlank
    String beerName;
	
	@NotNull
    BeerStyleEnum beerStyle;
    
	@Positive
	@NotNull
	Long upc;
	
	@NotNull
	@Positive
    BigDecimal price; 
	
    Integer quanityOnHand;
}
