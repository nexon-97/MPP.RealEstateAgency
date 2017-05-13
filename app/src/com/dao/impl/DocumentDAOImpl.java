package com.dao.impl;

import com.dao.AbstractCrudDAO;
import com.dao.DocumentDAO;
import com.model.Document;
import com.model.User;

import java.util.List;

public class DocumentDAOImpl extends AbstractCrudDAO<Document> implements DocumentDAO {

    public DocumentDAOImpl() {
        super(Document.class);
    }

    @Override
    public List<Document> listUserDocuments(User user) {
        return null;
    }
}
