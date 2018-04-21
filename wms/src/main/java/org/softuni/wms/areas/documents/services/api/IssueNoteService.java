package org.softuni.wms.areas.documents.services.api;

import org.softuni.wms.areas.documents.models.view.DocumentDetailsViewDto;
import org.softuni.wms.areas.documents.models.view.DocumentViewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IssueNoteService {

    Page<DocumentViewDto> findAllIssueNotesByPage(Pageable pageable);

    Page<DocumentViewDto> findAllIssueNotesByPageAndSpecification(String value, Pageable pageable);

    DocumentDetailsViewDto findIssueNoteById(String id);
}
