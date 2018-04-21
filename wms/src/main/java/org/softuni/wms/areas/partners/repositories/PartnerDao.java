package org.softuni.wms.areas.partners.repositories;

import org.softuni.wms.areas.partners.entities.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerDao extends JpaRepository<Partner, String>,
        PagingAndSortingRepository<Partner, String>,
        JpaSpecificationExecutor<Partner> {

    List<Partner> findPartnersBySupplierIsTrueOrderByName();

    Partner findPartnerByName(String name);

    List<Partner> findAllBySupplierIsTrue();

    List<Partner> findAllByCustomerIsTrue();
}
