package com.seniorproject.mims.web.rest;

import com.seniorproject.mims.MimsApp;

import com.seniorproject.mims.domain.Reportee;
import com.seniorproject.mims.repository.ReporteeRepository;
import com.seniorproject.mims.repository.search.ReporteeSearchRepository;
import com.seniorproject.mims.service.dto.ReporteeDTO;
import com.seniorproject.mims.service.mapper.ReporteeMapper;
import com.seniorproject.mims.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ReporteeResource REST controller.
 *
 * @see ReporteeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MimsApp.class)
public class ReporteeResourceIntTest {

    private static final String DEFAULT_PARENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_EMAIL = "BBBBBBBBBB";

    @Autowired
    private ReporteeRepository reporteeRepository;

    @Autowired
    private ReporteeMapper reporteeMapper;

    @Autowired
    private ReporteeSearchRepository reporteeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReporteeMockMvc;

    private Reportee reportee;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReporteeResource reporteeResource = new ReporteeResource(reporteeRepository, reporteeMapper, reporteeSearchRepository);
        this.restReporteeMockMvc = MockMvcBuilders.standaloneSetup(reporteeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reportee createEntity(EntityManager em) {
        Reportee reportee = new Reportee()
            .parentName(DEFAULT_PARENT_NAME)
            .parentPhoneNumber(DEFAULT_PARENT_PHONE_NUMBER)
            .parentEmail(DEFAULT_PARENT_EMAIL);
        return reportee;
    }

    @Before
    public void initTest() {
        reporteeSearchRepository.deleteAll();
        reportee = createEntity(em);
    }

    @Test
    @Transactional
    public void createReportee() throws Exception {
        int databaseSizeBeforeCreate = reporteeRepository.findAll().size();

        // Create the Reportee
        ReporteeDTO reporteeDTO = reporteeMapper.toDto(reportee);
        restReporteeMockMvc.perform(post("/api/reportees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reporteeDTO)))
            .andExpect(status().isCreated());

        // Validate the Reportee in the database
        List<Reportee> reporteeList = reporteeRepository.findAll();
        assertThat(reporteeList).hasSize(databaseSizeBeforeCreate + 1);
        Reportee testReportee = reporteeList.get(reporteeList.size() - 1);
        assertThat(testReportee.getParentName()).isEqualTo(DEFAULT_PARENT_NAME);
        assertThat(testReportee.getParentPhoneNumber()).isEqualTo(DEFAULT_PARENT_PHONE_NUMBER);
        assertThat(testReportee.getParentEmail()).isEqualTo(DEFAULT_PARENT_EMAIL);

        // Validate the Reportee in Elasticsearch
        Reportee reporteeEs = reporteeSearchRepository.findOne(testReportee.getId());
        assertThat(reporteeEs).isEqualToComparingFieldByField(testReportee);
    }

    @Test
    @Transactional
    public void createReporteeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reporteeRepository.findAll().size();

        // Create the Reportee with an existing ID
        reportee.setId(1L);
        ReporteeDTO reporteeDTO = reporteeMapper.toDto(reportee);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReporteeMockMvc.perform(post("/api/reportees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reporteeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reportee in the database
        List<Reportee> reporteeList = reporteeRepository.findAll();
        assertThat(reporteeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReportees() throws Exception {
        // Initialize the database
        reporteeRepository.saveAndFlush(reportee);

        // Get all the reporteeList
        restReporteeMockMvc.perform(get("/api/reportees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportee.getId().intValue())))
            .andExpect(jsonPath("$.[*].parentName").value(hasItem(DEFAULT_PARENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].parentPhoneNumber").value(hasItem(DEFAULT_PARENT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].parentEmail").value(hasItem(DEFAULT_PARENT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getReportee() throws Exception {
        // Initialize the database
        reporteeRepository.saveAndFlush(reportee);

        // Get the reportee
        restReporteeMockMvc.perform(get("/api/reportees/{id}", reportee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reportee.getId().intValue()))
            .andExpect(jsonPath("$.parentName").value(DEFAULT_PARENT_NAME.toString()))
            .andExpect(jsonPath("$.parentPhoneNumber").value(DEFAULT_PARENT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.parentEmail").value(DEFAULT_PARENT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReportee() throws Exception {
        // Get the reportee
        restReporteeMockMvc.perform(get("/api/reportees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReportee() throws Exception {
        // Initialize the database
        reporteeRepository.saveAndFlush(reportee);
        reporteeSearchRepository.save(reportee);
        int databaseSizeBeforeUpdate = reporteeRepository.findAll().size();

        // Update the reportee
        Reportee updatedReportee = reporteeRepository.findOne(reportee.getId());
        updatedReportee
            .parentName(UPDATED_PARENT_NAME)
            .parentPhoneNumber(UPDATED_PARENT_PHONE_NUMBER)
            .parentEmail(UPDATED_PARENT_EMAIL);
        ReporteeDTO reporteeDTO = reporteeMapper.toDto(updatedReportee);

        restReporteeMockMvc.perform(put("/api/reportees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reporteeDTO)))
            .andExpect(status().isOk());

        // Validate the Reportee in the database
        List<Reportee> reporteeList = reporteeRepository.findAll();
        assertThat(reporteeList).hasSize(databaseSizeBeforeUpdate);
        Reportee testReportee = reporteeList.get(reporteeList.size() - 1);
        assertThat(testReportee.getParentName()).isEqualTo(UPDATED_PARENT_NAME);
        assertThat(testReportee.getParentPhoneNumber()).isEqualTo(UPDATED_PARENT_PHONE_NUMBER);
        assertThat(testReportee.getParentEmail()).isEqualTo(UPDATED_PARENT_EMAIL);

        // Validate the Reportee in Elasticsearch
        Reportee reporteeEs = reporteeSearchRepository.findOne(testReportee.getId());
        assertThat(reporteeEs).isEqualToComparingFieldByField(testReportee);
    }

    @Test
    @Transactional
    public void updateNonExistingReportee() throws Exception {
        int databaseSizeBeforeUpdate = reporteeRepository.findAll().size();

        // Create the Reportee
        ReporteeDTO reporteeDTO = reporteeMapper.toDto(reportee);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReporteeMockMvc.perform(put("/api/reportees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reporteeDTO)))
            .andExpect(status().isCreated());

        // Validate the Reportee in the database
        List<Reportee> reporteeList = reporteeRepository.findAll();
        assertThat(reporteeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReportee() throws Exception {
        // Initialize the database
        reporteeRepository.saveAndFlush(reportee);
        reporteeSearchRepository.save(reportee);
        int databaseSizeBeforeDelete = reporteeRepository.findAll().size();

        // Get the reportee
        restReporteeMockMvc.perform(delete("/api/reportees/{id}", reportee.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean reporteeExistsInEs = reporteeSearchRepository.exists(reportee.getId());
        assertThat(reporteeExistsInEs).isFalse();

        // Validate the database is empty
        List<Reportee> reporteeList = reporteeRepository.findAll();
        assertThat(reporteeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchReportee() throws Exception {
        // Initialize the database
        reporteeRepository.saveAndFlush(reportee);
        reporteeSearchRepository.save(reportee);

        // Search the reportee
        restReporteeMockMvc.perform(get("/api/_search/reportees?query=id:" + reportee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportee.getId().intValue())))
            .andExpect(jsonPath("$.[*].parentName").value(hasItem(DEFAULT_PARENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].parentPhoneNumber").value(hasItem(DEFAULT_PARENT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].parentEmail").value(hasItem(DEFAULT_PARENT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reportee.class);
        Reportee reportee1 = new Reportee();
        reportee1.setId(1L);
        Reportee reportee2 = new Reportee();
        reportee2.setId(reportee1.getId());
        assertThat(reportee1).isEqualTo(reportee2);
        reportee2.setId(2L);
        assertThat(reportee1).isNotEqualTo(reportee2);
        reportee1.setId(null);
        assertThat(reportee1).isNotEqualTo(reportee2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReporteeDTO.class);
        ReporteeDTO reporteeDTO1 = new ReporteeDTO();
        reporteeDTO1.setId(1L);
        ReporteeDTO reporteeDTO2 = new ReporteeDTO();
        assertThat(reporteeDTO1).isNotEqualTo(reporteeDTO2);
        reporteeDTO2.setId(reporteeDTO1.getId());
        assertThat(reporteeDTO1).isEqualTo(reporteeDTO2);
        reporteeDTO2.setId(2L);
        assertThat(reporteeDTO1).isNotEqualTo(reporteeDTO2);
        reporteeDTO1.setId(null);
        assertThat(reporteeDTO1).isNotEqualTo(reporteeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(reporteeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(reporteeMapper.fromId(null)).isNull();
    }
}
