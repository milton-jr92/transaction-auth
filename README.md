# Serviço de Autorização de Transações

## Domínio do Projeto

O Serviço de Autorização de Transações é projetado para lidar com a autorização de transações financeiras. Ele processa transações com base em vários critérios, como saldos de contas e informações de comerciantes. O serviço garante que as transações sejam autorizadas ou rejeitadas de acordo com regras predefinidas e retorna códigos de status apropriados.

## Regras de Transação

O serviço usa as seguintes regras para determinar o status da transação:

1. **TRANSACTION_APPROVED ("00")**: A transação é aprovada se a conta tiver saldo suficiente de acordo com a categoria de consumo (FOOD, MEAL ou CASH).
2. **TRANSACTION_REJECTED ("51")**: A transação é rejeitada se a conta não tiver saldo suficiente na categoria de consumo.
3. **TRANSACTION_ERROR ("07")**: A transação encontra um erro, como uma exceção durante o processamento.

## Design e Estrutura do Projeto

O projeto é estruturado em camadas, seguindo o design MVC:

### 1. **Model**
- **`Transaction`**: Representa uma transação financeira contendo os atributos id, accountId, amount, MCC e merchant.
- **`Account`**: Representa uma conta com saldos para diferentes categorias (FOOD, MEAL, CASH) e um saldo total.
- **`MccType`**: Enum que representa códigos de transação para cada categoria (ex: "5411" -> FOOD).
- **`TransactionStatus`**: Enum representando códigos de status de transação (TRANSACTION_APPROVED, TRANSACTION_REJECTED, TRANSACTION_ERROR).

### 2. **Repository**
- **`AccountRepository`**: Interface para acessar dados da conta. Fornece métodos CRUD para obter e atualizar informações da conta utilizando um banco de dados relacional em memória (H2).

### 3. **Service**
- **`TransactionService`**: Classe de serviço principal responsável pelo processamento de transações. Usa o `AccountRepository` para buscar e atualizar dados da conta e o `MerchantMccMapper` para determinar o tipo de MCC com base no nome do comerciante.
- **`MerchantMccMapper`**: Classe auxiliar que mapeia nomes de comerciantes para tipos de MCC. Fornece um método para obter o tipo de MCC com base no nome do comerciante ou no código MCC.

### 4. **Test**
- **`TransactionServiceTest`**: Testes unitários para a classe `TransactionService`. Testa os casos de uso, como aprovação de transação, rejeição, tratamento de erros e mapeamento de MCC baseado no nome do comerciante.
- **`AccountServiceTest`**: Testes unitários para a classe `AccountService`. Testa a criação de contas, atualização de saldos e busca de contas por ID.

## Fluxo de funcionamento

1. **Inicialização**: O `TransactionService` é inicializado com um `AccountRepository` e um `MerchantMccMapper`.
2. **Processamento de Transações**: O método `processTransaction` no `TransactionService` processa uma transação para:
    - Buscar os detalhes da conta.
    - Determinar o tipo de MCC usando o `MerchantMccMapper`.
    - Verificar o saldo da conta na categoria relevante.
    - Atualizar o saldo da conta e retornar o `TransactionStatus` apropriado.

## Execução do Projeto

OBS: esse projeto foi construído e executado usando IntelliJ IDEA. Para executá-lo via linha de comando, siga as instruções abaixo:

1. **Pré-requisitos**: Java 17, Maven.
2. Clone o repositório: `git clone https://github.com/milton-jr92/transaction-auth.git`
3. cd transaction-auth
4. Compile o projeto: `mvn clean install`
5. Execute o projeto: `mvn exec:java -Dexec.mainClass="Main"`

### Endpoints Disponíveis

A aplicação expõe os seguintes endpoints REST:

- **POST /transaction**: Processa uma transação.
    - **Request Body**: JSON representando uma `Transaction`.
    ```json
    {
        "id": "99999",
        "accountId": "12345",
        "amount": 100.0,
        "mcc": "5811",
        "merchant": "PADARIA DO ZE               SAO PAULO BR"
    }
    ```
    - **Response**: JSON com o código de status da transação.

- **POST /account**: Cria uma nova conta.
    - **Request Body**: JSON representando uma `Account`.
    - **Response**: JSON com o código de status da operação (200 para sucesso, 400 para falha).

- **GET /account/:accountId**: Retorna os detalhes de uma conta específica.
    - **Response**: JSON representando a `Account`.

- **GET /accounts**: Retorna todas as contas.
    - **Response**: JSON representando uma lista de `Account`.

- **PUT /account**: Atualiza uma conta existente.
    - **Request Body**: JSON representando uma `Account`.
    - **Response**: JSON com o código de status da operação (200 para sucesso, 400 para falha).

- **DELETE /account/:accountId**: Deleta uma conta específica.
    - **Response**: JSON com o código de status da operação (200 para sucesso, 400 para falha).


