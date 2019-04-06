package io.github.divisioncompetition.service;

import io.github.divisioncompetition.domain.Subbranch;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Subbranch.
 */
public interface SubbranchService {

    /**
     * Save a subbranch.
     *
     * @param subbranch the entity to save
     * @return the persisted entity
     */
    Subbranch save(Subbranch subbranch);

    /**
     * Get all the subbranches.
     *
     * @return the list of entities
     */
    List<Subbranch> findAll();


    /**
     * Get the "id" subbranch.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Subbranch> findOne(Long id);

    /**
     * Delete the "id" subbranch.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
