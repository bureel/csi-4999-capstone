package com.seniorproject.mims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.seniorproject.mims.domain.Reportee;

import com.seniorproject.mims.repository.ReporteeRepository;
import com.seniorproject.mims.repository.search.ReporteeSearchRepository;
import com.seniorproject.mims.web.rest.util.HeaderUtil;
import com.seniorproject.mims.service.dto.ReporteeDTO;
import com.seniorproject.mims.service.mapper.ReporteeMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Reportee.
 */
@RestController
@RequestMapping("/api")
public class ReporteeResource {

    private final Logger log = LoggerFactory.getLogger(ReporteeResource.class);

    private static final String ENTITY_NAME = "reportee";

    private final ReporteeRepository reporteeRepository;

    private final ReporteeMapper reporteeMapper;

    private final ReporteeSearchRepository reporteeSearchRepository;

    public ReporteeResource(ReporteeRepository reporteeRepository, ReporteeMapper reporteeMapper, ReporteeSearchRepository reporteeSearchRepository) {
        this.reporteeRepository = reporteeRepository;
        this.reporteeMapper = reporteeMapper;
        this.reporteeSearchRepository = reporteeSearchRepository;
    }

    /**
     * POST  /reportees : Create a new reportee.
     *
     * @param reporteeDTO the reporteeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reporteeDTO, or with status 400 (Bad Request) if the reportee has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reportees")
    @Timed
    public ResponseEntity<ReporteeDTO> createReportee(@RequestBody ReporteeDTO reporteeDTO) throws URISyntaxException {
        log.debug("REST request to save Reportee : {}", reporteeDTO);
        if (reporteeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new reportee cannot already have an ID")).body(null);
        }
        Reportee reportee = reporteeMapper.toEntity(reporteeDTO);
        reportee = reporteeRepository.save(reportee);
        ReporteeDTO result = reporteeMapper.toDto(reportee);
        reporteeSearchRepository.save(reportee);
        return ResponseEntity.created(new URI("/api/reportees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reportees : Updates an existing reportee.
     *
     * @param reporteeDTO the reporteeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reporteeDTO,
     * or with status 400 (Bad Request) if the reporteeDTO is not valid,
     * or with status 500 (Internal Server Error) if the reporteeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reportees")
    @Timed
    public ResponseEntity<ReporteeDTO> updateReportee(@RequestBody ReporteeDTO reporteeDTO) throws URISyntaxException {
        log.debug("REST request to update Reportee : {}", reporteeDTO);
        if (reporteeDTO.getId() == null) {
            return createReportee(reporteeDTO);
        }
        Reportee reportee = reporteeMapper.toEntity(reporteeDTO);
        reportee = reporteeRepository.save(reportee);
        ReporteeDTO result = reporteeMapper.toDto(reportee);
        reporteeSearchRepository.save(reportee);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reporteeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reportees : get all the reportees.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reportees in body
     */
    @GetMapping("/reportees")
    @Timed
    public List<ReporteeDTO> getAllReportees() {
        log.debug("REST request to get all Reportees");
        List<Reportee> reportees = reporteeRepository.findAll();
        return reporteeMapper.toDto(reportees);
        }

    /**
     * GET  /reportees/:id : get the "id" reportee.
     *
     * @param id the id of the reporteeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reporteeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/reportees/{id}")
    @Timed
    public ResponseEntity<ReporteeDTO> getReportee(@PathVariable Long id) {
        log.debug("REST request to get Reportee : {}", id);
        Reportee reportee = reporteeRepository.findOne(id);
        ReporteeDTO reporteeDTO = reporteeMapper.toDto(reportee);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reporteeDTO));
    }

    /**
     * DELETE  /reportees/:id : delete the "id" reportee.
     *
     * @param id the id of the reporteeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reportees/{id}")
    @Timed
    public ResponseEntity<Void> deleteReportee(@PathVariable Long id) {
        log.debug("REST request to delete Reportee : {}", id);
        reporteeRepository.delete(id);
        reporteeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/reportees?query=:query : search for the reportee corresponding
     * to the query.
     *
     * @param query the query of the reportee search
     * @return the result of the search
     */
    @GetMapping("/_search/reportees")
    @Timed
    public List<ReporteeDTO> searchReportees(@RequestParam String query) {
        log.debug("REST request to search Reportees for query {}", query);
        return StreamSupport
            .stream(reporteeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(reporteeMapper::toDto)
            .collect(Collectors.toList());
    }

}
