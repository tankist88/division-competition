package io.github.divisioncompetition.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BuildingProcess.
 */
@Entity
@Table(name = "building_process")
public class BuildingProcess implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToMany(mappedBy = "buildingProcess")
    private Set<ResourceProgress> resources = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("buildingProcesses")
    private Subbranch subbranch;

    @ManyToOne
    @JsonIgnoreProperties("buildingProcesses")
    private Building building;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<ResourceProgress> getResources() {
        return resources;
    }

    public BuildingProcess resources(Set<ResourceProgress> resourceProgresses) {
        this.resources = resourceProgresses;
        return this;
    }

    public BuildingProcess addResources(ResourceProgress resourceProgress) {
        this.resources.add(resourceProgress);
        resourceProgress.setBuildingProcess(this);
        return this;
    }

    public BuildingProcess removeResources(ResourceProgress resourceProgress) {
        this.resources.remove(resourceProgress);
        resourceProgress.setBuildingProcess(null);
        return this;
    }

    public void setResources(Set<ResourceProgress> resourceProgresses) {
        this.resources = resourceProgresses;
    }

    public Subbranch getSubbranch() {
        return subbranch;
    }

    public BuildingProcess subbranch(Subbranch subbranch) {
        this.subbranch = subbranch;
        return this;
    }

    public void setSubbranch(Subbranch subbranch) {
        this.subbranch = subbranch;
    }

    public Building getBuilding() {
        return building;
    }

    public BuildingProcess building(Building building) {
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
        BuildingProcess buildingProcess = (BuildingProcess) o;
        if (buildingProcess.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), buildingProcess.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BuildingProcess{" +
            "id=" + getId() +
            "}";
    }
}
