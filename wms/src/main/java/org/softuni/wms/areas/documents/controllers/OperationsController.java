package org.softuni.wms.areas.documents.controllers;

import org.softuni.wms.areas.documents.models.view.OperationViewModel;
import org.softuni.wms.areas.documents.services.api.OperationService;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN','EMPLOYEE')")
@RequestMapping("/operation")
public class OperationsController {

    private final OperationService operationService;

    public OperationsController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping("/part_delivery")
    public List<OperationViewModel> getPartDeliveryByDocumentId(@Param("docId") String docId){
        return this.operationService.findPartDeliveryByDocumentId(docId);
    }

    @GetMapping("/part_issue")
    public List<OperationViewModel> getPartIssueByDocumentId(@Param("docId") String docId){
        return this.operationService.findPartIssueByDocumentId(docId);
    }
}

