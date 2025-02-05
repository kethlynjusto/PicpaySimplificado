# Authentication API

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)

## Objetivo: PicPay Simplificado

O PicPay Simplificado é uma plataforma de pagamentos simplificada. Nela é possível depositar e realizar transferências
de dinheiro entre usuários. Temos 2 tipos de usuários, os comuns e lojistas, ambos têm carteira com dinheiro e realizam
transferências entre eles.

### Requisitos

A seguir estão algumas regras de negócio que são importantes para o funcionamento do PicPay Simplificado:

- Para ambos tipos de usuário, precisamos do `Nome Completo`, `CPF`, `e-mail` e `Senha`. CPF/CNPJ e e-mails devem ser
  únicos no sistema. Sendo assim, seu sistema deve permitir apenas um cadastro com o mesmo CPF ou endereço de e-mail;

- Usuários podem enviar dinheiro (efetuar transferência) para lojistas e entre usuários;

- Lojistas **só recebem** transferências, não enviam dinheiro para ninguém;

- Validar se o usuário tem saldo antes da transferência;

- Antes de finalizar a transferência, deve-se consultar um serviço autorizador externo, use este mock
  [https://util.devi.tools/api/v2/authorize](https://util.devi.tools/api/v2/authorize) para simular o serviço
  utilizando o verbo `GET`;

- A operação de transferência deve ser uma transação (ou seja, revertida em qualquer caso de inconsistência) e o
  dinheiro deve voltar para a carteira do usuário que envia;

- No recebimento de pagamento, o usuário ou lojista precisa receber notificação (envio de email, sms) enviada por um
  serviço de terceiro e eventualmente este serviço pode estar indisponível/instável. Use este mock
  [https://util.devi.tools/api/v1/notify)](https://util.devi.tools/api/v1/notify)) para simular o envio da notificação
  utilizando o verbo `POST`;

- Este serviço deve ser RESTFul.

## Usage

1. Start the application with Maven
2. The API will be accessible at http://localhost:8080


## API Endpoints
The API provides the following endpoints:

**POST USERS**
```markdown
POST /users - Register a new user into the App
```
```json
[
    {
      "firstName": "Kethlyn",
      "lastName": "Diniz",
      "password": "senha",
      "document": "123456783",
      "email": "kethlyn@example.com",
      "userType": "COMMON",
      "balance": 10
    },
    {
      "firstName": "Joao",
      "lastName": "Silva",
      "password": "senha",
      "document": "123654789",
      "email": "joao@example.com",
      "userType": "MERCHANT",
      "balance": 10
    }
]
```

**GET USERS**
```markdown
GET /users - Retrieve a list of all users.
```
```json
[
    {
        "id": 1,
        "firstName": "Kethlyn",
        "lastName": "Diniz",
        "document": "123456787",
        "email": "kethlyn@example.com",
        "password": "senha",
        "balance": 10.00,
        "userType": "COMMON"
    },
    {
        "id": 2,
        "firstName": "Joao",
        "lastName": "Silva",
        "document": "123456787",
        "email": "joao@example.com",
        "password": "senha",
        "balance": 10.00,
        "userType": "MERCHANT"
    }
]
```

**POST TRANSACTIONS**
```markdown
POST /transactions - Register a new Transaction between users (COMMON to COMMON or COMMON to MERCHANT)
```

```json

{
  "senderId": 1,
  "receiverId": 2,
  "value": 10
}
```

## Database
The project utilizes [H2 Database](https://www.h2database.com/html/tutorial.html) as the database. 