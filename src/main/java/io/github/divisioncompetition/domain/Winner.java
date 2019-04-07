package io.github.divisioncompetition.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Winner.
 */
@Entity
@Table(name = "winner")
public class Winner implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("winners")
    private Subbranch subbranch;

    @ManyToOne
    @JsonIgnoreProperties("winners")
    private Building building;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subbranch getSubbranch() {
        return subbranch;
    }

    public Winner subbranch(Subbranch subbranch) {
        this.subbranch = subbranch;
        return this;
    }

    public void setSubbranch(Subbranch subbranch) {
        this.subbranch = subbranch;
    }

    public Building getBuilding() {
        return building;
    }

    public Winner building(Building building) {
        this.building = building;
        return this;
    }

    public void setBuilding(Building building) {
        this.building = building;
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
        Winner winner = (Winner) o;
        if (winner.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), winner.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Winner{" +
            "id=" + getId() +
            "}";
    }
}
