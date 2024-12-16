# BackEnd Desafio Teste

API para gerenciamento de usuários e tarefas com autenticação segura e banco de dados SQLite.

## Tecnologias
- Angular18
- Java + Spring Boot
- JPA + SQLite
- Spring Security + BCrypt

## Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/yancarvalho13/BackEndDesafioTeste.git
   ```
2. Importe na IDE e execute:
   ```bash
   mvn spring-boot:run
   ```
3. API disponível em `http://localhost:8080`.

## Endpoints Principais

### Autenticação
- **POST** `/auth/login`: Autenticação de usuário.
- **POST** `/auth/register`: Registro de novos usuários.

### Tarefas
- **POST** `/task/add/{userId}`: Cria nova tarefa para o usuário.
- **GET** `/task/list/{userID}`: Lista tarefas do usuário.
- **DELETE** `/task/delete/{userId}/{taskId}`: Remove tarefa específica.

## Testes com cURL

- Registro:
  ```bash
  curl -X POST -H "Content-Type: application/json" -d '{"email":"user@example.com","password":"pass123","role":"USER"}' http://localhost:8080/auth/register
  ```
- Login:
  ```bash
  curl -X POST -H "Content-Type: application/json" -d '{"email":"user@example.com","password":"pass123"}' http://localhost:8080/auth/login
  ```
- Criar Tarefa:
  ```bash
  curl -X POST -H "Authorization: Bearer <token>" -H "Content-Type: application/json" -d '{"title":"Task","description":"Details"}' http://localhost:8080/task/add/1
  ```
