package org.softuni.wms.areas.documents.entities.operations;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "ISSUE")
public class PartIssue extends Operation{

}
