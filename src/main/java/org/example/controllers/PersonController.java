package org.example.controllers;

import org.example.logic.Paging;
import org.example.models.Book;
import org.example.models.Person;
import org.example.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/library/people")
public class PersonController {
    private final PeopleService peopleService;

    @Autowired
    public PersonController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

//    @GetMapping()
//    public String getAllPeople(Model model) {
//        model.addAttribute("people", peopleService.getAllPeople());
//        return "people/allPeople";
//    }

    @GetMapping()
    public String getAllPeople(Model model, @RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "items_per_page", required = false) Integer itemsPerPage,
                               @RequestParam(value = "sort_by_year",required = false) Boolean sortByYear) {
        Paging paging = Paging.pageFabric(page, itemsPerPage, sortByYear, peopleService.getAllPeople().size(), Person.class);

        model.addAttribute("people", peopleService.getAllPeople(paging.getPage(), paging.getItemsPerPage(), paging.getSortBy()));
        model.addAttribute("paging", paging);

        return "people/allPeople";
    }


    @GetMapping("/{id}")
    public String getPerson(@PathVariable("id")int id, Model model) {
        List<Book> books = peopleService.getBooks(id);

        peopleService.areBooksExpired(Calendar.getInstance(), books);

        model.addAttribute("person", peopleService.getPerson(id));
        model.addAttribute("books", books);
        model.addAttribute("empty", books.isEmpty());

        return "people/person";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/new";

        peopleService.save(person);
        return "redirect:";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id")int id, Model model) {
        model.addAttribute("person", peopleService.getPerson(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id")int id) {
        if (bindingResult.hasErrors())
            return "people/edit";

        peopleService.update(person, id);
        return "redirect:/library/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id")int id) {
        peopleService.delete(id);
        return "redirect:.";
    }
}
