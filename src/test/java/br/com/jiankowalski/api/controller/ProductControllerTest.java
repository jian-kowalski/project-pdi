package br.com.jiankowalski.api.controller;

import br.com.jiankowalski.domain.service.ProductService;
import br.com.jiankowalski.util.ProductFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

  private final String END_POINT_PRODUCTS = "/product";
  private final String END_POINT_PRODUCT_ID = END_POINT_PRODUCTS + "/{productId}";

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private ProductService productService;

  @Test
  void deveRetornar201AoCriarComSucesso() throws Exception {
    var product = ProductFactory.criarProductInputValido();
    mockMvc
        .perform(
            MockMvcRequestBuilders.post(END_POINT_PRODUCTS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product))
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }
}
