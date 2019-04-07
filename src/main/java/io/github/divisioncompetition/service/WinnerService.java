package io.github.divisioncompetition.service;

import io.github.divisioncompetition.domain.Winner;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Winner.
 */
public interface WinnerService {

    /**
     * Save a winner.
     *
     * @param winner the entity to save
     * @return the persisted entity
     */
    Winner save(Winner winner);

    /**
     * Get all the winners.
     *
     * @return the list of entities
     */
    List<Winner> findAll();


    /**
     * Get the "id" winner.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Winner> findOne(Long id);

    /**
     * Delete the "id" winner.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
