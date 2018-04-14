package org.softuni.wms.areas.documents.repositories;

import org.softuni.wms.areas.documents.entities.operations.PartIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartIssueDao extends JpaRepository<PartIssue, String > {
}
