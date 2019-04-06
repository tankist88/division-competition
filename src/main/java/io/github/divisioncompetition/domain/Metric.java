package io.github.divisioncompetition.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import io.github.divisioncompetition.domain.enumeration.MetricTermType;

/**
 * A Metric.
 */
@Entity
@Table(name = "metric")
public class Metric implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "metric_id", nullable = false)
    private Integer metricId;

    @NotNull
    @Column(name = "metric_name", nullable = false)
    private String metricName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "term_type", nullable = false)
    private MetricTermType termType;

    @NotNull
    @Column(name = "term", nullable = false)
    private Integer term;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMetricId() {
        return metricId;
    }

    public Metric metricId(Integer metricId) {
        this.metricId = metricId;
        return this;
    }

    public void setMetricId(Integer metricId) {
        this.metricId = metricId;
    }

    public String getMetricName() {
        return metricName;
    }

    public Metric metricName(String metricName) {
        this.metricName = metricName;
        return this;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public MetricTermType getTermType() {
        return termType;
    }

    public Metric termType(MetricTermType termType) {
        this.termType = termType;
        return this;
    }

    public void setTermType(MetricTermType termType) {
        this.termType = termType;
    }

    public Integer getTerm() {
        return term;
    }

    public Metric term(Integer term) {
        this.term = term;
        return this;
    }

    public void setTerm(Integer term) {
        this.term = term;
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
        Metric metric = (Metric) o;
        if (metric.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), metric.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Metric{" +
            "id=" + getId() +
            ", metricId=" + getMetricId() +
            ", metricName='" + getMetricName() + "'" +
            ", termType='" + getTermType() + "'" +
            ", term=" + getTerm() +
            "}";
    }
}
