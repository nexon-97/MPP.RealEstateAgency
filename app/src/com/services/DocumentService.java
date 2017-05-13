package com.services;

import com.model.Document;
import com.model.User;

import java.util.List;

public interface DocumentService extends CrudService<Document> {
    List<Document> listUserDocuments(User user);
}
