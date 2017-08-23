package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Jenisharta;

import com.mycompany.myapp.repository.JenishartaRepository;
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
 * REST controller for managing Jenisharta.
 */
@RestController
@RequestMapping("/api")
public class JenishartaResource {

    private final Logger log = LoggerFactory.getLogger(JenishartaResource.class);

    private static final String ENTITY_NAME = "jenisharta";

    private final JenishartaRepository jenishartaRepository;

    public JenishartaResource(JenishartaRepository jenishartaRepository) {
        this.jenishartaRepository = jenishartaRepository;
    }

    /**
     * POST  /jenishartas : Create a new jenisharta.
     *
     * @param jenisharta the jenisharta to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jenisharta, or with status 400 (Bad Request) if the jenisharta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/jenishartas")
    @Timed
    public ResponseEntity<Jenisharta> createJenisharta(@Valid @RequestBody Jenisharta jenisharta) throws URISyntaxException {
        log.debug("REST request to save Jenisharta : {}", jenisharta);
        if (jenisharta.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new jenisharta cannot already have an ID")).body(null);
        }
        Jenisharta result = jenishartaRepository.save(jenisharta);
        return ResponseEntity.created(new URI("/api/jenishartas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jenishartas : Updates an existing jenisharta.
     *
     * @param jenisharta the jenisharta to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jenisharta,
     * or with status 400 (Bad Request) if the jenisharta is not valid,
     * or with status 500 (Internal Server Error) if the jenisharta couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/jenishartas")
    @Timed
    public ResponseEntity<Jenisharta> updateJenisharta(@Valid @RequestBody Jenisharta jenisharta) throws URISyntaxException {
        log.debug("REST request to update Jenisharta : {}", jenisharta);
        if (jenisharta.getId() == null) {
            return createJenisharta(jenisharta);
        }
        Jenisharta result = jenishartaRepository.save(jenisharta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jenisharta.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jenishartas : get all the jenishartas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of jenishartas in body
     */
    @GetMapping("/jenishartas")
    @Timed
    public ResponseEntity<List<Jenisharta>> getAllJenishartas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Jenishartas");
        Page<Jenisharta> page = jenishartaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jenishartas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /jenishartas/:id : get the "id" jenisharta.
     *
     * @param id the id of the jenisharta to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jenisharta, or with status 404 (Not Found)
     */
    @GetMapping("/jenishartas/{id}")
    @Timed
    public ResponseEntity<Jenisharta> getJenisharta(@PathVariable Long id) {
        log.debug("REST request to get Jenisharta : {}", id);
        Jenisharta jenisharta = jenishartaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jenisharta));
    }

    /**
     * DELETE  /jenishartas/:id : delete the "id" jenisharta.
     *
     * @param id the id of the jenisharta to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/jenishartas/{id}")
    @Timed
    public ResponseEntity<Void> deleteJenisharta(@PathVariable Long id) {
        log.debug("REST request to delete Jenisharta : {}", id);
        jenishartaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
