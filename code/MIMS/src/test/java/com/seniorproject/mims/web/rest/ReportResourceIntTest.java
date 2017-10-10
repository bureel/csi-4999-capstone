package com.seniorproject.mims.web.rest;

import com.seniorproject.mims.MimsApp;

import com.seniorproject.mims.domain.Report;
import com.seniorproject.mims.repository.ReportRepository;
import com.seniorproject.mims.service.ReportService;
import com.seniorproject.mims.repository.search.ReportSearchRepository;
import com.seniorproject.mims.service.dto.ReportDTO;
import com.seniorproject.mims.service.mapper.ReportMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.seniorproject.mims.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ReportResource REST controller.
 *
 * @see ReportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MimsApp.class)
public class ReportResourceIntTest {

    private static final String DEFAULT_VICTIM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VICTIM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_PHONE_NUMBER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_OF_BIRTH = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_OF_BIRTH = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_HEIGHT = "AAAAAAAAAA";
    private static final String UPDATED_HEIGHT = "BBBBBBBBBB";

    private static final Long DEFAULT_WEIGHT = 1L;
    private static final Long UPDATED_WEIGHT = 2L;

    private static final String DEFAULT_EYE_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_EYE_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_DEMOGRAPHIC = "AAAAAAAAAA";
    private static final String UPDATED_DEMOGRAPHIC = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_KNOWN_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LAST_KNOWN_LOCATION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIME_OF_LAST_SEEN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME_OF_LAST_SEEN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_SERVICE_PROVIDER = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_PROVIDER = "BBBBBBBBBB";

    private static final String DEFAULT_MASTER_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MASTER_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLAINT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_COMPLAINT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_REPORT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REPORT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_INVESTIGATOR_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_INVESTIGATOR_EMAIL = "BBBBBBBBBB";

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportSearchRepository reportSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReportMockMvc;

    private Report report;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReportResource reportResource = new ReportResource(reportService);
        this.restReportMockMvc = MockMvcBuilders.standaloneSetup(reportResource)
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
    public static Report createEntity(EntityManager em) {
        Report report = new Report()
            .victimName(DEFAULT_VICTIM_NAME)
            .parentName(DEFAULT_PARENT_NAME)
            .parentPhoneNumber(DEFAULT_PARENT_PHONE_NUMBER)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .height(DEFAULT_HEIGHT)
            .weight(DEFAULT_WEIGHT)
            .eyeColor(DEFAULT_EYE_COLOR)
            .demographic(DEFAULT_DEMOGRAPHIC)
            .lastKnownLocation(DEFAULT_LAST_KNOWN_LOCATION)
            .timeOfLastSeen(DEFAULT_TIME_OF_LAST_SEEN)
            .serviceProvider(DEFAULT_SERVICE_PROVIDER)
            .masterAccountNumber(DEFAULT_MASTER_ACCOUNT_NUMBER)
            .complaintNumber(DEFAULT_COMPLAINT_NUMBER)
            .reportNumber(DEFAULT_REPORT_NUMBER)
            .investigatorEmail(DEFAULT_INVESTIGATOR_EMAIL);
        return report;
    }

    @Before
    public void initTest() {
        reportSearchRepository.deleteAll();
        report = createEntity(em);
    }

