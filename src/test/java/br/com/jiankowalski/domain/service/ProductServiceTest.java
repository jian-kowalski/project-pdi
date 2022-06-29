package br.com.jiankowalski.domain.service;

import br.com.jiankowalski.domain.exception.DomainException;
import br.com.jiankowalski.domain.exception.NotFoundException;
import br.com.jiankowalski.domain.repository.ProductRepository;
import br.com.jiankowalski.util.ProductFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {ProductService.class})
class ProductServiceTest {
  @Autowired private ProductService productService;

  @MockBean private ProductRepository productRepository;

  @Test
  void deleteProdutoComSucesso() {
    Mockito.when(productRepository.findById(Mockito.any(String.class)))
        .thenReturn(ProductFactory.criarProductValido());
    var product = productService.findById("62b3730b1a9bf76859db5782");
    assertDoesNotThrow(() -> productService.removeProduct(product.getId()));
  }

  @Test
  void deleteProductNotFound() {
    Mockito.when(productRepository.findById(Mockito.any(String.class)))
        .thenThrow(NotFoundException.class);
    assertThrows(
        NotFoundException.class, () -> productService.removeProduct("62b3730b1a9bf79999db5782"));
  }

  @Test
  void deveLancarExceptionAoIncluirComValorInvalido() {
    Mockito.when(productRepository.findById(Mockito.any(String.class)))
        .thenReturn(ProductFactory.criarProductInValido());
    var product = productService.findById("62b3730b1a9bf76859db5782");
    assertThrows(DomainException.class, () -> productService.addProduct(product));
  }

  @Test
  void naoDeveLancarExceptionAoIncluirComValorValido() {
    Mockito.when(productRepository.findById(Mockito.any(String.class)))
        .thenReturn(ProductFactory.criarProductValido());
    var product = productService.findById("62b3730b1a9bf76859db5782");
    Assertions.assertDoesNotThrow(() -> productService.addProduct(product));
  }
}
