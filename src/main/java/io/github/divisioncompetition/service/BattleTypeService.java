package io.github.divisioncompetition.service;

import io.github.divisioncompetition.domain.BattleType;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing BattleType.
 */
public interface BattleTypeService {

    /**
     * Save a battleType.
     *
     * @param battleType the entity to save
     * @return the persisted entity
     */
    BattleType save(BattleType battleType);

    /**
     * Get all the battleTypes.
     *
     * @return the list of entities
     */
    List<BattleType> findAll();


    /**
     * Get the "id" battleType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BattleType> findOne(Long id);

    /**
     * Delete the "id" battleType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
