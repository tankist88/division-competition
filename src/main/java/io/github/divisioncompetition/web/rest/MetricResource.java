package io.github.divisioncompetition.web.rest;
import io.github.divisioncompetition.domain.Metric;
import io.github.divisioncompetition.service.MetricService;
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
 * REST controller for managing Metric.
 */
@RestController
@RequestMapping("/api")
public class MetricResource {

    private final Logger log = LoggerFactory.getLogger(MetricResource.class);

    private static final String ENTITY_NAME = "metric";

    private final MetricService metricService;

    public MetricResource(MetricService metricService) {
        this.metricService = metricService;
    }

    /**
     * POST  /metrics : Create a new metric.
     *
     * @param metric the metric to create
     * @return the ResponseEntity with status 201 (Created) and with body the new metric, or with status 400 (Bad Request) if the metric has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/metrics")
    public ResponseEntity<Metric> createMetric(@Valid @RequestBody Metric metric) throws URISyntaxException {
        log.debug("REST request to save Metric : {}", metric);
        if (metric.getId() != null) {
            throw new BadRequestAlertException("A new metric cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Metric result = metricService.save(metric);
        return ResponseEntity.created(new URI("/api/metrics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /metrics : Updates an existing metric.
     *
     * @param metric the metric to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated metric,
     * or with status 400 (Bad Request) if the metric is not valid,
     * or with status 500 (Internal Server Error) if the metric couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/metrics")
    public ResponseEntity<Metric> updateMetric(@Valid @RequestBody Metric metric) throws URISyntaxException {
        log.debug("REST request to update Metric : {}", metric);
        if (metric.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Metric result = metricService.save(metric);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, metric.getId().toString()))
            .body(result);
    }

    /**
     * GET  /metrics : get all the metrics.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of metrics in body
     */
    @GetMapping("/metrics")
    public List<Metric> getAllMetrics() {
        log.debug("REST request to get all Metrics");
        return metricService.findAll();
    }

    /**
     * GET  /metrics/:id : get the "id" metric.
     *
     * @param id the id of the metric to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the metric, or with status 404 (Not Found)
     */
    @GetMapping("/metrics/{id}")
    public ResponseEntity<Metric> getMetric(@PathVariable Long id) {
        log.debug("REST request to get Metric : {}", id);
        Optional<Metric> metric = metricService.findOne(id);
        return ResponseUtil.wrapOrNotFound(metric);
    }

    /**
     * DELETE  /metrics/:id : delete the "id" metric.
     *
     * @param id the id of the metric to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/metrics/{id}")
    public ResponseEntity<Void> deleteMetric(@PathVariable Long id) {
        log.debug("REST request to delete Metric : {}", id);
        metricService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
