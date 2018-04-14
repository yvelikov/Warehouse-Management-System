package org.softuni.wms.areas.partners.services;

import org.softuni.wms.areas.partners.models.binding.AddPartnerDto;
import org.softuni.wms.areas.partners.models.binding.EditPartnerDto;
import org.softuni.wms.areas.partners.models.service.PartnerServiceDto;
import org.softuni.wms.areas.partners.models.service.SupplierServiceDto;
import org.softuni.wms.areas.partners.models.view.PartnerViewDto;
import org.softuni.wms.areas.partners.models.view.SupplierViewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public interface PartnerService {
    void addPartner(AddPartnerDto addPartnerDto);

    Page<PartnerViewDto> findAllByPage(Pageable pageable);

    Page<PartnerViewDto> findAllByPageAndSpecification(String param, String type, Pageable pageable);

    EditPartnerDto getOne(String id);

    PartnerServiceDto findById(String id);

    void editPartner(EditPartnerDto editPartnerDto);

    List<SupplierServiceDto> findAllSuppliers();

    SupplierServiceDto findSupplierByName(String name);

    List<String> getAllSuppliersNames();

    List<SupplierViewDto> getAllSuppliers();
}
