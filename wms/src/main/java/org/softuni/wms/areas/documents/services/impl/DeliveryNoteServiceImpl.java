package org.softuni.wms.areas.documents.services.impl;

import org.softuni.wms.areas.documents.entities.docs.DeliveryNote;
import org.softuni.wms.areas.documents.models.view.DocumentDetailsViewDto;
import org.softuni.wms.areas.documents.models.view.DocumentViewDto;
import org.softuni.wms.areas.documents.repositories.DeliveryNoteDao;
import org.softuni.wms.areas.documents.services.api.DeliveryNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DeliveryNoteServiceImpl implements DeliveryNoteService {

    private final DeliveryNoteDao deliveryNoteDao;

    @Autowired
    public DeliveryNoteServiceImpl(DeliveryNoteDao deliveryNoteDao) {
        this.deliveryNoteDao = deliveryNoteDao;
    }

    @Override
    public Page<DocumentViewDto> findAllDeliveryNotesByPage(Pageable pageable) {
        Page<DeliveryNote> allDeliveryNotes = this.deliveryNoteDao.findAllDocuments(pageable);
        return allDeliveryNotes.map(DocumentUtils::mapDocumentToDto);
    }

    @Override
    public Page<DocumentViewDto> findAllDeliveryNotesByPageAndSpecification(String param, Pageable pageable) {
        Page<Object> all = this.deliveryNoteDao.findAllContaining(param, pageable);
        return all.map(DocumentUtils::mapObjectToDto);
    }

    @Override
    public DocumentDetailsViewDto findDeliveryNoteById(String id) {
        Object[] document = (Object[]) this.deliveryNoteDao.findDetailsById(id);
        return DocumentUtils.getDocumentDetailsViewDto(document);
    }
}
