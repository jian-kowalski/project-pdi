package br.com.jiankowalski.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductModel {

  private String id;

  private String name;

  private String description;

  private BigDecimal price;

  private Boolean active;
}
