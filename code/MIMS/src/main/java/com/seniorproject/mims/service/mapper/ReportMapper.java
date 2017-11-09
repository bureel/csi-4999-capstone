package com.seniorproject.mims.service.mapper;

import com.seniorproject.mims.domain.*;
import com.seniorproject.mims.service.dto.ReportDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Report and its DTO ReportDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface ReportMapper extends EntityMapper <ReportDTO, Report> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    ReportDTO toDto(Report report); 

    @Mapping(source = "userId", target = "user")
    Report toEntity(ReportDTO reportDTO); 
    default Report fromId(Long id) {
        if (id == null) {
            return null;
        }
        Report report = new Report();
        report.setId(id);
        return report;
    }
}
