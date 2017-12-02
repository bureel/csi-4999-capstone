package com.seniorproject.mims.web.rest;

import com.seniorproject.mims.MimsApp;

import com.seniorproject.mims.domain.Tip;
import com.seniorproject.mims.repository.TipRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TipResource REST controller.
 *
 * @see TipResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MimsApp.class)
public class TipResourceIntTest {

    private static final String DEFAULT_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_INFORMATION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private TipRepository tipRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipMockMvc;

    private Tip tip;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipResource tipResource = new TipResource(tipRepository);
        this.restTipMockMvc = MockMvcBuilders.standaloneSetup(tipResource)
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
    public static Tip createEntity(EntityManager em) {
        Tip tip = new Tip()
            .information(DEFAULT_INFORMATION)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .email(DEFAULT_EMAIL);
        return tip;
    }

    @Before
    public void initTest() {
        tip = createEntity(em);
    }

    @Test
    @Transactional
    public void createTip() throws Exception {
        int databaseSizeBeforeCreate = tipRepository.findAll().size();

        // Create the Tip
        restTipMockMvc.perform(post("/api/tips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tip)))
            .andExpect(status().isCreated());

        // Validate the Tip in the database
        List<Tip> tipList = tipRepository.findAll();
        assertThat(tipList).hasSize(databaseSizeBeforeCreate + 1);
        Tip testTip = tipList.get(tipList.size() - 1);
        assertThat(testTip.getInformation()).isEqualTo(DEFAULT_INFORMATION);
        assertThat(testTip.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testTip.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testTip.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createTipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipRepository.findAll().size();

        // Create the Tip with an existing ID
        tip.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipMockMvc.perform(post("/api/tips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tip)))
            .andExpect(status().isBadRequest());

        // Validate the Tip in the database
        List<Tip> tipList = tipRepository.findAll();
        assertThat(tipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTips() throws Exception {
        // Initialize the database
        tipRepository.saveAndFlush(tip);

        // Get all the tipList
        restTipMockMvc.perform(get("/api/tips?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tip.getId().intValue())))
            .andExpect(jsonPath("$.[*].information").value(hasItem(DEFAULT_INFORMATION.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getTip() throws Exception {
        // Initialize the database
        tipRepository.saveAndFlush(tip);

        // Get the tip
        restTipMockMvc.perform(get("/api/tips/{id}", tip.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tip.getId().intValue()))
            .andExpect(jsonPath("$.information").value(DEFAULT_INFORMATION.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTip() throws Exception {
        // Get the tip
        restTipMockMvc.perform(get("/api/tips/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTip() throws Exception {
        // Initialize the database
        tipRepository.saveAndFlush(tip);
        int databaseSizeBeforeUpdate = tipRepository.findAll().size();

        // Update the tip
        Tip updatedTip = tipRepository.findOne(tip.getId());
        updatedTip
            .information(UPDATED_INFORMATION)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .email(UPDATED_EMAIL);

        restTipMockMvc.perform(put("/api/tips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTip)))
            .andExpect(status().isOk());

        // Validate the Tip in the database
        List<Tip> tipList = tipRepository.findAll();
        assertThat(tipList).hasSize(databaseSizeBeforeUpdate);
        Tip testTip = tipList.get(tipList.size() - 1);
        assertThat(testTip.getInformation()).isEqualTo(UPDATED_INFORMATION);
        assertThat(testTip.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testTip.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testTip.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingTip() throws Exception {
        int databaseSizeBeforeUpdate = tipRepository.findAll().size();

        // Create the Tip

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTipMockMvc.perform(put("/api/tips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tip)))
            .andExpect(status().isCreated());

        // Validate the Tip in the database
        List<Tip> tipList = tipRepository.findAll();
        assertThat(tipList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTip() throws Exception {
        // Initialize the database
        tipRepository.saveAndFlush(tip);
        int databaseSizeBeforeDelete = tipRepository.findAll().size();

        // Get the tip
        restTipMockMvc.perform(delete("/api/tips/{id}", tip.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tip> tipList = tipRepository.findAll();
        assertThat(tipList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tip.class);
        Tip tip1 = new Tip();
        tip1.setId(1L);
        Tip tip2 = new Tip();
        tip2.setId(tip1.getId());
        assertThat(tip1).isEqualTo(tip2);
        tip2.setId(2L);
        assertThat(tip1).isNotEqualTo(tip2);
        tip1.setId(null);
        assertThat(tip1).isNotEqualTo(tip2);
    }
}
