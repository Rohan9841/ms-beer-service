package com.example.demo.web.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4461111650347229720L;

	@Null
	UUID id;
	
	@Null
	Integer version;
	
	@Null
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ", shape = JsonFormat.Shape.STRING)
	OffsetDateTime createdDate;
	
	@Null
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ", shape = JsonFormat.Shape.STRING)
	OffsetDateTime lastModifiedDate;
	
	@NotBlank
    String beerName;
	
	@NotNull
    BeerStyleEnum beerStyle;
    
	@NotNull
	String upc;
	
	@NotNull
	@Positive
	@JsonFormat(shape = JsonFormat.Shape.STRING)
    BigDecimal price; 
	
    Integer quantityOnHand;
}
