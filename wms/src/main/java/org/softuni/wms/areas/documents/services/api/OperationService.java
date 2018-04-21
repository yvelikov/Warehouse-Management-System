package org.softuni.wms.areas.documents.services.api;

import org.softuni.wms.areas.documents.models.binding.AddPartOperationDto;
import org.softuni.wms.areas.documents.models.view.OperationViewModel;

import java.util.List;

public interface OperationService {

    boolean save(AddPartOperationDto addPartDeliveryDto);

    List<OperationViewModel> findPartDeliveryByDocumentId(String id);

    List<OperationViewModel> findPartIssueByDocumentId(String id);
}
