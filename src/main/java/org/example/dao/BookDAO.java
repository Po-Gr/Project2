package org.example.dao;

import org.example.models.Book;
import org.example.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class BookDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public BookDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Book getBook(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, id);
    }

    @Transactional
    public void save(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }

    @Transactional
    public void update(Book book, int id) {
        Session session = sessionFactory.getCurrentSession();
        Book oldBook = session.get(Book.class, id);
        oldBook.setTitle(book.getTitle());
        oldBook.setAuthor(book.getAuthor());
        oldBook.setYear(book.getYear());
    }

    @Transactional(readOnly = true)
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Book.class, id));
    }

    @Transactional
    public void setFree(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        book.setReader(null);
    }

    @Transactional
    public void setBookToPerson(int id, int personId) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        Person person = session.get(Person.class, personId);

        book.setReader(person);
        person.getBooks().add(book);
    }
}
