package org.softuni.wms.areas.partners.controllers;

import org.softuni.wms.areas.partners.models.binding.AddPartnerDto;
import org.softuni.wms.areas.partners.models.binding.EditPartnerDto;
import org.softuni.wms.areas.partners.models.view.PartnerViewDto;
import org.softuni.wms.areas.partners.services.PartnerService;
import org.softuni.wms.controllers.BaseController;
import org.softuni.wms.utils.SearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;

@Controller
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN','EMPLOYEE')")
public class PartnersController extends BaseController {

    private final PartnerService partnerService;

    @Autowired
    public PartnersController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @GetMapping("/partners")
    public ModelAndView getPartners(@PageableDefault(size = 10, page = 0, sort = "name") Pageable pageable) {
        Page<PartnerViewDto> allPartners = this.partnerService.findAllByPage(pageable);
        return this.view("partners/all-partners","allPartners", allPartners);
    }

    @GetMapping("/partners/search")
    public ModelAndView partnersSearch(@RequestParam(value = "value") String value,
                                       @RequestParam(value = "type") String type,
                                       @PageableDefault(size = 10, page = 0, sort = "name") Pageable pageable) {

        Page<PartnerViewDto> allByPageAndSpecification = this.partnerService.findAllByPageAndSpecification(value, type, pageable);

        return this.view("partners/filtered-partners", new HashMap<String, Object>() {{
            put("filteredPartners", allByPageAndSpecification);
            put("filter", new SearchFilter(value, type));
        }});
    }

    @GetMapping("/partners/add")
    public ModelAndView addPartner() {
        return this.view("partners/add-partner", "addPartnerDto", new AddPartnerDto());
    }

    @PostMapping("/partners/add")
    public ModelAndView addPartnerConfirm(@RequestParam String action,
                                          @Valid @ModelAttribute AddPartnerDto addPartnerDto,
                                          BindingResult bindingResult,
                                          HttpServletRequest request) {
        if ("add".equals(action)) {
            if (bindingResult.hasErrors()) {
                return this.view("partners/add-partner", "addPartnerDto", addPartnerDto);
            }
            this.partnerService.addPartner(addPartnerDto);
        }

        if ("cancel".equals(action)) {
            return this.redirectToLast(request);
        }

        return this.redirectToLast(request);
    }

    @GetMapping("/partners/edit/{id}")
    public ModelAndView editPartner(@PathVariable String id) {
        EditPartnerDto editPartnerDto = this.partnerService.getOne(id);
        return this.view("partners/edit-partner", "editPartnerDto", editPartnerDto);
    }

    @PostMapping("/partners/edit/{id}")
    public ModelAndView editPartnerConfirm(@PathVariable String id,
                                           @RequestParam String action,
                                           @Valid @ModelAttribute EditPartnerDto editPartnerDto,
                                           BindingResult bindingResult,
                                           HttpServletRequest request) {

        if ("edit".equals(action)) {
            if (bindingResult.hasErrors()) {
                return this.view("partners/edit-partner", "editPartnerDto", editPartnerDto);
            }
            this.partnerService.editPartner(editPartnerDto);
        }

        if ("cancel".equals(action)) {
            return this.redirectToLast(request);
        }

        return this.redirectToLast(request);
    }
}
