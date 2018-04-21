package org.softuni.wms.areas.documents.services.impl;

import org.softuni.wms.areas.documents.entities.docs.DeliveryNote;
import org.softuni.wms.areas.documents.entities.docs.IssueNote;
import org.softuni.wms.areas.documents.entities.enums.Operation;
import org.softuni.wms.areas.documents.models.binding.AddPartOperationDto;
import org.softuni.wms.areas.documents.models.service.DocumentServiceDto;
import org.softuni.wms.areas.documents.models.view.DocumentByPartnerViewDto;
import org.softuni.wms.areas.documents.repositories.BaseDocumentDao;
import org.softuni.wms.areas.documents.services.api.DocumentService;
import org.softuni.wms.areas.documents.services.api.OperationService;
import org.softuni.wms.areas.partners.entities.Partner;
import org.softuni.wms.areas.partners.models.service.PartnerServiceDto;
import org.softuni.wms.areas.partners.services.PartnerService;
import org.softuni.wms.areas.parts.models.binding.OperationPartDto;
import org.softuni.wms.areas.parts.models.binding.PartsOperationDto;
import org.softuni.wms.areas.users.entities.User;
import org.softuni.wms.areas.users.services.impl.UserServiceImpl;
import org.softuni.wms.utils.DTOConvertUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;

@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

    private static final String DOCUMENT_CODE_FORMAT = "%09d";


    private final BaseDocumentDao baseDocumentDao;
    private final UserDetailsService userDetails;
    private final OperationService operationService;
    private final PartnerService partnerService;
    private final DocumentNumberGenerator documentNumberGenerator;

    public DocumentServiceImpl(BaseDocumentDao baseDocumentDao, UserServiceImpl userDetails,
                               OperationService operationService,
                               PartnerService partnerService,
                               DocumentNumberGenerator documentNumberGenerator) {
        this.baseDocumentDao = baseDocumentDao;
        this.userDetails = userDetails;
        this.operationService = operationService;
        this.partnerService = partnerService;
        this.documentNumberGenerator = documentNumberGenerator;
    }

    private void createPartOperations(PartsOperationDto partsOperationDto, DocumentServiceDto documentServiceDto, String type) {
        for (OperationPartDto operationPartDto : partsOperationDto.getParts()) {
            AddPartOperationDto addPartOperationDto = new AddPartOperationDto();
            addPartOperationDto.setDocument(documentServiceDto);
            addPartOperationDto.setPartId(operationPartDto.getId());
            addPartOperationDto.setQuantity(operationPartDto.getQuantity());
            addPartOperationDto.setType(type);
            this.operationService.save(addPartOperationDto);
        }
    }

    @Override
    public DocumentServiceDto generateDeliveryNote(Principal principal, PartsOperationDto partsOperationDto) {
        try {
            DeliveryNote deliveryNote = new DeliveryNote();
            User user = (User) this.userDetails.loadUserByUsername(principal.getName());
            PartnerServiceDto partnerServiceDto = this.partnerService.findById(partsOperationDto.getPartnerId());
            Partner partner = DTOConvertUtil.convert(partnerServiceDto, Partner.class);

            deliveryNote.setUser(user);
            deliveryNote.setDate(LocalDate.now());
            deliveryNote.setPartner(partner);
            deliveryNote.setDocumentCode(String.format(DOCUMENT_CODE_FORMAT, this.documentNumberGenerator.next()));
            DeliveryNote savedDeliveryNote = (DeliveryNote) this.baseDocumentDao.save(deliveryNote);

            DocumentServiceDto documentServiceDto = DTOConvertUtil.convert(savedDeliveryNote, DocumentServiceDto.class);

            this.createPartOperations(partsOperationDto, documentServiceDto, Operation.DELIVERY.name());
            return documentServiceDto;
        } catch (RuntimeException e) {
            return null;
        }
    }

    @Override
    public DocumentServiceDto generateIssueNote(Principal principal, PartsOperationDto partsOperationDto) {
        try {
            IssueNote issueNote = new IssueNote();
            User user = (User) this.userDetails.loadUserByUsername(principal.getName());
            PartnerServiceDto partnerServiceDto = this.partnerService.findById(partsOperationDto.getPartnerId());
            Partner partner = DTOConvertUtil.convert(partnerServiceDto, Partner.class);

            issueNote.setUser(user);
            issueNote.setDate(LocalDate.now());
            issueNote.setPartner(partner);
            issueNote.setDocumentCode(String.format(DOCUMENT_CODE_FORMAT, this.documentNumberGenerator.next()));
            IssueNote savedIssueNote = (IssueNote) this.baseDocumentDao.save(issueNote);

            DocumentServiceDto documentServiceDto = DTOConvertUtil.convert(savedIssueNote, DocumentServiceDto.class);

            this.createPartOperations(partsOperationDto, documentServiceDto, Operation.ISSUE.name());
            return documentServiceDto;
        } catch (RuntimeException e) {
            return null;
        }
    }

    @Override
    public Page<DocumentByPartnerViewDto> findDocumentsByPartnerId(String partnerId, Pageable pageable) {
        Page<Object> documentsByPartnerId = this.baseDocumentDao.findDocumentsByPartnerId(partnerId, pageable);
        return documentsByPartnerId.map(d->{
            Object[] documentData = (Object[]) d;
            Date date = (Date) documentData[2];
            LocalDate localDate = date.toLocalDate();
            DocumentByPartnerViewDto document = new DocumentByPartnerViewDto();
            document.setId((String) documentData[0]);
            document.setDocumentCode((String) documentData[1]);
            document.setDate(localDate);
            document.setType((String) documentData[3]);
            return document;
        });
    }
}
