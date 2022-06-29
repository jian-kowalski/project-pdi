package br.com.jiankowalski.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(Include.NON_NULL)
@Getter
@Builder
class Problem {

  private Integer status;
  private OffsetDateTime timestamp;
  private String type;
  private String title;
  private String detail;
  private String userMessage;
  private List<ModelObject> objects;

  @Getter
  @Builder
  public static class ModelObject {

    private String name;
    private String userMessage;
  }
}
