package org.softuni.wms.areas.documents.services.api;

import org.softuni.wms.areas.documents.models.view.DocumentDetailsViewDto;
import org.softuni.wms.areas.documents.models.view.DocumentViewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryNoteService {

    Page<DocumentViewDto> findAllDeliveryNotesByPage(Pageable pageable);

    Page<DocumentViewDto> findAllDeliveryNotesByPageAndSpecification(String value, Pageable pageable);

    DocumentDetailsViewDto findDeliveryNoteById(String id);

}
