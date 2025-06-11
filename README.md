# 💰 Account Balance API

This project is a simple **reactive REST API** for managing account balances with support for multi-currency transactions. It is built using **Spring Boot**, **Spring WebFlux**, **R2DBC**, and **PostgreSQL**.

## 🚀 Features

- Create named account balances (initially 0.0 USD)
- Add `deposit` or `withdraw` transactions in various currencies (USD, EUR, BYN, RUB)
- Convert all transactions to USD for balance calculations
- Retrieve account balance in USD
- Get a list of all transactions for a specific account

## Technologies Used

- Java 21
- Spring Boot 3.3.x
- Spring WebFlux
- R2DBC (Reactive PostgreSQL)
- Reactor Core
- Lombok
- PostgreSQL
- Maven

---

## REST API Summary

| Method | Endpoint                               | Description                   |
|--------|----------------------------------------|-------------------------------|
| POST   | `/balances/{name}`                     | Create a new balance          |
| GET    | `/balances/{name}`                     | Get balance (in USD)          |
| POST   | `/balances/{name}/transactions`        | Add a deposit or withdrawal   |
| GET    | `/balances/{name}/transactions`        | List all transactions         |

---

## Prerequisites

- JDK 21+
- Maven 3.8+
- PostgreSQL
- Docker (optional, for running PostgreSQL locally)

---

## ⚙️ Setup PostgreSQL (Option 1: Docker)

```bash
docker run --name account-db -e POSTGRES_DB=balance_db \
-e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres \
-p 5432:5432 -d postgres


API DOC
Account Balance API Documentation

1. Create Balance
Endpoint: POST /balances/{name}
Description: Creates a new account balance with the specified name. The initial balance is always 0.0 USD.

Request
Method: POST

URL: /balances/{name}

Path Variable:

name (string) – Unique name for the balance (e.g., "main-account")

Example Request
POST /balances/main-account
Successful Response (201 Created)
json

{
  "id": "96c9fbee-b10d-4ff1-9d52-cce59c0b3546",
  "name": "main-account",
  "createdAt": "2025-06-11T12:34:56.789Z"
}

Error Responses
400 Bad Request – If the account name already exists:

json

{
  "error": "Balance name already exists"
}

2. Get Balance (in USD)
Endpoint: GET /balances/{name}
Description: Retrieves the total account balance in USD, calculated from all transactions.

Request
Method: GET

URL: /balances/{name}

Path Variable:

name (string) – Name of the balance

Example Request
GET /balances/main-account
Successful Response (200 OK)

{
  "name": "main-account",
  "balanceInUsd": 1700.0
}


3. Add Transaction
Endpoint: POST /balances/{name}/transactions
Description: Adds a new transaction (either a deposit or a withdrawal) to the specified account.

Request
Method: POST

URL: /balances/{name}/transactions

Path Variable:

name (string) – Name of the balance to which the transaction will be added.

Request Body (JSON):

{
  "type": "DEPOSIT",       // or "WITHDRAW"
  "amount": 500.00,
  "currency": "USD"        // Supported: USD, EUR, RUB, BYN
}

Example Request
POST /balances/main-account/transactions
Content-Type: application/json
json

{
  "type": "WITHDRAW",
  "amount": 100.00,
  "currency": "EUR"
}

Successful Response (200 OK)

{
  "id": "bda50b9e-1c32-4a1f-9ec9-9c3df290aa47",
  "balanceId": "a1d94bfc-2f64-45ef-9f45-47e1e750e445",
  "type": "WITHDRAW",
  "amount": 100.00,
  "currency": "EUR",
  "timestamp": "2025-06-11T12:56:34.123Z"
}
Error Responses
404 Not Found – If the specified account name does not exist:

json

{
  "error": "Balance not found"
}
400 Bad Request – If trying to withdraw more funds than available in the balance (after currency conversion):

json

{
  "error": "Insufficient funds"
}


4. Get All Transactions
Endpoint: GET /balances/{name}/transactions
Description: Retrieves all transactions (deposits and withdrawals) associated with the specified account.

🔗 Request
Method: GET

URL: /balances/{name}/transactions

Path Variable:

name (string) – The name of the account.

Example Request

GET /balances/main-account/transactions
Successful Response (200 OK)
Returns a list of all transactions for the account:

json

[
  {
    "id": "fd10b43e-3bb3-4f1e-8bdf-71457ea4c728",
    "balanceId": "a1d94bfc-2f64-45ef-9f45-47e1e750e445",
    "type": "DEPOSIT",
    "amount": 500.00,
    "currency": "USD",
    "timestamp": "2025-06-11T10:15:20.654Z"
  },
  {
    "id": "adbc7d8f-622e-4891-93d7-00ecac7426d5",
    "balanceId": "a1d94bfc-2f64-45ef-9f45-47e1e750e445",
    "type": "WITHDRAW",
    "amount": 100.00,
    "currency": "EUR",
    "timestamp": "2025-06-11T11:30:05.432Z"
  }
]

Error Response
404 Not Found – If the specified account name does not exist:

json

{
  "error": "Balance not found"
}












