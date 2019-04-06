package io.github.divisioncompetition.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A BattleMember.
 */
@Entity
@Table(name = "battle_member")
public class BattleMember implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @NotNull
    @Column(name = "last_modified", nullable = false)
    private Instant lastModified;

    @ManyToOne
    @JsonIgnoreProperties("battleMembers")
    private Subbranch subbranch;

    @ManyToOne
    @JsonIgnoreProperties("battleMembers")
    private BattleType type;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public BattleMember status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public BattleMember lastModified(Instant lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public Subbranch getSubbranch() {
        return subbranch;
    }

    public BattleMember subbranch(Subbranch subbranch) {
        this.subbranch = subbranch;
        return this;
    }

    public void setSubbranch(Subbranch subbranch) {
        this.subbranch = subbranch;
    }

    public BattleType getType() {
        return type;
    }

    public BattleMember type(BattleType battleType) {
        this.type = battleType;
        return this;
    }

    public void setType(BattleType battleType) {
        this.type = battleType;
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
        BattleMember battleMember = (BattleMember) o;
        if (battleMember.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), battleMember.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BattleMember{" +
            "id=" + getId() +
            ", status=" + getStatus() +
            ", lastModified='" + getLastModified() + "'" +
            "}";
    }
}
