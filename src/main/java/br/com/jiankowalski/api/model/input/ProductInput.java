package br.com.jiankowalski.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductInput {
  @NotBlank
  @Size(min = 5)
  private String name;

  @Size(min = 10)
  @NotBlank
  private String description;

  @NotNull @PositiveOrZero private BigDecimal price;

  @NotNull private Boolean active;
}
