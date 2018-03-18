package org.softuni.wms.repostitory;

import org.softuni.wms.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierDao extends JpaRepository<Supplier, String> {
}
