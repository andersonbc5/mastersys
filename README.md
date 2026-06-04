# MasterSys — API de Gerenciamento de Academia

API REST para gerenciamento de alunos, matrículas, modalidades, planos e financeiro de academias.

---

## 🛠 Tecnologias

- Java 25
- Spring Boot 3.5
- Spring Data JPA
- Spring Validation
- PostgreSQL
- Flyway
- Lombok
- Springdoc OpenAPI (Swagger)

---

## ▶️ Como rodar

### Pré-requisitos

- Java 25
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
spring.datasource.username=postgres
spring.datasource.password=senha
```

### Rodando

```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`

A documentação Swagger estará em `http://localhost:8080/swagger-ui.html`

---

## 📡 Endpoints

### Alunos `/alunos`
| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/alunos` | Cadastrar aluno |
| GET | `/alunos` | Listar alunos (com filtros e paginação) |
| GET | `/alunos/{id}` | Buscar por ID |
| PUT | `/alunos/{id}` | Atualizar aluno |
| DELETE | `/alunos/{id}` | Excluir aluno |

### Matrículas `/matriculas`
| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/matriculas` | Cadastrar matrícula |
| GET | `/matriculas` | Listar (com filtros e paginação) |
| GET | `/matriculas/{id}` | Buscar por ID |
| PATCH | `/matriculas/{id}/encerrar` | Encerrar matrícula |
| PATCH | `/matriculas/{id}/cancelar` | Cancelar matrícula |


### Modalidades `/modalidades`
| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/modalidades` | Cadastrar modalidade |
| GET | `/modalidades` | Listar (filtro por ativa) |
| GET | `/modalidades/{id}` | Buscar por ID |
| PUT | `/modalidades/{id}` | Atualizar |
| PATCH | `/modalidades/{id}/ativar` | Ativar |
| PATCH | `/modalidades/{id}/desativar` | Desativar |
| DELETE | `/modalidades/{id}` | Excluir |

### Planos `/planos`
| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/planos` | Cadastrar plano |
| GET | `/planos` | Listar (filtro por modalidade/ativo) |
| GET | `/planos/{id}` | Buscar por ID |
| PUT | `/planos/{id}` | Atualizar |
| PATCH | `/planos/{id}/ativar` | Ativar |
| PATCH | `/planos/{id}/desativar` | Desativar |
| DELETE | `/planos/{id}` | Excluir |

### Graduações `/graduacoes`
| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/graduacoes` | Cadastrar graduação |
| GET | `/graduacoes` | Listar todas |
| GET | `/graduacoes/{id}` | Buscar por ID |
| GET | `/graduacoes/modalidade/{id}` | Listar por modalidade |
| PUT | `/graduacoes/{id}` | Atualizar |
| DELETE | `/graduacoes/{id}` | Excluir |

### Faturas `/faturas`
| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/faturas/matriculas/{id}/gerar` | Gerar fatura |
| GET | `/faturas` | Listar (filtro por status) |
| GET | `/faturas/{id}` | Buscar por ID |
| GET | `/faturas/matricula/{id}` | Listar por matrícula |
| PATCH | `/faturas/{id}/pagar` | Registrar pagamento |
| PATCH | `/faturas/{id}/cancelar` | Cancelar fatura |

### Relatórios `/relatorios`
| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/relatorios/faturamento-mensal` | Faturamento por mês |
| GET | `/relatorios/faturas-em-aberto` | Faturas em aberto |
| GET | `/relatorios/alunos-por-cidade` | Alunos agrupados por cidade |

---
