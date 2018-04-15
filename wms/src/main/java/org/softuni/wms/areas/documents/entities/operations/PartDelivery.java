package org.softuni.wms.areas.documents.entities.operations;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "DELIVERY")
public class PartDelivery extends Operation{


}
