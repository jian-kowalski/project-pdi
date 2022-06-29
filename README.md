# Projeto base

Esse projeto tem por objetivo servir de base para outros projetos da equipe, ele consiste em um crud básico de cadastro
de produtos, e tem uma cobertura básicas de testes de interação, negocio e de arquitetura.

Ele foi desenvolvido com a missão de padronizar a arquitetura e padrão de desenvolvimento da equipe, além de ajudar a
tirar duvídas durante o desenvolvimento.

## Arquitetura

# Api

Camada de interface (web — controllers e views no Spring MVC)

# Core

Camada responsável por configurações e criação de beans comuns.

# Domain

Camada responsável pela lógica de negócio (service — models, domains e services no Spring MVC) e camada de
persistência (data — repository no Spring Data).

Exceptions:
NotFound: usada quando um recurso não for encontrado.
Domain: usada quando uma violação de regra de negocio for violada.
DomainInUse: usada quando for excluído um recurso que está em uso por outro recurso.

# Infrastructure

## Construção

Execute `mvn install -DskipTests` para compilar o projeto ignorando a execução dos testes.

## Testes

Execute `mvn test` para executar os testes na aplicação.

## Docker

Execute `docker build --no-cache -t projetobase .` para gerar a imagem docker do projeto.

Execute ` cd docker && docker-compose up -d` para executar o projeto.
