package br.com.jiankowalski.api.exceptionhandler;

import lombok.Getter;

@Getter
enum ProblemType {
  ACESSO_NEGADO("/acesso-negado", "Acesso negado"),
  INVALID_PARAMETER("/parametro-invalido", "Parametro invalido"),
  MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
  RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrada", "Recurso não encontrado"),
  ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
  INVALID_DATA("/dados-invalidos", "Dados inválidos"),
  INTERNAL_ERROR("/erro-interno", "Erro interno"),
  DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
  ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio");

  private String title;
  private String uri;

  ProblemType(String path, String title) {
    this.uri = "https://highkube.sankhyacloud.com.br" + path;
    this.title = title;
  }
}
