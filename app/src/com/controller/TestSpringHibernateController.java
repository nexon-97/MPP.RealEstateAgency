package com.controller;

import com.dao.PersonDAO;
import com.model.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/testController")
public class TestSpringHibernateController {
    @Autowired
    private ApplicationContext context;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView helloWorldRequest() {
        String message = "<h1>Success</h1>";

        PersonDAO personDAO = context.getBean(PersonDAO.class);
        Person person = new Person();
        person.setName("Pankaj"); person.setCountry("India");

        personDAO.save(person);
        System.out.println("Person::"+person);

        List<Person> list = personDAO.list();
        for (Person p : list) {
            System.out.println("Person List::"+p);
        }

        return new ModelAndView("welcome", "message", message);
    }
}
