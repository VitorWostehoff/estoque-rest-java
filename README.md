# Estoque REST Java

Sistema de gestão de estoque desenvolvido em **Java** utilizando **Web Services REST**, como projeto da disciplina de **Programação Web II**.

## Sobre o projeto

O objetivo do projeto é desenvolver um Web Service REST para gerenciamento de estoque de pequenas empresas.

O sistema utiliza três entidades principais:

- **Produto**
- **Estoque**
- **Usuário**

Um **Estoque** pode possuir vários **Produtos**, estabelecendo um relacionamento de **1 para N**.

A aplicação permite realizar operações de **CRUD** (cadastrar, consultar, atualizar e excluir) e consultas adicionais para as entidades do sistema.

Os dados são armazenados em um banco de dados **MySQL**, utilizando **JPA/Hibernate** para persistência.

O sistema também possui autenticação e autorização de usuários utilizando **Spring Security e JWT**.

## Tecnologias utilizadas

- Java
- Maven
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- MySQL
- Spring Security
- JWT
- JSON
- Git e GitHub
- Postman

## Estrutura do projeto

```text
src/main/java/br/com/estoque/
├── controller/
├── dao/
├── dto/
├── exception/
├── model/
├── security/
└── service/
