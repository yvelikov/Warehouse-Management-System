package org.softuni.wms.areas.documents.entities.docs;

import org.hibernate.annotations.GenericGenerator;
import org.softuni.wms.areas.partners.entities.Partner;
import org.softuni.wms.areas.users.entities.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "documents")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Document {

    private String id;
    private String documentCode;
    private Date date;
    private User user;
    private Partner partner;

    protected Document() {
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

    @Column(name = "document_number", unique = true, nullable = false, updatable = false)
    public String getDocumentCode() {
        return this.documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    @Column
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "partner_id", referencedColumnName = "id")
    public Partner getPartner() {
        return this.partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

}
