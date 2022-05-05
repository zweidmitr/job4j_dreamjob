package ru.job4j.dreamjob;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Класс PostDbStore имеет конструктор с параметром объекта BacisDataSource.
 * Объект BasicDataSource мы создадим в класса Main и попросим Spring загрузить его к себе в контекст.
 * Это нужно, чтобы проинициализировать класс PostDbStore.
 */
@SpringBootApplication
public class Main {
    private Properties loadDbProperties() {
        Properties cfg = new Properties();
        try (var io = new BufferedReader(
                new InputStreamReader(
                        Main.class.getClassLoader()
                                .getResourceAsStream("db.properties")
                )
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return cfg;
    }

    /**
     * Пул содержит активные соединение с базой. Когда вызывается метод Connection.close()
     * соединение не закрывается, а возвращается обратно в пул.
     * <p>
     * Пул активируется в метод loadPool(). Это метод имеет аннотация @Bean,
     * это аннотация указывает Spring загрузить объект BasicDataSource в контекст.
     */
    @Bean
    public BasicDataSource loadPool() {
        Properties cfg = loadDbProperties();
        BasicDataSource pool = new BasicDataSource();
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
        return pool;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("Go to http://localhost:8080/index");
    }
}
