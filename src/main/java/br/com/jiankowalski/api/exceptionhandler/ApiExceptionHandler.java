package br.com.jiankowalski.api.exceptionhandler;

import br.com.jiankowalski.domain.exception.DomainException;
import br.com.jiankowalski.domain.exception.NotFoundException;
import br.com.jiankowalski.domain.exception.DomainInUseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  public static final String MSG_ERRO_GENERICA_USUARIO_FINAL =
      "Ocorreu um erro interno inesperado no sistema. Tente novamente e se "
          + "o problema persistir, entre em contato com o administrador do sistema.";

  @Autowired private MessageSource messageSource;

  @Override
  protected ResponseEntity<Object> handleBindException(
      BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
  }

  private ResponseEntity<Object> handleValidationInternal(
      Exception ex,
      BindingResult bindingResult,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    ProblemType problemType = ProblemType.DADOS_INVALIDOS;
    String detail =
        "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

    List<Problem.ModelObject> problemObjects =
        bindingResult.getAllErrors().stream()
            .map(
                objectError -> {
                  String message =
                      messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
                  String name = objectError.getObjectName();
                  if (objectError instanceof FieldError fieldErro) {
                    name = fieldErro.getField();
                  }
                  return Problem.ModelObject.builder().name(name).userMessage(message).build();
                })
            .toList();

    Problem problem = createProblem(status, problemType, detail, detail, problemObjects);
    return handleExceptionInternal(ex, problem, headers, status, request);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    ProblemType problemType = ProblemType.INTERNAL_ERROR;
    String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;
    Problem problem = createProblem(status, problemType, detail, detail, null);
    return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
    String detail =
        String.format("O recurso %s, que você tentou acessar, é inexistente.", ex.getRequestURL());

    Problem problem =
        createProblem(status, problemType, detail, MSG_ERRO_GENERICA_USUARIO_FINAL, null);

    return handleExceptionInternal(ex, problem, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(
      TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    if (ex instanceof MethodArgumentTypeMismatchException cause) {
      return handleMethodArgumentTypeMismatch(cause, headers, status, request);
    }

    return super.handleTypeMismatch(ex, headers, status, request);
  }

  private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    ProblemType problemType = ProblemType.INVALID_PARAMETER;

    String detail =
        String.format(
            "O parâmetro de URL '%s' recebeu o valor '%s', "
                + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
            ex.getName(), ex.getValue(), getSimpleName(ex));

    Problem problem =
        createProblem(status, problemType, detail, MSG_ERRO_GENERICA_USUARIO_FINAL, null);
    return handleExceptionInternal(ex, problem, headers, status, request);
  }

  private String getSimpleName(MethodArgumentTypeMismatchException ex) {
    var req =  ex.getRequiredType() == null ? null : ex.getRequiredType();
    return  req != null ? req.getSimpleName() : "";
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    Throwable rootCause = ExceptionUtils.getRootCause(ex);

    if (rootCause instanceof InvalidFormatException rootcause) {
      return handleInvalidFormat(rootcause, headers, status, request);
    } else if (rootCause instanceof PropertyBindingException rootcause) {
      return handlePropertyBinding(rootcause, headers, status, request);
    }

    ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
    String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";

    Problem problem =
        createProblem(status, problemType, detail, MSG_ERRO_GENERICA_USUARIO_FINAL, null);

    return handleExceptionInternal(ex, problem, headers, status, request);
  }

  private ResponseEntity<Object> handlePropertyBinding(
      PropertyBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    String path = joinPath(ex.getPath());

    ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
    String detail =
        String.format(
            "A propriedade '%s' não existe. "
                + "Corrija ou remova essa propriedade e tente novamente.",
            path);

    Problem problem =
        createProblem(status, problemType, detail, MSG_ERRO_GENERICA_USUARIO_FINAL, null);
    return handleExceptionInternal(ex, problem, headers, status, request);
  }

  private ResponseEntity<Object> handleInvalidFormat(
      InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    String path = joinPath(ex.getPath());

    ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
    String detail =
        String.format(
            "A propriedade '%s' recebeu o valor '%s', "
                + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
            path, ex.getValue(), ex.getTargetType().getSimpleName());

    Problem problem =
        createProblem(status, problemType, detail, MSG_ERRO_GENERICA_USUARIO_FINAL, null);
    return handleExceptionInternal(ex, problem, headers, status, request);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Object> handleEntidadeNaoEncontrada(
      NotFoundException ex, WebRequest request) {

    HttpStatus status = HttpStatus.NOT_FOUND;
    ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
    String detail = ex.getMessage();

    Problem problem = createProblem(status, problemType, detail, detail, null);
    return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
  }

  @ExceptionHandler(DomainInUseException.class)
  public ResponseEntity<Object> handleEntidadeEmUso(DomainInUseException ex, WebRequest request) {

    HttpStatus status = HttpStatus.CONFLICT;
    ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
    String detail = ex.getMessage();
    Problem problem = createProblem(status, problemType, detail, detail, null);
    return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
  }

  @ExceptionHandler(DomainException.class)
  public ResponseEntity<Object> handleNegocio(DomainException ex, WebRequest request) {

    HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
    ProblemType problemType = ProblemType.ERRO_NEGOCIO;
    String detail = ex.getMessage();

    Problem problem = createProblem(status, problemType, detail, detail, null);
    return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

    if (body == null) {

      body = createProblem(status, status.getReasonPhrase());
    } else if (body instanceof String cause) {
      body = createProblem(status, cause);
    }

    return super.handleExceptionInternal(ex, body, headers, status, request);
  }

  private Problem createProblem(
      HttpStatus status,
      ProblemType problemType,
      String detail,
      String userMessage,
      List<Problem.ModelObject> objects) {
    return Problem.builder()
            .timestamp(OffsetDateTime.now())
            .status(status.value())
            .type(problemType.getUri())
            .title(problemType.getTitle())
            .detail(detail)
            .userMessage(userMessage)
            .objects(objects)
            .build();
  }

  private Problem createProblem(HttpStatus status, String title) {
    return Problem.builder()
            .timestamp(OffsetDateTime.now())
            .status(status.value())
            .type(MSG_ERRO_GENERICA_USUARIO_FINAL)
            .title(title)
            .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
            .build();

  }

  private String joinPath(List<JsonMappingException.Reference> references) {
    return references.stream()
        .map(JsonMappingException.Reference::getFieldName)
        .collect(Collectors.joining("."));
  }
}
