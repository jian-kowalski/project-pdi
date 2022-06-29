package br.com.jiankowalski.api.controller;

import br.com.jiankowalski.api.model.input.ProductInput;
import br.com.jiankowalski.domain.model.Product;
import br.com.jiankowalski.domain.service.ProductService;
import br.com.jiankowalski.api.mapper.ProductMappper;
import br.com.jiankowalski.api.model.ProductModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

  private final ProductMappper productMappper;

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productMappper = ProductMappper.INSTANCE;
    this.productService = productService;
  }

  @GetMapping
  public List<ProductModel> listAllProducts() {
    return productMappper.productsToProductsModels(productService.findAll());
  }

  @GetMapping("/{productId}")
  public ProductModel findById(@PathVariable String productId) {
    return productMappper.productToProductModel(productService.findById(productId));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProductModel addProduct(@RequestBody @Valid ProductInput productInput) {
    var productInclude = productMappper.productInputToProduct(productInput);
    return productMappper.productToProductModel(productService.addProduct(productInclude));
  }

  @PutMapping("/{productId}")
  public ProductModel alterProductById(
      @PathVariable String productId, @RequestBody @Valid ProductInput productInput) {
    Product product = productService.findById(productId);
    productMappper.updateProduct(product, productInput);
    return productMappper.productToProductModel(productService.addProduct(product));
  }

  @DeleteMapping("/{productId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void removeProductById(@PathVariable String productId) {
    productService.removeProduct(productId);
  }
}
