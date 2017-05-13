package com.controller;

import com.model.Entity;
import com.services.CrudService;
import org.springframework.web.servlet.ModelAndView;

public interface CrudController<E extends Entity> {
    CrudService<E> getService();
}
