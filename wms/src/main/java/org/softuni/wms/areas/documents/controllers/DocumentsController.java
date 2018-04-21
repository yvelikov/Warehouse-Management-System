package org.softuni.wms.areas.documents.controllers;

import org.softuni.wms.areas.documents.models.view.DocumentDetailsViewDto;
import org.softuni.wms.areas.documents.models.view.DocumentViewDto;
import org.softuni.wms.areas.documents.services.api.DocumentService;
import org.softuni.wms.common.SearchFilter;
import org.softuni.wms.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Controller
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN','EMPLOYEE')")
public class DocumentsController extends BaseController {

    private static final String DATE_FORMAT = "dd-MMM-yyyy";
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private final DocumentService documentService;

    @Autowired
    public DocumentsController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/documents/delivery_notes")
    public ModelAndView getDeliveryNotes(@PageableDefault(size = 10, page = 0, sort = "documentCode") Pageable pageable) {
        Page<DocumentViewDto> allDocuments = this.documentService.findAllDeliveryNotesByPage(pageable);
        return this.view("documents/all-documents", new HashMap<String, Object>() {{
            put("allDocuments", allDocuments);
            put("dateFormatter", dateFormatter);
        }});
    }

    @GetMapping("/documents/issue_notes")
    public ModelAndView getIssueNotes(@PageableDefault(size = 10, page = 0, sort = "documentCode") Pageable pageable) {
        Page<DocumentViewDto> allDocuments = this.documentService.findAllIssueNotesByPage(pageable);
        return this.view("documents/all-documents", new HashMap<String, Object>() {{
            put("allDocuments", allDocuments);
            put("dateFormatter", dateFormatter);
        }});
    }

    @GetMapping("/documents/delivery_notes/search")
    public ModelAndView deliveryNotesSearch(@RequestParam(value = "value") String value,
                                            @PageableDefault(size = 10, page = 0, sort = "documentCode") Pageable pageable) {

        Page<DocumentViewDto> allByPageAndSpecification = this.documentService.findAllDeliveryNotesByPageAndSpecification(value, pageable);
        return this.view("documents/filtered-documents", new HashMap<String, Object>() {{
            put("filteredDocuments", allByPageAndSpecification);
            put("filter", new SearchFilter(value));
            put("dateFormatter", dateFormatter);
        }});
    }

    @GetMapping("/documents/issue_notes/search")
    public ModelAndView issueNotesSearch(@RequestParam(value = "value") String value,
                                         @PageableDefault(size = 10, page = 0, sort = "documentCode") Pageable pageable) {

        Page<DocumentViewDto> allByPageAndSpecification = this.documentService.findAllIssueNotesByPageAndSpecification(value, pageable);
        return this.view("documents/filtered-documents", new HashMap<String, Object>() {{
            put("filteredDocuments", allByPageAndSpecification);
            put("filter", new SearchFilter(value));
            put("dateFormatter", dateFormatter);
        }});
    }

    @GetMapping("/documents/delivery_notes/details/{id}")
    public ModelAndView deliveryNoteDetails(@PathVariable(value = "id") String id) {
        DocumentDetailsViewDto documentDetails = this.documentService.findDeliveryNoteById(id);
        return this.view("documents/document-details",  new HashMap<String, Object>() {{
            put("documentDetails",documentDetails);
            put("dateFormatter", dateFormatter);
        }});
    }

    @GetMapping("/documents/issue_notes/details/{id}")
    public ModelAndView issueNoteDetails(@PathVariable(value = "id") String id) {
        DocumentDetailsViewDto documentDetails = this.documentService.findIssueNoteById(id);
        return this.view("documents/document-details",  new HashMap<String, Object>() {{
            put("documentDetails",documentDetails);
            put("dateFormatter", dateFormatter);
        }});
    }
}
