package controller;

import com.google.gson.Gson;
import model.Account;
import model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.AccountService;
import service.TransactionService;

import static spark.Spark.*;

public class TransactionController {
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    public static void startServer() {
        port(8000);
        logger.info("Server is running on http://localhost:8000");

        TransactionService transactionService = new TransactionService();
        AccountService accountService = new AccountService();
        Gson gson = new Gson();

        post("/transaction", (request, response) -> {
            Transaction transaction = gson.fromJson(request.body(), Transaction.class);
            String resultCode = transactionService.processTransaction(transaction);
            response.type("application/json");
            return gson.toJson(new Response(resultCode));
        });

        post("/account", (request, response) -> {
            Account account = gson.fromJson(request.body(), Account.class);
            int rowsAffected = accountService.createAccount(account);
            response.type("application/json");
            return gson.toJson(new Response(rowsAffected > 0 ? "200" : "400"));
        });

        get("/account/:accountId", (request, response) -> {
            Account account = accountService.getAccount(request.params(":accountId"));
            response.type("application/json");
            return gson.toJson(account);
        });

        get("/accounts", (request, response) -> {
            response.type("application/json");
            return gson.toJson(accountService.getAllAccounts());
        });
    }

    static class Response {
        private String code;

        public Response(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
