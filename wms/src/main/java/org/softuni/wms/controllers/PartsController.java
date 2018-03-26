package org.softuni.wms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PartsController {

    @GetMapping("/parts/add")
    public String addParts(){
        return "parts/add-parts";
    }
}
