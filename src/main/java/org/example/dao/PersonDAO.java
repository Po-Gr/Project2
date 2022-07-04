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
public class PersonDAO {
    private final SessionFactory sessionFactory;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory, JdbcTemplate jdbcTemplate) {
        this.sessionFactory = sessionFactory;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(readOnly = true)
    public List<Person> getAllPeople() {
//        return jdbcTemplate.query("SELECT * FROM person ORDER BY full_name", new PersonMapper());

        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT p FROM Person p", Person.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Person getPerson(int id) {
//        return jdbcTemplate.query("SELECT * FROM person WHERE person_id=?", new Object[]{id},
//                new PersonMapper()).stream().findAny().orElse(null);

        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, id);
    }

    @Transactional()
    public void save(Person person) {
//        jdbcTemplate.update("INSERT INTO person (full_name, birth_year) VALUES (?, ?)",
//                person.getFullName(), person.getBirthYear());

        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

    @Transactional()
    public void update(Person person, int id) {
//        jdbcTemplate.update("UPDATE person SET full_name=?, birth_year=? WHERE person_id=?",
//                person.getFullName(), person.getBirthYear(), id);

        Session session = sessionFactory.getCurrentSession();
        Person oldPerson = session.get(Person.class, id);
        oldPerson.setFullName(person.getFullName());
        oldPerson.setBirthYear(person.getBirthYear());

    }

    @Transactional()
    public void delete(int id) {
//        jdbcTemplate.update("DELETE FROM person WHERE person_id=?", id);

        Session session = sessionFactory.getCurrentSession();
//        session.remove(id);
        session.remove(session.get(Person.class, id));
    }

    @Transactional(readOnly = true)
    public List<Book> getBooks(int id) {
//        return jdbcTemplate.query("SELECT * FROM book WHERE person_id=?", new Object[]{id},
//                new BookMapper());

        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, id).getBooks();
    }
}
