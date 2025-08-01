package com.example.algorythms;

import com.example.algorythms.database.InMemDbEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TestDatabaseCliShould {

    private InMemDbEngine imdb;

    @BeforeEach
    void setUp() {
        imdb = new InMemDbEngine();
    }

    @Test
    void setOnEmptyDb() {
        assertThat(imdb.parseCommand("SET a 10")).isEqualTo("NULL");
    }

    @Test
    void countOnEmptyDb() {
        assertThat(imdb.parseCommand("COUNT 10")).isEqualTo("0");
    }

    @Test
    void getOnEmptyDb() {
        assertThat(imdb.parseCommand("GET a")).isEqualTo("NULL");
    }

    @Test
    void deleteOnEmptyDb() {
        assertThat(imdb.parseCommand("DELETE a")).isEqualTo("NULL");
    }

    @Test
    void shouldParseScript() {
        assertThat(imdb.parseScript("""
                SET a 10
                GET a
                COUNT 10
                DELETE a
                GET a
                COUNT 10
                """)).isEqualTo("0");
    }

    @Test
    void operateAllCommands() {
        assertThat(imdb.parseCommand("SET a 10")).isEqualTo("NULL");
        assertThat(imdb.parseCommand("GET a")).isEqualTo("10");
        assertThat(imdb.parseCommand("COUNT 10")).isEqualTo("1");

        assertThat(imdb.parseCommand("DELETE a")).isEqualTo("NULL");
        assertThat(imdb.parseCommand("GET a")).isEqualTo("NULL");
        assertThat(imdb.parseCommand("COUNT 10")).isEqualTo("0");
    }

    @Test
    void countValues() {
        imdb.parseCommand("SET a 10");
        imdb.parseCommand("SET b 10");
        assertThat(imdb.parseCommand("COUNT 10")).isEqualTo("2");

        imdb.parseCommand("DELETE a");
        assertThat(imdb.parseCommand("COUNT 10")).isEqualTo("1");

        imdb.parseCommand("SET b 30");
        assertThat(imdb.parseCommand("COUNT 10")).isEqualTo("0");
    }

    @Test
    void rollbackTransaction() {
        imdb.parseCommand("BEGIN");
        imdb.parseCommand("SET a 10");
        imdb.parseCommand("ROLLBACK");
        assertThat(imdb.parseCommand("COUNT 10")).isEqualTo("0");
    }

    @Test
    void rollbackNestedTransactions() {
        imdb.parseCommand("BEGIN"); //T1
        imdb.parseCommand("SET a 10");
        assertThat(imdb.parseCommand("GET a")).isEqualTo("10");

        imdb.parseCommand("BEGIN"); //T2
        imdb.parseCommand("SET a 20");
        assertThat(imdb.parseCommand("GET a")).isEqualTo("20");

        imdb.parseCommand("ROLLBACK"); //T2 a=10
        assertThat(imdb.parseCommand("GET a")).isEqualTo("10");

        imdb.parseCommand("ROLLBACK");
        assertThat(imdb.parseCommand("GET a")).isEqualTo("NULL");
    }

    @Test
    void commitAndRollbackSet() {
        imdb.parseCommand("BEGIN"); //T1
        imdb.parseCommand("SET a 30");
        imdb.parseCommand("BEGIN"); //T2
        imdb.parseCommand("SET a 40");
        imdb.parseCommand("COMMIT"); //T2
        assertThat(imdb.parseCommand("GET a")).isEqualTo("40");

        assertThat(imdb.parseCommand("ROLLBACK")).isEqualTo("NO TRANSACTION");
        assertThat(imdb.parseCommand("GET a")).isEqualTo("40");
    }

    @Test
    void commitAndRollbackDelete() {
        imdb.parseCommand("SET a 50");
        imdb.parseCommand("BEGIN");
        assertThat(imdb.parseCommand("GET a")).isEqualTo("50");
        imdb.parseCommand("SET a 60");
        imdb.parseCommand("BEGIN");
        imdb.parseCommand("DELETE a");
        assertThat(imdb.parseCommand("GET a")).isEqualTo("NULL");
        imdb.parseCommand("ROLLBACK");
        assertThat(imdb.parseCommand("GET a")).isEqualTo("60");
        imdb.parseCommand("COMMIT");
        assertThat(imdb.parseCommand("GET a")).isEqualTo("60");
    }

    @Test
    void countOnTransactions() {
        imdb.parseCommand("SET a 10");
        imdb.parseCommand("BEGIN");
        assertThat(imdb.parseCommand("COUNT 10")).isEqualTo("1");

        imdb.parseCommand("BEGIN");
        imdb.parseCommand("DELETE a");
        assertThat(imdb.parseCommand("COUNT 10")).isEqualTo("0");

        imdb.parseCommand("ROLLBACK");
        assertThat(imdb.parseCommand("COUNT 10")).isEqualTo("1");
    }

    @Test
    void question1() {
        imdb.parseCommand("BEGIN");
        imdb.parseCommand("SET a 10");
        imdb.parseCommand("SET a 20");
        imdb.parseCommand("ROLLBACK");

        assertThat(imdb.parseCommand("GET A")).isEqualTo("NULL");
    }

    @Test
    void question2() {
        imdb.parseCommand("BEGIN");
        imdb.parseCommand("DELETE a");
        imdb.parseCommand("ROLLBACK");

        assertThat(imdb.parseCommand("GET A")).isEqualTo("NULL");
    }

}
