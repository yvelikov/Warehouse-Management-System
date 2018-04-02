package org.softuni.wms.areas.partners.repositories;

import org.softuni.wms.areas.partners.entities.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerDao extends JpaRepository<Partner, String> {

}
