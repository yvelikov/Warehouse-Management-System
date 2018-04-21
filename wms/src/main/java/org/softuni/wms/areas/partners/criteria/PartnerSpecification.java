package org.softuni.wms.areas.partners.criteria;

import org.softuni.wms.areas.partners.entities.Partner;
import org.softuni.wms.common.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public class PartnerSpecification implements Specification<Partner> {

    private final SearchCriteria searchCriteria;

    public PartnerSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Partner> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        switch (this.searchCriteria.getOperation()) {
            case "like":
                return criteriaBuilder.like(
                        root.<String>get(this.searchCriteria.getKey()), "%" + this.searchCriteria.getValue() + "%");
            case "=":
                return criteriaBuilder.equal(
                        root.<Boolean>get(this.searchCriteria.getKey()), (Boolean)this.searchCriteria.getValue());

            default:
                return null;
        }
    }
}
