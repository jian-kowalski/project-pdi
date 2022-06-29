package br.com.jiankowalski.util;

import br.com.jiankowalski.api.model.input.ProductInput;
import br.com.jiankowalski.domain.model.Product;

import java.math.BigDecimal;
import java.util.Optional;

public class ProductFactory {

  public static ProductInput criarProductInputValido() {
    var productInput = new ProductInput();
    productInput.setName("Computador");
    productInput.setDescription("Ideal para PDV; Baixo consumo de Energia;");
    productInput.setActive(true);
    productInput.setPrice(BigDecimal.valueOf(10));
    return productInput;
  }

  public static Optional<Product> criarProductValido() {
    var productInput = new Product();
    productInput.setId("62b3730b1a9bf76859db5782");
    productInput.setName("Computador");
    productInput.setDescription("Ideal para PDV; Baixo consumo de Energia;");
    productInput.setActive(true);
    productInput.setPrice(BigDecimal.valueOf(10));
    return Optional.of(productInput);
  }

  public static Optional<Product> criarProductInValido() {
    var productInput = new Product();
    productInput.setId("62b3730b1a9bf76859db5782");
    productInput.setName("Computador");
    productInput.setDescription("Ideal para PDV; Baixo consumo de Energia;");
    productInput.setActive(true);
    productInput.setPrice(BigDecimal.valueOf(2));
    return Optional.of(productInput);
  }
}
