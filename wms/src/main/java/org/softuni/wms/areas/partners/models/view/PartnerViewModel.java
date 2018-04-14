package org.softuni.wms.areas.partners.models.view;

import org.softuni.wms.areas.partners.entities.Partner;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = Partner.class, name = "partnerViewModel")
public interface PartnerViewModel {

    String getId();
    String getVatNumber();
    String getName();
    Boolean getCustomer();
    Boolean getSupplier();
}
