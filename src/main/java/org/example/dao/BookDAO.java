package org.example.dao;

import org.example.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAllBooks() {
        return jdbcTemplate.query("SELECT * FROM book ORDER BY title", new BookMapper());
    }

    public Book getBook(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE book_id=?", new Object[]{id},
                new BookMapper()).stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book (title, author, year) VALUES (?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void update(Book book, int id) {
        jdbcTemplate.update("UPDATE book SET title=?, author=?, year=? WHERE book_id=?",
               book.getTitle(), book.getAuthor(), book.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE book_id=?", id);
    }

    public void setFree(int id) {
        jdbcTemplate.update("UPDATE book SET person_id=null WHERE book_id=?", id);
    }

    public void setBookToPerson(int id, int personId) {
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE book_id=?", personId, id);
    }
}
