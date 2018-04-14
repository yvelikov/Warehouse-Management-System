package org.softuni.wms.areas.partners.criteria;

import org.softuni.wms.areas.partners.entities.Partner;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public class PartnerSpecification implements Specification<Partner> {

    private final PartnerSearchCriteria partnerSearchCriteria;

    public PartnerSpecification(PartnerSearchCriteria partnerSearchCriteria) {
        this.partnerSearchCriteria = partnerSearchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Partner> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        switch (this.partnerSearchCriteria.getOperation()) {
            case "like":
                return criteriaBuilder.like(
                        root.<String>get(this.partnerSearchCriteria.getKey()), "%" + this.partnerSearchCriteria.getValue() + "%");
            case "=":
                return criteriaBuilder.equal(
                        root.<Boolean>get(this.partnerSearchCriteria.getKey()), (Boolean)this.partnerSearchCriteria.getValue());

            default:
                return null;
        }
    }
}
