package org.softuni.wms.areas.parts.criteria;

import org.softuni.wms.areas.parts.entities.Part;
import org.softuni.wms.utils.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public class PartSpecification implements Specification<Part> {

    private final SearchCriteria searchCriteria;

    public PartSpecification(SearchCriteria partSearchCriteria) {
        this.searchCriteria = partSearchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Part> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        switch (this.searchCriteria.getOperation()) {
            case "like":
                return criteriaBuilder.like(
                        root.<String>get(this.searchCriteria.getKey()), "%" + this.searchCriteria.getValue() + "%");
            case "=":
                return criteriaBuilder.equal(
                        root.<Boolean>get(this.searchCriteria.getKey()), (Integer)this.searchCriteria.getValue());
            case ">":
                return criteriaBuilder.greaterThan(
                    root.<String> get(searchCriteria.getKey()), searchCriteria.getValue().toString());

            default:
                return null;
        }
    }
}
