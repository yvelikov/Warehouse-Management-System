package org.softuni.wms.areas.documents.entities;

import org.softuni.wms.areas.partners.entities.Partner;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "ISSUE")
public class IssueNote extends Document{

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Partner customer;


}
