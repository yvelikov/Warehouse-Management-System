package org.softuni.wms.areas.documents.services.api;

import org.softuni.wms.areas.documents.models.binding.AddPartOperationDto;

public interface OperationService {

    void save(AddPartOperationDto addPartDeliveryDto);
}
