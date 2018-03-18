package org.softuni.wms.repostitory;

import org.softuni.wms.entities.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryDao extends JpaRepository<History, String> {
}
