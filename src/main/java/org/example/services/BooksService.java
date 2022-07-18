package org.example.services;

import org.example.dao.BookDAO;
import org.example.models.Book;
import org.example.models.Person;
import org.example.repositories.BooksRepository;
import org.example.repositories.PeopleRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private final BookDAO bookDAO;
    private final PeopleRepository peopleRepository;

    public BooksService(BooksRepository booksRepository, BookDAO bookDAO, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.bookDAO = bookDAO;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> getAllBooks() {
        return booksRepository.findAll();
    }

    public List<Book> getAllBooks(int page, int booksPerPage, String sortBy) {
//        if (page == null){
//            if (sortByYear == null)
//                return booksRepository.findAll(Sort.by("title"));
//            else
//                return booksRepository.findAll(Sort.by("year"));
//        }
//        else {
//            if (sortByYear == null)
//                return booksRepository.findAll(PageRequest.of((Integer) page, (Integer) booksPerPage, Sort.by("title"))).getContent();
//            else
//                return booksRepository.findAll(PageRequest.of((Integer) page, (Integer) booksPerPage, Sort.by("year"))).getContent();
//        }



//        String sortBy = "title";
//        Integer pageNum = 0;
//        Integer booksCount = 15;
//
//        if (sortByYear != null)
//            sortBy = "year";
//        if (page != null)
//            pageNum = (Integer) page;
//        if (booksPerPage != null)
//            booksCount = (Integer) booksPerPage;
//
//        return booksRepository.findAll(PageRequest.of(pageNum, booksCount, Sort.by(sortBy))).getContent();

        return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by(sortBy))).getContent();
    }

    public Book getBook(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    public List<Book> getBooksByTitleStarting(String string) {
        return booksRepository.findByTitleStartingWith(string);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(Book book, int id) {
        book.setId(id);
        booksRepository.save(book);
    }

    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void setFree(int id) {
        Book book = getBook(id);
        book.setReader(null);
        book.setTakenAt(null);
    }

    @Transactional
    public void setBookToPerson(int id, int personId) {
        Book book = getBook(id);
        Person person = peopleRepository.findById(personId).orElse(null);

        book.setReader(person);
        book.setTakenAt(new Date());
        person.getBooks().add(book);
    }
}
