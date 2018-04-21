package org.softuni.wms.areas.documents.services.impl;

import org.softuni.wms.areas.documents.entities.docs.Document;
import org.softuni.wms.areas.documents.models.view.DocumentDetailsViewDto;
import org.softuni.wms.areas.documents.models.view.DocumentViewDto;

import java.sql.Date;
import java.time.LocalDate;

public class DocumentUtils {

    static DocumentViewDto mapDocumentToDto(Document d) {
        DocumentViewDto documentViewDto = new DocumentViewDto();
        documentViewDto.setId(d.getId());
        documentViewDto.setDocumentCode(d.getDocumentCode());
        documentViewDto.setDate(d.getDate());
        documentViewDto.setPartner(d.getPartner().getName());
        documentViewDto.setUser(d.getUser().getUsername());
        return documentViewDto;
    }

    static DocumentViewDto mapObjectToDto(Object o) {
        Object[] objects = (Object[]) o;
        DocumentViewDto documentViewDto = new DocumentViewDto();
        documentViewDto.setId((String) objects[0]);
        documentViewDto.setDocumentCode((String) objects[1]);
        documentViewDto.setDate((LocalDate) objects[2]);
        documentViewDto.setPartner((String) objects[3]);
        documentViewDto.setUser((String) objects[4]);
        return documentViewDto;
    }

    static DocumentDetailsViewDto getDocumentDetailsViewDto(Object[] document) {
        DocumentDetailsViewDto documentDetails = new DocumentDetailsViewDto();
        Date date = (Date) document[2];
        LocalDate localDate = date.toLocalDate();
        documentDetails.setId((String) document[0]);
        documentDetails.setType((String) document[1]);
        documentDetails.setDate(localDate);
        documentDetails.setDocumentCode((String) document[3]);
        documentDetails.setPartner((String) document[4]);
        documentDetails.setPartnerVatNumber((String) document[5]);
        documentDetails.setPartnerAddress((String) document[6]);
        documentDetails.setUser((String) document[7]);
        return documentDetails;
    }
}
