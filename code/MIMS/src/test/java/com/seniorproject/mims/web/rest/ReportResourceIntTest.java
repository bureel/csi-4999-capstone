package com.seniorproject.mims.web.rest;

import com.seniorproject.mims.MimsApp;

import com.seniorproject.mims.domain.Report;
import com.seniorproject.mims.repository.ReportRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
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

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_RESOLUTION = "AAAAAAAAAA";
    private static final String UPDATED_RESOLUTION = "BBBBBBBBBB";

    private static final String DEFAULT_VICTIM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VICTIM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VICTIM_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VICTIM_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_VICTIM_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_VICTIM_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_EMAIL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_HEIGHT = "AAAAAAAAAA";
    private static final String UPDATED_HEIGHT = "BBBBBBBBBB";

    private static final Double DEFAULT_WEIGHT = 1D;
    private static final Double UPDATED_WEIGHT = 2D;

    private static final String DEFAULT_EYE_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_EYE_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_DEMOGRAPHIC = "AAAAAAAAAA";
    private static final String UPDATED_DEMOGRAPHIC = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_KNOWN_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LAST_KNOWN_LOCATION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_SEEN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_SEEN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_SERVICE_PROVIDER = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_PROVIDER = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_PROVIDER_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_PROVIDER_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLAINT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_COMPLAINT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_REPORT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REPORT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_INVESTIGATOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_INVESTIGATOR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INVESTIGATOR_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_INVESTIGATOR_EMAIL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final byte[] DEFAULT_PHOTOS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTOS = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PHOTOS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTOS_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ADDITIONAL_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_INFORMATION = "BBBBBBBBBB";

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportMapper reportMapper;

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
        final ReportResource reportResource = new ReportResource(reportRepository, reportMapper);
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
            .status(DEFAULT_STATUS)
            .resolution(DEFAULT_RESOLUTION)
            .victimName(DEFAULT_VICTIM_NAME)
            .victimPhoneNumber(DEFAULT_VICTIM_PHONE_NUMBER)
            .victimEmail(DEFAULT_VICTIM_EMAIL)
            .parentName(DEFAULT_PARENT_NAME)
            .parentPhoneNumber(DEFAULT_PARENT_PHONE_NUMBER)
            .parentEmail(DEFAULT_PARENT_EMAIL)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .height(DEFAULT_HEIGHT)
            .weight(DEFAULT_WEIGHT)
            .eyeColor(DEFAULT_EYE_COLOR)
            .demographic(DEFAULT_DEMOGRAPHIC)
            .lastKnownLocation(DEFAULT_LAST_KNOWN_LOCATION)
            .lastSeen(DEFAULT_LAST_SEEN)
            .serviceProvider(DEFAULT_SERVICE_PROVIDER)
            .serviceProviderAccountNumber(DEFAULT_SERVICE_PROVIDER_ACCOUNT_NUMBER)
            .complaintNumber(DEFAULT_COMPLAINT_NUMBER)
            .reportNumber(DEFAULT_REPORT_NUMBER)
            .investigatorName(DEFAULT_INVESTIGATOR_NAME)
            .investigatorEmail(DEFAULT_INVESTIGATOR_EMAIL)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .photos(DEFAULT_PHOTOS)
            .photosContentType(DEFAULT_PHOTOS_CONTENT_TYPE)
            .additionalInformation(DEFAULT_ADDITIONAL_INFORMATION);
        return report;
    }

    @Before
    public void initTest() {
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
        assertThat(testReport.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testReport.getResolution()).isEqualTo(DEFAULT_RESOLUTION);
        assertThat(testReport.getVictimName()).isEqualTo(DEFAULT_VICTIM_NAME);
        assertThat(testReport.getVictimPhoneNumber()).isEqualTo(DEFAULT_VICTIM_PHONE_NUMBER);
        assertThat(testReport.getVictimEmail()).isEqualTo(DEFAULT_VICTIM_EMAIL);
        assertThat(testReport.getParentName()).isEqualTo(DEFAULT_PARENT_NAME);
        assertThat(testReport.getParentPhoneNumber()).isEqualTo(DEFAULT_PARENT_PHONE_NUMBER);
        assertThat(testReport.getParentEmail()).isEqualTo(DEFAULT_PARENT_EMAIL);
        assertThat(testReport.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testReport.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testReport.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testReport.getEyeColor()).isEqualTo(DEFAULT_EYE_COLOR);
        assertThat(testReport.getDemographic()).isEqualTo(DEFAULT_DEMOGRAPHIC);
        assertThat(testReport.getLastKnownLocation()).isEqualTo(DEFAULT_LAST_KNOWN_LOCATION);
        assertThat(testReport.getLastSeen()).isEqualTo(DEFAULT_LAST_SEEN);
        assertThat(testReport.getServiceProvider()).isEqualTo(DEFAULT_SERVICE_PROVIDER);
        assertThat(testReport.getServiceProviderAccountNumber()).isEqualTo(DEFAULT_SERVICE_PROVIDER_ACCOUNT_NUMBER);
        assertThat(testReport.getComplaintNumber()).isEqualTo(DEFAULT_COMPLAINT_NUMBER);
        assertThat(testReport.getReportNumber()).isEqualTo(DEFAULT_REPORT_NUMBER);
        assertThat(testReport.getInvestigatorName()).isEqualTo(DEFAULT_INVESTIGATOR_NAME);
        assertThat(testReport.getInvestigatorEmail()).isEqualTo(DEFAULT_INVESTIGATOR_EMAIL);
        assertThat(testReport.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testReport.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testReport.getPhotos()).isEqualTo(DEFAULT_PHOTOS);
        assertThat(testReport.getPhotosContentType()).isEqualTo(DEFAULT_PHOTOS_CONTENT_TYPE);
        assertThat(testReport.getAdditionalInformation()).isEqualTo(DEFAULT_ADDITIONAL_INFORMATION);
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
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].resolution").value(hasItem(DEFAULT_RESOLUTION.toString())))
            .andExpect(jsonPath("$.[*].victimName").value(hasItem(DEFAULT_VICTIM_NAME.toString())))
            .andExpect(jsonPath("$.[*].victimPhoneNumber").value(hasItem(DEFAULT_VICTIM_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].victimEmail").value(hasItem(DEFAULT_VICTIM_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].parentName").value(hasItem(DEFAULT_PARENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].parentPhoneNumber").value(hasItem(DEFAULT_PARENT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].parentEmail").value(hasItem(DEFAULT_PARENT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.toString())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].eyeColor").value(hasItem(DEFAULT_EYE_COLOR.toString())))
            .andExpect(jsonPath("$.[*].demographic").value(hasItem(DEFAULT_DEMOGRAPHIC.toString())))
            .andExpect(jsonPath("$.[*].lastKnownLocation").value(hasItem(DEFAULT_LAST_KNOWN_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].lastSeen").value(hasItem(sameInstant(DEFAULT_LAST_SEEN))))
            .andExpect(jsonPath("$.[*].serviceProvider").value(hasItem(DEFAULT_SERVICE_PROVIDER.toString())))
            .andExpect(jsonPath("$.[*].serviceProviderAccountNumber").value(hasItem(DEFAULT_SERVICE_PROVIDER_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].complaintNumber").value(hasItem(DEFAULT_COMPLAINT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].reportNumber").value(hasItem(DEFAULT_REPORT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].investigatorName").value(hasItem(DEFAULT_INVESTIGATOR_NAME.toString())))
            .andExpect(jsonPath("$.[*].investigatorEmail").value(hasItem(DEFAULT_INVESTIGATOR_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].photosContentType").value(hasItem(DEFAULT_PHOTOS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photos").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTOS))))
            .andExpect(jsonPath("$.[*].additionalInformation").value(hasItem(DEFAULT_ADDITIONAL_INFORMATION.toString())));
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
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.resolution").value(DEFAULT_RESOLUTION.toString()))
            .andExpect(jsonPath("$.victimName").value(DEFAULT_VICTIM_NAME.toString()))
            .andExpect(jsonPath("$.victimPhoneNumber").value(DEFAULT_VICTIM_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.victimEmail").value(DEFAULT_VICTIM_EMAIL.toString()))
            .andExpect(jsonPath("$.parentName").value(DEFAULT_PARENT_NAME.toString()))
            .andExpect(jsonPath("$.parentPhoneNumber").value(DEFAULT_PARENT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.parentEmail").value(DEFAULT_PARENT_EMAIL.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.toString()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.eyeColor").value(DEFAULT_EYE_COLOR.toString()))
            .andExpect(jsonPath("$.demographic").value(DEFAULT_DEMOGRAPHIC.toString()))
            .andExpect(jsonPath("$.lastKnownLocation").value(DEFAULT_LAST_KNOWN_LOCATION.toString()))
            .andExpect(jsonPath("$.lastSeen").value(sameInstant(DEFAULT_LAST_SEEN)))
            .andExpect(jsonPath("$.serviceProvider").value(DEFAULT_SERVICE_PROVIDER.toString()))
            .andExpect(jsonPath("$.serviceProviderAccountNumber").value(DEFAULT_SERVICE_PROVIDER_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.complaintNumber").value(DEFAULT_COMPLAINT_NUMBER.toString()))
            .andExpect(jsonPath("$.reportNumber").value(DEFAULT_REPORT_NUMBER.toString()))
            .andExpect(jsonPath("$.investigatorName").value(DEFAULT_INVESTIGATOR_NAME.toString()))
            .andExpect(jsonPath("$.investigatorEmail").value(DEFAULT_INVESTIGATOR_EMAIL.toString()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.photosContentType").value(DEFAULT_PHOTOS_CONTENT_TYPE))
            .andExpect(jsonPath("$.photos").value(Base64Utils.encodeToString(DEFAULT_PHOTOS)))
            .andExpect(jsonPath("$.additionalInformation").value(DEFAULT_ADDITIONAL_INFORMATION.toString()));
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
        int databaseSizeBeforeUpdate = reportRepository.findAll().size();

        // Update the report
        Report updatedReport = reportRepository.findOne(report.getId());
        updatedReport
            .status(UPDATED_STATUS)
            .resolution(UPDATED_RESOLUTION)
            .victimName(UPDATED_VICTIM_NAME)
            .victimPhoneNumber(UPDATED_VICTIM_PHONE_NUMBER)
            .victimEmail(UPDATED_VICTIM_EMAIL)
            .parentName(UPDATED_PARENT_NAME)
            .parentPhoneNumber(UPDATED_PARENT_PHONE_NUMBER)
            .parentEmail(UPDATED_PARENT_EMAIL)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .height(UPDATED_HEIGHT)
            .weight(UPDATED_WEIGHT)
            .eyeColor(UPDATED_EYE_COLOR)
            .demographic(UPDATED_DEMOGRAPHIC)
            .lastKnownLocation(UPDATED_LAST_KNOWN_LOCATION)
            .lastSeen(UPDATED_LAST_SEEN)
            .serviceProvider(UPDATED_SERVICE_PROVIDER)
            .serviceProviderAccountNumber(UPDATED_SERVICE_PROVIDER_ACCOUNT_NUMBER)
            .complaintNumber(UPDATED_COMPLAINT_NUMBER)
            .reportNumber(UPDATED_REPORT_NUMBER)
            .investigatorName(UPDATED_INVESTIGATOR_NAME)
            .investigatorEmail(UPDATED_INVESTIGATOR_EMAIL)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .photos(UPDATED_PHOTOS)
            .photosContentType(UPDATED_PHOTOS_CONTENT_TYPE)
            .additionalInformation(UPDATED_ADDITIONAL_INFORMATION);
        ReportDTO reportDTO = reportMapper.toDto(updatedReport);

        restReportMockMvc.perform(put("/api/reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportDTO)))
            .andExpect(status().isOk());

        // Validate the Report in the database
        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeUpdate);
        Report testReport = reportList.get(reportList.size() - 1);
        assertThat(testReport.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testReport.getResolution()).isEqualTo(UPDATED_RESOLUTION);
        assertThat(testReport.getVictimName()).isEqualTo(UPDATED_VICTIM_NAME);
        assertThat(testReport.getVictimPhoneNumber()).isEqualTo(UPDATED_VICTIM_PHONE_NUMBER);
        assertThat(testReport.getVictimEmail()).isEqualTo(UPDATED_VICTIM_EMAIL);
        assertThat(testReport.getParentName()).isEqualTo(UPDATED_PARENT_NAME);
        assertThat(testReport.getParentPhoneNumber()).isEqualTo(UPDATED_PARENT_PHONE_NUMBER);
        assertThat(testReport.getParentEmail()).isEqualTo(UPDATED_PARENT_EMAIL);
        assertThat(testReport.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testReport.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testReport.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testReport.getEyeColor()).isEqualTo(UPDATED_EYE_COLOR);
        assertThat(testReport.getDemographic()).isEqualTo(UPDATED_DEMOGRAPHIC);
        assertThat(testReport.getLastKnownLocation()).isEqualTo(UPDATED_LAST_KNOWN_LOCATION);
        assertThat(testReport.getLastSeen()).isEqualTo(UPDATED_LAST_SEEN);
        assertThat(testReport.getServiceProvider()).isEqualTo(UPDATED_SERVICE_PROVIDER);
        assertThat(testReport.getServiceProviderAccountNumber()).isEqualTo(UPDATED_SERVICE_PROVIDER_ACCOUNT_NUMBER);
        assertThat(testReport.getComplaintNumber()).isEqualTo(UPDATED_COMPLAINT_NUMBER);
        assertThat(testReport.getReportNumber()).isEqualTo(UPDATED_REPORT_NUMBER);
        assertThat(testReport.getInvestigatorName()).isEqualTo(UPDATED_INVESTIGATOR_NAME);
        assertThat(testReport.getInvestigatorEmail()).isEqualTo(UPDATED_INVESTIGATOR_EMAIL);
        assertThat(testReport.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testReport.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testReport.getPhotos()).isEqualTo(UPDATED_PHOTOS);
        assertThat(testReport.getPhotosContentType()).isEqualTo(UPDATED_PHOTOS_CONTENT_TYPE);
        assertThat(testReport.getAdditionalInformation()).isEqualTo(UPDATED_ADDITIONAL_INFORMATION);
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
        int databaseSizeBeforeDelete = reportRepository.findAll().size();

        // Get the report
        restReportMockMvc.perform(delete("/api/reports/{id}", report.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeDelete - 1);
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
