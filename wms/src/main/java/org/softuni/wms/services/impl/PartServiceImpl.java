package org.softuni.wms.services.impl;

import org.softuni.wms.repostitory.PartDao;
import org.softuni.wms.services.api.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PartServiceImpl implements PartService {

    private final PartDao partDao;

    @Autowired
    public PartServiceImpl(PartDao partDao) {
        this.partDao = partDao;
    }
}
