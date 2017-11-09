package com.seniorproject.mims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.seniorproject.mims.domain.Report;

import com.seniorproject.mims.repository.ReportRepository;
import com.seniorproject.mims.repository.UserRepository;
import com.seniorproject.mims.web.rest.util.HeaderUtil;
import com.seniorproject.mims.web.rest.util.PaginationUtil;
import com.seniorproject.mims.security.SecurityUtils;
import com.seniorproject.mims.service.dto.ReportDTO;
import com.seniorproject.mims.service.mapper.ReportMapper;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Report.
 */
@RestController
@RequestMapping("/api")
public class ReportResource {

    private final Logger log = LoggerFactory.getLogger(ReportResource.class);

    private static final String ENTITY_NAME = "report";

    private final ReportRepository reportRepository;

    private final ReportMapper reportMapper;

    private final UserRepository userRepository;

    public ReportResource(ReportRepository reportRepository, UserRepository userRepository, ReportMapper reportMapper) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.reportMapper = reportMapper;
    }

    /**
     * POST  /reports : Create a new report.
     *
     * @param reportDTO the reportDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reportDTO, or with status 400 (Bad Request) if the report has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reports")
    @Timed
    public ResponseEntity<ReportDTO> createReport(@RequestBody ReportDTO reportDTO) throws URISyntaxException {
        log.debug("REST request to save Report : {}", reportDTO);
        if (reportDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new report cannot already have an ID")).body(null);
        }
        Report report = reportMapper.toEntity(reportDTO);
        report.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
        report = reportRepository.save(report);
        ReportDTO result = reportMapper.toDto(report);
        return ResponseEntity.created(new URI("/api/reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reports : Updates an existing report.
     *
     * @param reportDTO the reportDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reportDTO,
     * or with status 400 (Bad Request) if the reportDTO is not valid,
     * or with status 500 (Internal Server Error) if the reportDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reports")
    @Timed
    public ResponseEntity<ReportDTO> updateReport(@RequestBody ReportDTO reportDTO) throws URISyntaxException {
        log.debug("REST request to update Report : {}", reportDTO);
        if (reportDTO.getId() == null) {
            return createReport(reportDTO);
        }
        Report report = reportMapper.toEntity(reportDTO);
        report = reportRepository.save(report);
        ReportDTO result = reportMapper.toDto(report);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reportDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reports : get all the reports.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of reports in body
     */
    @GetMapping("/reports")
    @Timed
    public ResponseEntity<List<ReportDTO>> getAllReports(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Reports");
        Page<Report> page = reportRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reports");
        return new ResponseEntity<>(reportMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /reports : get all reports for current user.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of reports in body
     */
    @GetMapping("/reports/user")
    @Timed
    public ResponseEntity<List<ReportDTO>> getReportsForUser(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Reports for user");
        Page<Report> page = reportRepository.findByUserIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reports/user");
        return new ResponseEntity<>(reportMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /reports/:id : get the "id" report.
     *
     * @param id the id of the reportDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reportDTO, or with status 404 (Not Found)
     */
    @GetMapping("/reports/{id}")
    @Timed
    public ResponseEntity<ReportDTO> getReport(@PathVariable Long id) {
        log.debug("REST request to get Report : {}", id);
        Report report = reportRepository.findOne(id);
        ReportDTO reportDTO = reportMapper.toDto(report);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reportDTO));
    }

    /**
     * DELETE  /reports/:id : delete the "id" report.
     *
     * @param id the id of the reportDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reports/{id}")
    @Timed
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        log.debug("REST request to delete Report : {}", id);
        reportRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
