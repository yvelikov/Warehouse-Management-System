package org.softuni.wms.areas.parts.services;

import org.softuni.wms.areas.parts.models.binding.AddPartDto;
import org.softuni.wms.areas.parts.models.binding.EditPartDto;
import org.softuni.wms.areas.parts.models.binding.PartsOperationDto;
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
    PartServiceDto addPart(AddPartDto addPartDto);

    List<PartViewDto> findAll();

    List<PartViewDto> findAllPartsOnStock();

    Page<PartViewDto> findAllByPage(Pageable pageable);

    Page<PartViewDto> findAllByPageAndSpecification(String value, String type, Pageable pageable);

    EditPartDto getOne(String id);

    PartServiceDto findById(String id);

    PartServiceDto editPart(EditPartDto editPartDto);

    List<PartViewDto> findPartsBySupplierId(String id);

    String getSupplierId(String id);

    boolean deliver(PartsOperationDto partsOperationDto);

    boolean issue(PartsOperationDto partsOperationDto);
}
