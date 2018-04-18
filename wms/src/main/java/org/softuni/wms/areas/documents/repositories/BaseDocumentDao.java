package org.softuni.wms.areas.documents.repositories;

import org.softuni.wms.areas.documents.entities.docs.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

@NoRepositoryBean
public interface BaseDocumentDao<E extends Document> extends JpaRepository<E, String> ,
        PagingAndSortingRepository<E, String>,
        JpaSpecificationExecutor<E> {

    @Query(value = "SELECT e FROM #{#entityName} e")
    Page<E> findAllDocuments(Pageable pageable);


    @Query("SELECT d.id, d.documentCode, d.date, p.name, u.username from #{#entityName} as d " +
            "join Partner as p on p.id = d.partner.id " +
            "join User as u on u.id = d.user.id " +
            "where d.documentCode like %?1% or p.name like %?1% or u.username like %?1%")
    Page<Object> findAllContaining(String str, Pageable pageable);

    @Query(value = "SELECT d.id, d.type, d.date, d.document_number, p.name, p.vat_number, p.address, concat(u.first_name,' ',u.last_name) FROM documents AS d\n" +
            "JOIN partners AS p\n" +
            "ON p.id = d.partner_id\n" +
            "JOIN users AS u\n" +
            "ON u.id = d.user_id\n" +
            "WHERE d.id = :docId", nativeQuery = true)
    Object findDetailsById(@Param("docId") String id);
}
