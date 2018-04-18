package org.softuni.wms.areas.documents.services.impl;

import org.softuni.wms.areas.documents.entities.docs.DeliveryNote;
import org.softuni.wms.areas.documents.entities.docs.Document;
import org.softuni.wms.areas.documents.entities.docs.IssueNote;
import org.softuni.wms.areas.documents.entities.operations.Operation;
import org.softuni.wms.areas.documents.entities.operations.PartDelivery;
import org.softuni.wms.areas.documents.entities.operations.PartIssue;
import org.softuni.wms.areas.documents.models.binding.AddPartOperationDto;
import org.softuni.wms.areas.documents.models.view.OperationViewModel;
import org.softuni.wms.areas.documents.repositories.OperationDao;
import org.softuni.wms.areas.documents.services.api.OperationService;
import org.softuni.wms.areas.parts.entities.Part;
import org.softuni.wms.areas.parts.models.service.PartServiceDto;
import org.softuni.wms.areas.parts.services.PartService;
import org.softuni.wms.utils.DTOConvertUtil;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class OperationServiceImpl implements OperationService {

    private final OperationDao operationDao;
    private final PartService partService;

    public OperationServiceImpl(OperationDao operationDao, PartService partService) {
        this.operationDao = operationDao;
        this.partService = partService;
    }

    private List<OperationViewModel> getOperationViewModels(List<Object> operationsByDocumentId) {
        List<OperationViewModel> operationViewModelList = new ArrayList<>();
        for (Object operation : operationsByDocumentId) {
            Object[] operationData = (Object[]) operation;
            OperationViewModel operationViewModel = new OperationViewModel();
            operationViewModel.setArticleCode((String) operationData[0]);
            operationViewModel.setPartName((String) operationData[1]);
            operationViewModel.setQuantity((BigInteger) operationData[2]);
            operationViewModel.setUnitOfMeasure((String) operationData[3]);
            operationViewModelList.add(operationViewModel);
        }
        return operationViewModelList;
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

    @Override
    @SuppressWarnings("unchecked")
    public List<OperationViewModel> findPartDeliveryByDocumentId(String id) {
        List<Object> operationsByDocumentId = this.operationDao.findOperationByDocumentId(id);
        return this.getOperationViewModels(operationsByDocumentId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<OperationViewModel> findPartIssueByDocumentId(String id) {
        List<Object> operationsByDocumentId = this.operationDao.findOperationByDocumentId(id);
        return this.getOperationViewModels(operationsByDocumentId);
    }
}
