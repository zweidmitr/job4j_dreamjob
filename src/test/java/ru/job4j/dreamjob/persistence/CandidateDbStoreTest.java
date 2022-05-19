package ru.job4j.dreamjob.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CandidateDbStoreTest {

    @Test
    public void whenFindALlCandidate() {
        BasicDataSource pool = new Main().loadPool();
        CandidateDbStore store = new CandidateDbStore(pool);
        Candidate oneCandidate = new Candidate(1, "one", "descOne");
        Candidate twoCandidate = new Candidate(2, "two", "descTwo");
        Candidate threeCandidate = new Candidate(3, "three", "descThree");

        List<Candidate> result = List.of(oneCandidate, twoCandidate, threeCandidate);
        assertThat(result, is(store.findAll()));
    }

    @Test
    public void whenAddCandidateAndFindById() {
        BasicDataSource pool = new Main().loadPool();
        CandidateDbStore store = new CandidateDbStore(pool);
        Candidate candidate = new Candidate(0, "name", "description");

        store.add(candidate);
        Candidate candidateDb = store.findById(candidate.getId());
        assertThat(candidateDb.getName(), is(candidate.getName()));
    }

    @Test
    public void whenUpdateCandidate() {
        BasicDataSource pool = new Main().loadPool();
        CandidateDbStore store = new CandidateDbStore(pool);
        Candidate candidate = new Candidate(0, "name", "description");

        store.add(candidate);
        candidate.setName("new candidate name");
        store.update(candidate);
        Candidate candidateDb = store.findById(candidate.getId());
        assertThat("new candidate name", is(candidateDb.getName()));
    }

}