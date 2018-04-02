package org.softuni.wms.areas.history.repositories;

import org.softuni.wms.areas.history.entities.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryDao extends JpaRepository<History, String> {
}
