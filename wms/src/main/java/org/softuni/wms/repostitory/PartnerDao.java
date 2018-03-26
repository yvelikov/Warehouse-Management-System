package org.softuni.wms.repostitory;

import org.softuni.wms.entities.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerDao extends JpaRepository<Partner, String> {

}
