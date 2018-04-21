package org.softuni.wms.areas.parts.controllers;

import com.sun.jdi.request.InvalidRequestStateException;
import org.softuni.wms.areas.documents.services.api.DocumentService;
import org.softuni.wms.areas.partners.models.view.CustomerViewDto;
import org.softuni.wms.areas.partners.models.view.SupplierViewDto;
import org.softuni.wms.areas.partners.services.PartnerService;
import org.softuni.wms.areas.parts.models.binding.*;
import org.softuni.wms.areas.parts.models.view.PartViewDto;
import org.softuni.wms.areas.parts.services.PartService;
import org.softuni.wms.constants.Constants;
import org.softuni.wms.controllers.BaseController;
import org.softuni.wms.common.SearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PartsController extends BaseController {

    private final PartService partService;
    private final PartnerService partnerService;
    private final DocumentService documentService;

    @Autowired
    public PartsController(PartService partService, PartnerService partnerService, DocumentService documentService) {
        this.partService = partService;
        this.partnerService = partnerService;
        this.documentService = documentService;
    }

    private List<SupplierViewDto> getSupplierViewDtos() {
        List<SupplierViewDto> allSuppliers = this.partnerService.getAllSuppliers();
        allSuppliers.forEach(s -> s.setNumberOfSuppliedParts(this.partService.findPartsBySupplierId(s.getId()).size()));
        return allSuppliers.stream().filter(s -> s.getNumberOfSuppliedParts() > 0).collect(Collectors.toList());
    }

    private boolean isSupplierValid(@Valid PartsOperationDto partsOperationDto) {
        String supplierId = partsOperationDto.getPartnerId();
        for (OperationPartDto operationPartDto : partsOperationDto.getParts()) {
            String partSupplierId = this.partService.getSupplierId(operationPartDto.getId());
            if (!supplierId.equals(partSupplierId)) {
                return false;
            }
        }
        return true;
    }

    private PartsOperationDto initializePartsOperationDto(@Valid @ModelAttribute OperationData deliveryDataDto) {
        String partnerId = deliveryDataDto.getPartnerId();
        Integer numberOfRows = deliveryDataDto.getNumberOfRows();

        PartsOperationDto partsOperationDto = new PartsOperationDto(new OperationPartDto[numberOfRows]);

        for (int i = 0; i < partsOperationDto.getParts().length; i++) {
            partsOperationDto.getParts()[i] = new OperationPartDto();
        }

        partsOperationDto.setPartnerId(partnerId);
        return partsOperationDto;
    }

    @GetMapping("/parts/add")
    public ModelAndView addPart() {
        List<String> suppliers = this.partnerService.getAllSuppliersNames();
        return this.view("parts/add-part", new HashMap<String, Object>() {{
            put("addPartDto", new AddPartDto());
            put("suppliers", suppliers);
        }});
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN,ADMIN,EMPLOYEE')")
    @PostMapping("/parts/add")
    public ModelAndView addPartConfirm(@RequestParam String action,
                                       @Valid @ModelAttribute AddPartDto addPartDto,
                                       BindingResult bindingResult,
                                       HttpServletRequest request) {
        if ("add".equals(action)) {
            if (bindingResult.hasErrors()) {
                List<String> suppliers = this.partnerService.getAllSuppliersNames();
                return this.view("parts/add-part", new HashMap<String, Object>() {{
                    put("addPartDto", addPartDto);
                    put("suppliers", suppliers);
                }});
            }
            this.partService.addPart(addPartDto);
        }

        if ("cancel".equals(action)) {
            return this.redirectToLast(request);
        }

        return this.redirect("/parts");
    }

    @GetMapping("/parts")
    public ModelAndView getParts(@PageableDefault(size = 10, page = 0, sort = "name") Pageable pageable) {
        Page<PartViewDto> allParts = this.partService.findAllByPage(pageable);
        return this.view("parts/all-parts", "allParts", allParts);
    }

    @GetMapping("/parts/search")
    public ModelAndView partsSearch(@RequestParam(value = "value") String value,
                                    @RequestParam(value = "type") String type,
                                    @PageableDefault(size = 10, page = 0, sort = "name") Pageable pageable) {

        Page<PartViewDto> allByPageAndSpecification = this.partService.findAllByPageAndSpecification(value, type, pageable);
        return this.view("parts/filtered-parts", new HashMap<String, Object>() {{
            put("filteredParts", allByPageAndSpecification);
            put("filter", new SearchFilter(value, type));
        }});
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN,ADMIN,EMPLOYEE')")
    @GetMapping("/parts/edit/{id}")
    public ModelAndView editPart(@PathVariable String id) {
        EditPartDto editPartDto = this.partService.getOne(id);
        List<String> suppliers = this.partnerService.getAllSuppliersNames();
        return this.view("parts/edit-part", new HashMap<String, Object>() {{
            put("editPartDto", editPartDto);
            put("suppliers", suppliers);
        }});
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN,ADMIN,EMPLOYEE')")
    @PostMapping("/parts/edit/{id}")
    public ModelAndView editPartConfirm(@PathVariable String id,
                                        @RequestParam String action,
                                        @Valid @ModelAttribute EditPartDto editPartDto,
                                        BindingResult bindingResult,
                                        HttpServletRequest request) {

        if ("edit".equals(action)) {
            if (bindingResult.hasErrors()) {
                List<String> suppliers = this.partnerService.getAllSuppliersNames();
                return this.view("parts/edit-part", new HashMap<String, Object>() {{
                    put("editPartDto", editPartDto);
                    put("suppliers", suppliers);
                }});
            }
            this.partService.editPart(editPartDto);
        }
        return this.redirectToLast(request);
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN,ADMIN,EMPLOYEE')")
    @GetMapping("/parts/deliver")
    public ModelAndView deliverParts() {
        List<SupplierViewDto> suppliersWithParts = this.getSupplierViewDtos();
        return this.view("parts/deliver-parts", new HashMap<String, Object>() {{
            put("suppliersWithParts", suppliersWithParts);
            put("deliveryDataDto", new DeliveryDataDto());
        }});
    }


    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN,ADMIN,EMPLOYEE')")
    @PostMapping("/parts/deliver/select_parts")
    public ModelAndView deliverPartsSelect(@RequestParam String action,
                                           @Valid @ModelAttribute DeliveryDataDto deliveryDataDto,
                                           BindingResult bindingResult,
                                           HttpServletRequest request) {
        if ("next".equals(action)) {
            if (bindingResult.hasErrors()) {
                List<SupplierViewDto> suppliersWithParts = this.getSupplierViewDtos();
                return this.view("parts/deliver-parts", new HashMap<String, Object>() {{
                    put("suppliersWithParts", suppliersWithParts);
                    put("deliveryDataDto", deliveryDataDto);
                }});
            }
            List<PartViewDto> partsBySupplierId = this.partService.findPartsBySupplierId(deliveryDataDto.getPartnerId());
            PartsOperationDto partsDeliveryDto = this.initializePartsOperationDto(deliveryDataDto);

            return this.view("parts/select-parts-delivery", new HashMap<String, Object>() {{
                put("partsBySupplierId", partsBySupplierId);
                put("partsDeliveryDto", partsDeliveryDto);
            }});
        }

        return this.redirectToLast(request);
    }



    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN,ADMIN,EMPLOYEE')")
    @PostMapping("/parts/deliver")
    public ModelAndView deliverPartsConfirm(@RequestParam String action,
                                            @Valid @ModelAttribute PartsOperationDto partsDeliveryDto,
                                            BindingResult bindingResult,
                                            RedirectAttributes redirectAttributes,
                                            HttpServletRequest request) {
        if ("finalize".equals(action)) {
            if (bindingResult.hasErrors()) {
                List<PartViewDto> partsBySupplierId = this.partService.findPartsBySupplierId(partsDeliveryDto.getPartnerId());
                return this.view("parts/select-parts-deliver", new HashMap<String, Object>() {{
                    put("partsBySupplierId", partsBySupplierId);
                    put("partsDeliveryDto", partsDeliveryDto);
                }});
            }

            if (!this.isSupplierValid(partsDeliveryDto)) {
                throw new InvalidRequestStateException();
            }

            if(this.partService.deliver(partsDeliveryDto)){
                redirectAttributes.addFlashAttribute("actionResult", Constants.SUCCESSFUL_DELIVERY);
                this.documentService.generateDeliveryNote(request.getUserPrincipal(), partsDeliveryDto);
            }
        }

        return this.redirect("/parts");
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN,ADMIN,EMPLOYEE')")
    @GetMapping("/parts/issue")
    public ModelAndView issueParts() {
        List<CustomerViewDto> allCustomers = this.partnerService.getAllCustomers();
        return this.view("parts/issue-parts", new HashMap<String, Object>() {{
            put("allCustomers", allCustomers);
            put("issueDataDto", new IssueDataDto());
        }});
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN,ADMIN,EMPLOYEE')")
    @PostMapping("/parts/issue/select_parts")
    public ModelAndView issuePartsSelect(@RequestParam String action,
                                           @Valid @ModelAttribute IssueDataDto issueDataDto,
                                           BindingResult bindingResult,
                                           HttpServletRequest request) {
        if ("next".equals(action)) {
            if (bindingResult.hasErrors()) {
                List<CustomerViewDto> allCustomers = this.partnerService.getAllCustomers();
                return this.view("parts/issue-parts", new HashMap<String, Object>() {{
                    put("allCustomers", allCustomers);
                    put("issueDataDto", issueDataDto);
                }});
            }
            List<PartViewDto> allPartsOnStock = this.partService.findAllPartsOnStock();
            PartsOperationDto partsIssueDto = this.initializePartsOperationDto(issueDataDto);

            return this.view("parts/select-parts-issue", new HashMap<String, Object>() {{
                put("allPartsOnStock", allPartsOnStock);
                put("partsIssueDto", partsIssueDto);
            }});
        }
        return this.redirectToLast(request);
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN,ADMIN,EMPLOYEE')")
    @PostMapping("/parts/issue")
    public ModelAndView issuePartsConfirm(@RequestParam String action,
                                            @Valid @ModelAttribute PartsOperationDto partsIssueDto,
                                            BindingResult bindingResult,
                                            RedirectAttributes redirectAttributes,
                                            HttpServletRequest request) {
        if ("finalize".equals(action)) {
            if (bindingResult.hasErrors()) {
                List<PartViewDto> allPartsOnStock = this.partService.findAllPartsOnStock();
                return this.view("parts/select-parts-issue", new HashMap<String, Object>() {{
                    put("allPartsOnStock", allPartsOnStock);
                    put("partsIssueDto", partsIssueDto);
                }});
            }

            if(this.partService.issue(partsIssueDto)){
                redirectAttributes.addFlashAttribute("actionResult", Constants.SUCCESSFUL_ISSUE);
                this.documentService.generateIssueNote(request.getUserPrincipal(), partsIssueDto);
            }

        }

        return this.redirect("/parts");
    }
}
