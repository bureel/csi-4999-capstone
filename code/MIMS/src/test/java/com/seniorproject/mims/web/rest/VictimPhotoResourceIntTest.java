package com.seniorproject.mims.web.rest;

import com.seniorproject.mims.MimsApp;

import com.seniorproject.mims.domain.VictimPhoto;
import com.seniorproject.mims.repository.VictimPhotoRepository;
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
 * Test class for the VictimPhotoResource REST controller.
 *
 * @see VictimPhotoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MimsApp.class)
public class VictimPhotoResourceIntTest {

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private VictimPhotoRepository victimPhotoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVictimPhotoMockMvc;

    private VictimPhoto victimPhoto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VictimPhotoResource victimPhotoResource = new VictimPhotoResource(victimPhotoRepository);
        this.restVictimPhotoMockMvc = MockMvcBuilders.standaloneSetup(victimPhotoResource)
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
    public static VictimPhoto createEntity(EntityManager em) {
        VictimPhoto victimPhoto = new VictimPhoto()
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
        return victimPhoto;
    }

    @Before
    public void initTest() {
        victimPhoto = createEntity(em);
    }

    @Test
    @Transactional
    public void createVictimPhoto() throws Exception {
        int databaseSizeBeforeCreate = victimPhotoRepository.findAll().size();

        // Create the VictimPhoto
        restVictimPhotoMockMvc.perform(post("/api/victim-photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(victimPhoto)))
            .andExpect(status().isCreated());

        // Validate the VictimPhoto in the database
        List<VictimPhoto> victimPhotoList = victimPhotoRepository.findAll();
        assertThat(victimPhotoList).hasSize(databaseSizeBeforeCreate + 1);
        VictimPhoto testVictimPhoto = victimPhotoList.get(victimPhotoList.size() - 1);
        assertThat(testVictimPhoto.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testVictimPhoto.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createVictimPhotoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = victimPhotoRepository.findAll().size();

        // Create the VictimPhoto with an existing ID
        victimPhoto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVictimPhotoMockMvc.perform(post("/api/victim-photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(victimPhoto)))
            .andExpect(status().isBadRequest());

        // Validate the VictimPhoto in the database
        List<VictimPhoto> victimPhotoList = victimPhotoRepository.findAll();
        assertThat(victimPhotoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVictimPhotos() throws Exception {
        // Initialize the database
        victimPhotoRepository.saveAndFlush(victimPhoto);

        // Get all the victimPhotoList
        restVictimPhotoMockMvc.perform(get("/api/victim-photos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(victimPhoto.getId().intValue())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))));
    }

    @Test
    @Transactional
    public void getVictimPhoto() throws Exception {
        // Initialize the database
        victimPhotoRepository.saveAndFlush(victimPhoto);

        // Get the victimPhoto
        restVictimPhotoMockMvc.perform(get("/api/victim-photos/{id}", victimPhoto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(victimPhoto.getId().intValue()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)));
    }

    @Test
    @Transactional
    public void getNonExistingVictimPhoto() throws Exception {
        // Get the victimPhoto
        restVictimPhotoMockMvc.perform(get("/api/victim-photos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVictimPhoto() throws Exception {
        // Initialize the database
        victimPhotoRepository.saveAndFlush(victimPhoto);
        int databaseSizeBeforeUpdate = victimPhotoRepository.findAll().size();

        // Update the victimPhoto
        VictimPhoto updatedVictimPhoto = victimPhotoRepository.findOne(victimPhoto.getId());
        updatedVictimPhoto
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

        restVictimPhotoMockMvc.perform(put("/api/victim-photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVictimPhoto)))
            .andExpect(status().isOk());

        // Validate the VictimPhoto in the database
        List<VictimPhoto> victimPhotoList = victimPhotoRepository.findAll();
        assertThat(victimPhotoList).hasSize(databaseSizeBeforeUpdate);
        VictimPhoto testVictimPhoto = victimPhotoList.get(victimPhotoList.size() - 1);
        assertThat(testVictimPhoto.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testVictimPhoto.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingVictimPhoto() throws Exception {
        int databaseSizeBeforeUpdate = victimPhotoRepository.findAll().size();

        // Create the VictimPhoto

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVictimPhotoMockMvc.perform(put("/api/victim-photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(victimPhoto)))
            .andExpect(status().isCreated());

        // Validate the VictimPhoto in the database
        List<VictimPhoto> victimPhotoList = victimPhotoRepository.findAll();
        assertThat(victimPhotoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVictimPhoto() throws Exception {
        // Initialize the database
        victimPhotoRepository.saveAndFlush(victimPhoto);
        int databaseSizeBeforeDelete = victimPhotoRepository.findAll().size();

        // Get the victimPhoto
        restVictimPhotoMockMvc.perform(delete("/api/victim-photos/{id}", victimPhoto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VictimPhoto> victimPhotoList = victimPhotoRepository.findAll();
        assertThat(victimPhotoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VictimPhoto.class);
        VictimPhoto victimPhoto1 = new VictimPhoto();
        victimPhoto1.setId(1L);
        VictimPhoto victimPhoto2 = new VictimPhoto();
        victimPhoto2.setId(victimPhoto1.getId());
        assertThat(victimPhoto1).isEqualTo(victimPhoto2);
        victimPhoto2.setId(2L);
        assertThat(victimPhoto1).isNotEqualTo(victimPhoto2);
        victimPhoto1.setId(null);
        assertThat(victimPhoto1).isNotEqualTo(victimPhoto2);
    }
}
