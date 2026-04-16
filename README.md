# Projeto Agendador de Tarefas — Microsserviços (BFF Orchestrator)

Este é o repositório principal do sistema Agendador de Tarefas, atuando como o **BFF (Backend For Frontend)** que orquestra a comunicação entre todos os microsserviços do ecossistema.

## 📝 Descrição do Sistema

O sistema é uma arquitetura moderna orientada a microsserviços para gestão completa de tarefas, perfis de usuários e notificações automáticas.

### Microsserviços do Ecossistema:
- **[Usuario](https://github.com/Ja0Santana/usuario)**: Gestão de perfis, endereços (ViaCEP) e autenticação JWT.
- **[Agendador de Tarefas](https://github.com/Ja0Santana/agendador-tarefas)**: CRUD de tarefas e lógica de agendamento.
- **[Notificador](https://github.com/Ja0Santana/notificador)**: Envio de emails via Mailtrap/SMTP.
- **BFF-Agendador (Este repositório)**: Orquestrador, Segurança Centralizada e Porta de Entrada Única.

## 🛠️ Stack Tecnológica

- **Linguagem**: Java 17/21+
- **Framework**: Spring Boot 3.x
- **Comunicação**: Spring Cloud OpenFeign
- **Segurança**: Spring Security + JWT
- **Banco de Dados**: PostgreSQL (para todos os serviços)
- **Infraestrutura**: Docker & Docker Compose
- **Qualidade**: SonarQube & GitHub Actions

## 🏗️ Arquitetura e Orquestração

O BFF simplifica a vida do frontend, consolidando chamadas para múltiplos microsserviços em um único ponto de entrada. Ele gerencia:
- Validação de tokens JWT
- Roteamento de requisições
- Agregação de dados (ex: trazer dados do usuário + suas tarefas em uma única resposta se necessário)

## 🔐 Configuração e Segurança

O projeto utiliza **variáveis de ambiente** para proteger todas as credenciais. 
**Importante**: Antes de rodar via Docker Compose, você deve configurar o arquivo `.env` na raiz deste diretório (BFF).

### Variáveis Principais:
| Variável | Descrição |
|---|---|
| `JWT_SECRET` | Chave secreta compartilhada para validação de tokens |
| `DB_USER` / `DB_PASS` | Credenciais do PostgreSQL |
| `MAIL_HOST` / `MAIL_PASS` | Credenciais para o Notificador (Mailtrap) |
| `CRON_USER_EMAIL` / `CRON_USER_PASSWORD` | Credenciais para tarefas agendadas (Schedules) |

## 🚦 Como Rodar o Sistema Completo

A maneira mais fácil de subir todo o ecossistema é utilizando o Docker Compose:

1. Clone todos os repositórios ou use este como base.
2. Certifique-se de que o Docker e Docker Compose estão instalados.
3. Na raiz deste repositório (`bff-agendador`), execute:

```bash
docker-compose up --build
```

Isso subirá:
- **BFF** na porta `8083`
- **Usuario** na porta `8080`
- **Agendador** na porta `8081`
- **Notificador** na porta `8082`
- **PostgreSQL** na porta `5432`

## 📖 Documentação da API

Após subir os serviços, a documentação centralizada via Swagger estará disponível em:

- **BFF**: `http://localhost:8083/swagger-ui.html`
- **Usuario**: `http://localhost:8080/swagger-ui.html`

## 🛡️ Engenharia de Software

- **IDOR/BOLA Protection**: Implementado em nível de serviço para garantir que um usuário nunca acesse dados de outro.
- **Stateless Architecture**: Autenticação baseada em JWT, facilitando a escalabilidade.
- **Containerization**: Ambiente consistente de desenvolvimento a produção.
