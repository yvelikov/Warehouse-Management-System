package org.softuni.wms.areas.partners.repositories;

import org.softuni.wms.areas.partners.entities.Partner;
import org.softuni.wms.areas.partners.models.view.PartnerViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "partners", path = "partners", excerptProjection = PartnerViewModel.class)
public interface PartnerDao extends JpaRepository<Partner, String>,
        PagingAndSortingRepository<Partner, String>,
        JpaSpecificationExecutor<Partner> {

    List<Partner> findPartnersBySupplierIsTrueOrderByName();

    Partner findPartnerByName(String name);

    List<Partner> findAllBySupplierIsTrue();
}
