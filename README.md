# Estoque REST API

Sistema de gestão de estoque desenvolvido em Java utilizando Spring Boot, como evolução do projeto anterior desenvolvido com Web Services SOAP, agora utilizando uma API RESTful, autenticação com JWT e persistência em banco de dados relacional.

## Sobre o projeto

O objetivo do projeto é desenvolver uma API REST para o gerenciamento de estoque de pequenas empresas.

O sistema possui três entidades principais:

- **Produto:** armazena informações como nome, descrição, preço, quantidade e quantidade mínima, além do vínculo com um estoque.
- **Estoque:** representa o local onde os produtos são armazenados.
- **Usuário:** armazena informações de acesso e perfil dos usuários do sistema.

A aplicação possui operações de CRUD para produtos, funcionalidades de cadastro e consulta de usuários e autenticação por meio de JWT (JSON Web Token).

Os dados são armazenados em banco de dados relacional, utilizando Spring Data JPA e Hibernate para o mapeamento e gerenciamento das entidades.

## Tecnologias utilizadas

- Java 17
- Spring Boot 3
- Spring Web
- Spring Security
- JWT (JJWT)
- Spring Data JPA
- Hibernate
- MariaDB / MySQL
- MySQL Connector/J
- Bean Validation
- Maven
- Git e GitHub

## Estrutura do projeto

```text
src/main/java/br/com/estoque/
├── controller/
│   ├── AuthController.java
│   ├── EstoqueController.java
│   ├── ProdutoController.java
│   └── UsuarioController.java
├── dao/
│   ├── EstoqueDAO.java
│   ├── ProdutoDAO.java
│   └── UsuarioDAO.java
├── dto/
│   ├── EstoqueDTO.java
│   ├── LoginDTO.java
│   ├── ProdutoDTO.java
│   ├── TokenDTO.java
│   └── UsuarioDTO.java
├── exception/
│   ├── BusinessException.java
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
├── model/
│   ├── Estoque.java
│   ├── Produto.java
│   └── Usuario.java
├── security/
│   ├── JwtFilter.java
│   ├── JwtService.java
│   ├── SecurityConfig.java
│   └── UserDetailsServiceImpl.java
├── service/
│   ├── AuthService.java
│   ├── EstoqueService.java
│   ├── ProdutoService.java
│   └── UsuarioService.java
└── EstoqueApplication.java
```

## Como executar o projeto

### 1. Clonar o repositório

```bash
git clone https://github.com/VitorWostehoff/estoque-rest-java.git
```

### 2. Configurar o banco de dados

Crie um banco de dados chamado `estoque_db` no MariaDB ou MySQL.

No arquivo `src/main/resources/application.properties`, configure as credenciais de acesso ao banco de dados:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/estoque_db
spring.datasource.username=root
spring.datasource.password=sua_senha
```

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
