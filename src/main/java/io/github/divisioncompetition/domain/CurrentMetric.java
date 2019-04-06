package io.github.divisioncompetition.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A CurrentMetric.
 */
@Entity
@Table(name = "current_metric")
public class CurrentMetric implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "count")
    private Integer count;

    @Column(name = "finalized_count")
    private Integer finalizedCount;

    @Column(name = "finalize_date")
    private Instant finalizeDate;

    @NotNull
    @Column(name = "last_modified", nullable = false)
    private Instant lastModified;

    @ManyToOne
    @JsonIgnoreProperties("currentMetrics")
    private Subbranch subbranch;

    @ManyToOne
    @JsonIgnoreProperties("currentMetrics")
    private Metric metric;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public CurrentMetric count(Integer count) {
        this.count = count;
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getFinalizedCount() {
        return finalizedCount;
    }

    public CurrentMetric finalizedCount(Integer finalizedCount) {
        this.finalizedCount = finalizedCount;
        return this;
    }

    public void setFinalizedCount(Integer finalizedCount) {
        this.finalizedCount = finalizedCount;
    }

    public Instant getFinalizeDate() {
        return finalizeDate;
    }

    public CurrentMetric finalizeDate(Instant finalizeDate) {
        this.finalizeDate = finalizeDate;
        return this;
    }

    public void setFinalizeDate(Instant finalizeDate) {
        this.finalizeDate = finalizeDate;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public CurrentMetric lastModified(Instant lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public Subbranch getSubbranch() {
        return subbranch;
    }

    public CurrentMetric subbranch(Subbranch subbranch) {
        this.subbranch = subbranch;
        return this;
    }

    public void setSubbranch(Subbranch subbranch) {
        this.subbranch = subbranch;
    }

    public Metric getMetric() {
        return metric;
    }

    public CurrentMetric metric(Metric metric) {
        this.metric = metric;
        return this;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
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
        CurrentMetric currentMetric = (CurrentMetric) o;
        if (currentMetric.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), currentMetric.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CurrentMetric{" +
            "id=" + getId() +
            ", count=" + getCount() +
            ", finalizedCount=" + getFinalizedCount() +
            ", finalizeDate='" + getFinalizeDate() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            "}";
    }
}
