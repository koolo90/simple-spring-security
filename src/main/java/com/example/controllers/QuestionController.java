package com.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/question")
public class QuestionController {
    private QuestionRepository questionRepository;

    @GetMapping("/count")
    public String count(Model model) {
        questionRepository.count();
        return "question/count";
    }
}
