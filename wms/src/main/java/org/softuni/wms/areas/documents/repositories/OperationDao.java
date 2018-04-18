package org.softuni.wms.areas.documents.repositories;

import org.softuni.wms.areas.documents.entities.operations.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationDao<E extends Operation> extends JpaRepository<E, String> {

    @Query(value = "SELECT e FROM #{#entityName} e")
    List<E> findAllOperations();

    @Query(value = "SELECT p.article_code, p.name, o.quantity, p.unit_of_measure FROM operations AS o\n" +
            "JOIN parts as p\n" +
            "ON p.id = o.part_id\n" +
            "WHERE document_id = :opId",nativeQuery = true)
    List<Object> findOperationByDocumentId(@Param("opId") String id);

}
