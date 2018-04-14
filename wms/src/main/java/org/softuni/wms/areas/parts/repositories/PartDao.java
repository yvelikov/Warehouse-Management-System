package org.softuni.wms.areas.parts.repositories;

import org.softuni.wms.areas.parts.entities.Part;
import org.softuni.wms.areas.parts.models.view.PartViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "parts", path = "parts", excerptProjection = PartViewModel.class)
public interface PartDao extends JpaRepository<Part,String>,PagingAndSortingRepository<Part, String>,
        JpaSpecificationExecutor<Part> {

    List<Part> getPartsByArticleCodeContains(String articleCode);

    List<Part> getPartsByArticleCodeContainsAndNameContains(String articleCode, String name);

    List<Part> findAllBySupplierId(String id);
}
