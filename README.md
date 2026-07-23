# API Gestão de Vagas

Projeto de API RESTful para gerenciamento de vagas, empresas e candidatos, com autenticação JWT e controle de acesso por perfil.

---

## 📌 Sobre o projeto

A **API Gestão de Vagas** foi desenvolvida com foco em boas práticas de arquitetura backend usando Java e Spring, permitindo:

- Cadastro e autenticação de **empresas**
- Cadastro e autenticação de **candidatos**
- Publicação e gerenciamento de **vagas**
- Proteção de rotas com **Spring Security**
- Autorização baseada em **token JWT**

---

## 🚀 Tecnologias utilizadas

- **Java 25**
- **Spring Boot**
- **Spring Web MVC**
- **Spring Data JPA**
- **Spring Security**
- **JWT (java-jwt / Auth0)**
- **PostgreSQL**
- **Maven**
- **Docker / Docker Compose**
- **Lombok**

---

## 🧱 Arquitetura e organização

A API está organizada em módulos e camadas para manter separação de responsabilidades:

- **controllers** → endpoints HTTP
- **useCases** → regras de negócio
- **entities** → entidades JPA
- **repositories** → acesso a dados
- **dto** → contratos de entrada/saída
- **security / providers** → autenticação e autorização com JWT
- **exceptions** → tratamento de exceções da aplicação

### Estrutura base (resumo)

```text
src/main/java/br/com/CarlosManoel/Gestao_vagas
├── modules
│   ├── company
│   │   ├── controllers
│   │   ├── dto
│   │   ├── entities
│   │   ├── repositories
│   │   └── useCases
│   └── candidate
│       ├── controllers
│       ├── dto
│       ├── entities
│       ├── repositories
│       └── useCases
├── security
├── providers
└── exceptions
```

---

## 🔐 Autenticação e autorização

A API utiliza JWT com separação por contexto:

- Rotas iniciadas com `/company` são tratadas por filtro de segurança da empresa.
- Rotas iniciadas com `/candidate` são tratadas por filtro de segurança do candidato.

### Header obrigatório em rotas protegidas

```http
Authorization: Bearer SEU_TOKEN_JWT
```

---

## ⚙️ Configuração de ambiente

Crie um arquivo `.env` com base no `.env_EXAMPLE`:

```env
POSTGRES_USER=seu_usuario
POSTGRES_PASSWORD=sua_senha
POSTGRES_DB=gestao_vagas
POSTGRES_PORT=5432
```

No `application.properties` (ou `application.yml`), configure também:

- conexão com banco PostgreSQL
- secret JWT (`spring.security.token.secret`)

---

## 🐳 Subindo banco com Docker

```bash
docker compose up -d
```

Isso irá subir o PostgreSQL definido no `docker-compose.yml`.

---

## ▶️ Como executar o projeto

### Pré-requisitos

- Java 25
- Maven (ou usar Maven Wrapper)
- Docker (opcional, para banco)

### Passos

```bash
# 1) Clonar repositório
git clone https://github.com/carlosmanoelrsdev/Gestao_vagas.git

# 2) Entrar no projeto
cd Gestao_vagas

# 3) Subir PostgreSQL (opcional, recomendado)
docker compose up -d

# 4) Executar aplicação
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

---

## 📮 Endpoints da API

> Base URL local (padrão): `http://localhost:8080`

> Abaixo está a documentação funcional dos endpoints já estruturados no projeto.  
> Se quiser, na próxima etapa eu gero a versão 100% espelhada com **todos os payloads reais** por classe DTO/Controller.

---

### 1) Empresa

#### `POST /company/`
Cria uma nova empresa.

- **Auth:** não exige token
- **Body esperado (exemplo):**
```json
{
  "username": "empresa123",
  "email": "contato@empresa.com",
  "password": "12345678",
  "website": "https://empresa.com",
  "name": "Empresa X",
  "description": "Empresa de tecnologia"
}
```

