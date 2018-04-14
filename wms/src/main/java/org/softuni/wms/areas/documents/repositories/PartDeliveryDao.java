package org.softuni.wms.areas.documents.repositories;

import org.softuni.wms.areas.documents.entities.operations.PartDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartDeliveryDao extends JpaRepository<PartDelivery, String> {
}
