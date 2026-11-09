#Estoque REST API (Spring Boot + MariaDB)

Sistema de gestão de estoque desenvolvido em Java utilizando Spring Boot, como evolução para uma API RESTful moderna com autenticação segura e persistência relacional.

---

##Sobre o Projeto

O objetivo do projeto é fornecer um Web Service REST para o gerenciamento eficiente de estoque e produtos em pequenas empresas.

O sistema implementa duas entidades principais e relacionadas:
* **Produto**: Contém informações como nome, preço, quantidade, quantidade mínima e o vínculo com o estoque.
* **Estoque**: Representa o armazém ou local físico onde os produtos estão armazenados.

A aplicação permite realizar operações completas de CRUD (cadastrar, consultar, atualizar e excluir) para ambas as entidades, garantindo integridade relacional através do Spring Data JPA e segurança de ponta a ponta com Spring Security (JWT).

---

##Tecnologias Utilizadas

* **Java 17**
* **Spring Boot 3**
* **Spring Security** (Autenticação Stateless via JWT)
* **Spring Data JPA / Hibernate**
* **MariaDB / MySQL** (Banco de dados relacional)
* **Lombok**
* **Bean Validation** (`@Valid`)
* **Maven** (Gerenciamento de dependências)
* **Git e GitHub**

---

##Estrutura do Projeto

```text
src/main/java/br/com/estoque/
├── controller/        # Controladores REST (Endpoints da API)
├── model/             # Entidades JPA (Produto e Estoque)
├── repository/        # Repositórios de dados (Spring Data JPA)
├── security/          # Configurações de segurança e filtros JWT
└── service/           # Regras de negócio da aplicação

Como Executar o Projeto:

Clone o repositório.:

Bash
git clone [https://github.com/VitorWostehoff/estoque-rest-java.git](https://github.com/VitorWostehoff/estoque-rest-java.git)
Configure o Banco de Dados:
No arquivo src/main/resources/application.properties, configure as credenciais de acesso ao seu servidor MariaDB (ou utilize variáveis de ambiente para a senha).

Execute a aplicação:

mvn spring-boot:run

Instruções para os Integrantes da Equipe
Esta seção contém instruções básicas de Git para os integrantes responsáveis pelo desenvolvimento do projeto.

Antes de começar a programar
Sempre atualize sua cópia local do projeto antes de realizar alterações:

git pull --rebase
Depois disso, faça suas alterações normalmente.

Quando terminar suas alterações
Adicione os arquivos modificados:

git add .
Crie um commit descrevendo o que foi feito:

git commit -m "descreva o que foi feito"
Antes de enviar, atualize novamente sua branch local:

git pull --rebase
Por fim, envie as alterações para o GitHub:

git push origin main
Fluxo resumido
git pull --rebase

Fazer as alterações no código

git add .

git commit -m "mensagem do commit"

git pull --rebase

git push origin main

Importante: Não começar a desenvolver sem antes executar git pull --rebase. Isso ajuda a garantir que você esteja trabalhando com a versão mais recente do projeto e reduz a possibilidade de conflitos com alterações realizadas por outros integrantes.