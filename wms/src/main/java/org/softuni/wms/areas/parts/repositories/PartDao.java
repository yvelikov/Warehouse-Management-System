package org.softuni.wms.areas.parts.repositories;

import org.softuni.wms.areas.parts.entities.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartDao extends JpaRepository<Part,String>,PagingAndSortingRepository<Part, String>,
        JpaSpecificationExecutor<Part> {

    List<Part> findAllBySupplierId(String id);

    @Query(value = "SELECT * FROM parts\n" +
            "WHERE quantity > 0", nativeQuery = true)
    List<Part> findAllPartsOnStock();
}
