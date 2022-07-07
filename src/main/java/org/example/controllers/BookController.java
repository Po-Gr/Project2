package org.example.controllers;

import org.example.dao.BookDAO;
import org.example.dao.PersonDAO;
import org.example.models.Book;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/library/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookDAO.getAllBooks());
        return "books/allBooks";
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable("id")int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.getBook(id));
        model.addAttribute("people", personDAO.getAllPeople());
        model.addAttribute("reader", bookDAO.getBook(id).getReader());
        return "books/book";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
//        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "books/new";

        bookDAO.save(book);
        return "redirect:";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id")int id, Model model) {
        model.addAttribute("book", bookDAO.getBook(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id")int id) {

//        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "books/edit";

        bookDAO.update(book, id);
        return "redirect:";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id")int id) {
        bookDAO.delete(id);
        return "redirect:.";
    }

    @PutMapping("/{id}")
    public String setFree(@PathVariable("id")int id) {
        bookDAO.setFree(id);
        return "redirect:";
    }

    @PatchMapping("/{id}/add")
    public String setBookToPerson(@ModelAttribute("person") Person person, @PathVariable("id")int id) {
        bookDAO.setBookToPerson(id, person.getId());
        return "redirect:.";
    }
}
