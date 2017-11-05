package com.seniorproject.mims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.seniorproject.mims.domain.VictimPhoto;

import com.seniorproject.mims.repository.VictimPhotoRepository;
import com.seniorproject.mims.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing VictimPhoto.
 */
@RestController
@RequestMapping("/api")
public class VictimPhotoResource {

    private final Logger log = LoggerFactory.getLogger(VictimPhotoResource.class);

    private static final String ENTITY_NAME = "victimPhoto";

    private final VictimPhotoRepository victimPhotoRepository;

    public VictimPhotoResource(VictimPhotoRepository victimPhotoRepository) {
        this.victimPhotoRepository = victimPhotoRepository;
    }

    /**
     * POST  /victim-photos : Create a new victimPhoto.
     *
     * @param victimPhoto the victimPhoto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new victimPhoto, or with status 400 (Bad Request) if the victimPhoto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/victim-photos")
    @Timed
    public ResponseEntity<VictimPhoto> createVictimPhoto(@RequestBody VictimPhoto victimPhoto) throws URISyntaxException {
        log.debug("REST request to save VictimPhoto : {}", victimPhoto);
        if (victimPhoto.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new victimPhoto cannot already have an ID")).body(null);
        }
        VictimPhoto result = victimPhotoRepository.save(victimPhoto);
        return ResponseEntity.created(new URI("/api/victim-photos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /victim-photos : Updates an existing victimPhoto.
     *
     * @param victimPhoto the victimPhoto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated victimPhoto,
     * or with status 400 (Bad Request) if the victimPhoto is not valid,
     * or with status 500 (Internal Server Error) if the victimPhoto couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/victim-photos")
    @Timed
    public ResponseEntity<VictimPhoto> updateVictimPhoto(@RequestBody VictimPhoto victimPhoto) throws URISyntaxException {
        log.debug("REST request to update VictimPhoto : {}", victimPhoto);
        if (victimPhoto.getId() == null) {
            return createVictimPhoto(victimPhoto);
        }
        VictimPhoto result = victimPhotoRepository.save(victimPhoto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, victimPhoto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /victim-photos : get all the victimPhotos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of victimPhotos in body
     */
    @GetMapping("/victim-photos")
    @Timed
    public List<VictimPhoto> getAllVictimPhotos() {
        log.debug("REST request to get all VictimPhotos");
        return victimPhotoRepository.findAll();
        }

    /**
     * GET  /victim-photos/:id : get the "id" victimPhoto.
     *
     * @param id the id of the victimPhoto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the victimPhoto, or with status 404 (Not Found)
     */
    @GetMapping("/victim-photos/{id}")
    @Timed
    public ResponseEntity<VictimPhoto> getVictimPhoto(@PathVariable Long id) {
        log.debug("REST request to get VictimPhoto : {}", id);
        VictimPhoto victimPhoto = victimPhotoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(victimPhoto));
    }

    /**
     * DELETE  /victim-photos/:id : delete the "id" victimPhoto.
     *
     * @param id the id of the victimPhoto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/victim-photos/{id}")
    @Timed
    public ResponseEntity<Void> deleteVictimPhoto(@PathVariable Long id) {
        log.debug("REST request to delete VictimPhoto : {}", id);
        victimPhotoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
