# Catálogo de Cursos

Aplicação independente em Spring Boot que cadastra instrutores e cursos e expõe essa relação um-para-muitos por uma API REST. O esquema do banco é criado pelo Liquibase. O Hibernate apenas valida o mapeamento.

## Integrantes do grupo

- Lucas Henrique
- Vitor Domingues
- Guilherme Eduardo

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Maven
- PostgreSQL
- Spring Data JPA
- Liquibase
- Spring Web
- Bean Validation
- JUnit
- Mockito
- MockMvc

## Configuração do projeto

Crie um banco exclusivo para esta atividade, com o nome `catalogocursos_dev`. No `psql`, conectado como um usuário administrador do PostgreSQL:

```sql
CREATE USER catalogocursos_app WITH PASSWORD 'defina-uma-senha-local';
CREATE DATABASE catalogocursos_dev OWNER catalogocursos_app;
```

A senha acima é apenas um exemplo. Use outra senha no seu ambiente.

Copie `.env.example` para `.env` na raiz do projeto e ajuste a URL, o usuário e a senha. O arquivo `.env` não é versionado.

```text
DB_URL=jdbc:postgresql://localhost:5432/catalogocursos_dev
DB_USERNAME=catalogocursos_app
DB_PASSWORD=defina-uma-senha-local
```

As mesmas chaves podem ser exportadas como variáveis de ambiente. A aplicação não possui usuário ou senha fixos no código. O Hibernate está configurado com `spring.jpa.hibernate.ddl-auto=validate`.

## Como executar

No Windows, na raiz do projeto, com o Java 21 e o PostgreSQL disponíveis:

```powershell
copy .env.example .env
.\mvnw.cmd spring-boot:run
```

A API sobe em `http://localhost:8080`. Na primeira execução, o Liquibase cria as tabelas `instrutor` e `curso`, a chave estrangeira e a restrição de email único.

## Endpoints disponíveis

### Cadastrar instrutor

`POST /api/instrutores`

Requisição:

```json
{
  "nome": "Marina Costa",
  "email": "marina.costa@example.com"
}
```

Resposta `201 Created`, com cabeçalho `Location`:

```json
{
  "id": 1,
  "nome": "Marina Costa",
  "email": "marina.costa@example.com"
}
```

O email é armazenado em letras minúsculas. A resposta não inclui a coleção de cursos.

### Cadastrar curso

`POST /api/cursos`

Requisição:

```json
{
  "titulo": "APIs REST com Spring Boot",
  "cargaHoraria": 40,
  "nivel": "INTERMEDIARIO",
  "instrutorId": 1
}
```

Resposta `201 Created`, com cabeçalho `Location`:

```json
{
  "id": 10,
  "titulo": "APIs REST com Spring Boot",
  "cargaHoraria": 40,
  "nivel": "INTERMEDIARIO",
  "instrutor": {
    "id": 1,
    "nome": "Marina Costa"
  }
}
```

O instrutor aparece apenas como resumo, sem a lista de cursos.

### Consultar curso

`GET /api/cursos/{id}`

Resposta `200 OK`, no mesmo formato do cadastro de curso.

### Listar cursos de um instrutor

`GET /api/instrutores/{id}/cursos`

Resposta `200 OK`. Os cursos pertencem somente ao instrutor informado e vêm ordenados pelo título:

```json
[
  {
    "id": 10,
    "titulo": "APIs REST com Spring Boot",
    "cargaHoraria": 40,
    "nivel": "INTERMEDIARIO"
  },
  {
    "id": 12,
    "titulo": "Java orientado a objetos",
    "cargaHoraria": 60,
    "nivel": "INICIANTE"
  }
]
```

### Códigos de erro

| Situação | Status |
| --- | --- |
| Dados inválidos | 400 |
| Instrutor ou curso inexistente | 404 |
| Email já cadastrado | 409 |

Exemplo:

```json
{
  "status": 404,
  "message": "Instrutor não encontrado",
  "path": "/api/instrutores/999/cursos"
}
```

## Testes

Os testes automatizados cobrem a consistência dos dois lados da relação, a rejeição de curso com instrutor inexistente, o retorno HTTP 400 para instrutor inválido e a listagem dos cursos de um instrutor ordenada pelo título.

No Windows:

```powershell
.\mvnw.cmd clean test
```

## Relacionamento entre entidades

Um instrutor possui vários cursos. Cada curso pertence obrigatoriamente a exatamente um instrutor.

`Curso` é o lado proprietário da relação, porque a chave estrangeira fica nessa entidade. `Instrutor` usa `@OneToMany` com `mappedBy = "instrutor"` e `FetchType.LAZY`. O método `adicionarCurso` rejeita curso nulo, preenche os dois lados e não inclui a mesma instância duas vezes. Não existe `setCursos` público.

## Banco de dados

Nome do banco: `catalogocursos_dev`.

O Liquibase, referenciado pelo changelog master, executa nesta ordem:

1. cria a tabela `instrutor`;
2. cria a tabela `curso`;
3. adiciona a chave estrangeira `curso.instrutor_id` para `instrutor.id`;
4. cria a restrição única de email.

Cada changeset possui rollback. Usuário e senha ficam apenas no ambiente local.
