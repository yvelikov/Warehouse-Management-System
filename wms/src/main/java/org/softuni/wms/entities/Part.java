package org.softuni.wms.entities;


import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.softuni.wms.enums.UnitOfMeasure;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "parts")
public class Part {

    private String id;
    private String articleCode;
    private String name;
    private BigDecimal deliveryPrice;
    private BigDecimal listPrice;
    private Long quantity;
    private UnitOfMeasure unitOfMeasure;
    private Partner supplier;

    public Part() {
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

    @Column(name = "article_code", nullable = false, unique = true)
    public String getArticleCode() {
        return this.articleCode;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    @Column(nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "delivery_price", nullable = false)
    public BigDecimal getDeliveryPrice() {
        return this.deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    @Column(name = "list_price", nullable = false)
    public BigDecimal getListPrice() {
        return this.listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    @Column(nullable = false)
    @ColumnDefault("0")
    public Long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Column(name = "unit_of_measure")
    @Enumerated(EnumType.STRING)
    public UnitOfMeasure getUnitOfMeasure() {
        return this.unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "supplier_id",referencedColumnName = "id")
    public Partner getSupplier() {
        return this.supplier;
    }

    public void setSupplier(Partner supplier) {
        this.supplier = supplier;
    }
}