    @Test
    @Transactional
    public void createReport() throws Exception {
        int databaseSizeBeforeCreate = reportRepository.findAll().size();

        // Create the Report
        ReportDTO reportDTO = reportMapper.toDto(report);
        restReportMockMvc.perform(post("/api/reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportDTO)))
            .andExpect(status().isCreated());

        // Validate the Report in the database
        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeCreate + 1);
        Report testReport = reportList.get(reportList.size() - 1);
        assertThat(testReport.getVictimName()).isEqualTo(DEFAULT_VICTIM_NAME);
        assertThat(testReport.getParentName()).isEqualTo(DEFAULT_PARENT_NAME);
        assertThat(testReport.getParentPhoneNumber()).isEqualTo(DEFAULT_PARENT_PHONE_NUMBER);
        assertThat(testReport.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testReport.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testReport.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testReport.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testReport.getEyeColor()).isEqualTo(DEFAULT_EYE_COLOR);
        assertThat(testReport.getDemographic()).isEqualTo(DEFAULT_DEMOGRAPHIC);
        assertThat(testReport.getLastKnownLocation()).isEqualTo(DEFAULT_LAST_KNOWN_LOCATION);
        assertThat(testReport.getTimeOfLastSeen()).isEqualTo(DEFAULT_TIME_OF_LAST_SEEN);
        assertThat(testReport.getServiceProvider()).isEqualTo(DEFAULT_SERVICE_PROVIDER);
        assertThat(testReport.getMasterAccountNumber()).isEqualTo(DEFAULT_MASTER_ACCOUNT_NUMBER);
        assertThat(testReport.getComplaintNumber()).isEqualTo(DEFAULT_COMPLAINT_NUMBER);
        assertThat(testReport.getReportNumber()).isEqualTo(DEFAULT_REPORT_NUMBER);
        assertThat(testReport.getInvestigatorEmail()).isEqualTo(DEFAULT_INVESTIGATOR_EMAIL);

        // Validate the Report in Elasticsearch
        Report reportEs = reportSearchRepository.findOne(testReport.getId());
        assertThat(reportEs).isEqualToComparingFieldByField(testReport);
    }

    @Test
    @Transactional
    public void createReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reportRepository.findAll().size();

        // Create the Report with an existing ID
        report.setId(1L);
        ReportDTO reportDTO = reportMapper.toDto(report);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportMockMvc.perform(post("/api/reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Report in the database
        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReports() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList
        restReportMockMvc.perform(get("/api/reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(report.getId().intValue())))
            .andExpect(jsonPath("$.[*].victimName").value(hasItem(DEFAULT_VICTIM_NAME.toString())))
            .andExpect(jsonPath("$.[*].parentName").value(hasItem(DEFAULT_PARENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].parentPhoneNumber").value(hasItem(DEFAULT_PARENT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(sameInstant(DEFAULT_DATE_OF_BIRTH))))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.toString())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].eyeColor").value(hasItem(DEFAULT_EYE_COLOR.toString())))
            .andExpect(jsonPath("$.[*].demographic").value(hasItem(DEFAULT_DEMOGRAPHIC.toString())))
            .andExpect(jsonPath("$.[*].lastKnownLocation").value(hasItem(DEFAULT_LAST_KNOWN_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].timeOfLastSeen").value(hasItem(sameInstant(DEFAULT_TIME_OF_LAST_SEEN))))
            .andExpect(jsonPath("$.[*].serviceProvider").value(hasItem(DEFAULT_SERVICE_PROVIDER.toString())))
            .andExpect(jsonPath("$.[*].masterAccountNumber").value(hasItem(DEFAULT_MASTER_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].complaintNumber").value(hasItem(DEFAULT_COMPLAINT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].reportNumber").value(hasItem(DEFAULT_REPORT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].investigatorEmail").value(hasItem(DEFAULT_INVESTIGATOR_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getReport() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get the report
        restReportMockMvc.perform(get("/api/reports/{id}", report.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(report.getId().intValue()))
            .andExpect(jsonPath("$.victimName").value(DEFAULT_VICTIM_NAME.toString()))
            .andExpect(jsonPath("$.parentName").value(DEFAULT_PARENT_NAME.toString()))
            .andExpect(jsonPath("$.parentPhoneNumber").value(DEFAULT_PARENT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(sameInstant(DEFAULT_DATE_OF_BIRTH)))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.toString()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.intValue()))
            .andExpect(jsonPath("$.eyeColor").value(DEFAULT_EYE_COLOR.toString()))
            .andExpect(jsonPath("$.demographic").value(DEFAULT_DEMOGRAPHIC.toString()))
            .andExpect(jsonPath("$.lastKnownLocation").value(DEFAULT_LAST_KNOWN_LOCATION.toString()))
            .andExpect(jsonPath("$.timeOfLastSeen").value(sameInstant(DEFAULT_TIME_OF_LAST_SEEN)))
            .andExpect(jsonPath("$.serviceProvider").value(DEFAULT_SERVICE_PROVIDER.toString()))
            .andExpect(jsonPath("$.masterAccountNumber").value(DEFAULT_MASTER_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.complaintNumber").value(DEFAULT_COMPLAINT_NUMBER.toString()))
            .andExpect(jsonPath("$.reportNumber").value(DEFAULT_REPORT_NUMBER.toString()))
            .andExpect(jsonPath("$.investigatorEmail").value(DEFAULT_INVESTIGATOR_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReport() throws Exception {
        // Get the report
        restReportMockMvc.perform(get("/api/reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReport() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);
        reportSearchRepository.save(report);
        int databaseSizeBeforeUpdate = reportRepository.findAll().size();

        // Update the report
        Report updatedReport = reportRepository.findOne(report.getId());
        updatedReport
            .victimName(UPDATED_VICTIM_NAME)
            .parentName(UPDATED_PARENT_NAME)
            .parentPhoneNumber(UPDATED_PARENT_PHONE_NUMBER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .height(UPDATED_HEIGHT)
            .weight(UPDATED_WEIGHT)
            .eyeColor(UPDATED_EYE_COLOR)
            .demographic(UPDATED_DEMOGRAPHIC)
            .lastKnownLocation(UPDATED_LAST_KNOWN_LOCATION)
            .timeOfLastSeen(UPDATED_TIME_OF_LAST_SEEN)
            .serviceProvider(UPDATED_SERVICE_PROVIDER)
            .masterAccountNumber(UPDATED_MASTER_ACCOUNT_NUMBER)
            .complaintNumber(UPDATED_COMPLAINT_NUMBER)
            .reportNumber(UPDATED_REPORT_NUMBER)
            .investigatorEmail(UPDATED_INVESTIGATOR_EMAIL);
        ReportDTO reportDTO = reportMapper.toDto(updatedReport);

        restReportMockMvc.perform(put("/api/reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportDTO)))
            .andExpect(status().isOk());

        // Validate the Report in the database
        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeUpdate);
        Report testReport = reportList.get(reportList.size() - 1);
        assertThat(testReport.getVictimName()).isEqualTo(UPDATED_VICTIM_NAME);
        assertThat(testReport.getParentName()).isEqualTo(UPDATED_PARENT_NAME);
        assertThat(testReport.getParentPhoneNumber()).isEqualTo(UPDATED_PARENT_PHONE_NUMBER);
        assertThat(testReport.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testReport.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testReport.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testReport.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testReport.getEyeColor()).isEqualTo(UPDATED_EYE_COLOR);
        assertThat(testReport.getDemographic()).isEqualTo(UPDATED_DEMOGRAPHIC);
        assertThat(testReport.getLastKnownLocation()).isEqualTo(UPDATED_LAST_KNOWN_LOCATION);
        assertThat(testReport.getTimeOfLastSeen()).isEqualTo(UPDATED_TIME_OF_LAST_SEEN);
        assertThat(testReport.getServiceProvider()).isEqualTo(UPDATED_SERVICE_PROVIDER);
        assertThat(testReport.getMasterAccountNumber()).isEqualTo(UPDATED_MASTER_ACCOUNT_NUMBER);
        assertThat(testReport.getComplaintNumber()).isEqualTo(UPDATED_COMPLAINT_NUMBER);
        assertThat(testReport.getReportNumber()).isEqualTo(UPDATED_REPORT_NUMBER);
        assertThat(testReport.getInvestigatorEmail()).isEqualTo(UPDATED_INVESTIGATOR_EMAIL);

        // Validate the Report in Elasticsearch
        Report reportEs = reportSearchRepository.findOne(testReport.getId());
        assertThat(reportEs).isEqualToComparingFieldByField(testReport);
    }

    @Test
    @Transactional
    public void updateNonExistingReport() throws Exception {
        int databaseSizeBeforeUpdate = reportRepository.findAll().size();

        // Create the Report
        ReportDTO reportDTO = reportMapper.toDto(report);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReportMockMvc.perform(put("/api/reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportDTO)))
            .andExpect(status().isCreated());

        // Validate the Report in the database
        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReport() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);
        reportSearchRepository.save(report);
        int databaseSizeBeforeDelete = reportRepository.findAll().size();

        // Get the report
        restReportMockMvc.perform(delete("/api/reports/{id}", report.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean reportExistsInEs = reportSearchRepository.exists(report.getId());
        assertThat(reportExistsInEs).isFalse();

        // Validate the database is empty
        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchReport() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);
        reportSearchRepository.save(report);

        // Search the report
        restReportMockMvc.perform(get("/api/_search/reports?query=id:" + report.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(report.getId().intValue())))
            .andExpect(jsonPath("$.[*].victimName").value(hasItem(DEFAULT_VICTIM_NAME.toString())))
            .andExpect(jsonPath("$.[*].parentName").value(hasItem(DEFAULT_PARENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].parentPhoneNumber").value(hasItem(DEFAULT_PARENT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(sameInstant(DEFAULT_DATE_OF_BIRTH))))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.toString())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].eyeColor").value(hasItem(DEFAULT_EYE_COLOR.toString())))
            .andExpect(jsonPath("$.[*].demographic").value(hasItem(DEFAULT_DEMOGRAPHIC.toString())))
            .andExpect(jsonPath("$.[*].lastKnownLocation").value(hasItem(DEFAULT_LAST_KNOWN_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].timeOfLastSeen").value(hasItem(sameInstant(DEFAULT_TIME_OF_LAST_SEEN))))
            .andExpect(jsonPath("$.[*].serviceProvider").value(hasItem(DEFAULT_SERVICE_PROVIDER.toString())))
            .andExpect(jsonPath("$.[*].masterAccountNumber").value(hasItem(DEFAULT_MASTER_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].complaintNumber").value(hasItem(DEFAULT_COMPLAINT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].reportNumber").value(hasItem(DEFAULT_REPORT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].investigatorEmail").value(hasItem(DEFAULT_INVESTIGATOR_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Report.class);
        Report report1 = new Report();
        report1.setId(1L);
        Report report2 = new Report();
        report2.setId(report1.getId());
        assertThat(report1).isEqualTo(report2);
        report2.setId(2L);
        assertThat(report1).isNotEqualTo(report2);
        report1.setId(null);
        assertThat(report1).isNotEqualTo(report2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportDTO.class);
        ReportDTO reportDTO1 = new ReportDTO();
        reportDTO1.setId(1L);
        ReportDTO reportDTO2 = new ReportDTO();
        assertThat(reportDTO1).isNotEqualTo(reportDTO2);
        reportDTO2.setId(reportDTO1.getId());
        assertThat(reportDTO1).isEqualTo(reportDTO2);
        reportDTO2.setId(2L);
        assertThat(reportDTO1).isNotEqualTo(reportDTO2);
        reportDTO1.setId(null);
        assertThat(reportDTO1).isNotEqualTo(reportDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(reportMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(reportMapper.fromId(null)).isNull();
    }
}
