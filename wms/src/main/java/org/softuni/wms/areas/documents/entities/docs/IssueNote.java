package org.softuni.wms.areas.documents.entities.docs;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "ISSUE")
public class IssueNote extends Document {

}
