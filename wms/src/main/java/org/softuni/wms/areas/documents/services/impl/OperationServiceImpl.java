package org.softuni.wms.areas.documents.services.impl;

import org.softuni.wms.areas.documents.entities.docs.DeliveryNote;
import org.softuni.wms.areas.documents.entities.docs.Document;
import org.softuni.wms.areas.documents.entities.docs.IssueNote;
import org.softuni.wms.areas.documents.entities.operations.Operation;
import org.softuni.wms.areas.documents.entities.operations.PartDelivery;
import org.softuni.wms.areas.documents.entities.operations.PartIssue;
import org.softuni.wms.areas.documents.models.binding.AddPartOperationDto;
import org.softuni.wms.areas.documents.repositories.OperationDao;
import org.softuni.wms.areas.documents.services.api.OperationService;
import org.softuni.wms.areas.parts.entities.Part;
import org.softuni.wms.areas.parts.models.service.PartServiceDto;
import org.softuni.wms.areas.parts.services.PartService;
import org.softuni.wms.utils.DTOConvertUtil;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class OperationServiceImpl implements OperationService {

    private final OperationDao operationDao;
    private final PartService partService;

    public OperationServiceImpl(OperationDao operationDao, PartService partService) {
        this.operationDao = operationDao;
        this.partService = partService;
    }


    @Override
    public void save(AddPartOperationDto addPartDeliveryDto) {
        Operation operation = null;
        Document document = null;
        switch (addPartDeliveryDto.getType()) {
            case "DELIVERY":
                operation = new PartDelivery();
                document = DTOConvertUtil.convert(addPartDeliveryDto.getDocument(), DeliveryNote.class);
                break;
            case "ISSUE":
                operation = new PartIssue();
                document = DTOConvertUtil.convert(addPartDeliveryDto.getDocument(), IssueNote.class);
                break;
        }

        PartServiceDto partServiceDto = this.partService.findById(addPartDeliveryDto.getPartId());
        Part part = DTOConvertUtil.convert(partServiceDto, Part.class);
        operation.setDocument(document);
        operation.setPart(part);
        operation.setQuantity(addPartDeliveryDto.getQuantity());

        this.operationDao.saveAndFlush(operation);

    }
}
