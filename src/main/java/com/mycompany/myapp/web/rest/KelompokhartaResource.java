package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Kelompokharta;

import com.mycompany.myapp.repository.KelompokhartaRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Kelompokharta.
 */
@RestController
@RequestMapping("/api")
public class KelompokhartaResource {

    private final Logger log = LoggerFactory.getLogger(KelompokhartaResource.class);

    private static final String ENTITY_NAME = "kelompokharta";

    private final KelompokhartaRepository kelompokhartaRepository;

    public KelompokhartaResource(KelompokhartaRepository kelompokhartaRepository) {
        this.kelompokhartaRepository = kelompokhartaRepository;
    }

    /**
     * POST  /kelompokhartas : Create a new kelompokharta.
     *
     * @param kelompokharta the kelompokharta to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kelompokharta, or with status 400 (Bad Request) if the kelompokharta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/kelompokhartas")
    @Timed
    public ResponseEntity<Kelompokharta> createKelompokharta(@Valid @RequestBody Kelompokharta kelompokharta) throws URISyntaxException {
        log.debug("REST request to save Kelompokharta : {}", kelompokharta);
        if (kelompokharta.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new kelompokharta cannot already have an ID")).body(null);
        }
        Kelompokharta result = kelompokhartaRepository.save(kelompokharta);
        return ResponseEntity.created(new URI("/api/kelompokhartas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /kelompokhartas : Updates an existing kelompokharta.
     *
     * @param kelompokharta the kelompokharta to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kelompokharta,
     * or with status 400 (Bad Request) if the kelompokharta is not valid,
     * or with status 500 (Internal Server Error) if the kelompokharta couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/kelompokhartas")
    @Timed
    public ResponseEntity<Kelompokharta> updateKelompokharta(@Valid @RequestBody Kelompokharta kelompokharta) throws URISyntaxException {
        log.debug("REST request to update Kelompokharta : {}", kelompokharta);
        if (kelompokharta.getId() == null) {
            return createKelompokharta(kelompokharta);
        }
        Kelompokharta result = kelompokhartaRepository.save(kelompokharta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, kelompokharta.getId().toString()))
            .body(result);
    }

    /**
     * GET  /kelompokhartas : get all the kelompokhartas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of kelompokhartas in body
     */
    @GetMapping("/kelompokhartas")
    @Timed
    public ResponseEntity<List<Kelompokharta>> getAllKelompokhartas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Kelompokhartas");
        Page<Kelompokharta> page = kelompokhartaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/kelompokhartas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /kelompokhartas/:id : get the "id" kelompokharta.
     *
     * @param id the id of the kelompokharta to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kelompokharta, or with status 404 (Not Found)
     */
    @GetMapping("/kelompokhartas/{id}")
    @Timed
    public ResponseEntity<Kelompokharta> getKelompokharta(@PathVariable Long id) {
        log.debug("REST request to get Kelompokharta : {}", id);
        Kelompokharta kelompokharta = kelompokhartaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(kelompokharta));
    }

    /**
     * DELETE  /kelompokhartas/:id : delete the "id" kelompokharta.
     *
     * @param id the id of the kelompokharta to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/kelompokhartas/{id}")
    @Timed
    public ResponseEntity<Void> deleteKelompokharta(@PathVariable Long id) {
        log.debug("REST request to delete Kelompokharta : {}", id);
        kelompokhartaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
