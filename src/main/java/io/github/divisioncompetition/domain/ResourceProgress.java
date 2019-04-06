package io.github.divisioncompetition.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A ResourceProgress.
 */
@Entity
@Table(name = "resource_progress")
public class ResourceProgress implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "progress")
    private Double progress;

    @NotNull
    @Column(name = "last_modified", nullable = false)
    private Instant lastModified;

    @ManyToOne
    @JsonIgnoreProperties("resourceProgresses")
    private Resource resource;

    @ManyToOne
    @JsonIgnoreProperties("resources")
    private BuildingProcess buildingProcess;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getProgress() {
        return progress;
    }

    public ResourceProgress progress(Double progress) {
        this.progress = progress;
        return this;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public ResourceProgress lastModified(Instant lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public Resource getResource() {
        return resource;
    }

    public ResourceProgress resource(Resource resource) {
        this.resource = resource;
        return this;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public BuildingProcess getBuildingProcess() {
        return buildingProcess;
    }

    public ResourceProgress buildingProcess(BuildingProcess buildingProcess) {
        this.buildingProcess = buildingProcess;
        return this;
    }

    public void setBuildingProcess(BuildingProcess buildingProcess) {
        this.buildingProcess = buildingProcess;
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
        ResourceProgress resourceProgress = (ResourceProgress) o;
        if (resourceProgress.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resourceProgress.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResourceProgress{" +
            "id=" + getId() +
            ", progress=" + getProgress() +
            ", lastModified='" + getLastModified() + "'" +
            "}";
    }
}
