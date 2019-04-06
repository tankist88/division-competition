package io.github.divisioncompetition.web.rest;
import io.github.divisioncompetition.domain.ResourceProgress;
import io.github.divisioncompetition.service.ResourceProgressService;
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
 * REST controller for managing ResourceProgress.
 */
@RestController
@RequestMapping("/api")
public class ResourceProgressResource {

    private final Logger log = LoggerFactory.getLogger(ResourceProgressResource.class);

    private static final String ENTITY_NAME = "resourceProgress";

    private final ResourceProgressService resourceProgressService;

    public ResourceProgressResource(ResourceProgressService resourceProgressService) {
        this.resourceProgressService = resourceProgressService;
    }

    /**
     * POST  /resource-progresses : Create a new resourceProgress.
     *
     * @param resourceProgress the resourceProgress to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resourceProgress, or with status 400 (Bad Request) if the resourceProgress has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/resource-progresses")
    public ResponseEntity<ResourceProgress> createResourceProgress(@Valid @RequestBody ResourceProgress resourceProgress) throws URISyntaxException {
        log.debug("REST request to save ResourceProgress : {}", resourceProgress);
        if (resourceProgress.getId() != null) {
            throw new BadRequestAlertException("A new resourceProgress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResourceProgress result = resourceProgressService.save(resourceProgress);
        return ResponseEntity.created(new URI("/api/resource-progresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /resource-progresses : Updates an existing resourceProgress.
     *
     * @param resourceProgress the resourceProgress to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resourceProgress,
     * or with status 400 (Bad Request) if the resourceProgress is not valid,
     * or with status 500 (Internal Server Error) if the resourceProgress couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/resource-progresses")
    public ResponseEntity<ResourceProgress> updateResourceProgress(@Valid @RequestBody ResourceProgress resourceProgress) throws URISyntaxException {
        log.debug("REST request to update ResourceProgress : {}", resourceProgress);
        if (resourceProgress.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResourceProgress result = resourceProgressService.save(resourceProgress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, resourceProgress.getId().toString()))
            .body(result);
    }

    /**
     * GET  /resource-progresses : get all the resourceProgresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of resourceProgresses in body
     */
    @GetMapping("/resource-progresses")
    public List<ResourceProgress> getAllResourceProgresses() {
        log.debug("REST request to get all ResourceProgresses");
        return resourceProgressService.findAll();
    }

    /**
     * GET  /resource-progresses/:id : get the "id" resourceProgress.
     *
     * @param id the id of the resourceProgress to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resourceProgress, or with status 404 (Not Found)
     */
    @GetMapping("/resource-progresses/{id}")
    public ResponseEntity<ResourceProgress> getResourceProgress(@PathVariable Long id) {
        log.debug("REST request to get ResourceProgress : {}", id);
        Optional<ResourceProgress> resourceProgress = resourceProgressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resourceProgress);
    }

    /**
     * DELETE  /resource-progresses/:id : delete the "id" resourceProgress.
     *
     * @param id the id of the resourceProgress to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/resource-progresses/{id}")
    public ResponseEntity<Void> deleteResourceProgress(@PathVariable Long id) {
        log.debug("REST request to delete ResourceProgress : {}", id);
        resourceProgressService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
