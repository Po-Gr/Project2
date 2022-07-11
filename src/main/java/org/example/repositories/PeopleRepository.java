package org.example.repositories;

import org.example.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    List<Person> findByFullName(String name);

    List<Person> findByFullNameOrderByBirthYear(String name);

    List<Person> findByFullNameStartingWith(String name);
}
