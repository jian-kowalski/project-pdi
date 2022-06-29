package br.com.jiankowalski.api.mapper;

import br.com.jiankowalski.api.model.input.ProductInput;
import br.com.jiankowalski.domain.model.Product;
import br.com.jiankowalski.api.model.ProductModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMappper {

  ProductMappper INSTANCE = Mappers.getMapper(ProductMappper.class);

  Product productInputToProduct(ProductInput newProduct);

  ProductModel productToProductModel(Product product);

  List<ProductModel> productsToProductsModels(List<Product> products);

  void updateProduct(@MappingTarget Product product, ProductInput productInput);
}
