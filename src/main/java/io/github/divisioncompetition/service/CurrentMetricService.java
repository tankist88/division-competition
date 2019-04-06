package io.github.divisioncompetition.service;

import io.github.divisioncompetition.domain.CurrentMetric;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CurrentMetric.
 */
public interface CurrentMetricService {

    /**
     * Save a currentMetric.
     *
     * @param currentMetric the entity to save
     * @return the persisted entity
     */
    CurrentMetric save(CurrentMetric currentMetric);

    /**
     * Get all the currentMetrics.
     *
     * @return the list of entities
     */
    List<CurrentMetric> findAll();


    /**
     * Get the "id" currentMetric.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CurrentMetric> findOne(Long id);

    /**
     * Delete the "id" currentMetric.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
