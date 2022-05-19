package ru.job4j.dreamjob.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.services.CityService;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PostDbStoreTest {

    @Test
    public void whenFindAll() {
        CityService cityService = new CityService(new CityStore());
        BasicDataSource pool = new Main().loadPool();
        PostDbStore store = new PostDbStore(pool, cityService);
        Post onePost = new Post(1, "one");
        Post twoPost = new Post(2, "two");
        Post threePost = new Post(3, "three");

        onePost.setCity(cityService.findById(1));
        twoPost.setCity(cityService.findById(2));
        threePost.setCity(cityService.findById(3));

        store.add(onePost);
        store.add(twoPost);
        store.add(threePost);

        List<Post> results = List.of(onePost, twoPost, threePost);
        assertThat(results, is(store.findAll()));

    }

    @Test
    public void whenAddPostAndFindById() {
        var cityService = new CityService(new CityStore());
        PostDbStore store = new PostDbStore(new Main().loadPool(), cityService);
        Post post = new Post(0, "Java job");

        post.setCity(cityService.findById(1));
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

    @Test
    public void whenUpdatePost() {
        CityService cityService = new CityService(new CityStore());
        BasicDataSource pool = new Main().loadPool();
        PostDbStore store = new PostDbStore(pool, cityService);
        Post post = new Post(0, "name");

        store.add(post);
        post.setName("new post name");
        store.update(post);
        Post postDb = store.findById(post.getId());
        assertThat("new post name", is(postDb.getName()));
    }

}