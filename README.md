# Estoque REST API

Sistema de gestão de estoque desenvolvido em Java utilizando Spring Boot, como evolução do projeto de estoque desenvolvido anteriormente com Web Services SOAP, para uma API RESTful com autenticação segura e persistência em banco de dados relacional.

## Sobre o projeto

O objetivo do projeto é desenvolver um Web Service REST para o gerenciamento de estoque de pequenas empresas.

O sistema utiliza duas entidades principais relacionadas:

- **Produto**
- **Estoque**

A aplicação permite realizar operações de CRUD (cadastrar, consultar, atualizar e excluir) para as duas entidades.

Diferentemente da versão anterior, que armazenava os dados em memória, este projeto utiliza MariaDB/MySQL para persistência dos dados, com Spring Data JPA e Hibernate.

A API também conta com autenticação e segurança utilizando Spring Security e JWT (JSON Web Token).

## Tecnologias utilizadas

- Java 17
- Spring Boot 3
- Spring Security
- JWT (JSON Web Token)
- Spring Data JPA
- Hibernate
- MariaDB / MySQL
- Lombok
- Bean Validation
- Maven
- Git e GitHub

## Estrutura do projeto

```text
src/main/java/br/com/estoque/
├── controller/
│   └── Controladores REST (Endpoints da API)
├── model/
│   ├── Produto.java
│   └── Estoque.java
├── repository/
│   └── Repositórios de dados
├── security/
│   └── Configurações de segurança e filtros JWT
└── service/
    └── Regras de negócio da aplicação
```

## Como executar o projeto

### 1. Clonar o repositório

```bash
git clone https://github.com/VitorWostehoff/estoque-rest-java.git
```

### 2. Configurar o banco de dados

No arquivo `src/main/resources/application.properties`, configure as credenciais de acesso ao servidor MariaDB/MySQL.

Também é possível utilizar variáveis de ambiente para configurar a senha do banco de dados.

### 3. Executar a aplicação

Na pasta do projeto, execute:

```bash
mvn spring-boot:run
```

## Instruções para os integrantes da equipe

Esta seção contém instruções básicas de Git para os integrantes responsáveis pelo desenvolvimento do projeto.

### Antes de começar a programar

Sempre atualize sua cópia local do projeto antes de realizar alterações:

```bash
git pull --rebase
```

Depois disso, faça suas alterações normalmente.

### Quando terminar suas alterações

Adicione os arquivos modificados:

```bash
git add .
```

Crie um commit descrevendo o que foi feito:

```bash
git commit -m "descreva o que foi feito"
```

Antes de enviar, atualize novamente sua branch local:

```bash
git pull --rebase
```

Por fim, envie as alterações para o GitHub:

```bash
git push origin main
```

### Fluxo resumido

1. `git pull --rebase`
2. Fazer as alterações no código
3. `git add .`
4. `git commit -m "mensagem do commit"`
5. `git pull --rebase`
6. `git push origin main`

**Importante:** não começar a desenvolver sem antes executar `git pull --rebase`. Isso ajuda a garantir que você esteja trabalhando com a versão mais recente do projeto e reduz a possibilidade de conflitos com alterações realizadas por outros integrantes.
