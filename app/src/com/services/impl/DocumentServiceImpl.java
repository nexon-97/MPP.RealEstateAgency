package com.services.impl;

import com.dao.DocumentDAO;
import com.dao.impl.DocumentDAOImpl;
import com.model.Document;
import com.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;

public class DocumentServiceImpl implements DocumentService {

    @Autowired
    DocumentDAO documentDAO;

    @Override
    public boolean addDocument(Document document) {
        return  documentDAO.addDocument(document);
    }
}
