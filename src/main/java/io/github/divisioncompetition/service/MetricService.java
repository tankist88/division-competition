package io.github.divisioncompetition.service;

import io.github.divisioncompetition.domain.Metric;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Metric.
 */
public interface MetricService {

    /**
     * Save a metric.
     *
     * @param metric the entity to save
     * @return the persisted entity
     */
    Metric save(Metric metric);

    /**
     * Get all the metrics.
     *
     * @return the list of entities
     */
    List<Metric> findAll();


    /**
     * Get the "id" metric.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Metric> findOne(Long id);

    /**
     * Delete the "id" metric.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
