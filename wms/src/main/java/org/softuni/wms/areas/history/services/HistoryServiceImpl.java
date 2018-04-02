package org.softuni.wms.areas.history.services;

import org.softuni.wms.areas.history.repositories.HistoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class HistoryServiceImpl implements HistoryService {

    private final HistoryDao historyDao;

    @Autowired
    public HistoryServiceImpl(HistoryDao historyDao) {
        this.historyDao = historyDao;
    }
}
