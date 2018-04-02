package org.softuni.wms.areas.documents.repositories;

import org.softuni.wms.areas.documents.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentDao extends JpaRepository<Document, String> {
}
