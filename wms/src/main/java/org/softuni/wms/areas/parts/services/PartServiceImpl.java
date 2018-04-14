package org.softuni.wms.areas.parts.services;

import org.softuni.wms.areas.partners.entities.Partner;
import org.softuni.wms.areas.partners.models.service.SupplierServiceDto;
import org.softuni.wms.areas.partners.services.PartnerService;
import org.softuni.wms.areas.parts.criteria.PartSearchCriteria;
import org.softuni.wms.areas.parts.criteria.PartSpecification;
import org.softuni.wms.areas.parts.entities.Part;
import org.softuni.wms.areas.parts.models.binding.AddPartDto;
import org.softuni.wms.areas.parts.models.binding.DeliveryPartDto;
import org.softuni.wms.areas.parts.models.binding.EditPartDto;
import org.softuni.wms.areas.parts.models.binding.PartsDeliveryDto;
import org.softuni.wms.areas.parts.models.service.PartServiceDto;
import org.softuni.wms.areas.parts.models.view.PartViewDto;
import org.softuni.wms.areas.parts.repositories.PartDao;
import org.softuni.wms.utils.DTOConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PartServiceImpl implements PartService {

    private final PartDao partDao;
    private final PartnerService partnerService;

    @Autowired
    public PartServiceImpl(PartDao partDao, PartnerService partnerService) {
        this.partDao = partDao;
        this.partnerService = partnerService;
    }

    @Override
    public void addPart(AddPartDto addPartDto) {
        Part part = new Part();
        part.setArticleCode(addPartDto.getArticleCode());
        part.setName(addPartDto.getName());
        part.setUnitOfMeasure(addPartDto.getUnitOfMeasure());
        part.setDeliveryPrice(addPartDto.getDeliveryPrice());
        part.setListPrice(addPartDto.getListPrice());
        part.setMarkUp(addPartDto.getMarkUp()/100);
        SupplierServiceDto supplier = this.partnerService.findSupplierByName(addPartDto.getSupplier());
        Partner partner = DTOConvertUtil.convert(supplier, Partner.class);
        part.setQuantity(0L);
        part.setSupplier(partner);

        this.partDao.saveAndFlush(part);
    }

    @Override
    public Page<PartViewDto> findAllByPage(Pageable pageable) {
        Page<Part> allParts = this.partDao.findAll(pageable);
        return DTOConvertUtil.convertToPage(allParts, PartViewDto.class);
    }

    @Override
    public Page<PartViewDto> findAllByPageAndSpecification(String value, String type, Pageable pageable) {
        PartSpecification specName = new PartSpecification(new PartSearchCriteria("name", "like", value));
        PartSpecification specArticleCode = new PartSpecification(new PartSearchCriteria("articleCode", "like", value));
        PartSpecification specType = null;
        Page<Part> parts = null;

        switch (type) {
            case "all":
                parts = this.partDao.findAll(Specification.where(specName).or(specArticleCode), pageable);
                break;
            case "onStock":
                specType = new PartSpecification(new PartSearchCriteria("quantity", ">", 0));
                parts = this.partDao.findAll(Specification.where(specName).or(specArticleCode).and(specType), pageable);
                break;
            case "outOfStock":
                specType = new PartSpecification(new PartSearchCriteria("quantity", "=", 0));
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
        editPartDto.setMarkUp(editPartDto.getMarkUp()*100);
        return editPartDto;
    }

    @Override
    public PartServiceDto findById(String id) {
        Part part = this.partDao.getOne(id);
        return DTOConvertUtil.convert(part, PartServiceDto.class);
    }

    @Override
    public void editPart(EditPartDto editPartDto) {
        Part part = this.partDao.getOne(editPartDto.getId());
        part.setArticleCode(editPartDto.getArticleCode());
        part.setName(editPartDto.getName());
        part.setDeliveryPrice(editPartDto.getDeliveryPrice());
        part.setListPrice(editPartDto.getListPrice());
        part.setMarkUp(editPartDto.getMarkUp()/100);
        part.setUnitOfMeasure(editPartDto.getUnitOfMeasure());
        SupplierServiceDto supplier = this.partnerService.findSupplierByName(editPartDto.getSupplier());
        Partner partner = DTOConvertUtil.convert(supplier, Partner.class);
        part.setSupplier(partner);

        this.partDao.saveAndFlush(part);
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
    public void deliver(PartsDeliveryDto partsDeliveryDto) {
        for (DeliveryPartDto deliveryPartDto : partsDeliveryDto.getParts()) {
            Part part = this.partDao.getOne(deliveryPartDto.getId());
            Long quantityOnStock = part.getQuantity();
            Long deliveredQuantity = deliveryPartDto.getQuantity();
            part.setQuantity(quantityOnStock + deliveredQuantity);
            this.partDao.saveAndFlush(part);
        }
    }
}
