# 🏥 Sistema de Clínica

Projeto desenvolvido em Java com foco no estudo de Programação Orientada a Objetos.

O sistema simula o gerenciamento de uma clínica médica, permitindo cadastrar pacientes e médicos, agendar consultas, cancelar consultas e consultar agendas.

Todo o projeto foi desenvolvido utilizando apenas Java puro (sem frameworks), com o objetivo de praticar os principais conceitos da linguagem.
## Objetivo

Este projeto foi criado para consolidar os conhecimentos em:

- Programação Orientada a Objetos
- Collections
- Manipulação de arquivos
- Expressões Regulares (Regex)
- Tratamento de exceções
- API de Data e Hora (java.time)
## Funcionalidades

✔ Cadastro de pacientes

✔ Cadastro de médicos

✔ Validação de CPF

✔ Validação de CRM

✔ Agendamento de consultas

✔ Cancelamento de consultas

✔ Consulta da agenda dos médicos

✔ Consulta do histórico de consultas dos pacientes

✔ Persistência de dados em arquivos .txt

✔ Tratamento de erros através de exceções
## Conceitos utilizados

### Programação Orientada a Objetos

- Encapsulamento
- Associação entre objetos
- Classes e Objetos
- Construtores
- Sobrescrita de métodos

### Collections

- ArrayList

### Java Time

- LocalDateTime
- LocalDate
- LocalTime
- DateTimeFormatter

### Regex

- Validação de CPF
- Validação de CRM
- Validação de nomes

### Exceções

- IllegalArgumentException
- try/catch

### Arquivos

- FileWriter
- BufferedWriter
## Estrutura

SistemaClinica

├── domain

│ ├── Paciente

│ ├── Medico

│ └── Consulta

└── service

└── Clinica
## Regras do sistema

- Não permite cadastrar dois pacientes com o mesmo CPF.

- Não permite cadastrar dois médicos com o mesmo CRM.

- Não permite consultas em datas passadas.

- Não permite consultas fora do horário da clínica.

- Não permite conflito de horários para médicos.

- Não permite conflito de horários para pacientes.

- Salva automaticamente pacientes, médicos e consultas em arquivos.
  
## Tecnologias
- Java
- Git
- GitHub

## Como executar

1. Clone o projeto

git clone https://github.com/RyanMartins77/SistemaClinica.git

2. Abra no IntelliJ

3. Execute a classe Main
## O que aprendi

Durante o desenvolvimento deste projeto pratiquei:

- Modelagem orientada a objetos
- Organização em pacotes
- Manipulação de listas
- Validação utilizando Regex
- Manipulação de datas
- Persistência em arquivos
- Tratamento de exceções
- Versionamento utilizando Git e GitHub
## Próximas melhorias

- Interface gráfica (JavaFX)

- Banco de dados (MySQL)

- JDBC

- Sistema de login

- Relatórios

- Exportação em PDF
