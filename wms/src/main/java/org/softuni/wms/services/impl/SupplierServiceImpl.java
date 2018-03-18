package org.softuni.wms.services.impl;

import org.softuni.wms.repostitory.SupplierDao;
import org.softuni.wms.services.api.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {

    private final SupplierDao supplierDao;

    @Autowired
    public SupplierServiceImpl(SupplierDao supplierDao) {
        this.supplierDao = supplierDao;
    }
}
