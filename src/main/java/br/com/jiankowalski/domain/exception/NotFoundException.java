package br.com.jiankowalski.domain.exception;

public class NotFoundException extends RuntimeException {

  public NotFoundException(String entity, String id) {
    super(String.format("Record not found for %s with id %s", entity, id));
  }
}
