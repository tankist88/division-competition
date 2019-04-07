package io.github.divisioncompetition.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @OneToMany(mappedBy = "winner")
    private Set<Subbranch> subbranches = new HashSet<>();
    @OneToMany(mappedBy = "winner")
    private Set<Building> buildings = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Subbranch> getSubbranches() {
        return subbranches;
    }

    public Winner subbranches(Set<Subbranch> subbranches) {
        this.subbranches = subbranches;
        return this;
    }

    public Winner addSubbranch(Subbranch subbranch) {
        this.subbranches.add(subbranch);
        subbranch.setWinner(this);
        return this;
    }

    public Winner removeSubbranch(Subbranch subbranch) {
        this.subbranches.remove(subbranch);
        subbranch.setWinner(null);
        return this;
    }

    public void setSubbranches(Set<Subbranch> subbranches) {
        this.subbranches = subbranches;
    }

    public Set<Building> getBuildings() {
        return buildings;
    }

    public Winner buildings(Set<Building> buildings) {
        this.buildings = buildings;
        return this;
    }

    public Winner addBuilding(Building building) {
        this.buildings.add(building);
        building.setWinner(this);
        return this;
    }

    public Winner removeBuilding(Building building) {
        this.buildings.remove(building);
        building.setWinner(null);
        return this;
    }

    public void setBuildings(Set<Building> buildings) {
        this.buildings = buildings;
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
