package org.softuni.wms.repostitory;

import org.softuni.wms.entities.documents.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentDao extends JpaRepository<Document, String> {
}
