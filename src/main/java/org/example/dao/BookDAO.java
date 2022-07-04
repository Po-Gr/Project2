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
    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
    }

//    public List<Book> getAllBooks() {
//        return jdbcTemplate.query("SELECT * FROM book ORDER BY title", new BookMapper());
//    }

    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Book getBook(int id) {
//        return jdbcTemplate.query("SELECT * FROM book WHERE book_id=?", new Object[]{id},
//                new BookMapper()).stream().findAny().orElse(null);
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, id);
    }

    @Transactional()
    public void save(Book book) {
//        jdbcTemplate.update("INSERT INTO book (title, author, year) VALUES (?, ?, ?)",
//                book.getTitle(), book.getAuthor(), book.getYear());

        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }

    @Transactional()
    public void update(Book book, int id) {
//        jdbcTemplate.update("UPDATE book SET title=?, author=?, year=? WHERE book_id=?",
//               book.getTitle(), book.getAuthor(), book.getYear(), id);

        Session session = sessionFactory.getCurrentSession();
        Book oldBook = session.get(Book.class, id);
        oldBook.setTitle(book.getTitle());
        oldBook.setAuthor(book.getAuthor());
        oldBook.setYear(book.getYear());
    }

    @Transactional(readOnly = true)
    public void delete(int id) {
//        jdbcTemplate.update("DELETE FROM book WHERE book_id=?", id);

        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Book.class, id));
    }

    @Transactional()
    public void setFree(int id) {
//        jdbcTemplate.update("UPDATE book SET person_id=null WHERE book_id=?", id);

        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        book.setReader(null);
    }

    @Transactional()
    public void setBookToPerson(int id, int personId) {
//        jdbcTemplate.update("UPDATE book SET person_id=? WHERE book_id=?", personId, id);

        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        Person person = session.get(Person.class, personId);

        book.setReader(person);
        person.getBooks().add(book);
    }
}
