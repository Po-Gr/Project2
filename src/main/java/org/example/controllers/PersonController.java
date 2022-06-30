package org.example.controllers;

import org.example.dao.PersonDAO;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/library/people")
public class PersonController {
    private final PersonDAO personDAO;
//    private final PersonValidator personValidator;

    @Autowired
    public PersonController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String getAllPeople(Model model) {
        model.addAttribute("people", personDAO.getAllPeople());
        return "people/allPeople";
    }

    @GetMapping("/{id}")
    public String getPerson(@PathVariable("id")int id, Model model) {
        model.addAttribute("person", personDAO.getPerson(id));
        model.addAttribute("books", personDAO.getBooks(id));

        return "people/person";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
//        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/new";

        personDAO.save(person);
        return "redirect:";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id")int id, Model model) {
        model.addAttribute("person", personDAO.getPerson(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id")int id) {

//        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/edit";

        personDAO.update(person, id);
        return "redirect:/library/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id")int id) {
        personDAO.delete(id);
        return "redirect:.";
    }
}
