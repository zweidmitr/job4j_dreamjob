package ru.job4j.dreamjob.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.persistence.PostStore;
import ru.job4j.dreamjob.services.PostService;

@Controller
public class PostController {

    private final PostStore store = PostStore.instOf();
    private final PostService service = PostService.instOf();

    @GetMapping("/posts")
    public String posts(Model model) {
        model.addAttribute("posts", service.findAll());
        return "posts";
    }

    @GetMapping("/addPost")
    public String addPost(Model model) {
        model.addAttribute("post", new Post(0, "Заполните поле"));
        return "addPost";
    }

    @GetMapping("/formAddPost")
    public String formAddPost(Model model) {
        return "addPost";
    }

    @GetMapping("/formUpdatePost/{postId}")
    public String formUpdatePost(Model model, @PathVariable("postId") int id) {
        model.addAttribute("post", service.findById(id));
        return "updatePost";
    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute Post post) {
        service.update(post);
        return "redirect:/posts";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post) {
        service.create(post);
        return "redirect:/posts";
    }
}
