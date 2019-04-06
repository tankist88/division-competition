package io.github.divisioncompetition.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import io.github.divisioncompetition.domain.enumeration.ResourceType;

/**
 * A Resource.
 */
@Entity
@Table(name = "resource")
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "count", nullable = false)
    private Double count;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private ResourceType type;

    @Column(name = "factor")
    private Double factor;

    @ManyToOne
    @JsonIgnoreProperties("resources")
    private Metric metric;

    @ManyToOne
    @JsonIgnoreProperties("resources")
    private Building building;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCount() {
        return count;
    }

    public Resource count(Double count) {
        this.count = count;
        return this;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public ResourceType getType() {
        return type;
    }

    public Resource type(ResourceType type) {
        this.type = type;
        return this;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public Double getFactor() {
        return factor;
    }

    public Resource factor(Double factor) {
        this.factor = factor;
        return this;
    }

    public void setFactor(Double factor) {
        this.factor = factor;
    }

    public Metric getMetric() {
        return metric;
    }

    public Resource metric(Metric metric) {
        this.metric = metric;
        return this;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    public Building getBuilding() {
        return building;
    }

    public Resource building(Building building) {
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
        Resource resource = (Resource) o;
        if (resource.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resource.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Resource{" +
            "id=" + getId() +
            ", count=" + getCount() +
            ", type='" + getType() + "'" +
            ", factor=" + getFactor() +
            "}";
    }
}
