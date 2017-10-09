package org.jhipster.blog.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Report.
 */
@Entity
@Table(name = "report")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "report")
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "victim_name")
    private String victimName;

    @Column(name = "parent_name")
    private String parentName;

    @Column(name = "parent_phone_number")
    private String parentPhoneNumber;

    @Column(name = "date_of_birth")
    private ZonedDateTime dateOfBirth;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "height")
    private String height;

    @Column(name = "weight")
    private Long weight;

    @Column(name = "eye_color")
    private String eyeColor;

    @Column(name = "demographic")
    private String demographic;

    @Column(name = "last_known_location")
    private String lastKnownLocation;

    @Column(name = "time_of_last_seen")
    private ZonedDateTime timeOfLastSeen;

    @Column(name = "service_provider")
    private String serviceProvider;

    @Column(name = "master_account_number")
    private String masterAccountNumber;

    @Column(name = "complaint_number")
    private String complaintNumber;

    @Column(name = "report_number")
    private String reportNumber;

    @Column(name = "investigator_email")
    private String investigatorEmail;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVictimName() {
        return victimName;
    }

    public Report victimName(String victimName) {
        this.victimName = victimName;
        return this;
    }

    public void setVictimName(String victimName) {
        this.victimName = victimName;
    }

    public String getParentName() {
        return parentName;
    }

    public Report parentName(String parentName) {
        this.parentName = parentName;
        return this;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentPhoneNumber() {
        return parentPhoneNumber;
    }

    public Report parentPhoneNumber(String parentPhoneNumber) {
        this.parentPhoneNumber = parentPhoneNumber;
        return this;
    }

    public void setParentPhoneNumber(String parentPhoneNumber) {
        this.parentPhoneNumber = parentPhoneNumber;
    }

    public ZonedDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public Report dateOfBirth(ZonedDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(ZonedDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Report phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHeight() {
        return height;
    }

    public Report height(String height) {
        this.height = height;
        return this;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public Long getWeight() {
        return weight;
    }

    public Report weight(Long weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public Report eyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
        return this;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getDemographic() {
        return demographic;
    }

    public Report demographic(String demographic) {
        this.demographic = demographic;
        return this;
    }

    public void setDemographic(String demographic) {
        this.demographic = demographic;
    }

    public String getLastKnownLocation() {
        return lastKnownLocation;
    }

    public Report lastKnownLocation(String lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
        return this;
    }

    public void setLastKnownLocation(String lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }

    public ZonedDateTime getTimeOfLastSeen() {
        return timeOfLastSeen;
    }

    public Report timeOfLastSeen(ZonedDateTime timeOfLastSeen) {
        this.timeOfLastSeen = timeOfLastSeen;
        return this;
    }

    public void setTimeOfLastSeen(ZonedDateTime timeOfLastSeen) {
        this.timeOfLastSeen = timeOfLastSeen;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public Report serviceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
        return this;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getMasterAccountNumber() {
        return masterAccountNumber;
    }

    public Report masterAccountNumber(String masterAccountNumber) {
        this.masterAccountNumber = masterAccountNumber;
        return this;
    }

    public void setMasterAccountNumber(String masterAccountNumber) {
        this.masterAccountNumber = masterAccountNumber;
    }

    public String getComplaintNumber() {
        return complaintNumber;
    }

    public Report complaintNumber(String complaintNumber) {
        this.complaintNumber = complaintNumber;
        return this;
    }

    public void setComplaintNumber(String complaintNumber) {
        this.complaintNumber = complaintNumber;
    }

    public String getReportNumber() {
        return reportNumber;
    }

    public Report reportNumber(String reportNumber) {
        this.reportNumber = reportNumber;
        return this;
    }

    public void setReportNumber(String reportNumber) {
        this.reportNumber = reportNumber;
    }

    public String getInvestigatorEmail() {
        return investigatorEmail;
    }

    public Report investigatorEmail(String investigatorEmail) {
        this.investigatorEmail = investigatorEmail;
        return this;
    }

    public void setInvestigatorEmail(String investigatorEmail) {
        this.investigatorEmail = investigatorEmail;
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
        Report report = (Report) o;
        if (report.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), report.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Report{" +
            "id=" + getId() +
            ", victimName='" + getVictimName() + "'" +
            ", parentName='" + getParentName() + "'" +
            ", parentPhoneNumber='" + getParentPhoneNumber() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", height='" + getHeight() + "'" +
            ", weight='" + getWeight() + "'" +
            ", eyeColor='" + getEyeColor() + "'" +
            ", demographic='" + getDemographic() + "'" +
            ", lastKnownLocation='" + getLastKnownLocation() + "'" +
            ", timeOfLastSeen='" + getTimeOfLastSeen() + "'" +
            ", serviceProvider='" + getServiceProvider() + "'" +
            ", masterAccountNumber='" + getMasterAccountNumber() + "'" +
            ", complaintNumber='" + getComplaintNumber() + "'" +
            ", reportNumber='" + getReportNumber() + "'" +
            ", investigatorEmail='" + getInvestigatorEmail() + "'" +
            "}";
    }
}
