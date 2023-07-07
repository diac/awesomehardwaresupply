package com.diac.awesomehardwaresupply.priceschedule.controller;

import com.diac.awesomehardwaresupply.domain.exception.ResourceConstraintViolationException;
import com.diac.awesomehardwaresupply.domain.exception.ResourceNotFoundException;
import com.diac.awesomehardwaresupply.domain.model.ProductDetail;
import com.diac.awesomehardwaresupply.priceschedule.service.ProductDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductDetailController.class)
@AutoConfigureMockMvc
public class ProductDetailControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private ProductDetailService productDetailService;

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void whenIndex() throws Exception {
        mockMvc.perform(
                get("/product_detail")
        ).andExpect(status().isOk());
    }

    @Test
    public void whenGetByIdFound() throws Exception {
        int id = 1;
        Mockito.when(productDetailService.findById(id)).thenReturn(ProductDetail.builder().id(id).build());
        String requestUrl = String.format("/product_detail/%d", id);
        mockMvc.perform(
                get(requestUrl)
        ).andExpect(status().isOk());
    }

    @Test
    public void whenGetByIdNotFound() throws Exception {
        int id = 1;
        Mockito.when(productDetailService.findById(id)).thenThrow(ResourceNotFoundException.class);
        String requestUrl = String.format("/product_detail/%d", id);
        mockMvc.perform(
                get(requestUrl)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void whenPost() throws Exception {
        int id = 1;
        String value = "test";
        int number = 1234;
        ProductDetail newProductDetail = ProductDetail.builder()
                .productSku(value)
                .listPrice(number)
                .cost(number)
                .priceCode(value)
                .build();
        ProductDetail savedProductDetail = ProductDetail.builder()
                .id(id)
                .productSku(value)
                .listPrice(number)
                .cost(number)
                .priceCode(value)
                .build();
        String requestBody = objectWriter.writeValueAsString(newProductDetail);
        String responseBody = objectWriter.writeValueAsString(savedProductDetail);
        Mockito.when(productDetailService.add(newProductDetail)).thenReturn(savedProductDetail);
        mockMvc.perform(
                        post("/product_detail")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().json(responseBody));
    }

    @Test
    public void whenPostWithNullValuesThenResponseStatusIsBadRequest() throws Exception {
        ProductDetail productDetail = ProductDetail.builder()
                .productSku(null)
                .listPrice(null)
                .cost(null)
                .priceCode(null)
                .build();
        String requestBody = objectWriter.writeValueAsString(productDetail);
        mockMvc.perform(
                post("/product_detail")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        ProductDetail productDetail = ProductDetail.builder()
                .productSku("")
                .listPrice(0)
                .cost(0)
                .priceCode("")
                .build();
        String requestBody = objectWriter.writeValueAsString(productDetail);
        mockMvc.perform(
                post("/product_detail")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPostViolatesResourceConstraintsThenStatusIsBadRequest() throws Exception {
        String value = "test";
        int number = 1234;
        ProductDetail productDetail = ProductDetail.builder()
                .productSku(value)
                .listPrice(number)
                .cost(number)
                .priceCode(value)
                .build();
        String requestBody = objectWriter.writeValueAsString(productDetail);
        Mockito.when(productDetailService.add(productDetail))
                .thenThrow(ResourceConstraintViolationException.class);
        mockMvc.perform(
                post("/product_detail")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPut() throws Exception {
        int id = 1;
        String value = "test";
        int number = 1234;
        ProductDetail productDetail = ProductDetail.builder()
                .id(id)
                .productSku(value)
                .listPrice(number)
                .cost(number)
                .priceCode(value)
                .build();
        String jsonValue = objectWriter.writeValueAsString(productDetail);
        String requestUrl = String.format("/product_detail/%d", id);
        Mockito.when(productDetailService.update(id, productDetail)).thenReturn(productDetail);
        mockMvc.perform(
                        put(requestUrl)
                                .content(jsonValue)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().json(jsonValue));
    }

    @Test
    public void whenPutWithNullValuesThenResponseStatusIsBadRequest() throws Exception {
        int id = 1;
        ProductDetail productDetail = ProductDetail.builder()
                .id(id)
                .productSku(null)
                .listPrice(null)
                .cost(null)
                .priceCode(null)
                .build();
        String jsonValue = objectWriter.writeValueAsString(productDetail);
        String requestUrl = String.format("/product_detail/%d", id);
        Mockito.when(productDetailService.update(id, productDetail)).thenReturn(productDetail);
        mockMvc.perform(
                put(requestUrl)
                        .content(jsonValue)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPutWithBlankValuesThenResponseStatusIsBadRequest() throws Exception {
        int id = 1;
        ProductDetail productDetail = ProductDetail.builder()
                .id(id)
                .productSku("")
                .listPrice(0)
                .cost(0)
                .priceCode("")
                .build();
        String jsonValue = objectWriter.writeValueAsString(productDetail);
        String requestUrl = String.format("/product_detail/%d", id);
        Mockito.when(productDetailService.update(id, productDetail)).thenReturn(productDetail);
        mockMvc.perform(
                put(requestUrl)
                        .content(jsonValue)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPutViolatesResourceConstraintsThenStatusIsBadRequest() throws Exception {
        int id = 1;
        String value = "test";
        int number = 1234;
        ProductDetail productDetail = ProductDetail.builder()
                .id(id)
                .productSku(value)
                .listPrice(number)
                .cost(number)
                .priceCode(value)
                .build();
        String jsonValue = objectWriter.writeValueAsString(productDetail);
        String requestUrl = String.format("/product_detail/%d", id);
        Mockito.when(productDetailService.update(id, productDetail))
                .thenThrow(ResourceConstraintViolationException.class);
        mockMvc.perform(
                put(requestUrl)
                        .content(jsonValue)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenDelete() throws Exception {
        int id = 1;
        String requestUrl = String.format("/product_detail/%d", id);
        mockMvc.perform(delete(requestUrl))
                .andExpect(status().isOk());
    }
}