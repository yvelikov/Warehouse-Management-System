package org.softuni.wms.areas.documents.repositories;

import org.softuni.wms.areas.documents.entities.docs.DeliveryNote;
import org.softuni.wms.areas.documents.entities.docs.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface BaseDocumentDao<E extends Document> extends JpaRepository<E, String> ,
        PagingAndSortingRepository<E, String>,
        JpaSpecificationExecutor<E> {

    @Query(value = "SELECT e FROM #{#entityName} e")
    Page<E> findAllDocuments(Pageable pageable);

    @Query(value = "SELECT e FROM #{#entityName} e")
    Page<DeliveryNote> findAllDocuments(Specification<E> specification, Pageable pageable);
}
