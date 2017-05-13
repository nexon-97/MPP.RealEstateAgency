package com.services.impl;

import com.dao.DocumentDAO;
import com.dao.impl.DocumentDAOImpl;
import com.model.Document;
import com.model.User;
import com.services.DocumentService;
import com.services.shared.AbstractCrudService;
import com.services.shared.ServiceId;
import com.services.shared.ServiceSharedResources;

import java.util.List;

public class DocumentServiceImpl extends AbstractCrudService<Document> implements DocumentService {

    public DocumentServiceImpl(ServiceSharedResources sharedResources) {
        super(ServiceId.DocumentService, sharedResources, Document.class);
    }

    @Override
    public List<Document> listUserDocuments(User user) {
        DocumentDAO documentDAO = new DocumentDAOImpl();
        return  documentDAO.listUserDocuments(user);
    }
}
