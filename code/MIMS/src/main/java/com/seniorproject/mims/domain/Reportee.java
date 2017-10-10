package com.seniorproject.mims.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Reportee.
 */
@Entity
@Table(name = "reportee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "reportee")
public class Reportee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "parent_name")
    private String parentName;

    @Column(name = "parent_phone_number")
    private String parentPhoneNumber;

    @Column(name = "parent_email")
    private String parentEmail;

    @OneToOne
    @JoinColumn(unique = true)
    private Report report;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParentName() {
        return parentName;
    }

    public Reportee parentName(String parentName) {
        this.parentName = parentName;
        return this;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentPhoneNumber() {
        return parentPhoneNumber;
    }

    public Reportee parentPhoneNumber(String parentPhoneNumber) {
        this.parentPhoneNumber = parentPhoneNumber;
        return this;
    }

    public void setParentPhoneNumber(String parentPhoneNumber) {
        this.parentPhoneNumber = parentPhoneNumber;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public Reportee parentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
        return this;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

    public Report getReport() {
        return report;
    }

    public Reportee report(Report report) {
        this.report = report;
        return this;
    }

    public void setReport(Report report) {
        this.report = report;
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
        Reportee reportee = (Reportee) o;
        if (reportee.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reportee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reportee{" +
            "id=" + getId() +
            ", parentName='" + getParentName() + "'" +
            ", parentPhoneNumber='" + getParentPhoneNumber() + "'" +
            ", parentEmail='" + getParentEmail() + "'" +
            "}";
    }
}
