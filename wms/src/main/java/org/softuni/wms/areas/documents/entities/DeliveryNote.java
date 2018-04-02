package org.softuni.wms.areas.documents.entities;

import org.softuni.wms.areas.partners.entities.Partner;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "DELIVERY")
public class DeliveryNote extends Document {

    @ManyToOne(optional = false)
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    private Partner supplier;

}
