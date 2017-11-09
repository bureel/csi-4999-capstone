package com.seniorproject.mims.service.dto;


import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Report entity.
 */
public class ReportDTO implements Serializable {

    private Long id;

    private String status;

    private String resolution;

    private String victimName;

    private String victimPhoneNumber;

    private String victimEmail;

    private String parentName;

    private String parentPhoneNumber;

    private String parentEmail;

    private LocalDate dateOfBirth;

    private String height;

    private Double weight;

    private String eyeColor;

    private String demographic;

    private String lastKnownLocation;

    private ZonedDateTime lastSeen;

    private String serviceProvider;

    private String serviceProviderAccountNumber;

    private String complaintNumber;

    private String reportNumber;

    private String investigatorName;

    private String investigatorEmail;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    @Lob
    private byte[] photos;
    private String photosContentType;

    private String additionalInformation;

    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getVictimName() {
        return victimName;
    }

    public void setVictimName(String victimName) {
        this.victimName = victimName;
    }

    public String getVictimPhoneNumber() {
        return victimPhoneNumber;
    }

    public void setVictimPhoneNumber(String victimPhoneNumber) {
        this.victimPhoneNumber = victimPhoneNumber;
    }

    public String getVictimEmail() {
        return victimEmail;
    }

    public void setVictimEmail(String victimEmail) {
        this.victimEmail = victimEmail;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentPhoneNumber() {
        return parentPhoneNumber;
    }

    public void setParentPhoneNumber(String parentPhoneNumber) {
        this.parentPhoneNumber = parentPhoneNumber;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getDemographic() {
        return demographic;
    }

    public void setDemographic(String demographic) {
        this.demographic = demographic;
    }

    public String getLastKnownLocation() {
        return lastKnownLocation;
    }

    public void setLastKnownLocation(String lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }

    public ZonedDateTime getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(ZonedDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getServiceProviderAccountNumber() {
        return serviceProviderAccountNumber;
    }

    public void setServiceProviderAccountNumber(String serviceProviderAccountNumber) {
        this.serviceProviderAccountNumber = serviceProviderAccountNumber;
    }

    public String getComplaintNumber() {
        return complaintNumber;
    }

    public void setComplaintNumber(String complaintNumber) {
        this.complaintNumber = complaintNumber;
    }

    public String getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(String reportNumber) {
        this.reportNumber = reportNumber;
    }

    public String getInvestigatorName() {
        return investigatorName;
    }

    public void setInvestigatorName(String investigatorName) {
        this.investigatorName = investigatorName;
    }

    public String getInvestigatorEmail() {
        return investigatorEmail;
    }

    public void setInvestigatorEmail(String investigatorEmail) {
        this.investigatorEmail = investigatorEmail;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public byte[] getPhotos() {
        return photos;
    }

    public void setPhotos(byte[] photos) {
        this.photos = photos;
    }

    public String getPhotosContentType() {
        return photosContentType;
    }

    public void setPhotosContentType(String photosContentType) {
        this.photosContentType = photosContentType;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReportDTO reportDTO = (ReportDTO) o;
        if(reportDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reportDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReportDTO{" +
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
            ", additionalInformation='" + getAdditionalInformation() + "'" +
            "}";
    }
}
