package org.softuni.wms.areas.documents.services.impl;

import org.softuni.wms.areas.documents.entities.docs.DeliveryNote;
import org.softuni.wms.areas.documents.models.binding.AddPartDeliveryDto;
import org.softuni.wms.areas.documents.models.service.DocumentServiceDto;
import org.softuni.wms.areas.documents.repositories.DocumentDao;
import org.softuni.wms.areas.documents.services.api.DocumentService;
import org.softuni.wms.areas.documents.services.api.PartDeliveryService;
import org.softuni.wms.areas.partners.entities.Partner;
import org.softuni.wms.areas.partners.models.service.PartnerServiceDto;
import org.softuni.wms.areas.partners.services.PartnerService;
import org.softuni.wms.areas.parts.models.binding.DeliveryPartDto;
import org.softuni.wms.areas.parts.models.binding.PartsDeliveryDto;
import org.softuni.wms.areas.users.entities.User;
import org.softuni.wms.areas.users.services.impl.UserServiceImpl;
import org.softuni.wms.utils.DTOConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Date;

@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

    private final DocumentDao documentDao;
    private final UserDetailsService userDetails;
    private final PartDeliveryService partDeliveryService;
    private final PartnerService partnerService;
    private final DocumentNumberGenerator documentNumberGenerator;

    @Autowired
    public DocumentServiceImpl(DocumentDao documentDao,
                               UserServiceImpl userDetails, PartDeliveryService partDeliveryService,
                               PartnerService partnerService, DocumentNumberGenerator documentNumberGenerator) {
        this.documentDao = documentDao;
        this.userDetails = userDetails;
        this.partDeliveryService = partDeliveryService;
        this.partnerService = partnerService;
        this.documentNumberGenerator = documentNumberGenerator;
    }

    @Override
    public void generateDeliveryNote(Principal principal, PartsDeliveryDto partsDeliveryDto) {
        DeliveryNote deliveryNote = new DeliveryNote();
        User user = (User) this.userDetails.loadUserByUsername(principal.getName());
        PartnerServiceDto partnerServiceDto = this.partnerService.findById(partsDeliveryDto.getSupplierId());
        Partner partner = DTOConvertUtil.convert(partnerServiceDto, Partner.class);

        deliveryNote.setUser(user);
        deliveryNote.setDate(new Date());
        deliveryNote.setPartner(partner);
        deliveryNote.setDocumentCode(String.format("%06d", this.documentNumberGenerator.next()));
        this.documentDao.save(deliveryNote);

        DocumentServiceDto documentServiceDto = DTOConvertUtil.convert(deliveryNote,DocumentServiceDto.class);

        for (DeliveryPartDto deliveryPartDto : partsDeliveryDto.getParts()) {
            AddPartDeliveryDto addPartDeliveryDto = new AddPartDeliveryDto();
            addPartDeliveryDto.setDocument(documentServiceDto);
            addPartDeliveryDto.setPartId(deliveryPartDto.getId());
            addPartDeliveryDto.setQuantity(deliveryPartDto.getQuantity());
            this.partDeliveryService.save(addPartDeliveryDto);
        }
    }
}