- **Retorno de sucesso:** dados da empresa criada
- **Possíveis erros:**
  - `400 Bad Request` (validação, usuário já existente, dados inválidos)

---

### 2) Autenticação de Empresa

#### `POST /company/auth`
Autentica empresa e retorna token JWT.

- **Auth:** não exige token
- **Body esperado (exemplo):**
```json
{
  "username": "empresa123",
  "password": "12345678"
}
```

- **Retorno de sucesso (exemplo):**
```json
{
  "access_token": "jwt_token_aqui",
  "expires_in": 3600
}
```

- **Possíveis erros:**
  - `401 Unauthorized` (credenciais inválidas)

---

### 3) Candidato

#### `POST /candidate/`
Cria novo candidato.

- **Auth:** não exige token
- **Body esperado (exemplo):**
```json
{
  "username": "candidato123",
  "email": "candidato@email.com",
  "password": "12345678",
  "name": "Nome do Candidato",
  "description": "Desenvolvedor Java"
}
```

- **Retorno de sucesso:** dados do candidato criado
- **Possíveis erros:**
  - `400 Bad Request` (validações de campos)

---

### 4) Autenticação de Candidato

#### `POST /candidate/auth`
Autentica candidato e retorna token JWT.

- **Auth:** não exige token
- **Body esperado (exemplo):**
```json
{
  "username": "candidato123",
  "password": "12345678"
}
```

- **Retorno de sucesso (exemplo):**
```json
{
  "access_token": "jwt_token_aqui",
  "expires_in": 3600
}
```

- **Possíveis erros:**
  - `401 Unauthorized` (credenciais inválidas)

---

### 5) Vagas (módulo Company)

#### `POST /company/job/`
Cria uma vaga vinculada à empresa autenticada.

- **Auth:** obrigatório (Bearer Token da empresa)
- **Body esperado (exemplo):**
```json
{
  "description": "Vaga para Dev Backend Java",
  "benefits": "VR, VA, Plano de saúde",
  "level": "PLENO"
}
```

- **Retorno de sucesso:** vaga criada
- **Possíveis erros:**
  - `401 Unauthorized` (token ausente/inválido)
  - `403 Forbidden` (sem permissão)

#### `GET /company/job/`
Lista vagas da empresa ou vagas cadastradas (conforme regra implementada no use case).

- **Auth:** obrigatório (Bearer Token da empresa)
- **Retorno:** lista de vagas

---

### 6) Candidatura a vaga

#### `POST /candidate/job/apply`
Permite candidato autenticado se candidatar a uma vaga.

- **Auth:** obrigatório (Bearer Token do candidato)
- **Body esperado (exemplo):**
```json
{
  "jobId": "UUID_DA_VAGA"
}
```

- **Retorno de sucesso:** confirmação da candidatura
- **Possíveis erros:**
  - `401 Unauthorized`
  - `404 Not Found` (vaga não encontrada)
  - `409 Conflict` (candidatura já existente)

---

## ✅ Validações importantes

Exemplos de validação já aplicadas:

- `username` sem espaços
- `email` válido
- `password` com tamanho mínimo/máximo
- campos obrigatórios com `@NotBlank`

---

## ❌ Tratamento de erros

A API retorna erros de validação e regras de negócio, como:

- usuário já existente
- campos inválidos
- autenticação inválida
- acesso não autorizado

---

## 📈 Status do projeto

🚧 Projeto em desenvolvimento contínuo.

Melhorias planejadas:

- documentação OpenAPI/Swagger
- paginação e filtros avançados
- testes de integração mais abrangentes
- padronização global de responses de erro

---

## 👨‍💻 Autor

**Carlos Manoel**

- GitHub: [carlosmanoelrsdev](https://github.com/carlosmanoelrsdev)
- Email: carlosmanoelrscontato@gmail.com
- Email alternativo: carlosmanoeldev@outlook.com
