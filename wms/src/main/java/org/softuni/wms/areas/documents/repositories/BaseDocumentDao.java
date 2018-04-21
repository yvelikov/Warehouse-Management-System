package org.softuni.wms.areas.documents.repositories;

import org.softuni.wms.areas.documents.entities.docs.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseDocumentDao<E extends Document> extends JpaRepository<E, String> ,
        PagingAndSortingRepository<E, String>,
        JpaSpecificationExecutor<E> {

    @Query(value = "SELECT e FROM #{#entityName} e")
    Page<E> findAllDocuments(Pageable pageable);


    @Query("SELECT d.id, d.documentCode, d.date, p.name, u.username FROM #{#entityName} AS d " +
            "JOIN Partner AS p ON p.id = d.partner.id " +
            "JOIN User AS u ON u.id = d.user.id " +
            "WHERE d.documentCode LIKE %?1% OR p.name LIKE %?1% OR u.username LIKE %?1%")
    Page<Object> findAllContaining(String str, Pageable pageable);

    @Query(value = "SELECT d.id, d.type, d.date, d.document_number, p.name, p.vat_number, p.address, concat(u.first_name,' ',u.last_name) FROM documents AS d\n" +
            "JOIN partners AS p\n" +
            "ON p.id = d.partner_id\n" +
            "JOIN users AS u\n" +
            "ON u.id = d.user_id\n" +
            "WHERE d.id = :docId", nativeQuery = true)
    Object findDetailsById(@Param("docId") String id);

    @Query(value = "SELECT d.id, d.document_number, d.date, d.type FROM documents AS d\n" +
            "WHERE d.partner_id = :partnerId", nativeQuery = true)
    Page<Object> findDocumentsByPartnerId(@Param("partnerId") String id, Pageable pageable);
}
