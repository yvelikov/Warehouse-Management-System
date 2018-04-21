package org.softuni.wms.areas.documents.services.impl;

import org.softuni.wms.areas.documents.entities.docs.IssueNote;
import org.softuni.wms.areas.documents.models.view.DocumentDetailsViewDto;
import org.softuni.wms.areas.documents.models.view.DocumentViewDto;
import org.softuni.wms.areas.documents.repositories.IssueNoteDao;
import org.softuni.wms.areas.documents.services.api.IssueNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class IssueNoteServiceImpl implements IssueNoteService {

    private final IssueNoteDao issueNoteDao;

    @Autowired
    public IssueNoteServiceImpl(IssueNoteDao issueNoteDao) {
        this.issueNoteDao = issueNoteDao;
    }


    @Override
    public Page<DocumentViewDto> findAllIssueNotesByPage(Pageable pageable) {
        Page<IssueNote> allIssueNotes = this.issueNoteDao.findAllDocuments(pageable);
        return allIssueNotes.map(DocumentUtils::mapDocumentToDto);
    }

    @Override
    public Page<DocumentViewDto> findAllIssueNotesByPageAndSpecification(String param, Pageable pageable) {
        Page<Object> all = this.issueNoteDao.findAllContaining(param, pageable);
        return all.map(DocumentUtils::mapObjectToDto);
    }

    @Override
    public DocumentDetailsViewDto findIssueNoteById(String id) {
        Object[] document = (Object[]) this.issueNoteDao.findDetailsById(id);
        return DocumentUtils.getDocumentDetailsViewDto(document);
    }
}
