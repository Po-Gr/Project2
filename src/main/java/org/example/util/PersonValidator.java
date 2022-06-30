//package org.example.util;
//
//import org.example.dao.PersonDAO;
//import org.example.models.Person;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//import org.springframework.validation.Validator;
//
//import java.util.Optional;
//
//@Component
//public class PersonValidator implements Validator {
//    private final PersonDAO personDAO;
//
//    @Autowired
//    public PersonValidator(PersonDAO personDAO) {
//        this.personDAO = personDAO;
//    }
//
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return Person.class.equals(aClass);
//    }
//
//    @Override
//    public void validate(Object o, Errors errors) {
//        Person person = (Person) o;
//        Optional<Person> optional = personDAO.getPerson(person.getEmail());
//
//        if (optional.isPresent() && optional.get().getId() != (person.getId()))
//            errors.rejectValue("email", "", "This email is already taken");
//    }
//}
