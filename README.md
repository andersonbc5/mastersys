# MasterSys — API de Gerenciamento de Academia

API REST para gerenciamento de alunos, matrículas, modalidades, planos e financeiro de academias.

---

## 🛠 Tecnologias

- Java 21
- Spring Boot 
- Spring Data JPA
- Spring Validation
- PostgreSQL
- Flyway
- Lombok
- Springdoc OpenAPI (Swagger)
- Spring Security
- JWT

---

## ▶️ Como rodar

### Pré-requisitos

- Java 21
- PostgreSQL rodando localmente
- Maven

### Clonar o projeto

git clone https://github.com/andersonbc5/mastersys.git

### Configuração

Crie um banco de dados no PostgreSQL:

```sql
CREATE DATABASE academia;
```

Configure as variáveis de ambiente:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/academia
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

jwt.secret=sua_chave_secreta
```

### Rodando

```bash
./mvnw spring-boot:run
```

### Documentação

Após iniciar a aplicação, a documentação Swagger estará em `http://localhost:8080/swagger-ui.html`

---

### Autenticação

A API utiliza autenticação baseada em **JWT (JSON Web Token)**.

### Login

Realize a autenticação através do endpoint:

```http
POST /auth/login
```

Exemplo de requisição:

```json
{
  "email": "admin@email.com",
  "senha": "123456"
}
```

Resposta:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### Utilizando o Token

Após obter o token, envie-o no header `Authorization` das requisições protegidas:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

### Perfis de Acesso

A aplicação possui controle de acesso baseado em perfis:

#### ADMIN

Permissões administrativas:

- Gerenciar usuários
- Cadastrar, atualizar e excluir planos
- Cadastrar, atualizar e excluir modalidades
- Ativar e desativar registros
- Acessar relatórios gerenciais

#### USER

Permissões de consulta:

- Consultar alunos
- Consultar matrículas
- Consultar modalidades
- Consultar planos

### Códigos de Retorno de Segurança

| Código | Descrição |
|----------|------------|
| 401 | Token ausente, inválido ou expirado |
| 403 | Usuário autenticado sem permissão para acessar o recurso |

---

### Endpoints

##  Alunos

Gerenciamento completo de alunos da academia.

| Método | Endpoint | Descrição |
|----------|----------|----------|
| POST | `/alunos` | Cadastrar aluno |
| GET | `/alunos` | Listar alunos (com filtros e paginação) |
| GET | `/alunos/{id}` | Buscar aluno por ID |
| PUT | `/alunos/{id}` | Atualizar aluno |
| DELETE | `/alunos/{id}` | Excluir aluno |

---

##  Matrículas

Controle de matrículas dos alunos.

| Método | Endpoint | Descrição |
|----------|----------|----------|
| POST | `/matriculas` | Cadastrar matrícula |
| GET | `/matriculas` | Listar matrículas |
| GET | `/matriculas/{id}` | Buscar matrícula por ID |
| PATCH | `/matriculas/{id}/encerrar` | Encerrar matrícula |
| PATCH | `/matriculas/{id}/cancelar` | Cancelar matrícula |

---

##  Modalidades

Gerenciamento das modalidades oferecidas pela academia.

| Método | Endpoint | Descrição |
|----------|----------|----------|
| POST | `/modalidades` | Cadastrar modalidade |
| GET | `/modalidades` | Listar modalidades |
| GET | `/modalidades/{id}` | Buscar modalidade por ID |
| PUT | `/modalidades/{id}` | Atualizar modalidade |
| PATCH | `/modalidades/{id}/ativar` | Ativar modalidade |
| PATCH | `/modalidades/{id}/desativar` | Desativar modalidade |
| DELETE | `/modalidades/{id}` | Excluir modalidade |

---

##  Planos

Gerenciamento dos planos vinculados às modalidades.

| Método | Endpoint | Descrição |
|----------|----------|----------|
| POST | `/planos` | Cadastrar plano |
| GET | `/planos` | Listar planos |
| GET | `/planos/{id}` | Buscar plano por ID |
| PUT | `/planos/{id}` | Atualizar plano |
| PATCH | `/planos/{id}/ativar` | Ativar plano |
| PATCH | `/planos/{id}/desativar` | Desativar plano |
| DELETE | `/planos/{id}` | Excluir plano |

---

##  Graduações

Controle das graduações por modalidade.

| Método | Endpoint | Descrição |
|----------|----------|----------|
| POST | `/graduacoes` | Cadastrar graduação |
| GET | `/graduacoes` | Listar graduações |
| GET | `/graduacoes/{id}` | Buscar graduação por ID |
| GET | `/graduacoes/modalidade/{id}` | Listar graduações por modalidade |
| PUT | `/graduacoes/{id}` | Atualizar graduação |
| DELETE | `/graduacoes/{id}` | Excluir graduação |

---

##  Financeiro

Controle de faturas e pagamentos.

| Método | Endpoint | Descrição |
|----------|----------|----------|
| POST | `/faturas/matriculas/{id}/gerar` | Gerar fatura |
| GET | `/faturas` | Listar faturas |
| GET | `/faturas/{id}` | Buscar fatura por ID |
| GET | `/faturas/matricula/{id}` | Buscar faturas da matrícula |
| PATCH | `/faturas/{id}/pagar` | Registrar pagamento |
| PATCH | `/faturas/{id}/cancelar` | Cancelar fatura |

---

##  Relatórios (ADMIN)

Endpoints exclusivos para usuários com perfil ADMIN.

| Método | Endpoint | Descrição |
|----------|----------|----------|
| GET | `/relatorios/faturamento-mensal` | Faturamento mensal |
| GET | `/relatorios/faturas-em-aberto` | Faturas pendentes |
| GET | `/relatorios/alunos-por-cidade` | Quantidade de alunos por cidade |

---

##  Usuários e Autenticação

| Método | Endpoint | Descrição |
|----------|----------|----------|
| POST | `/auth/login` | Realizar login |
| POST | `/auth/registrar` | Cadastrar usuário (ADMIN) |

