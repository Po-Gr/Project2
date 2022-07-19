package org.example.services;

import org.example.dao.PersonDAO;
import org.example.models.Book;
import org.example.models.Person;
import org.example.repositories.BooksRepository;
import org.example.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final BooksRepository booksRepository;
    private final PersonDAO personDAO;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, BooksRepository booksRepository, PersonDAO personDAO) {
        this.peopleRepository = peopleRepository;
        this.booksRepository = booksRepository;
        this.personDAO = personDAO;
    }

    public List<Person> getAllPeople() {
        return peopleRepository.findAll();
    }

    public List<Person> getAllPeople(int page, int itemsPerPage, String sortBy) {
        return peopleRepository.findAll(PageRequest.of(page, itemsPerPage, Sort.by(sortBy))).getContent();
    }

    public Person getPerson(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(Person person, int id) {
        person.setId(id);
        peopleRepository.save(person);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public List<Book> getBooks(int id) {
        return booksRepository.findByReader(getPerson(id));
    }

    @Transactional
    public void areBooksExpired(Calendar currentDate, List<Book> books) {
        currentDate.add(Calendar.MONTH, -1);
        for (Book book: books) {
            book.setExpired(currentDate.after(book.getTakenAt()));
        }
    }
}
