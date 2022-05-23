package ru.job4j.dreamjob.control;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.services.CityService;
import ru.job4j.dreamjob.services.PostService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PostControllerTest {
    @Test
    public void whenPosts() {
        List<Post> posts = Arrays.asList(
                new Post(1, "newPost"),
                new Post(2, "newPost")
        );
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(postService.findAll()).thenReturn(posts);
        PostController postController = new PostController(postService, cityService);

        String page = postController.posts(model);
        /**
         * Метод verify анализирует вызванные методы у объекта заглушки model.
         * Если коллекция posts переданные в модель и вызванная у метода verify будут отличаться,
         * то тест упадет с ошибкой.
         */
        verify(model).addAttribute("posts", posts);
        assertThat(page, is("posts"));
    }

    @Test
    public void whenCreatePost() {
        Post input = new Post(1, "newPost");
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        input.setCity(mock(City.class));
        PostController postController = new PostController(postService, cityService);
        String page = postController.createPost(input);
        verify(postService).create(input);
        assertThat(page, is("redirect:/posts"));
    }

    @Test
    public void whenUpdatePost() {
        Post input = new Post(1, "newPost");
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        input.setCity(mock(City.class));
        PostController postController = new PostController(postService, cityService);
        String page = postController.updatePost(input);
        verify(postService).update(input);
        assertThat(page, is("redirect:/posts"));
    }

}