package br.com.jiankowalski.domain.service;

import br.com.jiankowalski.domain.exception.NotFoundException;
import br.com.jiankowalski.domain.model.Product;
import br.com.jiankowalski.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<Product> findAll() {
    return productRepository.findAll();
  }

  public Product findById(String productId) {
    return productRepository
        .findById(productId)
        .orElseThrow(() -> new NotFoundException("product", productId));
  }

  public Product addProduct(Product productInclude) {
    productInclude.validPrice();
    return productRepository.save(productInclude);
  }

  public void removeProduct(String productId) {
    productRepository.delete(findById(productId));
  }
}
