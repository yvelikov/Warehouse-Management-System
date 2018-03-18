package org.softuni.wms.entities;


import org.hibernate.annotations.GenericGenerator;
import org.softuni.wms.entities.documents.Document;
import org.softuni.wms.enums.Operation;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "history")
public class History {

    private String id;
    private Part part;
    private User user;
    private Date date;
    private Operation operation;
    private Document document;

    public History() {
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
    @JoinColumn(name = "part_id", referencedColumnName = "id")
    public Part getPart() {
        return this.part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column
    @Enumerated(EnumType.STRING)
    public Operation getOperation() {
        return this.operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @OneToOne(optional = false)
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    public Document getDocument() {
        return this.document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
