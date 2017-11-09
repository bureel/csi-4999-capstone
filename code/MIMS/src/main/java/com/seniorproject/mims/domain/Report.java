package com.seniorproject.mims.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Report.
 */
@Entity
@Table(name = "report")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "status")
    private String status;

    @Column(name = "resolution")
    private String resolution;

    @Column(name = "victim_name")
    private String victimName;

    @Column(name = "victim_phone_number")
    private String victimPhoneNumber;

    @Column(name = "victim_email")
    private String victimEmail;

    @Column(name = "parent_name")
    private String parentName;

    @Column(name = "parent_phone_number")
    private String parentPhoneNumber;

    @Column(name = "parent_email")
    private String parentEmail;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "height")
    private String height;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "eye_color")
    private String eyeColor;

    @Column(name = "demographic")
    private String demographic;

    @Column(name = "last_known_location")
    private String lastKnownLocation;

    @Column(name = "last_seen")
    private ZonedDateTime lastSeen;

    @Column(name = "service_provider")
    private String serviceProvider;

    @Column(name = "service_provider_account_number")
    private String serviceProviderAccountNumber;

    @Column(name = "complaint_number")
    private String complaintNumber;

    @Column(name = "report_number")
    private String reportNumber;

    @Column(name = "investigator_name")
    private String investigatorName;

    @Column(name = "investigator_email")
    private String investigatorEmail;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Lob
    @Column(name = "photos")
    private byte[] photos;

    @Column(name = "photos_content_type")
    private String photosContentType;

    @Column(name = "additional_information")
    private String additionalInformation;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public Report status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResolution() {
        return resolution;
    }

    public Report resolution(String resolution) {
        this.resolution = resolution;
        return this;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
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

    public String getVictimPhoneNumber() {
        return victimPhoneNumber;
    }

    public Report victimPhoneNumber(String victimPhoneNumber) {
        this.victimPhoneNumber = victimPhoneNumber;
        return this;
    }

    public void setVictimPhoneNumber(String victimPhoneNumber) {
        this.victimPhoneNumber = victimPhoneNumber;
    }

    public String getVictimEmail() {
        return victimEmail;
    }

    public Report victimEmail(String victimEmail) {
        this.victimEmail = victimEmail;
        return this;
    }

    public void setVictimEmail(String victimEmail) {
        this.victimEmail = victimEmail;
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

    public String getParentEmail() {
        return parentEmail;
    }

    public Report parentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
        return this;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Report dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public Double getWeight() {
        return weight;
    }

    public Report weight(Double weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Double weight) {
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

    public ZonedDateTime getLastSeen() {
        return lastSeen;
    }

    public Report lastSeen(ZonedDateTime lastSeen) {
        this.lastSeen = lastSeen;
        return this;
    }

    public void setLastSeen(ZonedDateTime lastSeen) {
        this.lastSeen = lastSeen;
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

    public String getServiceProviderAccountNumber() {
        return serviceProviderAccountNumber;
    }

    public Report serviceProviderAccountNumber(String serviceProviderAccountNumber) {
        this.serviceProviderAccountNumber = serviceProviderAccountNumber;
        return this;
    }

    public void setServiceProviderAccountNumber(String serviceProviderAccountNumber) {
        this.serviceProviderAccountNumber = serviceProviderAccountNumber;
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

    public String getInvestigatorName() {
        return investigatorName;
    }

    public Report investigatorName(String investigatorName) {
        this.investigatorName = investigatorName;
        return this;
    }

    public void setInvestigatorName(String investigatorName) {
        this.investigatorName = investigatorName;
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

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public Report createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Report updatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public byte[] getPhotos() {
        return photos;
    }

    public Report photos(byte[] photos) {
        this.photos = photos;
        return this;
    }

    public void setPhotos(byte[] photos) {
        this.photos = photos;
    }

    public String getPhotosContentType() {
        return photosContentType;
    }

    public Report photosContentType(String photosContentType) {
        this.photosContentType = photosContentType;
        return this;
    }

    public void setPhotosContentType(String photosContentType) {
        this.photosContentType = photosContentType;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public Report additionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
        return this;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public User getUser() {
        return user;
    }

    public Report user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
            ", status='" + getStatus() + "'" +
            ", resolution='" + getResolution() + "'" +
            ", victimName='" + getVictimName() + "'" +
            ", victimPhoneNumber='" + getVictimPhoneNumber() + "'" +
            ", victimEmail='" + getVictimEmail() + "'" +
            ", parentName='" + getParentName() + "'" +
            ", parentPhoneNumber='" + getParentPhoneNumber() + "'" +
            ", parentEmail='" + getParentEmail() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", height='" + getHeight() + "'" +
            ", weight='" + getWeight() + "'" +
            ", eyeColor='" + getEyeColor() + "'" +
            ", demographic='" + getDemographic() + "'" +
            ", lastKnownLocation='" + getLastKnownLocation() + "'" +
            ", lastSeen='" + getLastSeen() + "'" +
            ", serviceProvider='" + getServiceProvider() + "'" +
            ", serviceProviderAccountNumber='" + getServiceProviderAccountNumber() + "'" +
            ", complaintNumber='" + getComplaintNumber() + "'" +
            ", reportNumber='" + getReportNumber() + "'" +
            ", investigatorName='" + getInvestigatorName() + "'" +
            ", investigatorEmail='" + getInvestigatorEmail() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", photos='" + getPhotos() + "'" +
            ", photosContentType='" + photosContentType + "'" +
            ", additionalInformation='" + getAdditionalInformation() + "'" +
            "}";
    }
}
