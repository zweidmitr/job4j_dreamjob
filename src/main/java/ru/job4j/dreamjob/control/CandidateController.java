package ru.job4j.dreamjob.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.services.CandidateService;

@Controller
public class CandidateController {
    private final CandidateService service = CandidateService.instOf();

    @GetMapping("/candidates")
    public String candidates(Model model) {
        model.addAttribute("candidates", service.findAll());
        return "candidates";
    }

    @GetMapping("/addCandidate")
    public String addCandidate(Model model) {
        model.addAttribute("Candidate", new Candidate(0, "Заполните имя", "Заполните описание"));
        return "addCandidate";
    }

    @GetMapping("/formUpdateCandidate/{candidateId}")
    public String formUpdateCandidate(Model model, @PathVariable("candidateId") int id) {
        model.addAttribute("candidate", service.findById(id));
        return "updateCandidate";
    }
//
//    @PostMapping("/saveCandidate")
//    public String saveCandidate(@ModelAttribute Candidate candidate) {
//        service.create(candidate);
//        return "redirect:/candidates";
//    }

    @PostMapping("/createCandidate")
    public String createCandidate(@ModelAttribute Candidate candidate) {
        service.create(candidate);
        return "redirect:/candidates";
    }

    @PostMapping("/updateCandidate")
    public String updateCandidate(@ModelAttribute Candidate candidate) {
        service.update(candidate);
        return "redirect:/candidates";
    }
}
