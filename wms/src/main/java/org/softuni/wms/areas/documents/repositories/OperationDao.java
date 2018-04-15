package org.softuni.wms.areas.documents.repositories;

import org.softuni.wms.areas.documents.entities.operations.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationDao extends JpaRepository<Operation, String> {
}
