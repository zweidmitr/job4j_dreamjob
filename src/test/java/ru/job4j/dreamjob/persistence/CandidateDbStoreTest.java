package ru.job4j.dreamjob.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Post;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CandidateDbStoreTest {
    private BasicDataSource pool = new Main().loadPool();
    private CandidateDbStore store = new CandidateDbStore(pool);

    private Candidate candidate = new Candidate(0, "name", "description");

    @Test
    public void whenCreateCandidate() {
        store.add(candidate);
        Candidate candidateDb = store.findById(candidate.getId());
        assertThat(candidateDb.getName(), is(candidate.getName()));
    }

    @Test
    public void whenUpdateCandidate() {
        store.add(candidate);
        candidate.setName("new candidate name");
        store.update(candidate);
        Candidate candidateDb = store.findById(candidate.getId());
        assertThat("new candidate name", is(candidateDb.getName()));
    }

}