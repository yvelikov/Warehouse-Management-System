package org.softuni.wms.areas.documents.services.impl;

import org.softuni.wms.areas.documents.entities.docs.DeliveryNote;
import org.softuni.wms.areas.documents.entities.operations.PartDelivery;
import org.softuni.wms.areas.documents.models.binding.AddPartDeliveryDto;
import org.softuni.wms.areas.documents.repositories.PartDeliveryDao;
import org.softuni.wms.areas.documents.services.api.PartDeliveryService;
import org.softuni.wms.areas.parts.entities.Part;
import org.softuni.wms.areas.parts.models.service.PartServiceDto;
import org.softuni.wms.areas.parts.services.PartService;
import org.softuni.wms.utils.DTOConvertUtil;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class PartDeliveryServiceImpl implements PartDeliveryService {

    private final PartDeliveryDao partDeliveryDao;
    private final PartService partService;

    public PartDeliveryServiceImpl(PartDeliveryDao partDeliveryDao, PartService partService) {
        this.partDeliveryDao = partDeliveryDao;
        this.partService = partService;
    }


    @Override
    public void save(AddPartDeliveryDto addPartDeliveryDto) {
        PartDelivery partDelivery = new PartDelivery();
        DeliveryNote document = DTOConvertUtil.convert(addPartDeliveryDto.getDocument(), DeliveryNote.class);
        PartServiceDto partServiceDto = this.partService.findById(addPartDeliveryDto.getPartId());
        Part part = DTOConvertUtil.convert(partServiceDto, Part.class);
        partDelivery.setDocument(document);
        partDelivery.setPart(part);
        partDelivery.setQuantity(addPartDeliveryDto.getQuantity());

        this.partDeliveryDao.saveAndFlush(partDelivery);

    }
}
