package ru.job4j.dreamjob.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.services.CityService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PostDbStoreTest {

    private CityService cityService = new CityService(new CityStore());
    private BasicDataSource pool = new Main().loadPool();
    private PostDbStore store = new PostDbStore(pool, cityService);
    private Post post = new Post(0, "name");

    @Test
    public void whenCreatePost() {
        post.setCity(cityService.findById(1));
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

    @Test
    public void whenUpdatePost() {
        store.add(post);
        post.setName("new post name");
        store.update(post);
        Post postDb = store.findById(post.getId());
        assertThat("new post name", is(postDb.getName()));
    }

}