package org.softuni.wms.areas.partners.controllers;

import org.softuni.wms.areas.partners.services.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8000")
@RestController
@RequestMapping("/partners/customers")
public class CustomersController {

    private final PartnerService partnerService;

    @Autowired
    public CustomersController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

}
