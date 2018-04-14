package org.softuni.wms.areas.parts.models.view;

import org.softuni.wms.areas.parts.entities.Part;
import org.softuni.wms.areas.parts.entities.enums.UnitOfMeasure;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = Part.class, name = "partViewModel")
public interface PartViewModel {

    String getId();
    String getArticleCode();
    String getName();
    String getDeliveryPrice();
    UnitOfMeasure getUnitOfMeasure();
}
