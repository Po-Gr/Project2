package org.example.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class BookDAO {
    private final EntityManager entityManager;

    @Autowired
    public BookDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
