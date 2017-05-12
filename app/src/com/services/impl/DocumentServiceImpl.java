package com.services.impl;

import com.dao.DocumentDAO;
import com.dao.impl.DocumentDAOImpl;
import com.model.Document;
import com.services.DocumentService;

public class DocumentServiceImpl implements DocumentService {

    @Override
    public boolean addDocument(Document document) {
        DocumentDAO documentDAO = new DocumentDAOImpl();

        return  documentDAO.addDocument(document);
    }
}
