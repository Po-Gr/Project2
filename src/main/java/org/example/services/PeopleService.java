package org.example.services;

import org.example.dao.PersonDAO;
import org.example.models.Book;
import org.example.models.Person;
import org.example.repositories.BooksRepository;
import org.example.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public void someM(Date currentDate, int id) {
        for (Book book: getBooks(id)) {
            book.setExpired(currentDate.getTime() - book.getTakenAt().getTime() > 864000000L);
        }
    }
}
