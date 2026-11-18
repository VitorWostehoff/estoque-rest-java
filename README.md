# Estoque REST API

API REST de estoques, produtos e usuários com Java 17, Spring Boot 3, JPA, MySQL e JWT.

## Configuração

Crie o banco no MySQL:

```sql
CREATE DATABASE IF NOT EXISTS estoque_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Configure o ambiente antes de iniciar:

- `DB_URL`: opcional, padrão `jdbc:mysql://localhost:3306/estoque_db`
- `DB_USERNAME`: opcional, padrão `root`
- `DB_PASSWORD`: senha do MySQL
- `JWT_SECRET`: obrigatório, segredo Base64 que decodifica pelo menos 32 bytes
- `ADMIN_EMAIL`: e-mail do primeiro administrador
- `ADMIN_PASSWORD`: senha do primeiro administrador, com no mínimo oito caracteres

No macOS/Linux:

```bash
export DB_USERNAME=root
export DB_PASSWORD='senha_do_mysql'
export JWT_SECRET="$(openssl rand -base64 32)"
export ADMIN_EMAIL='admin@exemplo.com'
export ADMIN_PASSWORD='defina_uma_senha_forte'
mvn spring-boot:run
```

O administrador inicial é criado somente se o e-mail informado ainda não existir. Alterar as variáveis depois não troca a senha da conta criada. Nunca envie segredos reais ao GitHub.

## Endpoints

| Método | Caminho | Acesso |
|---|---|---|
| POST | `/auth/login` | Público |
| GET | `/produtos` e `/produtos/{id}` | Autenticado |
| GET | `/produtos/busca?nome=...` | Autenticado |
| GET | `/produtos/preco?minimo=...&maximo=...` | Autenticado |
| POST, PUT, DELETE | `/produtos` e `/produtos/{id}` | ADMIN |
| GET | `/estoques` e `/estoques/{id}` | Autenticado |
| GET | `/estoques/busca?nome=...` | Autenticado |
| GET | `/estoques/localizacao?localizacao=...` | Autenticado |
| POST, PUT, DELETE | `/estoques` e `/estoques/{id}` | ADMIN |
| GET, POST, PUT, DELETE | `/usuarios` e `/usuarios/{id}` | ADMIN |
| GET | `/usuarios/busca?email=...` | ADMIN |
| GET | `/usuarios/perfil?perfil=ADMIN` | ADMIN |

O cadastro de usuários aceita `email`, `senha` e opcionalmente `role` (`USER` ou `ADMIN`). Somente ADMIN autenticado pode cadastrar usuários. Respostas dos endpoints de usuários não expõem a senha.

Faça login em `POST /auth/login` com JSON `{"email":"admin@exemplo.com","senha":"defina_uma_senha_forte"}` e passe o token nas chamadas protegidas:

```text
Authorization: Bearer SEU_TOKEN
```

Exemplo:

```bash
curl -X POST http://localhost:8080/estoques \
  -H 'Authorization: Bearer SEU_TOKEN' \
  -H 'Content-Type: application/json' \
  -d '{"nome":"Principal","localizacao":"Blumenau"}'
```

A exclusão de estoque com produtos associados retorna HTTP 409. Erros de validação retornam HTTP 400 e recursos inexistentes, HTTP 404. Rotas protegidas sem autenticação retornam HTTP 401; ações sem permissão devem retornar HTTP 403.

## Antes da entrega

Execute `mvn clean test` e `mvn spring-boot:run` com MySQL configurado. Teste CRUD e buscas extras de estoques/produtos/usuários, persistência após reinício, respostas HTTP 400/401/403/404/409 e autenticação com os dois perfis. A aplicação não foi executada em um ambiente com Maven e MySQL durante essas alterações.
