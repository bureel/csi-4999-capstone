package org.jhipster.blog.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Reportee entity.
 */
public class ReporteeDTO implements Serializable {

    private Long id;

    private String parentName;

    private String parentPhoneNumber;

    private String parentEmail;

    private Long reportId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReporteeDTO reporteeDTO = (ReporteeDTO) o;
        if(reporteeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reporteeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReporteeDTO{" +
            "id=" + getId() +
            ", parentName='" + getParentName() + "'" +
            ", parentPhoneNumber='" + getParentPhoneNumber() + "'" +
            ", parentEmail='" + getParentEmail() + "'" +
            "}";
    }
}
