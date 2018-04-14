package org.softuni.wms.areas.parts.services;

import org.softuni.wms.areas.parts.models.binding.AddPartDto;
import org.softuni.wms.areas.parts.models.binding.EditPartDto;
import org.softuni.wms.areas.parts.models.binding.PartsDeliveryDto;
import org.softuni.wms.areas.parts.models.service.PartServiceDto;
import org.softuni.wms.areas.parts.models.view.PartViewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public interface PartService {
    void addPart(AddPartDto addPartDto);

    Page<PartViewDto> findAllByPage(Pageable pageable);

    Page<PartViewDto> findAllByPageAndSpecification(String value, String type, Pageable pageable);

    EditPartDto getOne(String id);

    PartServiceDto findById(String id);

    void editPart(EditPartDto editPartDto);

    List<PartViewDto> findPartsBySupplierId(String id);

    String getSupplierId(String id);

    void deliver(PartsDeliveryDto partsDeliveryDto);
}
