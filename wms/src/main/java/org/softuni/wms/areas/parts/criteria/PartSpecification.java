package org.softuni.wms.areas.parts.criteria;

import org.softuni.wms.areas.parts.entities.Part;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public class PartSpecification implements Specification<Part> {

    private final PartSearchCriteria partSearchCriteria;

    public PartSpecification(PartSearchCriteria partSearchCriteria) {
        this.partSearchCriteria = partSearchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Part> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        switch (this.partSearchCriteria.getOperation()) {
            case "like":
                return criteriaBuilder.like(
                        root.<String>get(this.partSearchCriteria.getKey()), "%" + this.partSearchCriteria.getValue() + "%");
            case "=":
                return criteriaBuilder.equal(
                        root.<Boolean>get(this.partSearchCriteria.getKey()), (Integer)this.partSearchCriteria.getValue());
            case ">":
                return criteriaBuilder.greaterThan(
                    root.<String> get(partSearchCriteria.getKey()), partSearchCriteria.getValue().toString());

            default:
                return null;
        }
    }
}
