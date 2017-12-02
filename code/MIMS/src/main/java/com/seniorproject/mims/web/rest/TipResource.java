package com.seniorproject.mims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.seniorproject.mims.domain.Tip;

import com.seniorproject.mims.repository.TipRepository;
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
 * REST controller for managing Tip.
 */
@RestController
@RequestMapping("/api")
public class TipResource {

    private final Logger log = LoggerFactory.getLogger(TipResource.class);

    private static final String ENTITY_NAME = "tip";

    private final TipRepository tipRepository;

    public TipResource(TipRepository tipRepository) {
        this.tipRepository = tipRepository;
    }

    /**
     * POST  /tips : Create a new tip.
     *
     * @param tip the tip to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tip, or with status 400 (Bad Request) if the tip has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tips")
    @Timed
    public ResponseEntity<Tip> createTip(@RequestBody Tip tip) throws URISyntaxException {
        log.debug("REST request to save Tip : {}", tip);
        if (tip.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tip cannot already have an ID")).body(null);
        }
        Tip result = tipRepository.save(tip);
        return ResponseEntity.created(new URI("/api/tips/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tips : Updates an existing tip.
     *
     * @param tip the tip to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tip,
     * or with status 400 (Bad Request) if the tip is not valid,
     * or with status 500 (Internal Server Error) if the tip couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tips")
    @Timed
    public ResponseEntity<Tip> updateTip(@RequestBody Tip tip) throws URISyntaxException {
        log.debug("REST request to update Tip : {}", tip);
        if (tip.getId() == null) {
            return createTip(tip);
        }
        Tip result = tipRepository.save(tip);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tip.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tips : get all the tips.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tips in body
     */
    @GetMapping("/tips")
    @Timed
    public List<Tip> getAllTips() {
        log.debug("REST request to get all Tips");
        return tipRepository.findAll();
        }

    /**
     * GET  /tips/:id : get the "id" tip.
     *
     * @param id the id of the tip to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tip, or with status 404 (Not Found)
     */
    @GetMapping("/tips/{id}")
    @Timed
    public ResponseEntity<Tip> getTip(@PathVariable Long id) {
        log.debug("REST request to get Tip : {}", id);
        Tip tip = tipRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tip));
    }

    /**
     * DELETE  /tips/:id : delete the "id" tip.
     *
     * @param id the id of the tip to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tips/{id}")
    @Timed
    public ResponseEntity<Void> deleteTip(@PathVariable Long id) {
        log.debug("REST request to delete Tip : {}", id);
        tipRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
