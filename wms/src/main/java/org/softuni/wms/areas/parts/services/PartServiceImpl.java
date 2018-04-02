package org.softuni.wms.areas.parts.services;

import org.softuni.wms.areas.parts.repositories.PartDao;
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
