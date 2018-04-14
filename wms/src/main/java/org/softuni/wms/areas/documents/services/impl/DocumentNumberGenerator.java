package org.softuni.wms.areas.documents.services.impl;

import org.softuni.wms.areas.documents.entities.docs.Sequence;
import org.softuni.wms.areas.documents.repositories.SequenceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DocumentNumberGenerator{

    private final SequenceDao sequenceDao;

    @Autowired
    public DocumentNumberGenerator(SequenceDao sequenceDao) {
        this.sequenceDao = sequenceDao;
    }

    public Long next() {
        Sequence sequence = sequenceDao.findByName(getName());
        if (sequence == null) {
            sequence = new Sequence(getName());
        }

        Long value = sequence.getValue();
        sequence.setValue(++value);
        sequenceDao.saveAndFlush(sequence);

        return value;
    }

    String getName() {
        return "DOC";
    }
}
