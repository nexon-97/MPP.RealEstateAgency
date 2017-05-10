package com.services;

import com.dao.DocumentDAO;
import com.dao.DocumentDAOImpl;
import com.model.Document;

public class DocumentServiceImpl implements DocumentService {

    @Override
    public boolean addDocument(Document document) {
        DocumentDAO documentDAO = new DocumentDAOImpl();

        return  documentDAO.addDocument(document);
    }
}
