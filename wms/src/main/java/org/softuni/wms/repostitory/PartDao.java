package org.softuni.wms.repostitory;

import org.softuni.wms.entities.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartDao extends JpaRepository<Part,String>{
    @Query
    List<Part> getPartsByArticleCodeContains(String articleCode);

    @Query
    List<Part> getPartsByArticleCodeContainsAndNameContains(String articleCode, String name);
}
