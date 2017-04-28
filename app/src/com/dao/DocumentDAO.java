package com.dao;

import com.model.Document;

public interface DocumentDAO {
     Document getDocumentByID(int id);
     boolean updateDocument(Document document);
     boolean deleteDocument(Document document);
     boolean addDocument(Document document);

}
