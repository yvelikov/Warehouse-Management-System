package org.softuni.wms.areas.parts.controllers;

import org.softuni.wms.areas.parts.services.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8000")
@RestController
@RequestMapping("/parts/deliver")
public class PartsDeliveryController {

    private final PartService partService;

    @Autowired
    public PartsDeliveryController(PartService partService) {
        this.partService = partService;
    }

//    @CrossOrigin(origins = "http://localhost:8000")
//    @GetMapping("/partsBySupplier")
//    public ResponseEntity<List<PartViewModel>> getPartsBySupplier(@Param("id") String id, HttpServletResponse response) {
//        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8000");
//        return ResponseEntity.ok(this.partService.findPartsBySupplierId(id));
//    }
}
