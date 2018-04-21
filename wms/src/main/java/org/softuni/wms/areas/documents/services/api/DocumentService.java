package org.softuni.wms.areas.documents.services.api;

import org.softuni.wms.areas.documents.models.view.DocumentDetailsViewDto;
import org.softuni.wms.areas.documents.models.view.DocumentViewDto;
import org.softuni.wms.areas.parts.models.binding.PartsOperationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;

@Service
@Transactional
public interface DocumentService {
    boolean generateDeliveryNote(Principal principal, PartsOperationDto partsDeliveryDto);

    boolean generateIssueNote(Principal principal, PartsOperationDto partsIssueDto);

    Page<DocumentViewDto> findAllDeliveryNotesByPage(Pageable pageable);

    Page<DocumentViewDto> findAllDeliveryNotesByPageAndSpecification(String value, Pageable pageable);

    Page<DocumentViewDto> findAllIssueNotesByPage(Pageable pageable);

    Page<DocumentViewDto> findAllIssueNotesByPageAndSpecification(String value, Pageable pageable);

    DocumentDetailsViewDto findDeliveryNoteById(String id);

    DocumentDetailsViewDto findIssueNoteById(String id);
}
