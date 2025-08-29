# API de Tarefas (Spring Boot)

API REST para gerenciamento de tarefas com CRUD completo, validação, paginação e tratamento global de erros.

## Tecnologias

- Java 17
- Spring Boot 3.3.x (Web, Validation, Data JPA)
- MySQL (produção) / H2 (testes)
- JUnit 5 + Spring Boot Test

## Executando o projeto

1. Suba o MySQL com Docker:

```bash
docker compose up -d
```

2. Ajuste as variáveis em `src/main/resources/application.properties` se necessário:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tarefasdb?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

3. Execute a aplicação:

```bash
mvn spring-boot:run
```

4. A API ficará disponível em `http://localhost:8080`.

## Endpoints

Base path: `/api/tarefas`

### Criar tarefa

- **POST** `/api/tarefas`
- Body (JSON):

```json
{
  "nome": "Implementar autenticação",
  "dataEntrega": "2025-09-15",
  "responsavel": "Maria Silva"
}
```

- Responses:
  - 201 Created + Location header `/api/tarefas/{id}`
  - 400 Bad Request (erros de validação)

Exemplo curl:

```bash
curl -i -X POST http://localhost:8080/api/tarefas \
  -H 'Content-Type: application/json' \
  -d '{"nome":"Implementar autenticação","dataEntrega":"2025-09-15","responsavel":"Maria Silva"}'
```

### Listar tarefas (paginação e ordenação opcionais)

- **GET** `/api/tarefas`
- Query params: `page`, `size`, `sort` (ex.: `sort=nome,asc`)
- Responses:
  - 200 OK (Page JSON)

Exemplo curl:

```bash
curl -s 'http://localhost:8080/api/tarefas?page=0&size=10&sort=nome,asc'
```

### Detalhar tarefa

- **GET** `/api/tarefas/{id}`
- Responses:
  - 200 OK
  - 404 Not Found

Exemplo curl:

```bash
curl -i http://localhost:8080/api/tarefas/1
```

### Atualizar tarefa

- **PUT** `/api/tarefas/{id}`
- Body (JSON): igual ao POST
- Responses:
  - 200 OK
  - 400 Bad Request
  - 404 Not Found

Exemplo curl:

```bash
curl -i -X PUT http://localhost:8080/api/tarefas/1 \
  -H 'Content-Type: application/json' \
  -d '{"nome":"Refatorar módulo","dataEntrega":"2025-10-01","responsavel":"João Souza"}'
```

### Remover tarefa

- **DELETE** `/api/tarefas/{id}`
- Responses:
  - 204 No Content
  - 404 Not Found

Exemplo curl:

```bash
curl -i -X DELETE http://localhost:8080/api/tarefas/1
```

## Modelo de dados

Entidade `Tarefa`:

- `id` (Long, auto-incremento)
- `nome` (String, obrigatório, 3..120)
- `dataEntrega` (LocalDate, formato `yyyy-MM-dd`)
- `responsavel` (String, obrigatório, 3..120)

## Tratamento de erros

Erros retornam JSON padronizado:

```json
{
  "timestamp": "2025-01-01T12:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/tarefas"
}
```

## Testes

Execute os testes (perfil `test` com H2):

```bash
mvn -Dspring.profiles.active=test test
```
