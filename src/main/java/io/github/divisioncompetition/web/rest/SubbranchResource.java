package io.github.divisioncompetition.web.rest;
import io.github.divisioncompetition.domain.Subbranch;
import io.github.divisioncompetition.service.SubbranchService;
import io.github.divisioncompetition.web.rest.errors.BadRequestAlertException;
import io.github.divisioncompetition.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Subbranch.
 */
@RestController
@RequestMapping("/api")
public class SubbranchResource {

    private final Logger log = LoggerFactory.getLogger(SubbranchResource.class);

    private static final String ENTITY_NAME = "subbranch";

    private final SubbranchService subbranchService;

    public SubbranchResource(SubbranchService subbranchService) {
        this.subbranchService = subbranchService;
    }

    /**
     * POST  /subbranches : Create a new subbranch.
     *
     * @param subbranch the subbranch to create
     * @return the ResponseEntity with status 201 (Created) and with body the new subbranch, or with status 400 (Bad Request) if the subbranch has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/subbranches")
    public ResponseEntity<Subbranch> createSubbranch(@Valid @RequestBody Subbranch subbranch) throws URISyntaxException {
        log.debug("REST request to save Subbranch : {}", subbranch);
        if (subbranch.getId() != null) {
            throw new BadRequestAlertException("A new subbranch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Subbranch result = subbranchService.save(subbranch);
        return ResponseEntity.created(new URI("/api/subbranches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /subbranches : Updates an existing subbranch.
     *
     * @param subbranch the subbranch to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated subbranch,
     * or with status 400 (Bad Request) if the subbranch is not valid,
     * or with status 500 (Internal Server Error) if the subbranch couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/subbranches")
    public ResponseEntity<Subbranch> updateSubbranch(@Valid @RequestBody Subbranch subbranch) throws URISyntaxException {
        log.debug("REST request to update Subbranch : {}", subbranch);
        if (subbranch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Subbranch result = subbranchService.save(subbranch);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, subbranch.getId().toString()))
            .body(result);
    }

    /**
     * GET  /subbranches : get all the subbranches.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of subbranches in body
     */
    @GetMapping("/subbranches")
    public List<Subbranch> getAllSubbranches() {
        log.debug("REST request to get all Subbranches");
        return subbranchService.findAll();
    }

    /**
     * GET  /subbranches/:id : get the "id" subbranch.
     *
     * @param id the id of the subbranch to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subbranch, or with status 404 (Not Found)
     */
    @GetMapping("/subbranches/{id}")
    public ResponseEntity<Subbranch> getSubbranch(@PathVariable Long id) {
        log.debug("REST request to get Subbranch : {}", id);
        Optional<Subbranch> subbranch = subbranchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subbranch);
    }

    /**
     * DELETE  /subbranches/:id : delete the "id" subbranch.
     *
     * @param id the id of the subbranch to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/subbranches/{id}")
    public ResponseEntity<Void> deleteSubbranch(@PathVariable Long id) {
        log.debug("REST request to delete Subbranch : {}", id);
        subbranchService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
