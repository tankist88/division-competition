package io.github.divisioncompetition.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Building.
 */
@Entity
@Table(name = "building")
public class Building implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "site_url")
    private String siteUrl;

    @Column(name = "picture_file")
    private String pictureFile;

    @ManyToOne
    @JsonIgnoreProperties("buildings")
    private Winner winner;

    @OneToMany(mappedBy = "building")
    private Set<Resource> resources = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Building name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Building description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public Building siteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
        return this;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getPictureFile() {
        return pictureFile;
    }

    public Building pictureFile(String pictureFile) {
        this.pictureFile = pictureFile;
        return this;
    }

    public void setPictureFile(String pictureFile) {
        this.pictureFile = pictureFile;
    }

    public Winner getWinner() {
        return winner;
    }

    public Building winner(Winner winner) {
        this.winner = winner;
        return this;
    }

    public void setWinner(Winner winner) {
        this.winner = winner;
    }

    public Set<Resource> getResources() {
        return resources;
    }

    public Building resources(Set<Resource> resources) {
        this.resources = resources;
        return this;
    }

    public Building addResources(Resource resource) {
        this.resources.add(resource);
        resource.setBuilding(this);
        return this;
    }

    public Building removeResources(Resource resource) {
        this.resources.remove(resource);
        resource.setBuilding(null);
        return this;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
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
        Building building = (Building) o;
        if (building.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), building.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Building{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", siteUrl='" + getSiteUrl() + "'" +
            ", pictureFile='" + getPictureFile() + "'" +
            "}";
    }
}
