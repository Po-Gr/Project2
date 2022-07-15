package org.example.dao;

import org.example.models.Book;
import org.example.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Date;
import java.util.List;

@Component
public class BookDAO {
    private final EntityManager entityManager;

    @Autowired
    public BookDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
//
//    @Transactional
//    public void setFree(int id) {
//        Session session = entityManager.unwrap(Session.class);
//
//        Book book = session.get(Book.class, id);
//        book.setReader(null);
//        book.setTakenAt(null);
//    }
//
//    @Transactional
//    public void setBookToPerson(int id, int personId) {
//        Session session = entityManager.unwrap(Session.class);
//
//        Book book = session.get(Book.class, id);
//        Person person = session.get(Person.class, personId);
//
//        book.setReader(person);
//        book.setTakenAt(new Date());
//        person.getBooks().add(book);
//    }
}
