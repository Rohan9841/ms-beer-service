package com.example.demo.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

//import com.example.demo.bootstrap.BeerLoader;
import com.example.demo.web.model.BeerDto;
import com.example.demo.web.model.BeerStyleEnum;
import com.example.demo.web.services.BeerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BeerController.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@ComponentScan(basePackages = "com.example.demo.web.mappers")
class BeerControllerTest {
	
	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	BeerService beerService;

	BeerDto validBeer; 
	
	@BeforeEach
	public void setUp() {
		validBeer = BeerDto
				.builder()
				.id(UUID.randomUUID())
				.beerName("Beer1")
				.beerStyle(BeerStyleEnum.ALE)
				.upc("0631234200036")
				.price(new BigDecimal("12.95"))
				.build();
	}

	@Test
	void testGetBeerById() throws Exception {
		given(beerService.getBeerById(any(UUID.class))).willReturn(validBeer);
		ConstrainedFields fields = new ConstrainedFields(BeerDto.class);
		mockMvc.perform(get("/api/v1/beer/{beerId}", validBeer.getId().toString())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("v1/beer-get",
						pathParameters(
								parameterWithName("beerId")
								.description("UUID of desired beer to get.")
								),
						responseFields(
								fields.withPath("id").description("ID of beer").type(UUID.class),
								fields.withPath("version").description("Version Number").type(Integer.class),
								fields.withPath("createdDate").description("Date Created").type(OffsetDateTime.class),
								fields.withPath("lastModifiedDate").description("Date Updated").type(OffsetDateTime.class),
								fields.withPath("beerName").description("Beer Name").type(String.class),
								fields.withPath("beerStyle").description("Beer Style").type(BeerStyleEnum.class),
								fields.withPath("upc").description("UPC of Beer").type(Long.class),
								fields.withPath("price").description("Price").type(BigDecimal.class),
								fields.withPath("quantityOnHand").description("Quantity on Hand").type(Integer.class)
								)
						));
	}

	@Test
	void testSaveNewBeer() throws Exception {
		BeerDto beerDto = validBeer;
		beerDto.setId(null);
		
		given(beerService.saveNewBeer(any())).willReturn(validBeer);
		
		String beerDtoJson = objectMapper.writeValueAsString(beerDto);
		
		ConstrainedFields fields = new ConstrainedFields(BeerDto.class);
		
		mockMvc.perform(post("/api/v1/beer")
				.contentType(MediaType.APPLICATION_JSON)
				.content(beerDtoJson))
				.andExpect(status().isCreated())
				.andDo(document("v1/beer-new",
						requestFields(
								fields.withPath("id").ignored(),
								fields.withPath("version").ignored(),
								fields.withPath("createdDate").ignored(),
								fields.withPath("lastModifiedDate").ignored(),
								fields.withPath("beerName").description("Beer Name"),
								fields.withPath("beerStyle").description("Beer Style"),
								fields.withPath("upc").description("UPC of beer"),
								fields.withPath("price").description("Price"),
								fields.withPath("quantityOnHand").ignored()
								)
						));
	}

	@Test
	void testUpdateBeerById() throws Exception {
		
		given(beerService.updateBeer(any(), any())).willReturn(validBeer);
		
		BeerDto beerDto = validBeer;
		beerDto.setId(null);
		String beerDtoJson = objectMapper.writeValueAsString(beerDto);

		mockMvc.perform(put("/api/v1/beer/{beerId}",UUID.randomUUID().toString())
				.contentType(MediaType.APPLICATION_JSON)
				.content(beerDtoJson)).andExpect(status().isNoContent());

	}
	
	private static class ConstrainedFields {
        private final ConstraintDescriptions constraintDescriptions;
        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }
        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path)
            		.attributes(key("constraints")
            				.value(StringUtils
            						.collectionToDelimitedString(
            								this.constraintDescriptions
            								.descriptionsForProperty(path), ". "
            								)
            						)
            				);
        }
    }

}
