package org.softuni.wms.areas.documents.entities.operations;

import org.hibernate.annotations.GenericGenerator;
import org.softuni.wms.areas.documents.entities.docs.Document;
import org.softuni.wms.areas.parts.entities.Part;

import javax.persistence.*;

@Entity
@Table(name = "part_issue")
public class PartIssue {
    private String id;
    private Document document;
    private Part part;
    private Long quantity;

    public PartIssue() {
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false, updatable = false)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    public Document getDocument() {
        return this.document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "part_id", referencedColumnName = "id")
    public Part getPart() {
        return this.part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    @Column(nullable = false)
    public Long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
