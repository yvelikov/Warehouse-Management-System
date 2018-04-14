package org.softuni.wms.areas.documents.services.api;

import org.softuni.wms.areas.parts.models.binding.PartsDeliveryDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;

@Service
@Transactional
public interface DocumentService {
    void generateDeliveryNote(Principal principal, PartsDeliveryDto partsDeliveryDto);
}
