package io.github.divisioncompetition.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Subbranch.
 */
@Entity
@Table(name = "subbranch")
public class Subbranch implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "tb", nullable = false)
    private Integer tb;

    @Column(name = "branch")
    private Integer branch;

    @Column(name = "subbranch")
    private Integer subbranch;

    @Column(name = "name")
    private String name;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTb() {
        return tb;
    }

    public Subbranch tb(Integer tb) {
        this.tb = tb;
        return this;
    }

    public void setTb(Integer tb) {
        this.tb = tb;
    }

    public Integer getBranch() {
        return branch;
    }

    public Subbranch branch(Integer branch) {
        this.branch = branch;
        return this;
    }

    public void setBranch(Integer branch) {
        this.branch = branch;
    }

    public Integer getSubbranch() {
        return subbranch;
    }

    public Subbranch subbranch(Integer subbranch) {
        this.subbranch = subbranch;
        return this;
    }

    public void setSubbranch(Integer subbranch) {
        this.subbranch = subbranch;
    }

    public String getName() {
        return name;
    }

    public Subbranch name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Subbranch subbranch = (Subbranch) o;
        if (subbranch.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subbranch.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Subbranch{" +
            "id=" + getId() +
            ", tb=" + getTb() +
            ", branch=" + getBranch() +
            ", subbranch=" + getSubbranch() +
            ", name='" + getName() + "'" +
            "}";
    }
}
