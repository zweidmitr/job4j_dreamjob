package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore {
    private static final CandidateStore INST = new CandidateStore();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Java developer",
                "Java 11, Spring (Data/Web/Boot/Security),"
                        + " Rabbit/Kafka, PostgreSQL, Maven, Liquibase,"
                        + " Concordion, Gitlab"));
        candidates.put(2, new Candidate(2, "Java-разработчик",
                "Spring framework, Hibernate ORM, JMS, Docker, RabbiTMQ"));
        candidates.put(3, new Candidate(3, "Программист Java",
                "уровень middle/senior"));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
