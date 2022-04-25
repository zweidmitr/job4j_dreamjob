package ru.job4j.dreamjob.persistence;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class CandidateStore {
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(1);


   private CandidateStore() {
       candidates.put(0,new Candidate(0, "Дмитрий"," ищет больше времени в сутках"));
   }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public void create(Candidate candidate) {
        int newId = id.incrementAndGet();
        candidate.setId(newId);
        candidates.putIfAbsent(newId, candidate);
    }

    public void add(Candidate candidate) {
        int newId = id.incrementAndGet();
        candidate.setId(newId);
        candidates.putIfAbsent(newId, candidate);
    }

    public void update(Candidate candidate) {
        candidates.replace(candidate.getId(), candidate);
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }
}
