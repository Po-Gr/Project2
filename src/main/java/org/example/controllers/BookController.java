package org.example.controllers;

import org.example.dao.BookDAO;
import org.example.dao.PersonDAO;
import org.example.models.Book;
import org.example.models.Person;
import org.example.repositories.BooksRepository;
import org.example.repositories.PeopleRepository;
import org.example.services.BooksService;
import org.example.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/library/books")
public class BookController {
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String getAllBooks(@ModelAttribute("searchBy") Book book, Model model, @RequestParam(value = "page", required = false) Integer page,  // посмотреть без атрибута модели
                              @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                              @RequestParam(value = "sort_by_year",required = false) Boolean sortByYear) {
        String sortBy = "title";
        if (sortByYear == null)
            sortByYear = false;
        if (sortByYear)
            sortBy = "year";
        if (page == null)
            page = 0;
        if (booksPerPage == null)
            booksPerPage = 15;
        model.addAttribute("books", booksService.getAllBooks(page, booksPerPage, sortBy));


        model.addAttribute("page", page);
        model.addAttribute("booksPerPage", booksPerPage);
        model.addAttribute("sortByYear", sortByYear);


        model.addAttribute("hasNext", page < booksService.getAllBooks().size() / booksPerPage);
        model.addAttribute("hasPrevious", page > 0);

        return "books/allBooks";
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable("id")int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", booksService.getBook(id));
        model.addAttribute("people", peopleService.getAllPeople());
        model.addAttribute("reader", booksService.getBook(id).getReader());
        return "books/book";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";
        booksService.save(book);
        return "redirect:";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id")int id, Model model) {
        model.addAttribute("book", booksService.getBook(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id")int id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        booksService.update(book, id);
        return "redirect:";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id")int id) {
        booksService.delete(id);
        return "redirect:.";
    }

    @PutMapping("/{id}")
    public String setFree(@PathVariable("id")int id) {
        booksService.setFree(id);
        return "redirect:";
    }

    @PatchMapping("/{id}/add")
    public String setBookToPerson(@ModelAttribute("person") Person person, @PathVariable("id")int id) {
        booksService.setBookToPerson(id, person.getId());
        return "redirect:.";
    }

//    @GetMapping("/search")
//    public String searchByTitle(Model model, @RequestParam(value = "startWith", required = false) String startWith) {
//        return "books/search";
//    }

//    @GetMapping("/search")
//    public String searchByTitle(@ModelAttribute("searchBy") Book book, Model model) {
//        List<Book> books = booksService.getBooksByTitleStarting(book.getTitle());
//
//        model.addAttribute("books", books);
//        return "books/search";
//    }

    @GetMapping("/search")
    public String searchByTitle(Model model, @RequestParam(value = "title", required = false) String startWith,
                                @ModelAttribute("searchBy") @Valid Book book, BindingResult bindingResult) {

        if(startWith != null && !startWith.equals("")) {
            List<Book> books = booksService.getBooksByTitleStarting(startWith);
            model.addAttribute("books", books);
            if (bindingResult.hasErrors())
                return "books/search";
        }
        return "books/search";
    }
}
