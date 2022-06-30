package org.example.dao;

import org.example.models.Book;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAllPeople() {
        return jdbcTemplate.query("SELECT * FROM person ORDER BY full_name", new PersonMapper());
    }

    public Person getPerson(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE person_id=?", new Object[]{id},
                new PersonMapper()).stream().findAny().orElse(null);
    }

//    public Optional<Person> getPerson(String email) {
//        return jdbcTemplate.query("SELECT * FROM person WHERE email=?", new Object[]{email},
//                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
//    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person (full_name, birth_year) VALUES (?, ?)",
                person.getFullName(), person.getBirthYear());
    }

    public void update(Person person, int id) {
        jdbcTemplate.update("UPDATE person SET full_name=?, birth_year=? WHERE person_id=?",
                person.getFullName(), person.getBirthYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE person_id=?", id);
    }

    public List<Book> getBooks(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE person_id=?", new Object[]{id},
                new BookMapper());
    }
}
