package org.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/library")
public class LibraryController {
    public LibraryController() {
    }

    @GetMapping()
    public String showLibraryPage() {
        return "library/startPage";
    }
}
