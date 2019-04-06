package io.github.divisioncompetition.service;

import io.github.divisioncompetition.domain.Building;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Building.
 */
public interface BuildingService {

    /**
     * Save a building.
     *
     * @param building the entity to save
     * @return the persisted entity
     */
    Building save(Building building);

    /**
     * Get all the buildings.
     *
     * @return the list of entities
     */
    List<Building> findAll();


    /**
     * Get the "id" building.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Building> findOne(Long id);

    /**
     * Delete the "id" building.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
