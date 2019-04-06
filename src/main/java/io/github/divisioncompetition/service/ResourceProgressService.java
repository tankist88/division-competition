package io.github.divisioncompetition.service;

import io.github.divisioncompetition.domain.ResourceProgress;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ResourceProgress.
 */
public interface ResourceProgressService {

    /**
     * Save a resourceProgress.
     *
     * @param resourceProgress the entity to save
     * @return the persisted entity
     */
    ResourceProgress save(ResourceProgress resourceProgress);

    /**
     * Get all the resourceProgresses.
     *
     * @return the list of entities
     */
    List<ResourceProgress> findAll();


    /**
     * Get the "id" resourceProgress.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ResourceProgress> findOne(Long id);

    /**
     * Delete the "id" resourceProgress.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
