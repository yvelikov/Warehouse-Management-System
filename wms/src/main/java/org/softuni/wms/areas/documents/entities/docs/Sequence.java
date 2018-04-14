package org.softuni.wms.areas.documents.entities.docs;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "sequence")
public class Sequence {

    private Long id;
    private String name;
    private Long value = 0L;

    private Sequence() {
    }

    public Sequence(final String name) {
        this.name = name;
        this.value = 0L;
    }

    @Generated(GenerationTime.INSERT)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Column(unique = true, updatable = false)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    @NotNull
    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
