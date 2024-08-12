package controller;

import com.google.gson.Gson;
import model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.TransactionService;

import static spark.Spark.*;

public class TransactionController {
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    public static void main(String[] args) {
        port(8000);
        logger.info("Server is running on http://localhost:8000");

        TransactionService transactionService = new TransactionService();
        Gson gson = new Gson();

        post("/transaction", (request, response) -> {
            Transaction transaction = gson.fromJson(request.body(), Transaction.class);
            String resultCode = transactionService.processTransaction(transaction);
            response.type("application/json");
            return gson.toJson(new Response(resultCode));
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
