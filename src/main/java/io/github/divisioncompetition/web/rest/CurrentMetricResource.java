package io.github.divisioncompetition.web.rest;
import io.github.divisioncompetition.domain.CurrentMetric;
import io.github.divisioncompetition.service.CurrentMetricService;
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
 * REST controller for managing CurrentMetric.
 */
@RestController
@RequestMapping("/api")
public class CurrentMetricResource {

    private final Logger log = LoggerFactory.getLogger(CurrentMetricResource.class);

    private static final String ENTITY_NAME = "currentMetric";

    private final CurrentMetricService currentMetricService;

    public CurrentMetricResource(CurrentMetricService currentMetricService) {
        this.currentMetricService = currentMetricService;
    }

    /**
     * POST  /current-metrics : Create a new currentMetric.
     *
     * @param currentMetric the currentMetric to create
     * @return the ResponseEntity with status 201 (Created) and with body the new currentMetric, or with status 400 (Bad Request) if the currentMetric has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/current-metrics")
    public ResponseEntity<CurrentMetric> createCurrentMetric(@Valid @RequestBody CurrentMetric currentMetric) throws URISyntaxException {
        log.debug("REST request to save CurrentMetric : {}", currentMetric);
        if (currentMetric.getId() != null) {
            throw new BadRequestAlertException("A new currentMetric cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CurrentMetric result = currentMetricService.save(currentMetric);
        return ResponseEntity.created(new URI("/api/current-metrics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /current-metrics : Updates an existing currentMetric.
     *
     * @param currentMetric the currentMetric to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated currentMetric,
     * or with status 400 (Bad Request) if the currentMetric is not valid,
     * or with status 500 (Internal Server Error) if the currentMetric couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/current-metrics")
    public ResponseEntity<CurrentMetric> updateCurrentMetric(@Valid @RequestBody CurrentMetric currentMetric) throws URISyntaxException {
        log.debug("REST request to update CurrentMetric : {}", currentMetric);
        if (currentMetric.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CurrentMetric result = currentMetricService.save(currentMetric);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, currentMetric.getId().toString()))
            .body(result);
    }

    /**
     * GET  /current-metrics : get all the currentMetrics.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of currentMetrics in body
     */
    @GetMapping("/current-metrics")
    public List<CurrentMetric> getAllCurrentMetrics() {
        log.debug("REST request to get all CurrentMetrics");
        return currentMetricService.findAll();
    }

    /**
     * GET  /current-metrics/:id : get the "id" currentMetric.
     *
     * @param id the id of the currentMetric to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the currentMetric, or with status 404 (Not Found)
     */
    @GetMapping("/current-metrics/{id}")
    public ResponseEntity<CurrentMetric> getCurrentMetric(@PathVariable Long id) {
        log.debug("REST request to get CurrentMetric : {}", id);
        Optional<CurrentMetric> currentMetric = currentMetricService.findOne(id);
        return ResponseUtil.wrapOrNotFound(currentMetric);
    }

    /**
     * DELETE  /current-metrics/:id : delete the "id" currentMetric.
     *
     * @param id the id of the currentMetric to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/current-metrics/{id}")
    public ResponseEntity<Void> deleteCurrentMetric(@PathVariable Long id) {
        log.debug("REST request to delete CurrentMetric : {}", id);
        currentMetricService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
