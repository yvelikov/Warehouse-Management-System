package org.softuni.wms.areas.documents.services.api;

import org.softuni.wms.areas.documents.models.service.DocumentServiceDto;
import org.softuni.wms.areas.documents.models.view.DocumentByPartnerViewDto;
import org.softuni.wms.areas.documents.models.view.DocumentDetailsViewDto;
import org.softuni.wms.areas.parts.models.binding.PartsOperationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;

@Service
@Transactional
public interface DocumentService {
    DocumentServiceDto generateDeliveryNote(Principal principal, PartsOperationDto partsDeliveryDto);

    DocumentServiceDto generateIssueNote(Principal principal, PartsOperationDto partsIssueDto);






    Page<DocumentByPartnerViewDto> findDocumentsByPartnerId(String partnerId, Pageable pageable);
}
