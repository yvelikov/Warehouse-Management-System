package org.softuni.wms.areas.documents.repositories;

import org.softuni.wms.areas.documents.entities.docs.IssueNote;

import javax.transaction.Transactional;

@Transactional
public interface IssueNoteDao extends BaseDocumentDao<IssueNote>{
}
