package com.dao;

import com.model.Document;
import com.model.User;

import java.util.List;

public interface DocumentDAO extends CrudDAO<Document> {
     List<Document> listUserDocuments(User user);
}
