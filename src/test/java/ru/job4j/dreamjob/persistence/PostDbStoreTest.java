package ru.job4j.dreamjob.persistence;

import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.services.CityService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PostDbStoreTest {
    @Test
    @Ignore
    public void whenCreatePost() {
        var cityService = new CityService(new CityStore());
        PostDbStore store = new PostDbStore(new Main().loadPool(), cityService);
        Post post = new Post(0, "Java job");
        post.setCity(cityService.findById(1));
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

}