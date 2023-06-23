package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;


public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();

    @SneakyThrows
    public static Connection getConnection() {
        return DriverManager.getConnection(System.getProperty("datasource"), "app", "pass");
    }

    @SneakyThrows
    public static void clearTables() {
        String clearOrderTableQuery = "DELETE FROM order_entity;";
        String clearPaymentTableQuery = "DELETE FROM payment_entity;";
        String clearCreditRequestTableQuery = "DELETE FROM credit_request_entity;";
        runner.update(getConnection(), clearOrderTableQuery);
        runner.update(getConnection(), clearPaymentTableQuery);
        runner.update(getConnection(), clearCreditRequestTableQuery);
    }

    @SneakyThrows
    public static String getStatusByCard() {
        String status = "SELECT status FROM payment_entity ORDER BY created DESC";
        return runner.query(getConnection(), status, new ScalarHandler<>());
    }

    @SneakyThrows
    public static String getStatusByCredit() {
        String status = "SELECT status FROM credit_request_entity ORDER BY created DESC";
        return runner.query(getConnection(), status, new ScalarHandler<>());
    }

}

