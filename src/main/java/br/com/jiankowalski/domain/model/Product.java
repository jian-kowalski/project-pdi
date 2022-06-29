package br.com.jiankowalski.domain.model;

import br.com.jiankowalski.domain.exception.DomainException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Product {

  public static final int VALOR_MINIMO = 3;

  @EqualsAndHashCode.Include @Id private String id;

  private String name;

  private String description;

  private BigDecimal price;

  private Boolean active;

  public void validPrice() {
    if (this.getPrice().compareTo(BigDecimal.valueOf(VALOR_MINIMO)) < 0) {
      throw new DomainException(
          String.format("O valor minimo do produto e de %d R$", VALOR_MINIMO));
    }
  }
}
