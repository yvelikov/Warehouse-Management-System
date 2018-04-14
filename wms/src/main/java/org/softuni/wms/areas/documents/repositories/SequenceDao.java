package org.softuni.wms.areas.documents.repositories;

import org.softuni.wms.areas.documents.entities.docs.Sequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceDao extends JpaRepository<Sequence, Long> {
    Sequence findByName(String name);
}
