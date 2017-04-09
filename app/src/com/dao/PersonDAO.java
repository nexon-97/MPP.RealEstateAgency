package com.dao;

import com.model.Person;

import java.util.List;

public interface PersonDAO {
    void save(Person p);
    List<Person> list();
}
