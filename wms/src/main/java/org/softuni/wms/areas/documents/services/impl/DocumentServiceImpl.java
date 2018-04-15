package org.softuni.wms.areas.documents.services.impl;

import org.softuni.wms.areas.documents.entities.docs.DeliveryNote;
import org.softuni.wms.areas.documents.entities.docs.Document;
import org.softuni.wms.areas.documents.entities.docs.IssueNote;
import org.softuni.wms.areas.documents.entities.enums.Operation;
import org.softuni.wms.areas.documents.models.binding.AddPartOperationDto;
import org.softuni.wms.areas.documents.models.service.DocumentServiceDto;
import org.softuni.wms.areas.documents.models.view.DocumentViewDto;
import org.softuni.wms.areas.documents.repositories.DeliveryNoteDao;
import org.softuni.wms.areas.documents.repositories.IssueNoteDao;
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
import java.time.LocalDate;

@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

    private static final String DOCUMENT_CODE_FORMAT = "%09d";

    private final DeliveryNoteDao deliveryNoteDao;
    private final IssueNoteDao issueNoteDao;
    private final UserDetailsService userDetails;
    private final OperationService operationService;
    private final PartnerService partnerService;
    private final DocumentNumberGenerator documentNumberGenerator;

    public DocumentServiceImpl(DeliveryNoteDao deliveryNoteDao,
                               IssueNoteDao issueNoteDao,
                               UserServiceImpl userDetails,
                               OperationService operationService,
                               PartnerService partnerService,
                               DocumentNumberGenerator documentNumberGenerator) {
        this.deliveryNoteDao = deliveryNoteDao;
        this.issueNoteDao = issueNoteDao;
        this.userDetails = userDetails;
        this.operationService = operationService;
        this.partnerService = partnerService;
        this.documentNumberGenerator = documentNumberGenerator;
    }

    private static DocumentViewDto apply(Document d) {
        DocumentViewDto documentViewDto = new DocumentViewDto();
        documentViewDto.setId(d.getId());
        documentViewDto.setDocumentCode(d.getDocumentCode());
        documentViewDto.setDate(d.getDate());
        documentViewDto.setPartner(d.getPartner().getName());
        documentViewDto.setUser(d.getUser().getUsername());
        return documentViewDto;
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
    public void generateDeliveryNote(Principal principal, PartsOperationDto partsOperationDto) {
        DeliveryNote deliveryNote = new DeliveryNote();
        User user = (User) this.userDetails.loadUserByUsername(principal.getName());
        PartnerServiceDto partnerServiceDto = this.partnerService.findById(partsOperationDto.getPartnerId());
        Partner partner = DTOConvertUtil.convert(partnerServiceDto, Partner.class);

        deliveryNote.setUser(user);
        deliveryNote.setDate(LocalDate.now());
        deliveryNote.setPartner(partner);
        deliveryNote.setDocumentCode(String.format(DOCUMENT_CODE_FORMAT, this.documentNumberGenerator.next()));
        this.deliveryNoteDao.save(deliveryNote);

        DocumentServiceDto documentServiceDto = DTOConvertUtil.convert(deliveryNote, DocumentServiceDto.class);

        this.createPartOperations(partsOperationDto, documentServiceDto, Operation.DELIVERY.name());
    }

    @Override
    public void generateIssueNote(Principal principal, PartsOperationDto partsOperationDto) {
        IssueNote issueNote = new IssueNote();
        User user = (User) this.userDetails.loadUserByUsername(principal.getName());
        PartnerServiceDto partnerServiceDto = this.partnerService.findById(partsOperationDto.getPartnerId());
        Partner partner = DTOConvertUtil.convert(partnerServiceDto, Partner.class);

        issueNote.setUser(user);
        issueNote.setDate(LocalDate.now());
        issueNote.setPartner(partner);
        issueNote.setDocumentCode(String.format(DOCUMENT_CODE_FORMAT, this.documentNumberGenerator.next()));
        this.issueNoteDao.save(issueNote);

        DocumentServiceDto documentServiceDto = DTOConvertUtil.convert(issueNote, DocumentServiceDto.class);

        this.createPartOperations(partsOperationDto, documentServiceDto, Operation.ISSUE.name());
    }

    @Override
    public Page<DocumentViewDto> findAllDeliveryNotesByPage(Pageable pageable) {
        Page<DeliveryNote> allDeliveryNotes = this.deliveryNoteDao.findAllDocuments(pageable);
        return allDeliveryNotes.map(DocumentServiceImpl::apply);
    }

    @Override
    public Page<DocumentViewDto> findAllDeliveryNotesByPageAndSpecification(String param, Pageable pageable) {
        return null;
    }

    @Override
    public Page<DocumentViewDto> findAllIssueNotesByPage(Pageable pageable) {
        Page<IssueNote> allIssueNotes = this.issueNoteDao.findAllDocuments(pageable);
        return allIssueNotes.map(DocumentServiceImpl::apply);
    }

    @Override
    public Page<DocumentViewDto> findAllIssueNotesByPageAndSpecification(String value, Pageable pageable) {
        return null;
    }
}
