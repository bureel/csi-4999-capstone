package org.jhipster.blog.service.mapper;

import org.jhipster.blog.domain.*;
import org.jhipster.blog.service.dto.ReporteeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Reportee and its DTO ReporteeDTO.
 */
@Mapper(componentModel = "spring", uses = {ReportMapper.class, })
public interface ReporteeMapper extends EntityMapper <ReporteeDTO, Reportee> {

    @Mapping(source = "report.id", target = "reportId")
    ReporteeDTO toDto(Reportee reportee); 

    @Mapping(source = "reportId", target = "report")
    Reportee toEntity(ReporteeDTO reporteeDTO); 
    default Reportee fromId(Long id) {
        if (id == null) {
            return null;
        }
        Reportee reportee = new Reportee();
        reportee.setId(id);
        return reportee;
    }
}
