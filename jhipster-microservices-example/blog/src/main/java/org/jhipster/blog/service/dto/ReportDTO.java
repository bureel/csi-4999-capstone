package org.jhipster.blog.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Report entity.
 */
public class ReportDTO implements Serializable {

    private Long id;

    private String victimName;

    private String parentName;

    private String parentPhoneNumber;

    private ZonedDateTime dateOfBirth;

    private String phoneNumber;

    private String height;

    private Long weight;

    private String eyeColor;

    private String demographic;

    private String lastKnownLocation;

    private ZonedDateTime timeOfLastSeen;

    private String serviceProvider;

    private String masterAccountNumber;

    private String complaintNumber;

    private String reportNumber;

    private String investigatorEmail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVictimName() {
        return victimName;
    }

    public void setVictimName(String victimName) {
        this.victimName = victimName;
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

    public ZonedDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(ZonedDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
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

    public ZonedDateTime getTimeOfLastSeen() {
        return timeOfLastSeen;
    }

    public void setTimeOfLastSeen(ZonedDateTime timeOfLastSeen) {
        this.timeOfLastSeen = timeOfLastSeen;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getMasterAccountNumber() {
        return masterAccountNumber;
    }

    public void setMasterAccountNumber(String masterAccountNumber) {
        this.masterAccountNumber = masterAccountNumber;
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

    public String getInvestigatorEmail() {
        return investigatorEmail;
    }

    public void setInvestigatorEmail(String investigatorEmail) {
        this.investigatorEmail = investigatorEmail;
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
