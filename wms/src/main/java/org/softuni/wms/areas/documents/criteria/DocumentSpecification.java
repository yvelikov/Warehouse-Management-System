package org.softuni.wms.areas.documents.criteria;

import org.softuni.wms.areas.documents.entities.docs.Document;
import org.softuni.wms.utils.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public class DocumentSpecification implements Specification<Document> {

    private final SearchCriteria searchCriteria;

    public DocumentSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Document> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        switch (this.searchCriteria.getOperation()) {
            case "like":
                return criteriaBuilder.like(
                        root.<String>get(this.searchCriteria.getKey()), "%" + this.searchCriteria.getValue() + "%");
            default:
                return null;
        }
    }
}
