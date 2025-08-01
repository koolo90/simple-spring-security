package com.example.algorythms.database;

import java.util.*;

public class InMemDbEngine {
    Map<String, String> database = new HashMap<>();
    Deque<Transaction> transactions = new LinkedList<>();

    public String parseCommand(String command) {
        String[] split = command.split("\s");
        String commandName = split[0];
        String result = switch (commandName) {
            case "SET" -> setTransactional(split[1], split[2]);
            case "GET" -> get(split);
            case "DELETE" -> deleteTransactional(split[1]);
            case "COUNT" -> count(split);
            case "BEGIN" -> beginTransaction();
            case "ROLLBACK" -> rollback();
            case "COMMIT" -> commit();
            default -> null;
        };
        return String.valueOf(result).toUpperCase();
    }

    //Transaction support
    private String commit() {
        transactions.clear();
        return null;
    }

    private String rollback() {
        String result = null;
        if (transactions.isEmpty()) {
            return "NO TRANSACTION";
        }
        Transaction pop = transactions.pop();
        for (Map.Entry<String, String> entry : pop.preState.entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue();
            if (v != null) {
                database.put(k, v);
            } else {
                database.remove(k);
            }
        }
        return result;
    }

    private String beginTransaction() {
        transactions.push(new Transaction());
        return null;
    }

    //Nont-transactional
    private String count(String[] split) {
        String countAsString = null;
        String value = split[1];
        countAsString = "" + database.values().stream().filter(v -> v.equals(value)).count();
        return countAsString;
    }

    private String get(String[] split) {
        String varName = split[1];
        return database.get(varName);
    }

    //Transactional
    private String deleteTransactional(String variable) {
        updatePretransactionalState(variable);
        database.remove(variable);
        return null;
    }

    private void updatePretransactionalState(String variable) {
        if (!transactions.isEmpty()) {
            Transaction peek = transactions.peek();
            peek.setPreTransactionSate(variable, database.get(variable));
        }
    }

    private String setTransactional(String variable, String value) {
        updatePretransactionalState(variable);
        database.put(variable, value);
        return null;
    }

    public String parseScript(String script) {
        String result = null;
        for (String command : script.split("\n")) {
            result = parseCommand(command);
        }
        return result;
    }

    private static class Transaction {
        private Map<String, String> preState = new HashMap<>();

        public void setPreTransactionSate(String s, String s1) {
            this.preState.put(s, s1);
        }
    }
}
