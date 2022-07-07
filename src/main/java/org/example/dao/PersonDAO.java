package org.example.dao;

import org.example.models.Book;
import org.example.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class PersonDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> getAllPeople() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT p FROM Person p", Person.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Person getPerson(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, id);
    }

    @Transactional
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

    @Transactional
    public void update(Person person, int id) {
        Session session = sessionFactory.getCurrentSession();
        Person oldPerson = session.get(Person.class, id);
        oldPerson.setFullName(person.getFullName());
        oldPerson.setBirthYear(person.getBirthYear());

    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
//        session.remove(id);
        session.remove(session.get(Person.class, id));
    }

    @Transactional(readOnly = true)
    public List<Book> getBooks(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, id).getBooks();
    }
}
