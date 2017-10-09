package org.jhipster.blog.service.impl;

import org.jhipster.blog.service.ReportService;
import org.jhipster.blog.domain.Report;
import org.jhipster.blog.repository.ReportRepository;
import org.jhipster.blog.repository.search.ReportSearchRepository;
import org.jhipster.blog.service.dto.ReportDTO;
import org.jhipster.blog.service.mapper.ReportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Report.
 */
@Service
@Transactional
public class ReportServiceImpl implements ReportService{

    private final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);

    private final ReportRepository reportRepository;

    private final ReportMapper reportMapper;

    private final ReportSearchRepository reportSearchRepository;

    public ReportServiceImpl(ReportRepository reportRepository, ReportMapper reportMapper, ReportSearchRepository reportSearchRepository) {
        this.reportRepository = reportRepository;
        this.reportMapper = reportMapper;
        this.reportSearchRepository = reportSearchRepository;
    }

    /**
     * Save a report.
     *
     * @param reportDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ReportDTO save(ReportDTO reportDTO) {
        log.debug("Request to save Report : {}", reportDTO);
        Report report = reportMapper.toEntity(reportDTO);
        report = reportRepository.save(report);
        ReportDTO result = reportMapper.toDto(report);
        reportSearchRepository.save(report);
        return result;
    }

    /**
     *  Get all the reports.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ReportDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Reports");
        return reportRepository.findAll(pageable)
            .map(reportMapper::toDto);
    }

    /**
     *  Get one report by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ReportDTO findOne(Long id) {
        log.debug("Request to get Report : {}", id);
        Report report = reportRepository.findOne(id);
        return reportMapper.toDto(report);
    }

    /**
     *  Delete the  report by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Report : {}", id);
        reportRepository.delete(id);
        reportSearchRepository.delete(id);
    }

    /**
     * Search for the report corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ReportDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Reports for query {}", query);
        Page<Report> result = reportSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(reportMapper::toDto);
    }
}
