package org.softuni.wms.areas.partners.services;

import org.softuni.wms.areas.partners.criteria.PartnerSpecification;
import org.softuni.wms.areas.partners.entities.Partner;
import org.softuni.wms.areas.partners.models.binding.AddPartnerDto;
import org.softuni.wms.areas.partners.models.binding.EditPartnerDto;
import org.softuni.wms.areas.partners.models.service.PartnerServiceDto;
import org.softuni.wms.areas.partners.models.service.SupplierServiceDto;
import org.softuni.wms.areas.partners.models.view.CustomerViewDto;
import org.softuni.wms.areas.partners.models.view.PartnerViewDto;
import org.softuni.wms.areas.partners.models.view.SupplierViewDto;
import org.softuni.wms.areas.partners.repositories.PartnerDao;
import org.softuni.wms.common.SearchCriteria;
import org.softuni.wms.utils.DTOConvertUtil;
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
public class PartnerServiceImpl implements PartnerService {

    private final PartnerDao partnerDao;

    @Autowired
    public PartnerServiceImpl(PartnerDao partnerDao) {
        this.partnerDao = partnerDao;
    }

    @Override
    public PartnerServiceDto addPartner(AddPartnerDto addPartnerDto) {
        try{
            Partner partner = DTOConvertUtil.convert(addPartnerDto, Partner.class);
            Partner savedPartner = this.partnerDao.saveAndFlush(partner);
            return DTOConvertUtil.convert(savedPartner, PartnerServiceDto.class);
        }catch (RuntimeException e){
            return null;
        }
    }

    @Override
    public Page<PartnerViewDto> findAllByPage(Pageable pageable) {
        Page<Partner> partners = this.partnerDao.findAll(pageable);
        return DTOConvertUtil.convertToPage(partners, PartnerViewDto.class);
    }

    @Override
    public Page<PartnerViewDto> findAllByPageAndSpecification(String param, String type, Pageable pageable) {
        PartnerSpecification specName = new PartnerSpecification(new SearchCriteria("name", "like", param));
        PartnerSpecification specVat = new PartnerSpecification(new SearchCriteria("vatNumber", "like", param));
        PartnerSpecification specAddress = new PartnerSpecification(new SearchCriteria("address", "like", param));
        PartnerSpecification specType = null;
        Page<Partner> partners = null;

        switch (type) {
            case "all":
                partners = this.partnerDao.findAll(Specification.where(specName).or(specVat).or(specAddress), pageable);
                break;
            case "customer":
                specType = new PartnerSpecification(new SearchCriteria("customer", "=", Boolean.TRUE));
                partners = this.partnerDao.findAll(Specification.where(specName).or(specVat).or(specAddress).and(specType), pageable);
                break;
            case "supplier":
                specType = new PartnerSpecification(new SearchCriteria("supplier", "=", Boolean.TRUE));
                partners = this.partnerDao.findAll(Specification.where(specName).or(specVat).or(specAddress).and(specType), pageable);
                break;
        }

        return DTOConvertUtil.convertToPage(partners, PartnerViewDto.class);
    }

    @Override
    public EditPartnerDto getOne(String id) {
        Partner partner = this.partnerDao.getOne(id);
        return DTOConvertUtil.convert(partner, EditPartnerDto.class);
    }

    @Override
    public PartnerServiceDto findById(String id) {
        Partner partner = this.partnerDao.getOne(id);
        return DTOConvertUtil.convert(partner, PartnerServiceDto.class);
    }

    @Override
    public PartnerServiceDto editPartner(EditPartnerDto editPartnerDto) {
        try{
            Partner partner = this.partnerDao.getOne(editPartnerDto.getId());
            partner.setName(editPartnerDto.getName());
            partner.setVatNumber(editPartnerDto.getVatNumber());
            partner.setPhoneNumber(editPartnerDto.getPhoneNumber());
            partner.setAddress(editPartnerDto.getAddress());
            partner.setCustomer(editPartnerDto.getCustomer());
            partner.setSupplier(editPartnerDto.getSupplier());

            Partner savedPartner = this.partnerDao.saveAndFlush(partner);
            return DTOConvertUtil.convert(savedPartner, PartnerServiceDto.class);
        }catch (RuntimeException e){
            return null;
        }
    }

    @Override
    public List<SupplierServiceDto> findAllSuppliers() {
        List<Partner> suppliers = this.partnerDao.findPartnersBySupplierIsTrueOrderByName();
        return DTOConvertUtil.convert(suppliers, SupplierServiceDto.class);
    }

    @Override
    public SupplierServiceDto findSupplierByName(String name) {
        Partner partner = this.partnerDao.findPartnerByName(name);
        return DTOConvertUtil.convert(partner, SupplierServiceDto.class);
    }

    @Override
    public List<String> getAllSuppliersNames() {
        List<SupplierServiceDto> suppliers = this.findAllSuppliers();
        List<String> supplierNames = new ArrayList<>();
        for (SupplierServiceDto supplier : suppliers) {
            supplierNames.add(supplier.getName());
        }
        return supplierNames;
    }

    @Override
    public List<SupplierViewDto> getAllSuppliers() {
        List<Partner> suppliers = this.partnerDao.findAllBySupplierIsTrue();
        return DTOConvertUtil.convert(suppliers, SupplierViewDto.class);
    }

    @Override
    public List<CustomerViewDto> getAllCustomers() {
        List<Partner> customers = this.partnerDao.findAllByCustomerIsTrue();
        return DTOConvertUtil.convert(customers, CustomerViewDto.class);
    }
}
