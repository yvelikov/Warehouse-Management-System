package org.softuni.wms.areas.parts.services;

import org.softuni.wms.areas.partners.entities.Partner;
import org.softuni.wms.areas.partners.models.service.SupplierServiceDto;
import org.softuni.wms.areas.partners.services.PartnerService;
import org.softuni.wms.areas.parts.criteria.PartSpecification;
import org.softuni.wms.areas.parts.entities.Part;
import org.softuni.wms.areas.parts.models.binding.*;
import org.softuni.wms.areas.parts.models.service.PartServiceDto;
import org.softuni.wms.areas.parts.models.view.PartViewDto;
import org.softuni.wms.areas.parts.repositories.PartDao;
import org.softuni.wms.utils.DTOConvertUtil;
import org.softuni.wms.common.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PartServiceImpl implements PartService {

    private static final long DEFAULT_QUANTITY = 0L;

    private final PartDao partDao;
    private final PartnerService partnerService;

    @Autowired
    public PartServiceImpl(PartDao partDao, PartnerService partnerService) {
        this.partDao = partDao;
        this.partnerService = partnerService;
    }

    @Override
    public PartServiceDto addPart(AddPartDto addPartDto) {
        try{
            Part part = new Part();
            part.setArticleCode(addPartDto.getArticleCode());
            part.setName(addPartDto.getName());
            part.setUnitOfMeasure(addPartDto.getUnitOfMeasure());
            part.setDeliveryPrice(addPartDto.getDeliveryPrice());
            part.setListPrice(addPartDto.getListPrice());
            part.setMarkUp(addPartDto.getMarkUp() / 100);
            SupplierServiceDto supplier = this.partnerService.findSupplierByName(addPartDto.getSupplier());
            Partner partner = DTOConvertUtil.convert(supplier, Partner.class);
            part.setQuantity(DEFAULT_QUANTITY);
            part.setSupplier(partner);

            Part savedPart = this.partDao.saveAndFlush(part);
            return DTOConvertUtil.convert(savedPart, PartServiceDto.class);
        } catch (RuntimeException e){
            return null;
        }
    }

    @Override
    public List<PartViewDto> findAllPartsOnStock() {
        List<Part> allPartsOnStock = this.partDao.findAllPartsOnStock();
        return DTOConvertUtil.convert(allPartsOnStock, PartViewDto.class);
    }

    @Override
    public List<PartViewDto> findAll() {
        List<Part> allParts = this.partDao.findAll();
        return DTOConvertUtil.convert(allParts, PartViewDto.class);
    }

    @Override
    public Page<PartViewDto> findAllByPage(Pageable pageable) {
        Page<Part> allParts = this.partDao.findAll(pageable);
        return DTOConvertUtil.convertToPage(allParts, PartViewDto.class);
    }

    @Override
    public Page<PartViewDto> findAllByPageAndSpecification(String value, String type, Pageable pageable) {
        PartSpecification specName = new PartSpecification(new SearchCriteria("name", "like", value));
        PartSpecification specArticleCode = new PartSpecification(new SearchCriteria("articleCode", "like", value));
        PartSpecification specType = null;
        Page<Part> parts = null;

        switch (type) {
            case "all":
                parts = this.partDao.findAll(Specification.where(specName).or(specArticleCode), pageable);
                break;
            case "onStock":
                specType = new PartSpecification(new SearchCriteria("quantity", ">", 0));
                parts = this.partDao.findAll(Specification.where(specName).or(specArticleCode).and(specType), pageable);
                break;
            case "outOfStock":
                specType = new PartSpecification(new SearchCriteria("quantity", "=", 0));
                parts = this.partDao.findAll(Specification.where(specName).or(specArticleCode).and(specType), pageable);
                break;
        }

        return DTOConvertUtil.convertToPage(parts, PartViewDto.class);
    }

    @Override
    public EditPartDto getOne(String id) {
        Part part = this.partDao.getOne(id);
        SupplierServiceDto supplier = this.partnerService.findSupplierByName(part.getSupplier().getName());
        EditPartDto editPartDto = DTOConvertUtil.convert(part, EditPartDto.class);
        editPartDto.setSupplier(supplier.getName());
        editPartDto.setMarkUp(editPartDto.getMarkUp() * 100);
        return editPartDto;
    }

    @Override
    public PartServiceDto findById(String id) {
        Part part = this.partDao.getOne(id);
        return DTOConvertUtil.convert(part, PartServiceDto.class);
    }

    @Override
    public PartServiceDto editPart(EditPartDto editPartDto) {
        try{
            Part part = this.partDao.getOne(editPartDto.getId());
            part.setArticleCode(editPartDto.getArticleCode());
            part.setName(editPartDto.getName());
            part.setDeliveryPrice(editPartDto.getDeliveryPrice());
            part.setListPrice(editPartDto.getListPrice());
            part.setMarkUp(editPartDto.getMarkUp() / 100);
            part.setUnitOfMeasure(editPartDto.getUnitOfMeasure());
            SupplierServiceDto supplier = this.partnerService.findSupplierByName(editPartDto.getSupplier());
            Partner partner = DTOConvertUtil.convert(supplier, Partner.class);
            part.setSupplier(partner);

            Part savedPart = this.partDao.saveAndFlush(part);
            return DTOConvertUtil.convert(savedPart, PartServiceDto.class);
        }catch (RuntimeException e){
            return null;
        }
    }

    @Override
    public List<PartViewDto> findPartsBySupplierId(String id) {

        List<Part> allBySupplierId = this.partDao.findAllBySupplierId(id);
        String debug = "";
        return DTOConvertUtil.convert(allBySupplierId, PartViewDto.class);
    }

    @Override
    public String getSupplierId(String id) {
        Part part = this.partDao.getOne(id);
        return part.getSupplier().getId();
    }

    @Override
    public List<PartServiceDto> deliver(PartsOperationDto partsOperationDto) {
        try{
            List<PartServiceDto> partServiceDtoList = new ArrayList<>();
            for (OperationPartDto operationPartDto : partsOperationDto.getParts()) {
                Part part = this.partDao.getOne(operationPartDto.getId());
                Long quantityOnStock = part.getQuantity();
                Long deliveredQuantity = operationPartDto.getQuantity();
                part.setQuantity(quantityOnStock + deliveredQuantity);
                Part deliveredPart = this.partDao.saveAndFlush(part);
                partServiceDtoList.add(DTOConvertUtil.convert(deliveredPart, PartServiceDto.class));
            }
            return partServiceDtoList;
        } catch (RuntimeException e){
            return null;
        }
    }

    @Override
    public List<PartServiceDto> issue(PartsOperationDto partsIssueDto) {
        try{
            List<PartServiceDto> partServiceDtoList = new ArrayList<>();
            for (OperationPartDto operationPartDto : partsIssueDto.getParts()) {
                Part part = this.partDao.getOne(operationPartDto.getId());
                Long quantityOnStock = part.getQuantity();
                Long issuedQuantity = operationPartDto.getQuantity();

                if(quantityOnStock - issuedQuantity < 0){
                    throw new IllegalStateException();
                }

                part.setQuantity(quantityOnStock - issuedQuantity);
                Part issuedPart = this.partDao.saveAndFlush(part);
                partServiceDtoList.add(DTOConvertUtil.convert(issuedPart, PartServiceDto.class));
            }
            return partServiceDtoList;
        }catch (RuntimeException e){
            return null;
        }
    }
}
